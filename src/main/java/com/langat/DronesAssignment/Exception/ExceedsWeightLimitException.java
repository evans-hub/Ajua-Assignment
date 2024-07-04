package com.langat.DronesAssignment.Exception;

public class ExceedsWeightLimitException extends RuntimeException {
    public ExceedsWeightLimitException(String message) {
        super(message);
    }

    public ExceedsWeightLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
