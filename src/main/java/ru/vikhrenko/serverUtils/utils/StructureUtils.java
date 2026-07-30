package ru.vikhrenko.serverUtils.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public final class StructureUtils {
    private static void loadStructure(String namespace, File file) throws IOException {
        String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
        NamespacedKey key = new NamespacedKey(namespace, name);
        StructureManager manager = Bukkit.getStructureManager();
        Structure structure = manager.loadStructure(file);
        Bukkit.getStructureManager().registerStructure(key, structure);
    }
    public static void loadStructures() {
        World world = Bukkit.getWorlds().get(0);
        File generatedFolder = new File(world.getWorldFolder(), "generated");

        if (!generatedFolder.exists() || !generatedFolder.isDirectory()) {
            return;
        }

        File[] namespaceFolders = generatedFolder.listFiles(File::isDirectory);

        if (namespaceFolders == null) return;

        for (File namespaceFolder : namespaceFolders) {
            String namespace = namespaceFolder.getName();
            File structuresFolder = new File(namespaceFolder, "structures");

            if (!structuresFolder.exists() || !structuresFolder.isDirectory()) {
                continue;
            }

            File[] structureFiles = structuresFolder.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".nbt"));

            if (structureFiles == null) continue;

            for (File file : structureFiles) {
                try {
                    loadStructure(namespace, file);
                } catch (Throwable e) {
                    System.err.println("Failed to load structure from file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
                finally {
                    System.err.println("Failed to load structure from file: " + file.getAbsolutePath() + ": Some error");
                }
            }
        }
    }
    public static Structure load(NamespacedKey name) {
        StructureManager manager = Bukkit.getStructureManager();
        return manager.loadStructure(name);

    }
    public static void place(NamespacedKey name, Location location) {
        Structure structure = load(name);
        if (structure==null) throw new IllegalArgumentException("Structure "+name+" does not exists");
        place(structure, location);
    }
    public static void place(Structure structure, Location location) {
        structure.place(location, true, StructureRotation.NONE, Mirror.NONE, 0, 1, new Random());
    }
    private static ImmutableBox convertLocations(ImmutableBox other) {
        return new ImmutableBox(other.begin(), other.end().add(new Point(1, 1, 1)));
    }
    public static Structure saveStructure(ImmutableBox box, World world) {
        StructureManager manager = Bukkit.getStructureManager();
        Structure structure = manager.createStructure();
        ImmutableBox corners = convertLocations(box);
        structure.fill(corners.begin().toLocation(world), corners.end().toLocation(world), true);
        return structure;
    }
    public static Structure save(ImmutableBox box, World world, NamespacedKey name) {
        Structure structure = saveStructure(box, world);
        return save(structure, name);
    }
    public static Structure save(Structure structure, NamespacedKey name) {
        StructureManager manager = Bukkit.getStructureManager();
        try {
            manager.registerStructure(name, structure);
            manager.saveStructure(name, structure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return structure;
    }
    public static boolean deleteStructure(NamespacedKey key) {
        StructureManager manager = Bukkit.getStructureManager();
        try {
            manager.deleteStructure(key, true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
