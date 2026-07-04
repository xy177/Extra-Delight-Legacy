package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import java.util.ArrayList;
import java.util.List;

public class VatRecipeCategory implements IRecipeCategory<VatJeiRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public VatRecipeCategory(IGuiHelper helper) {
        this.background = new IDrawable() {
            @Override
            public int getWidth() {
                return 101;
            }

            @Override
            public int getHeight() {
                return 47 + 32 * 3;
            }

            @Override
            public void draw(Minecraft minecraft) {
            }

            @Override
            public void draw(Minecraft minecraft, int xOffset, int yOffset) {
            }
        };
        this.icon = helper.createDrawableIngredient(EDLBlocks.VAT.stack(1));
    }

    @Override
    public String getUid() {
        return EDLJeiRecipeTypes.VAT;
    }

    @Override
    public String getTitle() {
        return I18n.format("extradelightlegacy.jei.vat");
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
    public void setRecipe(IRecipeLayout recipeLayout, VatJeiRecipe recipeWrapper, IIngredients ingredients) {
        for (int i = 0; i < recipeWrapper.getIngredients().size(); i++) {
            int x = i % 3;
            int y = i / 3;
            recipeLayout.getItemStacks().init(i, true, 47 + x * 18, 0 + y * 18);
            recipeLayout.getItemStacks().set(i, recipeWrapper.getIngredients().get(i).getDisplayStack());
        }

        int slot = recipeWrapper.getIngredients().size();
        recipeLayout.getFluidStacks().init(0, true, 25, 1, 16, 34, 1000, false, null);
        recipeLayout.getFluidStacks().set(0, recipeWrapper.getFluid());

        recipeLayout.getItemStacks().init(slot++, true, 0, 9);
        recipeLayout.getItemStacks().set(slot - 1, fluidContainers(recipeWrapper.getFluid()));

        for (int i = 0; i < recipeWrapper.getStages().size(); i++) {
            recipeLayout.getItemStacks().init(slot++, true, 57, 58 + i * 31);
            if (recipeWrapper.getStages().get(i).hasIngredient()) {
                recipeLayout.getItemStacks().set(slot - 1, recipeWrapper.getStages().get(i).getIngredient().getDisplayStack());
            }
        }

        recipeLayout.getItemStacks().init(slot++, false, 57, 61 + recipeWrapper.getStages().size() * 31);
        recipeLayout.getItemStacks().set(slot - 1, recipeWrapper.getOutput());

        recipeLayout.getItemStacks().init(slot, true, 83, 61 + recipeWrapper.getStages().size() * 31);
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getContainer());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private static List<ItemStack> fluidContainers(FluidStack fluid) {
        List<ItemStack> stacks = new ArrayList<>();
        if (fluid == null || fluid.getFluid() == null) {
            return java.util.Collections.singletonList(ItemStack.EMPTY);
        }
        ItemStack bottle = FluidContainerHelper.bottleForFluid(fluid.getFluid());
        if (!bottle.isEmpty()) {
            stacks.add(bottle);
        }
        ItemStack bucket = FluidContainerHelper.bucketForFluid(fluid.getFluid());
        if (!bucket.isEmpty()) {
            stacks.add(bucket);
        }
        return stacks.isEmpty() ? java.util.Collections.singletonList(ItemStack.EMPTY) : stacks;
    }
}
