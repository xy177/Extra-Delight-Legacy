package xy177.extradelightlegacy.common.item;

import net.minecraft.item.Item;

public class ItemDamageableTool extends Item {
    public ItemDamageableTool(int durability) {
        setMaxStackSize(1);
        setMaxDamage(durability);
    }
}
