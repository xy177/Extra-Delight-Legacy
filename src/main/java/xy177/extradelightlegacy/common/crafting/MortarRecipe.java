package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class MortarRecipe {
    private final String id;
    private final MixingBowlIngredient input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int grinds;

    MortarRecipe(String id, MixingBowlIngredient input, ItemStack output, FluidStack fluidOutput, int grinds) {
        this.id = id;
        this.input = input;
        this.output = output.copy();
        this.fluidOutput = fluidOutput == null ? null : fluidOutput.copy();
        this.grinds = grinds;
    }

    public String getId() {
        return id;
    }

    public ItemStack getInput() {
        return input == null ? ItemStack.EMPTY : input.getDisplayStack();
    }

    public MixingBowlIngredient getIngredient() {
        return input;
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
        return input != null && input.matches(stack);
    }
}
