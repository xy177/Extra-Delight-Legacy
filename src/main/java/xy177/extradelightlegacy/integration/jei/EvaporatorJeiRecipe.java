package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipe;

import java.util.Arrays;
import java.util.List;

public class EvaporatorJeiRecipe implements IRecipeWrapper {
    private final FluidStack fluid;
    private final ItemStack output;

    public EvaporatorJeiRecipe(EvaporatorRecipe recipe) {
        this.fluid = recipe.getFluid();
        this.output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(FluidStack.class, fluid);
        ingredients.setOutput(ItemStack.class, output);
    }

    public FluidStack getFluid() {
        return fluid.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public List<ItemStack> getContainers() {
        if (fluid.getFluid() == FluidRegistry.WATER) {
            return Arrays.asList(
                new ItemStack(Items.WATER_BUCKET),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)
            );
        }
        return java.util.Collections.emptyList();
    }
}
