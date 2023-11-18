package com.lothrazar.cyclic.flib;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ArmorTickingItem {
    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param stack The stack
     * @param level The level object
     * @param player The player wearing the armor
     */
    void onArmorTick(ItemStack stack, Level level, Player player);
}
