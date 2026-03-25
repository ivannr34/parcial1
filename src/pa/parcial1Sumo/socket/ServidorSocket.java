package pa.parcial1Sumo.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pa.parcial1Sumo.modelo.Combate;
import pa.parcial1Sumo.modelo.Luchador;
import pa.parcial1Sumo.servicio.ArchivoCombateService;
import pa.parcial1Sumo.servicio.CombateService;
import pa.parcial1Sumo.servicio.RegistroLuchadorService;
import pa.parcial1Sumo.vista.VentanaServidor;

/**
 * Servidor que gestiona conexiones de clientes y ejecución de combates.
 */
public class ServidorSocket {

    private static final int PUERTO = 5000;

    private final VentanaServidor vista;
    private final RegistroLuchadorService registroService;
    private final ArchivoCombateService archivoCombateService;
    private final CombateService combateService;

    private final List<Luchador> aspirantes;
    private final Map<Integer, ManejadorCliente> clientesPorId;

    private boolean procesoCombatesIniciado;

    /**
     * Inicializa el servidor con sus servicios y estructuras.
     */
    public ServidorSocket(VentanaServidor vista) {
        this.vista = vista;
        this.registroService = new RegistroLuchadorService();
        this.archivoCombateService = new ArchivoCombateService();

        this.combateService = new CombateService(
                registroService.getDAO(),
                archivoCombateService
        );

        this.aspirantes = new ArrayList<>();
        this.clientesPorId = new HashMap<>();
        this.procesoCombatesIniciado = false;
    }

    /**
     * Inicia el servidor y escucha conexiones de clientes.
     */
    public void iniciar() {
        try (ServerSocket server = new ServerSocket(PUERTO)) {

            vista.registrarMovimiento("Servidor iniciado en puerto " + PUERTO);
            vista.registrarMovimiento("Esperando aspirantes...");

            while (true) {
                Socket socket = server.accept();
                vista.registrarMovimiento("Cliente conectado: " + socket.getInetAddress());

                ManejadorCliente handler = new ManejadorCliente(socket, this);
                handler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            vista.registrarMovimiento("Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Registra un luchador recibido desde un cliente.
     */
    public synchronized void registrarAspirante(Luchador luchador, ManejadorCliente cliente) {
        try {
            registroService.registrar(luchador);
            aspirantes.add(luchador);

            if (luchador.getId() != null) {
                clientesPorId.put(luchador.getId(), cliente);
            }

            vista.registrarMovimiento("Luchador recibido: " + luchador.getNombre());

            if (!procesoCombatesIniciado && registroService.contarDisponibles() >= 6) {
                procesoCombatesIniciado = true;
                new Thread(this::ejecutarCombates).start();
            }

        } catch (Exception e) {
            vista.registrarMovimiento("Error registrando aspirante: " + e.getMessage());
        }
    }

    /**
     * Ejecuta los combates y notifica resultados.
     */
    private void ejecutarCombates() {
        try {
            vista.registrarMovimiento("Iniciando combates...");

            List<Combate> combates = combateService.ejecutarCombates(3, vista);

            for (Combate combate : combates) {
                notificarResultado(combate);
            }

            combateService.mostrarHistorialEnConsola();

            vista.registrarMovimiento("Proceso finalizado.");

        } catch (Exception e) {
            vista.registrarMovimiento("Error en combates: " + e.getMessage());
        }
    }

    /**
     * Notifica el resultado a ambos luchadores.
     */
    private void notificarResultado(Combate combate) {
        if (combate.getLuchador1() != null) {
            enviarACliente(combate.getLuchador1(), combate);
        }
        if (combate.getLuchador2() != null) {
            enviarACliente(combate.getLuchador2(), combate);
        }
    }

    /**
     * Envía el resultado del combate a un cliente.
     */
    private void enviarACliente(Luchador luchador, Combate combate) {
        if (luchador == null || luchador.getId() == null) {
            return;
        }

        ManejadorCliente cliente = clientesPorId.get(luchador.getId());
        if (cliente == null) {
            return;
        }

        if (combate.getGanador() != null
                && combate.getGanador().getId() != null
                && combate.getGanador().getId().equals(luchador.getId())) {
            cliente.enviarResultado("GANASTE");
        } else {
            cliente.enviarResultado("PERDISTE");
        }

        clientesPorId.remove(luchador.getId());
    }
}