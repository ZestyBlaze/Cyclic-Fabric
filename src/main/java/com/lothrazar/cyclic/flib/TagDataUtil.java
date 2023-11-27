package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TagDataUtil {
    public static final String SKULLOWNER = "SkullOwner";

    public static ItemStack buildNamedPlayerSkull(Player player) {
        return buildNamedPlayerSkull(player.getDisplayName().getString());
    }

    public static ItemStack buildNamedPlayerSkull(String displayNameString) {
        CompoundTag t = new CompoundTag();
        t.putString(SKULLOWNER, displayNameString);
        return buildSkullFromTag(t);
    }

    public static ItemStack buildSkullFromTag(CompoundTag player) {
        ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
        skull.setTag(player);
        return skull;
    }

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
