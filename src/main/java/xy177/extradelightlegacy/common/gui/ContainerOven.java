package xy177.extradelightlegacy.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.tile.TileEntityOven;

public class ContainerOven extends Container {
    private static final int PLAYER_INV_START = TileEntityOven.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 27;
    private static final int HOTBAR_START = PLAYER_INV_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    private final World world;
    private final BlockPos pos;
    private final TileEntityOven oven;
    private final OvenInventory inventory = new OvenInventory();

    public ContainerOven(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.oven = (TileEntityOven) world.getTileEntity(pos);
        syncFromTile();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                addSlotToContainer(new OvenSlot(inventory, row * 3 + column, 30 + column * 18, 17 + row * 18));
            }
        }
        addSlotToContainer(new OvenSlot(inventory, TileEntityOven.CONTAINER_SLOT, 92, 63));
        addSlotToContainer(new ResultSlot(inventory, TileEntityOven.RESULT_SLOT, 124, 36));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 102 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 160));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return EDLBlocks.OVEN.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.OVEN.getBlock()
            && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        syncFromTile();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack copied = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        copied = stack.copy();
        if (index < TileEntityOven.SLOT_COUNT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!mergeIntoOven(stack)) {
            if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
                if (!mergeItemStack(stack, HOTBAR_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= HOTBAR_START && index < HOTBAR_END
                && !mergeItemStack(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        }
        slot.onSlotChanged();
        return copied;
    }

    private boolean mergeIntoOven(ItemStack stack) {
        if (oven != null && oven.isItemValidForSlot(TileEntityOven.CONTAINER_SLOT, stack)
            && mergeItemStack(stack, TileEntityOven.CONTAINER_SLOT, TileEntityOven.CONTAINER_SLOT + 1, false)) {
            return true;
        }
        return mergeItemStack(stack, 0, TileEntityOven.INGREDIENT_SLOTS, false);
    }

    public TileEntityOven getOven() {
        return oven;
    }

    private void syncFromTile() {
        if (oven == null) {
            return;
        }
        inventory.suppressChange = true;
        for (int i = 0; i < TileEntityOven.SLOT_COUNT; i++) {
            inventory.setInventorySlotContents(i, oven.getItems().get(i).copy());
        }
        inventory.suppressChange = false;
    }

    private final class OvenInventory extends InventoryBasic {
        private boolean suppressChange;

        private OvenInventory() {
            super("extradelightlegacy.oven", false, TileEntityOven.SLOT_COUNT);
        }
    }

    private class OvenSlot extends Slot {
        private OvenSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return oven != null && oven.isItemValidForSlot(getSlotIndex(), stack);
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerOven.this.inventory.suppressChange && oven != null) {
                oven.getItems().set(getSlotIndex(), getStack().copy());
                oven.markUpdated();
            }
            super.onSlotChanged();
        }
    }

    private final class ResultSlot extends OvenSlot {
        private ResultSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }
}
