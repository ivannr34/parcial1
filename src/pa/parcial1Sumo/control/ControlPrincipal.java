package pa.parcial1Sumo.control;

import pa.parcial1Sumo.vista.VentanaCliente;
import pa.parcial1Sumo.vista.VentanaPrincipal;
import pa.parcial1Sumo.vista.VentanaServidor;

/**
 * Controlador encargado de gestionar la navegación entre las ventanas de la aplicación.
 */
public class ControlPrincipal {

    private VentanaPrincipal vistaPrincipal;
    private VentanaServidor vistaServidor;
    private VentanaCliente vistaCliente;

    /**
     * Inicia la aplicación sin necesidad de instanciar desde el Launcher.
     */
    public static void iniciarAplicacion() {
        ControlPrincipal control = new ControlPrincipal();
        control.iniciar();
    }

    /**
     * Configura y muestra la ventana principal.
     */
    public void iniciar() {
        vistaPrincipal = new VentanaPrincipal();

        vistaPrincipal.getBtnServidor().addActionListener(e -> abrirVentanaServidor());
        vistaPrincipal.getBtnCliente().addActionListener(e -> abrirVentanaCliente());
        vistaPrincipal.getBtnSalir().addActionListener(e -> salirAplicacion());

        vistaPrincipal.setVisible(true);
    }

    /**
     * Abre la ventana del servidor y oculta la principal.
     */
    private void abrirVentanaServidor() {
        vistaServidor = new VentanaServidor(vistaPrincipal);
        vistaServidor.setVisible(true);
        vistaPrincipal.setVisible(false);
    }

    /**
     * Abre la ventana del cliente y oculta la principal.
     */
    private void abrirVentanaCliente() {
        vistaCliente = new VentanaCliente(vistaPrincipal);
        vistaCliente.setVisible(true);
        vistaPrincipal.setVisible(false);
    }

    /**
     * Cierra la aplicación.
     */
    private void salirAplicacion() {
        if (vistaPrincipal != null) {
            vistaPrincipal.dispose();
        }
        System.exit(0);
    }
}