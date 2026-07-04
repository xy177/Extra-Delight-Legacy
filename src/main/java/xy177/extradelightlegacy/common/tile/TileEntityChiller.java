package xy177.extradelightlegacy.common.tile;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionUtils;
import net.minecraft.init.PotionTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.ChillerRecipe;
import xy177.extradelightlegacy.common.crafting.ChillerRecipeManager;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import javax.annotation.Nullable;

public class TileEntityChiller extends TileEntity implements ITickable, IFluidHandler {
    public static final int INGREDIENT_SLOTS = 4;
    public static final int CONTAINER_SLOT = 4;
    public static final int FLUID_IN_SLOT = 5;
    public static final int FLUID_OUT_SLOT = 6;
    public static final int ICE_SLOT = 7;
    public static final int RESULT_SLOT = 8;
    public static final int DRIP_TRAY_OUT_SLOT = 9;
    public static final int SLOT_COUNT = 10;
    public static final int TANK_CAPACITY = 1000;
    public static final int DRIP_TANK_CAPACITY = 1000;
    private static final int BUCKET_VOLUME = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private FluidStack fluidTank;
    private FluidStack dripTray;
    private int cookTime;
    private int cookTimeTotal;
    private int chillTime;
    private int chillDuration;

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public FluidStack getDripFluid() {
        return dripTray == null ? null : dripTray.copy();
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getCookTimeTotal() {
        return cookTimeTotal;
    }

    public int getChillTime() {
        return chillTime;
    }

    public int getChillDuration() {
        return chillDuration;
    }

    public ChillerRecipe getCurrentRecipe() {
        return ChillerRecipeManager.find(getIngredientStacks(), items.get(CONTAINER_SLOT), fluidTank);
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        handleFluidInputSlot();
        handleFluidOutputSlot();
        handleDripTrayOutputSlot();
        tickActiveChill();

        ChillerRecipe recipe = getCurrentRecipe();
        if (recipe == null || !canOutput(recipe.getOutput())) {
            cookTime = 0;
            cookTimeTotal = 0;
            markUpdated();
            return;
        }

        cookTimeTotal = recipe.getCookingTime();
        if (ensureChilling()) {
            cookTime++;
            if (cookTime >= cookTimeTotal) {
                craft(recipe);
            }
            markUpdated();
        }
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (slot < INGREDIENT_SLOTS) {
            return ChillerRecipeManager.isValidIngredient(stack);
        }
        if (slot == CONTAINER_SLOT) {
            return ChillerRecipeManager.isValidContainer(stack);
        }
        if (slot == FLUID_IN_SLOT) {
            return canFillFromContainer(stack);
        }
        if (slot == FLUID_OUT_SLOT) {
            return canDrainToContainer(stack);
        }
        if (slot == ICE_SLOT) {
            return getChillValue(stack) > 0;
        }
        if (slot == DRIP_TRAY_OUT_SLOT) {
            return canDrainDripToContainer(stack);
        }
        return false;
    }

    public boolean canFillFromContainer(ItemStack stack) {
        FluidContainerResult result = getFillResult(stack);
        if (result != null && canAcceptFluid(result.fluid)) {
            return true;
        }
        return FluidUtil.tryEmptyContainer(stack, this, TANK_CAPACITY, null, false).isSuccess();
    }

    public boolean canDrainToContainer(ItemStack stack) {
        return !getDrainResult(stack).isEmpty()
            || FluidUtil.tryFillContainer(stack, this, TANK_CAPACITY, null, false).isSuccess();
    }

    public FluidContainerResult tryFillFromContainer(ItemStack stack) {
        FluidContainerResult result = getFillResult(stack);
        if (result != null && canAcceptFluid(result.fluid)) {
            addFluid(result.fluid);
            cookTime = 0;
            markUpdated();
            return result;
        }

        FluidStack contained = FluidUtil.getFluidContained(stack);
        FluidActionResult genericResult = FluidUtil.tryEmptyContainer(stack, this, TANK_CAPACITY, null, true);
        if (!genericResult.isSuccess() || contained == null) {
            return null;
        }
        cookTime = 0;
        markUpdated();
        return new FluidContainerResult(contained, genericResult.getResult());
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

    public ItemStack tryDrainDripToContainer(ItemStack emptyContainer) {
        ItemStack filled = getDripDrainResult(emptyContainer);
        if (filled.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int amount = emptyContainer.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
        drainDrip(amount);
        markUpdated();
        return filled;
    }

    public void clear() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        fluidTank = null;
        dripTray = null;
        cookTime = 0;
        cookTimeTotal = 0;
        chillTime = 0;
        chillDuration = 0;
    }

    private void handleFluidInputSlot() {
        ItemStack stack = items.get(FLUID_IN_SLOT);
        if (stack.isEmpty() || !items.get(FLUID_OUT_SLOT).isEmpty()) {
            return;
        }
        FluidContainerResult result = tryFillFromContainer(stack);
        if (result == null) {
            return;
        }

        stack.shrink(1);
        items.set(FLUID_IN_SLOT, stack.isEmpty() ? ItemStack.EMPTY : stack);
        items.set(FLUID_OUT_SLOT, result.emptyContainer.copy());
    }

    private void handleFluidOutputSlot() {
        ItemStack stack = items.get(FLUID_OUT_SLOT);
        if (stack.isEmpty()) {
            return;
        }
        ItemStack filled = tryDrainToContainer(stack);
        if (filled.isEmpty()) {
            return;
        }

        stack.shrink(1);
        items.set(FLUID_OUT_SLOT, filled);
    }

    private void handleDripTrayOutputSlot() {
        ItemStack stack = items.get(DRIP_TRAY_OUT_SLOT);
        if (stack.isEmpty()) {
            return;
        }
        ItemStack filled = tryDrainDripToContainer(stack);
        if (filled.isEmpty()) {
            return;
        }

        stack.shrink(1);
        items.set(DRIP_TRAY_OUT_SLOT, filled);
    }

    private FluidContainerResult getFillResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        FluidContainerHelper.FillResult result = FluidContainerHelper.getFillResult(stack);
        return result == null ? null : new FluidContainerResult(result.fluid, result.container);
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

    private boolean canDrainDripToContainer(ItemStack stack) {
        return !getDripDrainResult(stack).isEmpty();
    }

    private ItemStack getDripDrainResult(ItemStack emptyContainer) {
        if (emptyContainer.isEmpty() || dripTray == null || dripTray.amount <= 0 || dripTray.getFluid() != FluidRegistry.WATER) {
            return ItemStack.EMPTY;
        }
        if (emptyContainer.getItem() == Items.BUCKET && dripTray.amount >= BUCKET_VOLUME) {
            return new ItemStack(Items.WATER_BUCKET);
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && dripTray.amount >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
        }
        return ItemStack.EMPTY;
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

    private boolean ensureChilling() {
        if (dripTray != null && dripTray.amount >= DRIP_TANK_CAPACITY) {
            return false;
        }
        if (chillTime > 0) {
            return true;
        }

        ItemStack ice = items.get(ICE_SLOT);
        int duration = getChillValue(ice);
        if (duration <= 0) {
            return false;
        }
        ice.shrink(1);
        if (ice.isEmpty()) {
            items.set(ICE_SLOT, ItemStack.EMPTY);
        }
        chillTime = duration - 1;
        chillDuration = duration;
        return true;
    }

    private void tickActiveChill() {
        if (chillTime > 0) {
            chillTime--;
            addDripWater(1);
        }
        if (chillTime <= 0) {
            chillDuration = 0;
        }
    }

    private void addDripWater(int amount) {
        if (amount <= 0 || (dripTray != null && dripTray.amount >= DRIP_TANK_CAPACITY)) {
            return;
        }
        if (dripTray == null) {
            dripTray = new FluidStack(FluidRegistry.WATER, Math.min(amount, DRIP_TANK_CAPACITY));
        } else if (dripTray.getFluid() == FluidRegistry.WATER) {
            dripTray.amount = Math.min(DRIP_TANK_CAPACITY, dripTray.amount + amount);
        }
    }

    private void drainDrip(int amount) {
        if (dripTray == null) {
            return;
        }
        dripTray.amount -= amount;
        if (dripTray.amount <= 0) {
            dripTray = null;
        }
    }

    private int getChillValue(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.getItem() == net.minecraft.item.Item.getItemFromBlock(Blocks.PACKED_ICE)) {
            return 1200;
        }
        if (stack.getItem() == net.minecraft.item.Item.getItemFromBlock(Blocks.ICE)) {
            return 600;
        }
        return 0;
    }

    private boolean canOutput(ItemStack output) {
        ItemStack current = items.get(RESULT_SLOT);
        if (current.isEmpty()) {
            return true;
        }
        return ItemStack.areItemsEqual(current, output)
            && ItemStack.areItemStackTagsEqual(current, output)
            && current.getCount() + output.getCount() <= current.getMaxStackSize();
    }

    private void craft(ChillerRecipe recipe) {
        ItemStack output = recipe.getOutput();
        ItemStack current = items.get(RESULT_SLOT);
        if (current.isEmpty()) {
            items.set(RESULT_SLOT, output);
        } else {
            current.grow(output.getCount());
        }

        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                ItemStack container = stack.getItem().getContainerItem(stack);
                stack.shrink(1);
                if (!container.isEmpty()) {
                    EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, container);
                    entity.setDefaultPickupDelay();
                    world.spawnEntity(entity);
                }
                if (stack.isEmpty()) {
                    items.set(i, ItemStack.EMPTY);
                }
            }
        }

        if (recipe.shouldConsumeContainer() && !items.get(CONTAINER_SLOT).isEmpty()) {
            items.get(CONTAINER_SLOT).shrink(recipe.getContainer().getCount());
            if (items.get(CONTAINER_SLOT).isEmpty()) {
                items.set(CONTAINER_SLOT, ItemStack.EMPTY);
            }
        }

        FluidStack fluid = recipe.getFluid();
        if (fluid != null) {
            drain(fluid.amount);
        }
        cookTime = 0;
    }

    private ItemStack[] getIngredientStacks() {
        ItemStack[] stacks = new ItemStack[INGREDIENT_SLOTS];
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            stacks[i] = items.get(i);
        }
        return stacks;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("CookTimeTotal", cookTimeTotal);
        compound.setInteger("ChillTime", chillTime);
        compound.setInteger("ChillDuration", chillDuration);
        if (fluidTank != null && fluidTank.amount > 0) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTank.writeToNBT(fluidTag);
            compound.setTag("Fluid", fluidTag);
        }
        if (dripTray != null && dripTray.amount > 0) {
            NBTTagCompound dripTag = new NBTTagCompound();
            dripTray.writeToNBT(dripTag);
            compound.setTag("DripTray", dripTag);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ItemStackHelper.loadAllItems(compound, items);
        cookTime = compound.getInteger("CookTime");
        cookTimeTotal = compound.getInteger("CookTimeTotal");
        chillTime = compound.getInteger("ChillTime");
        chillDuration = compound.getInteger("ChillDuration");
        fluidTank = compound.hasKey("Fluid", 10)
            ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid"))
            : null;
        dripTray = compound.hasKey("DripTray", 10)
            ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("DripTray"))
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

        public FluidStack getFluid() {
            return fluid.copy();
        }

        public ItemStack getEmptyContainer() {
            return emptyContainer.copy();
        }
    }
}
