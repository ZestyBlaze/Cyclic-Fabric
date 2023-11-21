package com.lothrazar.cyclic.block;

import com.lothrazar.cyclic.flib.EntityBlockFlib;
import com.lothrazar.cyclic.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BlockCyclic extends EntityBlockFlib {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    private boolean hasGui = false;
    private boolean hasFluidInteract = false;

    public BlockCyclic(Properties properties) {
        super(properties);
        BlockRegistry.BLOCKSCLIENTREGISTRY.add(this);
    }

    public static boolean never(BlockState bs, BlockGetter bg, BlockPos pos) {
        return false;
    }

    protected BlockCyclic setHasGui() {
        this.hasGui = true;
        return this;
    }

    protected BlockCyclic setHasFluidInteract() {
        this.hasFluidInteract = true;
        return this;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p, BlockState st) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction oldDir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Direction newDir = direction.rotate(oldDir);
            //still rotate on axis, if its valid
            if (newDir != Direction.UP && newDir != Direction.DOWN) {
                return state.setValue(BlockStateProperties.HORIZONTAL_FACING, newDir);
            }
        }
        if (state.hasProperty(BlockStateProperties.FACING)) {
            Direction oldDir = state.getValue(BlockStateProperties.FACING);
            Direction newDir = direction.rotate(oldDir);
            // rotate state on axis dir
            return state.setValue(BlockStateProperties.FACING, newDir);
        }
        // default doesnt do much
        BlockState newState = state.rotate(direction);
        return newState;
    }

    /**
     * Override per block for render-ers/screens/etc
     */
    public void registerClient() {}
}
