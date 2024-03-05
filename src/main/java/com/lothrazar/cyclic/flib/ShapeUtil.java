package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ShapeUtil {
    public static List<BlockPos> cubeSquareBase(final BlockPos pos, int radius, int height) {
        List<BlockPos> shape = new ArrayList<>();
        int xMin = pos.getX() - radius;
        int xMax = pos.getX() + radius;
        int zMin = pos.getZ() - radius;
        int zMax = pos.getZ() + radius;
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                for (int y = pos.getY(); y <= pos.getY() + height; y++) {
                    //now go max height on each pillar for sort order
                    shape.add(new BlockPos(x, y, z));
                }
            }
        }
        return shape;
    }

    public static List<BlockPos> squareHorizontalFull(final BlockPos pos, int radius) {
        List<BlockPos> shape = new ArrayList<>();
        // search in a cube
        int xMin = pos.getX() - radius;
        int xMax = pos.getX() + radius;
        int zMin = pos.getZ() - radius;
        int zMax = pos.getZ() + radius;
        int y = pos.getY();
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                shape.add(new BlockPos(x, y, z));
            }
        }
        //corners are done so offset
        return shape;
    }

    public static List<BlockPos> repeatShapeByHeight(List<BlockPos> shape, final int height) {
        List<BlockPos> newShape = new ArrayList<>();
        newShape.addAll(shape); //copy it
        for (int i = 1; i <= Math.abs(height); i++) {
            for (BlockPos p : shape) {
                BlockPos newOffset = null;
                if (height > 0) {
                    newOffset = p.above(i);
                }
                else {
                    newOffset = p.below(i);
                }
                newShape.add(newOffset);
            }
        }
        return newShape;
    }
}
