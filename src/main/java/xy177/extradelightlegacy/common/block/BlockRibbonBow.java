package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockRibbonBow extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    private static final AxisAlignedBB NORTH = new AxisAlignedBB(0.0D, 0.0D, 12.0D / 16.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 4.0D / 16.0D);
    private static final AxisAlignedBB EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 4.0D / 16.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB WEST = new AxisAlignedBB(12.0D / 16.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB UP = new AxisAlignedBB(2.0D / 16.0D, 0.0D, 0.0D, 14.0D / 16.0D, 3.0D / 16.0D, 10.0D / 16.0D);
    private static final AxisAlignedBB DOWN = new AxisAlignedBB(2.0D / 16.0D, 13.0D / 16.0D, 6.0D / 16.0D, 14.0D / 16.0D, 1.0D, 1.0D);

    public BlockRibbonBow() {
        super(Material.CLOTH);
        setHardness(0.5F);
        setResistance(1.0F);
        setSoundType(SoundType.CLOTH);
        setLightOpacity(0);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing placedFacing = facing.getAxis().isVertical()
            ? facing
            : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, placedFacing);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            case NORTH:
            default:
                return NORTH;
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, facingFromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        switch (state.getValue(FACING)) {
            case SOUTH:
                return 0;
            case WEST:
                return 1;
            case NORTH:
                return 2;
            case EAST:
                return 3;
            case UP:
                return 4;
            case DOWN:
                return 5;
            default:
                return 2;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    private static EnumFacing facingFromMeta(int meta) {
        switch (meta) {
            case 0:
                return EnumFacing.SOUTH;
            case 1:
                return EnumFacing.WEST;
            case 3:
                return EnumFacing.EAST;
            case 4:
                return EnumFacing.UP;
            case 5:
                return EnumFacing.DOWN;
            case 2:
            default:
                return EnumFacing.NORTH;
        }
    }
}
