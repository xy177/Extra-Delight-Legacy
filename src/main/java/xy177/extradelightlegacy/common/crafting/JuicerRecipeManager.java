package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class JuicerRecipeManager {
    private static final ManagedRecipeRegistry<JuicerRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private JuicerRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, MixingBowlIngredient input, ItemStack output, FluidStack fluidOutput, int chance) {
        if (name == null || name.isEmpty() || input == null || input.getRequiredCount() != 1
            || fluidOutput == null || fluidOutput.amount <= 0
            || fluidOutput.getFluid() == null || chance < 0 || chance > 100) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized, new JuicerRecipe(name, input, output, fluidOutput, chance));
    }

    public static JuicerRecipe find(ItemStack input) {
        if (input.isEmpty()) {
            return null;
        }
        for (JuicerRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidInput(ItemStack stack) {
        return find(stack) != null;
    }

    public static List<JuicerRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    public static boolean remove(String id) {
        return RECIPES.remove(id);
    }

    public static int removeByItemOutput(ItemStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.stackMatches(output, recipe.getOutput()));
    }

    public static int removeByFluidOutput(FluidStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.fluidMatches(output, recipe.getFluidOutput()));
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
