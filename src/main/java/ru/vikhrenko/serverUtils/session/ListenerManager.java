package ru.vikhrenko.serverUtils.session;

import org.bukkit.event.Listener;

import java.util.UUID;

public interface ListenerManager {
    void add(Listener listener, UUID playerId);

    void remove(UUID playerId);
}
