package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class MortarRecipe {
    private final ItemStack input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int grinds;

    MortarRecipe(ItemStack input, ItemStack output, FluidStack fluidOutput, int grinds) {
        this.input = input.copy();
        this.output = output.copy();
        this.fluidOutput = fluidOutput == null ? null : fluidOutput.copy();
        this.grinds = grinds;
    }

    public ItemStack getInput() {
        return input.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public FluidStack getFluidOutput() {
        return fluidOutput == null ? null : fluidOutput.copy();
    }

    public int getGrinds() {
        return grinds;
    }

    public boolean matches(ItemStack stack) {
        return !stack.isEmpty() && ItemStack.areItemsEqual(input, stack);
    }
}
