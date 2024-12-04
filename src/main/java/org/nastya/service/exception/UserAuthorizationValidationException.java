package org.nastya.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User is not authorized to perform this action")
public class UserAuthorizationValidationException extends Exception {
    public UserAuthorizationValidationException(String message) {
        super(message);
    }
}
