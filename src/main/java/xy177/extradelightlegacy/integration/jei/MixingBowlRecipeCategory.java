package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

import java.util.List;

public class MixingBowlRecipeCategory implements IRecipeCategory<MixingBowlJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public MixingBowlRecipeCategory(IGuiHelper helper) {
        this.background = EDLJeiDrawables.jei(109, 182, 147, 74);
        this.icon = helper.createDrawableIngredient(EDLBlocks.MIXING_BOWL.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.MIXING_BOWL;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.mixingbowl");
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
    public void setRecipe(IRecipeLayout recipeLayout, MixingBowlJeiRecipe recipeWrapper, IIngredients ingredients) {
        List<ItemStack> inputs = recipeWrapper.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            int x = i % 3;
            int y = i / 3;
            recipeLayout.getItemStacks().init(i, true, x * 18 + 46, y * 18 + 10);
            recipeLayout.getItemStacks().set(i, inputs.get(i));
        }

        int slot = inputs.size();
        recipeLayout.getItemStacks().init(slot, true, getWidth() / 2 + 27, 11);
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getUtensil());
        slot++;

        ItemStack container = recipeWrapper.getContainer();
        if (!container.isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, getWidth() / 2 + 31, 51);
            recipeLayout.getItemStacks().set(slot, container);
            slot++;
        }

        if (!recipeWrapper.getFluidDisplays().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, -1000, -1000);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getFluidDisplays());
            slot++;
        }

        List<FluidStack> fluids = recipeWrapper.getFluids();
        for (int i = 0; i < fluids.size(); i++) {
            FluidStack fluid = fluids.get(i);
            recipeLayout.getFluidStacks().init(i, true, getWidth() / 2 - 49, 61 - i * 12, 16, 12, fluid.amount, false, null);
            recipeLayout.getFluidStacks().set(i, fluid);
        }

        recipeLayout.getItemStacks().init(slot, false, getWidth() / 2 + 56, 28);
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private int getWidth() {
        return 147;
    }
}
