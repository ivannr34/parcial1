package pa.parcial1Sumo.servicio;

import pa.parcial1Sumo.dao.LuchadorDAO;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Servicio encargado de la gestión y registro de luchadores.
 */
public class RegistroLuchadorService {

    private final LuchadorDAO luchadorDAO;

    /**
     * Inicializa el servicio con su DAO.
     */
    public RegistroLuchadorService() {
        this.luchadorDAO = new LuchadorDAO();
    }

    /**
     * Registra un luchador en la base de datos.
     */
    public void registrar(Luchador luchador) throws Exception {
        luchadorDAO.guardar(luchador);
    }

    /**
     * Cuenta los luchadores disponibles.
     */
    public int contarDisponibles() throws Exception {
        return luchadorDAO.contarDisponibles();
    }

    /**
     * Retorna el DAO de luchadores.
     */
    public LuchadorDAO getDAO() {
        return luchadorDAO;
    }
}