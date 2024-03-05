package com.lothrazar.cyclic.item.food;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.flib.LevelWorldUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.*;

public class EnderApple extends ItemBaseCyclic {
    public static ConfigValue<List<? extends String>> STRUCTURE_TAGS;
    public static IntValue PRINTED;
    private static final int COOLDOWN = 60;

    public EnderApple(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    //does pos exist in biome
    public Pair<BlockPos, Holder<Structure>> findNearestPair(ServerLevel sl, TagKey<Structure> p_215012_, BlockPos p_215013_, int p_215014_, boolean p_215015_) {
        //getLevelSettings() -> worldGenOptions
        if (!sl.getServer().getWorldData().worldGenOptions().generateStructures()) {
            return null;
        }
        else {
            Optional<HolderSet.Named<Structure>> optional = sl.registryAccess().registryOrThrow(Registries.STRUCTURE).getTag(p_215012_);
            if (optional.isEmpty()) {
                return null;
            }
            else {
                Pair<BlockPos, Holder<Structure>> pair = sl.getChunkSource().getGenerator().findNearestMapStructure(sl, optional.get(), p_215013_, p_215014_, p_215015_);
                return pair;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player player)) {
            return super.finishUsingItem(stack, worldIn, entityLiving);
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return super.finishUsingItem(stack, worldIn, entityLiving);
        }
        player.getCooldowns().addCooldown(this, COOLDOWN);
        if (worldIn instanceof ServerLevel serverlevel) {
            Map<String, Integer> distanceStructNames = new HashMap<>();
            try {
                final List<String> structList = (List<String>) STRUCTURE_TAGS.get();
                for (String conf : structList) {
                    //EXAMPLE    test = StructureTags.EYE_OF_ENDER_LOCATED;
                    Pair<BlockPos, Holder<Structure>> blockpos = findNearestPair(serverlevel,
                            TagKey.create(Registries.STRUCTURE, new ResourceLocation(conf)),
                            entityLiving.blockPosition(), 100, false);
                    if (blockpos != null) {
                        //add to ze frekni map yo
                        double distance = LevelWorldUtil.distanceBetweenHorizontal(blockpos.getFirst(), entityLiving.blockPosition());
                        distanceStructNames.put(blockpos.getSecond().value().toString(), (int) distance);
                    }
                }
            }
            catch (Exception e) {
                ModCyclic.LOGGER.error("Apple structure?", e);
            }
            if (distanceStructNames.isEmpty()) {
                ChatUtil.addServerChatMessage(player, "item.cyclic.apple_ender.empty");
            }
            else {
                //
                //SORT
                LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
                distanceStructNames.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
                //
                //      ModCyclic.LOGGER.info("Sorted Map   : " + sortedMap);
                int count = 0;
                for (Map.Entry<String, Integer> e : sortedMap.entrySet()) {
                    ChatUtil.addServerChatMessage(player, e.getValue() + "m | " + e.getKey());
                    count++;
                    //?? is it sorted
                    if (count >= PRINTED.get()) {
                        break;
                    }
                }
            }
        }
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }
}
