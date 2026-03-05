package fr.neri.brumelame.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance = null;

    private Connection connection = null;
    private String url ;
    private String user;
    private String password;

    public Connection getConnection() {
        return connection;
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
        loadProperties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/resources/db.properties")) {
            if (is != null) {
                props.load(is);
                this.url = props.getProperty("jdbc.url", url);
                this.user = props.getProperty("jdbc.user", user);
                this.password = props.getProperty("jdbc.password", password);
            } else {
                System.out.println("db.properties introuvable sur le classpath (/resources/db.properties)");
            }
        } catch (IOException e) {
            System.out.println("Impossible de charger db.properties: " + e.getMessage());
        }
    }
}
