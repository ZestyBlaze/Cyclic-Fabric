package com.lothrazar.cyclic.flib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class EntityUtil {
    private static final double ENTITY_PULL_DIST = 0.4; //closer than this and nothing happens
    private static final double ENTITY_PULL_SPEED_CUTOFF = 3; //closer than this and it slows down
    private static final float ITEMSPEEDFAR = 0.9F;
    private static final float ITEMSPEEDCLOSE = 0.2F;

    public static double getSpeedTranslated(double speed) {
        return speed * 100;
    }

    public static AABB makeBoundingBox(BlockPos center, int hRadius, int vRadius) {
        return makeBoundingBox(center.getX(), center.getY(), center.getZ(), hRadius, vRadius);
    }

    public static AABB makeBoundingBox(double x, double y, double z, int hRadius, int vRadius) {
        return new AABB(
                x - hRadius, y - vRadius, z - hRadius,
                x + hRadius, y + vRadius, z + hRadius);
    }

    public static void moveEntityItemsInRegion(Level world, BlockPos pos, int hRadius, int vRadius) {
        moveEntityItemsInRegion(world, pos.getX(), pos.getY(), pos.getZ(), hRadius, vRadius, true);
    }

    public static void moveEntityItemsInRegion(Level world, double x, double y, double z, int hRadius, int vRadius, boolean towardsPos) {
        AABB range = makeBoundingBox(x, y, z, hRadius, vRadius);
        List<Entity> all = getItemExp(world, range);
        pullEntityList(x, y, z, towardsPos, all);
    }

    public static List<Entity> getItemExp(Level world, AABB range) {
        List<Entity> all = new ArrayList<>();
        all.addAll(world.getEntitiesOfClass(ItemEntity.class, range));
        all.addAll(world.getEntitiesOfClass(ExperienceOrb.class, range));
        return all;
    }

    public static void pullEntityList(double x, double y, double z, boolean towardsPos, List<? extends Entity> all) {
        pullEntityList(x, y, z, towardsPos, all, ITEMSPEEDCLOSE, ITEMSPEEDFAR);
    }

    public static void pullEntityList(double x, double y, double z, boolean towardsPos, List<? extends Entity> all, float speedClose, float speedFar) {
        double hdist, xDist, zDist;
        float speed;
        int direction = (towardsPos) ? 1 : -1; //negative to flip the vector and push it away
        for (Entity entity : all) {
            if (entity == null) {
                continue;
            } //being paranoid
            if (entity instanceof Player && ((Player) entity).isCrouching()) {
                continue; //sneak avoid feature
            }
            BlockPos p = entity.blockPosition();
            xDist = Math.abs(x - p.getX());
            zDist = Math.abs(z - p.getZ());
            hdist = Math.sqrt(xDist * xDist + zDist * zDist);
            if (hdist > ENTITY_PULL_DIST) {
                speed = (hdist > ENTITY_PULL_SPEED_CUTOFF) ? speedFar : speedClose;
                setEntityMotionFromVector(entity, x, y, z, direction * speed);
            } //else its basically on it, no point
        }
    }

    public static void setEntityMotionFromVector(Entity entity, double x, double y, double z, float modifier) {
        Vector3 originalPosVector = new Vector3(x, y, z);
        Vector3 entityVector = new Vector3(entity);
        Vector3 finalVector = originalPosVector.copy().subtract(entityVector);
        if (finalVector.mag() > 1) {
            finalVector.normalize();
        }
        double motionX = finalVector.x * modifier;
        double motionY = finalVector.y * modifier;
        double motionZ = finalVector.z * modifier;
        entity.setDeltaMovement(motionX, motionY, motionZ);
    }

    public static void setCooldownItem(Player player, Item item, int cooldown) {
        player.getCooldowns().addCooldown(item, cooldown);
    }
}
