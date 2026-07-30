package ru.vikhrenko.serverUtils.session;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface ListenerUtils {
    boolean isTool(ItemStack itemStack);

    boolean isSpecialBlock(ItemStack itemStack);

    void remove(Listener listener, Player player);

    boolean isClickedByBlock(Player player, Player eventPlayer, ItemStack itemStack, Block block);

    boolean isPlacedSpecialBlock(Player player, Player eventPlayer, ItemStack itemStack);
}
