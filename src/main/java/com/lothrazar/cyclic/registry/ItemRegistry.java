package com.lothrazar.cyclic.registry;

import com.google.common.collect.Sets;
import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.item.GemstoneItem;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import com.lothrazar.cyclic.item.LaserItem;
import com.lothrazar.cyclic.item.TempChargerItem;
import com.lothrazar.cyclic.item.elemental.FireExtinguishItem;
import com.lothrazar.cyclic.item.elemental.FireballItem;
import com.lothrazar.cyclic.item.equipment.GlowingHelmetItem;
import com.lothrazar.cyclic.item.food.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemRegistry {

    public static List<ItemBaseCyclic> ITEMSFIXME = new ArrayList<>();
    public static Set<Item> ITEM_LIST = Sets.newLinkedHashSet();
    static final int SMALLPOTIONDUR = 20 * 90; // 1:30
    static final int LARGEPOTIONDUR = 3 * 20 * 60; // 3:00
    static final float APPLESATUR = Foods.APPLE.getSaturationModifier();
    public static final Item APPLE_ENDER = register("apple_ender", new EnderApple(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(0).alwaysEat().build())));
    public static final Item APPLE_LOFTY_STATURE = register("apple_lofty_stature", new LoftyStatureApple(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition() * 4).saturationMod(APPLESATUR * 4)
            .build())));
    public static final Item APPLE_HONEY = register("apple_honey", new ItemBaseCyclic(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition() * 4).saturationMod(APPLESATUR * 4)
            .build())));
    public static final Item APPLE_CHORUS = register("apple_chorus", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR)
            .effect(new MobEffectInstance(MobEffects.LEVITATION, LARGEPOTIONDUR, 1), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.UNLUCK, LARGEPOTIONDUR, 1), 1)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, SMALLPOTIONDUR, 1), 1)
            .alwaysEat()
            .build())));
    public static final Item APPLE_BONE = register("apple_bone", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR)
            .effect(new MobEffectInstance(MobEffects.JUMP, LARGEPOTIONDUR, 4 + 5), 1)
            .effect(new MobEffectInstance(MobEffects.INVISIBILITY, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.WEAKNESS, LARGEPOTIONDUR, 2), 1)
            .effect(new MobEffectInstance(MobEffects.UNLUCK, LARGEPOTIONDUR, 0), 1)
            .alwaysEat()
            .build())));
    public static final Item APPLE_PRISMARINE = register("apple_prismarine", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.GLOWING, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, LARGEPOTIONDUR, 0), 1)
            .alwaysEat()
            .build())));
    public static final Item APPLE_LAPIS = register("apple_lapis", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR * 4)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.CONDUIT_POWER, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, LARGEPOTIONDUR, 0), 1)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, LARGEPOTIONDUR, 0), 1)
            .fast().alwaysEat()
            .build())));
    public static final Item APPLE_IRON = register("apple_iron", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR)
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, LARGEPOTIONDUR, 2), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, LARGEPOTIONDUR, 2), 1)
            .fast().alwaysEat()
            .build())));
    public static final Item APPLE_DIAMOND = register("apple_diamond", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(1).saturationMod(1)
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, SMALLPOTIONDUR, 4), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, SMALLPOTIONDUR, 4), 1)
            .fast().alwaysEat()
            .build())));
    public static final Item APPLE_EMERALD = register("apple_emerald", new AppleBuffs(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition() * 3).saturationMod(APPLESATUR)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, SMALLPOTIONDUR, 2), 1)
            .effect(new MobEffectInstance(MobEffects.LUCK, SMALLPOTIONDUR, 1), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, SMALLPOTIONDUR, 1), 1)
            .effect(new MobEffectInstance(MobEffects.SLOW_FALLING, SMALLPOTIONDUR, 1), 1)
            .alwaysEat().build())));
    public static final Item APPLE_CHOCOLATE = register("apple_chocolate", new AppleChocolate(new FabricItemSettings().food(new FoodProperties.Builder().nutrition(Foods.APPLE.getNutrition()).saturationMod(APPLESATUR * 4)
            .alwaysEat().build())));
    public static final Item GLOWING_HELMET = register("glowing_helmet", new GlowingHelmetItem(MaterialRegistry.ArmorMats.GLOWING, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item GEM_AMBER = register("gem_amber", new GemstoneItem(new FabricItemSettings()));

    public static final Item FIRE_KILLER = register("fire_killer", new FireExtinguishItem(new FabricItemSettings()));
    public static final Item MILK_BOTTLE = register("milk_bottle", new MilkBottle(new FabricItemSettings()));
    public static final Item FIREBALL_ORANGE = register("fireball", new FireballItem(new FabricItemSettings()));
    public static final Item LASER_CANNON = register("laser_cannon", new LaserItem(new FabricItemSettings()));

    public static final Item TEMP_CHARGER_ITEM = register("charger_item", new TempChargerItem(new FabricItemSettings()));

    private static Item register(String name, Item item) {
        Item returnItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ModCyclic.MODID, name), item);
        ITEM_LIST.add(returnItem);
        return returnItem;
    }

    public static void init() {}
}
