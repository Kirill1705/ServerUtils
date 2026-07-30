package ru.vikhrenko.serverUtils.session;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.vikhrenko.serverUtils.structure.StructureBlock;
import ru.vikhrenko.serverUtils.session.ListenerUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Boxes;
import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import ru.vikhrenko.serverUtils.utils.dataStructures.Points;

@RequiredArgsConstructor
public class SaveListener implements Listener {
    private final Player player;
    private final ListenerUtils listenerUtils;
    private final StructureBlock structureBlock;
    private final String name;
    private final boolean replace;

    private Point corner1;

    @EventHandler
    public void onTap(PlayerInteractEvent event) {
        if (listenerUtils.isClickedByBlock(player, event.getPlayer(), event.getItem(), event.getClickedBlock())) {
            if (corner1 == null) {
                corner1 = Points.fromLocation(event.getClickedBlock().getLocation());
            }
            else {
                Point corner2 = Points.fromLocation(event.getClickedBlock().getLocation());
                ImmutableBox box = Boxes.fromCorners(corner1, corner2);
                boolean success = structureBlock.save(box, event.getPlayer().getWorld(), name, replace);
                listenerUtils.remove(this, player);
                if (!success) {
                    player.sendMessage(Component.text("Structure already exists, use force flag").color(NamedTextColor.YELLOW));
                }
            }
            event.setCancelled(true);
        }
    }
}
