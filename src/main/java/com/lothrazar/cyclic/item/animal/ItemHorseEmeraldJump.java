package com.lothrazar.cyclic.item.animal;

import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.flib.EntityUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class ItemHorseEmeraldJump extends ItemBaseCyclic {
    private static final int JUMP_MAX = 10;
    private static final double JUMP_AMT = 0.08;
    public static final UUID MODIFIER_ID = UUID.fromString("abc30aa2-eff2-4a81-b92b-a1cb95f115c6");

    public ItemHorseEmeraldJump(Properties prop) {
        super(prop);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Horse ahorse && !player.level().isClientSide()) {
            //got the attribute instance
            AttributeInstance mainAttribute = ahorse.getAttribute(Attributes.JUMP_STRENGTH);
            //now create a modifier
            if (mainAttribute.getValue() < JUMP_MAX) {
                //ok good
                AttributeModifier oldModifier = mainAttribute.getModifier(MODIFIER_ID);
                //what was the previous value
                double newAdded = (oldModifier == null) ? JUMP_AMT : oldModifier.getAmount() + JUMP_AMT;
                //got it      //replace the modifier on the main attribute
                mainAttribute.removeModifier(MODIFIER_ID);
                AttributeModifier newModifier = new AttributeModifier(MODIFIER_ID, "Cyclic Carrot Jump", newAdded, AttributeModifier.Operation.ADDITION);
                mainAttribute.addPermanentModifier(newModifier);
                //finish up
                //
                //success doesnt work, its broken. player still does the mounting lol
                stack.shrink(1);
                ahorse.eating();
                ChatUtil.sendStatusMessage(player, "" + (mainAttribute.getValue() + newAdded));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
