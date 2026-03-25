package pa.parcial1Sumo.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Cliente que se conecta al servidor mediante sockets para enviar un luchador.
 */
public class ClienteSocket {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    /**
     * Envía un luchador al servidor y recibe el resultado.
     *
     * @param luchador luchador a enviar
     * @return respuesta del servidor o mensaje de error
     */
    public String enviarLuchador(Luchador luchador) {
        String resultado = "Sin respuesta del servidor";

        try (
                Socket socket = new Socket(HOST, PUERTO);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            out.flush();
            out.writeObject(luchador);
            out.flush();

            Object respuesta = in.readObject();
            if (respuesta instanceof String) {
                resultado = (String) respuesta;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultado = "Error de conexión: " + e.getMessage();
        }

        return resultado;
    }
}