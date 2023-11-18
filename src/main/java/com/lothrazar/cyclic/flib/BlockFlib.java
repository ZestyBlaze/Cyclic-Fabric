package com.lothrazar.cyclic.flib;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.List;

public class BlockFlib extends Block {

    private static final int MAX_CONNECTED_UPDATE = 18;
    public static final EnumProperty<DyeColor> COLOUR = EnumProperty.create("color", DyeColor.class);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public static class Settings {

        boolean tooltip = false;
        boolean rotateColour = false;
        boolean rotateColourConsume = false;
        boolean litWhenPowered;
        private boolean facingAttachment;

        public Settings rotateColour(boolean consume) {
            this.rotateColour = true;
            this.rotateColourConsume = consume;
            return this;
        }

        public Settings litWhenPowered() {
            this.litWhenPowered = true;
            return this;
        }

        public Settings facingAttachment() {
            this.facingAttachment = true;
            return this;
        }

        public Settings tooltip() {
            this.tooltip = true;
            return this;
        }

        public Settings noTooltip() {
            this.tooltip = false;
            return this;
        }

        public void tooltipApply(Block block, List<Component> tooltipList) {
            tooltipList.add(ChatUtil.ilang(block.getDescriptionId() + ".tooltip").withStyle(ChatFormatting.GRAY));
        }
    }

    Settings me;

    public BlockFlib(Properties prop) {
        this(prop, new Settings());
    }

    public BlockFlib(Properties prop, Settings custom) {
        super(prop);
        this.me = custom;
        BlockState def = defaultBlockState();
        if (me.rotateColour) {
            def = def.setValue(COLOUR, DyeColor.WHITE);
        }
        if (me.litWhenPowered) {
            def = def.setValue(LIT, Boolean.valueOf(false));
        }
        this.registerDefaultState(def);
    }
}
