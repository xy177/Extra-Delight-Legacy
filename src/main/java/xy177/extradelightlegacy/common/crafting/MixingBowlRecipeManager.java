package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class MixingBowlRecipeManager {
    public static final String FLUID_MILK = "milk";
    public static final String FLUID_WATER = "water";

    private static final ManagedRecipeRegistry<MixingBowlRecipe> RECIPES = new ManagedRecipeRegistry<>();

    private MixingBowlRecipeManager() {
    }

    public static void clear() {
        RECIPES.clear();
    }

    public static boolean register(String name, List<MixingBowlIngredient> ingredients, List<MixingBowlFluidIngredient> fluids,
                                   ItemStack container, String utensilOre, ItemStack output, int stirs) {
        if (ingredients == null || fluids == null || output.isEmpty() || stirs <= 0 || ingredients.size() > 9) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(name);
        return normalized != null && RECIPES.register(normalized,
            new MixingBowlRecipe(name, ingredients, fluids, container, utensilOre, output, stirs));
    }

    public static MixingBowlRecipe find(ItemStack[] inputs, ItemStack container, String fluidId, int fluidAmount) {
        return find(inputs, container, Collections.singletonMap(fluidId, fluidAmount));
    }

    public static MixingBowlRecipe find(ItemStack[] inputs, ItemStack container, Map<String, Integer> fluids) {
        for (MixingBowlRecipe recipe : RECIPES.getRecipes()) {
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
        for (MixingBowlRecipe recipe : RECIPES.getRecipes()) {
            for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
                if (ingredient.matchesIgnoringCount(stack)) {
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
        for (MixingBowlRecipe recipe : RECIPES.getRecipes()) {
            ItemStack container = recipe.getContainer();
            if (!container.isEmpty() && RecipeManagerUtil.stackMatches(container, stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidUtensil(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (MixingBowlRecipe recipe : RECIPES.getRecipes()) {
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
        return RECIPES.getRecipes();
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

    private static boolean matches(MixingBowlRecipe recipe, ItemStack[] inputs, ItemStack container, Map<String, Integer> fluids) {
        if (!matchesContainer(recipe.getContainer(), container)) {
            return false;
        }
        for (MixingBowlFluidIngredient fluid : recipe.getFluids()) {
            if (!fluid.matches(fluids)) {
                return false;
            }
        }
        return recipe.matchIngredientSlots(inputs) != null;
    }

    private static boolean matchesContainer(ItemStack required, ItemStack actual) {
        if (required.isEmpty()) {
            return actual.isEmpty();
        }
        return !actual.isEmpty() && actual.getCount() >= required.getCount()
            && RecipeManagerUtil.stackMatches(required, actual);
    }

    private static boolean matchesOre(ItemStack stack, String oreName) {
        if (stack.isEmpty() || oreName == null || oreName.isEmpty()) {
            return false;
        }
        for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
            if (RecipeManagerUtil.stackMatches(oreStack, stack) || matchesDamageableTool(oreStack, stack)) {
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
