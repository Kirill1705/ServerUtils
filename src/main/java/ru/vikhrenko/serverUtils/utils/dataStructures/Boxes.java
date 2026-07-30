package ru.vikhrenko.serverUtils.utils.dataStructures;

import ru.vikhrenko.serverUtils.utils.dataStructures.ImmutableBox;
import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

public class Boxes {
    public static ImmutableBox fromCorners(Point corner1, Point corner2) {
        return new ImmutableBox(corner1.min(corner2), corner1.max(corner2));
    }

    public static ImmutableBox fromBeginAndSize(Point begin, Point size) {
        if (!size.more(new Point(0 ,0, 0))) {
            throw new IllegalArgumentException("size must be greater than zero!");
        }
        return new ImmutableBox(begin, begin.add(size).subtract(new Point(1, 1, 1)));
    }
}
