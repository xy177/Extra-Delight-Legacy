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
import xy177.extradelightlegacy.common.tile.TileEntityWreathDisplay;

public class TileEntityWreathDisplayRenderer extends TileEntitySpecialRenderer<TileEntityWreathDisplay> {
    @Override
    public void render(TileEntityWreathDisplay tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = getFacing(tile);
        for (int i = 0; i < tile.getSlotCount(); i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (!stack.isEmpty()) {
                renderItem(stack, x, y, z, facing, i);
            }
        }
    }

    private void renderItem(ItemStack stack, double x, double y, double z, EnumFacing facing, int slot) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
        GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0D, 0.0D, -0.35D);
        if ((slot & 1) == 0) {
            GlStateManager.translate(0.0D, 0.0D, -0.01D);
        }
        GlStateManager.rotate(slot * 45.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.0D, 0.35D, 0.05D);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(slot * -45.0F, 0.0F, 0.0F, 1.0F);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private EnumFacing getFacing(TileEntityWreathDisplay tile) {
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
