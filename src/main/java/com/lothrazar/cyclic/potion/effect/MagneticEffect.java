package com.lothrazar.cyclic.potion.effect;

import com.lothrazar.cyclic.flib.EntityUtil;
import com.lothrazar.cyclic.potion.CyclicMobEffect;
import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MagneticEffect extends CyclicMobEffect {
    public MagneticEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        int amp = living.getEffect(this).getAmplifier();
        EntityUtil.moveEntityItemsInRegion(living.level(), living.blockPosition(), 8 * amp, 1 + amp);
    }
}
