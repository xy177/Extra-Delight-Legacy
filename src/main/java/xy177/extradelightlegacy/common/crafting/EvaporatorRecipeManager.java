package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class EvaporatorRecipeManager {
    private static final ManagedRecipeRegistry<EvaporatorRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private EvaporatorRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, FluidStack fluid, ItemStack output, int cookingTime) {
        if (name == null || name.isEmpty() || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null
            || output.isEmpty() || cookingTime <= 0) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized, new EvaporatorRecipe(name, fluid, output, cookingTime));
    }

    public static EvaporatorRecipe find(FluidStack tank) {
        if (tank == null || tank.amount <= 0 || tank.getFluid() == null) {
            return null;
        }
        for (EvaporatorRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matches(tank)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<EvaporatorRecipe> getRecipes() {
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
