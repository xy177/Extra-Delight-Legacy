package xy177.extradelightlegacy.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class BlockCutoutStyleable extends BlockStyleable {
    public BlockCutoutStyleable(Material material, SoundType soundType, int styleCount) {
        super(material, soundType, styleCount);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
