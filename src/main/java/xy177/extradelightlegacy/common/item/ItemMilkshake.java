package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMilkshake extends ItemContainerFood {
    private final float healAmount;

    public ItemMilkshake(float healAmount) {
        super(0, 0.0F, Items.GLASS_BOTTLE);
        this.healAmount = healAmount;
        setAlwaysEdible();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 64;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            entityLiving.heal(healAmount);
            if (entityLiving.isBurning()) {
                entityLiving.extinguish();
            }
        }
        return result;
    }
}
