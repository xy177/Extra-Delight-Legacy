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
import xy177.extradelightlegacy.common.tile.TileEntityVat;

public class ContainerVat extends Container {
    private static final int PLAYER_INV_START = TileEntityVat.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 27;
    private static final int HOTBAR_START = PLAYER_INV_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    private final World world;
    private final BlockPos pos;
    private final TileEntityVat vat;
    private final VatInventory inventory = new VatInventory();

    public ContainerVat(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.vat = (TileEntityVat) world.getTileEntity(pos);
        syncFromTile();

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                addSlotToContainer(new VatSlot(inventory, row * 3 + column, 55 + column * 18, -5 + row * 18));
            }
        }
        addSlotToContainer(new VatSlot(inventory, TileEntityVat.STAGE_SLOT, 55, 51));
        addSlotToContainer(new FluidInputSlot(inventory, TileEntityVat.FLUID_IN_SLOT, 8, -5));
        addSlotToContainer(new FluidOutputSlot(inventory, TileEntityVat.FLUID_OUT_SLOT, 8, 51));
        addSlotToContainer(new VatSlot(inventory, TileEntityVat.CONTAINER_SLOT, 151, 32));
        addSlotToContainer(new ResultSlot(inventory, TileEntityVat.RESULT_SLOT, 151, 51));

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
        return EDLBlocks.VAT.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.VAT.getBlock()
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
        if (index < TileEntityVat.SLOT_COUNT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!mergeIntoVat(stack)) {
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

    private boolean mergeIntoVat(ItemStack stack) {
        for (int slot : new int[]{TileEntityVat.FLUID_IN_SLOT, TileEntityVat.FLUID_OUT_SLOT,
            TileEntityVat.STAGE_SLOT, TileEntityVat.CONTAINER_SLOT}) {
            if (vat != null && vat.isItemValidForSlot(slot, stack)
                && mergeItemStack(stack, slot, slot + 1, false)) {
                return true;
            }
        }
        return mergeItemStack(stack, 0, TileEntityVat.INGREDIENT_SLOTS, false);
    }

    public TileEntityVat getVat() {
        return vat;
    }

    private void syncFromTile() {
        if (vat == null) {
            return;
        }
        inventory.suppressChange = true;
        for (int i = 0; i < TileEntityVat.SLOT_COUNT; i++) {
            if (i == TileEntityVat.FLUID_IN_SLOT || i == TileEntityVat.FLUID_OUT_SLOT) {
                continue;
            }
            inventory.setInventorySlotContents(i, vat.getItems().get(i).copy());
        }
        inventory.suppressChange = false;
    }

    private void syncTileFromInventory() {
        if (vat == null) {
            return;
        }
        for (int i = 0; i < TileEntityVat.SLOT_COUNT; i++) {
            if (i == TileEntityVat.FLUID_IN_SLOT || i == TileEntityVat.FLUID_OUT_SLOT) {
                continue;
            }
            vat.getItems().set(i, inventory.getStackInSlot(i).copy());
        }
        vat.markUpdated();
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote && vat != null) {
            returnTransientSlot(playerIn, TileEntityVat.FLUID_IN_SLOT);
            returnTransientSlot(playerIn, TileEntityVat.FLUID_OUT_SLOT);
        }
    }

    private void returnTransientSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = inventory.getStackInSlot(slotIndex);
        if (!stack.isEmpty()) {
            inventory.setInventorySlotContents(slotIndex, ItemStack.EMPTY);
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }
        }
    }

    private final class VatInventory extends InventoryBasic {
        private boolean suppressChange;

        private VatInventory() {
            super("extradelightlegacy.vat", false, TileEntityVat.SLOT_COUNT);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            if (!suppressChange) {
                syncTileFromInventory();
            }
        }
    }

    private class VatSlot extends Slot {
        private VatSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return vat != null && vat.isItemValidForSlot(getSlotIndex(), stack);
        }

        @Override
        public void onSlotChanged() {
            super.onSlotChanged();
        }
    }

    private final class FluidInputSlot extends Slot {
        private FluidInputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return vat != null
                && vat.canFillFromContainer(stack)
                && inventory.getStackInSlot(TileEntityVat.FLUID_OUT_SLOT).isEmpty();
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerVat.this.inventory.suppressChange && vat != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    TileEntityVat.FluidContainerResult result = vat.tryFillFromContainer(stack);
                    if (result != null) {
                        ContainerVat.this.inventory.suppressChange = true;
                        putStack(ItemStack.EMPTY);
                        ContainerVat.this.inventory.setInventorySlotContents(TileEntityVat.FLUID_OUT_SLOT, result.getEmptyContainer());
                        ContainerVat.this.inventory.suppressChange = false;
                    }
                }
            }
            super.onSlotChanged();
        }
    }

    private final class FluidOutputSlot extends Slot {
        private FluidOutputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return vat != null && vat.canDrainToContainer(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerVat.this.inventory.suppressChange && vat != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    ItemStack filled = vat.tryDrainToContainer(stack);
                    if (!filled.isEmpty()) {
                        putStack(filled);
                    }
                }
            }
            super.onSlotChanged();
        }
    }

    private final class ResultSlot extends Slot {
        private ResultSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }
}
