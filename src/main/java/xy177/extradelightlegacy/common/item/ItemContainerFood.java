package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemContainerFood extends AddonFoodItem {
    private final Item containerItem;

    public ItemContainerFood(int healAmount, float saturation, Item containerItem) {
        super(healAmount, saturation, false);
        this.containerItem = containerItem;
        setContainerItem(containerItem);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (worldIn.isRemote || containerItem == null || entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
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
