package com.lothrazar.cyclic.block;

import com.lothrazar.cyclic.flib.ShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;

public class LavaSpongeBlock extends BlockCyclic {

    public static IntValue RADIUS;

    public LavaSpongeBlock(Properties properties) {
        super(properties.randomTicks().strength(0.7f).sound(SoundType.GRASS));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock())) {
            this.tryAbsorbLava(level, pos);
        }
    }

    public void tryAbsorbLava(Level level, BlockPos pos) {
        int r = RADIUS.get();
        List<BlockPos> around = ShapeUtil.cubeSquareBase(pos.below(r / 2), r, r);
        final int max = level.random.nextInt(10) + around.size() / 3;
        int current = 0;
        for (BlockPos posSide : around) {
            if (current > max) {
                break;
            }
            if (posSide.equals(pos)) {
                continue;
            }
            BlockState blockHere = level.getBlockState(posSide);
            FluidState fluidHere = blockHere.getFluidState();
            if (Fluids.LAVA == fluidHere.getType()) { // this check does source only, not flowing
                boolean success = level.setBlock(posSide, Blocks.AIR.defaultBlockState(), 3);
                if (success) {
                    current++;
                }
            }
        }
    }
}
