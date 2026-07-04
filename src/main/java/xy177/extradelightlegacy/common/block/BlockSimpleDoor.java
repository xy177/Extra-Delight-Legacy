package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSimpleDoor extends BlockDoor {
    public BlockSimpleDoor(Material material, SoundType soundType) {
        super(material);
        setSoundType(soundType);
        setHardness(1.5F);
        setResistance(5.0F);
    }
}
