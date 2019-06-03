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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uniandes.cupi2.blog.cliente.mundo.Articulo;

/**
 * Panel para la visualizaci�n de la lista de todos los art�culos del blog.
 */
public class PanelArticulos extends JPanel implements ListSelectionListener, ActionListener {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante que representa el comando para actualizar la lista de art�culos.
     */
    private final static String ACTUALIZAR = "Actualizar lista";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicaci�n.
     */
    private InterfazClienteBlog principal;

    /**
     * Lista de art�culos publicados en el blog.
     */
    private JList listaArticulos;

    /**
     * Bot�n actualizar lista.
     */
    private JButton btnActualizarLista;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor del panel de art�culos. <br>
     * <b> post: </b> Se crea el panel con todos sus elementos gr�ficos.
     *
     * @param pPrincipal Ventana principal de la aplicaci�n. pPrincipal != null.
     */
    public PanelArticulos(InterfazClienteBlog pPrincipal) {
        principal = pPrincipal;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 200));

        TitledBorder borde = BorderFactory.createTitledBorder("Art�culos del blog");
        setBorder(borde);

        listaArticulos = new JList();
        listaArticulos.addListSelectionListener(this);

        JScrollPane scroll = new JScrollPane(listaArticulos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroll, BorderLayout.CENTER);

        btnActualizarLista = new JButton(ACTUALIZAR);
        btnActualizarLista.setActionCommand(ACTUALIZAR);
        btnActualizarLista.addActionListener(this);
        add(btnActualizarLista, BorderLayout.SOUTH);
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Limpia todos los elementos de la lista.
     */
    public void limpiarLista() {
        listaArticulos.setListData(new Object[0]);
    }

    /**
     * Actualiza la lista de art�culos publicados.
     *
     * @param pArticulos La nueva lista de art�culos. pArticulos != null.
     */
    public void actualizarListaArticulos(ArrayList<Articulo> pArticulos) {
        listaArticulos.setListData(pArticulos.toArray());
    }

    // -----------------------------------------------------------------
    // M�todos del manejo de eventos
    // -----------------------------------------------------------------

    /**
     * M�todo para atender el evento cuando un usuario selecciona un tax�n de la lista.
     *
     * @param pEvento El evento de selecci�n de un elemento de la lista de taxones. pEvento != null.
     */
    public void valueChanged(ListSelectionEvent pEvento) {
        Articulo articulo = (Articulo) listaArticulos.getSelectedValue();
        if (articulo != null) {
            principal.actualizarArticulo(articulo);
        }
    }

    /**
     * Manejo de los eventos de los botones.
     *
     * @param pEvento Acci�n que gener� el evento. pEvento != null.
     */
    public void actionPerformed(ActionEvent pEvento) {
        String comando = pEvento.getActionCommand();
        if (comando.equals(ACTUALIZAR)) {
            principal.listarTodosArticulos();
        }
    }

}
