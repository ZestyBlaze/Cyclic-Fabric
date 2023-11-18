package com.lothrazar.cyclic.net;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.item.LaserItem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PacketEntityLaser {
    public static final ResourceLocation ID = new ResourceLocation(ModCyclic.MODID, "laser_packet");

    public static void send(Entity entity, boolean crosshair) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(entity.getId());
        buf.writeBoolean(crosshair);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayer serverPlayer, FriendlyByteBuf buf) {
        Level level = serverPlayer.level();
        Entity target = level.getEntity(buf.readInt());
        boolean crosshair = buf.readBoolean();
        server.execute(() -> {
            //validate also covers delay
            ItemStack stack = LaserItem.getIfHeld(serverPlayer);
            LaserItem item = (LaserItem)stack.getItem();
            if (canShoot(serverPlayer, target, stack)) {
                float dmg = crosshair ? LaserItem.DMG_CLOSE : LaserItem.DMG_FAR;
                if (target.hurt(level.damageSources().indirectMagic(serverPlayer, serverPlayer), dmg)) {
                    //DRAIN RF etc
                    LaserItem.resetStackDamageCool(stack, level.getGameTime());
                    item.tryUseEnergy(stack, LaserItem.COST);
                }
            }
        });
    }

    private static boolean canShoot(ServerPlayer sender, Entity target, ItemStack stack) {
        if (!sender.isAlive() || !target.isAlive() || target.isInvulnerable()) {
            //somene died or target is invincible
            return false;
        }
        if (stack.isEmpty()) {
            return false;
        }
        LaserItem item = (LaserItem)stack.getItem();
        return item.tryUseEnergy(stack, LaserItem.COST);
    }
}
