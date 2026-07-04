package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
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
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatRecipe;
import xy177.extradelightlegacy.common.crafting.VatRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatStage;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import javax.annotation.Nullable;

public class TileEntityVat extends TileEntity implements ITickable, IStyleableTile, IFluidHandler {
    private static final int STYLE_COUNT = 17;
    public static final int INGREDIENT_SLOTS = 6;
    public static final int STAGE_SLOT = 6;
    public static final int FLUID_IN_SLOT = 7;
    public static final int FLUID_OUT_SLOT = 8;
    public static final int CONTAINER_SLOT = 9;
    public static final int RESULT_SLOT = 10;
    public static final int SLOT_COUNT = 11;
    public static final int TANK_CAPACITY = 1000;
    private static final int BUCKET_VOLUME = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private FluidStack fluidTank;
    private String recipeName = "";
    private int stageIndex;
    private int stageTime;
    private int stageTimeTotal;
    private int style;

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public FluidStack getFluid() {
        return fluidTank == null ? null : fluidTank.copy();
    }

    public int getStageIndex() {
        return stageIndex;
    }

    public int getStageTime() {
        return stageTime;
    }

    public int getStageTimeTotal() {
        return stageTimeTotal;
    }

    public int getStageCount() {
        VatRecipe recipe = getActiveRecipe();
        return recipe == null ? 0 : recipe.getStageCount();
    }

    public boolean isLidRequired() {
        VatStage stage = getCurrentStage();
        return stage != null && stage.requiresLid();
    }

    public boolean hasLid() {
        return world != null
            && EDLBlocks.VAT_LID.getBlock() != null
            && world.getBlockState(pos.up()).getBlock() == EDLBlocks.VAT_LID.getBlock();
    }

    public VatRecipe getCurrentRecipe() {
        return VatRecipeManager.find(getIngredientStacks(), items.get(CONTAINER_SLOT), fluidTank);
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        handleFluidInputSlot();
        handleFluidOutputSlot();

        VatRecipe recipe = getActiveRecipe();
        if (recipe == null) {
            recipe = getCurrentRecipe();
            if (recipe != null) {
                beginRecipe(recipe);
            }
        }
        if (recipe == null || !canOutput(recipe.getOutput())) {
            resetProgress();
            markUpdated();
            return;
        }

        VatStage stage = getCurrentStage();
        if (stage == null) {
            resetProgress();
            markUpdated();
            return;
        }
        stageTimeTotal = stage.getTime();
        if (!canProcessStage(stage)) {
            markUpdated();
            return;
        }

        stageTime++;
        if (stageTime >= stageTimeTotal) {
            finishStage(recipe, stage);
        }
        markUpdated();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (slot < INGREDIENT_SLOTS) {
            return VatRecipeManager.isValidIngredient(stack);
        }
        if (slot == STAGE_SLOT) {
            return VatRecipeManager.isValidStageIngredient(stack);
        }
        if (slot == FLUID_IN_SLOT) {
            return canFillFromContainer(stack);
        }
        if (slot == FLUID_OUT_SLOT) {
            return canDrainToContainer(stack);
        }
        if (slot == CONTAINER_SLOT) {
            return VatRecipeManager.isValidContainer(stack);
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
            resetProgress();
            markUpdated();
            return result;
        }

        FluidStack contained = FluidUtil.getFluidContained(stack);
        FluidActionResult genericResult = FluidUtil.tryEmptyContainer(stack, this, TANK_CAPACITY, null, true);
        if (!genericResult.isSuccess() || contained == null) {
            return null;
        }
        resetProgress();
        markUpdated();
        return new FluidContainerResult(contained, genericResult.getResult());
    }

    public ItemStack tryDrainToContainer(ItemStack emptyContainer) {
        ItemStack filled = getDrainResult(emptyContainer);
        if (!filled.isEmpty()) {
            int amount = emptyContainer.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
            drain(amount);
            resetProgress();
            markUpdated();
            return filled;
        }

        FluidActionResult genericResult = FluidUtil.tryFillContainer(emptyContainer, this, TANK_CAPACITY, null, true);
        if (!genericResult.isSuccess()) {
            return ItemStack.EMPTY;
        }
        resetProgress();
        markUpdated();
        return genericResult.getResult();
    }

    public void clear() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        fluidTank = null;
        resetProgress();
    }

    private void beginRecipe(VatRecipe recipe) {
        recipeName = recipe.getName();
        stageIndex = 0;
        stageTime = 0;
        stageTimeTotal = recipe.getStages().get(0).getTime();
    }

    private VatRecipe getActiveRecipe() {
        if (recipeName.isEmpty()) {
            return null;
        }
        for (VatRecipe recipe : VatRecipeManager.getRecipes()) {
            if (recipeName.equals(recipe.getName()) && recipe.matchesBase(getIngredientStacks(), items.get(CONTAINER_SLOT), fluidTank)) {
                return recipe;
            }
        }
        return null;
    }

    private VatStage getCurrentStage() {
        VatRecipe recipe = getActiveRecipe();
        if (recipe == null || stageIndex < 0 || stageIndex >= recipe.getStages().size()) {
            return null;
        }
        return recipe.getStages().get(stageIndex);
    }

    private boolean canProcessStage(VatStage stage) {
        if (stage.requiresLid() != hasLid()) {
            return false;
        }
        return !stage.hasIngredient() || stage.getIngredient().matches(items.get(STAGE_SLOT));
    }

    private void finishStage(VatRecipe recipe, VatStage stage) {
        if (stage.hasIngredient()) {
            consumeStageInput();
        }

        stageIndex++;
        stageTime = 0;
        if (stageIndex >= recipe.getStages().size()) {
            craft(recipe);
        } else {
            stageTimeTotal = recipe.getStages().get(stageIndex).getTime();
        }
    }

    private void consumeStageInput() {
        ItemStack stack = items.get(STAGE_SLOT);
        if (stack.isEmpty()) {
            return;
        }
        ItemStack container = stack.getItem().getContainerItem(stack);
        stack.shrink(1);
        if (!container.isEmpty()) {
            EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, container);
            entity.setDefaultPickupDelay();
            world.spawnEntity(entity);
        }
        if (stack.isEmpty()) {
            items.set(STAGE_SLOT, ItemStack.EMPTY);
        }
    }

    private void craft(VatRecipe recipe) {
        ItemStack current = items.get(RESULT_SLOT);
        ItemStack output = recipe.getOutput();
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
        items.get(CONTAINER_SLOT).shrink(recipe.getContainer().getCount());
        if (items.get(CONTAINER_SLOT).isEmpty()) {
            items.set(CONTAINER_SLOT, ItemStack.EMPTY);
        }
        drain(recipe.getFluid().amount);
        resetProgress();
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
            resetProgress();
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
            resetProgress();
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

    private boolean canOutput(ItemStack output) {
        ItemStack current = items.get(RESULT_SLOT);
        if (current.isEmpty()) {
            return true;
        }
        return ItemStack.areItemsEqual(current, output)
            && ItemStack.areItemStackTagsEqual(current, output)
            && current.getCount() + output.getCount() <= current.getMaxStackSize();
    }

    private ItemStack[] getIngredientStacks() {
        ItemStack[] stacks = new ItemStack[INGREDIENT_SLOTS];
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            stacks[i] = items.get(i);
        }
        return stacks;
    }

    private void resetProgress() {
        recipeName = "";
        stageIndex = 0;
        stageTime = 0;
        stageTimeTotal = 0;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        compound.setString("Recipe", recipeName);
        compound.setInteger("StageIndex", stageIndex);
        compound.setInteger("StageTime", stageTime);
        compound.setInteger("StageTimeTotal", stageTimeTotal);
        if (fluidTank != null && fluidTank.amount > 0) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTank.writeToNBT(fluidTag);
            compound.setTag("Fluid", fluidTag);
        }
        compound.setInteger("Style", style);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ItemStackHelper.loadAllItems(compound, items);
        recipeName = compound.getString("Recipe");
        stageIndex = compound.getInteger("StageIndex");
        stageTime = compound.getInteger("StageTime");
        stageTimeTotal = compound.getInteger("StageTimeTotal");
        fluidTank = compound.hasKey("Fluid", 10)
            ? FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid"))
            : null;
        style = Math.max(0, Math.min(STYLE_COUNT - 1, compound.getInteger("Style")));
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

    @Override
    public int getStyleCount() {
        return STYLE_COUNT;
    }

    @Override
    public int getStyle() {
        return style;
    }

    @Override
    public void setStyle(int style) {
        this.style = Math.max(0, Math.min(STYLE_COUNT - 1, style));
        markUpdated();
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
