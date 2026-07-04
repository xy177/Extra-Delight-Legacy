package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class JuicerRecipe {
    private final String name;
    private final MixingBowlIngredient input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int chance;

    public JuicerRecipe(String name, MixingBowlIngredient input, ItemStack output, FluidStack fluidOutput, int chance) {
        this.name = name;
        this.input = input;
        this.output = output == null ? ItemStack.EMPTY : output.copy();
        this.fluidOutput = fluidOutput == null ? null : fluidOutput.copy();
        this.chance = chance;
    }

    public String getName() {
        return name;
    }

    public MixingBowlIngredient getInput() {
        return input;
    }

    public ItemStack getInputDisplay() {
        return input == null ? ItemStack.EMPTY : input.getDisplayStack();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public FluidStack getFluidOutput() {
        return fluidOutput == null ? null : fluidOutput.copy();
    }

    public int getChance() {
        return chance;
    }

    public boolean matches(ItemStack stack) {
        return input != null && input.matches(stack);
    }
}
