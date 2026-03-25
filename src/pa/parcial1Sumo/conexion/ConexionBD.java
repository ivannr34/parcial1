package pa.parcial1Sumo.conexion;


import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Singleton encargado de administrar una única conexión a la base de datos.
 * Lee su configuración desde el archivo data/db.properties.
 */
public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private String url;
    private String usuario;
    private String password;
    private String driver;

    /**
     * Constructor privado para cumplir con el patrón Singleton.
     */
    private ConexionBD() {
        cargarPropiedades();
        conectar();
    }

    /**
     * Obtiene la instancia única de la conexión.
     *
     * @return instancia de ConexionBD
     */
    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Retorna la conexión activa.
     *
     * @return conexión JDBC
     */
    public synchronized Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conectar();
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo validar la conexión a BD", e);
        }
        return conexion;
    }

    /**
     * Carga la configuración desde data/db.properties.
     */
    private void cargarPropiedades() {
        Properties props = new Properties();

        try (InputStream is = new FileInputStream("data/db.properties")) {
            props.load(is);

            url = props.getProperty("db.url");
            usuario = props.getProperty("db.usuario");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer data/db.properties", e);
        }
    }

    /**
     * Abre la conexión a la base de datos.
     */
    private void conectar() {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar a la base de datos", e);
        }
    }
}