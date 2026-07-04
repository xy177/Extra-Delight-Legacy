package xy177.extradelightlegacy.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.tile.TileEntityMixingBowl;

public class TileEntityMixingBowlRenderer extends TileEntitySpecialRenderer<TileEntityMixingBowl> {
    private static final int SOURCE_TANK_CAPACITY = 6000;

    @Override
    public void render(TileEntityMixingBowl tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderIngredientCircle(tile, x, y, z);
        renderFluid(tile, x, y, z);
    }

    private void renderIngredientCircle(TileEntityMixingBowl tile, double x, double y, double z) {
        for (int i = 0; i < TileEntityMixingBowl.INGREDIENT_SLOTS; i++) {
            ItemStack stack = tile.getItems().get(i);
            if (stack.isEmpty()) {
                continue;
            }

            float rotY = ((i % 8) * 45.0F) + ((tile.getStirs() % 4) * 90.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 0.25D, z + 0.5D);
            GlStateManager.rotate(rotY, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.20D, 0.0D, 0.0D);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(35.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.65F, 0.65F, 0.65F);
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private void renderFluid(TileEntityMixingBowl tile, double x, double y, double z) {
        if (tile.getFluidAmount() <= 0 || tile.getFluidId().isEmpty()) {
            return;
        }

        FluidRenderInfo info = getFluidRenderInfo(tile.getFluidId());
        TextureAtlasSprite sprite = Minecraft.getMinecraft()
            .getTextureMapBlocks()
            .getAtlasSprite(info.stillTexture.toString());
        if (sprite == null || "missingno".equals(sprite.getIconName())) {
            return;
        }

        int color = info.color;
        int a = color >>> 24;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        if (a == 0) {
            a = 255;
        }

        double height = (double) tile.getFluidAmount() / (double) SOURCE_TANK_CAPACITY * 0.25D + 0.1D;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(0.18D, height, 0.82D).tex(sprite.getMinU(), sprite.getMaxV()).color(r, g, b, a).endVertex();
        buffer.pos(0.82D, height, 0.82D).tex(sprite.getMaxU(), sprite.getMaxV()).color(r, g, b, a).endVertex();
        buffer.pos(0.82D, height, 0.18D).tex(sprite.getMaxU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        buffer.pos(0.18D, height, 0.18D).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        tessellator.draw();

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private FluidRenderInfo getFluidRenderInfo(String fluidId) {
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xff3f76e4);
        }
        if (MixingBowlRecipeManager.FLUID_MILK.equals(fluidId)) {
            FluidStack stack = EDLFluids.milkStack(1);
            if (stack != null && stack.getFluid() != null && stack.getFluid().getStill() != null) {
                return new FluidRenderInfo(stack.getFluid().getStill(), stack.getFluid().getColor());
            }
            return new FluidRenderInfo(new ResourceLocation(ExtraDelightLegacy.MODID, "liquid/milk_still"), 0xfffff9df);
        }
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (fluidId.equals(definition.getFluidId()) && definition.getFluid() != null && definition.getFluid().getStill() != null) {
                return new FluidRenderInfo(definition.getFluid().getStill(), definition.getFluid().getColor());
            }
        }
        return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xffffffff);
    }

    private static final class FluidRenderInfo {
        private final ResourceLocation stillTexture;
        private final int color;

        private FluidRenderInfo(ResourceLocation stillTexture, int color) {
            this.stillTexture = stillTexture;
            this.color = color;
        }
    }
}
