package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.ChillerRecipe;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import java.util.ArrayList;
import java.util.List;

public class ChillerJeiRecipe implements IRecipeWrapper {
    private final List<ItemStack> inputs = new ArrayList<>();
    private final ItemStack container;
    private final ItemStack output;
    private final FluidStack fluid;
    private final List<ItemStack> fluidContainers = new ArrayList<>();
    private final List<ItemStack> chillingItems = new ArrayList<>();

    public ChillerJeiRecipe(ChillerRecipe recipe) {
        for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
            ItemStack display = ingredient.getDisplayStack();
            if (!display.isEmpty()) {
                inputs.add(display);
            }
        }
        this.container = recipe.getContainer();
        this.output = recipe.getOutput();
        this.fluid = recipe.getFluid();
        if (fluid != null && fluid.getFluid() != null) {
            BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByFluid(fluid.getFluid());
            if (bottleRecipe != null && !bottleRecipe.getBottle().isEmpty()) {
                fluidContainers.add(bottleRecipe.getBottle());
            }
            ItemStack helperBucket = FluidContainerHelper.bucketForFluid(fluid.getFluid());
            if (!helperBucket.isEmpty()) {
                fluidContainers.add(helperBucket);
            }
            EDLFluids.FluidDefinition definition = EDLFluids.find(fluid.getFluid());
            if (definition != null && !definition.bucketStack().isEmpty()
                && !ItemStack.areItemsEqual(definition.bucketStack(), helperBucket)) {
                fluidContainers.add(definition.bucketStack());
            }
        }
        chillingItems.add(new ItemStack(Blocks.ICE));
        chillingItems.add(new ItemStack(Blocks.PACKED_ICE));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> itemInputs = new ArrayList<>(inputs);
        if (!container.isEmpty()) {
            itemInputs.add(container);
        }
        ingredients.setInputs(ItemStack.class, itemInputs);
        ingredients.setOutput(ItemStack.class, output);
        if (fluid != null) {
        ingredients.setInput(FluidStack.class, fluid);
        }
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getContainer() {
        return container;
    }

    public ItemStack getOutput() {
        return output;
    }

    public FluidStack getFluid() {
        return fluid == null ? null : fluid.copy();
    }

    public List<ItemStack> getFluidContainers() {
        return fluidContainers;
    }

    public List<ItemStack> getChillingItems() {
        return chillingItems;
    }
}
