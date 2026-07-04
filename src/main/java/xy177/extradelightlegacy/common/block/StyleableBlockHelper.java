package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.IStyleableTile;

public final class StyleableBlockHelper {
    public static final String TAG_STYLE = "Style";

    private StyleableBlockHelper() {
    }

    public static int getTileStyle(IBlockAccess world, BlockPos pos, int fallback) {
        TileEntity tile = world == null || pos == null ? null : world.getTileEntity(pos);
        return tile instanceof IStyleableTile ? ((IStyleableTile) tile).getStyle() : fallback;
    }

    public static ItemStack stackWithStyle(Block block, int style) {
        ItemStack stack = new ItemStack(block);
        if (style > 0) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger(TAG_STYLE, style);
            stack.setTagCompound(tag);
        }
        return stack;
    }

    public static void addDropWithTileStyle(NonNullList<ItemStack> drops, Block block, IBlockAccess world, BlockPos pos) {
        drops.add(stackWithStyle(block, getTileStyle(world, pos, 0)));
    }

    public static void applyStackStyle(World world, BlockPos pos, ItemStack stack) {
        if (world == null || pos == null || stack.isEmpty() || !stack.hasTagCompound()) {
            return;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IStyleableTile && stack.getTagCompound().hasKey(TAG_STYLE)) {
            ((IStyleableTile) tile).setStyle(stack.getTagCompound().getInteger(TAG_STYLE));
        }
    }

    public static boolean isSameBlock(IBlockState state, Block block) {
        return state != null && state.getBlock() == block;
    }
}
