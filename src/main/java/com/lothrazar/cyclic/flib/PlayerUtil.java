package com.lothrazar.cyclic.flib;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlayerUtil {
    public static Item getItemArmorSlot(Player player, EquipmentSlot slot) {
        ItemStack inslot = player.getInventory().armor.get(slot.getIndex());
        //    ItemStack inslot = player.inventory.armorInventory[slot.getIndex()];
        Item item = (inslot.isEmpty()) ? null : inslot.getItem();
        return item;
    }
}
