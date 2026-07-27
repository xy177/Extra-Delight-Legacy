package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.plugins.vanilla.ingredients.item.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

import java.util.List;

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
        List<List<ItemStack>> inputSlots = recipeWrapper.getInputSlots();
        BatchCycle batchCycle = recipeWrapper.isCondensedSingleInput()
            ? new BatchCycle(recipeWrapper.getBatchCounts())
            : null;
        IIngredientRenderer<ItemStack> batchRenderer = batchCycle == null
            ? null
            : new BatchCountRenderer(batchCycle);

        for (int i = 0; i < inputSlots.size(); i++) {
            int x = i % 3;
            int y = i / 3;
            if (batchRenderer != null && i == 0) {
                recipeLayout.getItemStacks().init(i, true, batchRenderer, x * 18, y * 18, 16, 16, 0, 0);
            } else {
                recipeLayout.getItemStacks().init(i, true, x * 18, y * 18);
            }
            recipeLayout.getItemStacks().set(i, inputSlots.get(i));
        }
        int slot = inputSlots.size();
        if (!recipeWrapper.getContainer().isEmpty()) {
            recipeLayout.getItemStacks().init(slot, true, 62, 46);
            recipeLayout.getItemStacks().set(slot, recipeWrapper.getContainerVariants());
            slot++;
        }
        if (batchRenderer != null) {
            recipeLayout.getItemStacks().init(slot, false, batchRenderer, 94, 19, 16, 16, 0, 0);
        } else {
            recipeLayout.getItemStacks().init(slot, false, 94, 19);
        }
        recipeLayout.getItemStacks().set(slot, recipeWrapper.getOutputVariants());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private static final class BatchCycle {
        private final List<Integer> counts;

        private BatchCycle(List<Integer> counts) {
            this.counts = counts;
        }

        private int getCurrentCount() {
            if (counts.isEmpty()) {
                return 1;
            }
            int index = (int) ((System.currentTimeMillis() / 1000L) % counts.size());
            return counts.get(index);
        }
    }

    private static final class BatchCountRenderer implements IIngredientRenderer<ItemStack> {
        private final ItemStackRenderer delegate = new ItemStackRenderer();
        private final BatchCycle batchCycle;

        private BatchCountRenderer(BatchCycle batchCycle) {
            this.batchCycle = batchCycle;
        }

        @Override
        public void render(Minecraft minecraft, int xPosition, int yPosition, ItemStack ingredient) {
            delegate.render(minecraft, xPosition, yPosition, withCurrentCount(ingredient));
        }

        @Override
        public List<String> getTooltip(Minecraft minecraft, ItemStack ingredient, ITooltipFlag tooltipFlag) {
            return delegate.getTooltip(minecraft, withCurrentCount(ingredient), tooltipFlag);
        }

        @Override
        public FontRenderer getFontRenderer(Minecraft minecraft, ItemStack ingredient) {
            return delegate.getFontRenderer(minecraft, ingredient);
        }

        private ItemStack withCurrentCount(ItemStack ingredient) {
            if (ingredient == null || ingredient.isEmpty()) {
                return ItemStack.EMPTY;
            }
            ItemStack display = ingredient.copy();
            display.setCount(batchCycle.getCurrentCount());
            return display;
        }
    }
}
