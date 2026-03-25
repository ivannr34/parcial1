
package pa.parcial1Sumo.dao;

import java.util.List;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Contrato de acceso a datos para luchadores.
 */
public interface ILuchadorDAO {

    /**
     * Guarda un luchador en la base de datos.
     *
     * @param luchador luchador a guardar
     * @return id generado
     */
    int guardar(Luchador luchador);

    /**
     * Obtiene todos los luchadores de la base de datos.
     *
     * @return lista de luchadores
     */
    List<Luchador> listarTodos();

    /**
     * Obtiene los luchadores disponibles para combatir.
     *
     * @return lista de luchadores disponibles
     */
    List<Luchador> listarDisponibles();

    /**
     * Actualiza las victorias de un luchador.
     *
     * @param idLuchador identificador del luchador
     * @param victorias cantidad de victorias
     */
    void actualizarVictorias(int idLuchador, int victorias);

    /**
     * Actualiza la disponibilidad de un luchador.
     *
     * @param idLuchador identificador del luchador
     * @param disponible estado de disponibilidad
     */
    void actualizarDisponibilidad(int idLuchador, boolean disponible);

    /**
     * Cuenta cuántos luchadores están disponibles.
     *
     * @return total de disponibles
     */
    int contarDisponibles();
}