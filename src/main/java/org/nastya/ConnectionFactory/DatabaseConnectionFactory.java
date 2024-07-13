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

    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed", e);
        }
        return connection;
    }

    @PostConstruct
    public void readingFromFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("database.properties")) {
            properties.load(input);
            this.dbUrl = properties.getProperty("DB_URL");
            this.user = properties.getProperty("USER");
            this.password = properties.getProperty("PASS");
        } catch (IOException e) {
            throw new RuntimeException("File reading failed", e);
        }
    }
}
