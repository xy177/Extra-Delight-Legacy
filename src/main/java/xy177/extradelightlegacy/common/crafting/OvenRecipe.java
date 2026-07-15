package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

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
        return IngredientSlotMatcher.match(ingredients, inputs);
    }

    boolean matches(ItemStack[] inputs, ItemStack actualContainer) {
        if (!container.isEmpty() && (actualContainer.isEmpty() || actualContainer.getCount() < container.getCount()
            || !RecipeManagerUtil.stackMatches(container, actualContainer))) {
            return false;
        }
        return matchIngredientSlots(inputs) != null;
    }
}
