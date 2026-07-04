package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.block.BlockWallDecor;
import xy177.extradelightlegacy.common.tile.TileEntitySpiceRackDisplay;

public class TileEntitySpiceRackDisplayRenderer extends TileEntitySpecialRenderer<TileEntitySpiceRackDisplay> {
    private static final double[][] OFFSETS = {
        {0.20D, 0.55D, 0.15D},
        {0.40D, 0.55D, 0.125D},
        {0.60D, 0.55D, 0.15D},
        {0.80D, 0.55D, 0.125D}
    };

    @Override
    public void render(TileEntitySpiceRackDisplay tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = getFacing(tile);
        for (int i = 0; i < tile.getSlotCount(); i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (!stack.isEmpty()) {
                renderItem(stack, x, y, z, facing, OFFSETS[i][0], OFFSETS[i][1], OFFSETS[i][2]);
            }
        }
    }

    private void renderItem(ItemStack stack, double x, double y, double z, EnumFacing facing, double xoff, double yoff, double zoff) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y, z + 0.5D);
        GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(xoff - 0.5D, yoff, zoff - 0.5D);
        GlStateManager.scale(0.75F, 0.75F, 0.75F);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private EnumFacing getFacing(TileEntitySpiceRackDisplay tile) {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        if (world != null && world.getBlockState(pos).getBlock() instanceof BlockWallDecor) {
            return world.getBlockState(pos).getValue(BlockWallDecor.FACING);
        }
        return EnumFacing.NORTH;
    }

    private static float rotationForFacing(EnumFacing facing) {
        return -facing.getHorizontalAngle();
    }
}
