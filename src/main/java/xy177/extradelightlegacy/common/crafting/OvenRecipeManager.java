package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;

public final class OvenRecipeManager {
    private static final ManagedRecipeRegistry<OvenRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private OvenRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, List<MixingBowlIngredient> ingredients, ItemStack container,
                                   ItemStack output, int cookingTime, boolean consumeContainer) {
        if (name == null || name.isEmpty() || ingredients == null || ingredients.isEmpty() || ingredients.size() > 9
            || output.isEmpty() || cookingTime <= 0) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized,
            new OvenRecipe(name, ingredients, container, output, cookingTime, consumeContainer));
    }

    public static OvenRecipe find(ItemStack[] inputs, ItemStack container) {
        for (OvenRecipe recipe : RECIPES.getRecipes()) {
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
        for (OvenRecipe recipe : RECIPES.getRecipes()) {
            for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
                if (ingredient.matchesIgnoringCount(stack)) {
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
        for (OvenRecipe recipe : RECIPES.getRecipes()) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && RecipeManagerUtil.stackMatches(container, stack)) {
                return true;
            }
        }
        return false;
    }

    public static List<OvenRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    public static boolean remove(String id) {
        return RECIPES.remove(id);
    }

    public static int removeByOutput(ItemStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.stackMatches(output, recipe.getOutput()));
    }

    public static int removeAll() {
        return RECIPES.removeAll();
    }

    public static void captureBaseline() {
        RECIPES.captureBaseline();
    }

    public static void restoreBaseline() {
        RECIPES.restoreBaseline();
    }
}
