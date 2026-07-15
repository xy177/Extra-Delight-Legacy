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
import xy177.extradelightlegacy.common.registry.EDLItems;

public class MortarRecipeCategory implements IRecipeCategory<MortarJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(0, 0, 84, 52);
        this.icon = helper.createDrawableIngredient(EDLBlocks.MORTAR_STONE.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.MORTAR;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.mortar");
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
    public void setRecipe(IRecipeLayout recipeLayout, MortarJeiRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 8, 17);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInputs());
        recipeLayout.getItemStacks().init(1, true, 28, 0);
        recipeLayout.getItemStacks().set(1, EDLItems.PESTLE_STONE.stack(1));
        if (!recipeWrapper.getOutput().isEmpty()) {
            recipeLayout.getItemStacks().init(2, false, 60, 25);
            recipeLayout.getItemStacks().set(2, recipeWrapper.getOutput());
        }
        FluidStack fluidOutput = recipeWrapper.getFluidOutput();
        if (fluidOutput != null) {
            recipeLayout.getFluidStacks().init(3, false, 61, 8, 16, 16, fluidOutput.amount, true, null);
            recipeLayout.getFluidStacks().set(3, fluidOutput);
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
