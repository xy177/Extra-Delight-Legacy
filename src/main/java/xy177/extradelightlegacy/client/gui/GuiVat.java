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
import xy177.extradelightlegacy.common.gui.ContainerVat;
import xy177.extradelightlegacy.common.tile.TileEntityVat;

import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GuiVat extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/vat.png");
    private static final int TANK_X = 32;
    private static final int TANK_Y = -5;
    private static final int TANK_WIDTH = 16;
    private static final int TANK_HEIGHT = 72;
    private final ContainerVat container;

    public GuiVat(ContainerVat inventorySlotsIn) {
        super(inventorySlotsIn);
        this.container = inventorySlotsIn;
        this.xSize = 176;
        this.ySize = 183;
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
        int y = (height - ySize) / 2 - 19;
        drawTexturedModalRect(x, y, 0, 0, 175, 183);
        drawProgress(x, y + 19);
        drawStageText(x, y + 19);
        drawFluidTank(x + TANK_X, y + 19 + TANK_Y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(I18n.format("container.inventory"), 8, 72, 4210752);
    }

    private void drawProgress(int x, int y) {
        TileEntityVat vat = container.getVat();
        if (vat == null || vat.getStageTime() <= 0 || vat.getStageTimeTotal() <= 0) {
            return;
        }
        int width = vat.getStageTime() * 87 / vat.getStageTimeTotal();
        drawTexturedModalRect(x + 62, y + 31, 0, 184, width, 18);
    }

    private void drawStageText(int x, int y) {
        TileEntityVat vat = container.getVat();
        if (vat == null) {
            return;
        }
        int total = vat.getStageTimeTotal();
        if (total <= 0) {
            fontRenderer.drawString(I18n.format("extradelightlegacy.gui.vat.not_fermenting"), x + 92, y + 56, 4210752);
            return;
        }

        DecimalFormat df = new DecimalFormat("0.##");
        df.setRoundingMode(RoundingMode.UP);
        float days = (float) (total - vat.getStageTime()) / 24000.0F;
        fontRenderer.drawString(I18n.format("extradelightlegacy.jei.info.vat.days", df.format(days)), x + 95, y + 56, 4210752);
        fontRenderer.drawString(I18n.format("extradelightlegacy.gui.vat.stage"), x + 125, y, 4210752);
        fontRenderer.drawString((vat.getStageIndex() + 1) + "/" + vat.getStageCount(), x + 131, y + 10, 4210752);
        if (vat.isLidRequired() && !vat.hasLid()) {
            fontRenderer.drawString(I18n.format("extradelightlegacy.gui.vat.needs_lid"), x + 110, y + 20, 0xff0000);
        } else if (!vat.isLidRequired() && vat.hasLid()) {
            fontRenderer.drawString(I18n.format("extradelightlegacy.gui.vat.remove_lid"), x + 113, y + 20, 0xff0000);
        }
    }

    private void drawFluidTank(int x, int y) {
        TileEntityVat vat = container.getVat();
        FluidStack fluid = vat == null ? null : vat.getFluid();
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return;
        }
        drawFluidArea(x, y, TANK_WIDTH, TANK_HEIGHT, fluid, TileEntityVat.TANK_CAPACITY);
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
        TileEntityVat vat = container.getVat();
        FluidStack fluid = vat == null ? null : vat.getFluid();
        if (fluid == null || fluid.amount <= 0) {
            return;
        }
        int x = (width - xSize) / 2 + TANK_X;
        int y = (height - ySize) / 2 - 19 + 19 + TANK_Y;
        if (mouseX < x || mouseY < y || mouseX >= x + TANK_WIDTH || mouseY >= y + TANK_HEIGHT) {
            return;
        }
        List<String> tooltip = new ArrayList<>();
        tooltip.add(fluid.getLocalizedName());
        tooltip.add(" - " + fluid.amount + "mB");
        drawHoveringText(tooltip, mouseX, mouseY);
    }
}
