package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BottleFluidRecipeManager {
    public static final int BOTTLE_VOLUME = 250;
    private static final List<BottleFluidRecipe> RECIPES = new ArrayList<>();

    private BottleFluidRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack bottle, FluidStack fluid, ItemStack bucket) {
        if (bottle.isEmpty() || bucket.isEmpty() || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return;
        }

        RECIPES.add(new BottleFluidRecipe(bottle, fluid, bucket));
    }

    public static BottleFluidRecipe findByBottle(ItemStack bottle) {
        for (BottleFluidRecipe recipe : RECIPES) {
            if (recipe.matchesBottle(bottle)) {
                return recipe;
            }
        }
        return null;
    }

    public static BottleFluidRecipe findByFluid(Fluid fluid) {
        if (fluid == null) {
            return null;
        }

        for (BottleFluidRecipe recipe : RECIPES) {
            if (recipe.getFluid().getFluid() == fluid) {
                return recipe;
            }
        }
        return null;
    }

    public static BottleFluidRecipe findByFluidStack(FluidStack fluid) {
        for (BottleFluidRecipe recipe : RECIPES) {
            if (recipe.matchesFluid(fluid)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<BottleFluidRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }
}
