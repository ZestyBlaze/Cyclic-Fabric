package com.lothrazar.cyclic.flib;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerFlib extends AbstractContainerMenu {
    public static final int PLAYERSIZE = 4 * 9;
    protected Player playerEntity;
    protected Inventory playerInventory;
    protected int startInv = 0;
    protected int endInv = 17; //must be set by extending class

    protected ContainerFlib(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        try {
            //if last machine slot is 17, endInv is 18
            int playerStart = endInv;
            int playerEnd = endInv + PLAYERSIZE; //53 = 17 + 36
            //standard logic based on start/end
            ItemStack itemstack = ItemStack.EMPTY;
            Slot slot = this.slots.get(index);
            if (slot != null && slot.hasItem()) {
                ItemStack stack = slot.getItem();
                itemstack = stack.copy();
                if (index < this.endInv) {
                    if (!this.moveItemStackTo(stack, playerStart, playerEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index <= playerEnd && !this.moveItemStackTo(stack, startInv, endInv, false)) {
                    return ItemStack.EMPTY;
                }
                if (stack.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                }
                else {
                    slot.setChanged();
                }
                if (stack.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(player, stack);
            }
            return itemstack;
        }
        catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
