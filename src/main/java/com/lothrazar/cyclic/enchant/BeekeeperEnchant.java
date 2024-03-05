package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.flib.EnchantmentFlib;
import dev.zestyblaze.zestylib.events.living.LivingChangeTargetEvent;
import dev.zestyblaze.zestylib.events.living.LivingDamageEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class BeekeeperEnchant extends EnchantmentFlib {
    public static final String ID = "beekeeper";
    public static BooleanValue CFG;

    public BeekeeperEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        onLivingChangeTargetEvent();
        onLivingDamageEvent();
    }

    @Override
    public boolean isEnabled() {
        return CFG.get();
    }

    @Override
    public boolean isTradeable() {
        return isEnabled() && super.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return isEnabled() && super.isDiscoverable();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return isEnabled() && super.canEnchant(stack);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public void onLivingChangeTargetEvent() {
        LivingChangeTargetEvent.CHANGE_TARGET.register(event -> {
            if (!isEnabled()) {
                return;
            }
            if (event.getOriginalTarget() instanceof Player && event.getEntity().getType() == EntityType.BEE && event.getEntity() instanceof Bee bee) {
                int level = this.getCurrentArmorLevel(event.getOriginalTarget());
                if (level > 0) {
                    event.setCanceled(true);
                    bee.setAggressive(false);
                    bee.setRemainingPersistentAngerTime(0);
                    bee.setPersistentAngerTarget(null);
                }
            }
        });
    }

    public void onLivingDamageEvent() {
        LivingDamageEvent.HURT.register(event -> {
            if (!isEnabled()) {
                return;
            }
            int level = this.getCurrentArmorLevel(event.getEntity());
            if (level >= 1 && event.getSource() != null
                    && event.getSource().getDirectEntity() != null) {
                // Beekeeper I+
                Entity esrc = event.getSource().getDirectEntity();
                if (esrc.getType() == EntityType.BEE ||
                        esrc.getType() == EntityType.BAT ||
                        esrc.getType() == EntityType.LLAMA_SPIT) {
                    event.setAmount(0);
                }
                if (level >= 2) {
                    //Beekeeper II+
                    //all of level I and also
                    if (esrc.getType() == EntityType.PHANTOM) {
                        event.setAmount(0);
                    }
                }
            }
        });
    }
}
