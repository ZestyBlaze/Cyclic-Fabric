package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;

public class LevelWorldUtil {
    public static Direction getRandomDirection(RandomSource rand) {
        int index = Mth.nextInt(rand, 0, Direction.values().length - 1);
        return Direction.values()[index];
    }

    public static String dimensionToString(Level world) {
        //example: returns "minecraft:overworld" resource location
        return world.dimension().location().toString();
        //RegistryKey.create(Registry.WORLD_KEY, new ResourceLocation("twilightforest", "twilightforest"));
    }

    public static ResourceKey<Level> stringToDimension(String key) {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(key));
    }

    public static double distanceBetweenHorizontal(BlockPos start, BlockPos end) {
        int xDistance = Math.abs(start.getX() - end.getX());
        int zDistance = Math.abs(start.getZ() - end.getZ());
        // ye olde pythagoras
        return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
    }

    public static BlockPos nextReplaceableInDirection(Level world, BlockPos posIn, Direction facing, int max, Block blockMatch) {
        BlockPos posToPlaceAt = new BlockPos(posIn);
        BlockPos posLoop = new BlockPos(posIn);
        //    world.getBlockState(posLoop).getBlock().isReplaceable(state, useContext)
        for (int i = 0; i < max; i++) {
            BlockState state = world.getBlockState(posLoop);
            if (state.getBlock() != null
                    && world.getBlockState(posLoop).isAir()) {
                posToPlaceAt = posLoop;
                break;
            }
            else {
                posLoop = posLoop.relative(facing);
            }
        }
        return posToPlaceAt;
    }

    /**
     * Check if empty, then if not drop the stack safely in a new ItemEntity
     */
    public static ItemEntity dropItemStackInWorld(Level world, BlockPos pos, ItemStack stack) {
        if (pos == null || world == null || stack.isEmpty()) {
            return null;
        }
        ItemEntity entityItem = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
        if (world.isClientSide == false) {
            world.addFreshEntity(entityItem);
        }
        return entityItem;
    }

    public static void dropItemStackRandomMotion(Level world, BlockPos pos, ItemStack itemStack, float motion) {
        ItemEntity entityitem = new ItemEntity(world,
                pos.getX() + world.random.nextFloat() * 0.8F + 0.1F,
                pos.getY() + world.random.nextFloat() * 0.8F + 0.1F,
                pos.getZ() + world.random.nextFloat() * 0.8F + 0.1F, itemStack);
        float motionX = (float) world.random.nextGaussian() * motion;
        float motionY = (float) world.random.nextGaussian() * motion + 0.2F;
        float motionZ = (float) world.random.nextGaussian() * motion;
        entityitem.setDeltaMovement(motionX, motionY, motionZ);
        world.addFreshEntity(entityitem);
    }

    public static boolean doesBlockExist(Level world, BlockPos start, BlockState blockHunt, final int radius, final int height) {
        int xMin = start.getX() - radius;
        int xMax = start.getX() + radius;
        int yMin = start.getY();
        int yMax = start.getY() + height;
        int zMin = start.getZ() - radius;
        int zMax = start.getZ() + radius;
        BlockPos posCurrent = null;
        for (int xLoop = xMin; xLoop <= xMax; xLoop++) {
            for (int yLoop = yMin; yLoop <= yMax; yLoop++) {
                for (int zLoop = zMin; zLoop <= zMax; zLoop++) {
                    posCurrent = new BlockPos(xLoop, yLoop, zLoop);
                    if (world.getBlockState(posCurrent) == blockHunt) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public static ArrayList<BlockPos> findBlocks(Level world, BlockPos start, Block blockHunt, final int radius) {
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
                    if (world.getBlockState(posCurrent).getBlock().equals(blockHunt)) {
                        found.add(posCurrent);
                    }
                }
            }
        }
        return found;
    }

    public static void toggleLeverPowerState(Level worldIn, BlockPos blockPos, BlockState blockState) {
        boolean hasPowerHere = blockState.getValue(LeverBlock.POWERED).booleanValue();
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

    public static List<BlockPos> getPositionsInRange(BlockPos pos, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        List<BlockPos> found = new ArrayList<BlockPos>();
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                for (int z = zMin; z <= zMax; z++) {
                    found.add(new BlockPos(x, y, z));
                }
            }
        }
        return found;
    }

    public static boolean doBlockStatesMatch(BlockState replacedBlockState, BlockState newToPlace) {
        //    replacedBlockState.eq?
        return replacedBlockState.equals(newToPlace);
    }

    public static BlockPos getFirstBlockAbove(Level world, BlockPos pos) {
        //similar to vanilla fn getTopSolidOrLiquidBlock
        BlockPos posCurrent = null;
        for (int y = pos.getY() + 1; y < world.getMaxBuildHeight(); y++) {
            posCurrent = new BlockPos(pos.getX(), y, pos.getZ());
            if (world.getBlockState(posCurrent).isAir() &&
                    world.getBlockState(posCurrent.above()).isAir() &&
                    !world.getBlockState(posCurrent.below()).isAir()) {
                return posCurrent;
            }
        }
        return null;
    }

    public static BlockPos getLastAirBlockAbove(Level world, BlockPos pos) {
        //keep going up until you hit something that isn't air, then return the last air block
        return getLastAirBlock(world, pos, Direction.UP);
    }

    public static BlockPos getLastAirBlockBelow(Level world, BlockPos pos) {
        return getLastAirBlock(world, pos, Direction.DOWN);
    }

    public static BlockPos getLastAirBlock(Level world, BlockPos pos, Direction direction) {
        int increment;
        if (direction == Direction.DOWN) {
            increment = -1;
        }
        else {
            increment = 1;
        }
        BlockPos posCurrent;
        BlockPos posPrevious = pos;
        for (int y = pos.getY(); y < world.getMaxBuildHeight() && y > 0; y += increment) {
            posCurrent = new BlockPos(pos.getX(), y, pos.getZ());
            if (!world.isEmptyBlock(posCurrent)) {
                return posPrevious;
            }
            posPrevious = posCurrent;
        }
        return pos;
    }

    public static boolean dimensionIsEqual(BlockPosDim targetPos, Level world) {
        if (targetPos == null || targetPos.getDimension() == null) {
            return false;
        }
        return targetPos.getDimension().equalsIgnoreCase(LevelWorldUtil.dimensionToString(world));
    }

    public static BlockPos returnClosest(BlockPos playerPos, BlockPos pos1, BlockPos pos2) {
        if (pos1 == null && pos2 == null) {
            return null;
        }
        else if (pos1 == null) {
            return pos2;
        }
        else if (pos2 == null) {
            return pos1;
        }
        else if (LevelWorldUtil.distanceBetweenHorizontal(playerPos, pos1) <= LevelWorldUtil.distanceBetweenHorizontal(playerPos, pos2)) {
            return pos1;
        }
        return pos2;
    }

    public static boolean withinArea(BlockPos center, int radius, int height, BlockPos candle) {
        //    from center of box, go up and down height, and around in square radius, return true if candle is in region
        if (center.getX() - radius < candle.getX() && candle.getX() < center.getX() + radius
                && center.getY() - height < candle.getY() && candle.getY() < center.getY() + height
                && center.getY() - radius < candle.getY() && candle.getY() < center.getY() + radius) {
            return true;
        }
        return false;
    }
}
