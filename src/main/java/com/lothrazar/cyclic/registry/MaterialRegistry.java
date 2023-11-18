package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.material.GlowingArmorMaterial;
import net.minecraft.world.item.ArmorMaterial;

public class MaterialRegistry {
    public static class ArmorMats {
        public static final ArmorMaterial GLOWING = new GlowingArmorMaterial();
    }

    public static void init() {};
}
