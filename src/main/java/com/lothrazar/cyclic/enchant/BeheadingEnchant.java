package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.config.ConfigRegistry;
import com.lothrazar.cyclic.flib.EnchantmentFlib;
import com.lothrazar.cyclic.flib.ItemStackUtil;
import com.lothrazar.cyclic.flib.TagDataUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.Map;

public class BeheadingEnchant extends EnchantmentFlib {
    public static IntValue PERCDROP;
    public static IntValue PERCPERLEVEL;
    public static final String ID = "beheading";
    public static BooleanValue CFG;

    public BeheadingEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
        onEntityDeath();
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
        return 3;
    }

    private int percentForLevel(int level) {
        return PERCDROP.get() + (level - 1) * PERCPERLEVEL.get();
    }

    public void onEntityDeath() {
        ServerLivingEntityEvents.AFTER_DEATH.register((target, damageSource) -> {
            if (!isEnabled()) {
                return;
            }
            if (damageSource.getEntity() instanceof Player attacker) {
                int level = getCurrentLevelTool(attacker);
                if (level <= 0) {
                    return;
                }
                Level world = attacker.level();
                if (Mth.nextInt(world.random, 0, 100) > percentForLevel(level)) {
                    return;
                }
                if (target == null) {
                    return;
                } //probably wont happen just extra safe
                BlockPos pos = target.blockPosition();
                if (target instanceof Player) {
                    //player head
                    ItemStackUtil.drop(world, pos, TagDataUtil.buildNamedPlayerSkull((Player) target));
                    return;
                }
                //else the random number was less than 10, so it passed the 10% chance req
                ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
                String key = type.toString();
                ////we allow all these, which include config, to override the vanilla skulls below
                Map<String, String> mappedBeheading = ConfigRegistry.getMappedBeheading();
                if (target.getType() == EntityType.ENDER_DRAGON) {
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.DRAGON_HEAD));
                }
                else if (target.getType() == EntityType.CREEPER) {
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.CREEPER_HEAD));
                }
                else if (target.getType() == EntityType.ZOMBIE) {
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.ZOMBIE_HEAD));
                }
                else if (target.getType() == EntityType.SKELETON) {
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.SKELETON_SKULL));
                }
                else if (target.getType() == EntityType.WITHER_SKELETON) {
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.WITHER_SKELETON_SKULL));
                }
                else if (target.getType() == EntityType.WITHER) { //Drop number of heads equal to level of enchant [1,3]
                    ItemStackUtil.drop(world, pos, new ItemStack(Items.WITHER_SKELETON_SKULL, Math.max(level, 3)));
                }
                // TODO: Can Uncomment when Hephaestus is ported to 1.20.2
                /*
                else if (ModList.get().isLoaded(CompatConstants.TCONSTRUCT)) {
                    //tconstruct: drowned_head husk_head enderman_head cave_spider_head stray_head
                    String id = CompatConstants.TCONSTRUCT;
                    ItemStack tFound = ItemStack.EMPTY;
                    if (target.getType() == EntityType.DROWNED) {
                        tFound = ItemStackUtil.findItem(id + ":drowned_head");
                    }
                    else if (target.getType() == EntityType.HUSK) {
                        tFound = ItemStackUtil.findItem(id + ":husk_head");
                    }
                    else if (target.getType() == EntityType.ENDERMAN) {
                        tFound = ItemStackUtil.findItem(id + ":enderman_head");
                    }
                    else if (target.getType() == EntityType.SPIDER) {
                        tFound = ItemStackUtil.findItem(id + ":spider_head");
                    }
                    else if (target.getType() == EntityType.CAVE_SPIDER) {
                        tFound = ItemStackUtil.findItem(id + ":cave_spider_head");
                    }
                    else if (target.getType() == EntityType.STRAY) {
                        tFound = ItemStackUtil.findItem(id + ":stray_head");
                    }
                    else if (target.getType() == EntityType.BLAZE) {
                        tFound = ItemStackUtil.findItem(id + ":blaze_head");
                    }
                    if (!tFound.isEmpty()) {
                        ItemStackUtil.drop(world, pos, tFound);
                        return;
                    }
                }
                 */
                else if (mappedBeheading.containsKey(key)) {
                    //otherwise not a real mob, try the config last
                    ItemStackUtil.drop(world, pos, TagDataUtil.buildNamedPlayerSkull(mappedBeheading.get(key)));
                }
            }
        });
    }
}
