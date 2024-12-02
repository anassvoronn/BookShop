package org.nastya.service.UserClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserAuthorizationChecker {
    private static final Logger log = LoggerFactory.getLogger(UserAuthorizationChecker.class);
    private final RestTemplate restTemplate;

    public UserAuthorizationChecker(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isUserAuthorized(String sessionId) {
        log.info("Checking authorization for sessionId: {}", sessionId);
        String baseUrl = "http://localhost:8081/book-shop-authenticator";
        String url = baseUrl + "/api/session/status/" + sessionId;
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, null, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }
}
