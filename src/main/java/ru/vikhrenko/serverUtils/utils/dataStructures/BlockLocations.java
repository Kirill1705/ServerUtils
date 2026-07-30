package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.Location;
import org.bukkit.World;
import ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class BlockLocations {
    public static ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation fromLocation(Location location) {
        return new ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld());
    }

    public static ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation fromPointAndWorld(Point point, World world) {
        return new ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation(point.x(), point.y(), point.z(), world);
    }

    public static Point toPoint(ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation location) {
        return new Point(location.x(), location.y(), location.z());
    }

    public static Location toLocation(BlockLocation location) {
        return new Location(location.world(), location.x(), location.y(), location.z());
    }
}
