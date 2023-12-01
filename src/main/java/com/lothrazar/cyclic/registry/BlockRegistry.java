package com.lothrazar.cyclic.registry;

import com.google.common.collect.Sets;
import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.block.BlockCyclic;
import com.lothrazar.cyclic.block.BlockSimple;
import com.lothrazar.cyclic.block.LavaSpongeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockRegistry {
    private static final CreativeModeTab ITEMS_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemRegistry.GEM_AMBER))
            .title(Component.translatable("itemGroup." + ModCyclic.MODID + "items"))
            .displayItems((enabledFlags, populator) -> {
                ItemRegistry.ITEM_LIST.forEach(populator::accept);
            })
            .build();

    private static final CreativeModeTab BLOCKS_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BlockRegistry.COMPRESSED_COBBLESTONE))
            .title(Component.translatable("itemGroup." + ModCyclic.MODID))
            .displayItems((enabledFlags, populator) -> {
                BlockRegistry.BLOCKS.forEach(block -> populator.accept(block.asItem()));
            })
            .build();

    public static List<BlockCyclic> BLOCKSCLIENTREGISTRY = new ArrayList<>();
    private static final ResourceKey<CreativeModeTab> TAB_ITEMS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModCyclic.MODID, "items"));
    private static final ResourceKey<CreativeModeTab> TAB_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModCyclic.MODID, "tab"));
    public static final Set<Block> BLOCKS = Sets.newLinkedHashSet();
    public static final Block COMPRESSED_COBBLESTONE = register("compressed_cobblestone", new BlockSimple(FabricBlockSettings.create().strength(1.0f, 5.0f)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {}
    });

    public static final Block SPONGE_LAVA = register("sponge_lava", new LavaSpongeBlock(FabricBlockSettings.create().sound(SoundType.SPORE_BLOSSOM).lightLevel(p -> 2)));

    private static Block register(String name, Block block) {
        Block returnBlock = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(ModCyclic.MODID, name), block);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ModCyclic.MODID, name), new BlockItem(returnBlock, new FabricItemSettings()));
        BLOCKS.add(returnBlock);
        return returnBlock;
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_ITEMS, ITEMS_TAB);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_BLOCKS, BLOCKS_TAB);
    }
}
