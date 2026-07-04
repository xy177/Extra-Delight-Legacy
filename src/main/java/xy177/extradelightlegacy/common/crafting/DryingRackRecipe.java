package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

public final class DryingRackRecipe {
    private final ItemStack input;
    private final ItemStack output;
    private final int cookingTime;

    DryingRackRecipe(ItemStack input, ItemStack output, int cookingTime) {
        this.input = input.copy();
        this.output = output.copy();
        this.cookingTime = cookingTime;
    }

    public boolean matches(ItemStack stack) {
        return !stack.isEmpty()
            && stack.getItem() == input.getItem()
            && (input.getMetadata() == net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE
                || input.getMetadata() == stack.getMetadata());
    }

    public ItemStack getInput() {
        return input.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getCookingTime() {
        return cookingTime;
    }
}
