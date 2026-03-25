package pa.parcial1Sumo.modelo;

/**
 * Hilo de ejecución asociado a un luchador.
 * Solicita turnos al dohyō mientras ambos participantes sigan dentro.
 */
public class LuchadorThread extends Thread {

    private final Luchador luchador;
    private final Dohyo dohyo;

    /**
     * Crea el hilo del luchador.
     *
     * @param luchador luchador asociado
     * @param dohyo dohyō que coordina el combate
     */
    public LuchadorThread(Luchador luchador, Dohyo dohyo) {
        this.luchador = luchador;
        this.dohyo = dohyo;
    }

    @Override
    public void run() {
        while (luchador.estaDentroDohyo()
                && luchador.getRival() != null
                && luchador.getRival().estaDentroDohyo()) {

            dohyo.turno(luchador);

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}