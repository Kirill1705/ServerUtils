package ru.vikhrenko.serverUtils.exception;

public class StructureNotFoundException extends RuntimeException {
    public StructureNotFoundException(String name) {
        super("Structure with name or path " + name + " not found");
    }
}
