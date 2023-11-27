package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.flib.EnchantmentFlib;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class AutoSmeltEnchant extends EnchantmentFlib {
    public static final String ID = "auto_smelt";
    public static BooleanValue CFG;

    public AutoSmeltEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
    }

    //
    // config stuff start
    //
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
    public boolean isEnabled() {
        return CFG.get();
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean checkCompatibility(Enchantment ench) {
        return ench != Enchantments.SILK_TOUCH && ench != Enchantments.BLOCK_FORTUNE && super.checkCompatibility(ench);
    }
}
