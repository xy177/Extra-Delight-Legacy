package xy177.extradelightlegacy.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipe;
import xy177.extradelightlegacy.common.gui.ContainerDoughShaping;

import java.io.IOException;
import java.util.List;

public class GuiDoughShaping extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/dough_shaping.png");
    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 14;
    private static final int RECIPE_COLUMNS = 4;
    private static final int RECIPE_ROWS = 3;
    private static final int RECIPE_WIDTH = 16;
    private static final int RECIPE_HEIGHT = 18;
    private final ContainerDoughShaping container;
    private float scrollOffset;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public GuiDoughShaping(ContainerDoughShaping container) {
        super(container);
        this.container = container;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        drawRecipeTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(I18n.format("tile.extradelightlegacy.dough_shaping.name"), 8, 6, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        displayRecipes = container.hasInputItem();
        int left = guiLeft;
        int top = guiTop;
        int scrollerOffset = (int) (41.0F * scrollOffset);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
        drawTexturedModalRect(left + 119, top + 15 + scrollerOffset, 176 + (isScrollBarActive() ? 0 : 12), 0, 12, 15);
        drawRecipeButtons(mouseX, mouseY, left + RECIPES_X, top + RECIPES_Y);
        drawRecipes(left + RECIPES_X, top + RECIPES_Y);
        drawResultPreview(left + 143, top + 33);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        scrolling = false;
        if (displayRecipes) {
            int left = guiLeft + RECIPES_X;
            int top = guiTop + RECIPES_Y;
            int end = startIndex + RECIPE_COLUMNS * RECIPE_ROWS;
            for (int i = startIndex; i < end && i < container.getRecipes().size(); i++) {
                int local = i - startIndex;
                int x = left + local % RECIPE_COLUMNS * RECIPE_WIDTH;
                int y = top + local / RECIPE_COLUMNS * RECIPE_HEIGHT;
                if (mouseX >= x && mouseX < x + RECIPE_WIDTH && mouseY >= y && mouseY < y + RECIPE_HEIGHT && container.enchantItem(mc.player, i)) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    mc.playerController.sendEnchantPacket(container.windowId, i);
                    return;
                }
            }

            int scrollX = guiLeft + 119;
            int scrollY = guiTop + 15;
            if (mouseX >= scrollX && mouseX < scrollX + 12 && mouseY >= scrollY && mouseY < scrollY + 54) {
                scrolling = true;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (scrolling && isScrollBarActive()) {
            int top = guiTop + RECIPES_Y;
            int bottom = top + 54;
            scrollOffset = ((float) mouseY - top - 7.5F) / ((float) (bottom - top) - 15.0F);
            scrollOffset = Math.max(0.0F, Math.min(1.0F, scrollOffset));
            startIndex = Math.round(scrollOffset * getOffscreenRows()) * RECIPE_COLUMNS;
            return;
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = org.lwjgl.input.Mouse.getEventDWheel();
        if (wheel != 0 && isScrollBarActive()) {
            int rows = getOffscreenRows();
            float delta = wheel > 0 ? -1.0F / rows : 1.0F / rows;
            scrollOffset = Math.max(0.0F, Math.min(1.0F, scrollOffset + delta));
            startIndex = Math.round(scrollOffset * rows) * RECIPE_COLUMNS;
        }
    }

    private void drawRecipeButtons(int mouseX, int mouseY, int left, int top) {
        if (!displayRecipes) {
            return;
        }

        int end = startIndex + RECIPE_COLUMNS * RECIPE_ROWS;
        for (int i = startIndex; i < end && i < container.getRecipes().size(); i++) {
            int local = i - startIndex;
            int x = left + local % RECIPE_COLUMNS * RECIPE_WIDTH;
            int y = top + local / RECIPE_COLUMNS * RECIPE_HEIGHT + 2;
            int textureY = ySize;
            if (i == container.getSelectedIndex()) {
                textureY += 18;
            } else if (mouseX >= x && mouseX < x + RECIPE_WIDTH && mouseY >= y && mouseY < y + RECIPE_HEIGHT) {
                textureY += 36;
            }
            drawTexturedModalRect(x, y - 1, 0, textureY, RECIPE_WIDTH, RECIPE_HEIGHT);
        }
    }

    private void drawRecipes(int left, int top) {
        if (!displayRecipes) {
            if (startIndex != 0 || scrollOffset != 0.0F) {
                startIndex = 0;
                scrollOffset = 0.0F;
            }
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();
        List<DoughShapingRecipe> recipes = container.getRecipes();
        int end = startIndex + RECIPE_COLUMNS * RECIPE_ROWS;
        for (int i = startIndex; i < end && i < recipes.size(); i++) {
            int local = i - startIndex;
            ItemStack output = recipes.get(i).getOutput();
            int x = left + local % RECIPE_COLUMNS * RECIPE_WIDTH;
            int y = top + local / RECIPE_COLUMNS * RECIPE_HEIGHT + 1;
            itemRender.renderItemAndEffectIntoGUI(output, x, y);
            itemRender.renderItemOverlayIntoGUI(fontRenderer, output, x, y, null);
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
    }

    private void drawResultPreview(int x, int y) {
        List<DoughShapingRecipe> recipes = container.getRecipes();
        int selectedIndex = container.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= recipes.size()) {
            return;
        }

        RenderHelper.enableGUIStandardItemLighting();
        ItemStack output = recipes.get(selectedIndex).getOutput();
        itemRender.renderItemAndEffectIntoGUI(output, x, y);
        itemRender.renderItemOverlayIntoGUI(fontRenderer, output, x, y, null);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
    }

    private void drawRecipeTooltip(int mouseX, int mouseY) {
        if (!displayRecipes) {
            return;
        }

        int left = guiLeft + RECIPES_X;
        int top = guiTop + RECIPES_Y;
        List<DoughShapingRecipe> recipes = container.getRecipes();
        int end = startIndex + RECIPE_COLUMNS * RECIPE_ROWS;
        for (int i = startIndex; i < end && i < recipes.size(); i++) {
            int local = i - startIndex;
            int x = left + local % RECIPE_COLUMNS * RECIPE_WIDTH;
            int y = top + local / RECIPE_COLUMNS * RECIPE_HEIGHT;
            if (mouseX >= x && mouseX < x + RECIPE_WIDTH && mouseY >= y && mouseY < y + RECIPE_HEIGHT) {
                renderToolTip(recipes.get(i).getOutput(), mouseX, mouseY);
                return;
            }
        }
    }

    private boolean isScrollBarActive() {
        return displayRecipes && container.getRecipes().size() > RECIPE_COLUMNS * RECIPE_ROWS;
    }

    private int getOffscreenRows() {
        return (container.getRecipes().size() + RECIPE_COLUMNS - 1) / RECIPE_COLUMNS - RECIPE_ROWS;
    }
}
