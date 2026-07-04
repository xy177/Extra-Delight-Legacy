package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xy177.extradelightlegacy.ExtraDelightLegacy;

import java.io.IOException;

final class EDLJeiDrawables {
    static final ResourceLocation JEI = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/jei.png");
    static final ResourceLocation JEI3 = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/jei3.png");

    private static final ResourceLocation JEI_FALLBACK = new ResourceLocation(ExtraDelightLegacy.MODID, "gui/jei.png");
    private static final ResourceLocation JEI3_FALLBACK = new ResourceLocation(ExtraDelightLegacy.MODID, "gui/jei3.png");

    private EDLJeiDrawables() {
    }

    static IDrawableStatic jei(int u, int v, int width, int height) {
        return crop(JEI, JEI_FALLBACK, u, v, width, height);
    }

    static IDrawableStatic jei3(int u, int v, int width, int height) {
        return crop(JEI3, JEI3_FALLBACK, u, v, width, height);
    }

    static void drawJei3(Minecraft minecraft, int x, int y, int u, int v, int width, int height) {
        drawTexture(minecraft, JEI3, JEI3_FALLBACK, x, y, u, v, width, height);
    }

    private static IDrawableStatic crop(ResourceLocation texture, ResourceLocation fallback, int u, int v, int width, int height) {
        return new IDrawableStatic() {
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public void draw(Minecraft minecraft, int xOffset, int yOffset) {
                draw(minecraft, xOffset, yOffset, 0, 0, 0, 0);
            }

            @Override
            public void draw(Minecraft minecraft, int xOffset, int yOffset, int maskTop, int maskBottom, int maskLeft, int maskRight) {
                int drawWidth = width - maskLeft - maskRight;
                int drawHeight = height - maskTop - maskBottom;
                if (drawWidth <= 0 || drawHeight <= 0) {
                    return;
                }
                drawTexture(minecraft, texture, fallback, xOffset + maskLeft, yOffset + maskTop, u + maskLeft, v + maskTop, drawWidth, drawHeight);
            }
        };
    }

    private static void drawTexture(Minecraft minecraft, ResourceLocation texture, ResourceLocation fallback, int x, int y, int u, int v, int width, int height) {
        GlStateManager.pushAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        minecraft.getTextureManager().bindTexture(resolveTexture(minecraft, texture, fallback));
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, 256.0F, 256.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popAttrib();
    }

    private static ResourceLocation resolveTexture(Minecraft minecraft, ResourceLocation texture, ResourceLocation fallback) {
        try {
            minecraft.getResourceManager().getResource(texture).close();
            return texture;
        } catch (IOException ignored) {
            return fallback;
        }
    }
}
