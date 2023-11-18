package com.lothrazar.cyclic.flib;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemStackUtil {
    public static void repairItem(ItemStack s) {
        repairItem(s, 1);
    }

    public static void repairItem(ItemStack s, int amount) {
        s.setDamageValue(Math.max(0, s.getDamageValue() - amount));
    }

    public static void damageItem(LivingEntity player, ItemStack stack) {
        damageItem(player, stack, InteractionHand.MAIN_HAND);
    }

    public static void damageItem(LivingEntity player, ItemStack stack, InteractionHand hand) {
        if (!stack.isDamageableItem()) {
            //unbreakable
            return;
        }
        if (player == null) {
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
        else {
            stack.hurtAndBreak(1, player, (p) -> {
                p.broadcastBreakEvent(InteractionHand.MAIN_HAND);
            });
        }
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
            stack = ItemStack.EMPTY;
        }
    }
}
