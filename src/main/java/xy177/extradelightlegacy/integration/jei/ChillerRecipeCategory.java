package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class ChillerRecipeCategory implements IRecipeCategory<ChillerJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public ChillerRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(132, 0, 124, 73);
        this.icon = helper.createDrawableIngredient(EDLBlocks.CHILLER.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.CHILLER;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.chiller");
    }

    @Override
    public String getModName() {
        return ExtraDelightLegacy.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ChillerJeiRecipe recipeWrapper, IIngredients ingredients) {
        for (int i = 0; i < recipeWrapper.getInputs().size(); i++) {
            int x = i % 2;
            int y = i / 2;
            recipeLayout.getItemStacks().init(i, true, 44 + x * 18, 19 + y * 18);
            recipeLayout.getItemStacks().set(i, recipeWrapper.getInputs().get(i));
        }
        int slot = recipeWrapper.getInputs().size();
        if (!recipeWrapper.getContainer().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, 53, 55);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getContainer());
            slot++;
        }
        recipeLayout.getItemStacks().init(slot, false, 106, 29);
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getOutput());

        FluidStack fluid = recipeWrapper.getFluid();
        if (fluid != null) {
            recipeLayout.getFluidStacks().init(0, true, getWidth() / 2 - 38, 1, 16, 71, fluid.amount, false, null);
            recipeLayout.getFluidStacks().set(0, fluid);
        }
        slot++;

        if (!recipeWrapper.getFluidContainers().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, 0, 0);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getFluidContainers());
            slot++;
        }
        if (!recipeWrapper.getChillingItems().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, 106, 8);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getChillingItems());
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private int getWidth() {
        return 124;
    }
}
