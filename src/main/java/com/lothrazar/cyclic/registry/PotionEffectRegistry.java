package com.lothrazar.cyclic.registry;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.potion.CyclicMobEffect;
import com.lothrazar.cyclic.potion.effect.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PotionEffectRegistry {
    public static final MobEffect STUN = register("stun", new StunEffect(MobEffectCategory.HARMFUL, 0xcccc00));
    public static final MobEffect WATERWALK = register("waterwalk", new WaterwalkEffect(MobEffectCategory.NEUTRAL, 0x221061));
    public static final MobEffect SNOWWALK = register("snowwalk", new SnowwalkEffect(MobEffectCategory.NEUTRAL, 0xf0ecdf));
    public static final MobEffect ATTACK_RANGE = register("attack_range", new CyclicMobEffect(MobEffectCategory.BENEFICIAL, 0x35db77)
            .addAttributeModifier(ReachEntityAttributes.ATTACK_RANGE, "5207DE5E-7CE8-4030-940E-514C1F160890", 2, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final MobEffect REACH_DISTANCE = register("reach_distance", new CyclicMobEffect(MobEffectCategory.BENEFICIAL, 0x500980)
            .addAttributeModifier(ReachEntityAttributes.REACH, "5207DE5E-7CE8-4030-940E-514C1F160890", 2, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final MobEffect BUTTERFINGERS = register("butter", new ButterEffect(MobEffectCategory.HARMFUL, 0xe5e500));
    public static final MobEffect FROST_WALKER = register("frost_walker", new FrostEffect(MobEffectCategory.BENEFICIAL, 0x42f4d7));
    public static final MobEffect MAGNETIC = register("magnetic", new MagneticEffect(MobEffectCategory.NEUTRAL, 0x224BAF));
    public static final MobEffect FLIGHT = register("flight", new FlightMayflyEffect(MobEffectCategory.BENEFICIAL, 0xF24BAF));

    public static MobEffect register(String name, MobEffect effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(ModCyclic.MODID, name), effect);
    }

    public static void init() {}
}
