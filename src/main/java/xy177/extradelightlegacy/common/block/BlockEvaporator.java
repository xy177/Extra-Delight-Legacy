package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityEvaporator;

import javax.annotation.Nullable;

public class BlockEvaporator extends Block implements ITileEntityProvider, IStyleableBlock {
    public static final PropertyInteger STYLE = PropertyInteger.create("style", 0, 4);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

    public BlockEvaporator() {
        super(Material.IRON);
        setHardness(2.0F);
        setResistance(6.0F);
        setSoundType(SoundType.METAL);
        setDefaultState(blockState.getBaseState().withProperty(STYLE, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityEvaporator)) {
            return false;
        }
        TileEntityEvaporator evaporator = (TileEntityEvaporator) tile;
        ItemStack held = player.getHeldItem(hand);

        if (held.isEmpty()) {
            return false;
        }
        if (evaporator.hasOutput()) {
            if (held.getItem() instanceof ItemSpade) {
                if (!world.isRemote) {
                    evaporator.dropItems();
                    held.damageItem(1, player);
                }
                return true;
            }
            return false;
        }

        TileEntityEvaporator.FluidContainerResult result = evaporator.tryFillFromContainer(held);
        if (result == null) {
            return false;
        }
        if (!world.isRemote) {
            held.shrink(1);
            if (held.isEmpty()) {
                player.setHeldItem(hand, result.getEmptyContainer());
            } else if (!player.inventory.addItemStackToInventory(result.getEmptyContainer())) {
                EntityItem entity = new EntityItem(world, player.posX, player.posY + 0.5D, player.posZ, result.getEmptyContainer());
                entity.setDefaultPickupDelay();
                world.spawnEntity(entity);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityEvaporator) {
            ((TileEntityEvaporator) tile).dropItems();
        }
        super.breakBlock(world, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEvaporator();
    }

    @Override
    public int getStyleCount() {
        return 5;
    }

    @Override
    public int getCurrentStyle(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    public IBlockState withStyle(IBlockState state, int style) {
        return state.withProperty(STYLE, Math.max(0, Math.min(4, style)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STYLE, Math.max(0, Math.min(4, meta & 7)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STYLE);
    }
}
