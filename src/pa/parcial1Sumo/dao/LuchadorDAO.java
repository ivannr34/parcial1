package pa.parcial1Sumo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import pa.parcial1Sumo.conexion.ConexionBD;
import pa.parcial1Sumo.modelo.Luchador;

/**
 * Implementación JDBC para la gestión de luchadores en la base de datos.
 */
public class LuchadorDAO implements ILuchadorDAO {

    /**
     * Guarda un luchador en la base de datos.
     * @return id generado o -1 si falla
     */
    @Override
    public int guardar(Luchador luchador) {
        String sql = "INSERT INTO luchador (nombre, peso, tunica, tecnicas, victorias, disponible) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, luchador.getNombre());
            ps.setDouble(2, luchador.getPeso());
            ps.setString(3, luchador.getTunica());
            ps.setString(4, convertirTecnicasAString(luchador.getTecnicas()));
            ps.setInt(5, luchador.getVictorias());
            ps.setBoolean(6, luchador.isDisponible());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    luchador.setId(id);
                    return id;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar luchador", e);
        }

        return -1;
    }

    /**
     * Lista todos los luchadores.
     */
    @Override
    public List<Luchador> listarTodos() {
        String sql = "SELECT * FROM luchador ORDER BY id_luchador";
        List<Luchador> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al listar luchadores", e);
        }

        return lista;
    }

    /**
     * Lista los luchadores disponibles.
     */
    @Override
    public List<Luchador> listarDisponibles() {
        String sql = "SELECT * FROM luchador WHERE disponible = 1 ORDER BY RAND()";
        List<Luchador> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al listar disponibles", e);
        }

        return lista;
    }

    /**
     * Actualiza el número de victorias de un luchador.
     */
    @Override
    public void actualizarVictorias(int idLuchador, int victorias) {
        String sql = "UPDATE luchador SET victorias = ? WHERE id_luchador = ?";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, victorias);
            ps.setInt(2, idLuchador);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar victorias", e);
        }
    }

    /**
     * Actualiza la disponibilidad de un luchador.
     */
    @Override
    public void actualizarDisponibilidad(int idLuchador, boolean disponible) {
        String sql = "UPDATE luchador SET disponible = ? WHERE id_luchador = ?";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setBoolean(1, disponible);
            ps.setInt(2, idLuchador);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar disponibilidad", e);
        }
    }

    /**
     * Cuenta los luchadores disponibles.
     */
    @Override
    public int contarDisponibles() {
        String sql = "SELECT COUNT(*) FROM luchador WHERE disponible = 1";

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al contar disponibles", e);
        }

        return 0;
    }

    /**
     * Convierte un ResultSet en un objeto Luchador.
     */
    private Luchador mapear(ResultSet rs) throws Exception {
        return new Luchador(
                rs.getInt("id_luchador"),
                rs.getString("nombre"),
                rs.getDouble("peso"),
                rs.getString("tunica"),
                convertirStringALista(rs.getString("tecnicas")),
                rs.getBoolean("disponible"),
                rs.getInt("victorias")
        );
    }

    /**
     * Convierte lista de técnicas a texto.
     */
    private String convertirTecnicasAString(List<String> tecnicas) {
        if (tecnicas == null || tecnicas.isEmpty()) {
            return "";
        }
        return String.join(",", tecnicas);
    }

    /**
     * Convierte texto a lista de técnicas.
     */
    private List<String> convertirStringALista(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(texto.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}