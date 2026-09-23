package ru.vikhrenko.serverUtils.utils.dataStructures;

public record DoublePosition(double x, double y, double z, Point direction) {
    public DoublePosition(double x, double y, double z) {
        this(x, y, z, new Point(1, 0, 0));
    }
}
