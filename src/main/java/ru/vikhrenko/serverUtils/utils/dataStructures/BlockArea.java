package ru.vikhrenko.serverUtils.utils.dataStructures;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation;

import java.util.Iterator;
import java.util.NoSuchElementException;

record BlockArea(ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation begin, ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation end) implements Iterable<Block>{
    @Override
    public @NotNull Iterator<Block> iterator() {
        return new BlockIterator(begin, end);
    }

    private static class BlockIterator implements Iterator<Block> {
        private final ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation begin;
        private final ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation end;
        private int currentX;
        private int currentY;
        private int currentZ;
        public BlockIterator(ru.vikhrenko.serverUtils.utils.dataStructures.BlockLocation begin, BlockLocation end) {
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
        public Block next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more BlockPositions in the area");
            }

            Location nextBlockPosition = new Location(begin.world(), currentX, currentY, currentZ);

            currentX++;
            if (currentX > end.x()) {
                currentX = begin.x();
                currentY++;
                if (currentY > end.y()) {
                    currentY = begin.y();
                    currentZ++;
                }
            }

            return nextBlockPosition.getBlock();
        }
    }
}
