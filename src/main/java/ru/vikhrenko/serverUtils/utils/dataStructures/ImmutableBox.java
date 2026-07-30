package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import ru.vikhrenko.serverUtils.utils.dataStructures.BlockPositionIterator;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Iterator;

public record ImmutableBox(Point begin, Point end) implements Iterable<Point> {
    public ImmutableBox {
        if (!end.moreOrEquals(begin)) {
            throw new IllegalArgumentException("end should be greater than begin");
        }
    }

    public Point begin() {
        return begin;
    }

    public Point end() {
        return end;
    }

    public @NotNull Iterator<Point> iterator() {
        return new BlockPositionIterator(begin, end);
    }

    public Point size() {
        return begin().size(end());
    }
    public BoundingBox toBoundingBox() {
        return begin().toBoundingBox(end());
    }
    public boolean contains(Point position) {
        return begin().lessOrEquals(position) && end().moreOrEquals(position);
    }
}
