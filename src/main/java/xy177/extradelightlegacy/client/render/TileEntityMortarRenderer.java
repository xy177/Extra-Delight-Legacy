package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.tile.TileEntityMortar;

public class TileEntityMortarRenderer extends TileEntitySpecialRenderer<TileEntityMortar> {
    @Override
    public void render(TileEntityMortar tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderFluid(tile, x, y, z);

        ItemStack stack = tile.getInsertedItem();
        if (stack.isEmpty()) {
            return;
        }

        for (int i = 0; i < stack.getCount(); i++) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 0.15D, z + 0.5D);
            GlStateManager.rotate((90.0F * i) + (45.0F * tile.getGrinds()), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.15D, 0.0D, 0.0D);
            float scale = 0.65F / (1.0F + tile.getGrinds());
            GlStateManager.scale(scale, scale, scale);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private void renderFluid(TileEntityMortar tile, double x, double y, double z) {
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

        double min = 5.0D / 16.0D;
        double max = 11.0D / 16.0D;
        double surfaceY = 0.14D + (0.10D * tile.getFluidFullness());

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(min, surfaceY, max).tex(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).endVertex();
        buffer.pos(max, surfaceY, max).tex(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).endVertex();
        buffer.pos(max, surfaceY, min).tex(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        buffer.pos(min, surfaceY, min).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
