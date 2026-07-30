package ru.vikhrenko.serverUtils.session;

import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import ru.vikhrenko.serverUtils.session.ListenerUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;
import ru.vikhrenko.serverUtils.utils.dataStructures.Points;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public class LoadListener implements Listener {
    private final Player player;
    private final ListenerUtils listenerUtils;
    private final BiConsumer<Point, World> service;

    @EventHandler
    public void onTap(PlayerInteractEvent event) {
        if (listenerUtils.isClickedByBlock(player, event.getPlayer(), event.getItem(), event.getClickedBlock())) {
            Point point = Points.fromLocation(event.getClickedBlock().getLocation());
            World world = event.getClickedBlock().getWorld();
            service.accept(point, world);
            event.setCancelled(true);
            listenerUtils.remove(this, player);
        }
    }
}
