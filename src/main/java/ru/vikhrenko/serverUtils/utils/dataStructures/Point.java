package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;

/**
 * Presents unmodifiable integer 3D Point
 */
public record Point(int x, int y, int z) {

    public Point add(Point other) {
        return new Point(x + other.x(), y + other.y(), z + other.z());
    }

    public Point subtract(Point other) {
        return new Point(x - other.x(), y - other.y(), z - other.z());
    }

    public Point multiply(Point other) {
        return new Point(x * other.x(), y * other.y(), z * other.z());
    }

    public Point min(Point other) {
        return new Point(Math.min(x, other.x()), Math.min(y, other.y()), Math.min(z, other.z()));
    }

    public Point max(Point other) {
        return new Point(Math.max(x, other.x()), Math.max(y, other.y()), Math.max(z, other.z()));
    }

    public Point size(Point other) {
        return new Point(Math.abs(other.x() - x) + 1, Math.abs(other.y() - y) + 1, Math.abs(other.z() - z) + 1);
    }

    public Point add(int i) {
        return new Point(x + i, y + i, z + i);
    }

    public Point multiply(int i) {
        return new Point(x * i, y * i, z * i);
    }

    public Point abs() {
        return new Point(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public boolean moreOrEquals(Point other) {
        return x() >= other.x() && y() >= other.y() && z() >= other.z();
    }
    public boolean lessOrEquals(Point other) {
        return x() <= other.x() && y() <= other.y() && z() <= other.z();
    }
    public boolean more(Point other) {
        return x() > other.x() && y() > other.y() && z() > other.z();
    }
    public boolean less(Point other) {
        return x() < other.x() && y() < other.y() && z() < other.z();
    }
    public BlockVector toBlockVector() {
        return new BlockVector(x(), y(), z());
    }

    public BoundingBox toBoundingBox(Point other) {
        Point min = min(other);
        Point max = max(other);
        return new BoundingBox(min.x(), min.y(), min.z(), max.x() + 1, max.y() + 1, max.z() + 1);
    }

    public int scalarMultiply(Point other) {
        return x() * other.x() + y() * other.y() + z() * other.z();
    }

    public int sumXYZ() {
        return x() + y() + z();
    }

    public double size() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    public double distanceTo(Point other) {
        return Math.sqrt(distanceSquared(other));
    }
    public double distanceSquared(Point other) {
        return (other.x() - x())*(other.x() - x()) + (other.y() - y())*(other.y() - y()) + (other.z() - z())*(other.z() - z());
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }
}
