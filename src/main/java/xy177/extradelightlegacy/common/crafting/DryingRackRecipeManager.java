package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;

public final class DryingRackRecipeManager {
    private static final ManagedRecipeRegistry<DryingRackRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private DryingRackRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack input, ItemStack output, int cookingTime) {
        register(RecipeManagerUtil.generatedId("drying", input, output), MixingBowlIngredient.stack(input), output, cookingTime);
    }

    public static boolean register(String id, MixingBowlIngredient input, ItemStack output, int cookingTime) {
        if (input == null || input.getRequiredCount() != 1 || input.getDisplayStack().isEmpty()
            || output.isEmpty() || cookingTime <= 0) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(id);
        return normalized != null && RECIPES.register(normalized, new DryingRackRecipe(normalized, input, output, cookingTime));
    }

    public static DryingRackRecipe find(ItemStack stack) {
        for (DryingRackRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<DryingRackRecipe> getRecipes() {
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
