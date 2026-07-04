package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.tile.TileEntityDryingRack;

public class TileEntityDryingRackRenderer extends TileEntitySpecialRenderer<TileEntityDryingRack> {
    @Override
    public void render(TileEntityDryingRack tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        for (int i = 0; i < TileEntityDryingRack.SLOT_COUNT; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 0.45D + (i > 3 ? 0.5D : 0.0D), z + 0.5D);
            GlStateManager.rotate(135.0F + 90.0F * (i % 4), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.25D, 0.0D, 0.0D);
            GlStateManager.scale(0.65F, 0.65F, 0.65F);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }
}
