package org.nastya.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class DaoConfiguration {

    @Bean
    public ThreadLocal<Connection> connectionThreadLocal() {
        return new ThreadLocal<>();
    }
}
