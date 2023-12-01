package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.flib.EnchantUtil;
import com.lothrazar.cyclic.flib.EnchantmentFlib;
import com.lothrazar.cyclic.flib.FakePlayerUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

import java.util.Collections;
import java.util.List;

public class GloomCurseEnchant extends EnchantmentFlib {
    public static final String ID = "curse";
    public static final double BASE_ACTIVATION_CHANCE = 0.1;
    public static final double BASE_APPLY_CHANCE = 0.3;
    public static final double MIN_EFFECTS = 1;
    public static final double MAX_EFFECTS = 3;
    public static final int EFFECT_DURATION = 20 * 5;
    public static BooleanValue CFG;

    public GloomCurseEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isEnabled() {
        return CFG.get();
    }

    @Override
    public int getMaxLevel() {
        return 1;
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
    public boolean canEnchant(ItemStack stack) {
        return isEnabled() && stack.getItem() instanceof ArmorItem;
    }

    @Override
    public void doPostHurt(LivingEntity user, Entity attacker, int level) {
        if (user.level().isClientSide || !(attacker instanceof LivingEntity)
                || FakePlayerUtil.isFakePlayer(attacker)) {
            //do nothing on clientside, server only
            //only trigger if attacker is alive and not a fakeplayer
            return;
        }
        LivingEntity livingAttacker = (LivingEntity) attacker;
        //only allow activation once
        int totalLevels = getCurrentArmorLevelSlot(user, EquipmentSlot.HEAD)
                + getCurrentArmorLevelSlot(user, EquipmentSlot.CHEST)
                + getCurrentArmorLevelSlot(user, EquipmentSlot.LEGS)
                + getCurrentArmorLevelSlot(user, EquipmentSlot.FEET);
        double adjustedActivationChance = BASE_ACTIVATION_CHANCE / totalLevels;
        //does it pass the chance
        if (adjustedActivationChance > user.level().random.nextDouble()) {
            List<MobEffect> negativeEffects = EnchantUtil.getNegativeEffects();
            Collections.shuffle(negativeEffects);
            int appliedEffects = 0;
            for (MobEffect effect : negativeEffects) {
                if (effect == null) {
                    continue;
                    //should be impossible, but i had a random NPE crash log
                }
                if (appliedEffects < MIN_EFFECTS
                        || BASE_APPLY_CHANCE > user.level().random.nextDouble()) {
                    //the OR means, if we are under minimum, always go thru
                    //if we are beyond minimum, check the random chance
                    livingAttacker.addEffect(new MobEffectInstance(effect, EFFECT_DURATION));
                    appliedEffects++;
                    if (appliedEffects >= MAX_EFFECTS) {
                        break;
                    }
                }
            }
        }
        super.doPostHurt(user, attacker, level);
    }
}
