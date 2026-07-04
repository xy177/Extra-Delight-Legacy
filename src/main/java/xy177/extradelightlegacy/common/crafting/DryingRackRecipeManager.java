package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DryingRackRecipeManager {
    private static final List<DryingRackRecipe> RECIPES = new ArrayList<>();

    private DryingRackRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack input, ItemStack output, int cookingTime) {
        if (input.isEmpty() || output.isEmpty() || cookingTime <= 0) {
            return;
        }

        RECIPES.add(new DryingRackRecipe(input, output, cookingTime));
    }

    public static DryingRackRecipe find(ItemStack stack) {
        for (DryingRackRecipe recipe : RECIPES) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<DryingRackRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
