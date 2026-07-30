package ru.vikhrenko.serverUtils.command.load;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import ru.vikhrenko.serverUtils.structure.StructureBlock;
import ru.vikhrenko.serverUtils.session.CommandUtils;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class NameStructureLoadOptions implements StructureLoadOptions {
    private final StructureBlock structureBlock;

    @Override
    public void execute(Point point, World world, String data) {
        structureBlock.load(point, world, data);
    }

    @Override
    public CompletableFuture<Suggestions> suggest(SuggestionsBuilder builder) {
        return CommandUtils.VALUE.suggest(builder, structureBlock.allStructures(), false);
    }

    @Override
    public String getLiteral() {
        return "name";
    }
}
