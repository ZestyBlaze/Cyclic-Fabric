package com.lothrazar.cyclic.potion;

import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CyclicMobEffect extends MobEffect {
    public CyclicMobEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    public void tick(LivingEvent.LivingTickEvent event) {}

    public void onRemoved(LivingEntity entity) {}
}
