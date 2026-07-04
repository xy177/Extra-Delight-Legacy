package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.tile.TileEntityFunnel;

public class TileEntityFunnelRenderer extends TileEntitySpecialRenderer<TileEntityFunnel> {
    @Override
    public void render(TileEntityFunnel tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        FluidStack fluid = tile.getFluid();
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null || fluid.getFluid().getStill() == null) {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getMinecraft()
            .getTextureMapBlocks()
            .getAtlasSprite(fluid.getFluid().getStill().toString());
        int color = fluid.getFluid().getColor();
        int a = color >>> 24;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        if (a == 0) {
            a = 255;
        }
        a = Math.min(a, 224);

        double min = 1.0D / 16.0D;
        double max = 15.0D / 16.0D;
        double yPos = 14.0D / 16.0D;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        buffer.pos(min, yPos, max).tex(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(max, yPos, max).tex(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(max, yPos, min).tex(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).normal(0.0F, 1.0F, 0.0F).endVertex();
        buffer.pos(min, yPos, min).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
