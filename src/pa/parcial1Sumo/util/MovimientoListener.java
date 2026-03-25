package pa.parcial1Sumo.util;

/**
 * Contrato para recibir mensajes del combate sin acoplar el modelo a la vista.
 */
@FunctionalInterface
public interface MovimientoListener {

    /**
     * Registra un mensaje del combate.
     *
     * @param texto mensaje a mostrar
     */
    void registrarMovimiento(String texto);
}