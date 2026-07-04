package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.MortarRecipe;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MortarJeiRecipe implements IRecipeWrapper {
    private final ItemStack input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int grinds;

    public MortarJeiRecipe(MortarRecipe recipe) {
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
        this.fluidOutput = recipe.getFluidOutput();
        this.grinds = recipe.getGrinds();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        List<ItemStack> itemOutputs = new ArrayList<>();
        if (!output.isEmpty()) {
            itemOutputs.add(output);
        }
        if (fluidOutput != null) {
            ingredients.setOutput(FluidStack.class, fluidOutput);
            EDLFluids.FluidDefinition definition = EDLFluids.find(fluidOutput.getFluid());
            if (definition != null && !definition.bucketStack().isEmpty()) {
                itemOutputs.add(definition.bucketStack());
            }
            BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByFluid(fluidOutput.getFluid());
            if (bottleRecipe != null && !bottleRecipe.getBottle().isEmpty()) {
                itemOutputs.add(bottleRecipe.getBottle());
            }
        }
        if (!itemOutputs.isEmpty()) {
            ingredients.setOutputs(ItemStack.class, itemOutputs);
        }
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public FluidStack getFluidOutput() {
        return fluidOutput == null ? null : fluidOutput.copy();
    }

    public int getGrinds() {
        return grinds;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString("x" + grinds, 42, 8, 0xFFFFFF);
    }
}
