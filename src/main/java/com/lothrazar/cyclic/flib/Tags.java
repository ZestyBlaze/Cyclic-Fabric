package com.lothrazar.cyclic.flib;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Tags {
    public static class Blocks {
        public static final TagKey<Block> ORES = TagKey.create(Registries.BLOCK, new ResourceLocation("c", "ores"));
    }
}
