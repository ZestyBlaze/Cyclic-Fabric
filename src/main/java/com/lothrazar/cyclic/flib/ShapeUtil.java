package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ShapeUtil {
    public static List<BlockPos> cubeSquareBase(final BlockPos pos, int radius, int height) {
        List<BlockPos> shape = new ArrayList<BlockPos>();
        // search in a cube
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
}
