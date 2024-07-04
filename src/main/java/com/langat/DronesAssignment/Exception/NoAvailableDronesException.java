package com.langat.DronesAssignment.Exception;

public class NoAvailableDronesException extends RuntimeException {
    public NoAvailableDronesException(String message) {
        super(message);
    }

    public NoAvailableDronesException(String message, Throwable cause) {
        super(message, cause);
    }
}
