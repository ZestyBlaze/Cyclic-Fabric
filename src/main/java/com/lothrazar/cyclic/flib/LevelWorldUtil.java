package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class LevelWorldUtil {
    public static String dimensionToString(Level world) {
        //example: returns "minecraft:overworld" resource location
        return world.dimension().location().toString();
    }

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

    public static void toggleLeverPowerState(Level worldIn, BlockPos blockPos, BlockState blockState) {
        boolean hasPowerHere = blockState.getValue(LeverBlock.POWERED);
        BlockState stateNew = blockState.setValue(LeverBlock.POWERED, !hasPowerHere);
        boolean success = worldIn.setBlockAndUpdate(blockPos, stateNew);
        if (success) {
            flagUpdate(worldIn, blockPos, blockState, stateNew);
            flagUpdate(worldIn, blockPos.below(), blockState, stateNew);
            flagUpdate(worldIn, blockPos.above(), blockState, stateNew);
            flagUpdate(worldIn, blockPos.west(), blockState, stateNew);
            flagUpdate(worldIn, blockPos.east(), blockState, stateNew);
            flagUpdate(worldIn, blockPos.north(), blockState, stateNew);
            flagUpdate(worldIn, blockPos.south(), blockState, stateNew);
        }
    }

    public static void flagUpdate(Level worldIn, BlockPos blockPos, BlockState blockState, BlockState stateNew) {
        worldIn.sendBlockUpdated(blockPos, blockState, stateNew, 3);
        worldIn.updateNeighborsAt(blockPos, stateNew.getBlock());
        worldIn.updateNeighborsAt(blockPos, blockState.getBlock());
    }
}
