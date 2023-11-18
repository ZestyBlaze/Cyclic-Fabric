package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundRegistry {
    public static final SoundEvent FIRELAUNCH = register("firelaunch");
    public static final SoundEvent LASERBEANPEW = register("laserbeanpew");
    public static final SoundEvent STEP_HEIGHT_DOWN = register("step_height_down");
    public static final SoundEvent STEP_HEIGHT_UP = register("step_height_up");
    public static final SoundEvent EQUIP_EMERALD = register("equip_emerald");

    private static SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(ModCyclic.MODID, name), SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCyclic.MODID, name)));
    }

    public static void init() {}
}
