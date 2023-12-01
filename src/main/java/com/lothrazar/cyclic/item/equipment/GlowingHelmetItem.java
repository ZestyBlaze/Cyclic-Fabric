package com.lothrazar.cyclic.item.equipment;

import com.lothrazar.cyclic.flib.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class GlowingHelmetItem extends ArmorItem implements IHasClickToggle {
    public static final String NBT_STATUS = "onoff";

    public GlowingHelmetItem(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        boolean isTurnedOn = this.isOn(stack);
        removeNightVision(player, isTurnedOn);
        if (isTurnedOn) {
            addNightVision(player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable(ChatUtil.lang(this.getDescriptionId() + ".tooltip")).withStyle(ChatFormatting.GRAY));
        String onoff = this.isOn(stack) ? "on" : "off";
        MutableComponent t = Component.translatable(ChatUtil.lang("item.cantoggle.tooltip.info") + " " + ChatUtil.lang("item.cantoggle.tooltip." + onoff));
        t.withStyle(ChatFormatting.DARK_GRAY);
        tooltip.add(t);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    private static void addNightVision(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * Const.TICKS_PER_SEC, 0));
    }

    public static void removeNightVision(Player player, boolean hidden) {
        //flag it so we know the purple glow was from this item, not something else
        player.removeEffectNoUpdate(MobEffects.NIGHT_VISION);
    }

    private static void checkIfHelmOff(Player player) {
        Item itemInSlot = PlayerUtil.getItemArmorSlot(player, EquipmentSlot.HEAD);
        if (itemInSlot instanceof GlowingHelmetItem) {
            //turn it off once, from the message
            removeNightVision(player, false);
        }
    }

    @Override
    public void toggle(Player player, ItemStack held) {
        CompoundTag tags = TagDataUtil.getItemStackNBT(held);
        int vnew = isOn(held) ? 0 : 1;
        tags.putInt(NBT_STATUS, vnew);
    }

    @Override
    public boolean isOn(ItemStack held) {
        //    CompoundTag tags = UtilNBT.getItemStackNBT(held);
        return isOnStatic(held);
    }

    private static boolean isOnStatic(ItemStack held) {
        CompoundTag tags = TagDataUtil.getItemStackNBT(held);
        if (!tags.contains(NBT_STATUS)) {
            return true;
        } //default for newlycrafted//legacy items
        return tags.getInt(NBT_STATUS) == 1;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action.equals(ClickAction.SECONDARY)) {
            ((IHasClickToggle)stack.getItem()).toggle(player, stack);
            return true;
        }
        return false;
    }

    public static void onEntityUpdate(Player player) {
        if (player.level().getGameTime() % 20 == 0) {
            checkIfHelmOff(player);
            /*
            // get helm
            ItemStack helm = CharmUtil.getCurio(player, ItemRegistry.GLOWING_HELMET.get());
            if (!helm.isEmpty()) {
                if (isOnStatic(helm)) {
                    addNightVision(player);
                }
                else {
                    removeNightVision(player, false);
                }
            }
             */
        }
    }
}
