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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.item.ItemOffsetSpatula;
import xy177.extradelightlegacy.common.tile.TileEntityVat;

public class BlockVat extends Block implements ITileEntityProvider, IStyleableBlock {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger STYLE = PropertyInteger.create("style", 0, 16);

    public BlockVat() {
        super(Material.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STYLE, 0));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityVat();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (!held.isEmpty() && held.getItem() instanceof ItemOffsetSpatula) {
            if (!world.isRemote) {
                if (player.isSneaking()) {
                    player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.STYLEABLE, world, pos.getX(), pos.getY(), pos.getZ());
                } else {
                    setNextStyle(world, pos, state);
                    if (!player.capabilities.isCreativeMode && held.isItemStackDamageable()) {
                        held.damageItem(1, player);
                    }
                    world.playSound(null, pos, net.minecraft.init.SoundEvents.BLOCK_SLIME_PLACE, net.minecraft.util.SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return true;
        }

        if (!world.isRemote) {
            player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.VAT, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityVat) {
            TileEntityVat vat = (TileEntityVat) tile;
            for (ItemStack stack : vat.getItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
            vat.clear();
        }
        super.breakBlock(world, pos, state);
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
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        StyleableBlockHelper.addDropWithTileStyle(drops, this, world, pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STYLE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(STYLE, StyleableBlockHelper.getTileStyle(worldIn, pos, 0));
    }

    @Override
    public int getStyleCount() {
        return 17;
    }

    @Override
    public int getCurrentStyle(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    public IBlockState withStyle(IBlockState state, int style) {
        return state.withProperty(STYLE, Math.max(0, Math.min(16, style)));
    }
}
