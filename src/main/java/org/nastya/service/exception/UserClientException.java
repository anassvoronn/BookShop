package org.nastya.service.exception;

public class UserClientException extends RuntimeException {
    public UserClientException(String message) {
        super(message);
    }
}
