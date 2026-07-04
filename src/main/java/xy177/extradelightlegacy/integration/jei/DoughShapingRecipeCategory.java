package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class DoughShapingRecipeCategory implements IRecipeCategory<DoughShapingJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public DoughShapingRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(0, 238, 78, 18);
        this.icon = helper.createDrawableIngredient(EDLBlocks.DOUGH_SHAPING.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.DOUGH_SHAPING;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.doughshaping");
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
    public void setRecipe(IRecipeLayout recipeLayout, DoughShapingJeiRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 0, 0);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInputs());
        recipeLayout.getItemStacks().init(1, false, 60, 0);
        recipeLayout.getItemStacks().set(1, recipeWrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
