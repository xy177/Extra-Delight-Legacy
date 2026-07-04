package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSimpleTrapDoor extends BlockTrapDoor {
    public BlockSimpleTrapDoor(Material material, SoundType soundType) {
        super(material);
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
    }
}
