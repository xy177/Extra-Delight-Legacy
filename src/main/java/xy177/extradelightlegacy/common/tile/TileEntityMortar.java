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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.MortarRecipe;
import xy177.extradelightlegacy.common.crafting.MortarRecipeManager;
import xy177.extradelightlegacy.common.event.EDLAdvancements;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import javax.annotation.Nullable;

public class TileEntityMortar extends TileEntity implements IFluidHandler {
    private static final int MAX_STACK_SIZE = 4;
    private static final int TANK_CAPACITY = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private int grinds;
    private FluidStack fluidTank;

    public boolean insertHeldItem(EntityPlayer player, ItemStack held) {
        if (held.isEmpty() || MortarRecipeManager.find(held) == null) {
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
        held.shrink(movable);
        grinds = 0;
        markUpdated();
        return true;
    }

    public boolean extractItem(EntityPlayer player) {
        ItemStack stack = items.get(0);
        if (stack.isEmpty()) {
            return false;
        }

        ItemStack extracted = stack.copy();
        items.set(0, ItemStack.EMPTY);
        grinds = 0;
        if (!player.inventory.addItemStackToInventory(extracted)) {
            player.dropItem(extracted, false);
        }
        markUpdated();
        return true;
    }

    public boolean handleFluidContainer(EntityPlayer player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }

        if (held.getItem() == Items.BUCKET) {
            return fillBucket(player, held);
        }
        if (held.getItem() == Items.GLASS_BOTTLE) {
            return fillBottle(player, held);
        }

        EDLFluids.FluidDefinition definition = EDLFluids.findByBucket(held.getItem());
        if (definition != null && definition.getFluid() != null) {
            FluidStack incoming = new FluidStack(definition.getFluid(), TANK_CAPACITY);
            if (!canAcceptFluid(incoming)) {
                return false;
            }

            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
                givePlayerItem(player, new ItemStack(Items.BUCKET));
            }

            addFluid(incoming);
            markUpdated();
            return true;
        }

        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByBottle(held);
        if (bottleRecipe == null) {
            return false;
        }

        FluidStack incoming = bottleRecipe.getFluid();
        if (!canAcceptFluid(incoming)) {
            return false;
        }

        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
            givePlayerItem(player, new ItemStack(Items.GLASS_BOTTLE));
        }

        addFluid(incoming);
        markUpdated();
        return true;
    }

    public boolean grind(EntityPlayer player) {
        ItemStack stack = items.get(0);
        MortarRecipe recipe = MortarRecipeManager.find(stack);
        if (recipe == null) {
            return false;
        }
        if (!canAcceptRecipeFluid(recipe, stack.getCount())) {
            return false;
        }

        if (grinds + 1 < recipe.getGrinds()) {
            grinds++;
            spawnGrindingEffects(stack);
            markUpdated();
            return true;
        }

        ItemStack output = recipe.getOutput();
        if (!output.isEmpty()) {
            for (int i = 0; i < stack.getCount(); i++) {
                ItemStack single = output.copy();
                EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.45D, pos.getZ() + 0.5D, single);
                entity.setDefaultPickupDelay();
                world.spawnEntity(entity);
            }
        }

        FluidStack fluidOutput = recipe.getFluidOutput();
        if (fluidOutput != null) {
            FluidStack total = fluidOutput.copy();
            total.amount *= stack.getCount();
            addFluid(total);
        }
        items.set(0, ItemStack.EMPTY);
        grinds = 0;
        spawnGrindingEffects(stack);
        EDLAdvancements.trigger(EDLAdvancements.GRIND, player);
        markUpdated();
        return true;
    }

    public ItemStack getInsertedItem() {
        return items.get(0);
    }

    public int getGrinds() {
        return grinds;
    }

    public void clear() {
        items.set(0, ItemStack.EMPTY);
        grinds = 0;
    }

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public float getFluidFullness() {
        return fluidTank == null ? 0.0F : (float) fluidTank.amount / (float) TANK_CAPACITY;
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
        if (fillAmount <= 0) {
            return 0;
        }

        if (doFill) {
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
        compound.setInteger("Grinds", grinds);
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
        grinds = compound.getInteger("Grinds");
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

    private void spawnGrindingEffects(ItemStack stack) {
        if (world == null) {
            return;
        }

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

    private boolean fillBucket(EntityPlayer player, ItemStack held) {
        if (fluidTank == null || fluidTank.amount < TANK_CAPACITY) {
            return false;
        }

        EDLFluids.FluidDefinition definition = EDLFluids.find(fluidTank.getFluid());
        if (definition == null || definition.getBucket() == null) {
            return false;
        }

        if (player.capabilities.isCreativeMode) {
            givePlayerItem(player, definition.bucketStack());
            return true;
        }

        held.shrink(1);
        givePlayerItem(player, definition.bucketStack());
        fluidTank.amount -= TANK_CAPACITY;
        if (fluidTank.amount <= 0) {
            fluidTank = null;
        }
        markUpdated();
        return true;
    }

    private boolean fillBottle(EntityPlayer player, ItemStack held) {
        if (fluidTank == null || fluidTank.amount < BottleFluidRecipeManager.BOTTLE_VOLUME) {
            return false;
        }

        BottleFluidRecipe recipe = BottleFluidRecipeManager.findByFluid(fluidTank.getFluid());
        if (recipe == null || recipe.getBottle().isEmpty()) {
            return false;
        }

        if (player.capabilities.isCreativeMode) {
            givePlayerItem(player, recipe.getBottle());
            return true;
        }

        held.shrink(1);
        givePlayerItem(player, recipe.getBottle());
        fluidTank.amount -= BottleFluidRecipeManager.BOTTLE_VOLUME;
        if (fluidTank.amount <= 0) {
            fluidTank = null;
        }
        markUpdated();
        return true;
    }

    private boolean canAcceptRecipeFluid(MortarRecipe recipe, int inputCount) {
        FluidStack output = recipe.getFluidOutput();
        if (output == null) {
            return true;
        }

        FluidStack total = output.copy();
        total.amount *= inputCount;
        return canAcceptFluid(total);
    }

    private boolean canAcceptFluid(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return true;
        }
        if (fluidTank == null) {
            return incoming.amount <= TANK_CAPACITY;
        }
        return fluidTank.isFluidEqual(incoming) && fluidTank.amount + incoming.amount <= TANK_CAPACITY;
    }

    private boolean isManagedFluid(FluidStack incoming) {
        return incoming != null
            && incoming.amount > 0
            && incoming.getFluid() != null
            && EDLFluids.find(incoming.getFluid()) != null;
    }

    private void addFluid(FluidStack incoming) {
        if (incoming == null || incoming.amount <= 0 || incoming.getFluid() == null) {
            return;
        }

        if (fluidTank == null) {
            fluidTank = incoming.copy();
        } else if (fluidTank.isFluidEqual(incoming)) {
            fluidTank.amount = Math.min(TANK_CAPACITY, fluidTank.amount + incoming.amount);
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
