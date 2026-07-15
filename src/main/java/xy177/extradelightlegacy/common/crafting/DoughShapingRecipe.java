package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

public final class DoughShapingRecipe {
    private final String name;
    private final MixingBowlIngredient input;
    private final ItemStack output;

    DoughShapingRecipe(String name, MixingBowlIngredient input, ItemStack output) {
        this.name = name;
        this.input = input;
        this.output = output.copy();
    }

    public String getName() {
        return name;
    }

    public MixingBowlIngredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output.copy();
    }
}
