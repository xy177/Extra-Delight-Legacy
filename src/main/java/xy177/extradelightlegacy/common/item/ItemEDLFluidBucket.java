package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemEDLFluidBucket extends ItemBucket {
    public ItemEDLFluidBucket(Block containedBlock) {
        super(containedBlock);
        setMaxStackSize(1);
        setContainerItem(Items.BUCKET);
    }
}
