package com.lothrazar.cyclic.item.food;

import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AppleBuffs extends ItemBaseCyclic {

    public AppleBuffs(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (this.getFoodProperties() != null && this.getFoodProperties().getEffects() != null) {
            List<Pair<MobEffectInstance, Float>> eff = this.getFoodProperties().getEffects();
            for (Pair<MobEffectInstance, Float> entry : eff) {
                MobEffectInstance effCurrent = entry.getFirst();
                if (effCurrent == null || effCurrent.getEffect() == null) {
                    continue;
                }
                MutableComponent t = Component.translatable(effCurrent.getEffect().getDescriptionId());
                t.append(" " + ChatUtil.lang("potion.potency." + effCurrent.getAmplifier()));
                t.withStyle(ChatFormatting.DARK_GRAY);
                tooltip.add(t);
            }
        }
        //    super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }
}
