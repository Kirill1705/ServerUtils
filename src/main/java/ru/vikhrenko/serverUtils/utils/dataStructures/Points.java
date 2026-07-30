package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class Points {
    public static Point fromVector(Vector vector) {
        return new Point(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static Point fromLocation(Location location) {
        return new Point(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Point fromBlockLocation(BlockLocation location) {
        return new Point(location.x(), location.y(), location.z());
    }
}
