package ru.vikhrenko.serverUtils.session;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public enum CommandUtils {
    VALUE;

    private final String defaultValue = "default";

    public CompletableFuture<Suggestions> suggest(SuggestionsBuilder suggestionsBuilder, List<String> suggestions, boolean addDefault) {
        String remaining = suggestionsBuilder.getRemaining().toLowerCase();
        if (addDefault) {
            suggestions = new ArrayList<>(suggestions);
            suggestions.add(defaultValue);
        }
        suggestions.stream()
                .filter(mode -> mode.toLowerCase().startsWith(remaining))
                .forEach(suggestionsBuilder::suggest);
        return suggestionsBuilder.buildFuture();
    }

    public<T> T getNullable(CommandContext<CommandSourceStack> context, String name, Function<String, T> parse) {
        String value = context.getArgument(name, String.class);
        if (value.equals(defaultValue)) {
            return null;
        }
        return parse.apply(value);
    }
}
