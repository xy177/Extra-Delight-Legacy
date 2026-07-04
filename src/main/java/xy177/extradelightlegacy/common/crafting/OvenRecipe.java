package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OvenRecipe {
    private final String name;
    private final List<MixingBowlIngredient> ingredients;
    private final ItemStack container;
    private final ItemStack output;
    private final int cookingTime;
    private final boolean consumeContainer;

    OvenRecipe(String name, List<MixingBowlIngredient> ingredients, ItemStack container, ItemStack output,
               int cookingTime, boolean consumeContainer) {
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.container = container == null ? ItemStack.EMPTY : container.copy();
        this.output = output.copy();
        this.cookingTime = cookingTime;
        this.consumeContainer = consumeContainer;
    }

    public String getName() {
        return name;
    }

    public List<MixingBowlIngredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public ItemStack getContainer() {
        return container.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public boolean shouldConsumeContainer() {
        return consumeContainer;
    }

    public int[] matchIngredientSlots(ItemStack[] inputs) {
        List<ItemStack> actual = new ArrayList<>();
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            ItemStack input = inputs[i];
            if (!input.isEmpty()) {
                actual.add(input);
                slots.add(i);
            }
        }
        if (actual.size() != ingredients.size()) {
            return null;
        }

        boolean[] used = new boolean[actual.size()];
        int[] matchedSlots = new int[ingredients.size()];
        for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
            MixingBowlIngredient ingredient = ingredients.get(ingredientIndex);
            boolean matched = false;
            for (int actualIndex = 0; actualIndex < actual.size(); actualIndex++) {
                if (!used[actualIndex] && ingredient.matches(actual.get(actualIndex))) {
                    used[actualIndex] = true;
                    matchedSlots[ingredientIndex] = slots.get(actualIndex);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return null;
            }
        }
        return matchedSlots;
    }

    boolean matches(ItemStack[] inputs, ItemStack actualContainer) {
        if (!container.isEmpty() && (actualContainer.isEmpty() || actualContainer.getCount() < container.getCount()
            || !OreDictionary.itemMatches(container, actualContainer, false))) {
            return false;
        }
        return matchIngredientSlots(inputs) != null;
    }
}
