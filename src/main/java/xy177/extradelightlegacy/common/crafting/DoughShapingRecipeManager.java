package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DoughShapingRecipeManager {
    private static final ManagedRecipeRegistry<DoughShapingRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private DoughShapingRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, String inputOre, ItemStack output) {
        register(name, MixingBowlIngredient.ore(inputOre), output);
    }

    public static boolean register(String name, MixingBowlIngredient input, ItemStack output) {
        if (input == null || input.getDisplayStack().isEmpty() || output.isEmpty()) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized, new DoughShapingRecipe(normalized, input, output));
    }

    public static DoughShapingRecipe find(ItemStack input, int selectedIndex) {
        if (input.isEmpty()) {
            return null;
        }

        List<DoughShapingRecipe> matches = findAll(input);
        if (matches.isEmpty()) {
            return null;
        }

        int index = selectedIndex % matches.size();
        if (index < 0) {
            index += matches.size();
        }
        return matches.get(index);
    }

    public static int countMatches(ItemStack input) {
        return findAll(input).size();
    }

    public static List<DoughShapingRecipe> findMatchingRecipes(ItemStack input) {
        return Collections.unmodifiableList(findAll(input));
    }

    public static List<DoughShapingRecipe> getRecipes() {
        return RECIPES.getRecipes();
    }

    private static List<DoughShapingRecipe> findAll(ItemStack input) {
        List<DoughShapingRecipe> matches = new ArrayList<>();
        for (DoughShapingRecipe recipe : RECIPES.getRecipes()) {
            if (recipe.getInput().matches(input)) {
                matches.add(recipe);
            }
        }
        return matches;
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
