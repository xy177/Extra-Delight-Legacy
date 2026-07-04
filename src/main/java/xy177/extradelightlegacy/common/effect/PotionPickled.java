package xy177.extradelightlegacy.common.effect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public class PotionPickled extends Potion {
    private static final ResourceLocation ICON = new ResourceLocation(
        ExtraDelightLegacy.MODID,
        "textures/mob_effect/pickled.png"
    );

    public PotionPickled() {
        super(false, 0xB3D05B);
        setBeneficial();
        setPotionName("effect.extradelightlegacy.pickled");
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

    @SideOnly(Side.CLIENT)
    private static void drawIcon(int x, int y, float alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ICON);
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 18, 18, 18.0F, 18.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
