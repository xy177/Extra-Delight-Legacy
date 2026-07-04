package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSimplePressurePlate extends BlockPressurePlate {
    public BlockSimplePressurePlate() {
        super(Material.WOOD, Sensitivity.EVERYTHING);
        setSoundType(SoundType.WOOD);
        setHardness(0.5F);
    }
}
