package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.flib.EntityUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemHorseRedstoneSpeed extends ItemBaseCyclic {
    public static final int SPEED_MAX = 50;
    private static final double SPEED_AMT = 0.004;

    public ItemHorseRedstoneSpeed(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Horse ahorse && !player.level().isClientSide()) {
            double speed = ahorse.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
            double newSpeed = speed + SPEED_AMT;
            if (EntityUtil.getSpeedTranslated(newSpeed) < SPEED_MAX) {
                ahorse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newSpeed);
                stack.shrink(1);
                ahorse.eating();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
