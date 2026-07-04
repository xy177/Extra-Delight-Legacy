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
import xy177.extradelightlegacy.common.block.BlockChocolateBox;
import xy177.extradelightlegacy.common.tile.TileEntityChocolateBox;

public class TileEntityChocolateBoxRenderer extends TileEntitySpecialRenderer<TileEntityChocolateBox> {
    @Override
    public void render(TileEntityChocolateBox tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        if (world == null || !(world.getBlockState(pos).getBlock() instanceof BlockChocolateBox)
            || !world.getBlockState(pos).getValue(BlockChocolateBox.OPEN)) {
            return;
        }

        EnumFacing facing = world.getBlockState(pos).getValue(BlockChocolateBox.FACING);
        for (int i = 0; i < TileEntityChocolateBox.SLOT_COUNT; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            double xoff = (i % 4) * 0.15D - 0.225D;
            double zoff = (i / 4 + 1) * 0.15D - 0.2D;
            if (i % 2 == 0) {
                zoff += 0.05D;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 0.1D, z + 0.5D);
            GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(xoff, 0.0D, zoff);
            GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private static float rotationForFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return -90.0F;
            case SOUTH:
                return 180.0F;
            case WEST:
                return 90.0F;
            case NORTH:
            default:
                return 0.0F;
        }
    }
}
