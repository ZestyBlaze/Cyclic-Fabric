package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.flib.EnchantmentFlib;
import com.lothrazar.cyclic.flib.EntityUtil;
import com.lothrazar.cyclic.flib.SoundUtil;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class EnderPearlEnchant extends EnchantmentFlib {
    public static final int COOLDOWN = 6 * 20;
    private static final float VELOCITY = 1.5F; //Same as EnderPearlItem
    private static final float INNACCURACY = 1F; //Same as EnderPearlItem
    public static final String ID = "ender";
    public static BooleanValue CFG;

    public EnderPearlEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        onRightClickItem();
    }

    @Override
    public boolean isEnabled() {
        return CFG.get();
    }

    @Override
    public boolean isTradeable() {
        return isEnabled() && super.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled() && super.isDiscoverable();
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return isEnabled() && stack.getItem() instanceof SwordItem;
    }

    public void onRightClickItem() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!isEnabled()) {
                return InteractionResultHolder.pass(player.getItemInHand(hand));
            }

            if (!world.isClientSide()) {
                int level = EnchantmentHelper.getItemEnchantmentLevel(this, player.getItemInHand(hand));
                if (level > 0) {
                    int adjustedCooldown = COOLDOWN / level;
                    if (player.getCooldowns().isOnCooldown(player.getItemInHand(hand).getItem())) {
                        return InteractionResultHolder.pass(player.getItemInHand(hand));
                    }
                    ThrownEnderpearl pearl = new ThrownEnderpearl(world, player);
                    Vec3 lookVector = player.getLookAngle();
                    pearl.shoot(lookVector.x(), lookVector.y(), lookVector.z(), VELOCITY, INNACCURACY);
                    EntityUtil.setCooldownItem(player, player.getItemInHand(hand).getItem(), adjustedCooldown);
                    SoundUtil.playSound(player, SoundEvents.ENDER_PEARL_THROW, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
                    world.addFreshEntity(pearl);
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });
    }
}
