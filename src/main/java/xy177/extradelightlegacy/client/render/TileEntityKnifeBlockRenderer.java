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
import xy177.extradelightlegacy.common.block.BlockKnifeBlock;
import xy177.extradelightlegacy.common.tile.TileEntityKnifeBlock;

public class TileEntityKnifeBlockRenderer extends TileEntitySpecialRenderer<TileEntityKnifeBlock> {
    private static final float[][] TRANSFORMS = {
        {0.33F, 0.65F, 0.35F, 0.0F, 90.0F, 180.0F},
        {0.50F, 0.65F, 0.35F, 0.0F, 90.0F, 180.0F},
        {0.67F, 0.65F, 0.35F, 0.0F, 90.0F, 180.0F},
        {0.33F, 0.35F, 0.125F, -45.0F, 0.0F, 225.0F}
    };

    @Override
    public void render(TileEntityKnifeBlock tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = getFacing(tile);
        for (int i = 0; i < tile.getSlotCount() && i < TRANSFORMS.length; i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (!stack.isEmpty()) {
                renderItem(stack, x, y, z, facing, TRANSFORMS[i]);
            }
        }
    }

    private void renderItem(ItemStack stack, double x, double y, double z, EnumFacing facing, float[] transform) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y, z + 0.5D);
        GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(transform[0] - 0.5F, transform[1], transform[2] - 0.5F);
        GlStateManager.rotate(transform[3], 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(transform[4], 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(transform[5], 0.0F, 0.0F, 1.0F);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private EnumFacing getFacing(TileEntityKnifeBlock tile) {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        if (world != null && world.getBlockState(pos).getBlock() instanceof BlockKnifeBlock) {
            return world.getBlockState(pos).getValue(BlockKnifeBlock.FACING);
        }
        return EnumFacing.NORTH;
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
