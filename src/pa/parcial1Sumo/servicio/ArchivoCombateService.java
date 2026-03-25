package pa.parcial1Sumo.servicio;

import java.io.EOFException;
import java.io.File;
import java.io.RandomAccessFile;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pa.parcial1Sumo.modelo.Combate;

/**
 * Servicio que guarda y lee resultados de combate usando RandomAccessFile.
 */
public class ArchivoCombateService {

    private static final String RUTA_ARCHIVO = "data/combates.raf";
    private final File archivo = new File(RUTA_ARCHIVO);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Guarda un combate en el archivo de acceso aleatorio.
     *
     * @param combate combate a registrar
     */
    public void guardar(Combate combate) {
        try {
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
            }

            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                raf.seek(raf.length());

                raf.writeUTF(combate.getFechaHora().format(formatter));
                raf.writeUTF(combate.getLuchador1().getNombre());
                raf.writeUTF(combate.getLuchador2().getNombre());
                raf.writeUTF(combate.getGanador() != null ? combate.getGanador().getNombre() : "SIN GANADOR");
                raf.writeInt(combate.getLuchador1().getVictorias());
                raf.writeInt(combate.getLuchador2().getVictorias());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error guardando combate en archivo", e);
        }
    }

    /**
     * Lee todo el archivo y lo retorna como lista de líneas.
     *
     * @return lista con el historial de combates
     */
    public List<String> leerTodo() {
        List<String> lineas = new ArrayList<>();

        if (!archivo.exists()) {
            return lineas;
        }

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (true) {
                String fecha = raf.readUTF();
                String l1 = raf.readUTF();
                String l2 = raf.readUTF();
                String ganador = raf.readUTF();
                int vict1 = raf.readInt();
                int vict2 = raf.readInt();

                lineas.add(fecha + " | " + l1 + " vs " + l2 + " | ganador: " + ganador
                        + " | victorias: " + vict1 + " - " + vict2);
            }
        } catch (EOFException eof) {
            // fin natural del archivo
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo archivo de combates", e);
        }

        return lineas;
    }

    /**
     * Imprime el contenido del archivo por consola.
     */
    public void imprimirEnConsola() {
        for (String linea : leerTodo()) {
            System.out.println(linea);
        }
    }
}
