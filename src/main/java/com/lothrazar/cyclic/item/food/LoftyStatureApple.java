package com.lothrazar.cyclic.item.food;

import com.lothrazar.cyclic.capability.ICyclicPlayer;
import com.lothrazar.cyclic.flib.AttributesUtil;
import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import com.lothrazar.cyclic.registry.CapabilityRegistry;
import com.lothrazar.cyclic.registry.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class LoftyStatureApple extends ItemBaseCyclic {

    public LoftyStatureApple(Properties properties) {
        super(properties);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if(entityLiving instanceof Player player && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.getCooldowns().addCooldown(stack.getItem(), 40); // 2seconds
            if (!worldIn.isClientSide) {
                ICyclicPlayer datFile = player.getComponent(CapabilityRegistry.CYCLIC_PLAYER);
                datFile.toggleStepHeight();
                ChatUtil.addServerChatMessage(player, "cyclic.unlocks.stepheight." + datFile.getStepHeight());
            }
        }
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundRegistry.STEP_HEIGHT_UP;
    }

    public static void onUpdate(Player player) {
        if (player.getComponent(CapabilityRegistry.CYCLIC_PLAYER).getStepHeight()) {
            AttributesUtil.enableStepHeight(player);
        }
        else {
            AttributesUtil.disableStepHeight(player);
        }
    }
}
