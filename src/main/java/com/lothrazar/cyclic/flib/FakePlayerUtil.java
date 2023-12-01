package com.lothrazar.cyclic.flib;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.world.entity.Entity;

public class FakePlayerUtil {
    public static boolean isFakePlayer(Entity attacker) {
        return attacker instanceof FakePlayer;
    }
}
