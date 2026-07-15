package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

public final class DryingRackRecipe {
    private final String id;
    private final MixingBowlIngredient input;
    private final ItemStack output;
    private final int cookingTime;

    DryingRackRecipe(String id, MixingBowlIngredient input, ItemStack output, int cookingTime) {
        this.id = id;
        this.input = input;
        this.output = output.copy();
        this.cookingTime = cookingTime;
    }

    public String getId() {
        return id;
    }

    public boolean matches(ItemStack stack) {
        return input != null && input.matches(stack);
    }

    public ItemStack getInput() {
        return input == null ? ItemStack.EMPTY : input.getDisplayStack();
    }

    public MixingBowlIngredient getIngredient() {
        return input;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getCookingTime() {
        return cookingTime;
    }
}
