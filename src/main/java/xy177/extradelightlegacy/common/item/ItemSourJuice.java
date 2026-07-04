package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public class ItemSourJuice extends ItemContainerFood {
    private static final ResourceLocation SOUR_PUCKER = new ResourceLocation(ExtraDelightLegacy.MODID, "sour_pucker");
    private final int amplifier;
    private final int duration;

    public ItemSourJuice(int healAmount, float saturation, Item containerItem, int amplifier, int duration) {
        super(healAmount, saturation, containerItem);
        this.amplifier = amplifier;
        this.duration = duration;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            entityLiving.heal(2.0F);
            Potion potion = ForgeRegistries.POTIONS.getValue(SOUR_PUCKER);
            if (potion != null) {
                entityLiving.addPotionEffect(new PotionEffect(potion, duration, amplifier));
            }
        }
        return result;
    }
}
