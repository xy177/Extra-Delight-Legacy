package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGlowBerryJuice extends ItemContainerFood {
    public ItemGlowBerryJuice(int healAmount, float saturation, Item containerItem) {
        super(healAmount, saturation, containerItem);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            entityLiving.heal(2.0F);
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));
        }
        return result;
    }
}
