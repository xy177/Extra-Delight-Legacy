package xy177.extradelightlegacy.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileEntityKeg extends TileEntity implements IFluidHandler {
    public static final int CAPACITY = 8000;
    private static final String FLUID_TAG = "Fluid";

    private FluidStack fluid;

    public FluidStack getFluid() {
        return fluid == null ? null : fluid.copy();
    }

    public float getFullness() {
        return fluid == null ? 0.0F : (float) fluid.amount / (float) CAPACITY;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{
            new FluidTankProperties(fluid == null ? null : fluid.copy(), CAPACITY, true, true)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!canAccept(resource)) {
            return 0;
        }
        int accepted = fluid == null
            ? Math.min(resource.amount, CAPACITY)
            : Math.min(resource.amount, CAPACITY - fluid.amount);
        if (doFill && accepted > 0) {
            FluidStack incoming = resource.copy();
            incoming.amount = accepted;
            if (fluid == null) {
                fluid = incoming;
            } else {
                fluid.amount += accepted;
            }
            markUpdated();
        }
        return accepted;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || fluid == null || !fluid.isFluidEqual(resource)) {
            return null;
        }
        return drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid == null || maxDrain <= 0) {
            return null;
        }
        int amount = Math.min(maxDrain, fluid.amount);
        FluidStack drained = fluid.copy();
        drained.amount = amount;
        if (doDrain) {
            fluid.amount -= amount;
            if (fluid.amount <= 0) {
                fluid = null;
            }
            markUpdated();
        }
        return drained;
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
        if (fluid != null && fluid.amount > 0) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluid.writeToNBT(fluidTag);
            compound.setTag(FLUID_TAG, fluidTag);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fluid = compound.hasKey(FLUID_TAG, 10)
            ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag(FLUID_TAG))
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
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos, pos.add(1, 1, 1));
    }

    private boolean canAccept(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return false;
        }
        return fluid == null || fluid.isFluidEqual(incoming) && fluid.amount < CAPACITY;
    }

    private void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}
