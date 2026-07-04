package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSimpleFence extends BlockFence {
    public BlockSimpleFence(Material material, SoundType soundType) {
        super(material, material.getMaterialMapColor());
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
    }
}
