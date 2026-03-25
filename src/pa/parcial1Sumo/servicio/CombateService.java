
package pa.parcial1Sumo.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import pa.parcial1Sumo.dao.ILuchadorDAO;
import pa.parcial1Sumo.modelo.Combate;
import pa.parcial1Sumo.modelo.Dohyo;
import pa.parcial1Sumo.modelo.Luchador;
import pa.parcial1Sumo.util.MovimientoListener;

/**
 * Servicio que selecciona luchadores disponibles, ejecuta combates,
 * actualiza victorias y persiste el resultado en archivo.
 */
public class CombateService {

    private final ILuchadorDAO luchadorDAO;
    private final ArchivoCombateService archivoCombateService;
    private final Random random = new Random();

    /**
     * Construye el servicio de combate.
     *
     * @param luchadorDAO acceso a datos de luchadores
     * @param archivoCombateService servicio de archivo de combates
     */
    public CombateService(ILuchadorDAO luchadorDAO, ArchivoCombateService archivoCombateService) {
        this.luchadorDAO = luchadorDAO;
        this.archivoCombateService = archivoCombateService;
    }

    /**
     * Ejecuta la cantidad de combates solicitada, si hay suficientes luchadores.
     *
     * @param cantidad cantidad de combates a realizar
     * @param listener receptor de mensajes del dohyō
     * @return lista de combates realizados
     */
    public List<Combate> ejecutarCombates(int cantidad, MovimientoListener listener) {
        List<Combate> combatesRealizados = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            List<Luchador> disponibles = luchadorDAO.listarDisponibles();

            if (disponibles.size() < 2) {
                break;
            }

            Luchador[] pareja = seleccionarDosAleatorios(disponibles);
            Luchador l1 = pareja[0];
            Luchador l2 = pareja[1];

            luchadorDAO.actualizarDisponibilidad(l1.getId(), false);
            luchadorDAO.actualizarDisponibilidad(l2.getId(), false);

            l1.setDisponible(false);
            l2.setDisponible(false);

            Dohyo dohyo = new Dohyo(l1, l2, listener);
            dohyo.iniciarCombate();

            Luchador ganador = dohyo.getGanador();
            if (ganador != null && ganador.getId() != null) {
                ganador.sumarVictoria();
                luchadorDAO.actualizarVictorias(ganador.getId(), ganador.getVictorias());
            }

            Combate combate = new Combate(l1, l2, ganador, LocalDateTime.now());
            archivoCombateService.guardar(combate);
            combatesRealizados.add(combate);
        }

        return combatesRealizados;
    }

    /**
     * Selecciona dos luchadores distintos de una lista.
     *
     * @param disponibles lista de luchadores disponibles
     * @return arreglo de dos luchadores
     */
    private Luchador[] seleccionarDosAleatorios(List<Luchador> disponibles) {
        int i1 = random.nextInt(disponibles.size());
        Luchador l1 = disponibles.remove(i1);

        int i2 = random.nextInt(disponibles.size());
        Luchador l2 = disponibles.remove(i2);

        return new Luchador[]{l1, l2};
    }

    /**
     * Imprime el historial almacenado en el archivo de acceso aleatorio.
     */
    public void mostrarHistorialEnConsola() {
        archivoCombateService.imprimirEnConsola();
    }
}