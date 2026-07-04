package xy177.extradelightlegacy.common.block;

import net.minecraft.util.BlockRenderLayer;

public class BlockTranslucentRecipeFeast extends BlockRecipeFeast {
    public BlockTranslucentRecipeFeast(boolean hasLeftovers) {
        super(hasLeftovers);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
