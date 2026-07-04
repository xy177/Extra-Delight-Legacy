package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VatRecipeManager {
    private static final List<VatRecipe> RECIPES = new ArrayList<>();

    private VatRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid,
                                ItemStack container, ItemStack output, List<VatStage> stages) {
        if (name == null || name.isEmpty() || ingredients.isEmpty() || ingredients.size() > 6
            || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null
            || container.isEmpty() || output.isEmpty() || stages.isEmpty()) {
            return;
        }
        for (VatStage stage : stages) {
            if (stage == null || stage.getTime() <= 0) {
                return;
            }
        }
        RECIPES.add(new VatRecipe(name, ingredients, fluid, container, output, stages));
    }

    public static VatRecipe find(ItemStack[] inputs, ItemStack container, FluidStack fluid) {
        for (VatRecipe recipe : RECIPES) {
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
        for (VatRecipe recipe : RECIPES) {
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
        for (VatRecipe recipe : RECIPES) {
            if (net.minecraftforge.oredict.OreDictionary.itemMatches(recipe.getContainer(), stack, false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidStageIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (VatRecipe recipe : RECIPES) {
            for (VatStage stage : recipe.getStages()) {
                if (stage.hasIngredient() && stage.getIngredient().matches(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<VatRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
