package xy177.extradelightlegacy.common.tile;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityChocolateBox extends TileEntity {
    public static final int SLOT_COUNT = 8;
    public static final String TAG_INVENTORY = "inv";
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    public ItemStack getStackInSlot(int slot) {
        return slot < 0 || slot >= SLOT_COUNT ? ItemStack.EMPTY : items.get(slot);
    }

    public boolean addItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (items.get(i).isEmpty()) {
                ItemStack inserted = stack.copy();
                inserted.setCount(1);
                items.set(i, inserted);
                markUpdated();
                return true;
            }
        }
        return false;
    }

    public ItemStack removeLastItem() {
        for (int i = SLOT_COUNT - 1; i >= 0; i--) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                items.set(i, ItemStack.EMPTY);
                markUpdated();
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public int getFilledSlots() {
        int filled = 0;
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                filled++;
            }
        }
        return filled;
    }

    public NonNullList<ItemStack> copyItems() {
        NonNullList<ItemStack> copy = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_COUNT; i++) {
            copy.set(i, items.get(i).copy());
        }
        return copy;
    }

    public NBTTagCompound writeInventoryToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        ItemStackHelper.saveAllItems(tag, items);
        return tag;
    }

    public void readInventoryFromNBT(NBTTagCompound tag) {
        items.clear();
        ItemStackHelper.loadAllItems(tag, items);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag(TAG_INVENTORY, writeInventoryToNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(TAG_INVENTORY, 10)) {
            readInventoryFromNBT(compound.getCompoundTag(TAG_INVENTORY));
        } else {
            readInventoryFromNBT(compound);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    private void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }
}
