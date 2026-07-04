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

public class MeltingPotRecipeCategory implements IRecipeCategory<MeltingPotJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public MeltingPotRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(203, 107, 53, 47);
        this.icon = helper.createDrawableIngredient(EDLBlocks.MELTING_POT.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.MELTING_POT;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.meltingpot");
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
    public void setRecipe(IRecipeLayout recipeLayout, MeltingPotJeiRecipe recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 6, 6);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getInput());

        FluidStack output = recipeWrapper.getOutput();
        if (output != null) {
            recipeLayout.getFluidStacks().init(0, false, getWidth() / 2 + 4, 7, 16, 16, output.amount, false, null);
            recipeLayout.getFluidStacks().set(0, output);
        }

        if (!recipeWrapper.getContainers().isEmpty()) {
            recipeLayout.getItemStacks().init(1, false, getWidth() / 2 + 3, 29);
            recipeLayout.getItemStacks().set(1, recipeWrapper.getContainers());
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private int getWidth() {
        return 53;
    }
}
