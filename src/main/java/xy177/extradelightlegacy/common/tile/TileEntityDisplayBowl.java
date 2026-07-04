package xy177.extradelightlegacy.common.tile;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityDisplayBowl extends TileEntity implements IStyleableTile {
    public static final int SLOT_COUNT = 9;
    public static final String TAG_INVENTORY = "inv";
    private static final int STYLE_COUNT = 50;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int style;

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

    public NonNullList<ItemStack> copyItems() {
        NonNullList<ItemStack> copy = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_COUNT; i++) {
            copy.set(i, items.get(i).copy());
        }
        return copy;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        compound.setInteger("Style", style);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for (int i = 0; i < SLOT_COUNT; i++) {
            items.set(i, ItemStack.EMPTY);
        }
        ItemStackHelper.loadAllItems(compound, items);
        style = Math.max(0, Math.min(STYLE_COUNT - 1, compound.getInteger("Style")));
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

    @Override
    public int getStyleCount() {
        return STYLE_COUNT;
    }

    @Override
    public int getStyle() {
        return style;
    }

    @Override
    public void setStyle(int style) {
        this.style = Math.max(0, Math.min(STYLE_COUNT - 1, style));
        markUpdated();
    }
}
