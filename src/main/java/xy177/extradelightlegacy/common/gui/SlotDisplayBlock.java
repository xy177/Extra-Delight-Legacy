package xy177.extradelightlegacy.common.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotDisplayBlock extends Slot {
    private boolean active;

    public SlotDisplayBlock(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        this(inventoryIn, index, xPosition, yPosition, true);
    }

    public SlotDisplayBlock(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean active) {
        super(inventoryIn, index, xPosition, yPosition);
        this.active = active;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return active && !stack.isEmpty() && stack.getItem() instanceof ItemBlock;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
