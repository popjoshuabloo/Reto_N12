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

/**
 * Clase que representa un usuario del sistema.<br>
 * <b>inv:</b> <br>
 * login != null && login != "". <br>
 * nombre != null && nombre != "". <br>
 * apellido != null && apellido != "". <br>
 */
public class Usuario {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Login del usuario.
     */
    private String login;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa la informaci�n del usuario con la informaci�n dada por par�metro. <br>
     * <b> post: </b> El usuario se cre� con los valores de login, nombre y apellido dados por par�metro.
     *
     * @param pLogin    El login del usuario. pLogin != null && pLogin != "".
     * @param pNombre   El nombre del usuario. pNombre != null && pNombre != "".
     * @param pApellido El apellido del usuario. pApellido != null && pApellido != "".
     */
    public Usuario(String pLogin, String pNombre, String pApellido) {
        login = pLogin;
        nombre = pNombre;
        apellido = pApellido;

        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el login del usuario.
     *
     * @return Login del usuario.
     */
    public String darLogin() {
        return login;
    }

    /**
     * Retorna el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String darNombre() {
        return nombre;
    }

    /**
     * Retorna el apellido del usuario.
     *
     * @return Apellido del usuario.
     */
    public String darApellido() {
        return apellido;
    }

    /**
     * Retorna una cadena con el login del usuario, seguido por su apellido y nombre.
     *
     * @return La representaci�n del usuario en String: <login>: <apellido>, <nombre>.
     */
    public String toString() {
        return login + ": " + apellido + ", " + nombre;
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Revisa la invariante de la clase.<br>
     * <b>inv:</b> <br>
     * login != null && login != "". <br>
     * nombre != null && nombre != "". <br>
     * apellido != null && apellido != "". <br>
     */
    private void verificarInvariante() {
        assert login != null && !login.equals("") : "El login no puede tener un valor nulo o vac�o.";
        assert nombre != null && !nombre.equals("") : "El nombre no puede tener un valor nulo o vac�o.";
        assert apellido != null && !apellido.equals("") : "El apellido no puede tener un valor nulo o vac�o.";
    }
}