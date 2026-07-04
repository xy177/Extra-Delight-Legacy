package xy177.extradelightlegacy.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityDisplayCabinet;

public class ContainerDisplayCabinet extends Container {
    private final TileEntityDisplayCabinet cabinet;
    private final int cabinetSlots;
    private boolean storageTab = true;

    public ContainerDisplayCabinet(InventoryPlayer playerInventory, World world, BlockPos pos,
                                   int cabinetSlots, int displaySlot, boolean sinkLayout) {
        TileEntity tile = world.getTileEntity(pos);
        this.cabinet = tile instanceof TileEntityDisplayCabinet ? (TileEntityDisplayCabinet) tile : null;
        this.cabinetSlots = cabinetSlots;

        if (cabinet != null) {
            cabinet.openInventory(playerInventory.player);
            if (sinkLayout) {
                addStorageGrid(11, 8, 0, 3, 3);
                addStorageGrid(113, 8, 9, 3, 3);
            } else {
                addStorageGrid(8, 8, 0, 9, 3);
            }
            addSlotToContainer(new SlotDisplayBlock(cabinet, displaySlot, 80, 36, false));
        }

        int playerY = 68;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, playerY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlotToContainer(new Slot(playerInventory, col, 8 + col * 18, 126));
        }
    }

    private void addStorageGrid(int startX, int startY, int slotOffset, int cols, int rows) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                addSlotToContainer(new SlotHideable(cabinet, slotOffset + col + row * cols, startX + col * 18, startY + row * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return cabinet != null && cabinet.isUsableByPlayer(playerIn);
    }

    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id) {
        if (id == 0) {
            switchTabs();
            return true;
        }
        return super.enchantItem(playerIn, id);
    }

    public void switchTabs() {
        storageTab = !storageTab;
        for (Slot slot : inventorySlots) {
            if (slot instanceof SlotDisplayBlock) {
                ((SlotDisplayBlock) slot).setActive(!storageTab);
            } else if (slot instanceof SlotHideable) {
                ((SlotHideable) slot).setActive(storageTab);
            }
        }
    }

    public boolean isStorageTab() {
        return storageTab;
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
        int playerStart = cabinetSlots;
        int playerEnd = playerStart + 36;
        if (index < cabinetSlots) {
            if (slot instanceof SlotHideable && !((SlotHideable) slot).isActive()) {
                return ItemStack.EMPTY;
            }
            if (slot instanceof SlotDisplayBlock && !((SlotDisplayBlock) slot).isActive()) {
                return ItemStack.EMPTY;
            }
            if (!mergeItemStack(stack, playerStart, playerEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else if (stack.getItem() instanceof ItemBlock && !storageTab) {
            if (!mergeItemStack(stack, cabinetSlots - 1, cabinetSlots, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!storageTab) {
            return ItemStack.EMPTY;
        } else if (!mergeItemStack(stack, 0, cabinetSlots - 1, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (cabinet != null) {
            cabinet.closeInventory(playerIn);
        }
    }

    private static class SlotHideable extends Slot {
        private boolean active = true;

        private SlotHideable(TileEntityDisplayCabinet inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        private void setActive(boolean active) {
            this.active = active;
        }

        private boolean isActive() {
            return active;
        }

        @Override
        public boolean isEnabled() {
            return active;
        }
    }
}
