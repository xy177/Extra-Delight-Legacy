package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class EvaporatorRecipe {
    private final String name;
    private final FluidStack fluid;
    private final ItemStack output;
    private final int cookingTime;

    public EvaporatorRecipe(String name, FluidStack fluid, ItemStack output, int cookingTime) {
        this.name = name;
        this.fluid = fluid.copy();
        this.output = output.copy();
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public FluidStack getFluid() {
        return fluid.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public boolean matches(FluidStack tank) {
        return tank != null
            && tank.getFluid() != null
            && tank.amount >= fluid.amount
            && tank.isFluidEqual(fluid);
    }
}
