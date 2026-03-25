package pa.parcial1Sumo.control;

import pa.parcial1Sumo.socket.ServidorSocket;
import pa.parcial1Sumo.vista.VentanaServidor;

/**
 * Controlador encargado de iniciar el servidor en segundo plano.
 */
public class ControlServidor {

    private ServidorSocket servidor;
    private final VentanaServidor vista;

    /**
     * Crea el controlador del servidor.
     *
     * @param vista ventana del servidor
     */
    public ControlServidor(VentanaServidor vista) {
        this.vista = vista;
    }

    /**
     * Arranca el servidor socket en un hilo aparte.
     */
    public void iniciarServidor() {
        servidor = new ServidorSocket(vista);

        Thread hiloServidor = new Thread(servidor::iniciar);
        hiloServidor.start();
    }
}