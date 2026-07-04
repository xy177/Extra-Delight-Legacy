package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class BlockSimplePillar extends BlockRotatedPillar {
    public BlockSimplePillar(Material material, SoundType soundType) {
        super(material);
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        int axisBits = meta & 12;
        if (axisBits == 4) {
            axis = EnumFacing.Axis.X;
        } else if (axisBits == 8) {
            axis = EnumFacing.Axis.Z;
        }
        return getDefaultState().withProperty(AXIS, axis);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing.Axis axis = state.getValue(AXIS);
        if (axis == EnumFacing.Axis.X) {
            return 4;
        }
        return axis == EnumFacing.Axis.Z ? 8 : 0;
    }
}
