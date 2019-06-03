/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_cupiLatinChat
 * Autor: Equipo Cupi2 2019
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package mundo;

/**
 * Clase que representa una excepci�n de protocolo en la comunicaci�n entre cliente y servidor.
 */
public class CupiBlogProtocoloException extends Exception {

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Inicializa una nueva excepci�n de protocolo en la comunicaci�n entre cliente y servidor.
     *
     * @param pMensaje Mensaje de la excepci�n. pMensaje != null && pMensaje != "".
     */
    public CupiBlogProtocoloException(String pMensaje) {
        super(pMensaje);
    }

}
