package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemCoffeeDrink extends ItemContainerEffectFood {
    public ItemCoffeeDrink(int healAmount, float saturation, List<FoodEffectEntry> effects) {
        super(healAmount, saturation, Items.GLASS_BOTTLE, effects);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
}
