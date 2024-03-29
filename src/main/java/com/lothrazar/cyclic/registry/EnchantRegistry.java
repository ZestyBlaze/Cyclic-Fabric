package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.enchant.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantRegistry {
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };

    public static final Enchantment EXCAVATE = register(ExcavationEnchant.ID, new ExcavationEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment BEHEADING = register(BeheadingEnchant.ID, new BeheadingEnchant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment GROWTH = register(GrowthEnchant.ID, new GrowthEnchant(Enchantment.Rarity.COMMON, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment AUTOSMELT = register(AutoSmeltEnchant.ID, new AutoSmeltEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
    public static final Enchantment DISARM = register(DisarmEnchant.ID, new DisarmEnchant(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment CURSE = register(GloomCurseEnchant.ID, new GloomCurseEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, EquipmentSlot.CHEST));
    public static final Enchantment PEARL = register(EnderPearlEnchant.ID, new EnderPearlEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static final Enchantment BEEKEEPER = register(BeekeeperEnchant.ID, new BeekeeperEnchant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));
    public static final Enchantment STAND = register(LastStandEnchant.ID, new LastStandEnchant(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.ARMOR_LEGS, EquipmentSlot.LEGS));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT, new ResourceLocation(ModCyclic.MODID, name), enchantment);
    }

    public static void init() {}
}
