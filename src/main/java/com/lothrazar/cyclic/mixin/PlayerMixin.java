package com.lothrazar.cyclic.mixin;

import com.lothrazar.cyclic.item.elemental.FireballItem;
import com.lothrazar.cyclic.item.food.LoftyStatureApple;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Unique private final Player player = (Player)(Object)this;

    @Inject(method = "tick", at = @At("TAIL"))
    private void cyclic$tick(CallbackInfo ci) {
        LoftyStatureApple.onUpdate(player);
        FireballItem.tickHoldingFireball(player);
    }
}
