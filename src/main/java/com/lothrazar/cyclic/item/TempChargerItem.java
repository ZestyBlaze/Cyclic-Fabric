package com.lothrazar.cyclic.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TempChargerItem extends Item {
    public TempChargerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack laserStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (laserStack != null && laserStack.getItem() instanceof LaserItem laserItem) {
            laserItem.setStoredEnergy(laserStack, ItemBaseCyclic.MAX_ENERGY);
            InteractionResultHolder.success(laserStack);
        }
        return InteractionResultHolder.fail(laserStack);
    }
}
