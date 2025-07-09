package org.nastya.service.config;

import org.nastya.lib.auth.AuthorizationValidator;
import org.nastya.lib.auth.UserAuthorizationChecker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UserAuthorizationChecker userAuthorizationChecker(RestTemplate restTemplate, @Value("${book-shop.authenticator}") String baseUrl) {
        return new UserAuthorizationChecker(restTemplate, baseUrl);
    }

    @Bean
    public AuthorizationValidator authorizationValidator(UserAuthorizationChecker checker) {
        return new AuthorizationValidator(checker);
    }
}
