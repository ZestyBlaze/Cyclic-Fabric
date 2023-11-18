package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.capability.CyclicPlayer;
import com.lothrazar.cyclic.capability.ICyclicPlayer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class CapabilityRegistry implements EntityComponentInitializer {
    public static final ComponentKey<ICyclicPlayer> CYCLIC_PLAYER = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(ModCyclic.MODID, "cyclic_player"), ICyclicPlayer.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(CYCLIC_PLAYER, CyclicPlayer::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
