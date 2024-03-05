package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AttributeRegistry {
    public static final Attribute STEP_HEIGHT = register("step_height", new RangedAttribute("generic.cyclic.step-height", 0.0F, -1024.0F, 1024.0F).setSyncable(true));

    public static float getStepHeight(float baseStepHeight, LivingEntity entity) {
        return baseStepHeight + (float) entity.getAttribute(STEP_HEIGHT).getValue();
    }

    public static Attribute register(String name, Attribute attribute) {
        return Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(ModCyclic.MODID), attribute);
    }

    public static void init() {}
}
