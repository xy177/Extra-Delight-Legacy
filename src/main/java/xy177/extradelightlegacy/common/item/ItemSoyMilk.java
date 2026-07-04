package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSoyMilk extends ItemContainerFood {
    public ItemSoyMilk() {
        super(1, 0.1F, Items.GLASS_BOTTLE);
        setAlwaysEdible();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            PotionEffect removable = null;
            for (PotionEffect effect : entityLiving.getActivePotionEffects()) {
                Potion potion = effect.getPotion();
                if (potion != null && potion.isBadEffect()) {
                    removable = effect;
                    break;
                }
            }
            if (removable != null) {
                entityLiving.removePotionEffect(removable.getPotion());
            }
        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, net.minecraft.client.util.ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("farmersdelight.tooltip.soy_milk"));
    }
}
