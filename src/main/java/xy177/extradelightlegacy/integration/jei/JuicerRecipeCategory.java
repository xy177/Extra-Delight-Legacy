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

public class JuicerRecipeCategory implements IRecipeCategory<JuicerJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public JuicerRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(0, 181, 64, 36);
        this.icon = helper.createDrawableIngredient(EDLBlocks.JUICER.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.JUICER;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.juicer");
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
    public void setRecipe(IRecipeLayout recipeLayout, JuicerJeiRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 9);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInput());
        if (!recipeWrapper.getOutput().isEmpty()) {
            recipeLayout.getItemStacks().init(1, false, 23, 18);
            recipeLayout.getItemStacks().set(1, recipeWrapper.getOutput());
        }
        FluidStack fluidOutput = recipeWrapper.getFluidOutput();
        if (fluidOutput != null) {
            recipeLayout.getFluidStacks().init(2, false, 24, 1, 16, 16, fluidOutput.amount, false, null);
            recipeLayout.getFluidStacks().set(2, fluidOutput);
            recipeLayout.getItemStacks().init(3, true, 46, 0);
            recipeLayout.getItemStacks().set(3, recipeWrapper.getFluidContainers());
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
