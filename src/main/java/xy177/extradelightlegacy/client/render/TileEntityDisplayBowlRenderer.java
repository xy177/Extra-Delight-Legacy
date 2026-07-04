package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.tile.TileEntityDisplayBowl;

public abstract class TileEntityDisplayBowlRenderer<T extends TileEntityDisplayBowl> extends TileEntitySpecialRenderer<T> {
    private final double[][] offsets;
    private final float scale;
    private final boolean scaleBeforeCenter;

    protected TileEntityDisplayBowlRenderer(double[][] offsets, float scale) {
        this(offsets, scale, false);
    }

    protected TileEntityDisplayBowlRenderer(double[][] offsets, float scale, boolean scaleBeforeCenter) {
        this.offsets = offsets;
        this.scale = scale;
        this.scaleBeforeCenter = scaleBeforeCenter;
    }

    @Override
    public void render(T tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        for (int i = 0; i < offsets.length; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            renderItem(stack, x, y, z, offsets[i]);
        }
    }

    private void renderItem(ItemStack stack, double x, double y, double z, double[] offset) {
        GlStateManager.pushMatrix();
        if (scaleBeforeCenter) {
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0.5D, 0.0D, 0.5D);
        } else {
            GlStateManager.translate(x + 0.5D, y, z + 0.5D);
        }
        GlStateManager.rotate((float) offset[3], 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(offset[0], offset[1], offset[2]);
        GlStateManager.rotate((float) offset[4], 1.0F, 0.0F, 0.0F);
        if (!scaleBeforeCenter) {
            GlStateManager.scale(scale, scale, scale);
        }
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
