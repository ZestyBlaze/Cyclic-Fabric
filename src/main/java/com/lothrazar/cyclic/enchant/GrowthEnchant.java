package com.lothrazar.cyclic.enchant;

import com.lothrazar.cyclic.flib.EnchantmentFlib;
import com.lothrazar.cyclic.flib.ItemStackUtil;
import com.lothrazar.cyclic.flib.ShapeUtil;
import com.lothrazar.cyclic.util.HarvestUtil;
import dev.zestyblaze.zestylib.events.living.LivingEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.Collections;
import java.util.List;

public class GrowthEnchant extends EnchantmentFlib {
    public static final double ODDS_ROTATE = 0.04;
    public static final String ID = "growth";
    public static BooleanValue CFG;
    public static IntValue RADIUSFACTOR;

    public GrowthEnchant(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot... slots) {
        super(rarityIn, typeIn, slots);
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
        return isEnabled() && super.canEnchant(stack) && stack.getItem() instanceof HoeItem;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public void onEntityUpdate() {
        LivingEvent.TICK.register(event -> {
            if (!isEnabled()) {
                return;
            }
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player) {
                Player p = (Player) entity;
                if (p.isSpectator() || !p.isAlive()) {
                    return;
                }
            }
            //Ticking
            int level = getCurrentLevelTool(entity.getItemInHand(InteractionHand.MAIN_HAND));
            if (level > 0 && !entity.level().isClientSide) {
                if (entity.level().random.nextDouble() > ODDS_ROTATE / level) {
                    return; //slow the dice down
                }
                final int growthLimit = level * 2 + (entity.level().isRaining() ? 4 : 1); //faster when raining too
                int grown = 0;
                List<BlockPos> shape = ShapeUtil.squareHorizontalFull(entity.blockPosition().below(), level + RADIUSFACTOR.get());
                shape = ShapeUtil.repeatShapeByHeight(shape, 2);
                Collections.shuffle(shape);
                for (int i = 0; i < shape.size(); i++) {
                    if (grown >= growthLimit) {
                        break;
                    }
                    //do one
                    BlockPos pos = shape.get(i);
                    BlockState target = entity.level().getBlockState(pos);
                    if (target.getBlock() instanceof CropBlock igrowable) { // IGrowable gone, dont use BonemealableBlock
                        igrowable.growCrops(entity.level(), pos, target);
                        grown++; // allow mods to override growCrops() for custom behavior
                    }
                    else { //still use same old age logic as always unchanged
                        IntegerProperty propAge = HarvestUtil.getAgeProp(target);
                        if (propAge == null) {
                            continue;
                        }
                        int maxAge = Collections.max(propAge.getPossibleValues());
                        Integer currentAge = target.getValue(propAge);
                        if (currentAge < maxAge) {
                            if (entity.level().setBlockAndUpdate(pos, target.setValue(propAge, currentAge + 1))) {
                                grown++;
                            }
                        }
                    }
                }
                if (grown > 0) {
                    ItemStackUtil.damageItem(entity, entity.getItemInHand(InteractionHand.MAIN_HAND));
                }
            }
        });
    }
}
