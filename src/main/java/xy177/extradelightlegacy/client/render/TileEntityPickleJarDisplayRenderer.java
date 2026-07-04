package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import xy177.extradelightlegacy.common.block.BlockPickleJarDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityPickleJarDisplay;

public class TileEntityPickleJarDisplayRenderer extends TileEntitySpecialRenderer<TileEntityPickleJarDisplay> {
    @Override
    public void render(TileEntityPickleJarDisplay tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.NORTH;
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        if (world != null && world.getBlockState(pos).getBlock() instanceof BlockPickleJarDisplay) {
            facing = world.getBlockState(pos).getValue(BlockPickleJarDisplay.FACING);
        }

        for (int i = 0; i < TileEntityPickleJarDisplay.SLOT_COUNT; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            double[] offset = sourceOffset(i);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
            GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(offset[0], -0.25D, offset[1]);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private static float rotationForFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return 270.0F;
            case SOUTH:
                return 180.0F;
            case WEST:
                return 90.0F;
            case NORTH:
            default:
                return 0.0F;
        }
    }

    private static double[] sourceOffset(int slot) {
        switch (slot) {
            case 1:
                return new double[]{-0.25D, 0.25D};
            case 2:
                return new double[]{0.25D, -0.25D};
            case 3:
                return new double[]{-0.25D, -0.25D};
            case 0:
            default:
                return new double[]{0.25D, 0.25D};
        }
    }
}
