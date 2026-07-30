package ru.vikhrenko.serverUtils.session;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ru.vikhrenko.serverUtils.session.ListenerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaperListenerManager implements ListenerManager {
    private final Map<UUID, Listener> listenerMap = new HashMap<>();
    private final Plugin plugin;

    public PaperListenerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void add(Listener listener, UUID playerId) {
        remove(playerId);
        listenerMap.put(playerId, listener);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void remove(UUID playerId) {
        Listener listener = listenerMap.remove(playerId);
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }
}
