package pa.parcial1Sumo.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana que muestra el ganador y un resumen del combate.
 */
public class VentanaGanador extends JFrame {

    /**
     * Construye la ventana de resultado.
     *
     * @param nombreGanador nombre del luchador ganador
     * @param resumenCombate resumen textual del combate
     */
    public VentanaGanador(String nombreGanador, String resumenCombate) {
        setTitle("RESULTADO DEL COMBATE");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("GANADOR DEL COMBATE", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(178, 34, 34));

        JLabel lblGanador = new JLabel("Ganador: " + nombreGanador, JLabel.CENTER);
        lblGanador.setFont(new Font("Arial", Font.BOLD, 20));
        lblGanador.setForeground(new Color(0, 102, 0));

        JTextArea areaResumen = new JTextArea();
        areaResumen.setEditable(false);
        areaResumen.setLineWrap(true);
        areaResumen.setWrapStyleWord(true);
        areaResumen.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResumen.setText(resumenCombate);

        JScrollPane scroll = new JScrollPane(areaResumen);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelCentro.add(lblGanador, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);

        add(titulo, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }
}