package com.langat.DronesAssignment.Exception;



public class DroneRegistrationException extends RuntimeException {
    public DroneRegistrationException(String message) {
        super(message);
    }

    public DroneRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }}

