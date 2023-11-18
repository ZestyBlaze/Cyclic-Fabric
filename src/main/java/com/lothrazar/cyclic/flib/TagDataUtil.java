package com.lothrazar.cyclic.flib;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class TagDataUtil {
    public static CompoundTag getItemStackNBT(ItemStack held) {
        return held.getOrCreateTag();
    }
}
