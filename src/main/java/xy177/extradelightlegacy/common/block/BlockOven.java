package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.tile.TileEntityOven;
import xy177.extradelightlegacy.common.util.EDLHeatSources;

import javax.annotation.Nullable;

public class BlockOven extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum<Support> SUPPORT = PropertyEnum.create("support", Support.class);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.625D, 0.875D);

    public BlockOven() {
        super(Material.ROCK);
        setHardness(3.5F);
        setResistance(6.0F);
        setSoundType(SoundType.STONE);
        setLightOpacity(0);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SUPPORT, Support.NONE));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOven();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.OVEN, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityOven) {
            TileEntityOven oven = (TileEntityOven) tile;
            for (ItemStack stack : oven.getItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
            oven.clear();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {
        Support support = facing == EnumFacing.DOWN ? Support.HANDLE : (isHeatedBelow(world, pos) ? Support.TRAY : Support.NONE);
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(SUPPORT, support);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (state.getValue(SUPPORT) != Support.HANDLE) {
            Support support = isHeatedBelow(world, pos) ? Support.TRAY : Support.NONE;
            if (state.getValue(SUPPORT) != support) {
                world.setBlockState(pos, state.withProperty(SUPPORT, support), 3);
            }
        }
    }

    private boolean isHeatedBelow(World world, BlockPos pos) {
        return EDLHeatSources.isCookwareHeated(world, pos);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOX;
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
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Support support = Support.byMeta((meta >> 2) & 3);
        return getDefaultState().withProperty(FACING, facing).withProperty(SUPPORT, support);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(SUPPORT).meta << 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SUPPORT);
    }

    public enum Support implements IStringSerializable {
        NONE("none", 0),
        TRAY("tray", 1),
        HANDLE("handle", 2);

        private final String name;
        private final int meta;

        Support(String name, int meta) {
            this.name = name;
            this.meta = meta;
        }

        @Override
        public String getName() {
            return name;
        }

        private static Support byMeta(int meta) {
            for (Support support : values()) {
                if (support.meta == meta) {
                    return support;
                }
            }
            return NONE;
        }
    }
}
