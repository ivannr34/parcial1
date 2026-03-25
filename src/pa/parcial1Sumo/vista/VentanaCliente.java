package pa.parcial1Sumo.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import pa.parcial1Sumo.control.ControlCliente;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Ventana que permite registrar y enviar un luchador al servidor.
 */
public class VentanaCliente extends JFrame {

    private VentanaLuchador panelLuchador;
    private JButton botonEnviar;
    private JButton botonVolver;
    private final VentanaPrincipal ventanaPrincipal;
    private final ControlCliente controlCliente;

    /**
     * Crea la ventana del cliente.
     */
    public VentanaCliente(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.controlCliente = new ControlCliente();

        configurarVentana();
        inicializarComponentes();
        construirInterfaz();
        agregarEventos();
    }

    /** Configura propiedades de la ventana. */
    private void configurarVentana() {
        setTitle("Registro de Luchador");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /** Inicializa componentes y carga datos. */
    private void inicializarComponentes() {
        List<String> tecnicasDisponibles;

        try {
            tecnicasDisponibles = controlCliente.obtenerTecnicasDisponibles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando técnicas: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            tecnicasDisponibles = java.util.Collections.emptyList();
        }

        panelLuchador = new VentanaLuchador("Luchador", tecnicasDisponibles);

        botonEnviar = new JButton("Enviar Luchador");
        botonVolver = new JButton("Volver");

        botonEnviar.setFont(new Font("Arial", Font.BOLD, 15));
        botonVolver.setFont(new Font("Arial", Font.BOLD, 15));
    }

    /** Construye la interfaz gráfica. */
    private void construirInterfaz() {
        JLabel titulo = new JLabel("REGISTRO DEL LUCHADOR", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCentral.add(panelLuchador, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonEnviar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /** Asigna eventos a los botones. */
    private void agregarEventos() {
        botonEnviar.addActionListener(e -> enviarLuchador());
        botonVolver.addActionListener(e -> volverAVentanaPrincipal());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ventanaPrincipal.setVisible(true);
            }
        });
    }

    /** Valida y envía el luchador al servidor. */
    private void enviarLuchador() {
        try {
            String nombre = panelLuchador.getNombreLuchador();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            double peso;
            try {
                peso = Double.parseDouble(panelLuchador.getPesoLuchador());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Peso inválido.");
                return;
            }

            if (panelLuchador.getTunicaSeleccionada().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona una túnica.");
                return;
            }

            if (panelLuchador.getTecnicasSeleccionadas().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona al menos una técnica.");
                return;
            }

            Luchador luchador = controlCliente.crearLuchador(
                    nombre,
                    peso,
                    panelLuchador.getTunicaSeleccionada(),
                    panelLuchador.getTecnicasSeleccionadas()
            );

            botonEnviar.setEnabled(false);
            registrarEstado("Enviado, esperando resultado...");

            new Thread(() -> {
                String resultado = controlCliente.enviarLuchador(luchador);

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Resultado: " + resultado);
                    registrarEstado("Resultado: " + resultado);
                    botonEnviar.setEnabled(true);
                    panelLuchador.limpiarCampos();
                });
            }).start();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            botonEnviar.setEnabled(true);
        }
    }

    /** Actualiza el estado en el título. */
    private void registrarEstado(String mensaje) {
        setTitle("Registro - " + mensaje);
    }

    /** Regresa a la ventana principal. */
    private void volverAVentanaPrincipal() {
        dispose();
        ventanaPrincipal.setVisible(true);
    }
}