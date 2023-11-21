package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemStackUtil {
    public static void repairItem(ItemStack s, int amount) {
        s.setDamageValue(Math.max(0, s.getDamageValue() - amount));
    }

    public static void damageItem(LivingEntity player, ItemStack stack) {
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

    public static void drop(Level world, BlockPos pos, ItemStack drop) {
        if (!world.isClientSide) {
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), drop));
        }
    }
}
