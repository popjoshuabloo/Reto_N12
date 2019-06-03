/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_cupiBlog
 * Autor: Equipo Cupi2 2019.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.blog.cliente.mundo.Articulo;

/**
 * Panel para la visualizaci�n de un art�culo del blog.
 */
public class PanelArticulo extends JPanel {

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicaci�n.
     */
    private InterfazClienteBlog principal;

    /**
     * Etiqueta con el t�tulo del art�culo.
     */
    private JLabel lblTitulo;

    /**
     * Etiqueta con el login del usuario del autor del art�culo.
     */
    private JLabel lblLogin;

    /**
     * Etiqueta con la fecha de publicaci�n del art�culo.
     */
    private JLabel lblFechaPublicacion;

    /**
     * Etiqueta con la categor�a del art�culo.
     */
    private JLabel lblCategoria;

    /**
     * Campo de texto con el contenido del art�culo.
     */
    private JTextArea txtContenido;

    /**
     * Panel para calificar el art�culo.
     */
    private PanelCalificacion panelCalificacion;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel para visualizar el art�culo con la ventana principal dada por par�metro.<br>
     * <b>post: </b> Se construy� el panel con la ventana principal dada por par�metro.
     *
     * @param pPrincipal Ventana principal de la aplicaci�n. pPrincipal != null.
     */
    public PanelArticulo(InterfazClienteBlog pPrincipal) {
        principal = pPrincipal;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(360, 515));
        setBorder(new TitledBorder("Art�culo"));

        // Panel auxiliar que contiene la informaci�n del art�culo.
        JPanel panelTituloArt = new JPanel();
        panelTituloArt.setPreferredSize(new Dimension(350, 100));
        panelTituloArt.setLayout(new BorderLayout());

        lblTitulo = new JLabel("T�tulo del art�culo");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 20));

        panelTituloArt.add(lblTitulo, BorderLayout.NORTH);

        // Panel auxiliar que contiene m�s informaci�n del art�culo.
        JPanel panelAuxArt = new JPanel();
        panelAuxArt.setLayout(new GridLayout(2, 2));
        panelAuxArt.setPreferredSize(new Dimension(350, 50));

        lblLogin = new JLabel("Por: ");
        panelAuxArt.add(lblLogin);

        lblFechaPublicacion = new JLabel("Fecha publicaci�n: ", SwingConstants.RIGHT);
        lblFechaPublicacion.setHorizontalAlignment(JLabel.RIGHT);
        panelAuxArt.add(lblFechaPublicacion);

        panelAuxArt.add(new JLabel());

        lblCategoria = new JLabel("Categor�a: ", SwingConstants.RIGHT);
        lblCategoria.setHorizontalAlignment(JLabel.RIGHT);
        panelAuxArt.add(lblCategoria);

        panelTituloArt.add(panelAuxArt, BorderLayout.CENTER);

        add(panelTituloArt, BorderLayout.NORTH);

        txtContenido = new JTextArea("Contenido del art�culo.");
        txtContenido.setWrapStyleWord(true);
        txtContenido.setLineWrap(true);
        txtContenido.setEditable(false);
        txtContenido.setBackground(Color.WHITE);
        txtContenido.setPreferredSize(new Dimension(100, 265));
        JScrollPane scroll = new JScrollPane(txtContenido, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll, BorderLayout.CENTER);

        panelCalificacion = new PanelCalificacion(principal);

        add(panelCalificacion, BorderLayout.SOUTH);

    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Se actualiza la informaci�n seg�n el art�culo que llega por par�metro.
     * <b> post: </b> Se actualizaron los campos con la informaci�n del art�culo dado por par�metro.
     *
     * @param pArticulo El art�culo con la informaci�n a mostrar. pArticulo != null.
     */
    public void actualizarArticulo(Articulo pArticulo) {
        // Actualiza la informaci�n principal del art�culo
        lblTitulo.setText(pArticulo.darTitulo());
        lblLogin.setText("Por: " + pArticulo.darLoginUsuario());
        lblCategoria.setText("Categor�a: " + pArticulo.darCategoria());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        String fecha = dateFormat.format(pArticulo.darFechaPublicacion());
        lblFechaPublicacion.setText("Fecha de publicaci�n: " + fecha);

        txtContenido.setText(pArticulo.darContenido());

        panelCalificacion.actualizarInformacion(pArticulo);

        if (pArticulo.darLoginUsuario().equals(principal.darLoginActual())) {
            panelCalificacion.desactivarBotones();
        }
    }

    /**
     * Desactiva las opciones del panel.
     * <b> post: </b> Desactiva los botones de calificaci�n y pone los textos a su valor por defecto.
     */
    public void desactivar() {
        lblTitulo.setText("T�tulo del art�culo");
        lblLogin.setText("Por: ");
        lblCategoria.setText("Categor�a: ");
        lblFechaPublicacion.setText("Fecha publicaci�n: ");
        txtContenido.setText("Contenido del art�culo: ");

        panelCalificacion.reiniciar();
    }


}
