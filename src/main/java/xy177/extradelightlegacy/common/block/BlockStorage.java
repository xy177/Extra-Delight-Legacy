package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public final class BlockStorage extends Block {
    public BlockStorage(Material material, SoundType soundType) {
        super(material);
        setHardness(0.8F);
        setResistance(2.5F);
        setSoundType(soundType);
    }
}
