package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static String url;
    private static String user;
    private static String password;

    static {

        try (InputStream in = ConexionBD.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (in == null) {
                throw new RuntimeException(
                        "No se encontró database.properties."
                );
            }

            Properties propiedades = new Properties();
            propiedades.load(in);

            String driver = propiedades.getProperty("db.driver");
            url = propiedades.getProperty("db.url");
            user = propiedades.getProperty("db.username");
            password = propiedades.getProperty("db.password");

            Class.forName(driver);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al cargar la configuración de la base de datos: "
                            + e.getMessage(), e
            );
        }
    }

    public static Connection obtenerConexion() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }
}