package org.nastya.ConnectionFactory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DatabaseConnectionFactory {
    private String dbUrl;
    private String user;
    private String password;

    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public Connection getConnection() {
        Connection connection = connectionThreadLocal.get();;
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dbUrl, user, password);
                connectionThreadLocal.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException("Connection failed", e);
            }
        }
        return connection;
    }

    @PostConstruct
    public void readingFromFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(input);
            this.dbUrl = properties.getProperty("database.url");
            this.user = properties.getProperty("database.user");
            this.password = properties.getProperty("database.pass");
        } catch (IOException e) {
            throw new RuntimeException("File reading failed", e);
        }
    }
}
