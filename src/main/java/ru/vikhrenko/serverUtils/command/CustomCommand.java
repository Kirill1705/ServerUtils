package ru.vikhrenko.serverUtils.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public interface CustomCommand {
    LiteralArgumentBuilder<CommandSourceStack> create();
}
