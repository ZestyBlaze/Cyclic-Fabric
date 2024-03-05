package com.lothrazar.cyclic.mixin;

import com.lothrazar.cyclic.registry.AttributeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "maxUpStep", at = @At("TAIL"), cancellable = true)
    private void cyclic$maxStepUp(CallbackInfoReturnable<Float> cir) {
        if ((Object)this instanceof LivingEntity) {
            float baseStepHeight = cir.getReturnValue();
            cir.setReturnValue(AttributeRegistry.getStepHeight(baseStepHeight, (LivingEntity)(Object)this));
        }
    }
}
