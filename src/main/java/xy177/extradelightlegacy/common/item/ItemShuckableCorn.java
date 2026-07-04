package xy177.extradelightlegacy.common.item;

import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.registry.EDLItems;

public class ItemShuckableCorn extends ItemShuckableOutput {
    public ItemShuckableCorn() {
        super(() -> new ItemStack[]{
            EDLItems.CORN_ON_COB.stack(1),
            EDLItems.CORN_HUSK.stack(2),
            EDLItems.CORN_SILK.stack(1)
        });
    }
}
