/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_CupiBlog
 * Autor: Equipo Cupi2 2019
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package mundo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Clase que representa a un cliente del blog.<br>
 * <b>inv: </b><br>
 * manejadorEventos != null. <br>
 * La lista de art�culos est� inicializada. <br>
 */
public class ClienteBlog {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Usuario del blog.
     */
    private Usuario usuario;

    /**
     * Art�culo actual.
     */
    private Articulo articuloActual;

    /**
     * Lista de art�culos que se han publicado.
     */
    private ArrayList<Articulo> articulos;

    /**
     * Observador de eventos del cliente.
     */
    private IObservadorEventos observadorEventos;

    /**
     * Manejador de la comunicaci�n con el servidor.
     */
    private ManejadorComunicacionServidor comunicacion;

    /**
     * Direcci�n IP del servidor.
     */
    private String ipServidor;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * El constructor del cliente del blog. <br>
     * <b> post: </b> Los atributos observadoEventos e IpServidor se inicializaron con los valores dados por par�metro. <br>
     * El usuario y el art�culo actual se inicializaron en null. <br>
     * La lista de art�culos qued� inicializada. <br>
     *
     * @param pObservador El observador de los eventos del cliente. pObservador != null.
     * @param pIpServidor La direcci�n IP del servidor al cual se va a conectar el cliente. pIpSevidor != null && pIpServidor != "".
     */
    public ClienteBlog(IObservadorEventos pObservador, String pIpServidor) {
        observadorEventos = pObservador;
        usuario = null;
        articuloActual = null;
        ipServidor = pIpServidor;

        articulos = new ArrayList<Articulo>();

        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna al usuario del blog.
     *
     * @return Usuario del blog.
     */
    public Usuario darUsuario() {
        return usuario;
    }

    /**
     * Retorna el art�culo actual.
     *
     * @return Art�culo actual.
     */
    public Articulo darArticuloActual() {
        return articuloActual;
    }

    /**
     * Retorna la lista de art�culos publicados del blog.
     *
     * @return Lista de art�culos publicados.
     */
    public ArrayList<Articulo> darListaArticulos() {
        return articulos;
    }

    /**
     * Modifica al usuario del blog por el dado por par�metro. <br>
     * <b> post: </b> El usuario fue modificado por el usuario dado por par�metro.
     *
     * @param pUsuario El nuevo usuario cliente. pUsuario != null.
     */
    public void modificarUsuario(Usuario pUsuario) {
        usuario = pUsuario;
    }

    /**
     * Modifica el art�culo actual por el dado por par�metro. <br>
     * <b> post: </b> El art�culo actual fue modificado por el dado por par�metro.
     *
     * @param pArticuloActual El nuevo art�culo. pArticuloActual != null.
     */
    public void modificarArticuloActual(Articulo pArticuloActual) {
        articuloActual = pArticuloActual;
    }

    /**
     * Modifica el contenido de la lista de art�culos. <br>
     * <b> post: </b> La lista de art�culos fue modificada por la lista dada por par�metro.
     *
     * @param pArticulos La nueva lista de art�culos. pArticulos != null.
     */
    public void modificarListaArticulos(ArrayList<Articulo> pArticulos) {
        articulos.clear();
        articulos.addAll(pArticulos);
        observadorEventos.actualizarListaArticulos(articulos);
    }

    /**
     * Reinicia el estado del cliente. <br>
     * <b> post: </b> Los valores de usuario y articuloActual son nulos. <br>
     * La lista de art�culos ya no tiene elementos.
     */
    public void reiniciar() {
        usuario = null;
        articuloActual = null;

        articulos.clear();

        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // M�todos de conexi�n
    // -----------------------------------------------------------------

    /**
     * Establece la conexi�n con el servidor. <br>
     *
     * @throws CupiBlogComunicacionException Si no puede establecer una conexi�n con el servidor.
     */
    private void iniciarConexion() throws CupiBlogComunicacionException {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipServidor, 9999), 5000);
            comunicacion = new ManejadorComunicacionServidor(this, socket);
        } catch (UnknownHostException e) {
            throw new CupiBlogComunicacionException("No hay conexi�n con el host.");
        } catch (IOException e) {
            String mensaje = "No se pudo establecer la conexi�n con el servidor.";
            if (e.getMessage().equals("connect timed out")) {
                mensaje += " El servidor no est� disponible. \n Int�ntelo de nuevo m�s tarde, o con otra direcci�n IP.";
            }
            throw new CupiBlogComunicacionException(mensaje);
        }
    }

    /**
     * Inicia la sesi�n del usuario. <br>
     * Si la comunicaci�n con el servidor no existe, debe crearla.
     *
     * @param pLoginUsuario El login del usuario que va a iniciar sesi�n. pLoginUsuario != null && pLoginUsuario != "".
     * @throws CupiBlogComunicacionException Si no se puede establecer una conexi�n con el servidor.
     * @throws CupiBlogProtocoloException    Si no se cumple con el protocolo establecido.
     */
    public void iniciarSesion(String pLoginUsuario) throws CupiBlogComunicacionException, CupiBlogProtocoloException {
        if (comunicacion == null) {
            iniciarConexion();
        }
        reiniciar();
        comunicacion.iniciarSesion(pLoginUsuario);
        listarArticulos();
    }

    /**
     * Busca los art�culos que pertenecen a la categor�a dada por par�metro.<br>
     * <b>pre: </b>La comunicaci�n con el servidor debe estar establecida.<br>
     *
     * @param pCategoria La categor�a deseada. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     */
    public void buscarArticulosCategoria(String pCategoria) {
        comunicacion.buscarArticulosCategoria(pCategoria);
    }

    /**
     * Califica al art�culo dado con la calificaci�n dada.<br>
     * <b>pre: </b>La comunicaci�n con el servidor debe estar establecida.<br>
     *
     * @param pCalificacion Calificaci�n del art�culo. pCalificacion >= 0 && pCalificacion <= 5.
     */
    public void calificarArticulo(int pCalificacion) {
        comunicacion.calificarArticulo(articuloActual, pCalificacion);
    }

    /**
     * Lista todos los art�culos del blog por orden cronol�gico.<br>
     * <b>pre: </b> La comunicaci�n con el servidor debe estar establecida. <br>
     */
    public void listarArticulos() {
        comunicacion.solicitarListaArticulos();
    }

    /**
     * Publica un nuevo art�culo con t�tulo, categor�a y contenido dados.<br>
     * <b> pre: </b> La comunicaci�n con el servidor debe estar establecida.<br>
     *
     * @param pTitulo    T�tulo del art�culo. pTitulo != null && pTitulo != "".
     * @param pCategoria Categor�a del art�culo. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     * @param pContenido Contenido del art�culo. pContenido != null && pContenido != "".
     */
    public void publicarArticulo(String pTitulo, String pCategoria, String pContenido) {
        comunicacion.publicarArticulo(pTitulo, pCategoria, pContenido);
    }

    /**
     * Solicita las estad�sticas de publicaciones del usuario.<br>
     * <b>pre: </b>La comunicaci�n con el servidor debe estar establecida. El usuario del cliente debe estar configurado.<br>
     */
    public void solicitarEstadisticas() {
        comunicacion.solicitarEstadisticasUsuario();
    }

    /**
     * Notifica al manejador de eventos la recepci�n de la informaci�n de estad�sticas del usuario.
     *
     * @param pNumeroArticulos      N�mero de art�culos publicados por el usuario. pNumeroArticulos >= 0.
     * @param pPromedioCalificacion N�mero de comentarios publicados por el usuario. pPromedioCalificacion >= 0.
     */
    public void notificarResultadosEstadisticas(int pNumeroArticulos, double pPromedioCalificacion) {
        String mensaje = "Usted ha publicado:\n";
        mensaje += pNumeroArticulos + " Art�culo(s). \n Y el promedio de sus calificaciones es: \n";
        mensaje += pPromedioCalificacion + ".";

        notificarMensaje(mensaje, "Estad�sticas");
    }

    /**
     * Notifica un nuevo mensaje proveniente del servidor.
     *
     * @param pMensaje El mensaje proveniente del servidor. pMensaje != null && pMensaje != "".
     * @param pTitulo  Titulo del mensaje que se va a notificar. pTitulo != null && pTItulo != "".
     */
    public void notificarMensaje(String pMensaje, String pTitulo) {
        observadorEventos.notificarMensaje(pMensaje, pTitulo);
    }

    /**
     * Notifica una nueva excepci�n en la comunicaci�n con el servidor.
     *
     * @param pExcepcion La excepci�n que ocurri� con la comunicaci�n con el servidor. pExcepcion != null.
     */
    public void notificarExcepcion(Exception pExcepcion) {
        observadorEventos.notificarExcepcion(pExcepcion);
    }

    /**
     * Notifica la calificaci�n de un art�culo.
     *
     * @param pArticulo El art�culo calificado. pArticulo != null.
     */
    public void notificarCalificacion(Articulo pArticulo) {
        observadorEventos.notificarCalificacion(pArticulo);
    }

    /**
     * Registra un usuario con login, nombre y apellido dados e inicia su sesi�n. <br>
     * Si la comunicaci�n con el servidor no existe, debe crearla.
     *
     * @param pLogin    Login del usuario con el cu�l iniciar sesi�n. pLogin != null && pLogin != "".
     * @param pNombre   Nombres del usuario. pNombre != null && pNombre != "".
     * @param pApellido Apellidos del usuario. pApellido != null && pApellido != "".
     * @throws CupiBlogComunicacionException Si no se puede establecer una conexi�n con el servidor.
     * @throws CupiBlogProtocoloException    Si no se cumple con el protocolo establecido.
     */
    public void registrarUsuario(String pLogin, String pNombre, String pApellido) throws CupiBlogComunicacionException, CupiBlogProtocoloException {
        if (comunicacion == null) {
            iniciarConexion();
        }
        reiniciar();
        comunicacion.registrarUsuario(pLogin, pNombre, pApellido);
        observadorEventos.cambiarEstadoSesion(true);
        listarArticulos();
    }

    /**
     * Cierra la sesi�n del usuario del cliente del blog.
     */
    public void cerrarSesion() {
        if (comunicacion != null) {
            comunicacion.cerrarSesion();
            observadorEventos.cambiarEstadoSesion(false);
            comunicacion = null;
        }
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica la invariante de la clase. <br>
     * <b>inv: </b> <br>
     * manejadorEventos != null.<br>
     * articulos != null.<br>
     */
    private void verificarInvariante() {
        assert observadorEventos != null : "El manejador de eventos debe existir.";
        assert articulos != null : "La lista de los art�culos debe estar inicializada.";
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1.
     *
     * @return respuesta1.
     */
    public String metodo1() {
        return "Respuesta 1.";
    }

    /**
     * M�todo para la extensi�n 2.
     *
     * @return respuesta2.
     */
    public String metodo2() {
        return "Respuesta 2.";
    }

}