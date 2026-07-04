package xy177.extradelightlegacy.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.ContainerMixingBowl;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuiMixingBowl extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/mixing_bowl.png");
    private static final int TANK_X = 41;
    private static final int TANK_Y = -4;
    private static final int TANK_WIDTH = 16;
    private static final int TANK_HEIGHT = 71;
    private static final int SOURCE_TANK_CAPACITY = 6000;
    private final ContainerMixingBowl bowlContainer;

    public GuiMixingBowl(ContainerMixingBowl inventorySlotsIn) {
        super(inventorySlotsIn);
        this.bowlContainer = inventorySlotsIn;
        this.xSize = 175;
        this.ySize = 166;
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
        drawTexturedModalRect(x, y - 19, 0, 0, 175, 183);
        drawFluidTank(x + TANK_X, y + TANK_Y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(String.valueOf(bowlContainer.getRequiredStirs()), 145, 14, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, 72, 4210752);
        drawUtensilPreview();
    }

    private void drawUtensilPreview() {
        ItemStack utensil = bowlContainer.getUtensilPreview();
        if (utensil.isEmpty()) {
            return;
        }

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        itemRender.renderItemAndEffectIntoGUI(utensil, 118, 5);
        itemRender.renderItemOverlayIntoGUI(fontRenderer, utensil, 118, 5, null);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void drawFluidTank(int x, int y) {
        Map<String, Integer> fluids = bowlContainer.getFluids();
        int totalAmount = bowlContainer.getFluidAmount();
        if (totalAmount <= 0 || fluids.isEmpty()) {
            return;
        }

        int drawn = 0;
        for (Map.Entry<String, Integer> entry : fluids.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isEmpty() || entry.getValue() == null || entry.getValue() <= 0) {
                continue;
            }
            int segment = Math.max(1, Math.min(TANK_HEIGHT - drawn, entry.getValue() * TANK_HEIGHT / SOURCE_TANK_CAPACITY));
            drawFluidSegment(entry.getKey(), x, y + TANK_HEIGHT - drawn - segment, segment);
            drawn += segment;
            if (drawn >= TANK_HEIGHT) {
                break;
            }
        }
        mc.getTextureManager().bindTexture(BACKGROUND);
    }

    private void drawFluidSegment(String fluidId, int x, int y, int height) {
        FluidRenderInfo info = getFluidRenderInfo(fluidId);
        TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(info.stillTexture.toString());
        if (sprite == null || "missingno".equals(sprite.getIconName())) {
            return;
        }

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();

        int a = info.color >>> 24;
        int r = info.color >> 16 & 255;
        int g = info.color >> 8 & 255;
        int b = info.color & 255;
        if (a == 0) {
            a = 255;
        }

        int remaining = height;
        int drawY = y;
        while (remaining > 0) {
            int drawHeight = Math.min(16, remaining);
            drawFluidRect(x, drawY, TANK_WIDTH, drawHeight, sprite, r, g, b, a);
            drawY += drawHeight;
            remaining -= drawHeight;
        }

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    private void drawFluidRect(int x, int y, int width, int height, TextureAtlasSprite sprite, int r, int g, int b, int a) {
        double minU = sprite.getMinU();
        double maxU = sprite.getInterpolatedU(width);
        double minV = sprite.getMinV();
        double maxV = sprite.getInterpolatedV(height);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y + height, zLevel).tex(minU, maxV).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex(maxU, maxV).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y, zLevel).tex(maxU, minV).color(r, g, b, a).endVertex();
        buffer.pos(x, y, zLevel).tex(minU, minV).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    private FluidRenderInfo getFluidRenderInfo(String fluidId) {
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xff3f76e4);
        }
        if (MixingBowlRecipeManager.FLUID_MILK.equals(fluidId)) {
            FluidStack stack = EDLFluids.milkStack(1);
            if (stack != null && stack.getFluid() != null && stack.getFluid().getStill() != null) {
                return new FluidRenderInfo(stack.getFluid().getStill(), stack.getFluid().getColor());
            }
            return new FluidRenderInfo(new ResourceLocation(ExtraDelightLegacy.MODID, "liquid/milk_still"), 0xfffff9df);
        }
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (fluidId.equals(definition.getFluidId()) && definition.getFluid() != null && definition.getFluid().getStill() != null) {
                return new FluidRenderInfo(definition.getFluid().getStill(), definition.getFluid().getColor());
            }
        }
        return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xffffffff);
    }

    private void drawFluidTooltip(int mouseX, int mouseY) {
        if (bowlContainer.getFluidAmount() <= 0 || bowlContainer.getFluids().isEmpty()) {
            return;
        }

        int x = (width - xSize) / 2 + TANK_X;
        int y = (height - ySize) / 2 + TANK_Y;
        if (mouseX < x || mouseY < y || mouseX >= x + TANK_WIDTH || mouseY >= y + TANK_HEIGHT) {
            return;
        }

        List<String> tooltip = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : bowlContainer.getFluids().entrySet()) {
            if (entry.getKey() != null && !entry.getKey().isEmpty() && entry.getValue() != null && entry.getValue() > 0) {
                tooltip.add(getFluidDisplayName(entry.getKey(), entry.getValue()) + " - " + entry.getValue() + "mB");
            }
        }
        drawHoveringText(tooltip, mouseX, mouseY);
    }

    private String getFluidDisplayName(String fluidId, int amount) {
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return I18n.format("tile.water.name");
        }
        if (MixingBowlRecipeManager.FLUID_MILK.equals(fluidId)) {
            FluidStack stack = EDLFluids.milkStack(amount);
            return stack == null ? I18n.format("item.milk.name") : stack.getLocalizedName();
        }
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (fluidId.equals(definition.getFluidId()) && definition.getFluid() != null) {
                FluidStack stack = definition.stack(amount);
                return stack == null ? fluidId : stack.getLocalizedName();
            }
        }
        return fluidId;
    }

    private static final class FluidRenderInfo {
        private final ResourceLocation stillTexture;
        private final int color;

        private FluidRenderInfo(ResourceLocation stillTexture, int color) {
            this.stillTexture = stillTexture;
            this.color = color;
        }
    }
}
