package com.lothrazar.cyclic.potion.effect;

import com.lothrazar.cyclic.potion.CyclicMobEffect;
import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;

public class FrostEffect extends CyclicMobEffect {
    public FrostEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void tick(LivingEvent.LivingTickEvent event) {
        // delete me i guess
        LivingEntity living = event.getEntity();
        int amp = living.getEffect(this).getAmplifier();
        FrostWalkerEnchantment.onEntityMoved(living, living.level(), living.blockPosition(), amp);
    }
}
