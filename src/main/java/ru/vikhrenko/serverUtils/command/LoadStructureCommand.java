package ru.vikhrenko.serverUtils.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.vikhrenko.serverUtils.command.load.StructureLoadOptions;
import ru.vikhrenko.serverUtils.session.LoadListener;
import ru.vikhrenko.serverUtils.command.CustomCommand;
import ru.vikhrenko.serverUtils.session.ListenerManager;
import ru.vikhrenko.serverUtils.session.PaperListenerUtils;
import ru.vikhrenko.serverUtils.session.ToolManager;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

@RequiredArgsConstructor
public class LoadStructureCommand implements CustomCommand {
    private final StructureLoadOptions service;
    private final ToolManager toolManager;
    private final ListenerManager listenerManager;

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> create() {
        return Commands.literal(service.getLiteral())
                .then(Commands.argument("name", StringArgumentType.string())
                        .suggests(((context, builder) -> service.suggest(builder)))
                        .executes(context -> {
                            Player player = (Player)context.getSource().getExecutor();
                            if (player == null) return 0;
                            LoadListener listener = new LoadListener(player, new PaperListenerUtils(toolManager, listenerManager), ((point, world) -> service.execute(point, world, context.getArgument("name", String.class))));
                            listenerManager.add(listener, player.getUniqueId());
                            toolManager.giveTool(player);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.argument("position", ArgumentTypes.blockPosition())
                                .executes(context -> {
                                    Player player = (Player)context.getSource().getExecutor();
                                    if (player == null) return 0;
                                    BlockPositionResolver resolver = context.getArgument("position", BlockPositionResolver.class);
                                    BlockPosition blockPos = resolver.resolve(context.getSource());
                                    service.execute(new Point(blockPos.blockX(), blockPos.blockY(), blockPos.blockZ()), player.getWorld(), context.getArgument("name", String.class));
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                );
    }
}
