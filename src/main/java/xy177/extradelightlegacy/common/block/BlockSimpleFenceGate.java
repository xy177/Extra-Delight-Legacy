package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;

public class BlockSimpleFenceGate extends BlockFenceGate {
    public BlockSimpleFenceGate(SoundType soundType) {
        super(BlockPlanks.EnumType.OAK);
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
    }
}
