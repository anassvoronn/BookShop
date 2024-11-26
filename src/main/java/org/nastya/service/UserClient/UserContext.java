package org.nastya.service.UserClient;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private static final ThreadLocal<String> currentUserSession = new ThreadLocal<>();

    public void setCurrentUserSession(String token) {
        currentUserSession.set(token);
    }

    public String getCurrentUserSession() {
        return currentUserSession.get();
    }

    public void clear() {
        currentUserSession.remove();
    }
}
