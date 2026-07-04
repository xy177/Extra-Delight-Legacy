package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipe;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipeManager;

public class TileEntityDryingRack extends TileEntity implements ITickable {
    public static final int SLOT_COUNT = 8;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private final int[] progress = new int[SLOT_COUNT];
    private final int[] cookingTime = new int[SLOT_COUNT];
    private final NonNullList<ItemStack> results = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);

    @Override
    public void update() {
        if (world == null) {
            return;
        }

        if (world.isRemote) {
            spawnDryingParticles();
            return;
        }

        boolean changed = false;
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty() || results.get(i).isEmpty() || cookingTime[i] <= 0) {
                continue;
            }

            progress[i]++;
            if (progress[i] >= cookingTime[i]) {
                items.set(i, results.get(i).copy());
                results.set(i, ItemStack.EMPTY);
                progress[i] = 0;
                cookingTime[i] = 0;
                changed = true;
            }
        }

        if (changed) {
            markUpdated();
        } else {
            markDirty();
        }
    }

    public boolean insertHeldItem(EntityPlayer player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }

        DryingRackRecipe recipe = DryingRackRecipeManager.find(held);
        if (recipe == null) {
            return false;
        }

        for (int i = 0; i < SLOT_COUNT; i++) {
            if (!items.get(i).isEmpty()) {
                continue;
            }

            ItemStack inserted = held.splitStack(1);
            items.set(i, inserted);
            results.set(i, recipe.getOutput());
            progress[i] = 0;
            cookingTime[i] = recipe.getCookingTime();
            markUpdated();
            return true;
        }
        return false;
    }

    public boolean extractOne(EntityPlayer player) {
        for (int i = SLOT_COUNT - 1; i >= 0; i--) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty()) {
                continue;
            }

            ItemStack extracted = stack.copy();
            items.set(i, ItemStack.EMPTY);
            results.set(i, ItemStack.EMPTY);
            progress[i] = 0;
            cookingTime[i] = 0;

            if (!player.inventory.addItemStackToInventory(extracted)) {
                player.dropItem(extracted, false);
            }
            markUpdated();
            return true;
        }
        return false;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public ItemStack getStackInSlot(int slot) {
        return slot < 0 || slot >= SLOT_COUNT ? ItemStack.EMPTY : items.get(slot);
    }

    public boolean isDrying(int slot) {
        return slot >= 0
            && slot < SLOT_COUNT
            && !items.get(slot).isEmpty()
            && !results.get(slot).isEmpty()
            && cookingTime[slot] > 0
            && progress[slot] < cookingTime[slot];
    }

    public void clear() {
        items.clear();
        results.clear();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        writeResults(compound);
        compound.setIntArray("Progress", progress);
        compound.setIntArray("CookingTime", cookingTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        items.clear();
        results.clear();
        ItemStackHelper.loadAllItems(compound, items);
        readResults(compound);
        copyArray(compound.getIntArray("Progress"), progress);
        copyArray(compound.getIntArray("CookingTime"), cookingTime);
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
    }

    private void writeResults(NBTTagCompound compound) {
        NBTTagCompound resultTag = new NBTTagCompound();
        ItemStackHelper.saveAllItems(resultTag, results);
        compound.setTag("Results", resultTag);
    }

    private void readResults(NBTTagCompound compound) {
        if (compound.hasKey("Results", 10)) {
            ItemStackHelper.loadAllItems(compound.getCompoundTag("Results"), results);
        }
    }

    private void copyArray(int[] source, int[] target) {
        int length = Math.min(source.length, target.length);
        for (int i = 0; i < length; i++) {
            target[i] = source[i];
        }
    }

    private void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    private void spawnDryingParticles() {
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (!isDrying(i)) {
                continue;
            }
            if (i > 3 && world.rand.nextInt(5) != 0) {
                continue;
            }

            double yOff = (i > 3 ? 0.5D : 0.0D) + 0.5D;
            double xOff = 0.0D;
            double zOff = 0.0D;
            switch (i % 4) {
                case 0:
                    xOff = 0.2D + world.rand.nextDouble() * 0.2D;
                    zOff = 0.2D + world.rand.nextDouble() * 0.2D;
                    break;
                case 1:
                    xOff = 0.2D + world.rand.nextDouble() * 0.2D;
                    zOff = 0.8D - world.rand.nextDouble() * 0.2D;
                    break;
                case 2:
                    xOff = 0.8D - world.rand.nextDouble() * 0.2D;
                    zOff = 0.8D - world.rand.nextDouble() * 0.2D;
                    break;
                default:
                    xOff = 0.8D - world.rand.nextDouble() * 0.2D;
                    zOff = 0.2D + world.rand.nextDouble() * 0.2D;
                    break;
            }

            world.spawnParticle(
                EnumParticleTypes.WATER_SPLASH,
                pos.getX() + world.rand.nextDouble() / 16.0D + xOff,
                pos.getY() - world.rand.nextDouble() / 16.0D + yOff,
                pos.getZ() + world.rand.nextDouble() / 16.0D + zOff,
                0.0D,
                i > 3 ? 0.04D : 0.2D,
                0.0D
            );
        }
    }
}
