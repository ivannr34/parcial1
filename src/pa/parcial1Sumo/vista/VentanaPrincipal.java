package pa.parcial1Sumo.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Ventana principal que permite navegar entre cliente, servidor o salir.
 */
public class VentanaPrincipal extends JFrame {

    private JButton btnServidor;
    private JButton btnCliente;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblImagen;

    /** Crea la ventana principal. */
    public VentanaPrincipal() {
        Ventana1();
        ConfigurarVentana();
    }

    /** Inicializa los componentes visuales. */
    private void Ventana1() {
        lblTitulo = new JLabel("COMBATE DE SUMO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(139, 69, 19));

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        java.net.URL rutaImagen = getClass().getResource("/pa/parcial1Sumo/Recursos/sumo.png");
        if (rutaImagen != null) {
            ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
            ImageIcon iconoEscalado = new ImageIcon(
                    iconoOriginal.getImage().getScaledInstance(300, 220, java.awt.Image.SCALE_SMOOTH)
            );
            lblImagen.setIcon(iconoEscalado);
        } else {
            lblImagen.setText("Imagen no disponible");
            lblImagen.setFont(new Font("Arial", Font.ITALIC, 14));
        }

        btnServidor = new JButton("Abrir Ventana Servidor");
        btnCliente = new JButton("Abrir Ventana Cliente");
        btnSalir = new JButton("Salir");

        btnServidor.setFont(new Font("Arial", Font.BOLD, 16));
        btnCliente.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));

        btnServidor.setBackground(new Color(205, 133, 63));
        btnCliente.setBackground(new Color(222, 184, 135));
        btnSalir.setBackground(new Color(178, 34, 34));

        btnServidor.setForeground(Color.WHITE);
        btnCliente.setForeground(Color.BLACK);
        btnSalir.setForeground(Color.WHITE);
    }

    /** Configura el diseño de la ventana. */
    private void ConfigurarVentana() {
        setTitle("Ventana Principal");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblImagen.setPreferredSize(new Dimension(400, 220));
        panelCentro.add(lblImagen, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 80, 20, 80));
        panelBotones.add(btnServidor);
        panelBotones.add(btnCliente);
        panelBotones.add(btnSalir);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /** @return botón servidor */
    public JButton getBtnServidor() {
        return btnServidor;
    }

    /** @return botón cliente */
    public JButton getBtnCliente() {
        return btnCliente;
    }

    /** @return botón salir */
    public JButton getBtnSalir() {
        return btnSalir;
    }
}