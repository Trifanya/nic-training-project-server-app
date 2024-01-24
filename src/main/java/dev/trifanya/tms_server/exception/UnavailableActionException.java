package dev.trifanya.tms_server.exception;

public class UnavailableActionException extends RuntimeException {
    public UnavailableActionException(String message) {
        super(message);
    }
}
