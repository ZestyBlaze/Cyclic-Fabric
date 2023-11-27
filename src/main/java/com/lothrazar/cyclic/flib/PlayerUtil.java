package com.lothrazar.cyclic.flib;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlayerUtil {
    public static double getExpTotal(Player player) {
        //  validateExpPositive(player);
        int level = player.experienceLevel;
        // numeric reference:
        // http://minecraft.gamepedia.com/Experience#Leveling_up
        double totalExp = getXpForLevel(level);
        double progress = Math.round(player.getXpNeededForNextLevel() * player.experienceProgress);
        totalExp += (int) progress;
        return totalExp;
    }

    public static int getXpForLevel(int level) {
        // numeric reference:
        // http://minecraft.gamepedia.com/Experience#Leveling_up
        int totalExp = 0;
        if (level <= 15) {
            totalExp = level * level + 6 * level;
        }
        else if (level <= 30) {
            totalExp = (int) (2.5 * level * level - 40.5 * level + 360);
        }
        else {
            // level >= 31
            totalExp = (int) (4.5 * level * level - 162.5 * level + 2220);
        }
        return totalExp;
    }

    public static Item getItemArmorSlot(Player player, EquipmentSlot slot) {
        ItemStack inSlot = player.getInventory().armor.get(slot.getIndex());
        return (inSlot.isEmpty()) ? null : inSlot.getItem();
    }
}
