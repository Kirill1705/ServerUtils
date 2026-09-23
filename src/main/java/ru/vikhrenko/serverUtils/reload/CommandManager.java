package ru.vikhrenko.serverUtils.reload;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.Plugin;
import ru.vikhrenko.serverUtils.reload.Reloadable;

import java.nio.file.Path;
import java.util.Collection;

public class CommandManager {
    public void registerReloadCommand(Plugin plugin, Collection<ru.vikhrenko.serverUtils.reload.Reloadable> repositories) {
        reload(repositories, plugin);

        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();
            commands.register(Commands.literal("reload").executes(context -> {
                        if (!context.getSource().getSender().isOp()) {
                            context.getSource().getSender().sendMessage(Component.text("Only for admins!"));
                            return Command.SINGLE_SUCCESS;
                        }
                        plugin.reloadConfig();
                        boolean success = reload(repositories, plugin);
                        if (success) {
                            context.getSource().getSender().sendMessage(Component.text("No exceptions was thrown").color(NamedTextColor.GREEN));
                        }
                        else {
                            context.getSource().getSender().sendMessage(Component.text("Some exceptions was thrown, see more in console").color(NamedTextColor.YELLOW));
                        }
                        return Command.SINGLE_SUCCESS;
                    }).build());
        });
    }

    private boolean reload(Collection<ru.vikhrenko.serverUtils.reload.Reloadable> repositories, Plugin plugin) {
        Path path = plugin.getDataPath().resolve(Path.of("config.yml"));
        boolean success = true;
        for (Reloadable reloadable: repositories) {
            try {
                reloadable.reload(path);
            } catch (RuntimeException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
