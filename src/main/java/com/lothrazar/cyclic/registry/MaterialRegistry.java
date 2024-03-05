package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.material.EmeraldArmorMaterial;
import com.lothrazar.cyclic.material.GlowingArmorMaterial;
import dev.zestyblaze.zestylib.tiers.SimpleTier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class MaterialRegistry {
    public static IntValue EMERALD_BOOTS;
    public static IntValue EMERALD_LEG;
    public static IntValue EMERALD_CHEST;
    public static IntValue EMERALD_HELM;
    public static IntValue OBS_BOOTS;
    public static IntValue OBS_LEG;
    public static IntValue OBS_CHEST;
    public static IntValue OBS_HELM;
    public static DoubleValue EMERALD_TOUGH;
    public static DoubleValue EMERALD_DMG;
    public static DoubleValue OBS_TOUGH;
    public static DoubleValue OBS_DMG;

    public static class ArmorMats {
        public static final ArmorMaterial EMERALD = new EmeraldArmorMaterial();
        public static final ArmorMaterial GLOWING = new GlowingArmorMaterial();
    }

    public static class ToolMats {
        public static final Tier AMETHYST = new SimpleTier(Tiers.IRON.getLevel(),
                Tiers.IRON.getUses() + 5, Tiers.IRON.getSpeed() + 0.2F, // uses aka durability
                Tiers.IRON.getAttackDamageBonus() + 0.1F, Tiers.GOLD.getEnchantmentValue() * 2,
                TagKey.create(Registries.BLOCK, new ResourceLocation(ModCyclic.MODID, "needs_amethyst_tool")),
                () -> Ingredient.of(Items.AMETHYST_SHARD)
        );
    }

    public static void init() {};
}
