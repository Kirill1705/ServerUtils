package ru.vikhrenko.serverUtils.utils.dataStructures;

import ru.vikhrenko.serverUtils.utils.dataStructures.Point;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BlockPositionIterator implements Iterator<Point> {
    private final Point begin;
    private final Point end;
    private int currentX;
    private int currentY;
    private int currentZ;
    public BlockPositionIterator(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
        currentX = begin.x();
        currentY = begin.y();
        currentZ = begin.z();
    }
    @Override
    public boolean hasNext() {
        return currentX <= end.x() && currentY <= end.y() && currentZ <= end.z();
    }

    @Override
    public Point next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more BlockPositions in the area");
        }

        Point nextBlockPosition = new Point(currentX, currentY, currentZ);

        currentX++;
        if (currentX > end.x()) {
            currentX = begin.x();
            currentY++;
            if (currentY > end.y()) {
                currentY = begin.y();
                currentZ++;
            }
        }

        return nextBlockPosition;
    }
}