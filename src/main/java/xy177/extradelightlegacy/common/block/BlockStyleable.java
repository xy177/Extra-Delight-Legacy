package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class BlockStyleable extends Block implements IStyleableBlock {
    public static final PropertyInteger STYLE = PropertyInteger.create("style", 0, 15);
    private final int styleCount;
    private final AxisAlignedBB box;

    public BlockStyleable(Material material, SoundType soundType, int styleCount) {
        this(material, soundType, styleCount, null);
    }

    public BlockStyleable(Material material, SoundType soundType, int styleCount, @Nullable AxisAlignedBB box) {
        super(material);
        this.styleCount = Math.max(1, Math.min(16, styleCount));
        this.box = box;
        setHardness(0.8F);
        setResistance(2.5F);
        setSoundType(soundType);
        setDefaultState(blockState.getBaseState().withProperty(STYLE, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return box == null ? super.getBoundingBox(state, source, pos) : box;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return box == null ? super.getCollisionBoundingBox(blockState, worldIn, pos) : box;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return box == null && super.isFullCube(state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return box == null && super.isOpaqueCube(state);
    }

    @Override
    public int getStyleCount() {
        return styleCount;
    }

    @Override
    public int getCurrentStyle(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    public IBlockState withStyle(IBlockState state, int style) {
        return state.withProperty(STYLE, Math.max(0, Math.min(styleCount - 1, style)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STYLE, Math.max(0, Math.min(styleCount - 1, meta & 15)));
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
