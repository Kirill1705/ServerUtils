package ru.vikhrenko.serverUtils.structure;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import ru.vikhrenko.serverUtils.exception.StructureNotFoundException;
import ru.vikhrenko.serverUtils.reload.YamlAbstractReloadable;
import ru.vikhrenko.serverUtils.utils.StructureUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StructureBlockImpl extends YamlAbstractReloadable<StructureBlockImpl.Config> implements StructureBlock {
    private final Path pluginData;

    public StructureBlockImpl(Path pluginData) {
        super(new Config(), Config.class);
        createIfNotExists(pluginData.resolve(getOptions().loadPath));
        createIfNotExists(pluginData.resolve(getOptions().savePath));
        this.pluginData = pluginData;
    }

    @Override
    public boolean save(ImmutableBox box, World world, String name, boolean replace) {
        Structure structure = StructureUtils.saveStructure(box, world);
        Path path = pluginData.resolve(Paths.get(getOptions().savePath)).resolve(name);
        if (!replace && path.toFile().exists()) {
            return false;
        }
        StructureManager structureManager = Bukkit.getStructureManager();
        try {
            structureManager.saveStructure(path.toFile(), structure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void load(Point position, World world, String name) {
        File file = pluginData.resolve(Paths.get(getOptions().loadPath)).resolve(name).toFile();
        checkExistsOrThrow(file);
        place(file, position, world);
    }

    @Override
    public void loadByPath(Point position, World world, String path) {
        File file = Paths.get(path).toFile();
        checkExistsOrThrow(file);
        place(file, position, world);
    }

    @Override
    public List<String> allStructures() {
        File directory = Paths.get(getOptions().loadPath).toFile();
        return Arrays.stream(directory.listFiles())
                .map(File::getName)
                .toList();
    }

    private void place(File file, Point position, World world) {
        StructureManager structureManager = Bukkit.getStructureManager();
        try {
            Structure structure = structureManager.loadStructure(file);
            StructureUtils.place(structure, position.toLocation(world));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkExistsOrThrow(File file) {
        if (!file.exists()) {
            throw new StructureNotFoundException(file.getPath());
        }
    }

    private void createIfNotExists(Path path) {
        File directory = path.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        else if (!directory.isDirectory()) {
            throw new IllegalArgumentException(path + " is not directory");
        }
    }

    @Data
    public static class Config {
        private String loadPath = "load";

        private String savePath = "save";
    }
}
