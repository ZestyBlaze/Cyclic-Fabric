package com.lothrazar.cyclic.registry;

import com.lothrazar.cyclic.net.PacketEntityLaser;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketRegistry {
    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(PacketEntityLaser.ID, ((server, player, handler, buf, responseSender) -> PacketEntityLaser.handle(server, player, buf)));
    }
}
