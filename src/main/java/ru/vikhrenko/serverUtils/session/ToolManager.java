package ru.vikhrenko.serverUtils.session;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ToolManager {
    void giveTool(Player player);

    void giveSpecialBlock(Player player);

    boolean isTool(ItemStack itemStack);

    boolean isSpecialBlock(ItemStack itemStack);

    void removeTools(Player player);

    void removeSpecialBlocks(Player player);
}
