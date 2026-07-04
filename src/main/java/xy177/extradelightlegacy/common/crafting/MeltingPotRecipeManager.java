package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MeltingPotRecipeManager {
    private static final List<MeltingPotRecipe> RECIPES = new ArrayList<>();

    private MeltingPotRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, MixingBowlIngredient input, FluidStack output, int cookingTime) {
        if (name == null || name.isEmpty() || input == null || output == null || output.amount <= 0
            || output.getFluid() == null || cookingTime <= 0) {
            return;
        }
        RECIPES.add(new MeltingPotRecipe(name, input, output, cookingTime));
    }

    public static MeltingPotRecipe find(ItemStack input) {
        if (input.isEmpty()) {
            return null;
        }
        for (MeltingPotRecipe recipe : RECIPES) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidInput(ItemStack stack) {
        return find(stack) != null;
    }

    public static List<MeltingPotRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
