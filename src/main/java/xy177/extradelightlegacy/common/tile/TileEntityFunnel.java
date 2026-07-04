package xy177.extradelightlegacy.common.tile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.block.BlockFunnel;

import javax.annotation.Nullable;

public class TileEntityFunnel extends TileEntity implements ITickable, IFluidHandler {
    public static final int CAPACITY = 1000;
    private FluidStack fluid;
    private int cooldown;

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        cooldown = 8;
        if (world.isBlockPowered(pos)) {
            return;
        }
        IBlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof BlockFunnel)) {
            return;
        }
        pushFluid(state.getValue(BlockFunnel.FACING));
        pullFluid();
    }

    public FluidStack getFluid() {
        return fluid == null ? null : fluid.copy();
    }

    public float getFluidFullness() {
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
        int amount = fluid == null ? Math.min(resource.amount, CAPACITY) : Math.min(resource.amount, CAPACITY - fluid.amount);
        if (doFill && amount > 0) {
            FluidStack accepted = resource.copy();
            accepted.amount = amount;
            if (fluid == null) {
                fluid = accepted;
            } else {
                fluid.amount += amount;
            }
            markUpdated();
        }
        return amount;
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
        compound.setInteger("TransferCooldown", cooldown);
        if (fluid != null && fluid.amount > 0) {
            NBTTagCompound tag = new NBTTagCompound();
            fluid.writeToNBT(tag);
            compound.setTag("Fluid", tag);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        cooldown = compound.getInteger("TransferCooldown");
        fluid = compound.hasKey("Fluid", 10) ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")) : null;
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

    private void pullFluid() {
        if (fluid != null) {
            return;
        }
        BlockPos above = pos.up();
        IBlockState state = world.getBlockState(above);
        Fluid source = sourceFluid(state);
        if (source != null) {
            fill(new FluidStack(source, CAPACITY), true);
            world.setBlockToAir(above);
            return;
        }

        TileEntity tile = world.getTileEntity(above);
        if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
            IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if (handler != null) {
                FluidStack drained = handler.drain(CAPACITY, true);
                if (drained != null && drained.amount > 0) {
                    fill(drained, true);
                }
            }
        }
    }

    private void pushFluid(EnumFacing facing) {
        if (fluid == null || facing == null) {
            return;
        }
        BlockPos target = pos.offset(facing);
        TileEntity tile = world.getTileEntity(target);
        if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite())) {
            IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
            if (handler != null) {
                int filled = handler.fill(fluid.copy(), true);
                if (filled > 0) {
                    drain(filled, true);
                }
                return;
            }
        }
        if (fluid.amount >= CAPACITY && world.isAirBlock(target) && fluid.getFluid() != null && fluid.getFluid().getBlock() != null) {
            world.setBlockState(target, fluid.getFluid().getBlock().getDefaultState(), 3);
            drain(CAPACITY, true);
        }
    }

    private boolean canAccept(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return false;
        }
        return fluid == null || fluid.isFluidEqual(incoming) && fluid.amount < CAPACITY;
    }

    private static Fluid sourceFluid(IBlockState state) {
        Block block = state.getBlock();
        if (block == net.minecraft.init.Blocks.WATER && state.getMaterial() == Material.WATER && isSourceLevel(state)) {
            return FluidRegistry.WATER;
        }
        if (block == net.minecraft.init.Blocks.LAVA && state.getMaterial() == Material.LAVA && isSourceLevel(state)) {
            return FluidRegistry.LAVA;
        }
        return null;
    }

    private static boolean isSourceLevel(IBlockState state) {
        return !state.getProperties().containsKey(BlockLiquid.LEVEL) || state.getValue(BlockLiquid.LEVEL) == 0;
    }

    private void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}
