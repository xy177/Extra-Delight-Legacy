package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipe;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import java.util.ArrayList;
import java.util.List;

public class MeltingPotJeiRecipe implements IRecipeWrapper {
    private final ItemStack input;
    private final FluidStack output;
    private final List<ItemStack> containers = new ArrayList<>();

    public MeltingPotJeiRecipe(MeltingPotRecipe recipe) {
        this.input = recipe.getInput().getDisplayStack();
        this.output = recipe.getOutput();
        if (output != null && output.getFluid() != null) {
            EDLFluids.FluidDefinition definition = EDLFluids.find(output.getFluid());
            if (definition != null && !definition.bucketStack().isEmpty()) {
                containers.add(definition.bucketStack());
            } else if (output.getFluid() == FluidRegistry.WATER) {
                containers.add(new ItemStack(Items.WATER_BUCKET));
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        if (output != null) {
            ingredients.setOutput(FluidStack.class, output);
        }
    }

    public ItemStack getInput() {
        return input;
    }

    public FluidStack getOutput() {
        return output == null ? null : output.copy();
    }

    public List<ItemStack> getContainers() {
        return containers;
    }
}
