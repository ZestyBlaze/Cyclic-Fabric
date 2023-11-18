package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;

public class LevelWorldUtil {
    public static double distanceBetweenHorizontal(BlockPos start, BlockPos end) {
        int xDistance = Math.abs(start.getX() - end.getX());
        int zDistance = Math.abs(start.getZ() - end.getZ());
        // ye olde pythagoras
        return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
    }

    public static ArrayList<BlockPos> findBlocksByTag(Level world, BlockPos start, TagKey<Block> blockHunt, final int radius) {
        ArrayList<BlockPos> found = new ArrayList<BlockPos>();
        int xMin = start.getX() - radius;
        int xMax = start.getX() + radius;
        int yMin = start.getY() - radius;
        int yMax = start.getY() + radius;
        int zMin = start.getZ() - radius;
        int zMax = start.getZ() + radius;
        BlockPos posCurrent = null;
        for (int xLoop = xMin; xLoop <= xMax; xLoop++) {
            for (int yLoop = yMin; yLoop <= yMax; yLoop++) {
                for (int zLoop = zMin; zLoop <= zMax; zLoop++) {
                    posCurrent = new BlockPos(xLoop, yLoop, zLoop);
                    if (world.getBlockState(posCurrent).is(blockHunt)) {
                        found.add(posCurrent);
                    }
                }
            }
        }
        return found;
    }
}
