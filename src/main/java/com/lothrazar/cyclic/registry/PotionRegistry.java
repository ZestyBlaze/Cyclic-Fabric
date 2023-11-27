package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class PotionRegistry {
    static final int normal = 3600;
    static final int smal = 1800;

    public static final Potion BLIND = register("blind", new Potion(ModCyclic.MODID + "_blind", new MobEffectInstance(MobEffects.BLINDNESS, normal)));
    public static final Potion BUTTERFINGERS = register("butter", new Potion(ModCyclic.MODID + "_butter", new MobEffectInstance(PotionEffectRegistry.BUTTERFINGERS, normal)));
    public static final Potion FLIGHT = register("flight", new Potion(ModCyclic.MODID + "_flight", new MobEffectInstance(PotionEffectRegistry.FLIGHT, normal)));
    public static final Potion HASTE = register("haste", new Potion(ModCyclic.MODID + "_haste", new MobEffectInstance(MobEffects.DIG_SPEED, normal)));
    public static final Potion HASTE_STRONG = register("strong_haste", new Potion(ModCyclic.MODID + "_strong_haste", new MobEffectInstance(MobEffects.DIG_SPEED, smal, 1)));
    public static final Potion HUNGER = register("hunger", new Potion(ModCyclic.MODID + "_hunger", new MobEffectInstance(MobEffects.HUNGER, normal)));
    public static final Potion HUNGER_STRONG = register("hunger_strong", new Potion(ModCyclic.MODID + "_hunger_strong", new MobEffectInstance(MobEffects.HUNGER, smal, 1)));
    public static final Potion LEVITATION = register("levitation", new Potion(ModCyclic.MODID + "_levitation", new MobEffectInstance(MobEffects.LEVITATION, smal)));
    public static final Potion MAGNETIC = register("magnetic", new Potion(ModCyclic.MODID + "_magnetic", new MobEffectInstance(PotionEffectRegistry.MAGNETIC, normal)));
    public static final Potion RESISTANCE = register("resistance", new Potion(ModCyclic.MODID + "_resistance", new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, smal)));
    public static final Potion STUN = register("stun", new Potion(ModCyclic.MODID + "_stun", new MobEffectInstance(PotionEffectRegistry.STUN, smal)));
    public static final Potion WATERWALK = register("waterwalk", new Potion(ModCyclic.MODID + "_waterwalk", new MobEffectInstance(PotionEffectRegistry.WATERWALK, smal)));
    public static final Potion WITHER = register("wither", new Potion(ModCyclic.MODID + "_wither", new MobEffectInstance(MobEffects.WITHER, smal)));

    public static Potion register(String name, Potion potion) {
        return Registry.register(BuiltInRegistries.POTION, new ResourceLocation(ModCyclic.MODID, name), potion);
    }

    public static class PotionRecipeConfig {
        public static BooleanValue ANTIGRAVITY;
        public static BooleanValue ATTACK_RANGE;
        public static BooleanValue BLIND;
        public static BooleanValue BUTTERFINGERS;
        public static BooleanValue FLIGHT;
        public static BooleanValue FROST_WALKER;
        public static BooleanValue GRAVITY;
        public static BooleanValue HASTE;
        public static BooleanValue HUNGER;
        public static BooleanValue LEVITATION;
        public static BooleanValue MAGNETIC;
        public static BooleanValue REACH_DISTANCE;
        public static BooleanValue RESISTANCE;
        public static BooleanValue STUN;
        public static BooleanValue SWIMSPEED;
        public static BooleanValue SNOWWALK;
        public static BooleanValue WATERWALK;
        public static BooleanValue WITHER;
    }

    public static void init() {
        if (PotionRecipeConfig.HASTE.get()) {
            basicBrewing(Potions.AWKWARD, Items.EMERALD, PotionRegistry.HASTE);
            basicBrewing(PotionRegistry.HASTE, Items.REDSTONE, PotionRegistry.HASTE_STRONG);
        }
        if (PotionRecipeConfig.STUN.get()) {
            basicBrewing(Potions.AWKWARD, Items.CLAY, PotionRegistry.STUN);
        }
        if (PotionRecipeConfig.BLIND.get()) {
            basicBrewing(Potions.NIGHT_VISION, Items.BEETROOT, PotionRegistry.BLIND);
        }
        if (PotionRecipeConfig.LEVITATION.get()) {
            basicBrewing(Potions.SLOW_FALLING, Items.FERMENTED_SPIDER_EYE, PotionRegistry.LEVITATION);
        }
        if (PotionRecipeConfig.RESISTANCE.get()) {
            basicBrewing(Potions.STRENGTH, Items.IRON_INGOT, PotionRegistry.RESISTANCE);
        }
        if (PotionRecipeConfig.WITHER.get()) {
            basicBrewing(Potions.WEAKNESS, Items.NETHER_BRICK, PotionRegistry.WITHER);
        }
        if (PotionRecipeConfig.HUNGER.get()) {
            basicBrewing(Potions.THICK, Items.ROTTEN_FLESH, PotionRegistry.HUNGER);
            basicBrewing(PotionRegistry.HUNGER, Items.REDSTONE, PotionRegistry.HUNGER_STRONG);
        }
        if (PotionRecipeConfig.WATERWALK.get()) {
            basicBrewing(Potions.AWKWARD, Items.PRISMARINE_SHARD, PotionRegistry.WATERWALK);
            basicBrewing(Potions.THICK, Items.COD, PotionRegistry.WATERWALK);
        }
        if (PotionRecipeConfig.BUTTERFINGERS.get()) {
            basicBrewing(Potions.AWKWARD, Items.GOLD_INGOT, PotionRegistry.BUTTERFINGERS);
        }
        if (PotionRecipeConfig.MAGNETIC.get()) {
            basicBrewing(Potions.AWKWARD, Items.LAPIS_LAZULI, PotionRegistry.MAGNETIC);
        }
        if (PotionRecipeConfig.FLIGHT.get()) {
            basicBrewing(Potions.STRONG_HEALING, Items.CHORUS_FRUIT, PotionRegistry.FLIGHT);
        }
    }

    private static void basicBrewing(Potion inputPot, Item ingredient, Potion outputPot) {
        PotionBrewing.addMix(inputPot, ingredient, outputPot);
    }
}
