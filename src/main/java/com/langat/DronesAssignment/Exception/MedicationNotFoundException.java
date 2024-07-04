package com.langat.DronesAssignment.Exception;

public  class MedicationNotFoundException extends RuntimeException {
    public MedicationNotFoundException(String message) {
        super(message);
    }

    public MedicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
