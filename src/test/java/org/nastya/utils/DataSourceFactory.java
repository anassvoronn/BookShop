package org.nastya.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {
    private String dbUrl;
    private String user;
    private String password;

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

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
