package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockMoldedWallpaper extends Block {
    public static final PropertyEnum<Half> HALF = PropertyEnum.create("half", Half.class);

    public BlockMoldedWallpaper() {
        super(Material.WOOD);
        setHardness(2.0F);
        setResistance(3.0F);
        setSoundType(SoundType.PLANT);
        setDefaultState(blockState.getBaseState().withProperty(HALF, Half.BOTTOM));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(HALF, meta == 1 ? Half.TOP : Half.BOTTOM);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == Half.TOP ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(HALF, hitY > 0.5F ? Half.TOP : Half.BOTTOM);
    }

    public enum Half implements IStringSerializable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        Half(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
