package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityUnripeSalami;

import java.util.Random;
import java.util.function.Supplier;

public class BlockSalami extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger COUNT = PropertyInteger.create("count", 0, 3);

    private static final AxisAlignedBB SINGLE = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    private static final AxisAlignedBB DOUBLE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB TRIPLE_NS = new AxisAlignedBB(0.125D, 0.0D, 0.25D, 0.875D, 1.0D, 0.75D);
    private static final AxisAlignedBB TRIPLE_EW = new AxisAlignedBB(0.25D, 0.0D, 0.125D, 0.75D, 1.0D, 0.875D);
    private static final AxisAlignedBB QUAD_NS = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D);
    private static final AxisAlignedBB QUAD_EW = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);

    private final boolean unripe;
    private final Supplier<Item> stackItem;
    private final Supplier<Block> ripeBlock;

    public BlockSalami(boolean unripe, Supplier<Item> stackItem, Supplier<Block> ripeBlock) {
        super(Material.PLANTS);
        this.unripe = unripe;
        this.stackItem = stackItem;
        this.ripeBlock = ripeBlock;
        setHardness(0.2F);
        setResistance(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(unripe);
        setDefaultState(blockState.getBaseState()
            .withProperty(COUNT, 0)
            .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return unripe ? new TileEntityUnripeSalami() : null;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return unripe;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int count = state.getValue(COUNT);
        if (count == 0) {
            return SINGLE;
        }
        if (count == 1) {
            return DOUBLE;
        }
        switch (state.getValue(FACING)) {
            case NORTH:
            case SOUTH:
                return count == 2 ? TRIPLE_NS : QUAD_NS;
            case EAST:
            case WEST:
            default:
                return count == 2 ? TRIPLE_EW : QUAD_EW;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        Item item = stackItem.get();
        if (item == null || held.isEmpty() || held.getItem() != item || state.getValue(COUNT) >= 3) {
            return false;
        }
        if (!world.isRemote) {
            world.setBlockState(pos, state.withProperty(COUNT, state.getValue(COUNT) + 1), 3);
            if (unripe) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileEntityUnripeSalami) {
                    ((TileEntityUnripeSalami) tile).resetAge();
                }
            }
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 0.8F, 1.0F);
        }
        return true;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!unripe || worldIn.isRemote) {
            return;
        }
        if (rand.nextInt(10) != 0) {
            return;
        }
        TileEntity tile = worldIn.getTileEntity(pos);
        int age = tile instanceof TileEntityUnripeSalami ? ((TileEntityUnripeSalami) tile).getAge() : 0;
        if (age < 15) {
            if (tile instanceof TileEntityUnripeSalami) {
                ((TileEntityUnripeSalami) tile).setAge(age + 1);
            }
            return;
        }
        Block block = ripeBlock.get();
        if (block != null) {
            worldIn.setBlockState(pos, block.getDefaultState()
                .withProperty(FACING, state.getValue(FACING))
                .withProperty(COUNT, state.getValue(COUNT)), 3);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return canStay(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canStay(worldIn, pos)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
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
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() + state.getValue(COUNT) * 4;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
            .withProperty(COUNT, (meta >> 2) & 3);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Item item = stackItem.get();
        if (item != null) {
            drops.add(new ItemStack(item, state.getValue(COUNT) + 1));
        }
    }

    private boolean canStay(World world, BlockPos pos) {
        return !world.isAirBlock(pos.up());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COUNT, FACING);
    }
}
