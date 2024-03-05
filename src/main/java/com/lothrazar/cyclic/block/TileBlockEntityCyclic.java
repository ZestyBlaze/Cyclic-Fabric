package com.lothrazar.cyclic.block;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.flib.ItemStackUtil;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class TileBlockEntityCyclic extends BlockEntity {
    public static final String NBTINV = "inv";
    public static final String NBTFLUID = "fluid";
    public static final String NBTENERGY = "energy";
    public static final int MENERGY = 64 * 1000;
    protected int flowing = 1;
    protected int needsRedstone = 1;
    protected int render = 0; // default to do not render
    protected int timer = 0;

    public TileBlockEntityCyclic(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public int getTimer() {
        return timer;
    }

    protected Player getLookingPlayer(int maxRange, boolean mustCrouch) {
        List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(
                this.worldPosition.getX() - maxRange, this.worldPosition.getY() - maxRange, this.worldPosition.getZ() - maxRange, this.worldPosition.getX() + maxRange, this.worldPosition.getY() + maxRange, this.worldPosition.getZ() + maxRange));
        for (Player player : players) {
            if (mustCrouch && !player.isCrouching()) {
                continue; //check the next one
            }
            //am i looking
            Vec3 positionEyes = player.getEyePosition(1F);
            Vec3 look = player.getViewVector(1F);
            //take the player eye position. draw a vector from the eyes, in the direction they are looking
            //of LENGTH equal to the range
            Vec3 visionWithLength = positionEyes.add(look.x * maxRange, look.y * maxRange, look.z * maxRange);
            //ray trayce from eyes, along the vision vec
            BlockHitResult rayTrace = this.level.clip(new ClipContext(positionEyes, visionWithLength, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (this.worldPosition.equals(rayTrace.getBlockPos())) {
                //at least one is enough, stop looping
                return player;
            }
        }
        return null;
    }

    public void tryDumpFakePlayerInvo(WeakReference<FakePlayer> fp, SimpleContainer out, boolean onGround) {
        if (out == null) {
            return;
        }
        int start = 1;
        ArrayList<ItemStack> toDrop = new ArrayList<>();
        for (int i = start; i < fp.get().getInventory().items.size(); i++) {
            ItemStack fpItem = fp.get().getInventory().items.get(i);
            if (fpItem.isEmpty()) {
                continue;
            }
            ModCyclic.LOGGER.info("NONEMPTY itemstack found what do we do");
            if (fpItem == fp.get().getMainHandItem()) {
                ModCyclic.LOGGER.info("aha continue main hand item dont doump it");
                continue;
            }
            for (int j = 0; j < out.getContainerSize(); j++) {
                ModCyclic.LOGGER.info(fpItem + "insert itit here" + j);
                fpItem = out.items.set(j, fpItem);
            }
            if (onGround) {
                toDrop.add(fpItem);
            }
            else {
                fp.get().getInventory().items.set(i, fpItem);
            }
        }
        if (onGround) {
            for (ItemStack stack : toDrop) {
                ItemStackUtil.drop(this.level, this.worldPosition.above(), stack);
            }
        }
    }

    public static void tryEquipItem(ItemStack item, WeakReference<FakePlayer> fp, InteractionHand hand) {
        if (fp == null) {
            return;
        }
        fp.get().setItemInHand(hand, item);
    }

    @Override
    public void load(CompoundTag tag) {
        flowing = tag.getInt("flowing");
        needsRedstone = tag.getInt("needsRedstone");
        render = tag.getInt("renderParticles");
        timer = tag.getInt("timer");
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("flowing", flowing);
        tag.putInt("needsRedstone", needsRedstone);
        tag.putInt("renderParticles", render);
        tag.putInt("timer", timer);
        super.saveAdditional(tag);
    }

    public abstract void setField(int field, int value);

    public abstract int getField(int field);

    public void setNeedsRedstone(int value) {
        this.needsRedstone = value % 2;
    }
}
