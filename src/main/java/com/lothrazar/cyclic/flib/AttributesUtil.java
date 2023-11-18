package com.lothrazar.cyclic.flib;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class AttributesUtil {
    public static final UUID ID_STEP_HEIGHT = UUID.fromString("66d30aa2-eaa2-4a81-b92b-a1cb95f115ca");
    static final float VANILLA = 0.6F;

    //    player.maxUpStep = 0.6F; // LivingEntity.class constructor defaults to this
    public static void disableStepHeight(Player player) {
        AttributeInstance attr = player.getAttribute(AttributeRegistry.STEP_HEIGHT);
        attr.removeModifier(ID_STEP_HEIGHT);
    }

    public static void enableStepHeight(Player player) {
        float newVal;
        if (player.isCrouching()) {
            //make sure that, when sneaking, dont fall off!!
            newVal = 0.9F - VANILLA;
        }
        else {
            newVal = 1.0F + (1F / 16F) - VANILLA; //PATH BLOCKS etc are 1/16th downif MY feature turns this on, then do it
        }
        //    player.maxUpStep = newVal; // Deprecated
        AttributeInstance attr = player.getAttribute(AttributeRegistry.STEP_HEIGHT);
        AttributeModifier oldModifier = attr.getModifier(AttributesUtil.ID_STEP_HEIGHT);
        double old = oldModifier == null ? 0 : oldModifier.getAmount();
        if (newVal != old) {
            AttributesUtil.setStepHeightInternal(player, newVal);
        }
    }

    private static void setStepHeightInternal(Player player, double newVal) {
        //    player.maxUpStep = 0.6F; // LivingEntity.class constructor defaults to this
        AttributeInstance attr = player.getAttribute(AttributeRegistry.STEP_HEIGHT);
        attr.removeModifier(ID_STEP_HEIGHT);
        if (newVal != 0) {
            AttributeModifier healthModifier = new AttributeModifier(ID_STEP_HEIGHT, ModCyclic.MODID, newVal, AttributeModifier.Operation.ADDITION);
            attr.addPermanentModifier(healthModifier);
        }
    }
}
