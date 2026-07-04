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
import xy177.extradelightlegacy.common.block.BlockFoodDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityFoodDisplay;

public class TileEntityFoodDisplayRenderer extends TileEntitySpecialRenderer<TileEntityFoodDisplay> {
    @Override
    public void render(TileEntityFoodDisplay tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = getFacing(tile);
        double zoff = 0.0D;
        double yoff = 0.0D;
        for (int i = 0; i < tile.getSlotCount(); i++) {
            ItemStack stack = tile.getStackInSlot(i);
            double xoff = (i % 3) * 0.28D;
            if (i % 3 == 0) {
                zoff += 0.22D;
                yoff -= 0.1D;
            }
            if (!stack.isEmpty()) {
                renderItem(stack, x, y, z, facing, i, xoff - 0.28D, yoff + 0.45D, zoff - 0.4D);
            }
        }
    }

    private void renderItem(ItemStack stack, double x, double y, double z, EnumFacing facing,
                            int slot, double xoff, double yoff, double zoff) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y, z + 0.5D);
        GlStateManager.rotate(rotationForFacing(facing), 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(xoff, yoff, zoff);
        if (slot % 2 == 0) {
            GlStateManager.translate(0.0D, 0.05D, 0.05D);
        }
        GlStateManager.rotate(-45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.65F, 0.65F, 0.65F);
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private EnumFacing getFacing(TileEntityFoodDisplay tile) {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        if (world != null && world.getBlockState(pos).getBlock() instanceof BlockFoodDisplay) {
            return world.getBlockState(pos).getValue(BlockFoodDisplay.FACING);
        }
        return EnumFacing.NORTH;
    }

    private static float rotationForFacing(EnumFacing facing) {
        return -facing.getHorizontalAngle();
    }
}
