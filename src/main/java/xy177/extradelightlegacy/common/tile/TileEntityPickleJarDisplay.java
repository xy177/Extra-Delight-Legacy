package xy177.extradelightlegacy.common.tile;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityPickleJarDisplay extends TileEntity {
    public static final int SLOT_COUNT = 4;
    private final NonNullList<ItemStack> jars = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    public ItemStack getStackInSlot(int slot) {
        return slot < 0 || slot >= SLOT_COUNT ? ItemStack.EMPTY : jars.get(slot);
    }

    public boolean addJar(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (jars.get(i).isEmpty()) {
                ItemStack inserted = stack.copy();
                inserted.setCount(1);
                jars.set(i, inserted);
                markUpdated();
                return true;
            }
        }
        return false;
    }

    public ItemStack removeLastJar() {
        for (int i = SLOT_COUNT - 1; i >= 0; i--) {
            ItemStack stack = jars.get(i);
            if (!stack.isEmpty()) {
                jars.set(i, ItemStack.EMPTY);
                markUpdated();
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getLastJar() {
        for (int i = SLOT_COUNT - 1; i >= 0; i--) {
            ItemStack stack = jars.get(i);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public int getFilledSlots() {
        int filled = 0;
        for (ItemStack stack : jars) {
            if (!stack.isEmpty()) {
                filled++;
            }
        }
        return filled;
    }

    public NonNullList<ItemStack> copyJars() {
        NonNullList<ItemStack> copy = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_COUNT; i++) {
            copy.set(i, jars.get(i).copy());
        }
        return copy;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, jars);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        jars.clear();
        ItemStackHelper.loadAllItems(compound, jars);
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
