package org.nastya.ConnectionFactory;

public class ClosedConnectionException extends RuntimeException {

    public ClosedConnectionException(String message) {
        super(message);
    }
}
