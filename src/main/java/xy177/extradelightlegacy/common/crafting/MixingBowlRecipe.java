package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MixingBowlRecipe {
    private final String name;
    private final List<MixingBowlIngredient> ingredients;
    private final List<MixingBowlFluidIngredient> fluids;
    private final ItemStack container;
    private final String utensilOre;
    private final ItemStack output;
    private final int stirs;

    MixingBowlRecipe(String name, List<MixingBowlIngredient> ingredients, List<MixingBowlFluidIngredient> fluids,
                     ItemStack container, String utensilOre, ItemStack output, int stirs) {
        this.name = name;
        this.ingredients = Collections.unmodifiableList(new ArrayList<>(ingredients));
        this.fluids = Collections.unmodifiableList(new ArrayList<>(fluids));
        this.container = container == null ? ItemStack.EMPTY : container.copy();
        this.utensilOre = utensilOre;
        this.output = output.copy();
        this.stirs = stirs;
    }

    public String getName() {
        return name;
    }

    public List<MixingBowlIngredient> getIngredients() {
        return ingredients;
    }

    public List<MixingBowlFluidIngredient> getFluids() {
        return fluids;
    }

    public ItemStack getContainer() {
        return container.copy();
    }

    public String getUtensilOre() {
        return utensilOre;
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getStirs() {
        return stirs;
    }
}
