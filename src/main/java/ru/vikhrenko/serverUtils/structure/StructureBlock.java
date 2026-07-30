package ru.vikhrenko.serverUtils.structure;

import org.bukkit.World;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.List;

public interface StructureBlock {
    boolean save(ImmutableBox box, World world, String name, boolean replace);

    void load(Point position, World world, String name);

    void loadByPath(Point position, World world, String path);

    List<String> allStructures();

}
