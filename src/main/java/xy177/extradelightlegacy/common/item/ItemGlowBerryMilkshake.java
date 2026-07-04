package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGlowBerryMilkshake extends ItemMilkshake {
    public ItemGlowBerryMilkshake(float healAmount) {
        super(healAmount);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));
        }
        return result;
    }
}
