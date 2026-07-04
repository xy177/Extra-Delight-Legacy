package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MeltingPotRecipe {
    private final String name;
    private final MixingBowlIngredient input;
    private final FluidStack output;
    private final int cookingTime;

    public MeltingPotRecipe(String name, MixingBowlIngredient input, FluidStack output, int cookingTime) {
        this.name = name;
        this.input = input;
        this.output = output == null ? null : output.copy();
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public MixingBowlIngredient getInput() {
        return input;
    }

    public FluidStack getOutput() {
        return output == null ? null : output.copy();
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public boolean matches(ItemStack stack) {
        return input != null && input.matches(stack);
    }
}
