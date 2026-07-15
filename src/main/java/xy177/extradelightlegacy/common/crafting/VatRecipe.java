package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VatRecipe {
    private final String name;
    private final List<MixingBowlIngredient> ingredients;
    private final FluidStack fluid;
    private final ItemStack container;
    private final ItemStack output;
    private final List<VatStage> stages;

    VatRecipe(String name, List<MixingBowlIngredient> ingredients, FluidStack fluid, ItemStack container,
              ItemStack output, List<VatStage> stages) {
        this.name = name;
        this.ingredients = new ArrayList<>(ingredients);
        this.fluid = fluid.copy();
        this.container = container.copy();
        this.output = output.copy();
        this.stages = new ArrayList<>(stages);
    }

    public String getName() {
        return name;
    }

    public List<MixingBowlIngredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public FluidStack getFluid() {
        return fluid.copy();
    }

    public ItemStack getContainer() {
        return container.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public List<VatStage> getStages() {
        return Collections.unmodifiableList(stages);
    }

    public int getStageCount() {
        return stages.size();
    }

    public int getTotalTime() {
        int total = 0;
        for (VatStage stage : stages) {
            total += stage.getTime();
        }
        return total;
    }

    public boolean matchesBase(ItemStack[] inputs, ItemStack actualContainer, FluidStack actualFluid) {
        if (actualFluid == null || !actualFluid.isFluidEqual(fluid) || actualFluid.amount < fluid.amount) {
            return false;
        }
        if (actualContainer.isEmpty() || actualContainer.getCount() < container.getCount()
            || !RecipeManagerUtil.stackMatches(container, actualContainer)) {
            return false;
        }
        return matchIngredientSlots(inputs) != null;
    }

    public int[] matchIngredientSlots(ItemStack[] inputs) {
        return IngredientSlotMatcher.match(ingredients, inputs);
    }
}
