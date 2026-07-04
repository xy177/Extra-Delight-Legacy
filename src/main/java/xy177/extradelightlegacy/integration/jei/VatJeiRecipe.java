package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.VatRecipe;
import xy177.extradelightlegacy.common.crafting.VatStage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VatJeiRecipe implements IRecipeWrapper {
    private final VatRecipe recipe;

    public VatJeiRecipe(VatRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();
        for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
            ItemStack display = ingredient.getDisplayStack();
            if (!display.isEmpty()) {
                inputs.add(display);
            }
        }
        inputs.add(recipe.getContainer());
        for (VatStage stage : recipe.getStages()) {
            if (stage.hasIngredient()) {
                ItemStack display = stage.getIngredient().getDisplayStack();
                if (!display.isEmpty()) {
                    inputs.add(display);
                }
            }
        }
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setInput(FluidStack.class, recipe.getFluid());
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
    }

    public List<MixingBowlIngredient> getIngredients() {
        return recipe.getIngredients();
    }

    public FluidStack getFluid() {
        return recipe.getFluid();
    }

    public ItemStack getContainer() {
        return recipe.getContainer();
    }

    public ItemStack getOutput() {
        return recipe.getOutput();
    }

    public List<VatStage> getStages() {
        return recipe.getStages();
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        drawBackground(minecraft);

        DecimalFormat df = new DecimalFormat("##.##");
        for (int i = 0; i < recipe.getStages().size(); i++) {
            VatStage stage = recipe.getStages().get(i);
            int y = 49 + i * 31;
            minecraft.fontRenderer.drawString(I18n.format("extradelightlegacy.jei.info.vat.stage", i + 1), 0, y, 0xFFFFFF);
            minecraft.fontRenderer.drawString(
                I18n.format(stage.requiresLid() ? "extradelightlegacy.jei.info.vat.lid.on" : "extradelightlegacy.jei.info.vat.lid.off"),
                0,
                y + 9,
                stage.requiresLid() ? 0xFF5555 : 0xFFFFFF
            );
            float days = (float) stage.getTime() / 24000.0F;
            minecraft.fontRenderer.drawString(
                I18n.format(days == 1.0F ? "extradelightlegacy.jei.info.vat.day" : "extradelightlegacy.jei.info.vat.days", df.format(days)),
                0,
                y + 18,
                0xFFFFFF
            );
        }
    }

    private void drawBackground(Minecraft minecraft) {
        EDLJeiDrawables.drawJei3(minecraft, 0, 0, 0, 0, 101, 47);
        for (int i = 0; i < recipe.getStages().size(); i++) {
            EDLJeiDrawables.drawJei3(minecraft, 0, 47 + i * 31, 0, 47, 101, 31);
        }
        EDLJeiDrawables.drawJei3(minecraft, 0, 46 + recipe.getStages().size() * 31, 0, 78, 101, 33);
    }
}
