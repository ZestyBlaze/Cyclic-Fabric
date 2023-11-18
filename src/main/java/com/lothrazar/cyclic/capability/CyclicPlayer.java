package com.lothrazar.cyclic.capability;

import com.lothrazar.cyclic.registry.CapabilityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class CyclicPlayer implements ICyclicPlayer {
    private final Player provider;
    private boolean stepHeight = false;

    public CyclicPlayer(Player player) {
        this.provider = player;
    }

    @Override
    public void setStepHeight(boolean value) {
        this.stepHeight = value;
        CapabilityRegistry.CYCLIC_PLAYER.sync(this.provider);
    }

    @Override
    public boolean getStepHeight() {
        return this.stepHeight;
    }

    @Override
    public void toggleStepHeight() {
        setStepHeight(!getStepHeight());
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("stepHeight", getStepHeight());
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setStepHeight(tag.getBoolean("stepHeight"));
    }
}
