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
import xy177.extradelightlegacy.common.tile.TileEntityMeltingPot;

public class ContainerMeltingPot extends Container {
    private static final int PLAYER_INV_START = TileEntityMeltingPot.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 27;
    private static final int HOTBAR_START = PLAYER_INV_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    private final World world;
    private final BlockPos pos;
    private final TileEntityMeltingPot pot;
    private final MeltingPotInventory inventory = new MeltingPotInventory();

    public ContainerMeltingPot(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.pot = (TileEntityMeltingPot) world.getTileEntity(pos);
        syncFromTile();

        addSlotToContainer(new MeltingInputSlot(inventory, TileEntityMeltingPot.INPUT_SLOT, 46, 23));
        addSlotToContainer(new ContainerInputSlot(inventory, TileEntityMeltingPot.CONTAINER_SLOT, 114, 26));
        addSlotToContainer(new ContainerOutputSlot(inventory, TileEntityMeltingPot.CONTAINER_OUT_SLOT, 114, 50));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 83 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 141));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return EDLBlocks.MELTING_POT.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.MELTING_POT.getBlock()
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
        if (index < TileEntityMeltingPot.SLOT_COUNT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pot != null && pot.canDrainToContainer(stack)) {
            if (!mergeItemStack(stack, TileEntityMeltingPot.CONTAINER_SLOT, TileEntityMeltingPot.CONTAINER_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pot != null && pot.isItemValidForSlot(TileEntityMeltingPot.INPUT_SLOT, stack)) {
            if (!mergeItemStack(stack, TileEntityMeltingPot.INPUT_SLOT, TileEntityMeltingPot.INPUT_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            if (!mergeItemStack(stack, HOTBAR_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= HOTBAR_START && index < HOTBAR_END
            && !mergeItemStack(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        }
        slot.onSlotChanged();
        return copied;
    }

    public TileEntityMeltingPot getPot() {
        return pot;
    }

    private void syncFromTile() {
        if (pot == null) {
            return;
        }
        inventory.suppressChange = true;
        for (int i = 0; i < TileEntityMeltingPot.SLOT_COUNT; i++) {
            inventory.setInventorySlotContents(i, pot.getItems().get(i).copy());
        }
        inventory.suppressChange = false;
    }

    private final class MeltingPotInventory extends InventoryBasic {
        private boolean suppressChange;

        private MeltingPotInventory() {
            super("extradelightlegacy.melting_pot", false, TileEntityMeltingPot.SLOT_COUNT);
        }
    }

    private class MeltingPotSlot extends Slot {
        private MeltingPotSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return pot != null && pot.isItemValidForSlot(getSlotIndex(), stack);
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerMeltingPot.this.inventory.suppressChange && pot != null) {
                pot.getItems().set(getSlotIndex(), getStack().copy());
                pot.markUpdated();
            }
            super.onSlotChanged();
        }
    }

    private final class MeltingInputSlot extends MeltingPotSlot {
        private MeltingInputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }
    }

    private final class ContainerInputSlot extends MeltingPotSlot {
        private ContainerInputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

    private final class ContainerOutputSlot extends MeltingPotSlot {
        private ContainerOutputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }
}
