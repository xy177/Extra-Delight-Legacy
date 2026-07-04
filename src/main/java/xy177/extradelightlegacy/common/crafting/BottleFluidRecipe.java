package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class BottleFluidRecipe {
    private final ItemStack bottle;
    private final FluidStack fluid;
    private final ItemStack bucket;

    BottleFluidRecipe(ItemStack bottle, FluidStack fluid, ItemStack bucket) {
        this.bottle = bottle.copy();
        this.fluid = fluid.copy();
        this.bucket = bucket.copy();
    }

    public ItemStack getBottle() {
        return bottle.copy();
    }

    public FluidStack getFluid() {
        return fluid.copy();
    }

    public ItemStack getBucket() {
        return bucket.copy();
    }

    public boolean matchesBottle(ItemStack stack) {
        return !stack.isEmpty() && ItemStack.areItemsEqual(bottle, stack);
    }

    public boolean matchesFluid(FluidStack stack) {
        return stack != null && fluid.isFluidEqual(stack);
    }
}
