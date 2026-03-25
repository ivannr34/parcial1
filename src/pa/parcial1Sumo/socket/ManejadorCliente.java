package pa.parcial1Sumo.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Hilo encargado de atender a un cliente individual.
 * Recibe el luchador aspirante y conserva el canal de salida
 * para responder cuando el servidor determine el resultado del combate.
 */
public class ManejadorCliente extends Thread {

    private final Socket socket;
    private final ServidorSocket servidor;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Luchador luchador;

    /**
     * Crea un manejador para un cliente conectado al servidor.
     *
     * @param socket socket del cliente
     * @param servidor servidor principal que coordina el proceso
     */
    public ManejadorCliente(Socket socket, ServidorSocket servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    /**
     * Recibe el objeto Luchador enviado por el cliente
     * y lo registra en el servidor.
     */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());

            luchador = (Luchador) in.readObject();

            servidor.registrarAspirante(luchador, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna el luchador recibido desde el cliente.
     *
     * @return luchador recibido
     */
    public Luchador getLuchador() {
        return luchador;
    }

    /**
     * Envía el resultado del combate al cliente.
     *
     * @param mensaje mensaje de resultado
     */
    public void enviarResultado(String mensaje) {
        try {
            if (out != null) {
                out.writeObject(mensaje);
                out.flush();
            }

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}