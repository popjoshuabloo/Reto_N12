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

package interfaz;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import uniandes.cupi2.blog.cliente.mundo.Articulo;
import uniandes.cupi2.blog.cliente.mundo.ClienteBlog;
import uniandes.cupi2.blog.cliente.mundo.CupiBlogComunicacionException;
import uniandes.cupi2.blog.cliente.mundo.CupiBlogProtocoloException;
import uniandes.cupi2.blog.cliente.mundo.IObservadorEventos;

/**
 * Ventana principal de la aplicaci�n cliente del blog.
 */
public class InterfazClienteBlog extends JFrame implements IObservadorEventos {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo.
     */
    private ClienteBlog blog;

    /**
     * Login del usuario actual.
     */
    private String loginActual;

    /**
     * Panel con la lista de art�culos.
     */
    private PanelArticulos panelArticulos;

    /**
     * Panel con la informaci�n del art�culo a mostrar.
     */
    private PanelArticulo panelArticulo;

    /**
     * Panel con los comandos del blog.
     */
    private PanelComandos panelComandos;

    /**
     * Panel con la imagen del encabezado.
     */
    private PanelImagen panelImagen;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea la interfaz del cliente del blog. <br>
     * <b>post: </b> La interfaz es creada.
     */
    public InterfazClienteBlog() {
        // Crea la clase principal
        String ipServidor = (String) JOptionPane.showInputDialog(null, "La direcci�n IP del servidor", "");
        if (ipServidor == null) {
            dispose();
        } else {
            if (ipServidor.length() == 0) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la direcci�n IP del Servidor al cual se desea conectar.", "Direcci�n ip del servidor", JOptionPane.INFORMATION_MESSAGE);
            }

            blog = new ClienteBlog(this, ipServidor);

            // Construye la forma
            setTitle("CupiBlog");
            setLayout(new BorderLayout());
            setSize(875, 700);
            setResizable(true);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Crea los paneles
            panelImagen = new PanelImagen();
            add(panelImagen, BorderLayout.NORTH);

            panelArticulos = new PanelArticulos(this);
            add(panelArticulos, BorderLayout.WEST);

            panelArticulo = new PanelArticulo(this);
            add(panelArticulo, BorderLayout.CENTER);

            panelComandos = new PanelComandos(this);
            add(panelComandos, BorderLayout.SOUTH);

            // Centrar la ventana
            setLocationRelativeTo(null);

            // Inicia la sesi�n del usuario.
            String loginSesion = (String) JOptionPane.showInputDialog(null, "Escriba su login para ingresar");
            if (loginSesion != null && !loginSesion.equals("")) {
                iniciarSesion(loginSesion);
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar un login para iniciar sesi�n.", "Iniciar sesi�n", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz. <br>
     * No se visualiza la interfaz.
     *
     * @param pArgs Los argumentos de ejecuci�n de la aplicaci�n. pArgs != null.
     */
    public static void main(String[] pArgs) {
        // Unifica la interfaz para Mac y para Windows.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new InterfazClienteBlog();
    }

    /**
     * Retorna el login del usuario actual.
     *
     * @return Login del usuario actual.
     */
    public String darLoginActual() {
        return loginActual;
    }

    /**
     * Solicita al servidor el inicio de sesi�n del usuario con login dado. <br>
     * Si se produce un error porque el login no se encuentra en el sistema, entonces se registra el usuario. <br>
     * Si se produce otro tipo de error, se cierra la interfaz.
     * <b> pre: </b> El blog est� inicializado.
     * <b> post: </b> El login actual es el login del usuario que inici� sesi�n.
     *
     * @param pLogin El nombre del usuario a ingresar. pLogin != null && pLogin != "".
     */
    public void iniciarSesion(String pLogin) {
        try {
            blog.iniciarSesion(pLogin);
            loginActual = pLogin;
            cambiarEstadoSesion(true);
        } catch (CupiBlogComunicacionException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Iniciar sesi�n", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } catch (CupiBlogProtocoloException e) {
            int resp = JOptionPane.showConfirmDialog(null, "Su login no se encuentra en el sistema. �Desea registrarse?", "Iniciar sesi�n", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                DialogoRegistrarUsuario dialogoRegistrar = new DialogoRegistrarUsuario(this);
                dialogoRegistrar.setVisible(true);
            } else if (resp == JOptionPane.NO_OPTION) {
                this.dispose();
            }
        }
    }

    /**
     * Actualiza la informaci�n a mostrar del art�culo.
     * <b> pre: </b> El blog est� inicializado.
     *
     * @param pArticulo El art�culo a mostrar en el panel. pArticulo != null.
     */
    public void actualizarArticulo(Articulo pArticulo) {
        try {
            blog.modificarArticuloActual(pArticulo);
            panelArticulo.actualizarArticulo(pArticulo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Actualizar art�culo", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca los art�culos que pertenezcan a la categor�a dada.
     * <b> pre: </b> El blog est� inicializado.
     *
     * @param pCategoria La categor�a a buscar. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     */
    public void buscarArticuloPorCategoria(String pCategoria) {
        try {
            blog.buscarArticulosCategoria(pCategoria);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Buscar art�culo", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Califica el art�culo actual con la calificaci�n dada por par�metro.
     * <b> pre: </b> El blog est� inicializado.
     *
     * @param pCalificacion La calificaci�n que se le da al art�culo. pCalificacion >= 0.
     */
    public void calificarArticulo(int pCalificacion) {
        try {
            blog.calificarArticulo(pCalificacion);
            JOptionPane.showMessageDialog(this, "Se ha calificado correctamente el art�culo.", "Calificar art�culo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Calificar art�culo", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita al servidor la lista de todos los art�culos.
     * <b> pre: </b> El blog est� inicializado.
     */
    public void listarTodosArticulos() {
        try {
            blog.listarArticulos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Listar art�culos", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita al servidor las estad�sticas del usuario.
     * <b> pre: </b> El blog est� inicializado.
     */
    public void solicitarEstadisticas() {
        try {
            blog.solicitarEstadisticas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Mostrar estad�sticas", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Publica un art�culo con los datos dados en el blog.
     * <b> pre: </b> El blog est� inicializado.
     *
     * @param pTitulo    El t�tulo del art�culo. pTitulo != null && pTitulo != "".
     * @param pCategoria La categor�a del art�culo. pCategoria != null && pCategoria pertenece a Articulo.CATEGORIAS.
     * @param pContenido El contenido del art�culo. pContenido != null && pContenido != "".
     */
    public void publicarArticulo(String pTitulo, String pCategoria, String pContenido) {
        try {
            blog.publicarArticulo(pTitulo, pCategoria, pContenido);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Publicar art�culo", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Env�a al servidor la solicitud de registrar un nuevo usuario con los datos dados.
     * <b> pre: </b> El blog est� inicializado.
     *
     * @param pLogin    El nombre del usuario a ingresar. pLogin != null && pLogin != "".
     * @param pNombre   El nombre de pila del usuario. pNombre != null && pNombre != "".
     * @param pApellido Los apellidos del usuario. pApellido != null && pApellido != "".
     */
    public void registrarUsuario(String pLogin, String pNombre, String pApellido) {
        try {
            blog.registrarUsuario(pLogin, pNombre, pApellido);
            loginActual = pLogin;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Registrar usuario", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cierra la sesi�n del usuario.
     * <b> pre: </b> El blog est� inicializado.
     */
    public void cerrarSesion() {
        try {
            if (loginActual != null) {
                blog.cerrarSesion();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Cerrar sesi�n", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -----------------------------------------------------------------
    // M�todos de observador
    // -----------------------------------------------------------------

    /**
     * El m�todo que se llama al cerrar la aplicaci�n. <br>
     * Se cierra la sesi�n del usuario.
     */
    public void dispose() {
        int resp = JOptionPane.showConfirmDialog(null, "�Est� seguro que quiere salir del programa?", "Cerrar sesi�n", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            cerrarSesion();
            super.dispose();
        }
    }

    /**
     * Actualiza los art�culos en el panel de art�culos seg�n la lista dada por par�metro.
     *
     * @param pArticulos La lista de art�culos. pArticulos != null.
     */
    public void actualizarListaArticulos(ArrayList<Articulo> pArticulos) {
        panelArticulos.actualizarListaArticulos(pArticulos);
    }

    /**
     * Notifica al usuario la notificaci�n de un mensaje.
     *
     * @param pMensaje El mensaje que se va a notificar. pMensaje != null && pMensaje != "".
     * @param pTitulo  Titulo del mensaje que se va a notificar. pTitulo != null && pTItulo != "".
     */
    public void notificarMensaje(String pMensaje, String pTitulo) {
        JOptionPane.showMessageDialog(this, pMensaje, pTitulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra la informaci�n de la excepci�n.
     *
     * @param pExcepcion La excepci�n de la aplicaci�n. pExcepcion != null.
     */
    public void notificarExcepcion(Exception pExcepcion) {
        JOptionPane.showMessageDialog(this, pExcepcion.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Notifica el inicio de sesi�n del usuario. <br>
     * Cuando el usuario inicia sesi�n, se visualiza la ventana principal. <br>
     * Cuando el usuario cierra sesi�n, se limpia la lista de art�culos y se desactivan las opciones del panel del art�culo.
     * <b> pre: </b> PanelComandos, PanelArticulo y PanerlArticulos est�n inicializados.
     *
     * @param pEstadoSesion El estado de sesi�n del usuario. pEstadoSesion != null.
     */
    public void cambiarEstadoSesion(boolean pEstadoSesion) {
        setVisible(true);
        panelComandos.sesionAbierta(pEstadoSesion);
        if (!pEstadoSesion) {
            panelArticulo.desactivar();
            panelArticulos.limpiarLista();
        }
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * Notifica la calificaci�n de un art�culo.
     * <b> pre: </b> El panel art�culo est� inicializado.
     *
     * @param pArticulo El art�culo que se va a calificar. pArticulo != null.
     */
    public void notificarCalificacion(Articulo pArticulo) {
        panelArticulo.actualizarArticulo(pArticulo);
    }

    /**
     * M�todo para la extensi�n 1.
     * <b> pre: </b> El blog est� inicializado.
     */
    public void reqFuncOpcion1() {
        String resultado = blog.metodo1();
        JOptionPane.showMessageDialog(this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 2.
     * <b> pre: </b> El blog est� inicializado.
     */
    public void reqFuncOpcion2() {
        String resultado = blog.metodo2();
        JOptionPane.showMessageDialog(this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }

}