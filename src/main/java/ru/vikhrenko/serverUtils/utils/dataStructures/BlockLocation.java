package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.World;

/**
 * Presents unmodifiable integer Location
 */
public record BlockLocation(int x, int y, int z, World world) {

}
