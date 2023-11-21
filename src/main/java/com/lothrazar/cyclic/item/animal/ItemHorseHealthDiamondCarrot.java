package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemHorseHealthDiamondCarrot extends ItemBaseCyclic {
    public static final int HEARTS_MAX = 40;

    public ItemHorseHealthDiamondCarrot(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Horse ahorse && !player.level().isClientSide()) {
            float mh = (float) ahorse.getAttribute(Attributes.MAX_HEALTH).getValue();
            if (mh < 2 * ItemHorseHealthDiamondCarrot.HEARTS_MAX) { // 20 hearts == 40 health points
                ahorse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(mh + 2);
                stack.shrink(1);
                ahorse.eating();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
