package com.lothrazar.cyclic.item;

import com.lothrazar.cyclic.flib.SoundUtil;
import com.lothrazar.cyclic.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

/**
 * TODO: Carry on updating this.
 * It should use NO client sided code, all server sided logic
 * `use` can be used to handle if it should initially fire or not
 * Energy Drain is handled in `onUseTick` while scanning for if still looking at an entity.
 */
public class LaserItem extends ItemBaseCyclic {
    public static final int DMG_FAR = 4;
    public static final int COST = 50;
    public static final int DMG_CLOSE = 6;
    public static final double RANGE_FACTOR = 8;
    public static final double RANGE_MAX = 6000;

    private static EntityHitResult ehr;

    public LaserItem(Properties properties) {
        super(properties.stacksTo(1));
        this.setUsesEnergy();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (!(getStoredEnergy(stack) < COST)) {
            double laserGamemodeRange = mc.gameMode.getPickRange() * RANGE_FACTOR;
            Entity camera = mc.getCameraEntity();
            Vec3 cameraViewVector = camera.getViewVector(1.0F);
            Vec3 cameraEyePosition = camera.getEyePosition(1.0F);
            Vec3 cameraEyeViewRay = cameraEyePosition.add(cameraViewVector.x * laserGamemodeRange, cameraViewVector.y * laserGamemodeRange, cameraViewVector.z * laserGamemodeRange);
            AABB aabb = camera.getBoundingBox().expandTowards(cameraViewVector.scale(laserGamemodeRange)).inflate(1.0D, 1.0D, 1.0D);
            ehr = ProjectileUtil.getEntityHitResult(camera, cameraEyePosition, cameraEyeViewRay, aabb, (ent) -> {
                return ent.isAttackable() && ent.isAlive();
            }, 0);

            if (ehr != null) {
                playerIn.startUsingItem(handIn);
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.fail(stack);


        /*
        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (!(getStoredEnergy(stack) < COST)) {
            if (mc.crosshairPickEntity != null) {
                playerIn.startUsingItem(handIn);
                if (worldIn.getGameTime() % 4 == 0) {
                    SoundUtil.playSound(playerIn, SoundRegistry.LASERBEANPEW, 0.2F);
                    if (!worldIn.isClientSide()) {
                        if (mc.crosshairPickEntity.hurt(worldIn.damageSources().indirectMagic(playerIn, playerIn), DMG_CLOSE)) {
                            resetStackDamageCool(stack, worldIn.getGameTime());
                            tryUseEnergy(stack, COST);
                        }
                    }
                    return InteractionResultHolder.fail(stack);
                }
            } else {
                double laserGamemodeRange = mc.gameMode.getPickRange() * RANGE_FACTOR;
                Entity camera = mc.getCameraEntity();
                Vec3 cameraViewVector = camera.getViewVector(1.0F);
                Vec3 cameraEyePosition = camera.getEyePosition(1.0F);
                Vec3 cameraEyeViewRay = cameraEyePosition.add(cameraViewVector.x * laserGamemodeRange, cameraViewVector.y * laserGamemodeRange, cameraViewVector.z * laserGamemodeRange);
                AABB aabb = camera.getBoundingBox().expandTowards(cameraViewVector.scale(laserGamemodeRange)).inflate(1.0D, 1.0D, 1.0D);
                EntityHitResult ehr = ProjectileUtil.getEntityHitResult(camera, cameraEyePosition, cameraEyeViewRay, aabb, (ent) -> {
                    return ent.isAttackable() && ent.isAlive();
                }, 0);
                if (ehr != null) {
                    Vec3 entityHitResultLocation = ehr.getLocation();
                    double distance = Math.sqrt(cameraEyePosition.distanceToSqr(entityHitResultLocation));
                    if (distance < LaserItem.RANGE_MAX) {
                        BlockHitResult miss = mc.level.clip(new ClipContext(cameraEyePosition, entityHitResultLocation, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, mc.player));
                        if (!(miss.getType() == HitResult.Type.BLOCK)) {
                            if (worldIn.getGameTime() % 4 == 0) {
                                SoundUtil.playSound(playerIn, SoundRegistry.LASERBEANPEW, 0.2F);
                                if(ehr.getEntity().hurt(worldIn.damageSources().indirectMagic(playerIn, playerIn), DMG_FAR)) {
                                    resetStackDamageCool(stack, worldIn.getGameTime());
                                    tryUseEnergy(stack, COST);
                                    return InteractionResultHolder.success(stack);
                                }
                            }
                        }
                    }
                }
            }
        }
        return InteractionResultHolder.fail(stack);

         */
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(ehr != null && !level.isClientSide()) {
            Vec3 cameraEyePosition = livingEntity.getEyePosition(1.0F);
            Vec3 entityHitResultLocation = ehr.getLocation();
            double distance = Math.sqrt(cameraEyePosition.distanceToSqr(entityHitResultLocation));
            if (distance < LaserItem.RANGE_MAX) {
                BlockHitResult miss = livingEntity.level().clip(new ClipContext(cameraEyePosition, entityHitResultLocation, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, livingEntity));
                if (!(miss.getType() == HitResult.Type.BLOCK)) {
                    if (level.getGameTime() % 4 == 0) {
                        SoundUtil.playSound(level, livingEntity.blockPosition(), SoundRegistry.LASERBEANPEW, 0.2f);
                        if(ehr.getEntity().hurt(level.damageSources().indirectMagic(livingEntity, livingEntity), DMG_FAR)) {
                            tryUseEnergy(stack, COST);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000 * 2;
    }

    public static ItemStack getIfHeld(Player player) {
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() instanceof LaserItem) {
            return heldItem;
        }
        //MAIN HAND ONLY for this case
        return ItemStack.EMPTY;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int chargeTimer) {
        ehr = null;
    }
}
