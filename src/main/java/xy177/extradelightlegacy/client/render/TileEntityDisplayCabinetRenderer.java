package xy177.extradelightlegacy.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import xy177.extradelightlegacy.common.block.BlockCounterCabinet;
import xy177.extradelightlegacy.common.block.BlockSinkCabinet;
import xy177.extradelightlegacy.common.tile.TileEntityDisplayCabinet;

public class TileEntityDisplayCabinetRenderer extends TileEntitySpecialRenderer<TileEntityDisplayCabinet> {
    @Override
    public void render(TileEntityDisplayCabinet tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = tile.getStackInSlot(tile.getDisplaySlot());
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemBlock)) {
            return;
        }

        EnumFacing facing = EnumFacing.NORTH;
        if (tile.hasWorld()) {
            Block block = tile.getWorld().getBlockState(tile.getPos()).getBlock();
            if (block instanceof BlockCounterCabinet) {
                facing = tile.getWorld().getBlockState(tile.getPos()).getValue(BlockCounterCabinet.FACING);
            } else if (block instanceof BlockSinkCabinet) {
                facing = tile.getWorld().getBlockState(tile.getPos()).getValue(BlockSinkCabinet.FACING);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.94D, z + 0.5D);
        GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 0.08F, 1.0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.popMatrix();
    }
}
