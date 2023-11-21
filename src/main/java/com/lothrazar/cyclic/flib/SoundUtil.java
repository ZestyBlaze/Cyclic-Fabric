package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class SoundUtil {
    public static void playSound(Level world, BlockPos pos, SoundEvent soundIn, float volume) {
        world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), soundIn, SoundSource.BLOCKS, volume, 0.5F, false);
    }

    public static void playSound(Entity entityIn, SoundEvent soundIn) {
        playSound(entityIn, soundIn, 1.0F, 1.0F);
    }

    public static void playSound(Entity entityIn, SoundEvent soundIn, float volume, float pitch) {
        if (entityIn != null && entityIn.level().isClientSide) {
            entityIn.playSound(soundIn, volume, pitch);
        }
    }
}
