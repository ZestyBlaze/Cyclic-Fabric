package com.lothrazar.cyclic.item.food;

import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Iterator;

public class AppleChocolate extends ItemBaseCyclic {
    public AppleChocolate(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        curePotionEffects(entityLiving);
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    private void curePotionEffects(LivingEntity entityLiving) {
        Iterator<MobEffectInstance> itr = entityLiving.getActiveEffectsMap().values().iterator();
        while (itr.hasNext()) {
            MobEffectInstance effect = itr.next();
            if (!effect.getEffect().isBeneficial()) {
                effect.getEffect().removeAttributeModifiers(entityLiving.getAttributes());
                itr.remove();
                entityLiving.effectsDirty = true;
                if (entityLiving instanceof Player player) {
                    player.getCooldowns().addCooldown(this, 30);
                }
            }
        }
    }
}
