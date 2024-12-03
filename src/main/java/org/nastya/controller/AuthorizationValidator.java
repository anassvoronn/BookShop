package org.nastya.controller;

import org.nastya.service.exception.UserAuthorizationValidationException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {

    private final UserAuthorizationChecker authorizationChecker;

    public AuthorizationValidator(UserAuthorizationChecker authorizationChecker) {
        this.authorizationChecker = authorizationChecker;
    }

    public void validateUserAuthorization(String sessionId) throws UserAuthorizationValidationException {
        if (!authorizationChecker.isUserAuthorized(sessionId)) {
            throw new UserAuthorizationValidationException("User is not authorized to perform this action");
        }
    }
}
