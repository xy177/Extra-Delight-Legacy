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
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.tile.TileEntitySinkCabinet;

public class BlockSinkCabinet extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockSinkCabinet() {
        super(Material.WOOD);
        setHardness(2.0F);
        setResistance(3.0F);
        setSoundType(SoundType.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySinkCabinet();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntitySinkCabinet)) {
            return false;
        }
        TileEntitySinkCabinet sink = (TileEntitySinkCabinet) tile;

        if (!held.isEmpty()) {
            if (held.getItem() == Items.GLASS_BOTTLE) {
                if (!world.isRemote) {
                    consumeAndGive(player, hand, held, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));
                }
                return true;
            }
            if (held.getItem() == Items.POTIONITEM && PotionUtils.getPotionFromItem(held) == PotionTypes.WATER) {
                if (!world.isRemote) {
                    consumeAndGive(player, hand, held, new ItemStack(Items.GLASS_BOTTLE));
                }
                return true;
            }
            FluidActionResult emptied = FluidUtil.tryEmptyContainer(held, sink, Integer.MAX_VALUE, player, true);
            if (emptied.isSuccess()) {
                if (!world.isRemote) {
                    consumeAndGive(player, hand, held, emptied.getResult());
                }
                return true;
            }
            FluidActionResult filled = FluidUtil.tryFillContainer(held, sink, Integer.MAX_VALUE, player, true);
            if (filled.isSuccess()) {
                if (!world.isRemote) {
                    consumeAndGive(player, hand, held, filled.getResult());
                }
                return true;
            }
        }

        if (!world.isRemote) {
            player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.SINK_CABINET, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    private static void consumeAndGive(EntityPlayer player, EnumHand hand, ItemStack held, ItemStack result) {
        if (!player.capabilities.isCreativeMode && held.getCount() == 1) {
            player.setHeldItem(hand, result.copy());
            return;
        }
        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
        }
        if (!player.inventory.addItemStackToInventory(result.copy())) {
            player.dropItem(result.copy(), false);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntitySinkCabinet) {
            for (ItemStack stack : ((TileEntitySinkCabinet) tile).copyItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
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
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
