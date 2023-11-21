package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemHorseLapisVariant extends ItemBaseCyclic {
    public ItemHorseLapisVariant(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Horse ahorse && !player.level().isClientSide()) {
            int seed = player.level().random.nextInt(7);
            ahorse.getEntityData().set(Horse.DATA_ID_TYPE_VARIANT, (seed | player.level().random.nextInt(5) << 8));
            player.getCooldowns().addCooldown(this, 10);
            stack.shrink(1);
            ahorse.eating();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
