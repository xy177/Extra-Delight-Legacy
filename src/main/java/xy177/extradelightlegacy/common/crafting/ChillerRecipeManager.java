package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChillerRecipeManager {
    private static final List<ChillerRecipe> RECIPES = new ArrayList<>();

    private ChillerRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid,
                                ItemStack container, ItemStack output, int cookingTime, boolean consumeContainer) {
        if (name == null || name.isEmpty() || output.isEmpty() || ingredients.size() > 4 || cookingTime <= 0) {
            return;
        }
        if (fluid != null && (fluid.amount <= 0 || fluid.getFluid() == null)) {
            return;
        }
        RECIPES.add(new ChillerRecipe(name, ingredients, fluid, container, output, cookingTime, consumeContainer));
    }

    public static ChillerRecipe find(ItemStack[] inputs, ItemStack container, FluidStack fluid) {
        for (ChillerRecipe recipe : RECIPES) {
            if (recipe.matches(inputs, container, fluid)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (ChillerRecipe recipe : RECIPES) {
            for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
                if (ingredient.matches(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidContainer(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (ChillerRecipe recipe : RECIPES) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(container, stack, false)) {
                return true;
            }
        }
        return false;
    }

    public static List<ChillerRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
