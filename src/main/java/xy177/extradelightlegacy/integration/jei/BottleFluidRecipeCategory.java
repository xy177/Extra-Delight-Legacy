package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;

public class BottleFluidRecipeCategory implements IRecipeCategory<BottleFluidJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public BottleFluidRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(84, 125, 61, 54);
        this.icon = helper.createDrawableIngredient(new ItemStack(Items.GLASS_BOTTLE));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.BOTTLE_FLUID;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.bottlefluid");
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
    public void setRecipe(IRecipeLayout recipeLayout, BottleFluidJeiRecipe recipeWrapper, IIngredients ingredients) {
        FluidStack fluid = recipeWrapper.getFluid();
        recipeLayout.getFluidStacks().init(0, false, 24, 1, 16, 16, BottleFluidRecipeManager.BOTTLE_VOLUME, true, null);
        recipeLayout.getFluidStacks().set(0, fluid);
        recipeLayout.getItemStacks().init(0, true, 1, 36);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getBottle());
        recipeLayout.getItemStacks().init(1, true, 44, 36);
        recipeLayout.getItemStacks().set(1, recipeWrapper.getBucket());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
