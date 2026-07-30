package ru.vikhrenko.serverUtils.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;

import java.io.File;
import java.util.Collection;
import java.util.function.Consumer;

public final class OtherUtils {
    public static FileConfiguration getConfig(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void fill(ImmutableBox box, Material material, World world, boolean physics) {
        int minX = Math.min(box.begin().x(), box.end().x());
        int minY = Math.min(box.begin().y(), box.end().y());
        int minZ = Math.min(box.begin().z(), box.end().z());
        int maxX = Math.max(box.begin().x(), box.end().x());
        int maxY = Math.max(box.begin().y(), box.end().y());
        int maxZ = Math.max(box.begin().z(), box.end().z());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType()!=material) {
                        block.setType(material, physics);
                    }
                }
            }
        }
    }

    public static void fill(ImmutableBox box, Material material, World world) {
        fill(box, material, world, true);
    }

    public static void removeAllEntitys(ImmutableBox box, World world) {
        int minX = Math.min(box.begin().x(), box.end().x())/16;
        int minZ = Math.min(box.begin().z(), box.end().z())/16;
        int maxX = Math.max(box.begin().x(), box.end().x())/16;
        int maxZ = Math.max(box.begin().z(), box.end().z())/16;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                world.loadChunk(x, z);
            }
        }
        Collection<Entity> entities = world.getNearbyEntities(box.toBoundingBox());
        for (Entity entity: entities) {
            if (entity.getType()!= EntityType.PLAYER) {
                entity.remove();
            }
        }
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                world.unloadChunk(x, z);
            }
        }
    }
    public static void resetPlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(5.0F);
        player.setExhaustion(0.0F);
        player.getInventory().clear();
        player.clearActivePotionEffects();
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0.0F);
        player.setFireTicks(0);
        player.setInvisible(false);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFallDistance(0);
        player.setGameMode(GameMode.SURVIVAL);
    }
    public static void configureVariable(String name, Consumer<String> todo, ConfigurationSection section) {
        if (section.contains(name)) {
            todo.accept(name);
        }
    }
    public static void configureVariable(String name, Consumer<String> todo, ConfigurationSection section, Object defaultValue) {
        section.addDefault(name, defaultValue);
        configureVariable(name, todo, section);
    }
}
