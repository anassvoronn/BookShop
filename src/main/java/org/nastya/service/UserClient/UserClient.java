package org.nastya.service.UserClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private static final Logger log = LoggerFactory.getLogger(UserClient.class);
    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isUserAuthorized(String session) {
        String url = "/api/authentication/validate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + session);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("Error while checking user authorization with session: {}. Error: {}", session, e.getMessage(), e);
            return false;
        }
    }
}
