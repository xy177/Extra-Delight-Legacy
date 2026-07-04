package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JuicerRecipeManager {
    private static final List<JuicerRecipe> RECIPES = new ArrayList<>();

    private JuicerRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, MixingBowlIngredient input, ItemStack output, FluidStack fluidOutput, int chance) {
        if (name == null || name.isEmpty() || input == null || fluidOutput == null || fluidOutput.amount <= 0
            || fluidOutput.getFluid() == null || chance < 0) {
            return;
        }
        RECIPES.add(new JuicerRecipe(name, input, output, fluidOutput, chance));
    }

    public static JuicerRecipe find(ItemStack input) {
        if (input.isEmpty()) {
            return null;
        }
        for (JuicerRecipe recipe : RECIPES) {
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
        return Collections.unmodifiableList(RECIPES);
    }
}
