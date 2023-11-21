package com.lothrazar.cyclic;

import com.lothrazar.cyclic.block.BlockCyclic;
import com.lothrazar.cyclic.event.EventRender;
import com.lothrazar.cyclic.registry.BlockRegistry;
import com.lothrazar.cyclic.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ModCyclicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.FIRE_BOLT, ThrownItemRenderer::new);
        EventRender.onRenderWorldLast();
        setupClient();
    }

    private void setupClient() {
        for (BlockCyclic b : BlockRegistry.BLOCKSCLIENTREGISTRY) {
            b.registerClient();
        }
    }
}
