package xy177.extradelightlegacy;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;
import xy177.extradelightlegacy.client.EDLClientRegistry;
import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.creative.EDLCreativeTabs;
import xy177.extradelightlegacy.common.event.EDLAdvancementEventHandler;
import xy177.extradelightlegacy.common.event.ButcherDropEventHandler;
import xy177.extradelightlegacy.common.event.EDLAdvancements;
import xy177.extradelightlegacy.common.event.EDLLootTableEventHandler;
import xy177.extradelightlegacy.common.event.PickledEffectEventHandler;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.registry.EDLBannerPatterns;
import xy177.extradelightlegacy.common.registry.EDLEnchantments;
import xy177.extradelightlegacy.common.registry.EDLEffects;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.registry.EDLItems;
import xy177.extradelightlegacy.common.registry.EDLOreDictionary;
import xy177.extradelightlegacy.common.registry.EDLRecipes;
import xy177.extradelightlegacy.common.tile.TileEntityChiller;
import xy177.extradelightlegacy.common.tile.TileEntityBubblePot;
import xy177.extradelightlegacy.common.tile.TileEntityCandyBowl;
import xy177.extradelightlegacy.common.tile.TileEntityChocolateBox;
import xy177.extradelightlegacy.common.tile.TileEntityCounterCabinet;
import xy177.extradelightlegacy.common.tile.TileEntityDryingRack;
import xy177.extradelightlegacy.common.tile.TileEntityEvaporator;
import xy177.extradelightlegacy.common.tile.TileEntityFoodDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityFruitBowl;
import xy177.extradelightlegacy.common.tile.TileEntityFunnel;
import xy177.extradelightlegacy.common.tile.TileEntityJar;
import xy177.extradelightlegacy.common.tile.TileEntityKnifeBlock;
import xy177.extradelightlegacy.common.tile.TileEntityKeg;
import xy177.extradelightlegacy.common.tile.TileEntitySpiceRackDisplay;
import xy177.extradelightlegacy.common.tile.TileEntitySinkCabinet;
import xy177.extradelightlegacy.common.tile.TileEntityTap;
import xy177.extradelightlegacy.common.tile.TileEntityMixingBowl;
import xy177.extradelightlegacy.common.tile.TileEntityMortar;
import xy177.extradelightlegacy.common.tile.TileEntityMeltingPot;
import xy177.extradelightlegacy.common.tile.TileEntityOven;
import xy177.extradelightlegacy.common.tile.TileEntityPanStyleable;
import xy177.extradelightlegacy.common.tile.TileEntityPicnicBasket;
import xy177.extradelightlegacy.common.tile.TileEntityJuicer;
import xy177.extradelightlegacy.common.tile.TileEntityVat;
import xy177.extradelightlegacy.common.tile.TileEntityVatLidStyleable;
import xy177.extradelightlegacy.common.tile.TileEntityFeastServings;
import xy177.extradelightlegacy.common.tile.TileEntityPickleJarDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityWreathDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityUnripeSalami;

@Mod(
    modid = ExtraDelightLegacy.MODID,
    name = ExtraDelightLegacy.NAME,
    version = ExtraDelightLegacy.VERSION,
    dependencies = "required-after:farmersdelight;after:brewinandchewinlegacy;after:crafttweaker;after:jei;after:suikecherry"
)
public class ExtraDelightLegacy {
    public static final String MODID = "extradelightlegacy";
    public static final String NAME = "ExtraDelight";
    public static final String VERSION = "1.1.0";

    @Mod.Instance(MODID)
    public static ExtraDelightLegacy instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        EDLConfig.load(event.getSuggestedConfigurationFile());
        EDLAdvancements.init();
        EDLBannerPatterns.register();
        EDLCreativeTabs.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new EDLGuiHandler());
        MinecraftForge.EVENT_BUS.register(new EDLAdvancementEventHandler());
        MinecraftForge.EVENT_BUS.register(new ButcherDropEventHandler());
        MinecraftForge.EVENT_BUS.register(new PickledEffectEventHandler());
        MinecraftForge.EVENT_BUS.register(new EDLLootTableEventHandler());
        registerCraftTweakerCompat();
        EDLFluids.registerFluids();
        GameRegistry.registerTileEntity(TileEntityDryingRack.class, MODID + ":drying_rack");
        GameRegistry.registerTileEntity(TileEntityMortar.class, MODID + ":mortar");
        GameRegistry.registerTileEntity(TileEntityMixingBowl.class, MODID + ":mixing_bowl");
        GameRegistry.registerTileEntity(TileEntityChiller.class, MODID + ":chiller");
        GameRegistry.registerTileEntity(TileEntityBubblePot.class, MODID + ":bubble_pot");
        GameRegistry.registerTileEntity(TileEntityMeltingPot.class, MODID + ":melting_pot");
        GameRegistry.registerTileEntity(TileEntityOven.class, MODID + ":oven");
        GameRegistry.registerTileEntity(TileEntityJuicer.class, MODID + ":juicer");
        GameRegistry.registerTileEntity(TileEntityFunnel.class, MODID + ":funnel");
        GameRegistry.registerTileEntity(TileEntityKeg.class, MODID + ":keg");
        GameRegistry.registerTileEntity(TileEntityTap.class, MODID + ":tap");
        GameRegistry.registerTileEntity(TileEntityJar.class, MODID + ":jar");
        GameRegistry.registerTileEntity(TileEntityVat.class, MODID + ":vat");
        GameRegistry.registerTileEntity(TileEntityEvaporator.class, MODID + ":evaporator");
        GameRegistry.registerTileEntity(TileEntityFeastServings.class, MODID + ":feast_servings");
        GameRegistry.registerTileEntity(TileEntityPickleJarDisplay.class, MODID + ":jar_display");
        GameRegistry.registerTileEntity(TileEntityChocolateBox.class, MODID + ":chocolate_box");
        GameRegistry.registerTileEntity(TileEntityCandyBowl.class, MODID + ":candy_bowl");
        GameRegistry.registerTileEntity(TileEntityFruitBowl.class, MODID + ":fruit_bowl");
        GameRegistry.registerTileEntity(TileEntityFoodDisplay.class, MODID + ":food_display");
        GameRegistry.registerTileEntity(TileEntityKnifeBlock.class, MODID + ":knife_block");
        GameRegistry.registerTileEntity(TileEntitySpiceRackDisplay.class, MODID + ":spice_rack_display");
        GameRegistry.registerTileEntity(TileEntityWreathDisplay.class, MODID + ":wreath_display");
        GameRegistry.registerTileEntity(TileEntityUnripeSalami.class, MODID + ":unripe_salami");
        GameRegistry.registerTileEntity(TileEntityPicnicBasket.class, MODID + ":picnic_basket");
        GameRegistry.registerTileEntity(TileEntityCounterCabinet.class, MODID + ":counter_cabinet");
        GameRegistry.registerTileEntity(TileEntitySinkCabinet.class, MODID + ":sink_cabinet");
        GameRegistry.registerTileEntity(TileEntityPanStyleable.class, MODID + ":styleable_pan");
        GameRegistry.registerTileEntity(TileEntityVatLidStyleable.class, MODID + ":styleable_vat_lid");
        if (event.getSide().isClient()) {
            EDLClientRegistry.preInit();
        }
    }

    private static void registerCraftTweakerCompat() {
        if (!Loader.isModLoaded("crafttweaker")) {
            return;
        }
        try {
            Object handler = Class.forName(
                "xy177.extradelightlegacy.common.compat.crafttweaker.CraftTweakerEventHandler"
            ).newInstance();
            MinecraftForge.EVENT_BUS.register(handler);
        } catch (ReflectiveOperationException exception) {
            logger.error("Failed to initialize CraftTweaker compatibility", exception);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EDLOreDictionary.register();
        EDLRecipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        EDLConfig.saveIfChanged();
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static final class RegistrationEvents {
        private RegistrationEvents() {
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            EDLFluids.registerBlocks(event.getRegistry());
            EDLBlocks.register(event.getRegistry());
            EDLConfig.saveIfChanged();
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            IForgeRegistry<Item> registry = event.getRegistry();
            EDLBlocks.registerItemBlocks(registry);
            EDLFluids.registerItems(registry);
            EDLItems.register(registry);
            EDLBlocks.registerCreativeStacks();
            EDLConfig.saveIfChanged();
        }

        @SubscribeEvent
        public static void registerPotions(RegistryEvent.Register<Potion> event) {
            EDLEffects.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
            EDLEnchantments.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            EDLRecipes.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            if (net.minecraftforge.fml.common.FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                EDLClientRegistry.registerModels();
            }
        }

        @SubscribeEvent
        public static void stitchTextures(TextureStitchEvent.Pre event) {
            if (net.minecraftforge.fml.common.FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                EDLClientRegistry.stitchTextures(event);
            }
        }
    }
}
