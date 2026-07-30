package ru.vikhrenko.serverUtils.command.load;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import ru.vikhrenko.serverUtils.structure.StructureBlock;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PathStructureLoadOptions implements StructureLoadOptions {
    private final StructureBlock structureBlock;

    @Override
    public void execute(Point point, World world, String data) {
        structureBlock.loadByPath(point, world, data);
    }

    @Override
    public CompletableFuture<Suggestions> suggest(SuggestionsBuilder builder) {
        String remain = builder.getRemaining();
        if (builder.getRemaining().isEmpty()) {
            return builder.buildFuture();
        }
        int idx = remain.lastIndexOf('/');
        File directory = idx > 0 ? Paths.get(remain.substring(1, idx)).toFile() : Paths.get("").toFile();
        if (!directory.exists() || !directory.isDirectory()) {
            return builder.buildFuture();
        }
        Arrays.stream(directory.listFiles())
                .map(file -> "\"" + file.getPath())
                .filter(s -> s.startsWith(remain))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }

    @Override
    public String getLiteral() {
        return "path";
    }
}
