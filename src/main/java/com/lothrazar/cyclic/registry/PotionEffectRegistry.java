package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.potion.effect.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class PotionEffectRegistry {
    public static final MobEffect STUN = register("stun", new StunEffect(MobEffectCategory.HARMFUL, 0xcccc00));
    public static final MobEffect WATERWALK = register("waterwalk", new WaterwalkEffect(MobEffectCategory.NEUTRAL, 0x221061));
    public static final MobEffect BUTTERFINGERS = register("butter", new ButterEffect(MobEffectCategory.HARMFUL, 0xe5e500));
    public static final MobEffect MAGNETIC = register("magnetic", new MagneticEffect(MobEffectCategory.NEUTRAL, 0x224BAF));
    public static final MobEffect FLIGHT = register("flight", new PotionMayflyEffect(MobEffectCategory.BENEFICIAL, 0xF24BAF));

    public static MobEffect register(String name, MobEffect effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(ModCyclic.MODID, name), effect);
    }

    public static void init() {}
}
