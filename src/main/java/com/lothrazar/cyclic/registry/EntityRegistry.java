package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.item.elemental.FireEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {
    public static final EntityType<FireEntity> FIRE_BOLT = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(ModCyclic.MODID, "fire_bolt"),
            FabricEntityTypeBuilder.<FireEntity>create(MobCategory.MISC, FireEntity::new)
                    .forceTrackedVelocityUpdates(true)
                    .trackedUpdateRate(1)
                    .trackRangeChunks(128)
                    .dimensions(EntityDimensions.scalable(0.6f, 0.6f))
                    .build()
    );

    public static void init() {}
}
