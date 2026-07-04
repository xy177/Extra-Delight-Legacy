package xy177.extradelightlegacy.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import xy177.extradelightlegacy.common.block.BlockPicnicBasket;
import xy177.extradelightlegacy.common.tile.TileEntityPicnicBasket;

public class TileEntityPicnicBasketRenderer extends TileEntitySpecialRenderer<TileEntityPicnicBasket> {
    private static final float[][] POSITIONS = {
        {0.4F, 0.20F, 0.3F, 180.0F}, {0.4F, 0.21F, 0.7F, 15.0F},
        {0.8F, 0.20F, 0.3F, 150.0F}, {0.8F, 0.21F, 0.7F, 0.0F},
        {0.2F, 0.25F, 0.5F, 180.0F}, {0.2F, 0.231F, 0.7F, 15.0F},
        {1.0F, 0.25F, 0.3F, 10.0F}, {0.9F, 0.251F, 0.7F, 60.0F},
        {0.4F, 0.30F, 0.3F, 180.0F}, {0.4F, 0.31F, 0.7F, 15.0F},
        {0.8F, 0.30F, 0.3F, 150.0F}, {0.8F, 0.31F, 0.7F, 0.0F}
    };

    @Override
    public void render(TileEntityPicnicBasket tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.NORTH;
        if (tile.hasWorld()) {
            IBlockState state = tile.getWorld().getBlockState(tile.getPos());
            if (state.getBlock() instanceof BlockPicnicBasket) {
                facing = state.getValue(BlockPicnicBasket.FACING);
            }
        }
        float baseYaw = facing.getHorizontalAngle() + 180.0F;

        for (int i = 0; i < TileEntityPicnicBasket.SLOT_COUNT; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            float[] p = POSITIONS[i];
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y, z + 0.5D);
            GlStateManager.rotate(baseYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(p[0] - 1.0F, p[1], p[2] - 1.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(p[3], 0.0F, 0.0F, 1.0F);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }
}
