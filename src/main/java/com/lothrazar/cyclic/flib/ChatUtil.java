package com.lothrazar.cyclic.flib;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class ChatUtil {
    public static MutableComponent ilang(String message) {
        return Component.translatable(message);
    }

    public static void addServerChatMessage(Player player, String message) {
        addServerChatMessage(player, ilang(message));
    }

    public static void addServerChatMessage(Player player, Component message) {
        if (!player.level().isClientSide) {
            player.sendSystemMessage(message);
        }
    }

    public static String lang(String message) {
        return ilang(message).getString();
    }
}
