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

import java.util.ArrayList;

/**
 * Interfaz que le permite a la interfaz gr�fica actualizarse ante varios eventos en el mundo.
 */
public interface IObservadorEventos {

    /**
     * M�todo que notifica una actualizaci�n de la lista de los art�culos.
     *
     * @param pArticulos La lista de art�culos obtenida. pArticulos != null.
     */
    void actualizarListaArticulos(ArrayList<Articulo> pArticulos);

    /**
     * Notifica el inicio de sesi�n del usuario.
     *
     * @param pEstadoSesion El estado de sesi�n del usuario. pEstadoSesion != null.
     */
    void cambiarEstadoSesion(boolean pEstadoSesion);

    /**
     * M�todo que notifica la llegada de un mensaje del servidor.
     *
     * @param pMensaje El mensaje del cual se debe notificar. pMensaje != null.
     * @param pTitulo  Titulo del mensaje que se va a notificar. pTitulo != null && pTItulo != "".
     */
    void notificarMensaje(String pMensaje, String pTitulo);

    /**
     * M�todo que notifica una excepci�n.
     *
     * @param pExcepcion La excepci�n que llega en el mensaje. pExcepcion != null.
     */
    void notificarExcepcion(Exception pExcepcion);

    /**
     * M�todo que notifica una nueva calificaci�n.
     *
     * @param pArticulo El art�culo que ha sido calificado. pArticulo != null.
     */
    void notificarCalificacion(Articulo pArticulo);
}
