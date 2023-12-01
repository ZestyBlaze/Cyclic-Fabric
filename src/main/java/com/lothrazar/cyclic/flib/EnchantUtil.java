package com.lothrazar.cyclic.flib;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnchantUtil {
    public static List<MobEffect> getNegativeEffects() {
        return getEffects(MobEffectCategory.HARMFUL);
    }

    public static List<MobEffect> getEffects(MobEffectCategory effectType) {
        Collection<MobEffect> effects = BuiltInRegistries.MOB_EFFECT.stream().toList();
        List<MobEffect> effectsList = new ArrayList<>();
        for (MobEffect effect : effects) {
            if (effectType == null || effect.getCategory() == effectType) {
                effectsList.add(effect);
            }
        }
        return effectsList;
    }
}
