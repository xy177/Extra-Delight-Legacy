package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemEffectFoodBlock extends ItemFoodBlock {
    private final List<FoodEffectEntry> effects = new ArrayList<>();
    private final String tooltipKey;

    public ItemEffectFoodBlock(Block block, int healAmount, float saturationModifier, FoodEffectEntry... effects) {
        this(block, healAmount, saturationModifier, null, effects);
    }

    public ItemEffectFoodBlock(Block block, int healAmount, float saturationModifier, @Nullable String tooltipKey,
                               FoodEffectEntry... effects) {
        super(block, healAmount, saturationModifier);
        this.tooltipKey = tooltipKey;
        for (FoodEffectEntry effect : effects) {
            this.effects.add(effect);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote && entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            for (FoodEffectEntry effect : effects) {
                if (worldIn.rand.nextFloat() <= effect.getChance()) {
                    net.minecraft.potion.Potion potion = net.minecraft.potion.Potion.REGISTRY.getObject(effect.getEffectId());
                    if (potion != null) {
                        player.addPotionEffect(new net.minecraft.potion.PotionEffect(
                            potion,
                            effect.getDuration(),
                            effect.getAmplifier()
                        ));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (tooltipKey != null && I18n.canTranslate(tooltipKey)) {
            tooltip.add(TextFormatting.BLUE + I18n.translateToLocal(tooltipKey));
        }
    }
}
