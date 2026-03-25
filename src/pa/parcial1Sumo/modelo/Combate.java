package pa.parcial1Sumo.modelo;

import java.time.LocalDateTime;

/**
 * Representa un combate entre dos luchadores, incluyendo ganador y fecha.
 */
public class Combate {

    private final Luchador luchador1;
    private final Luchador luchador2;
    private final Luchador ganador;
    private final LocalDateTime fechaHora;

    /**
     * Crea un combate con sus participantes y resultado.
     */
    public Combate(Luchador luchador1, Luchador luchador2, Luchador ganador, LocalDateTime fechaHora) {
        this.luchador1 = luchador1;
        this.luchador2 = luchador2;
        this.ganador = ganador;
        this.fechaHora = fechaHora;
    }

    /** @return primer luchador */
    public Luchador getLuchador1() {
        return luchador1;
    }

    /** @return segundo luchador */
    public Luchador getLuchador2() {
        return luchador2;
    }

    /** @return luchador ganador */
    public Luchador getGanador() {
        return ganador;
    }

    /**
     * Obtiene el luchador perdedor.
     * @return perdedor o null si no hay ganador
     */
    public Luchador getPerdedor() {
        if (ganador == null) {
            return null;
        }
        if (ganador.getId() != null && ganador.getId().equals(luchador1.getId())) {
            return luchador2;
        }
        return luchador1;
    }

    /** @return fecha y hora del combate */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}