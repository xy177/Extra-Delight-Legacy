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
import xy177.extradelightlegacy.common.tile.TileEntityChiller;

public class ContainerChiller extends Container {
    private static final int PLAYER_INV_START = TileEntityChiller.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 27;
    private static final int HOTBAR_START = PLAYER_INV_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    private final World world;
    private final BlockPos pos;
    private final TileEntityChiller chiller;
    private final ChillerInventory inventory = new ChillerInventory();

    public ContainerChiller(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.chiller = (TileEntityChiller) world.getTileEntity(pos);
        syncFromTile();

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++) {
                addSlotToContainer(new ChillerSlot(inventory, row * 2 + column, 64 + column * 18, 32 + row * 18));
            }
        }
        addSlotToContainer(new ChillerSlot(inventory, TileEntityChiller.CONTAINER_SLOT, 73, 68));
        addSlotToContainer(new FluidInputSlot(inventory, TileEntityChiller.FLUID_IN_SLOT, 19, 13));
        addSlotToContainer(new FluidOutputSlot(inventory, TileEntityChiller.FLUID_OUT_SLOT, 19, 68));
        addSlotToContainer(new ChillerSlot(inventory, TileEntityChiller.ICE_SLOT, 126, 10));
        addSlotToContainer(new ResultSlot(inventory, TileEntityChiller.RESULT_SLOT, 126, 42));
        addSlotToContainer(new DripTrayOutputSlot(inventory, TileEntityChiller.DRIP_TRAY_OUT_SLOT, 150, 68));

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
        return EDLBlocks.CHILLER.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.CHILLER.getBlock()
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
        if (index < TileEntityChiller.SLOT_COUNT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!mergeIntoChiller(stack)) {
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

    private boolean mergeIntoChiller(ItemStack stack) {
        for (int slot : new int[]{TileEntityChiller.FLUID_IN_SLOT, TileEntityChiller.FLUID_OUT_SLOT,
            TileEntityChiller.DRIP_TRAY_OUT_SLOT, TileEntityChiller.ICE_SLOT, TileEntityChiller.CONTAINER_SLOT}) {
            if (chiller != null && chiller.isItemValidForSlot(slot, stack)
                && mergeItemStack(stack, slot, slot + 1, false)) {
                return true;
            }
        }
        return mergeItemStack(stack, 0, TileEntityChiller.INGREDIENT_SLOTS, false);
    }

    public TileEntityChiller getChiller() {
        return chiller;
    }

    private void syncFromTile() {
        if (chiller == null) {
            return;
        }
        inventory.suppressChange = true;
        for (int i = 0; i < TileEntityChiller.SLOT_COUNT; i++) {
            if (i == TileEntityChiller.FLUID_IN_SLOT || i == TileEntityChiller.FLUID_OUT_SLOT
                || i == TileEntityChiller.DRIP_TRAY_OUT_SLOT) {
                continue;
            }
            inventory.setInventorySlotContents(i, chiller.getItems().get(i).copy());
        }
        inventory.suppressChange = false;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote && chiller != null) {
            returnTransientSlot(playerIn, TileEntityChiller.FLUID_IN_SLOT);
            returnTransientSlot(playerIn, TileEntityChiller.FLUID_OUT_SLOT);
            returnTransientSlot(playerIn, TileEntityChiller.DRIP_TRAY_OUT_SLOT);
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

    private final class ChillerInventory extends InventoryBasic {
        private boolean suppressChange;

        private ChillerInventory() {
            super("extradelightlegacy.chiller", false, TileEntityChiller.SLOT_COUNT);
        }
    }

    private class ChillerSlot extends Slot {
        private ChillerSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return chiller != null && chiller.isItemValidForSlot(getSlotIndex(), stack);
        }

        @Override
        public int getSlotStackLimit() {
            int slot = getSlotIndex();
            if (slot == TileEntityChiller.FLUID_IN_SLOT || slot == TileEntityChiller.FLUID_OUT_SLOT
                || slot == TileEntityChiller.DRIP_TRAY_OUT_SLOT) {
                return 1;
            }
            return super.getSlotStackLimit();
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerChiller.this.inventory.suppressChange && chiller != null) {
                chiller.getItems().set(getSlotIndex(), getStack().copy());
                chiller.markDirty();
            }
            super.onSlotChanged();
        }
    }

    private final class FluidInputSlot extends Slot {
        private FluidInputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return chiller != null
                && chiller.canFillFromContainer(stack)
                && inventory.getStackInSlot(TileEntityChiller.FLUID_OUT_SLOT).isEmpty();
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerChiller.this.inventory.suppressChange && chiller != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    TileEntityChiller.FluidContainerResult result = chiller.tryFillFromContainer(stack);
                    if (result != null) {
                        ContainerChiller.this.inventory.suppressChange = true;
                        putStack(ItemStack.EMPTY);
                        ContainerChiller.this.inventory.setInventorySlotContents(
                            TileEntityChiller.FLUID_OUT_SLOT,
                            result.getEmptyContainer()
                        );
                        ContainerChiller.this.inventory.suppressChange = false;
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
            return chiller != null && chiller.canDrainToContainer(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerChiller.this.inventory.suppressChange && chiller != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    ItemStack filled = chiller.tryDrainToContainer(stack);
                    if (!filled.isEmpty()) {
                        putStack(filled);
                    }
                }
            }
            super.onSlotChanged();
        }
    }

    private final class DripTrayOutputSlot extends Slot {
        private DripTrayOutputSlot(InventoryBasic inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return chiller != null && chiller.isItemValidForSlot(TileEntityChiller.DRIP_TRAY_OUT_SLOT, stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerChiller.this.inventory.suppressChange && chiller != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    ItemStack filled = chiller.tryDrainDripToContainer(stack);
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
