package ru.vikhrenko.serverUtils.exception;

public class ValidationException extends RuntimeException {
    private final Object cause;

    public ValidationException(Object cause) {
        super("Validation failed for object " + cause);
        this.cause = cause;
    }

    public Object getObjectCause() {
        return cause;
    }
}
