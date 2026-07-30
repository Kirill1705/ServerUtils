package ru.vikhrenko.serverUtils.exception;

public class EntityNotFoundException extends RuntimeException {
    private final Class<?> clazz;

    public EntityNotFoundException(Class<?> clazz) {
        super("Entity with type " + clazz.toString() + " not found");
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
