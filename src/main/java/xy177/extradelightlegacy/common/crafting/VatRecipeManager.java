package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class VatRecipeManager {
    private static final ManagedRecipeRegistry<VatRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private VatRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid,
                                   ItemStack container, ItemStack output, List<VatStage> stages) {
        if (name == null || name.isEmpty() || ingredients.isEmpty() || ingredients.size() > 6
            || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null
            || container.isEmpty() || output.isEmpty() || stages.isEmpty()) {
            return false;
        }
        for (VatStage stage : stages) {
            if (stage == null || stage.getTime() <= 0) {
                return false;
            }
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized,
            new VatRecipe(name, ingredients, fluid, container, output, stages));
    }

    public static VatRecipe find(ItemStack[] inputs, ItemStack container, FluidStack fluid) {
        for (VatRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matchesBase(inputs, container, fluid)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (VatRecipe recipe : RECIPES.getRecipes()) {
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
        for (VatRecipe recipe : RECIPES.getRecipes()) {
            if (RecipeManagerUtil.stackMatches(recipe.getContainer(), stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidStageIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (VatRecipe recipe : RECIPES.getRecipes()) {
            for (VatStage stage : recipe.getStages()) {
                if (stage.hasIngredient() && stage.getIngredient().matchesIgnoringCount(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<VatRecipe> getRecipes() {
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
