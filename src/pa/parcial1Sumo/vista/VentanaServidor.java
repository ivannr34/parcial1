package pa.parcial1Sumo.vista;

import javax.swing.*;
import java.awt.*;
import pa.parcial1Sumo.control.ControlServidor;
import pa.parcial1Sumo.util.MovimientoListener;

/**
 * Ventana del servidor que muestra eventos y permite iniciar el servicio.
 */
public class VentanaServidor extends JFrame implements MovimientoListener {

    private JTextArea areaCombate;
    private JButton btnIniciarServidor;
    private JButton btnVolver;
    private final VentanaPrincipal ventanaPrincipal;
    private final ControlServidor controlServidor;

    /** Crea la ventana del servidor. */
    public VentanaServidor(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.controlServidor = new ControlServidor(this);

        configurarVentana();
        inicializarComponentes();
        construirInterfaz();
        agregarEventos();
    }

    /** Configura la ventana. */
    private void configurarVentana() {
        setTitle("Servidor Combate");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /** Inicializa los componentes. */
    private void inicializarComponentes() {
        areaCombate = new JTextArea();
        areaCombate.setEditable(false);
        areaCombate.setFont(new Font("Monospaced", Font.PLAIN, 13));

        btnIniciarServidor = new JButton("Iniciar Servidor");
        btnVolver = new JButton("Volver");

        btnIniciarServidor.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolver.setFont(new Font("Arial", Font.BOLD, 16));
    }

    /** Construye la interfaz gráfica. */
    private void construirInterfaz() {
        JLabel titulo = new JLabel("DOHYO - COMBATE DE SUMO", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout());

        JLabel lblImagen = new JLabel("", JLabel.CENTER);
        java.net.URL rutaImagen = getClass().getResource("/pa/parcial1Sumo/Recursos/ring.png");

        if (rutaImagen != null) {
            lblImagen.setIcon(new ImageIcon(rutaImagen));
        } else {
            lblImagen.setText("Imagen no disponible");
        }

        panelCentro.add(lblImagen, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnIniciarServidor);
        panelBoton.add(btnVolver);

        panelCentro.add(panelBoton, BorderLayout.SOUTH);
        add(panelCentro, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(areaCombate);
        scroll.setPreferredSize(new Dimension(650, 200));
        add(scroll, BorderLayout.SOUTH);
    }

    /** Asigna eventos a los botones. */
    private void agregarEventos() {
        btnIniciarServidor.addActionListener(e -> iniciarServidor());

        btnVolver.addActionListener(e -> {
            dispose();
            ventanaPrincipal.setVisible(true);
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ventanaPrincipal.setVisible(true);
            }
        });
    }

    /** Inicia el servidor. */
    private void iniciarServidor() {
        try {
            controlServidor.iniciarServidor();

            btnIniciarServidor.setEnabled(false);
            registrarMovimiento("Servidor iniciado...");
            registrarMovimiento("Esperando luchadores...");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Muestra mensajes en el área de texto. */
    public void registrarMovimiento(String texto) {
        SwingUtilities.invokeLater(() -> areaCombate.append(texto + "\n"));
    }

    /** Muestra la ventana del ganador. */
    public void mostrarGanador(String nombreGanador, String resumenCombate) {
        SwingUtilities.invokeLater(() -> {
            VentanaGanador ventanaGanador = new VentanaGanador(nombreGanador, resumenCombate);
            ventanaGanador.setVisible(true);
        });
    }
}