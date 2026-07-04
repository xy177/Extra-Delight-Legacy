package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipe;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipeManager;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import javax.annotation.Nullable;

public class TileEntityEvaporator extends TileEntity implements ITickable, IFluidHandler {
    public static final int SLOT_COUNT = 9;
    public static final int TANK_CAPACITY = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private FluidStack fluidTank;
    private int cookTime;
    private int cookTimeTotal;

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getCookTimeTotal() {
        return cookTimeTotal;
    }

    public boolean hasOutput() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        EvaporatorRecipe recipe = EvaporatorRecipeManager.find(fluidTank);
        if (recipe == null || !world.canBlockSeeSky(pos.up()) || world.getBiome(pos).getTemperature(pos) <= 0.05F) {
            cookTimeTotal = recipe == null ? 0 : recipe.getCookingTime();
            markUpdated();
            return;
        }

        cookTimeTotal = recipe.getCookingTime();
        float temperature = world.getBiome(pos).getTemperature(pos);
        if (world.rand.nextFloat() > temperature) {
            return;
        }

        cookTime++;
        if (cookTime >= cookTimeTotal) {
            finishRecipe(recipe);
        }
        markUpdated();
    }

    public FluidContainerResult tryFillFromContainer(ItemStack stack) {
        FluidContainerResult result = getFillResult(stack);
        if (result == null || !canAcceptFluid(result.fluid)) {
            FluidStack contained = FluidUtil.getFluidContained(stack);
            FluidActionResult genericResult = FluidUtil.tryEmptyContainer(stack, this, TANK_CAPACITY, null, true);
            if (!genericResult.isSuccess() || contained == null) {
                return null;
            }
            cookTime = 0;
            cookTimeTotal = 0;
            markUpdated();
            return new FluidContainerResult(contained, genericResult.getResult());
        }
        addFluid(result.fluid);
        cookTime = 0;
        cookTimeTotal = 0;
        markUpdated();
        return result;
    }

    public void dropItems() {
        if (world == null || world.isRemote) {
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.35D, pos.getZ() + 0.5D, stack.copy());
                entity.setDefaultPickupDelay();
                world.spawnEntity(entity);
                items.set(i, ItemStack.EMPTY);
            }
        }
        markUpdated();
    }

    private void finishRecipe(EvaporatorRecipe recipe) {
        ItemStack output = recipe.getOutput();
        insertOutput(output);
        drain(recipe.getFluid().amount);
        cookTime = 0;
        cookTimeTotal = 0;
    }

    private void insertOutput(ItemStack output) {
        ItemStack remaining = output.copy();
        for (int i = 0; i < items.size(); i++) {
            ItemStack current = items.get(i);
            if (current.isEmpty()) {
                items.set(i, remaining.copy());
                return;
            }
            if (ItemStack.areItemsEqual(current, remaining)
                && ItemStack.areItemStackTagsEqual(current, remaining)
                && current.getCount() < current.getMaxStackSize()) {
                int move = Math.min(remaining.getCount(), current.getMaxStackSize() - current.getCount());
                current.grow(move);
                remaining.shrink(move);
                if (remaining.isEmpty()) {
                    return;
                }
            }
        }
    }

    private FluidContainerResult getFillResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }

        FluidContainerHelper.FillResult result = FluidContainerHelper.getFillResult(stack);
        return result == null ? null : new FluidContainerResult(result.fluid, result.container);
    }

    private boolean canAcceptFluid(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null || hasOutput()) {
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
            cookTimeTotal = 0;
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
            cookTimeTotal = 0;
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

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                items.get(i).writeToNBT(itemTag);
                compound.setTag("Item" + i, itemTag);
            }
        }
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
        for (int i = 0; i < items.size(); i++) {
            items.set(i, compound.hasKey("Item" + i, 10)
                ? new ItemStack(compound.getCompoundTag("Item" + i))
                : ItemStack.EMPTY);
        }
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

    public static final class FluidContainerResult {
        private final FluidStack fluid;
        private final ItemStack emptyContainer;

        private FluidContainerResult(FluidStack fluid, ItemStack emptyContainer) {
            this.fluid = fluid.copy();
            this.emptyContainer = emptyContainer.copy();
        }

        public ItemStack getEmptyContainer() {
            return emptyContainer.copy();
        }
    }
}
