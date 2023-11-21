package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StirrupsReverseItem extends ItemBaseCyclic {
    public StirrupsReverseItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        playerIn.swing(hand);
        if (playerIn.hasPassenger(target)) {
            target.removeVehicle();
            playerIn.removeVehicle();
            return InteractionResult.SUCCESS;
        }
        return target.startRiding(playerIn, true) ? InteractionResult.SUCCESS : super.interactLivingEntity(stack, playerIn, target, hand);
    }
}
