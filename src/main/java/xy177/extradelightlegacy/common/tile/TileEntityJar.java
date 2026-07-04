package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import javax.annotation.Nullable;

public class TileEntityJar extends TileEntity implements IFluidHandler {
    public static final int CAPACITY = 1000;
    private static final int BUCKET_VOLUME = 1000;
    private static final String FLUID_TAG = "Fluid";

    private FluidStack fluidTank;

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public float getFluidFullness() {
        return fluidTank == null ? 0.0F : (float) fluidTank.amount / (float) CAPACITY;
    }

    public boolean canHandleFluidContainer(ItemStack stack) {
        return getFillResult(stack) != null || !getDrainResult(stack).isEmpty();
    }

    public boolean handleFluidContainer(EntityPlayer player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }

        FluidContainerResult fill = getFillResult(held);
        if (fill != null && canAcceptFluid(fill.fluid)) {
            addFluid(fill.fluid);
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
            }
            givePlayerItem(player, fill.container.copy());
            markUpdated();
            return true;
        }

        ItemStack drained = getDrainResult(held);
        if (drained.isEmpty()) {
            return false;
        }
        int amount = held.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
        drain(amount, true);
        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
        }
        givePlayerItem(player, drained);
        markUpdated();
        return true;
    }

    public void readFluidFromStack(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(FLUID_TAG, 10)) {
            fluidTank = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(FLUID_TAG));
        }
        markUpdated();
    }

    public void writeFluidToStack(ItemStack stack) {
        if (fluidTank == null || fluidTank.amount <= 0) {
            return;
        }
        NBTTagCompound stackTag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        NBTTagCompound fluidTag = new NBTTagCompound();
        fluidTank.writeToNBT(fluidTag);
        stackTag.setTag(FLUID_TAG, fluidTag);
        stack.setTagCompound(stackTag);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{
            new FluidTankProperties(fluidTank == null ? null : fluidTank.copy(), CAPACITY, true, true)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!canAcceptFluid(resource)) {
            return 0;
        }
        int fillAmount = fluidTank == null
            ? Math.min(resource.amount, CAPACITY)
            : Math.min(resource.amount, CAPACITY - fluidTank.amount);
        if (doFill && fillAmount > 0) {
            FluidStack accepted = resource.copy();
            accepted.amount = fillAmount;
            addFluid(accepted);
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
        int amount = Math.min(maxDrain, fluidTank.amount);
        FluidStack drained = fluidTank.copy();
        drained.amount = amount;
        if (doDrain) {
            fluidTank.amount -= amount;
            if (fluidTank.amount <= 0) {
                fluidTank = null;
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
        if (fluidTank != null && fluidTank.amount > 0) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTank.writeToNBT(fluidTag);
            compound.setTag(FLUID_TAG, fluidTag);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fluidTank = compound.hasKey(FLUID_TAG, 10)
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

    private FluidContainerResult getFillResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        if (stack.getItem() == Items.WATER_BUCKET && FluidRegistry.WATER != null) {
            return new FluidContainerResult(new FluidStack(FluidRegistry.WATER, BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        if (stack.getItem() == Items.MILK_BUCKET && EDLFluids.getMilkFluid() != null) {
            return new FluidContainerResult(EDLFluids.milkStack(BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        if (stack.getItem() == Items.POTIONITEM
            && PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER
            && FluidRegistry.WATER != null) {
            return new FluidContainerResult(new FluidStack(FluidRegistry.WATER, BottleFluidRecipeManager.BOTTLE_VOLUME), new ItemStack(Items.GLASS_BOTTLE));
        }
        EDLFluids.FluidDefinition definition = EDLFluids.findByBucket(stack.getItem());
        if (definition != null && definition.getFluid() != null) {
            return new FluidContainerResult(new FluidStack(definition.getFluid(), BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByBottle(stack);
        if (bottleRecipe != null && bottleRecipe.getFluid().getFluid() != null) {
            return new FluidContainerResult(bottleRecipe.getFluid(), new ItemStack(Items.GLASS_BOTTLE));
        }
        return null;
    }

    private ItemStack getDrainResult(ItemStack emptyContainer) {
        if (emptyContainer.isEmpty() || fluidTank == null || fluidTank.amount <= 0) {
            return ItemStack.EMPTY;
        }
        if (emptyContainer.getItem() == Items.BUCKET && fluidTank.amount >= BUCKET_VOLUME) {
            if (fluidTank.getFluid() == FluidRegistry.WATER) {
                return new ItemStack(Items.WATER_BUCKET);
            }
            if (fluidTank.getFluid() == EDLFluids.getMilkFluid()) {
                return new ItemStack(Items.MILK_BUCKET);
            }
            EDLFluids.FluidDefinition definition = EDLFluids.find(fluidTank.getFluid());
            return definition == null ? ItemStack.EMPTY : definition.bucketStack();
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && fluidTank.amount >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            if (fluidTank.getFluid() == FluidRegistry.WATER) {
                return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
            }
            BottleFluidRecipe recipe = BottleFluidRecipeManager.findByFluid(fluidTank.getFluid());
            return recipe == null ? ItemStack.EMPTY : recipe.getBottle();
        }
        return ItemStack.EMPTY;
    }

    private boolean canAcceptFluid(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return false;
        }
        if (fluidTank == null) {
            return incoming.amount <= CAPACITY;
        }
        return fluidTank.isFluidEqual(incoming) && fluidTank.amount + incoming.amount <= CAPACITY;
    }

    private void addFluid(FluidStack incoming) {
        if (fluidTank == null) {
            fluidTank = incoming.copy();
        } else if (fluidTank.isFluidEqual(incoming)) {
            fluidTank.amount = Math.min(CAPACITY, fluidTank.amount + incoming.amount);
        }
    }

    private void givePlayerItem(EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty() && !player.inventory.addItemStackToInventory(stack)) {
            player.dropItem(stack, false);
        }
    }

    private void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    private static final class FluidContainerResult {
        private final FluidStack fluid;
        private final ItemStack container;

        private FluidContainerResult(FluidStack fluid, ItemStack container) {
            this.fluid = fluid == null ? null : fluid.copy();
            this.container = container == null ? ItemStack.EMPTY : container.copy();
        }
    }
}
