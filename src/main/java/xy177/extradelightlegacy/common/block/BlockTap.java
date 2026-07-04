package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityTap;
import xy177.extradelightlegacy.common.util.FluidContainerHelper;

import javax.annotation.Nullable;

public class BlockTap extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private static final AxisAlignedBB EAST_DOWN = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.5D, 0.625D, 0.625D);
    private static final AxisAlignedBB WEST_DOWN = new AxisAlignedBB(0.5D, 0.0D, 0.375D, 1.0D, 0.625D, 0.625D);
    private static final AxisAlignedBB NORTH_DOWN = new AxisAlignedBB(0.375D, 0.0D, 0.5D, 0.625D, 0.625D, 1.0D);
    private static final AxisAlignedBB SOUTH_DOWN = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.625D, 0.5D);
    private static final AxisAlignedBB EAST = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.25D, 0.625D, 0.625D);
    private static final AxisAlignedBB WEST = new AxisAlignedBB(0.75D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
    private static final AxisAlignedBB NORTH = new AxisAlignedBB(0.375D, 0.375D, 0.75D, 0.625D, 0.625D, 1.0D);
    private static final AxisAlignedBB SOUTH = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.25D);

    public BlockTap() {
        super(Material.IRON);
        setHardness(0.5F);
        setResistance(2.0F);
        setSoundType(SoundType.METAL);
        setDefaultState(blockState.getBaseState()
            .withProperty(DOWN, true)
            .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        if (facing == EnumFacing.UP) {
            EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
            return getDefaultState().withProperty(DOWN, true).withProperty(FACING, horizontal);
        }
        EnumFacing horizontal = facing.getAxis().isHorizontal() ? facing : EnumFacing.NORTH;
        return getDefaultState().withProperty(DOWN, false).withProperty(FACING, horizontal);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTap();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (held.isEmpty()) {
            return false;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityTap)) {
            return false;
        }
        if (!world.isRemote) {
            boolean handled = FluidContainerHelper.fillContainerFromSource(player, held, (TileEntityTap) tile);
            if (handled) {
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 0.6F, 1.0F);
                spawnDripParticle(world, pos, state);
            }
            return handled;
        }
        return FluidContainerHelper.canFillContainerFromSource(held);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        boolean down = state.getValue(DOWN);
        switch (state.getValue(FACING)) {
            case SOUTH:
                return down ? SOUTH_DOWN : SOUTH;
            case WEST:
                return down ? WEST_DOWN : WEST;
            case EAST:
                return down ? EAST_DOWN : EAST;
            case NORTH:
            default:
                return down ? NORTH_DOWN : NORTH;
        }
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
        return getDefaultState()
            .withProperty(DOWN, (meta & 4) != 0)
            .withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(DOWN) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DOWN, FACING);
    }

    private void spawnDripParticle(World world, BlockPos pos, IBlockState state) {
        EnumFacing out = state.getValue(FACING).getOpposite();
        double x = pos.getX() + 0.5D + 0.3D * out.getFrontOffsetX();
        double z = pos.getZ() + 0.5D + 0.3D * out.getFrontOffsetZ();
        world.spawnParticle(EnumParticleTypes.WATER_DROP, x, pos.getY() + 0.25D, z, 0.0D, 0.0D, 0.0D);
    }
}
