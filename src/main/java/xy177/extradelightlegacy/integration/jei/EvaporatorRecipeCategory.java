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
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class EvaporatorRecipeCategory implements IRecipeCategory<EvaporatorJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public EvaporatorRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei3(0, 183, 84, 73);
        this.icon = helper.createDrawableIngredient(EDLBlocks.EVAPORATOR.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.EVAPORATOR;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.evaporator");
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
    public void setRecipe(IRecipeLayout recipeLayout, EvaporatorJeiRecipe recipeWrapper, IIngredients ingredients) {
        FluidStack fluid = recipeWrapper.getFluid();
        recipeLayout.getFluidStacks().init(0, true, 1, 1, 16, 71, fluid.amount, false, null);
        recipeLayout.getFluidStacks().set(0, fluid);

        recipeLayout.getItemStacks().init(2, true, 0, 53);
        recipeLayout.getItemStacks().set(2, recipeWrapper.getContainers());

        recipeLayout.getItemStacks().init(0, true, 66, 29);
        recipeLayout.getItemStacks().set(0, new ItemStack(Items.IRON_SHOVEL));

        recipeLayout.getItemStacks().init(1, false, 43, 29);
        recipeLayout.getItemStacks().set(1, recipeWrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.fontRenderer.drawString(I18n.format("extradelightlegacy.jei.info.evaporator.view"), 27, 4, 0xFFFFFF);
        minecraft.fontRenderer.drawString(I18n.format("extradelightlegacy.jei.info.evaporator.sky"), 44, 14, 0xFFFFFF);
        minecraft.fontRenderer.drawString(I18n.format("extradelightlegacy.jei.info.evaporator.extra"), 30, 50, 0xFFFFFF);
    }
}
