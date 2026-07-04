package xy177.extradelightlegacy.common.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public class PotionSunshine extends Potion {
    private static final ResourceLocation ICON = new ResourceLocation(
        ExtraDelightLegacy.MODID,
        "textures/mob_effect/sunshine.png"
    );

    public PotionSunshine() {
        super(false, 0xFFFF00);
        setBeneficial();
        setPotionName("effect.extradelightlegacy.sunshine");
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
        drawIcon(x + 6, y + 7, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
        drawIcon(x + 3, y + 3, alpha);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        PotionEffect blindness = entity.getActivePotionEffect(MobEffects.BLINDNESS);
        if (blindness == null) {
            return;
        }

        entity.removePotionEffect(MobEffects.BLINDNESS);
        int nextAmplifier = blindness.getAmplifier() - 1;
        if (nextAmplifier >= 0) {
            entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, blindness.getDuration(), nextAmplifier));
        }
    }

    @SideOnly(Side.CLIENT)
    private static void drawIcon(int x, int y, float alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ICON);
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 18, 18, 18.0F, 18.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
