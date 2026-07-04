package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OvenRecipeManager {
    private static final List<OvenRecipe> RECIPES = new ArrayList<>();

    private OvenRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, List<MixingBowlIngredient> ingredients, ItemStack container,
                                ItemStack output, int cookingTime, boolean consumeContainer) {
        if (name == null || name.isEmpty() || ingredients == null || ingredients.isEmpty() || ingredients.size() > 9
            || output.isEmpty() || cookingTime <= 0) {
            return;
        }
        RECIPES.add(new OvenRecipe(name, ingredients, container, output, cookingTime, consumeContainer));
    }

    public static OvenRecipe find(ItemStack[] inputs, ItemStack container) {
        for (OvenRecipe recipe : RECIPES) {
            if (recipe.matches(inputs, container)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (OvenRecipe recipe : RECIPES) {
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
        for (OvenRecipe recipe : RECIPES) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && OreDictionary.itemMatches(container, stack, false)) {
                return true;
            }
        }
        return false;
    }

    public static List<OvenRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
