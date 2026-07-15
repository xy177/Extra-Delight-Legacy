package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class ChillerRecipeManager {
    private static final ManagedRecipeRegistry<ChillerRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private ChillerRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid,
                                   ItemStack container, ItemStack output, int cookingTime, boolean consumeContainer) {
        if (ingredients == null || output.isEmpty() || ingredients.size() > 4 || cookingTime <= 0) {
            return false;
        }
        if (fluid != null && (fluid.amount <= 0 || fluid.getFluid() == null)) {
            return false;
        }
        if (ingredients.isEmpty() && fluid == null && (container == null || container.isEmpty())) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized,
            new ChillerRecipe(name, ingredients, fluid, container, output, cookingTime, consumeContainer));
    }

    public static ChillerRecipe find(ItemStack[] inputs, ItemStack container, FluidStack fluid) {
        for (ChillerRecipe recipe : RECIPES.getRecipes()) {
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
        for (ChillerRecipe recipe : RECIPES.getRecipes()) {
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
        for (ChillerRecipe recipe : RECIPES.getRecipes()) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && RecipeManagerUtil.stackMatches(container, stack)) {
                return true;
            }
        }
        return false;
    }

    public static List<ChillerRecipe> getRecipes() {
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
