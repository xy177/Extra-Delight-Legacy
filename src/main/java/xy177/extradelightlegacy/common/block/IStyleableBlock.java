package xy177.extradelightlegacy.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.IStyleableTile;

public interface IStyleableBlock {
    int getStyleCount();

    int getCurrentStyle(IBlockState state);

    IBlockState withStyle(IBlockState state, int style);

    default int getStyleCount(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof IStyleableTile ? ((IStyleableTile) tile).getStyleCount() : getStyleCount();
    }

    default int getCurrentStyle(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof IStyleableTile ? ((IStyleableTile) tile).getStyle() : getCurrentStyle(state);
    }

    default void setNextStyle(World world, BlockPos pos, IBlockState state) {
        int next = getCurrentStyle(world, pos, state) + 1;
        if (next >= getStyleCount(world, pos, state)) {
            next = 0;
        }
        setStyle(world, pos, state, next);
    }

    default void setPrevStyle(World world, BlockPos pos, IBlockState state) {
        int prev = getCurrentStyle(world, pos, state) - 1;
        if (prev < 0) {
            prev = getStyleCount(world, pos, state) - 1;
        }
        setStyle(world, pos, state, prev);
    }

    default void setStyle(World world, BlockPos pos, IBlockState state, int style) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IStyleableTile) {
            ((IStyleableTile) tile).setStyle(style);
            tile.markDirty();
            world.notifyBlockUpdate(pos, state, state, 3);
            return;
        }
        world.setBlockState(pos, withStyle(state, style), 3);
    }

    default boolean isPatreonStyle(int style) {
        return false;
    }
}
