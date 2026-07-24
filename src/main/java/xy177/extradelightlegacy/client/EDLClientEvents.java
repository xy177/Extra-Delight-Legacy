package xy177.extradelightlegacy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLEffects;

@Mod.EventBusSubscriber(modid = ExtraDelightLegacy.MODID, value = Side.CLIENT)
public final class EDLClientEvents {
    private EDLClientEvents() {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        EDLClientRegistry.registerModels();
    }

    @SubscribeEvent
    public static void stitchTextures(TextureStitchEvent.Pre event) {
        EDLClientRegistry.stitchTextures(event);
    }

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        EDLClientRegistry.registerBlockColors(event.getBlockColors());
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        EDLClientRegistry.registerItemColors(event.getItemColors());
    }

    @SubscribeEvent
    public static void applySourPuckerFov(EntityViewRenderEvent.FOVModifier event) {
        if (EDLEffects.SOUR_PUCKER == null || Minecraft.getMinecraft().player == null
            || event.getEntity() != Minecraft.getMinecraft().player
            || !(event.getEntity() instanceof EntityLivingBase)) {
            return;
        }

        PotionEffect effect = ((EntityLivingBase) event.getEntity()).getActivePotionEffect(EDLEffects.SOUR_PUCKER);
        if (effect != null) {
            event.setFOV(event.getFOV() * Math.max(0.35F, 1.0F - 0.08F * (effect.getAmplifier() + 1)));
        }
    }
}
