package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.flib.ItemStackUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemHorseToxic extends ItemBaseCyclic {
    public ItemHorseToxic(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Horse horseOldEntity && !player.level().isClientSide() && !player.getCooldowns().isOnCooldown(this)) {
            ZombieHorse zombieNewEntity = EntityType.ZOMBIE_HORSE.spawn((ServerLevel) player.level(), (ItemStack) null, null, interactionTarget.blockPosition(), MobSpawnType.NATURAL, false, false);
            if (horseOldEntity.isTamed() && horseOldEntity.getOwnerUUID() == player.getUUID()) {
                zombieNewEntity.tameWithName(player);
            }
            if (horseOldEntity.isSaddled()) {
                zombieNewEntity.equipSaddle(SoundSource.PLAYERS);
            }
            SimpleContainer horseChest = horseOldEntity.inventory;
            if (horseChest != null && horseChest.getContainerSize() >= 2) {
                ItemStackUtil.drop(player.level(), interactionTarget.blockPosition(), horseChest.getItem(1));
            }

            if (horseOldEntity.hasCustomName()) {
                zombieNewEntity.setCustomName(horseOldEntity.getCustomName());
            }
            //remove the horse
            horseOldEntity.remove(Entity.RemovalReason.DISCARDED);
            player.getCooldowns().addCooldown(this, 10);
            stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
