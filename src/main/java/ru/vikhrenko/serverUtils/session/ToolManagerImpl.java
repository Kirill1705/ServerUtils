package ru.vikhrenko.serverUtils.session;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import ru.vikhrenko.serverUtils.session.ToolManager;

public class ToolManagerImpl implements ToolManager {
    private final NamespacedKey toolKey;
    private final NamespacedKey blockKey;

    public ToolManagerImpl(Plugin plugin) {
        toolKey = new NamespacedKey(plugin, "special_tool");
        blockKey = new NamespacedKey(plugin, "special_block");
    }

    @Override
    public void giveTool(Player player) {
        removeTools(player);
        giveItem(player, Material.WOODEN_AXE, toolKey);
    }

    @Override
    public void giveSpecialBlock(Player player) {
        removeSpecialBlocks(player);
        giveItem(player, Material.BARRIER, blockKey);
    }

    private void giveItem(Player player, Material material, NamespacedKey key) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(meta);
        player.getInventory().addItem(itemStack);
    }

    @Override
    public boolean isTool(ItemStack itemStack) {
        return itemStack.getPersistentDataContainer().has(toolKey);
    }

    @Override
    public boolean isSpecialBlock(ItemStack itemStack) {
        return itemStack.getPersistentDataContainer().has(blockKey);
    }

    @Override
    public void removeTools(Player player) {
        removeItemsByPdcKey(player, toolKey);
    }

    @Override
    public void removeSpecialBlocks(Player player) {
        removeItemsByPdcKey(player, blockKey);
    }

    private void removeItemsByPdcKey(Player player, NamespacedKey targetKey) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || !item.hasItemMeta()) {
                continue;
            }
            var pdc = item.getItemMeta().getPersistentDataContainer();
            if (pdc.has(targetKey)) {
                inventory.setItem(i, null);
            }
        }
    }
}
