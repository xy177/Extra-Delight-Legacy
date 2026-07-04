package xy177.extradelightlegacy.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.Random;

public class BlockCornmealLayer extends BlockThinLayer {
    public BlockCornmealLayer() {
        super(Material.CLOTH, SoundType.CLOTH);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item item = EDLItems.CORN_MEAL.getItem();
        return item == null ? super.getItemDropped(state, rand, fortune) : item;
    }
}
