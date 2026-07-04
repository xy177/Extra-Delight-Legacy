package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EvaporatorRecipeManager {
    private static final List<EvaporatorRecipe> RECIPES = new ArrayList<>();

    private EvaporatorRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, FluidStack fluid, ItemStack output, int cookingTime) {
        if (name == null || name.isEmpty() || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null
            || output.isEmpty() || cookingTime <= 0) {
            return;
        }
        RECIPES.add(new EvaporatorRecipe(name, fluid, output, cookingTime));
    }

    public static EvaporatorRecipe find(FluidStack tank) {
        if (tank == null || tank.amount <= 0 || tank.getFluid() == null) {
            return null;
        }
        for (EvaporatorRecipe recipe : RECIPES) {
            if (recipe.matches(tank)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<EvaporatorRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
