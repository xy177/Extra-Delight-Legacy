package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

final class IngredientSlotMatcher {
    private IngredientSlotMatcher() {
    }

    static int[] match(List<MixingBowlIngredient> ingredients, ItemStack[] inputs) {
        if (ingredients == null || inputs == null) {
            return null;
        }
        List<ItemStack> actual = new ArrayList<>();
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            ItemStack input = inputs[i];
            if (input != null && !input.isEmpty()) {
                actual.add(input);
                slots.add(i);
            }
        }
        if (actual.size() != ingredients.size()) {
            return null;
        }

        int[] matchedSlots = new int[ingredients.size()];
        boolean[] used = new boolean[actual.size()];
        return matchNext(ingredients, actual, slots, 0, used, matchedSlots) ? matchedSlots : null;
    }

    private static boolean matchNext(List<MixingBowlIngredient> ingredients, List<ItemStack> actual,
                                     List<Integer> slots, int ingredientIndex, boolean[] used,
                                     int[] matchedSlots) {
        if (ingredientIndex >= ingredients.size()) {
            return true;
        }
        MixingBowlIngredient ingredient = ingredients.get(ingredientIndex);
        for (int actualIndex = 0; actualIndex < actual.size(); actualIndex++) {
            if (used[actualIndex] || !ingredient.matches(actual.get(actualIndex))) {
                continue;
            }
            used[actualIndex] = true;
            matchedSlots[ingredientIndex] = slots.get(actualIndex);
            if (matchNext(ingredients, actual, slots, ingredientIndex + 1, used, matchedSlots)) {
                return true;
            }
            used[actualIndex] = false;
        }
        return false;
    }
}
