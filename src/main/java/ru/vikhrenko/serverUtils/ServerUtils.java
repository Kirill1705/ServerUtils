package ru.vikhrenko.serverUtils;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import ru.vikhrenko.serverUtils.command.LoadStructureCommand;
import ru.vikhrenko.serverUtils.command.SaveStructureCommand;
import ru.vikhrenko.serverUtils.command.load.NameStructureLoadOptions;
import ru.vikhrenko.serverUtils.command.load.PathStructureLoadOptions;
import ru.vikhrenko.serverUtils.structure.StructureBlockImpl;
import ru.vikhrenko.serverUtils.command.GroupCommand;
import ru.vikhrenko.serverUtils.reload.CommandManager;
import ru.vikhrenko.serverUtils.session.ListenerManager;
import ru.vikhrenko.serverUtils.session.PaperListenerManager;
import ru.vikhrenko.serverUtils.session.ToolManager;
import ru.vikhrenko.serverUtils.session.ToolManagerImpl;

import java.util.List;

public final class ServerUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        StructureBlockImpl structureBlock = new StructureBlockImpl(getDataPath());
        new CommandManager().registerReloadCommand(this, List.of(structureBlock));

        ToolManager toolManager = new ToolManagerImpl(this);
        ListenerManager listenerManager = new PaperListenerManager(this);
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();
            commands.register(new GroupCommand("structure", List.of(
                    new GroupCommand("load", List.of(
                            new LoadStructureCommand(new NameStructureLoadOptions(structureBlock), toolManager, listenerManager),
                            new LoadStructureCommand(new PathStructureLoadOptions(structureBlock), toolManager, listenerManager)
                    )),
                    new SaveStructureCommand(toolManager, listenerManager, structureBlock)
            )).create().build());
        });
    }
}
