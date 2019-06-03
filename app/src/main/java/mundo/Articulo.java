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

import java.util.Date;

/**
 * La clase que representa un art�culo del blog.<br>
 * <b>inv:</b><br>
 * usuario != null && !usuario.equals(""). <br>
 * titulo != null && !titulo.equals(""). <br>
 * categoria != null && categoria pertenece a alguna constante del arreglo CATEGORIAS. <br>
 * contenido != null && !contenido.equals(""). <br>
 * fechaPublicacion != null. <br>
 */
public class Articulo {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Arreglo de constantes que representan las posibles categor�as del art�culo.
     */
    public final static String[] CATEGORIAS = {"M�sica", "Televisi�n", "Cine", "Mascotas", "Comics", "Viajes", "Vida diaria", "Otros"};

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Login del usuario que public� del art�culo.
     */
    private String loginUsuario;

    /**
     * T�tulo del art�culo.
     */
    private String titulo;

    /**
     * Categor�a del art�culo.
     */
    private String categoria;

    /**
     * Texto del contenido del art�culo.
     */
    private String contenido;

    /**
     * Fecha de publicaci�n del art�culo.
     */
    private Date fechaPublicacion;

    /**
     * Calificaci�n acumulada del art�culo.
     */
    private int calificacionAcumulada;

    /**
     * Cantidad de veces que el art�culo ha sido calificado.
     */
    private int vecesCalificado;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Inicializa el art�culo del blog con la informaci�n dada por par�metro. <br>
     * <b> post: </b> El art�culo del blog se construy� con los valores de usuario, t�tulo, categor�a, contenido y fecha de publicaci�n dados por par�metro. <br>
     *
     * @param pLoginUsuario          Login del usuario autor del art�culo. pUsuario != null && pUsuario != "".
     * @param pTitulo                T�tulo del art�culo. pTitulo != null && pTitulo != "".
     * @param pCategoria             Categor�a del art�culo. pCategoria != null && pCategoria pertenece a CATEGORIAS.
     * @param pContenido             Texto con el contenido del art�culo. pContenido != null && pContenido != "".
     * @param pFechaPublicacion      Fecha de publicaci�n del art�culo. pFechaPublicacion != null.
     * @param pCalificacionAcumulada Calificaci�n acumulada del art�culo. pCalificacionAcumulada >= 0.
     * @param pVecesCalificado       Veces que se ha calificado el art�culo. pVecesCalificado >= 0.
     */
    public Articulo(String pLoginUsuario, String pTitulo, String pCategoria, String pContenido, Date pFechaPublicacion, int pCalificacionAcumulada, int pVecesCalificado) {
        loginUsuario = pLoginUsuario;
        titulo = pTitulo;
        categoria = pCategoria;
        contenido = pContenido;
        fechaPublicacion = pFechaPublicacion;
        calificacionAcumulada = pCalificacionAcumulada;
        vecesCalificado = pVecesCalificado;

        verificarInvariante();
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el login del usuario autor del art�culo.
     *
     * @return Login del usuario autor del art�culo.
     */
    public String darLoginUsuario() {
        return loginUsuario;
    }

    /**
     * Retorna el t�tulo del art�culo.
     *
     * @return T�tulo del art�culo.
     */
    public String darTitulo() {
        return titulo;
    }

    /**
     * Retorna la calificaci�n acumulada.
     *
     * @return Calificaci�n acumulada del art�culo.
     */
    public int darCalificacionAcumulada() {
        return calificacionAcumulada;
    }

    /**
     * Retorna la categor�a del art�culo.
     *
     * @return Categor�a del art�culo.
     */
    public String darCategoria() {
        return categoria;
    }

    /**
     * Retorna el texto del contenido del art�culo.
     *
     * @return Contenido del art�culo.
     */
    public String darContenido() {
        return contenido;
    }

    /**
     * Retorna la fecha de publicaci�n del art�culo.
     *
     * @return Fecha de publicaci�n del art�culo.
     */
    public Date darFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Retorna las veces que ha sido calificado el art�culo.
     *
     * @return Retorna las veces que el art�culo ha sido calificado.
     */
    public int darVecesCalificado() {
        return vecesCalificado;
    }

    /**
     * Retorna el promedio de calificaciones del art�culo.
     *
     * @return Promedio de calificaciones.
     */
    public double darPromedioCalificaciones() {
        double promedio = 0;
        if (vecesCalificado != 0) {
            promedio = (double) calificacionAcumulada / vecesCalificado;
        }
        return promedio;
    }

    /**
     * Retorna una cadena con el t�tulo del art�culo y el usuario autor.
     *
     * @return La representaci�n del art�culo en String: <titulo> ( Por: <usuario> ).
     */
    public String toString() {
        return titulo + " ( Por: " + loginUsuario + " )";
    }

    // -----------------------------------------------------------------
    // Invariante
    // -----------------------------------------------------------------

    /**
     * Revisa la invariante de la clase.<br>
     * <b>inv:</b><br>
     * usuario != null && !usuario.equals(""). <br>
     * titulo != null && !titulo.equals(""). <br>
     * categoria != null && categoria pertenece a alguna constante del arreglo CATEGORIAS. <br>
     * contenido != null && !contenido.equals(""). <br>
     * fechaPublicacion != null. <br>
     */
    private void verificarInvariante() {
        assert loginUsuario != null && !loginUsuario.equals("") : "El usuario autor del art�culo no puede tener un valor nulo o vac�o.";
        assert titulo != null && !titulo.equals("") : "El t�tulo del art�culo no puede tener un valor nulo o vac�o.";
        assert categoria != null && !categoria.equals("") && categoriaPerteneceAArreglo() : "La categor�a del art�culo no tiene un valor v�lido.";
        assert contenido != null && !contenido.equals("") : "El contenido del art�culo no puede tener un valor nulo o vac�o.";
        assert fechaPublicacion != null : "La fecha de publicaci�n del art�culo no puede tener un valor nulo.";
    }

    /**
     * Indica si la categor�a pertenece al arreglo CATEGORIAS.
     *
     * @return true si la categor�a pertenece al arreglo CATEGORIAS. False de lo contrario.
     */
    private boolean categoriaPerteneceAArreglo() {
        boolean pertenece = false;

        for (int i = 0; i < CATEGORIAS.length; i++) {
            if (categoria.equals(CATEGORIAS[i])) {
                pertenece = true;
            }
        }

        return pertenece;
    }

}