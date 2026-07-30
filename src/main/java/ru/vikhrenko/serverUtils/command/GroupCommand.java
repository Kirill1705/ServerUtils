package ru.vikhrenko.serverUtils.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import ru.vikhrenko.serverUtils.command.CustomCommand;

import java.util.List;

public class GroupCommand implements ru.vikhrenko.serverUtils.command.CustomCommand {
    private final String literal;
    private final List<ru.vikhrenko.serverUtils.command.CustomCommand> commands;
    private final boolean onlyOps;

    public GroupCommand(String literal, List<ru.vikhrenko.serverUtils.command.CustomCommand> commands) {
        this.literal = literal;
        this.commands = commands;
        this.onlyOps = true;
    }

    public GroupCommand(String literal, List<ru.vikhrenko.serverUtils.command.CustomCommand> commands, boolean onlyOps) {
        this.literal = literal;
        this.commands = commands;
        this.onlyOps = onlyOps;
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> create() {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(literal);
        if (onlyOps) {
            builder.requires(commandSourceStack -> commandSourceStack.getSender().isOp());
        }
        for (CustomCommand command: commands) {
            builder.then(command.create());
        }
        return builder;
    }
}
