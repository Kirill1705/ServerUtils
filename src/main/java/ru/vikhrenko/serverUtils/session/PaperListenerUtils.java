package ru.vikhrenko.serverUtils.session;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import ru.vikhrenko.serverUtils.session.ListenerManager;
import ru.vikhrenko.serverUtils.session.ListenerUtils;
import ru.vikhrenko.serverUtils.session.ToolManager;

public class PaperListenerUtils implements ListenerUtils {
    private final ToolManager toolManager;
    private final ListenerManager listenerManager;

    public PaperListenerUtils(ToolManager toolManager, ListenerManager listenerManager) {
        this.toolManager = toolManager;
        this.listenerManager = listenerManager;
    }

    @Override
    public boolean isTool(ItemStack itemStack) {
        return toolManager.isTool(itemStack);
    }

    @Override
    public boolean isSpecialBlock(ItemStack itemStack) {
        return toolManager.isSpecialBlock(itemStack);
    }

    @Override
    public void remove(Listener listener, Player player) {
        toolManager.removeSpecialBlocks(player);
        toolManager.removeTools(player);
        listenerManager.remove(player.getUniqueId());
    }

    @Override
    public boolean isClickedByBlock(Player player, Player eventPlayer, ItemStack itemStack, Block block) {
        return player == eventPlayer && itemStack != null && block != null && !(block.getType() == Material.AIR) && toolManager.isTool(itemStack);
    }

    @Override
    public boolean isPlacedSpecialBlock(Player player, Player eventPlayer, ItemStack itemStack) {
        return player == eventPlayer && itemStack != null && toolManager.isSpecialBlock(itemStack);
    }
}
