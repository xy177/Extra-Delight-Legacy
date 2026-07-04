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

public class OvenRecipeCategory implements IRecipeCategory<OvenJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public OvenRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(0, 53, 121, 72);
        this.icon = helper.createDrawableIngredient(EDLBlocks.OVEN.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.OVEN;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.oven");
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
    public void setRecipe(IRecipeLayout recipeLayout, OvenJeiRecipe recipeWrapper, IIngredients ingredients) {
        for (int i = 0; i < recipeWrapper.getInputSlots().size(); i++) {
            int x = i % 3;
            int y = i / 3;
            recipeLayout.getItemStacks().init(i, true, x * 18, y * 18);
            recipeLayout.getItemStacks().set(i, recipeWrapper.getInputSlots().get(i));
        }
        int slot = recipeWrapper.getInputSlots().size();
        if (!recipeWrapper.getContainer().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, 62, 46);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getContainerVariants());
            slot++;
        }
        recipeLayout.getItemStacks().init(slot, false, 94, 19);
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getOutputVariants());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
