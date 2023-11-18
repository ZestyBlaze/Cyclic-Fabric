package com.lothrazar.cyclic.item;

import com.lothrazar.cyclic.flib.ItemStackUtil;
import com.lothrazar.cyclic.registry.ItemRegistry;
import com.lothrazar.cyclic.registry.TextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import team.reborn.energy.api.base.SimpleEnergyItem;

import java.util.ArrayList;
import java.util.List;

public class ItemBaseCyclic extends Item implements SimpleEnergyItem {
    public static final int MAX_ENERGY = 16000;
    public static final String ENERGYTTMAX = "energyttmax";
    public static final String ENERGYTT = "energytt";
    public static final float INACCURACY_DEFAULT = 1.0F;
    public static final float VELOCITY_MAX = 1.5F;
    private boolean hasEnergy;

    public ItemBaseCyclic(Properties properties) {
        super(properties);
        ItemRegistry.ITEMSFIXME.add(this);
    }

    public void setUsesEnergy() {
        this.hasEnergy = true;
    }

    protected void shootMe(Level world, Player shooter, Projectile ball, float pitch, float velocityFactor) {
        if (world.isClientSide) {
            return;
        }
        Vec3 vec31 = shooter.getUpVector(1.0F);
        Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(pitch * ((float) Math.PI / 180F), vec31.x, vec31.y, vec31.z);
        Vec3 vec3 = shooter.getViewVector(1.0F);
        Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
        ball.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocityFactor * VELOCITY_MAX, INACCURACY_DEFAULT);
        //    worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
        //        SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        world.addFreshEntity(ball);
    }

    public static ItemStack findAmmo(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (itemstack.getItem() == item) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static List<ItemStack> findAmmos(Player player, Item item) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (itemstack.getItem() == item) {
                list.add(itemstack);
            }
        }
        return list;
    }

    /**
     * 1 item(torch) per durability default, override for higher
     *
     */
    public int getRepairPerItem() {
        return 1;
    }

    public void tryRepairWith(ItemStack stackToRepair, Player player, Item target) {
        if (stackToRepair.isDamaged()) {
            ItemStack torches = findAmmo(player, target);
            if (!torches.isEmpty()) {
                torches.shrink(1);
                ItemStackUtil.repairItem(stackToRepair, getRepairPerItem());
            }
        }
    }

    public float getChargedPercent(ItemStack stack, int chargeTimer) {
        return BowItem.getPowerForTime(this.getUseDuration(stack) - chargeTimer);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        if (hasEnergy) {
            return Rarity.EPIC; //uses energy
        }
        return super.getRarity(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (hasEnergy) {
            return TextureRegistry.COLOUR_RF_BAR;
        }
        return super.getBarColor(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return hasEnergy;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        if (this.hasEnergy) {
            long current;
            long energyttmax;
            current = getStoredEnergy(stack);
            energyttmax = getEnergyCapacity(stack);
            tooltip.add(Component.translatable(current + "/" + energyttmax).withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) {
        if (hasEnergy) {
            return MAX_ENERGY;
        }
        return 0;
    }

    @Override
    public long getEnergyMaxInput(ItemStack stack) {
        return Long.MAX_VALUE;
    }

    @Override
    public long getEnergyMaxOutput(ItemStack stack) {
        return Long.MAX_VALUE;
    }
}
