package pa.parcial1Sumo.control;

import java.util.List;
import pa.parcial1Sumo.modelo.Luchador;
import pa.parcial1Sumo.socket.ClienteSocket;

/**
 * Controlador del lado cliente.
 * Gestiona técnicas, creación del luchador y envío por socket.
 */
public class ControlCliente {

    /**
     * Obtiene las técnicas disponibles desde data/kimarites.properties.
     *
     * @return lista de técnicas
     */
    public List<String> obtenerTecnicasDisponibles() {
        return ControlKimarite.cargarTecnicasDesdeData();
    }

    /**
     * Construye un luchador con los datos ingresados por el usuario.
     *
     * @param nombre nombre del luchador
     * @param peso peso del luchador
     * @param tunica túnica elegida
     * @param tecnicas técnicas seleccionadas
     * @return luchador creado
     */
    public Luchador crearLuchador(String nombre, double peso, String tunica, List<String> tecnicas) {
        return new Luchador(nombre, peso, tunica, tecnicas);
    }

    /**
     * Envía el luchador al servidor.
     *
     * @param luchador luchador a enviar
     * @return respuesta del servidor
     */
    public String enviarLuchador(Luchador luchador) {
        ClienteSocket clienteSocket = new ClienteSocket();
        return clienteSocket.enviarLuchador(luchador);
    }
}