package xy177.extradelightlegacy.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import xy177.extradelightlegacy.common.block.IStyleableBlock;
import xy177.extradelightlegacy.client.render.TileEntityCandyBowlRenderer;
import xy177.extradelightlegacy.client.render.TileEntityChocolateBoxRenderer;
import xy177.extradelightlegacy.client.render.TileEntityDisplayCabinetRenderer;
import xy177.extradelightlegacy.client.render.TileEntityDryingRackRenderer;
import xy177.extradelightlegacy.client.render.TileEntityEvaporatorRenderer;
import xy177.extradelightlegacy.client.render.TileEntityFoodDisplayRenderer;
import xy177.extradelightlegacy.client.render.TileEntityFunnelRenderer;
import xy177.extradelightlegacy.client.render.TileEntityFruitBowlRenderer;
import xy177.extradelightlegacy.client.render.TileEntityKnifeBlockRenderer;
import xy177.extradelightlegacy.client.render.TileEntityMixingBowlRenderer;
import xy177.extradelightlegacy.client.render.TileEntityMortarRenderer;
import xy177.extradelightlegacy.client.render.TileEntityPicnicBasketRenderer;
import xy177.extradelightlegacy.client.render.TileEntityJuicerRenderer;
import xy177.extradelightlegacy.client.render.TileEntityJarRenderer;
import xy177.extradelightlegacy.client.render.TileEntityKegRenderer;
import xy177.extradelightlegacy.client.render.TileEntityPickleJarDisplayRenderer;
import xy177.extradelightlegacy.client.render.TileEntitySpiceRackDisplayRenderer;
import xy177.extradelightlegacy.client.render.TileEntityWreathDisplayRenderer;
import xy177.extradelightlegacy.common.block.BlockOrchardLeaves;
import xy177.extradelightlegacy.common.item.ItemDynamicJam;
import xy177.extradelightlegacy.common.item.ItemDynamicToast;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.registry.EDLItems;
import xy177.extradelightlegacy.common.tile.TileEntityCandyBowl;
import xy177.extradelightlegacy.common.tile.TileEntityChocolateBox;
import xy177.extradelightlegacy.common.tile.TileEntityCounterCabinet;
import xy177.extradelightlegacy.common.tile.TileEntityDryingRack;
import xy177.extradelightlegacy.common.tile.TileEntityEvaporator;
import xy177.extradelightlegacy.common.tile.TileEntityFoodDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityFunnel;
import xy177.extradelightlegacy.common.tile.TileEntityFruitBowl;
import xy177.extradelightlegacy.common.tile.TileEntityKnifeBlock;
import xy177.extradelightlegacy.common.tile.TileEntityMixingBowl;
import xy177.extradelightlegacy.common.tile.TileEntityMortar;
import xy177.extradelightlegacy.common.tile.TileEntityPicnicBasket;
import xy177.extradelightlegacy.common.tile.TileEntityJuicer;
import xy177.extradelightlegacy.common.tile.TileEntityJar;
import xy177.extradelightlegacy.common.tile.TileEntityKeg;
import xy177.extradelightlegacy.common.tile.TileEntityPickleJarDisplay;
import xy177.extradelightlegacy.common.tile.TileEntitySinkCabinet;
import xy177.extradelightlegacy.common.tile.TileEntitySpiceRackDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityWreathDisplay;

public final class EDLClientRegistry {
    private static final ResourceLocation MILK_STILL = new ResourceLocation("extradelightlegacy", "liquid/milk_still");
    private static final ResourceLocation MILK_FLOWING = new ResourceLocation("extradelightlegacy", "liquid/milk_flowing");

    private EDLClientRegistry() {
    }

    public static void preInit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDryingRack.class, new TileEntityDryingRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEvaporator.class, new TileEntityEvaporatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMortar.class, new TileEntityMortarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMixingBowl.class, new TileEntityMixingBowlRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJuicer.class, new TileEntityJuicerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFunnel.class, new TileEntityFunnelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKeg.class, new TileEntityKegRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJar.class, new TileEntityJarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPickleJarDisplay.class, new TileEntityPickleJarDisplayRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChocolateBox.class, new TileEntityChocolateBoxRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCandyBowl.class, new TileEntityCandyBowlRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFruitBowl.class, new TileEntityFruitBowlRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFoodDisplay.class, new TileEntityFoodDisplayRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKnifeBlock.class, new TileEntityKnifeBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpiceRackDisplay.class, new TileEntitySpiceRackDisplayRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWreathDisplay.class, new TileEntityWreathDisplayRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPicnicBasket.class, new TileEntityPicnicBasketRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCounterCabinet.class, new TileEntityDisplayCabinetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySinkCabinet.class, new TileEntityDisplayCabinetRenderer());
    }

    public static void registerModels() {
        for (Item item : EDLItems.getRegisteredItems()) {
            if (item instanceof ItemDynamicJam) {
                for (ItemDynamicJam.Flavor flavor : ItemDynamicJam.Flavor.values()) {
                    ModelLoader.setCustomModelResourceLocation(
                        item,
                        flavor.getMeta(),
                        new ModelResourceLocation(new ResourceLocation("extradelightlegacy", flavor.getModelName()), "inventory")
                    );
                }
            } else if (item instanceof ItemDynamicToast) {
                for (ItemDynamicToast.Topping topping : ItemDynamicToast.Topping.values()) {
                    ModelLoader.setCustomModelResourceLocation(
                        item,
                        topping.getMeta(),
                        new ModelResourceLocation(new ResourceLocation("extradelightlegacy", topping.getModelName()), "inventory")
                    );
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory")
                );
            }
        }

        for (Item item : EDLBlocks.getRegisteredItemBlocks()) {
            Block block = Block.getBlockFromItem(item);
            if (block instanceof IStyleableBlock && item.getHasSubtypes()) {
                int styles = ((IStyleableBlock) block).getStyleCount();
                for (int meta = 0; meta < styles; meta++) {
                    ModelLoader.setCustomModelResourceLocation(
                        item,
                        meta,
                        new ModelResourceLocation(item.getRegistryName(), "inventory")
                    );
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory")
                );
            }
        }

        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (definition.getBlock() != null) {
                ModelLoader.setCustomStateMapper(
                    definition.getBlock(),
                    new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build()
                );
            }
        }

        for (Item item : EDLFluids.getRegisteredBuckets()) {
            ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(item.getRegistryName(), "inventory")
            );
        }
    }

    public static void stitchTextures(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(MILK_STILL);
        event.getMap().registerSprite(MILK_FLOWING);
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (definition.getFluid() != null) {
                event.getMap().registerSprite(definition.getFluid().getStill());
                event.getMap().registerSprite(definition.getFluid().getFlowing());
            }
        }
    }

    public static void registerBlockColors(BlockColors blockColors) {
        for (Block block : EDLBlocks.getRegisteredBlocks()) {
            if (block instanceof BlockOrchardLeaves) {
                blockColors.registerBlockColorHandler(
                    (state, world, pos, tintIndex) -> tintIndex == 0 && world != null && pos != null
                        ? world.getBiome(pos).getFoliageColorAtPos(pos)
                        : 0x48B518,
                    block
                );
            }
        }
        registerWreathBlockColors(blockColors);
    }

    public static void registerItemColors(ItemColors itemColors) {
        for (Block block : EDLBlocks.getRegisteredBlocks()) {
            if (block instanceof BlockOrchardLeaves) {
                Item item = Item.getItemFromBlock(block);
                itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? 0x48B518 : 0xFFFFFF, item);
            }
        }
        registerWreathItemColors(itemColors);
    }

    private static void registerWreathBlockColors(BlockColors blockColors) {
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(0), 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(1), ColorizerFoliage.getFoliageColorPine());
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(2), ColorizerFoliage.getFoliageColorBirch());
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(3), 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(4), 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.WREATH_BLOCKS.get(5), 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.CINNAMON_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.LEMON_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.LIME_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.ORANGE_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.GRAPEFRUIT_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.HAZELNUT_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.APPLE_WREATH, 0x48B518);
        registerWreathColor(blockColors, EDLBlocks.CRIMSON_WREATH, 0xFFFFFF);
        registerWreathColor(blockColors, EDLBlocks.WARPED_WREATH, 0xFFFFFF);
        registerWreathColor(blockColors, EDLBlocks.CHERRY_WREATH, 0xFFFFFF);
    }

    private static void registerWreathColor(BlockColors blockColors, EDLBlocks.BlockDefinition definition, int fallbackColor) {
        if (definition.isRegistered()) {
            blockColors.registerBlockColorHandler(
                (state, world, pos, tintIndex) -> tintIndex == 0 ? fallbackColor : 0xFFFFFF,
                definition.getBlock()
            );
        }
    }

    private static void registerWreathItemColors(ItemColors itemColors) {
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(0), 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(1), ColorizerFoliage.getFoliageColorPine());
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(2), ColorizerFoliage.getFoliageColorBirch());
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(3), 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(4), 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.WREATH_BLOCKS.get(5), 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.CINNAMON_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.LEMON_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.LIME_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.ORANGE_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.GRAPEFRUIT_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.HAZELNUT_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.APPLE_WREATH, 0x48B518);
        registerWreathItemColor(itemColors, EDLBlocks.CRIMSON_WREATH, 0xFFFFFF);
        registerWreathItemColor(itemColors, EDLBlocks.WARPED_WREATH, 0xFFFFFF);
        registerWreathItemColor(itemColors, EDLBlocks.CHERRY_WREATH, 0xFFFFFF);
    }

    private static void registerWreathItemColor(ItemColors itemColors, EDLBlocks.BlockDefinition definition, int color) {
        if (definition.getItemBlock() != null) {
            itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? color : 0xFFFFFF, definition.getItemBlock());
        }
    }
}
