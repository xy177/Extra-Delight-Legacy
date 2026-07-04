package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonBowlFoodItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGlowBerryBowlFood extends AddonBowlFoodItem {
    public ItemGlowBerryBowlFood(int healAmount, float saturation, boolean alwaysEdible) {
        super(healAmount, saturation, alwaysEdible);
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
