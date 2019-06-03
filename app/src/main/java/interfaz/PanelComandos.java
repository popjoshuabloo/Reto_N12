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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import uniandes.cupi2.blog.cliente.mundo.Articulo;

/**
 * Panel para el manejo de comandos.
 */
public class PanelComandos extends JPanel implements ActionListener {

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante que representa el comando para publicar un art�culo.
     */
    private static final String PUBLICAR_ARTICULO = "PUBLICAR_ARTICULO";

    /**
     * Constante que representa el comando para buscar un art�culo por el nombre de la categor�a.
     */
    private static final String BUSCAR_ARTICULO_CATEGORIA = "BUSCAR_ARTICULO_CATEGORIA";

    /**
     * Constante que representa el comando para buscar un art�culo por el nombre de la categor�a.
     */
    private static final String LISTAR_TODOS_ARTICULOS = "LISTAR_TODOS_ARTICULOS";

    /**
     * Constante que representa el comando para mostrar las estad�sticas del usuario.
     */
    private static final String ESTADISTICAS = "ESTADISTICAS";

    /**
     * Constante que representa el comando para la opci�n 1.
     */
    private static final String OPCION_1 = "OPCION_1";

    /**
     * Constante que representa el comando para la opci�n 2.
     */
    private static final String OPCION_2 = "OPCION_2";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicaci�n.
     */
    private InterfazClienteBlog principal;

    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------

    /**
     * Bot�n para publicar un art�culo.
     */
    private JButton btnPublicarArticulo;

    /**
     * Bot�n para buscar un art�culo por la categor�a.
     */
    private JButton btnBuscarCategoria;

    /**
     * Bot�n para mostrar las estad�sticas.
     */
    private JButton btnEstadisticas;

    /**
     * Bot�n Opci�n 1.
     */
    private JButton btnOpcion1;

    /**
     * Bot�n Opci�n 2.
     */
    private JButton btnOpcion2;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel de comandos. <br>
     * <b> post: </b> Se crea el panel con todos sus elementos gr�ficos.
     *
     * @param pPrincipal La interfaz del blog. pPrincipal != null.
     */
    public PanelComandos(InterfazClienteBlog pPrincipal) {
        principal = pPrincipal;

        setBorder(null);
        setLayout(new GridLayout(0, 5));

        // Bot�n para publicar un art�culo
        btnPublicarArticulo = new JButton("Publicar art�culo");
        btnPublicarArticulo.setActionCommand(PUBLICAR_ARTICULO);
        btnPublicarArticulo.addActionListener(this);
        add(btnPublicarArticulo);

        // Bot�n para buscar art�culos por categor�a
        btnBuscarCategoria = new JButton("Buscar");
        btnBuscarCategoria.setActionCommand(BUSCAR_ARTICULO_CATEGORIA);
        btnBuscarCategoria.addActionListener(this);
        add(btnBuscarCategoria);

        // Bot�n para mostrar las estad�sticas del usuario
        btnEstadisticas = new JButton("Estadisticas");
        btnEstadisticas.setActionCommand(ESTADISTICAS);
        btnEstadisticas.addActionListener(this);
        add(btnEstadisticas);

        // Bot�n opci�n 1
        btnOpcion1 = new JButton("Opci�n 1");
        btnOpcion1.setActionCommand(OPCION_1);
        btnOpcion1.addActionListener(this);
        add(btnOpcion1);

        // Bot�n opci�n 2
        btnOpcion2 = new JButton("Opci�n 2");
        btnOpcion2.setActionCommand(OPCION_2);
        btnOpcion2.addActionListener(this);
        add(btnOpcion2);
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Activa y desactiva los botones seg�n el estado de la sesi�n.
     *
     * @param pAbierta El estado de la sesi�n. pAbierta != null.
     */
    public void sesionAbierta(boolean pAbierta) {
        btnPublicarArticulo.setEnabled(pAbierta);
        btnBuscarCategoria.setEnabled(pAbierta);
        btnEstadisticas.setEnabled(pAbierta);
    }

    /**
     * Manejo de los eventos de los botones.
     *
     * @param pEvento Acci�n que gener� el evento. pEvento != null.
     */
    public void actionPerformed(ActionEvent pEvento) {
        if (PUBLICAR_ARTICULO.equals(pEvento.getActionCommand())) {
            DialogoPublicarArticulo dialogo = new DialogoPublicarArticulo(principal);
            dialogo.setVisible(true);
        } else if (BUSCAR_ARTICULO_CATEGORIA.equals(pEvento.getActionCommand())) {
            JComboBox categorias = new JComboBox(Articulo.CATEGORIAS);
            JOptionPane.showMessageDialog(null, categorias, "Seleccione una categor�a", JOptionPane.QUESTION_MESSAGE);
            String categoria = (String) categorias.getSelectedItem();
            if (categoria != null) {
                principal.buscarArticuloPorCategoria(categoria);
            }
        } else if (ESTADISTICAS.equals(pEvento.getActionCommand())) {
            principal.solicitarEstadisticas();
        } else if (OPCION_1.equals(pEvento.getActionCommand())) {
            principal.reqFuncOpcion1();
        } else if (OPCION_2.equals(pEvento.getActionCommand())) {
            principal.reqFuncOpcion2();
        }

    }

}
