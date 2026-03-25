package pa.parcial1Sumo.vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel que permite ingresar los datos de un luchador.
 */
public class VentanaLuchador extends JPanel {

    private JTextField txtNombre;
    private JTextField txtPeso;

    private JRadioButton rbRoja;
    private JRadioButton rbAzul;
    private JRadioButton rbNegra;

    private ButtonGroup grupoTunica;
    private List<JCheckBox> checkTecnicas;

    /**
     * Crea el formulario con las técnicas disponibles.
     */
    public VentanaLuchador(String titulo, List<String> tecnicasDisponibles) {
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(lblTitulo);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Peso:"));
        txtPeso = new JTextField();
        panelFormulario.add(txtPeso);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Túnica:"));
        JPanel panelTunica = new JPanel(new FlowLayout(FlowLayout.LEFT));

        rbRoja = new JRadioButton("Roja");
        rbAzul = new JRadioButton("Azul");
        rbNegra = new JRadioButton("Negra");

        grupoTunica = new ButtonGroup();
        grupoTunica.add(rbRoja);
        grupoTunica.add(rbAzul);
        grupoTunica.add(rbNegra);

        panelTunica.add(rbRoja);
        panelTunica.add(rbAzul);
        panelTunica.add(rbNegra);

        panelFormulario.add(panelTunica);
        panelFormulario.add(Box.createVerticalStrut(10));

        panelFormulario.add(new JLabel("Técnicas:"));

        JPanel panelTecnicas = new JPanel(new GridLayout(0, 2, 5, 5));
        checkTecnicas = new ArrayList<>();

        for (String tecnica : tecnicasDisponibles) {
            JCheckBox check = new JCheckBox(tecnica);
            checkTecnicas.add(check);
            panelTecnicas.add(check);
        }

        JScrollPane scrollTecnicas = new JScrollPane(panelTecnicas);
        scrollTecnicas.setPreferredSize(new Dimension(400, 200));

        panelFormulario.add(scrollTecnicas);

        add(panelFormulario, BorderLayout.CENTER);
    }

    /** @return nombre ingresado */
    public String getNombreLuchador() {
        return txtNombre.getText().trim();
    }

    /** @return peso ingresado */
    public String getPesoLuchador() {
        return txtPeso.getText().trim();
    }

    /** @return túnica seleccionada */
    public String getTunicaSeleccionada() {
        if (rbRoja.isSelected()) return "Roja";
        if (rbAzul.isSelected()) return "Azul";
        if (rbNegra.isSelected()) return "Negra";
        return "";
    }

    /** @return lista de técnicas seleccionadas */
    public List<String> getTecnicasSeleccionadas() {
        List<String> seleccionadas = new ArrayList<>();

        for (JCheckBox check : checkTecnicas) {
            if (check.isSelected()) {
                seleccionadas.add(check.getText());
            }
        }

        return seleccionadas;
    }

    /** Limpia los campos del formulario. */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtPeso.setText("");
        grupoTunica.clearSelection();

        for (JCheckBox check : checkTecnicas) {
            check.setSelected(false);
        }
    }
}