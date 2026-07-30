package ru.vikhrenko.serverUtils.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.vikhrenko.serverUtils.session.SaveListener;
import ru.vikhrenko.serverUtils.structure.StructureBlock;
import ru.vikhrenko.serverUtils.command.CustomCommand;
import ru.vikhrenko.serverUtils.session.ListenerManager;
import ru.vikhrenko.serverUtils.session.PaperListenerUtils;
import ru.vikhrenko.serverUtils.session.ToolManager;

@RequiredArgsConstructor
public class SaveStructureCommand implements CustomCommand {
    private final ToolManager toolManager;
    private final ListenerManager listenerManager;
    private final StructureBlock structureBlock;

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> create() {
        return Commands.literal("save")
                .then(Commands.argument("name", StringArgumentType.word())
                        .then(Commands.argument("force", BoolArgumentType.bool())
                                .executes(context -> {
                                    Player player = (Player)context.getSource().getExecutor();
                                    if (player == null) return 0;
                                    SaveListener listener = new SaveListener(player, new PaperListenerUtils(toolManager, listenerManager), structureBlock, context.getArgument("name", String.class), context.getArgument("force", boolean.class));
                                    listenerManager.add(listener, player.getUniqueId());
                                    toolManager.giveTool(player);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
