package com.langat.DronesAssignment.Exception;


public class InvalidBatteryCapacityException extends RuntimeException {
    public InvalidBatteryCapacityException(String message) {
        super(message);
    }

    public InvalidBatteryCapacityException(String message, Throwable cause) {
        super(message, cause);
    }
}
