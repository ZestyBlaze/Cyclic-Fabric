package com.lothrazar.cyclic.potion.effect;

import com.lothrazar.cyclic.flib.EntityUtil;
import com.lothrazar.cyclic.potion.CyclicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MagneticEffect extends CyclicMobEffect {
    public MagneticEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void tick(LivingEntity living) {
        int amp = living.getEffect(this).getAmplifier();
        EntityUtil.moveEntityItemsInRegion(living.level(), living.blockPosition(), 8 * amp, 1 + amp);
    }
}
