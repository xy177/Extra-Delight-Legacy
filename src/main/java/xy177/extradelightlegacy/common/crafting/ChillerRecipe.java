package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ChillerRecipe {
    private final String name;
    private final List<MixingBowlIngredient> ingredients;
    private final FluidStack fluid;
    private final ItemStack container;
    private final ItemStack output;
    private final int cookingTime;
    private final boolean consumeContainer;

    ChillerRecipe(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid, ItemStack container,
                  ItemStack output, int cookingTime, boolean consumeContainer) {
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.fluid = fluid == null ? null : fluid.copy();
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

    public FluidStack getFluid() {
        return fluid == null ? null : fluid.copy();
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

    boolean matches(ItemStack[] inputs, ItemStack actualContainer, FluidStack actualFluid) {
        if (fluid == null) {
            if (actualFluid != null && actualFluid.amount > 0) {
                return false;
            }
        } else if (actualFluid == null || !actualFluid.isFluidEqual(fluid) || actualFluid.amount < fluid.amount) {
                return false;
        }
        if (!container.isEmpty() && (actualContainer.isEmpty() || actualContainer.getCount() < container.getCount()
            || !RecipeManagerUtil.stackMatches(container, actualContainer))) {
            return false;
        }
        return matchIngredientSlots(inputs) != null;
    }

    public int[] matchIngredientSlots(ItemStack[] inputs) {
        return IngredientSlotMatcher.match(ingredients, inputs);
    }
}
