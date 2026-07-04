package xy177.extradelightlegacy.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.ContainerOven;
import xy177.extradelightlegacy.common.tile.TileEntityOven;

import java.util.ArrayList;
import java.util.List;

public class GuiOven extends GuiContainer {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ExtraDelightLegacy.MODID, "textures/gui/oven.png");
    private final ContainerOven container;

    public GuiOven(ContainerOven container) {
        super(container);
        this.container = container;
        this.xSize = 176;
        this.ySize = 182;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawHeatTooltip(mouseX, mouseY);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(I18n.format("extradelightlegacy.container.oven"), 28, 6, 4210752);
        fontRenderer.drawString(I18n.format("container.inventory"), 8, 90, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(BACKGROUND);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        drawHeat(x, y);
        drawProgress(x, y);
    }

    private void drawHeat(int x, int y) {
        TileEntityOven oven = container.getOven();
        if (oven != null && oven.isHeated()) {
            drawTexturedModalRect(x + 124, y + 60, 176, 0, 17, 15);
        }
    }

    private void drawProgress(int x, int y) {
        TileEntityOven oven = container.getOven();
        if (oven == null || oven.getCookTime() <= 0 || oven.getCookTimeTotal() <= 0) {
            return;
        }
        int progress = oven.getCookTime() * 24 / oven.getCookTimeTotal();
        drawTexturedModalRect(x + 89, y + 35, 176, 15, progress + 1, 24);
    }

    private void drawHeatTooltip(int mouseX, int mouseY) {
        int x = (width - xSize) / 2 + 124;
        int y = (height - ySize) / 2 + 60;
        if (mouseX < x || mouseY < y || mouseX >= x + 17 || mouseY >= y + 15) {
            return;
        }
        TileEntityOven oven = container.getOven();
        List<String> tooltip = new ArrayList<>();
        tooltip.add(I18n.format(oven != null && oven.isHeated() ? "container.cooking_pot.heated" : "container.cooking_pot.not_heated"));
        drawHoveringText(tooltip, mouseX, mouseY);
    }
}
