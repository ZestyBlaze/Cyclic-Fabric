package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.material.EmeraldArmorMaterial;
import com.lothrazar.cyclic.material.GlowingArmorMaterial;
import net.minecraft.world.item.ArmorMaterial;
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

    public static void init() {};
}
