package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipe;

import java.util.Collections;
import java.util.List;

public class DryingRackJeiRecipe implements IRecipeWrapper {
    private final ItemStack input;
    private final ItemStack output;
    private final int cookingTime;

    public DryingRackJeiRecipe(DryingRackRecipe recipe) {
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
        this.cookingTime = recipe.getCookingTime();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}
