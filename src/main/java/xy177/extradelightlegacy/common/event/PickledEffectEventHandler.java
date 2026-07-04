package xy177.extradelightlegacy.common.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.common.registry.EDLEffects;

public class PickledEffectEventHandler {
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() != DamageSource.MAGIC || EDLEffects.PICKLED == null) {
            return;
        }
        EntityLivingBase entity = event.getEntityLiving();
        PotionEffect poison = entity.getActivePotionEffect(MobEffects.POISON);
        PotionEffect pickled = entity.getActivePotionEffect(EDLEffects.PICKLED);
        if (poison == null || pickled == null) {
            return;
        }

        int chanceDivisor = 4;
        int numerator = Math.max(1, pickled.getAmplifier() + 1);
        if (entity.getRNG().nextInt(chanceDivisor) < numerator) {
            entity.removePotionEffect(MobEffects.POISON);
            event.setCanceled(true);
        }
    }
}
