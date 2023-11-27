package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.flib.EnchantmentFlib;
import com.lothrazar.cyclic.flib.PlayerUtil;
import com.lothrazar.cyclic.flib.SoundUtil;
import com.lothrazar.cyclic.registry.SoundRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class LastStandEnchant extends EnchantmentFlib {
    public static final String ID = "laststand";
    public static BooleanValue CFG;
    public static IntValue COST;
    public static IntValue ABS;
    public static IntValue COOLDOWN;

    public LastStandEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        onEntityUpdate();
    }

    @Override
    public boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench)
                //&& ench != EnchantRegistry.LAUNCH.get()
                //&& ench != EnchantRegistry.EXPERIENCE_BOOST.get()
                && ench != Enchantments.MENDING
                && ench != Enchantments.THORNS;
    }

    @Override
    public boolean isTradeable() {
        return isEnabled() && super.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled() && super.isDiscoverable();
    }

    /*
    @Override
    public boolean isAllowedOnBooks() {
        return isEnabled() && super.isAllowedOnBooks();
    }
     */

    @Override
    public boolean canEnchant(ItemStack stack) {
        return isEnabled() && super.canEnchant(stack);
    }

    /*
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return isEnabled() && super.canApplyAtEnchantingTable(stack);
    }
     */

    @Override
    public boolean isEnabled() {
        return CFG.get();
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public void onEntityUpdate() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (!isEnabled()) {
                return true;
            }
            final int level = getCurrentArmorLevelSlot(entity, EquipmentSlot.LEGS);
            if (level > 0 && entity instanceof ServerPlayer player) {
                //if enchanted, and it would cause death, then we go on
                if (COOLDOWN.get() > 0 &&
                        player.getCooldowns().isOnCooldown(player.getItemBySlot(EquipmentSlot.LEGS).getItem())) {
                    return true; //if equippped enchanted item is on cooldown for any reason, done
                }
                final int xpCost = Math.max(1, COST.get() / level); // min 1.  higher level gives a lower cost. level 1 is 30xp, lvl 3 is 10xp etc
                if (PlayerUtil.getExpTotal(player) < xpCost) {
                    return true; // POOR
                }
                //survive
                player.setHealth(1);
                player.giveExperiencePoints(-1 * xpCost);
                //now the fluff
                SoundUtil.playSoundFromServer(player, SoundRegistry.CHAOS_REAPER, 1F, 0.4F);
                ChatUtil.sendStatusMessage(player, "enchantment." + ModCyclic.MODID + "." + ID + ".activated");
                if (ABS.get() > 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, ABS.get(), level - 1));
                }
                if (COOLDOWN.get() > 0) {
                    player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.LEGS).getItem(), COOLDOWN.get());
                }
                return false;
            }
            return true;
        });
    }
}
