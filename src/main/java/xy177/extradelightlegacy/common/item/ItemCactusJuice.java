package xy177.extradelightlegacy.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCactusJuice extends ItemContainerFood {
    public ItemCactusJuice(int healAmount, float saturation, Item containerItem) {
        super(healAmount, saturation, containerItem);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            entityLiving.heal(2.0F);
            if (entityLiving.isBurning()) {
                entityLiving.extinguish();
            }
            applyRandomSourceEffect(worldIn, entityLiving);
        }
        return result;
    }

    private void applyRandomSourceEffect(World world, EntityLivingBase entity) {
        switch (world.rand.nextInt(10)) {
            case 0:
                entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
            case 1:
                entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100));
            case 2:
                entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
            case 3:
                entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100));
            case 4:
                entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100));
            default:
                break;
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GREEN + I18n.translateToLocal("extradelightlegacy.cactus_juice.hovertext"));
    }
}
