package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemContainerEffectFood extends AddonFoodItem {
    private final Item containerItem;

    public ItemContainerEffectFood(int healAmount, float saturation, Item containerItem, List<FoodEffectEntry> effects) {
        super(healAmount, saturation, false, effects);
        this.containerItem = containerItem;
        setContainerItem(containerItem);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (worldIn.isRemote || containerItem == null
            || entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
            return result;
        }

        ItemStack container = new ItemStack(containerItem);
        if (result.isEmpty()) {
            return container;
        }
        if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).inventory.addItemStackToInventory(container)) {
            ((EntityPlayer) entityLiving).dropItem(container, false);
        }
        return result;
    }
}
