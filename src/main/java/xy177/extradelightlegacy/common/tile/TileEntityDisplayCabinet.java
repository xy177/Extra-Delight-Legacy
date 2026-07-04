package xy177.extradelightlegacy.common.tile;

import com.wdcftgg.farmersdelightlegacy.common.registry.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.block.BlockCounterCabinet;

public class TileEntityDisplayCabinet extends TileEntity implements IInventory {
    public static final String TAG_INVENTORY = "inv";
    private final int slots;
    private final int displaySlot;
    private final String nameKey;
    private NonNullList<ItemStack> items;
    private int openerCount;

    public TileEntityDisplayCabinet(int slots, int displaySlot, String nameKey) {
        this.slots = slots;
        this.displaySlot = displaySlot;
        this.nameKey = nameKey;
        this.items = NonNullList.withSize(slots, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {
        return slots;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index < 0 || index >= slots ? ItemStack.EMPTY : items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(items, index, count);
        if (!stack.isEmpty()) {
            markUpdated();
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = ItemStackHelper.getAndRemove(items, index);
        if (!stack.isEmpty()) {
            markUpdated();
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
        markUpdated();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(net.minecraft.entity.player.EntityPlayer player) {
        return world != null && world.getTileEntity(pos) == this && player.getDistanceSq(pos) <= 64.0D;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void openInventory(EntityPlayer player) {
        if (!(this instanceof TileEntityCounterCabinet) || player.isSpectator() || world == null || world.isRemote) {
            return;
        }

        if (openerCount < 0) {
            openerCount = 0;
        }

        openerCount++;
        if (openerCount == 1) {
            updateCounterOpenState(true);
            playCabinetSound(ModSounds.cabinetOpen);
        }
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if (!(this instanceof TileEntityCounterCabinet) || player.isSpectator() || world == null || world.isRemote) {
            return;
        }

        if (openerCount > 0) {
            openerCount--;
        }

        if (openerCount <= 0) {
            openerCount = 0;
            updateCounterOpenState(false);
            playCabinetSound(ModSounds.CABINET_CLOSE);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index != displaySlot || stack.getItem() instanceof ItemBlock;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        items.clear();
        markUpdated();
    }

    @Override
    public String getName() {
        return nameKey;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(nameKey);
    }

    public int getDisplaySlot() {
        return displaySlot;
    }

    public NonNullList<ItemStack> copyItems() {
        NonNullList<ItemStack> copy = NonNullList.withSize(slots, ItemStack.EMPTY);
        for (int i = 0; i < slots; i++) {
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
        items = NonNullList.withSize(slots, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, items);
        markUpdated();
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

    protected void markUpdated() {
        markDirty();
        if (world != null && pos != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    private void updateCounterOpenState(boolean open) {
        if (world == null || pos == null) {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockCounterCabinet && state.getValue(BlockCounterCabinet.OPEN) != open) {
            world.setBlockState(pos, state.withProperty(BlockCounterCabinet.OPEN, open), 3);
        }
    }

    private void playCabinetSound(SoundEvent sound) {
        if (world == null || pos == null || sound == null) {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        EnumFacing facing = state.getBlock() instanceof BlockCounterCabinet
            ? state.getValue(BlockCounterCabinet.FACING)
            : EnumFacing.NORTH;
        Vec3i vector = facing.getDirectionVec();
        double x = pos.getX() + 0.5D + vector.getX() / 2.0D;
        double y = pos.getY() + 0.5D + vector.getY() / 2.0D;
        double z = pos.getZ() + 0.5D + vector.getZ() / 2.0D;
        world.playSound(null, x, y, z, sound, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    }

}
