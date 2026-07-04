package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.JuicerRecipe;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JuicerJeiRecipe implements IRecipeWrapper {
    private final ItemStack input;
    private final ItemStack output;
    private final FluidStack fluidOutput;
    private final int chance;

    public JuicerJeiRecipe(JuicerRecipe recipe) {
        this.input = recipe.getInputDisplay();
        this.output = recipe.getOutput();
        this.fluidOutput = recipe.getFluidOutput();
        this.chance = recipe.getChance();
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

    public List<ItemStack> getFluidContainers() {
        if (fluidOutput == null || fluidOutput.getFluid() == null) {
            return Collections.emptyList();
        }
        List<ItemStack> stacks = new ArrayList<>();
        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByFluid(fluidOutput.getFluid());
        if (bottleRecipe != null) {
            if (!bottleRecipe.getBottle().isEmpty()) {
                stacks.add(bottleRecipe.getBottle());
            }
            if (!bottleRecipe.getBucket().isEmpty()) {
                stacks.add(bottleRecipe.getBucket());
            }
        }
        EDLFluids.FluidDefinition definition = EDLFluids.find(fluidOutput.getFluid());
        if (definition != null && !definition.bucketStack().isEmpty()
            && stacks.stream().noneMatch(stack -> ItemStack.areItemsEqual(stack, definition.bucketStack()))) {
            stacks.add(definition.bucketStack());
        }
        return stacks;
    }

    public int getChance() {
        return chance;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (!output.isEmpty()) {
            minecraft.fontRenderer.drawString(chance + "%", recipeWidth / 2 + 14, 24, 0xFFFFFF);
        }
    }
}
