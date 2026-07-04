package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
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
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.tile.TileEntityPickleJarDisplay;

public class BlockPickleJarDisplay extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB BOX_NORTH_2 = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.4375D);
    private static final AxisAlignedBB BOX_SOUTH_2 = new AxisAlignedBB(0.0625D, 0.0D, 0.5625D, 0.9375D, 0.5D, 0.9375D);
    private static final AxisAlignedBB BOX_WEST_2 = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.4375D, 0.5D, 0.9375D);
    private static final AxisAlignedBB BOX_EAST_2 = new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);
    private static final AxisAlignedBB BOX_FULL = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);

    public BlockPickleJarDisplay() {
        super(Material.GLASS);
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.GLASS);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPickleJarDisplay();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityPickleJarDisplay)) {
            return false;
        }
        TileEntityPickleJarDisplay display = (TileEntityPickleJarDisplay) tile;
        ItemStack held = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (!world.isRemote) {
                extractLastJar(world, pos, state, player, display);
            }
            return true;
        }
        if (isPickleJarItem(held)) {
            if (!world.isRemote && display.addJar(held)) {
                if (!player.capabilities.isCreativeMode) {
                    held.shrink(1);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.8F, 1.0F);
            }
            return true;
        }

        int slot = hitSlot(state.getValue(FACING), hitX, hitZ);
        ItemStack jar = display.getStackInSlot(slot);
        if (!jar.isEmpty() && BlockPickleJar.tryTakeServingFromJarStack(world, pos, player, hand, held, jar)) {
            display.markDirty();
            world.notifyBlockUpdate(pos, state, state, 3);
            world.markBlockRangeForRenderUpdate(pos, pos);
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPickleJarDisplay && ((TileEntityPickleJarDisplay) tile).getFilledSlots() > 1) {
            for (ItemStack stack : ((TileEntityPickleJarDisplay) tile).copyJars()) {
                if (!stack.isEmpty()) {
                    ItemStack drop = BlockPickleJar.getDropForJarStack(stack);
                    if (!drop.isEmpty()) {
                        spawnAsEntity(world, pos, drop);
                    }
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // Contents are dropped explicitly from breakBlock so the technical display block never drops itself.
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return BlockPickleJar.canJarStay(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!BlockPickleJar.canJarStay(worldIn, pos)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TileEntity tile = source.getTileEntity(pos);
        int count = tile instanceof TileEntityPickleJarDisplay
            ? ((TileEntityPickleJarDisplay) tile).getFilledSlots()
            : 2;
        if (count >= 3) {
            return BOX_FULL;
        }
        switch (state.getValue(FACING)) {
            case SOUTH:
                return BOX_SOUTH_2;
            case WEST:
                return BOX_WEST_2;
            case EAST:
                return BOX_EAST_2;
            case NORTH:
            default:
                return BOX_NORTH_2;
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
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public static boolean isPickleJarItem(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemBlock)) {
            return false;
        }
        return ((ItemBlock) stack.getItem()).getBlock() instanceof BlockPickleJar;
    }

    private static void extractLastJar(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                       TileEntityPickleJarDisplay display) {
        ItemStack extracted = display.removeLastJar();
        if (!extracted.isEmpty() && !player.inventory.addItemStackToInventory(extracted)) {
            spawnAsEntity(world, pos, extracted);
        }
        if (display.getFilledSlots() <= 1) {
            convertToSingular(world, pos, state, display);
        } else {
            world.notifyBlockUpdate(pos, state, state, 3);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    private static void convertToSingular(World world, BlockPos pos, IBlockState state, TileEntityPickleJarDisplay display) {
        ItemStack stack = display.getLastJar().copy();
        if (!isPickleJarItem(stack)) {
            world.setBlockToAir(pos);
            return;
        }
        Block jarBlock = ((ItemBlock) stack.getItem()).getBlock();
        IBlockState jarState = jarBlock.getDefaultState()
            .withProperty(BlockPickleJar.FACING, state.getValue(FACING));
        world.setBlockState(pos, jarState, 3);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof xy177.extradelightlegacy.common.tile.TileEntityFeastServings) {
            ((xy177.extradelightlegacy.common.tile.TileEntityFeastServings) tile).setServings(BlockPickleJar.getServings(stack));
        }
    }

    private static int hitSlot(EnumFacing facing, float hitX, float hitZ) {
        int raw;
        if (hitX < 0.5F && hitZ < 0.5F) {
            raw = 0;
        } else if (hitX >= 0.5F && hitZ < 0.5F) {
            raw = 1;
        } else if (hitX < 0.5F) {
            raw = 2;
        } else {
            raw = 3;
        }

        switch (facing) {
            case SOUTH:
                return 3 - raw;
            case EAST:
                return raw == 0 ? 2 : raw == 1 ? 0 : raw == 2 ? 3 : 1;
            case WEST:
                return raw == 0 ? 1 : raw == 1 ? 3 : raw == 2 ? 0 : 2;
            case NORTH:
            default:
                return raw;
        }
    }

    public static Block getDisplayBlock() {
        return EDLBlocks.JAR_DISPLAY_BLOCK.getBlock();
    }
}
