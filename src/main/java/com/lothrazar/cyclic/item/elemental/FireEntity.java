package com.lothrazar.cyclic.item.elemental;

import com.lothrazar.cyclic.flib.Const;
import com.lothrazar.cyclic.registry.EntityRegistry;
import com.lothrazar.cyclic.registry.ItemRegistry;
import com.lothrazar.cyclic.registry.PotionEffectRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FireEntity extends ThrowableItemProjectile {
    public FireEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    public FireEntity(LivingEntity livingEntityIn, Level worldIn) {
        super(EntityRegistry.FIRE_BOLT, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.FIREBALL_ORANGE;
    }

    @Override
    protected void onHit(HitResult result) {
        HitResult.Type type = result.getType();
        if (type == HitResult.Type.ENTITY) {
            //damage entity by zero
            //drop torch
            EntityHitResult entityRayTrace = (EntityHitResult) result;
            Entity target = entityRayTrace.getEntity();
            var level = level();
            if (target.isAlive()) {
                target.hurt(level.damageSources().thrown(this, this.getOwner()), Mth.nextInt(level.random, 2, 6));
                if (!level.isClientSide && target.isOnFire() == false
                        && target instanceof LivingEntity) {
                    target.hurt(level.damageSources().inFire(), Mth.nextInt(level.random, 3, 5));
                    LivingEntity living = (LivingEntity) target;
                    living.addEffect(new MobEffectInstance(PotionEffectRegistry.STUN, Const.TICKS_PER_SEC * 4, 1));
                    living.setSecondsOnFire(Mth.nextInt(level.random, 1, 5));
                }
            }
        }
        else if (type == HitResult.Type.BLOCK) {
            BlockHitResult ray = (BlockHitResult) result;
            if (ray.getBlockPos() == null) {
                return;
            }
            //      BlockPos pos = ray.getPos();//.offset(ray.getFace());
            //      Block blockHere = world.getBlockState(pos).getBlock();
            //      if (blockHere == Blocks.SNOW
            //          || blockHere == Blocks.SNOW_BLOCK
            //          || blockHere == Blocks.SNOW
            //          || blockHere == Blocks.ICE) {
            //        this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
            //      }
        }
        this.remove(RemovalReason.DISCARDED);
    }
}
