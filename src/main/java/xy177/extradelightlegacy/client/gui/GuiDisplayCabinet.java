package xy177.extradelightlegacy.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.ContainerDisplayCabinet;

import java.io.IOException;

public class GuiDisplayCabinet extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/display.png");
    private static final ResourceLocation SETTINGS = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/sprites/settings.png");
    private final ContainerDisplayCabinet container;
    private final boolean sinkLayout;
    private boolean storageTab = true;

    public GuiDisplayCabinet(ContainerDisplayCabinet inventorySlotsIn, String titleKey, boolean sinkLayout) {
        super(inventorySlotsIn);
        this.container = inventorySlotsIn;
        this.sinkLayout = sinkLayout;
        this.xSize = 176;
        this.ySize = 149;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BACKGROUND);

        drawTexturedModalRect(guiLeft, guiTop + 60, 0, 0, 175, 89);
        if (storageTab) {
            if (sinkLayout) {
                drawTexturedModalRect(guiLeft + 69, guiTop, 68, 126, 38, 65);
                drawTexturedModalRect(guiLeft + 3, guiTop, 0, 191, 68, 65);
                drawTexturedModalRect(guiLeft + 105, guiTop, 0, 191, 68, 65);
            } else {
                drawTexturedModalRect(guiLeft, guiTop, 0, 0, 175, 65);
            }
        } else {
            drawTexturedModalRect(guiLeft + 72, guiTop + 28, 0, 90, 32, 32);
        }

        mc.getTextureManager().bindTexture(SETTINGS);
        drawModalRectWithCustomSizedTexture(guiLeft - 24, guiTop, 0.0F, 0.0F, 24, 24, 24.0F, 24.0F);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0
            && mouseX >= guiLeft - 24
            && mouseX < guiLeft
            && mouseY >= guiTop
            && mouseY < guiTop + 24) {
            storageTab = !storageTab;
            container.switchTabs();
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            mc.playerController.sendEnchantPacket(container.windowId, 0);
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        storageTab = container.isStorageTab();
    }
}
