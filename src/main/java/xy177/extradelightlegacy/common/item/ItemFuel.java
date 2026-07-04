package xy177.extradelightlegacy.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFuel extends Item {
    private final int burnTime;

    public ItemFuel(int burnTime) {
        this.burnTime = burnTime;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return burnTime;
    }
}
