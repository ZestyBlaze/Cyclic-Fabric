package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class PotionRegistry {
    static final int normal = 3600;
    static final int smal = 1800;

    public static final Potion MAGNETIC = register("magnetic", new Potion(ModCyclic.MODID + "_magnetic", new MobEffectInstance(PotionEffectRegistry.MAGNETIC, normal)));

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
        if (PotionRecipeConfig.MAGNETIC.get()) {
            basicBrewing(Potions.AWKWARD, Items.LAPIS_LAZULI ,PotionRegistry.MAGNETIC);
        }
    }

    private static void basicBrewing(Potion inputPot, Item ingredient, Potion outputPot) {
        PotionBrewing.addMix(inputPot, ingredient, outputPot);
    }
}
