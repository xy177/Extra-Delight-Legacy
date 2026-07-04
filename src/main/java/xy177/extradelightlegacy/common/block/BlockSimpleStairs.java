package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockSimpleStairs extends BlockStairs {
    public BlockSimpleStairs(IBlockState modelState, SoundType soundType) {
        super(modelState);
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
        useNeighborBrightness = true;
    }
}
