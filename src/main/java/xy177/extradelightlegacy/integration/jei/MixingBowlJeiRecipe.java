package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xy177.extradelightlegacy.common.crafting.MixingBowlFluidIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipe;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MixingBowlJeiRecipe implements IRecipeWrapper {
    private static final int FLUID_SLOT_X = 24;
    private static final int FLUID_SLOT_Y = 61;
    private static final int FLUID_SLOT_WIDTH = 16;
    private static final int FLUID_SLOT_HEIGHT = 12;
    private final MixingBowlRecipe recipe;
    private final List<ItemStack> inputs = new ArrayList<>();
    private final List<ItemStack> fluidDisplays = new ArrayList<>();
    private final List<MixingBowlFluidIngredient> logicalFluids = new ArrayList<>();
    private final List<FluidStack> fluids = new ArrayList<>();

    public MixingBowlJeiRecipe(MixingBowlRecipe recipe) {
        this.recipe = recipe;
        for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
            ItemStack display = ingredient.getDisplayStack();
            if (!display.isEmpty()) {
                inputs.add(display);
            }
        }
        for (MixingBowlFluidIngredient fluid : recipe.getFluids()) {
            logicalFluids.add(fluid);
            ItemStack display = fluid.getDisplayStack();
            if (!display.isEmpty()) {
                fluidDisplays.add(display);
            }
            FluidStack stack = toFluidStack(fluid);
            if (stack != null) {
                fluids.add(stack);
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> allInputs = new ArrayList<>(inputs);
        allInputs.addAll(fluidDisplays);
        ItemStack container = recipe.getContainer();
        if (!container.isEmpty()) {
            allInputs.add(container);
        }
        ingredients.setInputs(ItemStack.class, allInputs);
        ingredients.setOutput(ItemStack.class, recipe.getOutput());
        if (!fluids.isEmpty()) {
            ingredients.setInputs(FluidStack.class, fluids);
        }
    }

    public List<ItemStack> getInputs() {
        return Collections.unmodifiableList(inputs);
    }

    public List<ItemStack> getFluidDisplays() {
        return Collections.unmodifiableList(fluidDisplays);
    }

    public List<FluidStack> getFluids() {
        List<FluidStack> copies = new ArrayList<>();
        for (FluidStack fluid : fluids) {
            copies.add(fluid.copy());
        }
        return copies;
    }

    public ItemStack getContainer() {
        return recipe.getContainer();
    }

    public ItemStack getOutput() {
        return recipe.getOutput();
    }

    public ItemStack getUtensil() {
        if (recipe.getUtensilOre() == null || recipe.getUtensilOre().isEmpty()) {
            return ItemStack.EMPTY;
        }
        for (ItemStack stack : net.minecraftforge.oredict.OreDictionary.getOres(recipe.getUtensilOre(), false)) {
            if (!stack.isEmpty()) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public int getStirs() {
        return recipe.getStirs();
    }

    public List<MixingBowlFluidIngredient> getFluidIngredients() {
        return Collections.unmodifiableList(logicalFluids);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        for (int i = 0; i < logicalFluids.size(); i++) {
            MixingBowlFluidIngredient fluid = logicalFluids.get(i);
            if (hasForgeFluid(fluid)) {
                continue;
            }
            int x = FLUID_SLOT_X;
            int y = FLUID_SLOT_Y - i * 12;
            if (mouseX >= x && mouseY >= y && mouseX < x + FLUID_SLOT_WIDTH && mouseY < y + FLUID_SLOT_HEIGHT) {
                List<String> tooltip = new ArrayList<>();
                tooltip.add(getFluidName(fluid.getFluidId()));
                tooltip.add(" - " + fluid.getAmount() + "mB");
                return tooltip;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        for (int i = 0; i < logicalFluids.size(); i++) {
            MixingBowlFluidIngredient fluid = logicalFluids.get(i);
            if (!hasForgeFluid(fluid)) {
                drawLogicalFluid(minecraft, fluid.getFluidId(), FLUID_SLOT_X, FLUID_SLOT_Y - i * 12);
            }
        }
        minecraft.fontRenderer.drawString(String.valueOf(getStirs()), recipeWidth / 2 + 51, 20, 0xFFFFFF);
    }

    private static FluidStack toFluidStack(MixingBowlFluidIngredient ingredient) {
        return EDLFluids.stackForLogical(ingredient.getFluidId(), ingredient.getAmount());
    }

    private boolean hasForgeFluid(MixingBowlFluidIngredient ingredient) {
        for (FluidStack fluid : fluids) {
            FluidStack expected = toFluidStack(ingredient);
            if (fluid != null && expected != null && fluid.isFluidEqual(expected) && fluid.amount == ingredient.getAmount()) {
                return true;
            }
        }
        return false;
    }

    private static void drawLogicalFluid(Minecraft minecraft, String fluidId, int x, int y) {
        FluidRenderInfo info = getLogicalFluidRenderInfo(fluidId);
        TextureAtlasSprite sprite = minecraft.getTextureMapBlocks().getAtlasSprite(info.stillTexture.toString());
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

        GlStateManager.pushAttrib();
        minecraft.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(x, y + FLUID_SLOT_HEIGHT, 0).tex(sprite.getMinU(), sprite.getInterpolatedV(FLUID_SLOT_HEIGHT)).color(r, g, b, a).endVertex();
        buffer.pos(x + FLUID_SLOT_WIDTH, y + FLUID_SLOT_HEIGHT, 0).tex(sprite.getInterpolatedU(FLUID_SLOT_WIDTH), sprite.getInterpolatedV(FLUID_SLOT_HEIGHT)).color(r, g, b, a).endVertex();
        buffer.pos(x + FLUID_SLOT_WIDTH, y, 0).tex(sprite.getInterpolatedU(FLUID_SLOT_WIDTH), sprite.getMinV()).color(r, g, b, a).endVertex();
        buffer.pos(x, y, 0).tex(sprite.getMinU(), sprite.getMinV()).color(r, g, b, a).endVertex();
        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popAttrib();
    }

    private static FluidRenderInfo getLogicalFluidRenderInfo(String fluidId) {
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xff3f76e4);
        }
        if (MixingBowlRecipeManager.FLUID_MILK.equals(fluidId)) {
            FluidStack stack = EDLFluids.milkStack(1);
            if (stack != null && stack.getFluid() != null && stack.getFluid().getStill() != null) {
                return new FluidRenderInfo(stack.getFluid().getStill(), stack.getFluid().getColor());
            }
            return new FluidRenderInfo(new ResourceLocation("extradelightlegacy", "liquid/milk_still"), 0xfffff9df);
        }
        return new FluidRenderInfo(new ResourceLocation("minecraft", "blocks/water_still"), 0xffffffff);
    }

    private static String getFluidName(String fluidId) {
        if (MixingBowlRecipeManager.FLUID_MILK.equals(fluidId)) {
            FluidStack stack = EDLFluids.milkStack(1);
            return stack == null ? I18n.format("item.milk.name") : stack.getLocalizedName();
        }
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return I18n.format("tile.water.name");
        }
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (fluidId.equals(definition.getFluidId()) && definition.getFluid() != null) {
                FluidStack stack = definition.stack(1);
                return stack == null ? fluidId : stack.getLocalizedName();
            }
        }
        return fluidId;
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
