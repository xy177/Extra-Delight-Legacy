package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

public final class DoughShapingRecipe {
    private final String name;
    private final String inputOre;
    private final ItemStack output;

    DoughShapingRecipe(String name, String inputOre, ItemStack output) {
        this.name = name;
        this.inputOre = inputOre;
        this.output = output.copy();
    }

    public String getName() {
        return name;
    }

    public String getInputOre() {
        return inputOre;
    }

    public ItemStack getOutput() {
        return output.copy();
    }
}
