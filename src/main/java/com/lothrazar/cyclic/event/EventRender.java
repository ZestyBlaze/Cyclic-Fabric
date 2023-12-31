package com.lothrazar.cyclic.event;

import com.lothrazar.cyclic.flib.RenderEntityToBlockLaser;
import com.lothrazar.cyclic.item.LaserItem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class EventRender {
    public static void onRenderWorldLast() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            if (context.blockOutlines()) {
                Minecraft mc = Minecraft.getInstance();
                Player player = mc.player;
                if (player == null) {
                    return;
                }
                Level world = player.level();
                ItemStack stack;
                /****************** end rendering cubes. start laser beam render ********************/
                stack = LaserItem.getIfHeld(player);
                if (!stack.isEmpty() && player.isUsingItem()) {
                    LaserItem laserItem = (LaserItem) stack.getItem();
                    if (laserItem.getStoredEnergy(stack) < LaserItem.COST) {
                        return;
                    }

                    // RayTraceResult  became HitResult
                    // objectMouseOver became hitResult
                    if (mc.crosshairPickEntity != null) {
                        //Render and Shoot
                        RenderEntityToBlockLaser.renderLaser(context.matrixStack(), player, mc.getFrameTime(), InteractionHand.MAIN_HAND, 18, -0.02F); // TODO
                    } else {
                        //out of range- do custom raytrace
                        double laserGamemodeRange = mc.gameMode.getPickRange() * LaserItem.RANGE_FACTOR;
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
                                //first vector is FROM, second is TO
                                BlockHitResult miss = mc.level.clip(new ClipContext(cameraEyePosition, entityHitResultLocation, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, mc.player));
                                //              BlockHitResult miss = BlockHitResult.miss(entityHitResultLocation, Direction.getNearest(cameraViewVector.x, cameraViewVector.y, cameraViewVector.z),                new BlockPos(entityHitResultLocation));
                                if (miss.getType() == HitResult.Type.BLOCK) {
                                    //we hit a wall, dont shoot thru walls
                                } else {
                                    //Render and Shoot
                                    RenderEntityToBlockLaser.renderLaser(context.matrixStack(), player, mc.getFrameTime(), InteractionHand.MAIN_HAND);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
