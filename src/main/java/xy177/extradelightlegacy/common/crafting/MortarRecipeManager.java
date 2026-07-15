package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public final class MortarRecipeManager {
    private static final ManagedRecipeRegistry<MortarRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private MortarRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(ItemStack input, ItemStack output, int grinds) {
        register(input, output, null, grinds);
    }

    public static void register(ItemStack input, ItemStack output, FluidStack fluidOutput, int grinds) {
        register(RecipeManagerUtil.generatedId("mortar", input, output, fluidOutput),
            MixingBowlIngredient.stack(input), output, fluidOutput, grinds);
    }

    public static boolean register(String id, MixingBowlIngredient input, ItemStack output, FluidStack fluidOutput, int grinds) {
        if (input == null || input.getRequiredCount() != 1 || input.getDisplayStack().isEmpty()
            || (output.isEmpty() && isEmptyFluid(fluidOutput)) || grinds <= 0) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(id);
        return normalized != null && RECIPES.register(normalized, new MortarRecipe(normalized, input, output, fluidOutput, grinds));
    }

    public static MortarRecipe find(ItemStack stack) {
        for (MortarRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<MortarRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    public static boolean remove(String id) {
        return RECIPES.remove(id);
    }

    public static int removeByItemOutput(ItemStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.stackMatches(output, recipe.getOutput()));
    }

    public static int removeByFluidOutput(FluidStack output) {
        return RECIPES.removeIf(recipe -> RecipeManagerUtil.fluidMatches(output, recipe.getFluidOutput()));
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

    private static boolean isEmptyFluid(FluidStack stack) {
        return stack == null || stack.amount <= 0 || stack.getFluid() == null;
    }
}
