package xy177.extradelightlegacy.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import javax.annotation.Nullable;

public final class FluidContainerHelper {
    public static final int BUCKET_VOLUME = 1000;

    private FluidContainerHelper() {
    }

    public static boolean interactWithTank(EntityPlayer player, ItemStack held, IFluidHandler tank) {
        if (held.isEmpty()) {
            return false;
        }

        FillResult fill = getFillResult(held);
        if (fill != null && tank.fill(fill.fluid, false) == fill.fluid.amount) {
            tank.fill(fill.fluid, true);
            consumeAndGive(player, held, fill.container);
            return true;
        }

        ItemStack drainResult = getDrainResult(held, tank);
        if (!drainResult.isEmpty()) {
            int amount = held.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
            tank.drain(amount, true);
            consumeAndGive(player, held, drainResult);
            return true;
        }

        FluidActionResult filled = FluidUtil.tryFillContainer(held, tank, Integer.MAX_VALUE, player, true);
        if (filled.isSuccess()) {
            consumeAndGive(player, held, filled.getResult());
            return true;
        }

        FluidActionResult emptied = FluidUtil.tryEmptyContainer(held, tank, Integer.MAX_VALUE, player, true);
        if (emptied.isSuccess()) {
            consumeAndGive(player, held, emptied.getResult());
            return true;
        }
        return false;
    }

    public static boolean canInteractWithTank(ItemStack held, IFluidHandler tank) {
        if (held.isEmpty()) {
            return false;
        }
        FillResult fill = getFillResult(held);
        if (fill != null && tank.fill(fill.fluid, false) == fill.fluid.amount) {
            return true;
        }
        if (!getDrainResult(held, tank).isEmpty()) {
            return true;
        }
        return FluidUtil.getFluidHandler(held) != null;
    }

    public static boolean fillContainerFromSource(EntityPlayer player, ItemStack held, IFluidHandler source) {
        if (held.isEmpty()) {
            return false;
        }
        if (held.getItem() == Items.GLASS_BOTTLE) {
            consumeAndGive(player, held, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));
            return true;
        }
        if (held.getItem() == Items.POTIONITEM) {
            consumeAndGive(player, held, new ItemStack(Items.GLASS_BOTTLE));
            return true;
        }

        FluidActionResult filled = FluidUtil.tryFillContainer(held, source, Integer.MAX_VALUE, player, true);
        if (filled.isSuccess()) {
            consumeAndGive(player, held, filled.getResult());
            return true;
        }

        FluidActionResult emptied = FluidUtil.tryEmptyContainer(held, source, Integer.MAX_VALUE, player, true);
        if (emptied.isSuccess()) {
            consumeAndGive(player, held, emptied.getResult());
            return true;
        }
        return false;
    }

    public static boolean canFillContainerFromSource(ItemStack held) {
        return !held.isEmpty()
            && (held.getItem() == Items.GLASS_BOTTLE
            || held.getItem() == Items.POTIONITEM
            || FluidUtil.getFluidHandler(held) != null);
    }

    @Nullable
    public static FillResult getFillResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        if (stack.getItem() == Items.WATER_BUCKET && FluidRegistry.WATER != null) {
            return new FillResult(new FluidStack(FluidRegistry.WATER, BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        if (stack.getItem() == Items.MILK_BUCKET && EDLFluids.getMilkFluid() != null) {
            return new FillResult(EDLFluids.milkStack(BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        if (stack.getItem() == Items.POTIONITEM
            && PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER
            && FluidRegistry.WATER != null) {
            return new FillResult(new FluidStack(FluidRegistry.WATER, BottleFluidRecipeManager.BOTTLE_VOLUME), new ItemStack(Items.GLASS_BOTTLE));
        }
        EDLFluids.FluidDefinition definition = EDLFluids.findByBucket(stack.getItem());
        if (definition != null && definition.getFluid() != null) {
            return new FillResult(new FluidStack(definition.getFluid(), BUCKET_VOLUME), new ItemStack(Items.BUCKET));
        }
        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByBottle(stack);
        if (bottleRecipe != null && bottleRecipe.getFluid().getFluid() != null) {
            return new FillResult(bottleRecipe.getFluid(), new ItemStack(Items.GLASS_BOTTLE));
        }
        FillResult handlerResult = getHandlerFillResult(stack);
        if (handlerResult != null) {
            return handlerResult;
        }
        return null;
    }

    @Nullable
    private static FillResult getHandlerFillResult(ItemStack stack) {
        ItemStack single = stack.copy();
        single.setCount(1);
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(single);
        if (handler == null) {
            return null;
        }

        FluidStack contained = handler.drain(Integer.MAX_VALUE, false);
        if (contained == null || contained.amount <= 0 || contained.getFluid() == null) {
            return null;
        }

        FluidStack toDrain = contained.copy();
        FluidStack drained = handler.drain(toDrain, true);
        if (drained == null || drained.amount != contained.amount || !drained.isFluidEqual(contained)) {
            return null;
        }
        drained.amount = contained.amount;
        ItemStack container = handler.getContainer();
        return new FillResult(drained, container == null ? ItemStack.EMPTY : container);
    }

    public static ItemStack getDrainResult(ItemStack emptyContainer, IFluidHandler tank) {
        if (emptyContainer.isEmpty()) {
            return ItemStack.EMPTY;
        }
        FluidStack available = tank.drain(Integer.MAX_VALUE, false);
        if (available == null || available.amount <= 0 || available.getFluid() == null) {
            return ItemStack.EMPTY;
        }
        if (emptyContainer.getItem() == Items.BUCKET && available.amount >= BUCKET_VOLUME) {
            return bucketForFluid(available.getFluid());
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && available.amount >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            return bottleForFluid(available.getFluid());
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack bucketForFluid(Fluid fluid) {
        if (fluid == FluidRegistry.WATER) {
            return new ItemStack(Items.WATER_BUCKET);
        }
        if (fluid == EDLFluids.getMilkFluid()) {
            return new ItemStack(Items.MILK_BUCKET);
        }
        EDLFluids.FluidDefinition definition = EDLFluids.find(fluid);
        return definition == null ? ItemStack.EMPTY : definition.bucketStack();
    }

    public static ItemStack bottleForFluid(Fluid fluid) {
        if (fluid == FluidRegistry.WATER) {
            return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
        }
        BottleFluidRecipe recipe = BottleFluidRecipeManager.findByFluid(fluid);
        return recipe == null ? ItemStack.EMPTY : recipe.getBottle();
    }

    private static void consumeAndGive(EntityPlayer player, ItemStack held, ItemStack result) {
        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
        }
        if (!result.isEmpty() && !player.inventory.addItemStackToInventory(result.copy())) {
            player.dropItem(result.copy(), false);
        }
    }

    public static final class FillResult {
        public final FluidStack fluid;
        public final ItemStack container;

        private FillResult(FluidStack fluid, ItemStack container) {
            this.fluid = fluid.copy();
            this.container = container.copy();
        }
    }
}
