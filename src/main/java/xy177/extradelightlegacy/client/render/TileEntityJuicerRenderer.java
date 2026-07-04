package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.tile.TileEntityJuicer;

public class TileEntityJuicerRenderer extends TileEntitySpecialRenderer<TileEntityJuicer> {
    private static final ResourceLocation IRON_BLOCK = new ResourceLocation("minecraft", "blocks/iron_block");
    private static final ResourceLocation COPPER_BLOCK = new ResourceLocation("extradelightlegacy", "block/copper_block");
    private static final ResourceLocation BARREL_BOTTOM = new ResourceLocation("extradelightlegacy", "block/barrel_bottom");
    private static final ResourceLocation OAK_LOG_TOP = new ResourceLocation("minecraft", "blocks/log_oak_top");

    @Override
    public void render(TileEntityJuicer tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderPress(tile, x, y, z);
        ItemStack stack = tile.getInsertedItem();
        if (!stack.isEmpty()) {
            for (int i = 0; i < stack.getCount(); i++) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + 0.5D, y + 0.15D, z + 0.5D);
                GlStateManager.rotate((90.0F * i) + (45.0F * tile.getSqueezes()), 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translate(0.15D, 0.0D, 0.0D);
                float scale = 1.0F / (1.0F + tile.getSqueezes());
                GlStateManager.scale(scale, scale, scale);
                GlStateManager.scale(0.65F, 0.65F, 0.65F);
                RenderHelper.enableStandardItemLighting();
                Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();
            }
        }
        renderFluid(tile, x, y, z);
    }

    private void renderPress(TileEntityJuicer tile, double x, double y, double z) {
        int grind = tile.getSqueezes();
        float height = ((-0.3F) / 4.0F) * (grind - 1) + 0.1F;

        GlStateManager.pushAttrib();
        setupBlockLighting(tile);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + height, z + 0.5D);
        GlStateManager.rotate((float) Math.toDegrees(grind), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5D, 0.0D, -0.5D);
        renderCrank();
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 0.875D + height, z);
        renderPlate();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderCrank() {
        TextureAtlasSprite iron = sprite(IRON_BLOCK);
        TextureAtlasSprite copper = sprite(COPPER_BLOCK);
        TextureAtlasSprite barrelBottom = sprite(BARREL_BOTTOM);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderCuboid(7, 15, 7, 9, 29, 9, 45, 'y', 8, 22, 8, iron,
            uv(1, 1, 3, 15), uv(1, 1, 3, 15), uv(1, 1, 3, 15), uv(1, 1, 3, 15), uv(1, 1, 3, 3), uv(1, 1, 3, 3));
        renderCuboid(6, 29, 6, 10, 31, 10, 0, 'y', 18, 14, 7, copper,
            uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(4, 0, 8, 4), uv(4, 0, 8, 4));
        renderCuboid(6, 15, 6, 10, 17, 10, 0, 'y', 18, 0, 7, copper,
            uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(6, 14, 10, 16), uv(4, 0, 8, 4), uv(4, 0, 8, 4));
        renderCuboid(10, 29.5D, 7.5D, 16, 30.5D, 8.5D, 45, 'x', 8, 30, 8, barrelBottom,
            uv(9, 15, 15, 16), uv(7, 15, 8, 16), uv(10, 15, 16, 16), uv(7, 15, 8, 16), uv(9, 15, 15, 16), uv(9, 15, 15, 16));
        renderCuboid(0, 29.5D, 7.5D, 6, 30.5D, 8.5D, 45, 'x', 8, 30, 8, barrelBottom,
            uv(8, 15, 14, 16), uv(8, 15, 9, 16), uv(3, 15, 9, 16), uv(8, 15, 9, 16), uv(8, 15, 14, 16), uv(8, 15, 14, 16));
        renderCuboid(7, 31, 7, 9, 32, 9, 0, 'y', 18, 15, 7, copper,
            uv(12, 13, 14, 14), uv(12, 13, 14, 14), uv(12, 13, 14, 14), uv(12, 13, 14, 14), uv(12, 12, 14, 14), uv(4, 0, 6, 2));
        renderCuboid(-1, 29, 7, 1, 31, 9, 0, 'y', 10, 14, 7, copper,
            uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 12, 14, 14), uv(4, 0, 6, 2));
        renderCuboid(15, 29, 7, 17, 31, 9, 0, 'y', 26, 14, 7, copper,
            uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 13, 14, 15), uv(12, 12, 14, 14), uv(4, 0, 6, 2));
    }

    private void renderPlate() {
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderCuboid(3, 0, 3, 13, 1, 13, 0, 'y', 3, 0, 3, sprite(OAK_LOG_TOP),
            uv(3, 3, 13, 4), uv(3, 3, 13, 4), uv(3, 3, 13, 4), uv(3, 3, 13, 4), uv(3, 3, 13, 13), uv(3, 3, 13, 13));
    }

    private static void setupBlockLighting(TileEntityJuicer tile) {
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (tile.getWorld() != null && tile.getPos() != null) {
            int light = tile.getWorld().getCombinedLight(tile.getPos().up(), 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light & 65535, light >> 16);
        }
    }

    private void renderFluid(TileEntityJuicer tile, double x, double y, double z) {
        FluidStack fluid = tile.getFluid();
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null || fluid.getFluid().getStill() == null) {
            return;
        }
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill().toString());
        int color = fluid.getFluid().getColor();
        int a = color >>> 24;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        if (a == 0) {
            a = 255;
        }
        a = Math.min(a, tile.getInsertedItem().isEmpty() ? 224 : 192);

        double min = 2.0D / 16.0D;
        double max = 14.0D / 16.0D;
        double bottomY = 2.8D / 16.0D;
        double surfaceY = bottomY + (tile.getFluidFullness() * 11.0D / 16.0D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        faceColor(buffer, sprite, r, g, b, a, 0.0F, 1.0F, 0.0F, min, surfaceY, min, min, surfaceY, max, max, surfaceY, max, max, surfaceY, min);
        faceColor(buffer, sprite, r, g, b, a, 1.0F, 0.0F, 0.0F, max, bottomY, max, max, bottomY, min, max, surfaceY, min, max, surfaceY, max);
        faceColor(buffer, sprite, r, g, b, a, -1.0F, 0.0F, 0.0F, min, bottomY, min, min, bottomY, max, min, surfaceY, max, min, surfaceY, min);
        faceColor(buffer, sprite, r, g, b, a, 0.0F, 0.0F, -1.0F, max, bottomY, min, min, bottomY, min, min, surfaceY, min, max, surfaceY, min);
        faceColor(buffer, sprite, r, g, b, a, 0.0F, 0.0F, 1.0F, min, bottomY, max, max, bottomY, max, max, surfaceY, max, min, surfaceY, max);
        faceColor(buffer, sprite, r, g, b, a, 0.0F, -1.0F, 0.0F, min, bottomY, min, max, bottomY, min, max, bottomY, max, min, bottomY, max);
        tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static TextureAtlasSprite sprite(ResourceLocation location) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    private static void renderCuboid(double minX, double minY, double minZ, double maxX, double maxY, double maxZ,
                                     double angle, char axis, double originX, double originY, double originZ,
                                     TextureAtlasSprite sprite, double[] northUv, double[] eastUv, double[] southUv,
                                     double[] westUv, double[] upUv, double[] downUv) {
        if (sprite == null || "missingno".equals(sprite.getIconName())) {
            return;
        }
        double x1 = minX / 16.0D;
        double y1 = minY / 16.0D;
        double z1 = minZ / 16.0D;
        double x2 = maxX / 16.0D;
        double y2 = maxY / 16.0D;
        double z2 = maxZ / 16.0D;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            northUv, x1, y1, z1, x2, y1, z1, x2, y2, z1, x1, y2, z1);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            southUv, x2, y1, z2, x1, y1, z2, x1, y2, z2, x2, y2, z2);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            westUv, x1, y1, z2, x1, y1, z1, x1, y2, z1, x1, y2, z2);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            eastUv, x2, y1, z1, x2, y1, z2, x2, y2, z2, x2, y2, z1);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            upUv, x1, y2, z1, x2, y2, z1, x2, y2, z2, x1, y2, z2);
        face(buffer, sprite, angle, axis, originX / 16.0D, originY / 16.0D, originZ / 16.0D,
            downUv, x1, y1, z2, x2, y1, z2, x2, y1, z1, x1, y1, z1);
        tessellator.draw();
    }

    private static void face(BufferBuilder buffer, TextureAtlasSprite sprite, double angle, char axis,
                             double originX, double originY, double originZ,
                             double[] uv,
                             double x1, double y1, double z1, double x2, double y2, double z2,
                             double x3, double y3, double z3, double x4, double y4, double z4) {
        double[] p1 = rotate(x1, y1, z1, Math.toRadians(angle), axis, originX, originY, originZ);
        double[] p2 = rotate(x2, y2, z2, Math.toRadians(angle), axis, originX, originY, originZ);
        double[] p3 = rotate(x3, y3, z3, Math.toRadians(angle), axis, originX, originY, originZ);
        double[] p4 = rotate(x4, y4, z4, Math.toRadians(angle), axis, originX, originY, originZ);
        double[] normal = normal(p4, p3, p2);
        vertex(buffer, p4[0], p4[1], p4[2], sprite.getInterpolatedU(uv[0]), sprite.getInterpolatedV(uv[1]), normal);
        vertex(buffer, p3[0], p3[1], p3[2], sprite.getInterpolatedU(uv[2]), sprite.getInterpolatedV(uv[1]), normal);
        vertex(buffer, p2[0], p2[1], p2[2], sprite.getInterpolatedU(uv[2]), sprite.getInterpolatedV(uv[3]), normal);
        vertex(buffer, p1[0], p1[1], p1[2], sprite.getInterpolatedU(uv[0]), sprite.getInterpolatedV(uv[3]), normal);
    }

    private static void faceColor(BufferBuilder buffer, TextureAtlasSprite sprite, int r, int g, int b, int a,
                                  float normalX, float normalY, float normalZ,
                                  double x1, double y1, double z1, double x2, double y2, double z2,
                                  double x3, double y3, double z3, double x4, double y4, double z4) {
        buffer.pos(x1, y1, z1).tex(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).normal(normalX, normalY, normalZ).endVertex();
        buffer.pos(x2, y2, z2).tex(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).normal(normalX, normalY, normalZ).endVertex();
        buffer.pos(x3, y3, z3).tex(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).normal(normalX, normalY, normalZ).endVertex();
        buffer.pos(x4, y4, z4).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).normal(normalX, normalY, normalZ).endVertex();
    }

    private static double[] uv(double minU, double minV, double maxU, double maxV) {
        return new double[]{minU, minV, maxU, maxV};
    }

    private static void vertex(BufferBuilder buffer, double x, double y, double z, double u, double v) {
        buffer.pos(x, y, z).tex(u, v).endVertex();
    }

    private static void vertex(BufferBuilder buffer, double x, double y, double z, double u, double v, double[] normal) {
        buffer.pos(x, y, z).tex(u, v).normal((float) normal[0], (float) normal[1], (float) normal[2]).endVertex();
    }

    private static double[] normal(double[] p1, double[] p2, double[] p3) {
        double ux = p2[0] - p1[0];
        double uy = p2[1] - p1[1];
        double uz = p2[2] - p1[2];
        double vx = p3[0] - p1[0];
        double vy = p3[1] - p1[1];
        double vz = p3[2] - p1[2];
        double nx = uy * vz - uz * vy;
        double ny = uz * vx - ux * vz;
        double nz = ux * vy - uy * vx;
        double length = Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (length < 1.0E-6D) {
            return new double[]{0.0D, 1.0D, 0.0D};
        }
        return new double[]{nx / length, ny / length, nz / length};
    }

    private static double[] rotate(double x, double y, double z, double angle, char axis,
                                   double originX, double originY, double originZ) {
        if (angle == 0.0D) {
            return new double[]{x, y, z};
        }
        double dx = x - originX;
        double dy = y - originY;
        double dz = z - originZ;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        if (axis == 'x') {
            return new double[]{x, originY + dy * cos - dz * sin, originZ + dy * sin + dz * cos};
        }
        if (axis == 'y') {
            return new double[]{originX + dx * cos + dz * sin, y, originZ - dx * sin + dz * cos};
        }
        return new double[]{originX + dx * cos - dy * sin, originY + dx * sin + dy * cos, z};
    }
}
