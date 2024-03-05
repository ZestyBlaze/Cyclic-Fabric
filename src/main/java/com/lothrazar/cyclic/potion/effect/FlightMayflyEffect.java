package com.lothrazar.cyclic.potion.effect;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.potion.CyclicMobEffect;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FlightMayflyEffect extends CyclicMobEffect {
    private static final AbilitySource FLIGHT_POTION = Pal.getAbilitySource(new ResourceLocation(ModCyclic.MODID, "potion_flight"));

    public FlightMayflyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int i) {
        super.onEffectStarted(livingEntity, i);
        if (livingEntity instanceof ServerPlayer sp) {
            Pal.grantAbility(sp, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
        }
    }

    @Override
    public void onRemoved(LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer sp) {
            Pal.revokeAbility(sp, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
        }
    }
}
