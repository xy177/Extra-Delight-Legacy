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

import java.util.Collections;
import java.util.List;

public class DryingRackRecipeCategory implements IRecipeCategory<DryingRackJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public DryingRackRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(0, 125, 85, 47);
        this.icon = helper.createDrawableIngredient(EDLBlocks.DRYING_RACK.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.DRYING;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.dryingrack");
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
    public void setRecipe(IRecipeLayout recipeLayout, DryingRackJeiRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 3, 21);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInputs());
        recipeLayout.getItemStacks().init(1, false, 63, 21);
        recipeLayout.getItemStacks().set(1, recipeWrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 32 && mouseX <= 54 && mouseY >= 22 && mouseY <= 38) {
            return Collections.singletonList(I18n.format("extradelightlegacy.jei.dryingrack.time", 1000 / 20));
        }
        return Collections.emptyList();
    }
}
