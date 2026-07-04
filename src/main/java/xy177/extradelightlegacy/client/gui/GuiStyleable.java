package xy177.extradelightlegacy.client.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.IStyleableBlock;
import xy177.extradelightlegacy.common.gui.ContainerStyleable;

import java.io.IOException;

public class GuiStyleable extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/styleable.png");
    private static final ResourceLocation SCROLLER = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/stonecutter/scroller.png");
    private static final ResourceLocation SCROLLER_DISABLED = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/stonecutter/scroller_disabled.png");
    private static final ResourceLocation RECIPE = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/stonecutter/recipe.png");
    private static final ResourceLocation RECIPE_HIGHLIGHTED = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/stonecutter/recipe_highlighted.png");
    private static final ResourceLocation PAGE_FORWARD = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/widget/page_forward.png");
    private static final ResourceLocation PAGE_FORWARD_HIGHLIGHTED = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/widget/page_forward_highlighted.png");
    private static final ResourceLocation PAGE_BACKWARD = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/widget/page_backward.png");
    private static final ResourceLocation PAGE_BACKWARD_HIGHLIGHTED = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/widget/page_backward_highlighted.png");

    private static final int PANEL_X = 8;
    private static final int PANEL_Y = 36;
    private static final int PANEL_U = 97;
    private static final int PANEL_V = 186;
    private static final int PANEL_WIDTH = 159;
    private static final int PANEL_HEIGHT = 70;
    private static final int SCROLLER_X = 146;
    private static final int SCROLLER_Y = 44;
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int SCROLLER_TRAVEL = 41;
    private static final int SCROLLER_TRACK_HEIGHT = 56;
    private static final int BACK_BUTTON_X = 13;
    private static final int FORWARD_BUTTON_X = 49;
    private static final int PAGE_BUTTON_Y = 86;
    private static final int PAGE_BUTTON_WIDTH = 23;
    private static final int PAGE_BUTTON_HEIGHT = 13;
    private static final int GRID_X = 79;
    private static final int GRID_Y = 43;
    private static final int GRID_COLUMNS = 4;
    private static final int GRID_ROWS = 3;
    private static final int GRID_CELL_WIDTH = 16;
    private static final int GRID_CELL_HEIGHT = 18;
    private static final int GRID_VISIBLE = GRID_COLUMNS * GRID_ROWS;
    private static final float SMALL_PREVIEW_SCALE = 9.0F;
    private static final float SMALL_PREVIEW_ORIGIN_X_CORRECTION = -6.5F;
    private static final float MAIN_PREVIEW_X = 43.5F;
    private static final float MAIN_PREVIEW_Y = 60.0F;
    private static final float MAIN_PREVIEW_SCALE = 30.0F;
    private static final float MAIN_PREVIEW_LEFT_CORRECTION = -22.0F;

    private final ContainerStyleable container;
    private float scrollOffset;
    private boolean scrolling;
    private int startIndex;

    public GuiStyleable(ContainerStyleable container) {
        super(container);
        this.container = container;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        normalizeScrollState();

        mc.getTextureManager().bindTexture(BACKGROUND);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(guiLeft + PANEL_X, guiTop + PANEL_Y, PANEL_U, PANEL_V, PANEL_WIDTH, PANEL_HEIGHT);

        drawPageButton(guiLeft + BACK_BUTTON_X, guiTop + PAGE_BUTTON_Y, false, mouseX, mouseY);
        drawPageButton(guiLeft + FORWARD_BUTTON_X, guiTop + PAGE_BUTTON_Y, true, mouseX, mouseY);
        drawScrollBar();
        drawStyleButtons(mouseX, mouseY);
        drawStylePreviews();
        drawCurrentStylePreview();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        scrolling = false;

        if (clickPageButton(mouseX, mouseY, guiLeft + FORWARD_BUTTON_X, guiTop + PAGE_BUTTON_Y, 0)
            || clickPageButton(mouseX, mouseY, guiLeft + BACK_BUTTON_X, guiTop + PAGE_BUTTON_Y, 1)) {
            return;
        }

        if (clickStyleButton(mouseX, mouseY)) {
            return;
        }

        if (isScrollBarActive()
            && mouseX >= guiLeft + SCROLLER_X
            && mouseX < guiLeft + SCROLLER_X + SCROLLER_WIDTH
            && mouseY >= guiTop + SCROLLER_Y
            && mouseY < guiTop + SCROLLER_Y + SCROLLER_TRACK_HEIGHT) {
            scrolling = true;
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (scrolling && isScrollBarActive()) {
            updateScrollFromMouse(mouseY);
            return;
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int wheel = Mouse.getEventDWheel();
        if (wheel == 0 || !isScrollBarActive()) {
            return;
        }

        int rows = getOffscreenRows();
        float amount = (wheel > 0 ? -1.0F : 1.0F) / (float) rows;
        scrollOffset = clamp(scrollOffset + amount);
        startIndex = Math.round(scrollOffset * rows) * GRID_COLUMNS;
    }

    private void drawPageButton(int x, int y, boolean forward, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX < x + PAGE_BUTTON_WIDTH && mouseY >= y && mouseY < y + PAGE_BUTTON_HEIGHT;
        ResourceLocation texture;
        if (forward) {
            texture = hovered ? PAGE_FORWARD_HIGHLIGHTED : PAGE_FORWARD;
        } else {
            texture = hovered ? PAGE_BACKWARD_HIGHLIGHTED : PAGE_BACKWARD;
        }

        mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, PAGE_BUTTON_WIDTH, PAGE_BUTTON_HEIGHT, PAGE_BUTTON_WIDTH, PAGE_BUTTON_HEIGHT);
    }

    private boolean clickPageButton(int mouseX, int mouseY, int x, int y, int buttonId) {
        if (mouseX < x || mouseX >= x + PAGE_BUTTON_WIDTH || mouseY < y || mouseY >= y + PAGE_BUTTON_HEIGHT) {
            return false;
        }

        if (container.getStyleable() == null || container.getStyleCount() <= 0) {
            return false;
        }

        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        mc.playerController.sendEnchantPacket(container.windowId, buttonId);
        return true;
    }

    private void drawScrollBar() {
        int offset = (int) (SCROLLER_TRAVEL * scrollOffset);
        mc.getTextureManager().bindTexture(isScrollBarActive() ? SCROLLER : SCROLLER_DISABLED);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(
            guiLeft + SCROLLER_X,
            guiTop + SCROLLER_Y + offset,
            0.0F,
            0.0F,
            SCROLLER_WIDTH,
            SCROLLER_HEIGHT,
            SCROLLER_WIDTH,
            SCROLLER_HEIGHT
        );
    }

    private void drawStyleButtons(int mouseX, int mouseY) {
        int count = container.getStyleCount();
        int end = Math.min(startIndex + GRID_VISIBLE, count);
        for (int style = startIndex; style < end; style++) {
            int local = style - startIndex;
            int x = guiLeft + GRID_X + local % GRID_COLUMNS * GRID_CELL_WIDTH;
            int y = guiTop + GRID_Y + local / GRID_COLUMNS * GRID_CELL_HEIGHT;
            boolean hovered = mouseX >= x && mouseX < x + GRID_CELL_WIDTH && mouseY >= y && mouseY < y + GRID_CELL_HEIGHT;

            mc.getTextureManager().bindTexture(hovered ? RECIPE_HIGHLIGHTED : RECIPE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            drawModalRectWithCustomSizedTexture(x, y + 1, 0.0F, 0.0F, GRID_CELL_WIDTH, GRID_CELL_HEIGHT, GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        }
    }

    private boolean clickStyleButton(int mouseX, int mouseY) {
        int count = container.getStyleCount();
        int end = Math.min(startIndex + GRID_VISIBLE, count);
        for (int style = startIndex; style < end; style++) {
            int local = style - startIndex;
            int x = guiLeft + GRID_X + local % GRID_COLUMNS * GRID_CELL_WIDTH;
            int y = guiTop + GRID_Y + local / GRID_COLUMNS * GRID_CELL_HEIGHT;
            if (mouseX >= x
                && mouseX < x + GRID_CELL_WIDTH
                && mouseY >= y
                && mouseY < y + GRID_CELL_HEIGHT) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_MAGMACUBE_SQUISH, 1.0F));
                mc.playerController.sendEnchantPacket(container.windowId, style + 2);
                return true;
            }
        }
        return false;
    }

    private void drawStylePreviews() {
        IStyleableBlock styleable = container.getStyleable();
        if (styleable == null) {
            return;
        }

        IBlockState baseState = container.getActualState();
        int count = container.getStyleCount();
        int end = Math.min(startIndex + GRID_VISIBLE, count);
        for (int style = startIndex; style < end; style++) {
            int local = style - startIndex;
            float x = guiLeft + GRID_X + local % GRID_COLUMNS * GRID_CELL_WIDTH + GRID_CELL_WIDTH / 2.0F + SMALL_PREVIEW_ORIGIN_X_CORRECTION;
            float y = guiTop + GRID_Y + local / GRID_COLUMNS * GRID_CELL_HEIGHT + 6.5F;
            renderSourceStyleBlock(styleable.withStyle(baseState, style), x, y, SMALL_PREVIEW_SCALE);
        }
    }

    private void drawCurrentStylePreview() {
        IStyleableBlock styleable = container.getStyleable();
        if (styleable == null) {
            return;
        }

        renderSourceStyleBlock(container.getActualState(), guiLeft + MAIN_PREVIEW_X + MAIN_PREVIEW_LEFT_CORRECTION, guiTop + MAIN_PREVIEW_Y, MAIN_PREVIEW_SCALE);
    }

    private void renderSourceStyleBlock(IBlockState state, float x, float y, float scale) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        try {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableDepth();
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            GlStateManager.translate(x, y, 100.0F);
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
            renderBlockLikeSource(state);
        } finally {
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableDepth();
            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void renderBlockLikeSource(IBlockState state) {
        GlStateManager.pushMatrix();
        try {
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
            GlStateManager.scale(1.0F, -1.0F, 1.0F);
            mc.getBlockRendererDispatcher().renderBlockBrightness(state, 1.0F);
        } finally {
            GlStateManager.popMatrix();
        }
    }

    private void updateScrollFromMouse(int mouseY) {
        int top = guiTop + SCROLLER_Y;
        int bottom = top + SCROLLER_TRACK_HEIGHT;
        scrollOffset = clamp(((float) mouseY - top - 7.5F) / ((float) (bottom - top) - SCROLLER_HEIGHT));
        startIndex = Math.round(scrollOffset * getOffscreenRows()) * GRID_COLUMNS;
    }

    private boolean isScrollBarActive() {
        return container.getStyleCount() > GRID_VISIBLE;
    }

    private int getOffscreenRows() {
        return Math.max(0, (container.getStyleCount() + GRID_COLUMNS - 1) / GRID_COLUMNS - GRID_ROWS);
    }

    private void normalizeScrollState() {
        if (!isScrollBarActive()) {
            scrollOffset = 0.0F;
            startIndex = 0;
            return;
        }

        int maxStart = getOffscreenRows() * GRID_COLUMNS;
        if (startIndex > maxStart) {
            startIndex = maxStart;
            scrollOffset = clamp((float) startIndex / (float) maxStart);
        }
    }

    private static float clamp(float value) {
        return Math.max(0.0F, Math.min(1.0F, value));
    }
}
