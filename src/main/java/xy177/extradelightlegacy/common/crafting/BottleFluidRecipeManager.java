package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class BottleFluidRecipeManager {
    public static final int BOTTLE_VOLUME = 250;
    private static final ManagedRecipeRegistry<BottleFluidRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private BottleFluidRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack bottle, FluidStack fluid, ItemStack bucket) {
        register(RecipeManagerUtil.generatedId("bottle_fluid", bottle, fluid, bucket), bottle, fluid, bucket);
    }

    public static boolean register(String id, ItemStack bottle, FluidStack fluid, ItemStack bucket) {
        if (bottle.isEmpty() || bucket.isEmpty() || fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(id);
        return normalized != null && RECIPES.register(normalized, new BottleFluidRecipe(normalized, bottle, fluid, bucket));
    }

    public static BottleFluidRecipe findByBottle(ItemStack bottle) {
        for (BottleFluidRecipe recipe : RECIPES.getRecipes()) {
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

        for (BottleFluidRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.getFluid().getFluid() == fluid) {
                return recipe;
            }
        }
        return null;
    }

    public static BottleFluidRecipe findByFluidStack(FluidStack fluid) {
        for (BottleFluidRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matchesFluid(fluid)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<BottleFluidRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    public static boolean remove(String id) {
        return RECIPES.remove(id);
    }

    public static int removeByBottle(ItemStack bottle) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.stackMatches(bottle, recipe.getBottle()));
    }

    public static int removeByFluid(FluidStack fluid) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.fluidMatches(fluid, recipe.getFluid()));
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
