package org.nastya.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {
    private String dbUrl;
    private String user;
    private String password;
    private String driver;

    public HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(20); // Adjust based on your needs
        config.setMinimumIdle(5);
        return new HikariDataSource(config);
    }

    public void readingFromFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(input);
            this.dbUrl = properties.getProperty("database.url");
            this.user = properties.getProperty("database.user");
            this.password = properties.getProperty("database.pass");
            this.driver = properties.getProperty("database.driver");
        } catch (IOException e) {
            throw new RuntimeException("File reading failed", e);
        }
    }
}
