package ru.vikhrenko.serverUtils.command.load;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import org.bukkit.World;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.concurrent.CompletableFuture;

public interface StructureLoadOptions {
    void execute(Point point, World world, String data);

    CompletableFuture<Suggestions> suggest(SuggestionsBuilder builder);

    String getLiteral();
}
