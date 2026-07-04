package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.block.SoundType;

public class BlockSimpleButton extends BlockButtonWood {
    public BlockSimpleButton() {
        setSoundType(SoundType.WOOD);
        setHardness(0.5F);
    }
}
