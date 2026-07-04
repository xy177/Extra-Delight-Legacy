package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemXocolatl extends ItemContainerEffectFood {
    public ItemXocolatl(int healAmount, float saturation, List<FoodEffectEntry> effects) {
        super(healAmount, saturation, Items.GLASS_BOTTLE, effects);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.BLUE + I18n.format("farmersdelight.tooltip.xocolati"));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            removeOneBadEffect(entityLiving);
        }
        return result;
    }

    private static void removeOneBadEffect(EntityLivingBase entity) {
        PotionEffect removable = null;
        for (PotionEffect effect : entity.getActivePotionEffects()) {
            Potion potion = effect.getPotion();
            if (potion != null && potion.isBadEffect()) {
                removable = effect;
                break;
            }
        }
        if (removable != null) {
            entity.removePotionEffect(removable.getPotion());
        }
    }
}
