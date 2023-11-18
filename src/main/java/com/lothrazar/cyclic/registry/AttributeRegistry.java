package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AttributeRegistry {
    public static final Attribute STEP_HEIGHT = new RangedAttribute("generic.step-height-entity-attributes.step-height", 0.0f, -1024.0F, 1024.0F).setSyncable(true);

    public static void register() {
        Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(ModCyclic.MODID, "step_height"), STEP_HEIGHT);
    }

    public static float getStepHeight(float baseStepHeight, LivingEntity entity) {
        return baseStepHeight + (float) entity.getAttribute(STEP_HEIGHT).getValue();
    }
}
