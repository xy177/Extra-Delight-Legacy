package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class MeltingPotRecipeManager {
    private static final ManagedRecipeRegistry<MeltingPotRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private MeltingPotRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, MixingBowlIngredient input, FluidStack output, int cookingTime) {
        if (name == null || name.isEmpty() || input == null || output == null || output.amount <= 0
            || output.getFluid() == null || cookingTime <= 0) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized, new MeltingPotRecipe(name, input, output, cookingTime));
    }

    public static MeltingPotRecipe find(ItemStack input) {
        if (input.isEmpty()) {
            return null;
        }
        for (MeltingPotRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidInput(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (MeltingPotRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.getInput().matchesIgnoringCount(stack)) {
                return true;
            }
        }
        return false;
    }

    public static List<MeltingPotRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    public static boolean remove(String id) {
        return RECIPES.remove(id);
    }

    public static int removeByFluidOutput(FluidStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.fluidMatches(output, recipe.getOutput()));
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
