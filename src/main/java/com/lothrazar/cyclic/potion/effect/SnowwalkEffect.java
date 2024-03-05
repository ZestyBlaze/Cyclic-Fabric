package com.lothrazar.cyclic.potion.effect;

import com.lothrazar.cyclic.potion.CyclicMobEffect;
import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowwalkEffect extends CyclicMobEffect {
    public SnowwalkEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void tick(LivingEvent.LivingTickEvent event) {
        // delete me i guess
        LivingEntity living = event.getEntity();
        Level level = living.level();
        BlockPos blockpos = living.blockPosition();
        BlockState blockstate = Blocks.SNOW.defaultBlockState();
        living.getEffect(this).getAmplifier(); // TODO: radius?
        if (level.isEmptyBlock(blockpos) && blockstate.canSurvive(level, blockpos)) {
            //world.getBlockState(blockpos).is(Blocks.AIR)) {
            //is air
            level.setBlockAndUpdate(blockpos, blockstate);
        }
    }
}
