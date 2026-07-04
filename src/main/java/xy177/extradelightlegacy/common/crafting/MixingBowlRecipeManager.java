package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class MixingBowlRecipeManager {
    public static final String FLUID_MILK = "milk";
    public static final String FLUID_WATER = "water";

    private static final List<MixingBowlRecipe> RECIPES = new ArrayList<>();

    private MixingBowlRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static void register(String name, List<MixingBowlIngredient> ingredients, List<MixingBowlFluidIngredient> fluids,
                                ItemStack container, String utensilOre, ItemStack output, int stirs) {
        if (name == null || name.isEmpty() || output.isEmpty() || stirs <= 0 || ingredients.size() > 9) {
            return;
        }
        RECIPES.add(new MixingBowlRecipe(name, ingredients, fluids, container, utensilOre, output, stirs));
    }

    public static MixingBowlRecipe find(ItemStack[] inputs, ItemStack container, String fluidId, int fluidAmount) {
        return find(inputs, container, Collections.singletonMap(fluidId, fluidAmount));
    }

    public static MixingBowlRecipe find(ItemStack[] inputs, ItemStack container, Map<String, Integer> fluids) {
        for (MixingBowlRecipe recipe : RECIPES) {
            if (matches(recipe, inputs, container, fluids)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (MixingBowlRecipe recipe : RECIPES) {
            for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
                if (ingredient.matches(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidContainer(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (MixingBowlRecipe recipe : RECIPES) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && OreDictionary.itemMatches(container, stack, false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidUtensil(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (MixingBowlRecipe recipe : RECIPES) {
            if (matchesOre(stack, recipe.getUtensilOre())) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesUtensil(ItemStack stack, MixingBowlRecipe recipe) {
        return recipe != null && matchesOre(stack, recipe.getUtensilOre());
    }

    public static List<MixingBowlRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }

    private static boolean matches(MixingBowlRecipe recipe, ItemStack[] inputs, ItemStack container, Map<String, Integer> fluids) {
        if (!matchesContainer(recipe.getContainer(), container)) {
            return false;
        }
        for (MixingBowlFluidIngredient fluid : recipe.getFluids()) {
            if (!fluid.matches(fluids)) {
                return false;
            }
        }
        return matchesItems(recipe.getIngredients(), inputs);
    }

    private static boolean matchesContainer(ItemStack required, ItemStack actual) {
        if (required.isEmpty()) {
            return actual.isEmpty();
        }
        return !actual.isEmpty() && actual.getCount() >= required.getCount() && OreDictionary.itemMatches(required, actual, false);
    }

    private static boolean matchesItems(List<MixingBowlIngredient> ingredients, ItemStack[] inputs) {
        List<ItemStack> actual = new ArrayList<>();
        for (ItemStack input : inputs) {
            if (!input.isEmpty()) {
                actual.add(input);
            }
        }
        if (actual.size() != ingredients.size()) {
            return false;
        }

        boolean[] used = new boolean[actual.size()];
        for (MixingBowlIngredient ingredient : ingredients) {
            boolean matched = false;
            for (int i = 0; i < actual.size(); i++) {
                if (!used[i] && ingredient.matches(actual.get(i))) {
                    used[i] = true;
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesOre(ItemStack stack, String oreName) {
        if (stack.isEmpty() || oreName == null || oreName.isEmpty()) {
            return false;
        }
        for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
            if (OreDictionary.itemMatches(oreStack, stack, false) || matchesDamageableTool(oreStack, stack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesDamageableTool(ItemStack oreStack, ItemStack stack) {
        return !oreStack.isEmpty()
            && oreStack.getItem() == stack.getItem()
            && (oreStack.isItemStackDamageable() || stack.isItemStackDamageable());
    }
}
