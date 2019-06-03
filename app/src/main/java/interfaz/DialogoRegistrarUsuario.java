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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Di�logo para registrar un nuevo usuario al blog.
 */
public class DialogoRegistrarUsuario extends JDialog implements ActionListener {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante que representa el comando para registrar el usuario.
     */
    private static final String REGISTRAR_USUARIO = "Registrar";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal de la aplicaci�n.
     */
    private InterfazClienteBlog principal;

    /**
     * Campo de texto para el login del usuario.
     */
    private JTextField txtLogin;

    /**
     * Campo de texto para el nombre del usuario.
     */
    private JTextField txtNombre;

    /**
     * Campo de texto para el apellido del usuario.
     */
    private JTextField txtApellido;

    /**
     * Bot�n ingresar al blog.
     */
    private JButton btnIngresarBlog;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un di�logo para registrar un usuario.<br>
     * <b> post: </b> Se crea el di�logo con todos sus elementos gr�ficos.
     *
     * @param pPrincipal Ventana principal de la aplicaci�n. pPrincipal != null.
     */
    public DialogoRegistrarUsuario(InterfazClienteBlog pPrincipal) {
        principal = pPrincipal;

        setTitle("Registrar usuario");
        setSize(300, 200);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(new TitledBorder("Campos"));

        JLabel labNombreUsuario = new JLabel("Login:");
        txtLogin = new JTextField();
        panelCampos.add(labNombreUsuario);
        panelCampos.add(txtLogin);

        JLabel labNombres = new JLabel("Nombre:");
        txtNombre = new JTextField();
        panelCampos.add(labNombres);
        panelCampos.add(txtNombre);

        JLabel labApellidos = new JLabel("Apellido:");
        txtApellido = new JTextField();
        panelCampos.add(labApellidos);
        panelCampos.add(txtApellido);

        add(panelCampos, BorderLayout.CENTER);

        btnIngresarBlog = new JButton(REGISTRAR_USUARIO);
        btnIngresarBlog.setActionCommand(REGISTRAR_USUARIO);
        btnIngresarBlog.addActionListener(this);
        add(btnIngresarBlog, BorderLayout.SOUTH);
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones.
     *
     * @param pEvento Acci�n que gener� el evento. pEvento != null.
     */
    public void actionPerformed(ActionEvent pEvento) {
        String comando = pEvento.getActionCommand();
        if (comando.equals(REGISTRAR_USUARIO)) {
            String login = txtLogin.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            if (login != null && !login.equals("") && nombre != null && !nombre.equals("") && apellido != null && !apellido.equals("")) {
                principal.registrarUsuario(login, nombre, apellido);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese todos los datos.", "Registrar usuario", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
