package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BottleFluidJeiRecipe implements IRecipeWrapper {
    private final ItemStack bottle;
    private final FluidStack fluid;
    private final ItemStack bucket;

    public BottleFluidJeiRecipe(BottleFluidRecipe recipe) {
        this.bottle = recipe.getBottle();
        this.fluid = recipe.getFluid();
        this.bucket = recipe.getBucket();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Arrays.asList(bottle, bucket));
        ingredients.setOutputs(ItemStack.class, Arrays.asList(bottle, bucket));
        ingredients.setInput(FluidStack.class, fluid);
        ingredients.setOutput(FluidStack.class, fluid);
    }

    public ItemStack getBottle() {
        return bottle;
    }

    public FluidStack getFluid() {
        return fluid.copy();
    }

    public ItemStack getBucket() {
        return bucket;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }
}
