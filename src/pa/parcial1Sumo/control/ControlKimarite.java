package pa.parcial1Sumo.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controlador auxiliar para cargar las técnicas desde un archivo properties.
 */
public class ControlKimarite {

    private static final String RUTA_DEFAULT = "data/kimarites.properties";

    /**
     * Carga las técnicas desde el archivo por defecto en la carpeta data.
     *
     * @return lista de técnicas
     */
    public static List<String> cargarTecnicasDesdeData() {
        return cargarTecnicasDesdeArchivo(new File(RUTA_DEFAULT));
    }

    /**
     * Carga las técnicas desde un archivo properties seleccionado.
     *
     * @param archivo archivo properties
     * @return lista de técnicas
     */
    public static List<String> cargarTecnicasDesdeArchivo(File archivo) {
        List<String> tecnicas = new ArrayList<>();

        try (InputStream is = new FileInputStream(archivo)) {
            Properties props = new Properties();
            props.load(is);

            for (String key : props.stringPropertyNames()) {
                tecnicas.add(props.getProperty(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tecnicas;
    }

    /**
     * Abre un JFileChooser para seleccionar un archivo properties.
     *
     * @return archivo seleccionado o null si se cancela
     */
    public static File seleccionarArchivoProperties() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo de técnicas");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos Properties", "properties"));

        int opcion = chooser.showOpenDialog(null);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }
}