package pa.parcial1Sumo.control;

import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación.
 * Se encarga de ejecutar la interfaz gráfica en el hilo de eventos de Swing (EDT).
 */
public class Launcher {

    /**
     * Método de entrada del programa.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ControlPrincipal::iniciarAplicacion);
    }
}