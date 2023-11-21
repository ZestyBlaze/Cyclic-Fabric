package com.lothrazar.cyclic.item.redstone;

import com.lothrazar.cyclic.flib.ChatUtil;
import com.lothrazar.cyclic.flib.LevelWorldUtil;
import com.lothrazar.cyclic.flib.TagDataUtil;
import com.lothrazar.cyclic.item.ItemBaseCyclic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LeverRemote extends ItemBaseCyclic {
    public LeverRemote(Properties properties) {
        super(properties);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BlockPos pointer = TagDataUtil.getItemStackBlockPos(stack);
        if (pointer != null) {
            int dimensionTarget = stack.getOrCreateTag().getInt("LeverDim");
            tooltip.add(Component.translatable(ChatFormatting.RED + ChatUtil.blockPosToString(pointer) + " [" + dimensionTarget + "]"));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        ItemStack stack = playerIn.getItemInHand(hand);
        boolean success = false;
        success = trigger(stack, worldIn, playerIn);
        if (success) {
            playerIn.swing(hand);
            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
        }
        else {
            return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, stack);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        if (player.getCooldowns().isOnCooldown(this)) {
            return super.useOn(context);
        }
        ItemStack stack = player.getItemInHand(context.getHand());
        BlockPos pos = context.getClickedPos();
        if (world.getBlockState(pos).getBlock() instanceof LeverBlock) {
            TagDataUtil.setItemStackBlockPos(stack, pos);
            //and save dimension
            stack.getOrCreateTag().putString("LeverDim", LevelWorldUtil.dimensionToString(player.level()));
            //      UtilNBT.setItemStackNBTVal(stack, "LeverDim", player.dimension.getId());
            if (world.isClientSide) {
                ChatUtil.sendStatusMessage(player, this.getDescriptionId() + ".saved");
            }
            //      UtilSound.playSound(player, SoundEvents.BLOCK_LEVER_CLICK);
            return InteractionResult.SUCCESS;
        }
        else {
            boolean success = false;
            success = trigger(stack, world, player);
            if (success) {
                return InteractionResult.SUCCESS;
            }
            else {
                return InteractionResult.FAIL;
            }
        }
        //    return super.onItemUse(context);
    }

    private boolean trigger(ItemStack stack, Level world, Player player) {
        BlockPos blockPos = TagDataUtil.getItemStackBlockPos(stack);
        //default is zero which is ok
        if (blockPos == null) {
            if (world.isClientSide) {
                ChatUtil.sendStatusMessage(player, this.getDescriptionId() + ".invalid");
            }
            return false;
        }
        String dimensionTarget = stack.getOrCreateTag().getString("LeverDim");
        //check if we can avoid crossing dimensions
        String currentDim = LevelWorldUtil.dimensionToString(player.level());
        if (dimensionTarget.equalsIgnoreCase(currentDim)) { //same dim eh
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState == null || blockState.getBlock() != Blocks.LEVER) {
                if (world.isClientSide) {
                    ChatUtil.sendStatusMessage(player, this.getDescriptionId() + ".invalid");
                }
                return false;
            }
            blockState = world.getBlockState(blockPos);
            boolean hasPowerHere = blockState.getValue(LeverBlock.POWERED).booleanValue();
            LevelWorldUtil.toggleLeverPowerState(world, blockPos, blockState);
            ChatUtil.sendStatusMessage(player, this.getDescriptionId() + ".powered." + (!hasPowerHere));
            return true;
        }
        return false;
    }
}
