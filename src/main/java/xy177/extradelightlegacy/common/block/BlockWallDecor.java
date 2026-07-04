package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
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

public class BlockWallDecor extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final AxisAlignedBB northBox;
    private final AxisAlignedBB southBox;
    private final AxisAlignedBB eastBox;
    private final AxisAlignedBB westBox;
    private final boolean collidable;

    public BlockWallDecor(Material material, SoundType soundType, AxisAlignedBB northBox,
                          AxisAlignedBB southBox, AxisAlignedBB eastBox, AxisAlignedBB westBox,
                          boolean collidable) {
        super(material);
        this.northBox = northBox;
        this.southBox = southBox;
        this.eastBox = eastBox;
        this.westBox = westBox;
        this.collidable = collidable;
        setHardness(0.5F);
        setResistance(1.0F);
        setSoundType(soundType);
        setLightOpacity(0);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public static BlockWallDecor ribbon() {
        return new BlockWallDecor(
            Material.CLOTH,
            SoundType.CLOTH,
            new AxisAlignedBB(0.0D, 0.0D, 12.0D / 16.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 4.0D / 16.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 4.0D / 16.0D, 1.0D, 1.0D),
            new AxisAlignedBB(12.0D / 16.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            false
        );
    }

    public static BlockWallDecor spiceRack(boolean filled) {
        return new BlockDisplayWallDecor(
            Material.WOOD,
            SoundType.WOOD,
            new AxisAlignedBB(0.0D, 0.0D, 10.0D / 16.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 6.0D / 16.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 6.0D / 16.0D, 1.0D, 1.0D),
            new AxisAlignedBB(10.0D / 16.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            true,
            BlockDisplayWallDecor.DisplayKind.SPICE_RACK
        );
    }

    public static BlockWallDecor wreath() {
        BlockWallDecor block = new BlockDisplayWallDecor(
            Material.LEAVES,
            SoundType.PLANT,
            new AxisAlignedBB(0.0D, 0.0D, 12.0D / 16.0D, 1.0D, 1.0D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 4.0D / 16.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 4.0D / 16.0D, 1.0D, 1.0D),
            new AxisAlignedBB(12.0D / 16.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            false,
            BlockDisplayWallDecor.DisplayKind.WREATH
        );
        block.setHardness(2.5F);
        block.setResistance(6.0F);
        return block;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case SOUTH:
                return southBox;
            case EAST:
                return eastBox;
            case WEST:
                return westBox;
            case NORTH:
            default:
                return northBox;
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        if (!collidable) {
            return NULL_AABB;
        }
        return getBoundingBox(blockState, worldIn, pos);
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
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

}
