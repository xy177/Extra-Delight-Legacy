package xy177.extradelightlegacy.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.ContainerChiller;
import xy177.extradelightlegacy.common.tile.TileEntityChiller;

import java.util.ArrayList;
import java.util.List;

public class GuiChiller extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/chiller.png");
    private static final int TANK_X = 43;
    private static final int TANK_Y = 13;
    private static final int TANK_WIDTH = 16;
    private static final int TANK_HEIGHT = 71;
    private static final int DRIP_X = 126;
    private static final int DRIP_Y = 73;
    private static final int DRIP_WIDTH = 16;
    private static final int DRIP_HEIGHT = 11;
    private final ContainerChiller container;

    public GuiChiller(ContainerChiller inventorySlotsIn) {
        super(inventorySlotsIn);
        this.container = inventorySlotsIn;
        this.xSize = 176;
        this.ySize = 182;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawFluidTooltip(mouseX, mouseY);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BACKGROUND);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawProgress(x, y);
        drawChill(x, y);
        drawFluidTank(x + TANK_X, y + TANK_Y);
        drawDripTray(x + DRIP_X, y + DRIP_Y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(I18n.format("tile.extradelightlegacy.chiller.name"), 8, 3, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, 90, 4210752);
    }

    private void drawProgress(int x, int y) {
        TileEntityChiller chiller = container.getChiller();
        if (chiller == null || chiller.getCookTime() <= 0 || chiller.getCookTimeTotal() <= 0) {
            return;
        }
        int width = chiller.getCookTime() * 22 / chiller.getCookTimeTotal();
        drawTexturedModalRect(x + 101, y + 42, 176, 0, width, 16);
    }

    private void drawChill(int x, int y) {
        TileEntityChiller chiller = container.getChiller();
        if (chiller == null || chiller.getChillTime() <= 0 || chiller.getChillDuration() <= 0) {
            return;
        }
        int height = chiller.getChillTime() * 12 / chiller.getChillDuration();
        drawTexturedModalRect(x + 128, y + 28 + 12 - height, 176, 16 + 12 - height, 12, height);
    }

    private void drawFluidTank(int x, int y) {
        TileEntityChiller chiller = container.getChiller();
        FluidStack fluid = chiller == null ? null : chiller.getFluid();
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return;
        }
        drawFluidArea(x, y, TANK_WIDTH, TANK_HEIGHT, fluid, TileEntityChiller.TANK_CAPACITY);
    }

    private void drawDripTray(int x, int y) {
        TileEntityChiller chiller = container.getChiller();
        FluidStack fluid = chiller == null ? null : chiller.getDripFluid();
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return;
        }
        drawFluidArea(x, y, DRIP_WIDTH, DRIP_HEIGHT, fluid, TileEntityChiller.DRIP_TANK_CAPACITY);
    }

    private void drawFluidArea(int x, int y, int width, int height, FluidStack fluid, int capacity) {
        TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
        if (sprite == null || "missingno".equals(sprite.getIconName())) {
            return;
        }

        int filled = Math.max(1, Math.min(height, fluid.amount * height / capacity));
        int top = y + height - filled;
        int color = fluid.getFluid().getColor(fluid);
        int a = color >>> 24;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        if (a == 0) {
            a = 255;
        }

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        int remaining = filled;
        int drawY = top;
        while (remaining > 0) {
            int drawHeight = Math.min(16, remaining);
            drawFluidRect(x, drawY, width, drawHeight, sprite, r, g, b, a);
            drawY += drawHeight;
            remaining -= drawHeight;
        }
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        mc.getTextureManager().bindTexture(BACKGROUND);
    }

    private void drawFluidRect(int x, int y, int width, int height, TextureAtlasSprite sprite, int r, int g, int b, int a) {
        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y + height, zLevel).tex(sprite.getMinU(), sprite.getInterpolatedV(height)).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex(sprite.getInterpolatedU(width), sprite.getInterpolatedV(height)).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y, zLevel).tex(sprite.getInterpolatedU(width), sprite.getMinV()).color(r, g, b, a).endVertex();
        buffer.pos(x, y, zLevel).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    private void drawFluidTooltip(int mouseX, int mouseY) {
        TileEntityChiller chiller = container.getChiller();
        if (chiller == null) {
            return;
        }
        if (drawFluidTooltip(mouseX, mouseY, TANK_X, TANK_Y, TANK_WIDTH, TANK_HEIGHT, chiller.getFluid())) {
            return;
        }
        drawFluidTooltip(mouseX, mouseY, DRIP_X, DRIP_Y, DRIP_WIDTH, DRIP_HEIGHT, chiller.getDripFluid());
    }

    private boolean drawFluidTooltip(int mouseX, int mouseY, int offsetX, int offsetY, int areaWidth, int areaHeight, FluidStack fluid) {
        if (fluid == null || fluid.amount <= 0) {
            return false;
        }
        int x = (width - xSize) / 2 + offsetX;
        int y = (height - ySize) / 2 + offsetY;
        if (mouseX < x || mouseY < y || mouseX >= x + areaWidth || mouseY >= y + areaHeight) {
            return false;
        }
        List<String> tooltip = new ArrayList<>();
        tooltip.add(fluid.getLocalizedName());
        tooltip.add(" - " + fluid.amount + "mB");
        drawHoveringText(tooltip, mouseX, mouseY);
        return true;
    }
}
