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
 * Clase que representa una excepci�n de comunicaci�n entre el cliente y el servidor.
 */
public class CupiBlogComunicacionException extends Exception {

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa una nueva excepci�n de tipo comunicaci�n entre el cliente y el servidor.
     *
     * @param pMensaje Mensaje de la excepci�n. pMensaje != null && pMensaje != "".
     */
    public CupiBlogComunicacionException(String pMensaje) {
        super(pMensaje);
    }

}
