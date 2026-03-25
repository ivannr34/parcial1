package pa.parcial1Sumo.modelo;

import java.util.Random;
import pa.parcial1Sumo.util.MovimientoListener;

/**
 * Representa el dohyō y coordina el combate entre dos luchadores.
 * El modelo no conoce una vista concreta; solo reporta movimientos
 * mediante un listener.
 */
public class Dohyo {

    private final Luchador l1;
    private final Luchador l2;
    private final MovimientoListener movimientoListener;
    private final Random random = new Random();

    private Luchador ganador;
    private boolean turnoL1 = true;

    /**
     * Construye el dohyō con sus luchadores y un listener para los movimientos.
     *
     * @param l1 primer luchador
     * @param l2 segundo luchador
     * @param movimientoListener receptor de mensajes del combate
     */
    public Dohyo(Luchador l1, Luchador l2, MovimientoListener movimientoListener) {
        this.l1 = l1;
        this.l2 = l2;
        this.movimientoListener = movimientoListener;
    }

    /**
     * Inicia el combate creando ambos hilos y esperando su finalización.
     */
    public void iniciarCombate() {
        turnoL1 = true;
        ganador = null;

        l1.setDentroDohyo(true);
        l2.setDentroDohyo(true);

        l1.setRival(l2);
        l2.setRival(l1);

        LuchadorThread t1 = new LuchadorThread(l1, this);
        LuchadorThread t2 = new LuchadorThread(l2, this);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Ejecuta un turno sincronizado para el luchador indicado.
     *
     * @param l luchador que está solicitando turno
     */
    public synchronized void turno(Luchador l) {
        try {
            if (ganador != null) {
                notifyAll();
                return;
            }

            while ((l == l1 && !turnoL1) || (l == l2 && turnoL1)) {
                wait();
                if (ganador != null) {
                    notifyAll();
                    return;
                }
            }

            if (ganador != null) {
                notifyAll();
                return;
            }

            String tecnica = l.usarTecnica();
            registrarMovimiento(l.getNombre() + " usa la técnica: " + tecnica);

            int prob = random.nextInt(100);

            if (prob < 15) {
                if (l.getRival() != null) {
                    l.getRival().setDentroDohyo(false);
                }
                l.sumarVictoria();
                ganador = l;

                registrarMovimiento("¡¡" + l.getNombre() + " saca a su rival del dohyō!!");
                registrarMovimiento("GANADOR: " + l.getNombre());

                notifyAll();
                return;
            }

            registrarMovimiento("El ataque no fue decisivo...");

            Thread.sleep(random.nextInt(501));

            turnoL1 = !turnoL1;
            notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Envía un mensaje al listener de movimientos.
     *
     * @param texto mensaje a registrar
     */
    private void registrarMovimiento(String texto) {
        if (movimientoListener != null) {
            movimientoListener.registrarMovimiento(texto);
        }
    }

    /**
     * Obtiene el luchador ganador del combate.
     *
     * @return ganador o null si el combate aún no termina
     */
    public Luchador getGanador() {
        return ganador;
    }
}