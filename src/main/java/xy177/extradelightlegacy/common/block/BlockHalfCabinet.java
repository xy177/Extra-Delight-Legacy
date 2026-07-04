package xy177.extradelightlegacy.common.block;

import com.wdcftgg.farmersdelightlegacy.common.block.BlockCabinet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockHalfCabinet extends BlockCabinet {
    private static final AxisAlignedBB EAST_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
    private static final AxisAlignedBB NORTH_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB WEST_BOX = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB SOUTH_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        if (facing == EnumFacing.EAST) {
            return EAST_BOX;
        }
        if (facing == EnumFacing.WEST) {
            return WEST_BOX;
        }
        if (facing == EnumFacing.SOUTH) {
            return SOUTH_BOX;
        }
        return NORTH_BOX;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
