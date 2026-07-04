package xy177.extradelightlegacy.common.gui;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.block.BlockPicnicBasket;
import xy177.extradelightlegacy.common.tile.TileEntityPicnicBasket;

public class ContainerPicnicBasket extends Container {
    private static final int BASKET_END = 12;
    private static final int PLAYER_START = BASKET_END;
    private static final int PLAYER_END = PLAYER_START + 27;
    private static final int HOTBAR_START = PLAYER_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;
    private final TileEntityPicnicBasket basket;

    public ContainerPicnicBasket(InventoryPlayer playerInventory, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        this.basket = tile instanceof TileEntityPicnicBasket ? (TileEntityPicnicBasket) tile : null;

        if (basket != null) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 4; col++) {
                    addSlotToContainer(new SlotPicnicBasket(basket, col + row * 4, 53 + col * 18, 8 + row * 18));
                }
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 68 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInventory, col, 8 + col * 18, 126));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return basket != null && basket.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return result;
        }

        ItemStack stack = slot.getStack();
        result = stack.copy();
        if (index < BASKET_END) {
            if (!mergeItemStack(stack, PLAYER_START, HOTBAR_END, true)) {
                return ItemStack.EMPTY;
            }
        } else if (!mergeItemStack(stack, 0, BASKET_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        return result;
    }

    private static class SlotPicnicBasket extends Slot {
        private SlotPicnicBasket(TileEntityPicnicBasket inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack.getItem() instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) stack.getItem();
                if (itemBlock.getBlock() instanceof BlockPicnicBasket || itemBlock.getBlock() instanceof BlockShulkerBox) {
                    return false;
                }
            }
            return super.isItemValid(stack);
        }
    }
}
