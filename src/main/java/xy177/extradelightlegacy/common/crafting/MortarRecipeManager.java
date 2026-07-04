package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MortarRecipeManager {
    private static final List<MortarRecipe> RECIPES = new ArrayList<>();

    private MortarRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack input, ItemStack output, int grinds) {
        register(input, output, null, grinds);
    }

    public static void register(ItemStack input, ItemStack output, FluidStack fluidOutput, int grinds) {
        if (input.isEmpty() || (output.isEmpty() && isEmptyFluid(fluidOutput)) || grinds <= 0) {
            return;
        }

        RECIPES.add(new MortarRecipe(input, output, fluidOutput, grinds));
    }

    public static MortarRecipe find(ItemStack stack) {
        for (MortarRecipe recipe : RECIPES) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<MortarRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }

    private static boolean isEmptyFluid(FluidStack stack) {
        return stack == null || stack.amount <= 0 || stack.getFluid() == null;
    }
}
