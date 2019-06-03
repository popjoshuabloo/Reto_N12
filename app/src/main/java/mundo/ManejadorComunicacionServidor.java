/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_cupiBlog
 * Autor: Equipo Cupi2 2019
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package mundo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que maneja la comunicaci�n con el servidor. <br>
 * <b>inv:</b> <br>
 * clienteBlog != null. <br>
 * socket != null. <br>
 * in != null. <br>
 * out != null. <br>
 */
public class ManejadorComunicacionServidor extends Thread {

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante que representa el separador de un comando.
     */
    public static final String SEPARADOR_COMANDO = ";;;";

    /**
     * Constante que representa el separador de los par�metros.
     */
    public static final String SEPARADOR_PARAMETROS = ":::";

    /**
     * Constante que representa el mensaje LOGIN para que un usuario ingrese al sistema.
     */
    public static final String LOGIN = "LOGIN";

    /**
     * Constante que representa el mensaje REGISTRAR, para que un usuario se registre en el sistema.
     */
    public static final String REGISTRAR = "REGISTRAR";

    /**
     * Constante que representa el mensaje REGISTRADO, para indicar que un usuario fue registrado en el sistema.
     */
    public static final String REGISTRADO = "REGISTRADO";

    /**
     * Constante que representa el mensaje LISTA_ARTICULOS, para iniciar el env�o de la informaci�n de los art�culos.
     */
    public static final String LISTA_ARTICULOS = "LISTA_ARTICULOS";

    /**
     * Constante que representa el mensaje ARTICULOS, para indicar de cuantos art�culos se est� enviando informaci�n.
     */
    public static final String ARTICULOS = "ARTICULOS";

    /**
     * Constante que representa el mensaje ARTICULO, para notificar el env�o de la informaci�n de un art�culo.
     */
    public static final String ARTICULO = "ARTICULO";

    /**
     * Constante que representa el mensaje PUBLICAR_ARTICULO, para publicar un art�culo.
     */
    public static final String PUBLICAR_ARTICULO = "PUBLICAR_ARTICULO";

    /**
     * Constante que representa el mensaje CALIFICAR, para enviar la calificaci�n de un art�culo.
     */
    public static final String CALIFICAR = "CALIFICAR";

    /**
     * Constante que representa el mensaje ESTADISTICAS, para solicitar las estad�sticas del promedio de las calificaciones de los art�culos de un usuario.
     */
    public static final String ESTADISTICAS = "ESTADISTICAS";

    /**
     * Constante que representa el mensaje BUSQUEDA_CATEGORIA, para solicitar la b�squeda de los art�culos de una categor�a.
     */
    public static final String BUSQUEDA_CATEGORIA = "BUSQUEDA_CATEGORIA";

    /**
     * Constante que representa el mensaje LOGOUT, para notificar el cierre de sesi�n de un usuario.
     */
    public static final String LOGOUT = "LOGOUT";

    /**
     * Constante que representa el mensaje ERROR, que se utiliza en para notificar un error en cualquiera de las peticiones.
     */
    public static final String ERROR = "ERROR";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Cliente del blog.
     */
    private ClienteBlog clienteBlog;

    /**
     * Canal de comunicaci�n con el servidor.
     */
    private Socket socket;

    /**
     * Canal de escritura en la comunicaci�n con el servidor.
     */
    private PrintWriter out;

    /**
     * Canal de escritura en la comunicaci�n con el servidor.
     */
    private BufferedReader in;

    /**
     * Indica si un art�culo ha sido calificado.
     */
    private boolean calificacion;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa el manejador de comunicaciones con el servidor a partir de la informaci�n dada por par�metro. <br>
     * <b> post: </b> Los atributos clienteBlog y socket se inicializaron con los valores dados por par�metro. <br>
     * Los canales de escritura y de lectura son inicializados para que lean o escriban usando el socket dado por par�metro. <br>
     * La calificaci�n se inicializa como false.
     *
     * @param pCliente Cliente del blog. pCliente != null.
     * @param pSocket  Canal de comunicaci�n con el servidor. pSocket != null.
     * @throws CupiBlogComunicacionException Si el canal de escritura o lectura no son creados correctamente.
     */
    public ManejadorComunicacionServidor(ClienteBlog pCliente, Socket pSocket) throws CupiBlogComunicacionException {
        calificacion = false;
        clienteBlog = pCliente;
        socket = pSocket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CupiBlogComunicacionException("No se pueden inicializar los canales de comunicaci�n.");
        }
        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // M�todos de recepci�n
    // -----------------------------------------------------------------

    /**
     * M�todo encargado de procesar un mensaje proveniente del servidor
     *
     * @return true si el mensaje proveniente del servidor confirma el cierre de sesi�n, false de lo contrario.
     * @throws CupiBlogComunicacionException Si se presenta un error en la comunicaci�n.
     * @throws CupiBlogProtocoloException    Si se presenta un error con el protocolo de comunicaci�n.
     */
    public boolean procesarMensaje() throws CupiBlogComunicacionException, CupiBlogProtocoloException {
        try {
            boolean logout = false;
            String linea = in.readLine();
            if (linea == null) {
                throw new CupiBlogComunicacionException("El usuario no se ha podido conectar.");
            }

            String[] partes = linea.split(SEPARADOR_COMANDO);
            String comando = partes[0];
            String[] parametros = {""};
            if (partes.length > 1) {
                parametros = partes[1].split(SEPARADOR_PARAMETROS);
            }

            // El servidor acepta el inicio de sesi�n.
            if (comando.equals(LOGIN)) {
                String login = parametros[0];
                String nombres = parametros[1];
                String apellidos = parametros[2];

                Usuario usuario = new Usuario(login, nombres, apellidos);
                clienteBlog.modificarUsuario(usuario);
                this.start();
            }

            // El servidor registr� al usuario
            else if (comando.equals(REGISTRADO)) {
                String login = parametros[0];
                iniciarSesion(login);
            }

            // El servidor env�a la informaci�n de los art�culos.
            else if (comando.equals(ARTICULOS)) {
                ArrayList<Articulo> articulos = new ArrayList<Articulo>();

                String tamanioArticulosString = parametros[0];
                int tamanioArticulos = Integer.parseInt(tamanioArticulosString);

                for (int i = 0; i < tamanioArticulos; i++) {
                    String respuestaServidor = in.readLine();
                    String[] partesArticulo = respuestaServidor.split(SEPARADOR_COMANDO);
                    String comandoArticulo = partesArticulo[0];
                    if (comandoArticulo.equals(ARTICULO)) {
                        String[] parametrosArticulo = partesArticulo[1].split(SEPARADOR_PARAMETROS);
                        try {
                            String loginDuenio = parametrosArticulo[0];
                            String titulo = parametrosArticulo[1];
                            String categoria = parametrosArticulo[2];
                            String contenido = parametrosArticulo[3];
                            String fechaPublicacionString = parametrosArticulo[4];
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                            Date fechaPublicacion = dateFormat.parse(fechaPublicacionString);

                            String calificacionAcumuladaString = parametrosArticulo[5];
                            int calificacionAcumulada = Integer.parseInt(calificacionAcumuladaString);

                            String vecesCalificadoString = parametrosArticulo[6];
                            int vecesCalificado = Integer.parseInt(vecesCalificadoString);

                            Articulo articulo = new Articulo(loginDuenio, titulo, categoria, contenido, fechaPublicacion, calificacionAcumulada, vecesCalificado);
                            articulos.add(articulo);
                        } catch (Exception e) {
                            throw new CupiBlogProtocoloException("Error en la construcci�n del mensaje de art�culo: " + e.getMessage() + ".");
                        }
                    } else {
                        throw new CupiBlogProtocoloException("No envi� correctamente la lista de los art�culos.");
                    }
                }
                clienteBlog.modificarListaArticulos(articulos);
                if (calificacion) {
                    Articulo articuloCalificado = articulos.get(0);
                    clienteBlog.notificarCalificacion(articuloCalificado);
                    calificacion = false;
                }
            }

            // El servidor env�a las estad�sticas.
            else if (comando.equals(ESTADISTICAS)) {
                String articulosPublicadosString = parametros[0];
                int articulosPublicados = Integer.parseInt(articulosPublicadosString);

                String promedioCalificacionString = parametros[1];
                double promedioCalificacion = Double.parseDouble(promedioCalificacionString);

                clienteBlog.notificarResultadosEstadisticas(articulosPublicados, promedioCalificacion);
            }

            // El servidor confirma el cierre de sesi�n.
            else if (comando.equals(LOGOUT)) {
                logout = true;
                cerrarConexiones();
            }

            // El servidor env�a un mensaje de error.
            else if (comando.equals(ERROR)) {
                String mensajeError = parametros[0];
                throw new CupiBlogProtocoloException("Error en el servidor: " + mensajeError);
            }

            // En caso de enviar el contenido que no deb�a.
            else {
                throw new CupiBlogProtocoloException("No recibi� correctamente uno de los mensajes: " + comando + ".");
            }

            verificarInvariante();
            return logout;
        } catch (IOException e) {
            String mensaje = "Error al leer el mensaje del servidor: " + e.getMessage() + ".";
            if (e.getMessage().equals("Connection reset")) {
                mensaje = "Se perdi� la conexi�n con el servidor.";
            }
            throw new CupiBlogComunicacionException(mensaje);
        }

    }

    // -----------------------------------------------------------------
    // M�todos de env�o
    // -----------------------------------------------------------------

    /**
     * Inicia la sesi�n del usuario dado su login.
     *
     * @param pLogin Login del usuario que desea iniciar sesi�n. pLogin != null && pLogin != "".
     * @throws CupiBlogComunicacionException Si se presenta un error en la comunicaci�n.
     * @throws CupiBlogProtocoloException    Si se presenta un error con el protocolo de comunicaci�n.
     */
    public void iniciarSesion(String pLogin) throws CupiBlogComunicacionException, CupiBlogProtocoloException {
        out.println(LOGIN + SEPARADOR_COMANDO + pLogin);
        procesarMensaje();
    }

    /**
     * Registra un usuario dado su login, nombre y apellido. <br>
     * Se inicia la sesi�n del usuario.
     *
     * @param pLogin    Login del usuario. pLogin != null && pLogin != "".
     * @param pNombre   Nombre del usuario. pNombre != null && pNombre != "".
     * @param pApellido Apellido del usuario. pApellido != null && pApellido != "".
     * @throws CupiBlogComunicacionException Si se presenta un error en la comunicaci�n.
     * @throws CupiBlogProtocoloException    Si se presenta un error con el protocolo de comunicaci�n.
     */
    public void registrarUsuario(String pLogin, String pNombre, String pApellido) throws CupiBlogComunicacionException, CupiBlogProtocoloException {
        out.println(REGISTRAR + SEPARADOR_COMANDO + pLogin + SEPARADOR_PARAMETROS + pNombre + SEPARADOR_PARAMETROS + pApellido);
        procesarMensaje();
    }

    /**
     * Solicita la lista de todos los art�culos del blog.
     */
    public void solicitarListaArticulos() {
        out.println(LISTA_ARTICULOS);
    }

    /**
     * Publica el art�culo en el servidor del blog, dado el t�tulo, la categor�a y el contenido.
     *
     * @param pTitulo    T�tulo del art�culo. pTitulo != null && pTitulo != "".
     * @param pCategoria Categor�a del art�culo. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     * @param pContenido Contenido del art�culo. pContenido != null && pContenido != "".
     */
    public void publicarArticulo(String pTitulo, String pCategoria, String pContenido) {
        out.println(PUBLICAR_ARTICULO + SEPARADOR_COMANDO + pTitulo + SEPARADOR_PARAMETROS + pCategoria + SEPARADOR_PARAMETROS + pContenido);
    }

    /**
     * Califica el art�culo dado.
     *
     * @param pArticulo     Art�culo que se va a calificar. pArticulo != null.
     * @param pCalificacion Calificaci�n del art�culo. pCalificacion >= 0 && pCalificacion <= 5.
     */
    public void calificarArticulo(Articulo pArticulo, int pCalificacion) {
        out.println(CALIFICAR + SEPARADOR_COMANDO + pArticulo.darLoginUsuario() + SEPARADOR_PARAMETROS + pArticulo.darTitulo() + SEPARADOR_PARAMETROS
                + pCalificacion);
        calificacion = true;
    }

    /**
     * Solicita las estad�sticas de publicaci�n del usuario que inici� sesi�n.
     */
    public void solicitarEstadisticasUsuario() {
        out.println(ESTADISTICAS);
    }

    /**
     * Busca los art�culos que pertenezcan a la categor�a dada por par�metro.
     *
     * @param pCategoria Categor�a buscada. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     */
    public void buscarArticulosCategoria(String pCategoria) {
        out.println(BUSQUEDA_CATEGORIA + SEPARADOR_COMANDO + pCategoria);
    }

    /**
     * Le indica al servidor que el usuario va a cerrar sesi�n.
     */
    public void cerrarSesion() {
        out.println(LOGOUT);
        verificarInvariante();
    }

    /**
     * Cierra las conexiones con el servidor.
     *
     * @throws CupiBlogComunicacionException Si se produce un error al cerrar los canales de comunicaci�n.
     */
    public void cerrarConexiones() throws CupiBlogComunicacionException {
        try {
            // reset de los elementos de la interfaz
            clienteBlog.reiniciar();

            // Cierra la conexi�n
            out.close();
            in.close();
            socket.close();

        } catch (Exception e) {
            throw new CupiBlogComunicacionException("Error al cerrar los canales de comunicaci�n con el servidor: " + e.getMessage() + ".");
        }
    }

    /**
     * Notifica que se lanz� una excepci�n.
     *
     * @param pException La excepci�n lanzada. pException != null.
     */
    public void notificarExcepcion(Exception pException) {
        clienteBlog.notificarExcepcion(pException);
    }

    /**
     * Inicia un hilo de ejecuci�n para manejar la comunicaci�n entre el usuario y el servidor. <br>
     * Una vez iniciada la sesi�n, se encarga de procesar los mensajes de respuesta del servidor. <br>
     * El hilo de ejecuci�n termina cuando se recibe el mensaje de cierre de sesi�n por parte del servidor.
     */
    public void run() {
        while (true) {
            try {
                boolean logout = procesarMensaje();
                if (logout) {
                    return;
                }
            } catch (CupiBlogComunicacionException pComunicacionException) {
                notificarExcepcion(pComunicacionException);
                return;
            } catch (CupiBlogProtocoloException pProtocoloException) {
                notificarExcepcion(pProtocoloException);
            }
        }
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Verifica la invariante de la clase. <br>
     * <b>inv: </b> <br>
     * cliente != null. <br>
     * socket != null. <br>
     * in != null. <br>
     * out != null. <br>
     */
    private void verificarInvariante() {
        assert clienteBlog != null : "El v�nculo con la clase principal debe existir.";
        assert socket != null : "El canal debe estar inicializado.";
        assert in != null : "El buffer de lectura debe estar inicializado.";
        assert out != null : "El buffer de escritura debe estar inicializado.";
    }
}