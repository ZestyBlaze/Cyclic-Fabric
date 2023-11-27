package com.lothrazar.cyclic.mixin;

import com.lothrazar.cyclic.potion.CyclicMobEffect;
import com.lothrazar.cyclic.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique private final LivingEntity livingEntity = (LivingEntity)(Object)this;
    @Shadow public abstract Collection<MobEffectInstance> getActiveEffects();

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void cyclic$createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(AttributeRegistry.STEP_HEIGHT);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void cyclic$tick(CallbackInfo ci) {
        Iterator<MobEffectInstance> itr = getActiveEffects().iterator();
        if (itr.hasNext()) {
            MobEffectInstance mobEffect = itr.next();
            if (mobEffect.getEffect() instanceof CyclicMobEffect cyclicMobEffect) {
                cyclicMobEffect.tick(livingEntity);
            }
        }
    }

    @Inject(method = "onEffectRemoved", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;removeAttributeModifiers(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V"))
    private void cyclic$onEffectRemoved(MobEffectInstance effectInstance, CallbackInfo ci) {
        if (effectInstance.getEffect() instanceof CyclicMobEffect cyclicMobEffect) {
            cyclicMobEffect.onRemoved((LivingEntity)(Object)this);
        }
    }
}
