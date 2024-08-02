package org.nastya.dao.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("database.properties")
public class DaoConfiguration {

    @Value("${database.url}")
    private String jdbcUrl;

    @Value("${database.user}")
    private String username;

    @Value("${database.pass}")
    private String password;

    @Value("${database.driver}")
    private String driverClassName;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(20); // Adjust based on your needs
        config.setMinimumIdle(5);
        return new HikariDataSource(config);
    }
}
