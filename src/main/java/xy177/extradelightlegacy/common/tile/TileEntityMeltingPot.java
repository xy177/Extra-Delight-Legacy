package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipe;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipeManager;
import xy177.extradelightlegacy.common.util.EDLHeatSources;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import javax.annotation.Nullable;

public class TileEntityMeltingPot extends TileEntity implements ITickable, IFluidHandler {
    public static final int INPUT_SLOT = 0;
    public static final int CONTAINER_SLOT = 1;
    public static final int CONTAINER_OUT_SLOT = 2;
    public static final int SLOT_COUNT = 3;
    public static final int TANK_CAPACITY = 6000;
    private static final int BUCKET_VOLUME = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private FluidStack fluidTank;
    private int cookTime;
    private int cookTimeTotal;

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getCookTimeTotal() {
        return cookTimeTotal;
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        handleContainerSlots();
        MeltingPotRecipe recipe = MeltingPotRecipeManager.find(items.get(INPUT_SLOT));
        if (recipe == null || !isHeated() || !canAcceptFluid(recipe.getOutput())) {
            cookTime = 0;
            cookTimeTotal = recipe == null ? 0 : recipe.getCookingTime();
            markUpdated();
            return;
        }

        cookTimeTotal = recipe.getCookingTime();
        cookTime++;
        if (cookTime >= cookTimeTotal) {
            craft(recipe);
        }
        markUpdated();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (slot == INPUT_SLOT) {
            return MeltingPotRecipeManager.isValidInput(stack);
        }
        if (slot == CONTAINER_SLOT) {
            return canDrainToContainer(stack);
        }
        if (slot == CONTAINER_OUT_SLOT) {
            return false;
        }
        return false;
    }

    public boolean handleFluidContainer(EntityPlayer player, ItemStack held) {
        if (held.isEmpty() || !canDrainToContainer(held)) {
            return false;
        }

        ItemStack filled = tryDrainToContainer(held);
        if (filled.isEmpty()) {
            return false;
        }

        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
        }
        if (!player.inventory.addItemStackToInventory(filled)) {
            player.dropItem(filled, false);
        }
        return true;
    }

    public boolean canDrainToContainer(ItemStack stack) {
        return !getDrainResult(stack).isEmpty()
            || FluidUtil.tryFillContainer(stack, this, TANK_CAPACITY, null, false).isSuccess();
    }

    public ItemStack tryDrainToContainer(ItemStack emptyContainer) {
        ItemStack filled = getDrainResult(emptyContainer);
        if (!filled.isEmpty()) {
            int amount = emptyContainer.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
            drain(amount);
            cookTime = 0;
            markUpdated();
            return filled;
        }

        FluidActionResult genericResult = FluidUtil.tryFillContainer(emptyContainer, this, TANK_CAPACITY, null, true);
        if (!genericResult.isSuccess()) {
            return ItemStack.EMPTY;
        }
        cookTime = 0;
        markUpdated();
        return genericResult.getResult();
    }

    public void clear() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        fluidTank = null;
        cookTime = 0;
        cookTimeTotal = 0;
    }

    private void handleContainerSlots() {
        ItemStack inputContainer = items.get(CONTAINER_SLOT);
        if (!inputContainer.isEmpty() && items.get(CONTAINER_OUT_SLOT).isEmpty()) {
            ItemStack filled = tryDrainToContainer(inputContainer);
            if (!filled.isEmpty()) {
                inputContainer.shrink(1);
                items.set(CONTAINER_SLOT, inputContainer.isEmpty() ? ItemStack.EMPTY : inputContainer);
                items.set(CONTAINER_OUT_SLOT, filled);
            }
        }

        ItemStack outputContainer = items.get(CONTAINER_OUT_SLOT);
        if (outputContainer.isEmpty()) {
            return;
        }
        ItemStack filled = tryDrainToContainer(outputContainer);
        if (filled.isEmpty()) {
            return;
        }
        items.set(CONTAINER_OUT_SLOT, filled);
    }

    private ItemStack getDrainResult(ItemStack emptyContainer) {
        if (emptyContainer.isEmpty() || fluidTank == null || fluidTank.amount <= 0) {
            return ItemStack.EMPTY;
        }
        if (emptyContainer.getItem() == Items.BUCKET && fluidTank.amount >= BUCKET_VOLUME) {
            return FluidContainerHelper.bucketForFluid(fluidTank.getFluid());
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && fluidTank.amount >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            return FluidContainerHelper.bottleForFluid(fluidTank.getFluid());
        }
        return ItemStack.EMPTY;
    }

    private void craft(MeltingPotRecipe recipe) {
        ItemStack input = items.get(INPUT_SLOT);
        if (input.isEmpty()) {
            return;
        }

        addFluid(recipe.getOutput());
        input.shrink(1);
        if (input.isEmpty()) {
            items.set(INPUT_SLOT, ItemStack.EMPTY);
        }
        cookTime = 0;
        cookTimeTotal = 0;
        world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.25F, 1.5F);
    }

    private boolean canAcceptFluid(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return false;
        }
        if (fluidTank == null) {
            return incoming.amount <= TANK_CAPACITY;
        }
        return fluidTank.isFluidEqual(incoming) && fluidTank.amount + incoming.amount <= TANK_CAPACITY;
    }

    private void addFluid(FluidStack incoming) {
        if (fluidTank == null) {
            fluidTank = incoming.copy();
        } else if (fluidTank.isFluidEqual(incoming)) {
            fluidTank.amount = Math.min(TANK_CAPACITY, fluidTank.amount + incoming.amount);
        }
    }

    private void drain(int amount) {
        if (fluidTank == null) {
            return;
        }
        fluidTank.amount -= amount;
        if (fluidTank.amount <= 0) {
            fluidTank = null;
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{
            new FluidTankProperties(fluidTank == null ? null : fluidTank.copy(), TANK_CAPACITY, true, true)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!canAcceptFluid(resource)) {
            return 0;
        }
        int fillAmount = fluidTank == null
            ? Math.min(resource.amount, TANK_CAPACITY)
            : Math.min(resource.amount, TANK_CAPACITY - fluidTank.amount);
        if (doFill && fillAmount > 0) {
            FluidStack accepted = resource.copy();
            accepted.amount = fillAmount;
            addFluid(accepted);
            cookTime = 0;
            markUpdated();
        }
        return fillAmount;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || fluidTank == null || !fluidTank.isFluidEqual(resource)) {
            return null;
        }
        return drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluidTank == null || maxDrain <= 0) {
            return null;
        }
        int drained = Math.min(maxDrain, fluidTank.amount);
        FluidStack result = fluidTank.copy();
        result.amount = drained;
        if (doDrain) {
            fluidTank.amount -= drained;
            if (fluidTank.amount <= 0) {
                fluidTank = null;
            }
            cookTime = 0;
            markUpdated();
        }
        return result;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    public boolean isHeated() {
        return EDLHeatSources.isCookwareHeated(world, pos);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("CookTimeTotal", cookTimeTotal);
        if (fluidTank != null && fluidTank.amount > 0) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTank.writeToNBT(fluidTag);
            compound.setTag("Fluid", fluidTag);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        items.set(INPUT_SLOT, ItemStack.EMPTY);
        items.set(CONTAINER_SLOT, ItemStack.EMPTY);
        items.set(CONTAINER_OUT_SLOT, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, items);
        cookTime = compound.getInteger("CookTime");
        cookTimeTotal = compound.getInteger("CookTimeTotal");
        fluidTank = compound.hasKey("Fluid", 10)
            ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid"))
            : null;
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

    public void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}
