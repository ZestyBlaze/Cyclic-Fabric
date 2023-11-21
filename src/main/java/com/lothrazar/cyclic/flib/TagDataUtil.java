package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class TagDataUtil {
    public static void setItemStackBlockPos(ItemStack item, BlockPos pos) {
        if (pos == null || item.isEmpty()) {
            return;
        }
        setItemStackNBTVal(item, "xpos", pos.getX());
        setItemStackNBTVal(item, "ypos", pos.getY());
        setItemStackNBTVal(item, "zpos", pos.getZ());
    }

    public static BlockPos getItemStackBlockPos(ItemStack item) {
        if (item.isEmpty() || item.getTag() == null || !item.getTag().contains("xpos")) {
            return null;
        }
        CompoundTag tag = item.getOrCreateTag();
        return getBlockPos(tag);
    }

    public static BlockPos getBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("xpos"), tag.getInt("ypos"), tag.getInt("zpos"));
    }

    public static void setItemStackNBTVal(ItemStack item, String prop, int value) {
        if (item.isEmpty()) {
            return;
        }
        item.getOrCreateTag().putInt(prop, value);
    }

    public static CompoundTag getItemStackNBT(ItemStack held) {
        return held.getOrCreateTag();
    }
}
