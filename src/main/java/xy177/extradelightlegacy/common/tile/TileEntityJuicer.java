package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
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
import xy177.extradelightlegacy.common.crafting.JuicerRecipe;
import xy177.extradelightlegacy.common.crafting.JuicerRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import javax.annotation.Nullable;

public class TileEntityJuicer extends TileEntity implements IFluidHandler {
    private static final int MAX_STACK_SIZE = 8;
    private static final int SQUEEZES_REQUIRED = 8;
    public static final int TANK_CAPACITY = 4000;
    private static final int BUCKET_VOLUME = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private FluidStack fluidTank;
    private int squeezes;

    public boolean insertHeldItem(EntityPlayer player, ItemStack held) {
        if (held.isEmpty() || !JuicerRecipeManager.isValidInput(held)) {
            return false;
        }
        ItemStack inserted = items.get(0);
        if (!inserted.isEmpty() && (!ItemStack.areItemsEqual(inserted, held) || inserted.getCount() >= MAX_STACK_SIZE)) {
            return false;
        }

        int movable = inserted.isEmpty() ? Math.min(MAX_STACK_SIZE, held.getCount()) : Math.min(MAX_STACK_SIZE - inserted.getCount(), held.getCount());
        if (movable <= 0) {
            return false;
        }
        if (inserted.isEmpty()) {
            ItemStack copy = held.copy();
            copy.setCount(movable);
            items.set(0, copy);
        } else {
            inserted.grow(movable);
        }
        if (!player.capabilities.isCreativeMode) {
            held.shrink(movable);
        }
        squeezes = 0;
        markUpdated();
        return true;
    }

    public boolean extractItem(EntityPlayer player) {
        ItemStack stack = items.get(0);
        if (stack.isEmpty()) {
            return false;
        }
        items.set(0, ItemStack.EMPTY);
        squeezes = 0;
        givePlayerItem(player, stack.copy());
        markUpdated();
        return true;
    }

    public boolean squeeze(EntityPlayer player) {
        ItemStack stack = items.get(0);
        JuicerRecipe recipe = JuicerRecipeManager.find(stack);
        if (recipe == null || !canAcceptRecipeFluid(recipe, stack.getCount())) {
            return false;
        }

        if (squeezes + 1 < SQUEEZES_REQUIRED) {
            squeezes++;
            spawnSqueezeEffects(stack);
            markUpdated();
            return true;
        }

        FluidStack fluid = recipe.getFluidOutput();
        fluid.amount *= stack.getCount();
        addFluid(fluid);
        ItemStack output = recipe.getOutput();
        if (!output.isEmpty()) {
            for (int i = 0; i < stack.getCount(); i++) {
                if (world.rand.nextInt(100) < recipe.getChance()) {
                    EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.45D, pos.getZ() + 0.5D, output.copy());
                    entity.setDefaultPickupDelay();
                    world.spawnEntity(entity);
                }
            }
        }
        items.set(0, ItemStack.EMPTY);
        squeezes = 0;
        spawnSqueezeEffects(stack);
        markUpdated();
        return true;
    }

    public boolean handleFluidContainer(EntityPlayer player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }
        ItemStack filled = getDrainResult(held);
        if (filled.isEmpty()) {
            return false;
        }
        int amount = held.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
        }
        givePlayerItem(player, filled);
        drain(amount, true);
        return true;
    }

    public ItemStack getInsertedItem() {
        return items.get(0);
    }

    public int getSqueezes() {
        return squeezes;
    }

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public float getFluidFullness() {
        return fluidTank == null ? 0.0F : (float) fluidTank.amount / (float) TANK_CAPACITY;
    }

    public void clear() {
        items.set(0, ItemStack.EMPTY);
        fluidTank = null;
        squeezes = 0;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{
            new FluidTankProperties(fluidTank == null ? null : fluidTank.copy(), TANK_CAPACITY, true, true)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!isManagedFluid(resource) || !canAcceptFluid(resource)) {
            return 0;
        }
        int fillAmount = fluidTank == null
            ? Math.min(resource.amount, TANK_CAPACITY)
            : Math.min(resource.amount, TANK_CAPACITY - fluidTank.amount);
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
        int drained = Math.min(maxDrain, fluidTank.amount);
        FluidStack result = fluidTank.copy();
        result.amount = drained;
        if (doDrain) {
            fluidTank.amount -= drained;
            if (fluidTank.amount <= 0) {
                fluidTank = null;
            }
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
        ItemStackHelper.saveAllItems(compound, items);
        compound.setInteger("Squeezes", squeezes);
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
        items.set(0, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, items);
        squeezes = compound.getInteger("Squeezes");
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

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(2, 3, 2));
    }

    private boolean canAcceptRecipeFluid(JuicerRecipe recipe, int inputCount) {
        FluidStack output = recipe.getFluidOutput();
        if (output == null) {
            return false;
        }
        output.amount *= inputCount;
        return canAcceptFluid(output);
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

    private boolean isManagedFluid(FluidStack incoming) {
        return incoming != null && incoming.amount > 0 && incoming.getFluid() != null
            && (EDLFluids.find(incoming.getFluid()) != null || incoming.getFluid() == FluidRegistry.WATER);
    }

    private void addFluid(FluidStack incoming) {
        if (fluidTank == null) {
            fluidTank = incoming.copy();
        } else if (fluidTank.isFluidEqual(incoming)) {
            fluidTank.amount = Math.min(TANK_CAPACITY, fluidTank.amount + incoming.amount);
        }
    }

    private ItemStack getDrainResult(ItemStack emptyContainer) {
        if (emptyContainer.isEmpty() || fluidTank == null || fluidTank.amount <= 0) {
            return ItemStack.EMPTY;
        }
        if (emptyContainer.getItem() == Items.BUCKET && fluidTank.amount >= BUCKET_VOLUME) {
            EDLFluids.FluidDefinition definition = EDLFluids.find(fluidTank.getFluid());
            return definition == null ? ItemStack.EMPTY : definition.bucketStack();
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && fluidTank.amount >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            if (fluidTank.getFluid() == FluidRegistry.WATER) {
                ItemStack waterBottle = new ItemStack(Items.POTIONITEM);
                return PotionUtils.addPotionToItemStack(waterBottle, net.minecraft.init.PotionTypes.WATER);
            }
            BottleFluidRecipe recipe = BottleFluidRecipeManager.findByFluid(fluidTank.getFluid());
            return recipe == null ? ItemStack.EMPTY : recipe.getBottle();
        }
        return ItemStack.EMPTY;
    }

    private void spawnSqueezeEffects(ItemStack stack) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
        for (int i = 0; i < 1 + world.rand.nextInt(4); i++) {
            world.spawnParticle(
                EnumParticleTypes.ITEM_CRACK,
                pos.getX() + 0.25D + world.rand.nextDouble() / 2.0D,
                pos.getY() + 0.5D - world.rand.nextDouble() / 2.0D,
                pos.getZ() + 0.25D + world.rand.nextDouble() / 2.0D,
                0.0D,
                0.0D,
                0.0D,
                net.minecraft.item.Item.getIdFromItem(stack.getItem()),
                stack.getMetadata()
            );
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
}
