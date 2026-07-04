package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DoughShapingRecipeManager {
    private static final List<DoughShapingRecipe> RECIPES = new ArrayList<>();

    private DoughShapingRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, String inputOre, ItemStack output) {
        if (name == null || inputOre == null || inputOre.isEmpty() || output.isEmpty()) {
            return;
        }

        RECIPES.add(new DoughShapingRecipe(name, inputOre, output));
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
        return Collections.unmodifiableList(RECIPES);
    }

    private static List<DoughShapingRecipe> findAll(ItemStack input) {
        List<DoughShapingRecipe> matches = new ArrayList<>();
        for (DoughShapingRecipe recipe : RECIPES) {
            if (matchesOre(input, recipe.getInputOre())) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    private static boolean matchesOre(ItemStack stack, String oreName) {
        for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
            if (OreDictionary.itemMatches(oreStack, stack, false)) {
                return true;
            }
        }
        return false;
    }
}
