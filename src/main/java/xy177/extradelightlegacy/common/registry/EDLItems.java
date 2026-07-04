package xy177.extradelightlegacy.common.registry;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonBowlFoodItem;
import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.content.EDLContentEntry;
import xy177.extradelightlegacy.common.content.EDLContentRegistry;
import xy177.extradelightlegacy.common.creative.EDLCreativeTabs;
import xy177.extradelightlegacy.common.item.ItemDamageableTool;
import xy177.extradelightlegacy.common.item.ItemCactusJuice;
import xy177.extradelightlegacy.common.item.ItemCitrusBannerPattern;
import xy177.extradelightlegacy.common.item.ItemCoffeeDrink;
import xy177.extradelightlegacy.common.item.ItemContainerFood;
import xy177.extradelightlegacy.common.item.ItemContainerEffectFood;
import xy177.extradelightlegacy.common.item.ItemCornCobPipe;
import xy177.extradelightlegacy.common.item.ItemCraftingContainer;
import xy177.extradelightlegacy.common.item.ItemFuel;
import xy177.extradelightlegacy.common.item.ItemDynamicJam;
import xy177.extradelightlegacy.common.item.ItemDynamicToast;
import xy177.extradelightlegacy.common.item.ItemDeprecatedJam;
import xy177.extradelightlegacy.common.item.ItemEffectFoodBlock;
import xy177.extradelightlegacy.common.item.ItemFoodBlock;
import xy177.extradelightlegacy.common.item.ItemFrosting;
import xy177.extradelightlegacy.common.item.ItemGlowBerryBowlFood;
import xy177.extradelightlegacy.common.item.ItemGlowBerryJuice;
import xy177.extradelightlegacy.common.item.ItemGlowBerryMilkshake;
import xy177.extradelightlegacy.common.item.ItemGroundPlantableFood;
import xy177.extradelightlegacy.common.item.ItemMilkshake;
import xy177.extradelightlegacy.common.item.ItemOffsetSpatula;
import xy177.extradelightlegacy.common.item.ItemPlantableAddonFood;
import xy177.extradelightlegacy.common.item.ItemPlantableBlock;
import xy177.extradelightlegacy.common.item.ItemShuckableCorn;
import xy177.extradelightlegacy.common.item.ItemShuckableOutput;
import xy177.extradelightlegacy.common.item.ItemSoyMilk;
import xy177.extradelightlegacy.common.item.ItemSourJuice;
import xy177.extradelightlegacy.common.item.ItemTea;
import xy177.extradelightlegacy.common.item.ItemXocolatl;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class EDLItems {
    private static final String REQUIRES_MIXING_BOWL = "mixing_bowl";
    private static final String REQUIRES_OVEN = "oven";
    private static final String REQUIRES_DOUGH_SHAPING = "dough_shaping";
    private static final String REQUIRES_CHILLER = "chiller";
    private static final String REQUIRES_MELTING_POT = "melting_pot";
    private static final String REQUIRES_JUICER = "juicer";
    private static final String REQUIRES_VAT = "vat";
    private static final String REQUIRES_BAR_MOLD = "bar_mold";
    private static final String REQUIRES_SHEET = "sheet";
    private static final String REQUIRES_TRAY = "tray";
    private static final String REQUIRES_MUFFIN_TIN = "muffin_tin";
    private static final String REQUIRES_PIE_DISH = "pie_dish";
    private static final String REQUIRES_LOAF_PAN = "loaf_pan";
    private static final String REQUIRES_SQUARE_PAN = "square_pan";
    private static final String REQUIRES_PANFORTE = "panforte";
    private static final String REQUIRES_CARAMEL_SAUCE_FLUID = "caramel_sauce_fluid";
    private static final ResourceLocation INSTANT_DAMAGE = new ResourceLocation("minecraft", "instant_damage");
    private static final ResourceLocation REGENERATION = new ResourceLocation("minecraft", "regeneration");
    private static final ResourceLocation ABSORPTION = new ResourceLocation("minecraft", "absorption");
    private static final ResourceLocation FIRE_RESISTANCE = new ResourceLocation("minecraft", "fire_resistance");
    private static final ResourceLocation HUNGER = new ResourceLocation("minecraft", "hunger");
    private static final ResourceLocation HEALTH_BOOST = new ResourceLocation("minecraft", "health_boost");
    private static final ResourceLocation MOVEMENT_SPEED = new ResourceLocation("minecraft", "speed");
    private static final ResourceLocation JUMP_BOOST = new ResourceLocation("minecraft", "jump_boost");
    private static final ResourceLocation WATER_BREATHING = new ResourceLocation("minecraft", "water_breathing");
    private static final ResourceLocation RESISTANCE = new ResourceLocation("minecraft", "resistance");
    private static final ResourceLocation GLOWING = new ResourceLocation("minecraft", "glowing");
    private static final ResourceLocation SUNSHINE = new ResourceLocation(ExtraDelightLegacy.MODID, "sunshine");
    private static final ResourceLocation SOUR_PUCKER = new ResourceLocation(ExtraDelightLegacy.MODID, "sour_pucker");
    private static final ResourceLocation PICKLED = new ResourceLocation(ExtraDelightLegacy.MODID, "pickled");
    private static final ResourceLocation COMFORT = new ResourceLocation("farmersdelight", "comfort");
    private static final ResourceLocation NOURISHMENT = new ResourceLocation("farmersdelight", "nourishment");
    private static final int BRIEF_EFFECT_DURATION = 600;
    private static final int SHORT_EFFECT_DURATION = 1200;
    private static final int MEDIUM_EFFECT_DURATION = 3600;
    private static final int LONG_EFFECT_DURATION = 6000;

    private static final List<ItemDefinition> DEFINITIONS = new ArrayList<>();
    private static final List<Item> REGISTERED_ITEMS = new ArrayList<>();
    private static final Map<String, ItemDefinition> DEFINITIONS_BY_ID = new LinkedHashMap<>();

    public static final ItemDefinition CORN_SEEDS = seed(
        EDLModule.CROPS,
        "corn_seeds",
        "Corn",
        () -> EDLBlocks.CORN_CROP.getBlock(),
        "corn_crop"
    );
    public static final ItemDefinition UNSHUCKED_CORN = shuckableCorn(
        EDLModule.CROPS,
        "unshucked_corn",
        "Unshucked Corn",
        "corn_seeds"
    );
    public static final ItemDefinition CORN_ON_COB = item(
        EDLModule.CORE,
        "corn_on_cob",
        "Corn on the Cob",
        "unshucked_corn"
    );
    public static final ItemDefinition CORN_HUSK = item(
        EDLModule.CORE,
        "corn_husk",
        "Corn Husk",
        "unshucked_corn"
    );
    public static final ItemDefinition DRIED_CORN_HUSK = fuelItem(
        EDLModule.CORE,
        "dried_corn_husk",
        "Dried Corn Husk",
        100,
        "drying_rack",
        "corn_husk"
    );
    public static final ItemDefinition CORN_SILK = fuelItem(
        EDLModule.CORE,
        "corn_silk",
        "Corn Silk",
        100,
        "unshucked_corn"
    );
    public static final ItemDefinition CORN_COB = fuelItem(
        EDLModule.CORE,
        "corn_cob",
        "Corn Cob",
        200,
        "corn_on_cob"
    );
    public static final ItemDefinition CORN_COB_PIPE = define(new ItemDefinition(
        EDLModule.KITCHEN,
        "corn_cob_pipe",
        "Corn Cob Pipe",
        ItemCornCobPipe::new,
        "corn_cob"
    ));
    public static final ItemDefinition GRILLED_CORN_ON_COB = food(
        EDLModule.CORE,
        "grilled_corn_on_cob",
        "Grilled Corn on the Cob",
        3,
        0.6F,
        "corn_on_cob"
    );
    public static final ItemDefinition POPCORN = bowlFood(
        EDLModule.CORE,
        "popcorn",
        "Popcorn",
        2,
        0.6F,
        "corn_seeds"
    );
    public static final ItemDefinition ROASTED_PUMPKIN_SEEDS = alwaysEdibleFood(
        EDLModule.CORE,
        "roasted_pumpkin_seeds",
        "Roasted Pumpkin Seeds",
        1,
        0.1F
    );
    public static final ItemDefinition SUNFLOWER_SEEDS = alwaysEdibleFood(
        EDLModule.CORE,
        "sunflower_seeds",
        "Sunflower Seeds",
        1,
        0.1F
    );
    public static final ItemDefinition CHILI_SEEDS = seed(
        EDLModule.CROPS,
        "chili_seeds",
        "Chili Seeds",
        () -> EDLBlocks.CHILI_CROP.getBlock(),
        "chili_crop"
    );
    public static final ItemDefinition CHILI = effectFood(
        EDLModule.CROPS,
        "chili",
        "Chili",
        3,
        0.6F,
        FIRE_RESISTANCE,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "chili_seeds"
    );
    public static final ItemDefinition SLICED_CHILI = effectFood(
        EDLModule.CORE,
        "sliced_chili",
        "Sliced Chili",
        3,
        0.6F,
        FIRE_RESISTANCE,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "chili"
    );
    public static final ItemDefinition DRIED_CHILI = effectFood(
        EDLModule.CORE,
        "dried_chili",
        "Dried Chili",
        3,
        0.6F,
        FIRE_RESISTANCE,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "drying_rack",
        "chili"
    );
    public static final ItemDefinition GARLIC = food(
        EDLModule.CROPS,
        "garlic",
        "Garlic",
        1,
        0.3F,
        "garlic_crop"
    );
    public static final ItemDefinition GARLIC_CLOVE = plantableFood(
        EDLModule.CROPS,
        "garlic_clove",
        "Garlic Clove",
        1,
        0.3F,
        () -> EDLBlocks.GARLIC_CROP.getBlock(),
        "garlic"
    );
    public static final ItemDefinition GINGER = food(
        EDLModule.CROPS,
        "ginger",
        "Ginger",
        1,
        0.3F,
        "ginger_crop"
    );
    public static final ItemDefinition GINGER_CUTTING = plantableItem(
        EDLModule.CROPS,
        "ginger_cutting",
        "Ginger Cutting",
        () -> EDLBlocks.GINGER_CROP.getBlock(),
        ItemPlantableBlock.SoilMode.CROP,
        "ginger"
    );
    public static final ItemDefinition MINT = plantableItem(
        EDLModule.CROPS,
        "mint",
        "Mint",
        () -> EDLBlocks.MINT_CROP.getBlock(),
        ItemPlantableBlock.SoilMode.GROUND,
        "mint_crop"
    );
    public static final ItemDefinition PEANUTS_IN_SHELL = item(
        EDLModule.CROPS,
        "peanuts_in_shell",
        "Peanuts in Shell",
        "peanut_crop"
    );
    public static final ItemDefinition PEANUTS = plantableFood(
        EDLModule.CROPS,
        "peanuts",
        "Peanuts",
        2,
        0.1F,
        () -> EDLBlocks.PEANUT_CROP.getBlock(),
        "peanuts_in_shell"
    );
    public static final ItemDefinition ROASTED_PEANUTS = food(
        EDLModule.CORE,
        "roasted_peanuts",
        "Roasted Peanuts",
        6,
        0.1F,
        "peanuts"
    );
    public static final ItemDefinition COFFEE_CHERRIES = groundPlantableFood(
        EDLModule.CROPS,
        "coffee_cherries",
        "Coffee Cherries",
        2,
        0.1F,
        () -> EDLBlocks.COFFEE_BUSH.getBlock(),
        new FoodEffectEntry[]{new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "coffee_bush"
    );
    public static final ItemDefinition GREEN_COFFEE = effectFood(
        EDLModule.CORE,
        "green_coffee",
        "Green Coffee",
        2,
        0.1F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "coffee_cherries",
        "drying_rack"
    );
    public static final ItemDefinition COFFEE_BEANS = effectFood(
        EDLModule.CORE,
        "coffee_beans",
        "Coffee Beans",
        3,
        0.1F,
        MOVEMENT_SPEED,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "green_coffee"
    );
    public static final ItemDefinition GROUND_COFFEE = item(
        EDLModule.CORE,
        "ground_coffee",
        "Ground Coffee",
        "coffee_beans",
        "mortar_stone",
        "pestle_stone"
    );
    public static final ItemDefinition MALLOW_ROOT = plantableItem(
        EDLModule.CROPS,
        "mallow_root",
        "Mallow Root",
        () -> EDLBlocks.MALLOW_ROOT_CROP.getBlock(),
        ItemPlantableBlock.SoilMode.CROP,
        "mallow_root_crop"
    );
    public static final ItemDefinition CUCUMBER_SEED = seed(
        EDLModule.CROPS,
        "cucumber_seed",
        "Cucumber Seeds",
        () -> EDLBlocks.CUCUMBER_CROP.getBlock(),
        "cucumber_crop"
    );
    public static final ItemDefinition CUCUMBER = food(
        EDLModule.CROPS,
        "cucumber",
        "Cucumber",
        2,
        0.3F,
        "cucumber_seed"
    );
    public static final ItemDefinition SOYBEANS = plantableFood(
        EDLModule.CROPS,
        "soybeans",
        "Soybeans",
        1,
        0.1F,
        () -> EDLBlocks.SOYBEAN_CROP.getBlock(),
        "soybean_crop"
    );
    public static final ItemDefinition SOYBEAN_POD = shuckableOutput(
        EDLModule.CROPS,
        "soybean_pod",
        "Soybean Pod",
        () -> new ItemStack[]{SOYBEANS.stack(3)},
        "soybeans"
    );
    public static final ItemDefinition LEMON = food(
        EDLModule.ORCHARD,
        "lemon",
        "Lemon",
        2,
        0.2F,
        "lemon_sapling"
    );
    public static final ItemDefinition LIME = food(
        EDLModule.ORCHARD,
        "lime",
        "Lime",
        1,
        0.2F,
        "lime_sapling"
    );
    public static final ItemDefinition ORANGE = food(
        EDLModule.ORCHARD,
        "orange",
        "Orange",
        2,
        0.3F,
        "orange_sapling"
    );
    public static final ItemDefinition GRAPEFRUIT = food(
        EDLModule.ORCHARD,
        "grapefruit",
        "Grapefruit",
        3,
        0.3F,
        "grapefruit_sapling"
    );
    public static final ItemDefinition HAZELNUTS_IN_SHELL = item(
        EDLModule.ORCHARD,
        "hazelnuts_in_shell",
        "Hazelnuts in the Shell",
        "hazelnut_sapling"
    );
    public static final ItemDefinition HAZELNUTS = food(
        EDLModule.ORCHARD,
        "hazelnuts",
        "Hazelnuts",
        2,
        0.1F,
        "hazelnuts_in_shell"
    );
    public static final ItemDefinition CINNAMON_BARK = item(
        EDLModule.ORCHARD,
        "cinnamon_bark",
        "Cinnamon Bark",
        "cinnamon_sapling"
    );
    public static final ItemDefinition RAW_CINNAMON = item(
        EDLModule.CORE,
        "raw_cinnamon",
        "Raw Cinnamon",
        "cinnamon_bark"
    );
    public static final ItemDefinition CINNAMON_STICK = item(
        EDLModule.CORE,
        "cinnamon_stick",
        "Cinnamon Stick",
        "drying_rack",
        "raw_cinnamon"
    );
    public static final ItemDefinition GROUND_CINNAMON = item(
        EDLModule.CORE,
        "ground_cinnamon",
        "Ground Cinnamon",
        "mortar_stone",
        "pestle_stone",
        "cinnamon_stick"
    );

    public static final ItemDefinition ROASTED_CARROT = food(
        EDLModule.CORE,
        "roasted_carrot",
        "Roasted Carrot",
        7,
        0.6F
    );
    public static final ItemDefinition ROASTED_APPLE = food(
        EDLModule.CORE,
        "roasted_apple",
        "Roasted Apple",
        8,
        0.3F
    );

    public static final ItemDefinition WOODEN_SPOON = tool(
        EDLModule.KITCHEN,
        "wooden_spoon",
        "Wooden Spoon",
        59
    );
    public static final ItemDefinition STONE_SPOON = tool(
        EDLModule.KITCHEN,
        "stone_spoon",
        "Stone Spoon",
        131
    );
    public static final ItemDefinition IRON_SPOON = tool(
        EDLModule.KITCHEN,
        "iron_spoon",
        "Iron Spoon",
        250
    );
    public static final ItemDefinition GOLD_SPOON = tool(
        EDLModule.KITCHEN,
        "gold_spoon",
        "Gold Spoon",
        32
    );
    public static final ItemDefinition DIAMOND_SPOON = tool(
        EDLModule.KITCHEN,
        "diamond_spoon",
        "Diamond Spoon",
        1561
    );
    public static final ItemDefinition NETHERITE_SPOON = tool(
        EDLModule.KITCHEN,
        "netherite_spoon",
        "Netherite Spoon",
        2031
    );
    public static final ItemDefinition GRATER = tool(
        EDLModule.KITCHEN,
        "grater",
        "Box Grater",
        250
    );
    public static final ItemDefinition WHISK = tool(
        EDLModule.KITCHEN,
        "whisk",
        "Whisk",
        250
    );
    public static final ItemDefinition PESTLE_STONE = tool(
        EDLModule.KITCHEN,
        "pestle_stone",
        "Stone Pestle",
        150,
        "mortar_stone"
    );
    public static final ItemDefinition OFFSET_SPATULA_WOOD = offsetSpatula(EDLModule.KITCHEN, "offset_spatula_wood", "Wooden Offset Spatula", 59);
    public static final ItemDefinition OFFSET_SPATULA_IRON = offsetSpatula(EDLModule.KITCHEN, "offset_spatula_iron", "Iron Offset Spatula", 250);
    public static final ItemDefinition OFFSET_SPATULA_GOLD = offsetSpatula(EDLModule.KITCHEN, "offset_spatula_gold", "Golden Offset Spatula", 32);
    public static final ItemDefinition OFFSET_SPATULA_DIAMOND = offsetSpatula(EDLModule.KITCHEN, "offset_spatula_diamond", "Diamond Offset Spatula", 1561);
    public static final ItemDefinition OFFSET_SPATULA_NETHERITE = offsetSpatula(EDLModule.KITCHEN, "offset_spatula_netherite", "Netherite Offset Spatula", 2031);
    public static final ItemDefinition PESTLE_GRANITE = tool(EDLModule.KITCHEN, "pestle_granite", "Granite Pestle", 150, "mortar_stone");
    public static final ItemDefinition PESTLE_DIORITE = tool(EDLModule.KITCHEN, "pestle_diorite", "Diorite Pestle", 150, "mortar_stone");
    public static final ItemDefinition PESTLE_ANDESITE = tool(EDLModule.KITCHEN, "pestle_andesite", "Andesite Pestle", 150, "mortar_stone");
    public static final ItemDefinition PESTLE_ENDSTONE = tool(EDLModule.KITCHEN, "pestle_endstone", "End Stone Pestle", 150, "mortar_stone");
    public static final ItemDefinition PESTLE_DEEPSLATE = externalTool(EDLModule.KITCHEN, "pestle_deepslate", "Deepslate Pestle", 150, "deeperdepths:deepslate", "mortar_stone");
    public static final ItemDefinition PESTLE_BLACKSTONE = externalTool(EDLModule.KITCHEN, "pestle_blackstone", "Blackstone Pestle", 150, "nb:black_stone", "mortar_stone");
    public static final ItemDefinition PESTLE_BASALT = externalTool(EDLModule.KITCHEN, "pestle_basalt", "Basalt Pestle", 150, "nb:basalt", "mortar_stone");
    public static final ItemDefinition PESTLE_AMETHYST = externalTool(EDLModule.KITCHEN, "pestle_amethyst", "Amethyst Pestle", 150, "deeperdepths:amethyst_block", "mortar_stone");
    public static final ItemDefinition PESTLE_GILDED_BLACKSTONE = externalTool(EDLModule.KITCHEN, "pestle_gilded_blackstone", "Gilded Blackstone Pestle", 150, "nb:gilded_blackstone", "mortar_stone");

    public static final ItemDefinition PEELED_GINGER = food(
        EDLModule.CORE,
        "peeled_ginger",
        "Peeled Ginger",
        1,
        0.3F,
        "ginger",
        "wooden_spoon"
    );
    public static final ItemDefinition SLICED_GINGER = food(
        EDLModule.CORE,
        "sliced_ginger",
        "Sliced Ginger",
        1,
        0.3F,
        "peeled_ginger"
    );
    public static final ItemDefinition GRATED_GINGER = food(
        EDLModule.CORE,
        "grated_ginger",
        "Grated Ginger",
        1,
        0.3F,
        "peeled_ginger",
        "grater"
    );
    public static final ItemDefinition GRATED_GARLIC = food(
        EDLModule.CORE,
        "grated_garlic",
        "Grated Garlic",
        1,
        0.3F,
        "garlic_clove",
        "grater"
    );
    public static final ItemDefinition GRATED_POTATO = food(
        EDLModule.CORE,
        "grated_potato",
        "Grated Potato",
        1,
        0.3F,
        "grater"
    );
    public static final ItemDefinition SLICED_POTATO = food(
        EDLModule.CORE,
        "sliced_potato",
        "Sliced Potato",
        1,
        0.3F
    );
    public static final ItemDefinition POTATO_STICKS = food(
        EDLModule.CORE,
        "potato_sticks",
        "Potato Sticks",
        1,
        0.3F,
        "sliced_potato"
    );
    public static final ItemDefinition GRATED_CARROT = food(
        EDLModule.CORE,
        "grated_carrot",
        "Grated Carrot",
        3,
        0.6F,
        "grater"
    );
    public static final ItemDefinition SLICED_APPLE = food(
        EDLModule.CORE,
        "sliced_apple",
        "Apple Slices",
        4,
        0.3F
    );
    public static final ItemDefinition SLICED_ONION = food(
        EDLModule.CORE,
        "sliced_onion",
        "Sliced Onion",
        0,
        0.4F
    );
    public static final ItemDefinition SLICED_TOMATO = food(
        EDLModule.CORE,
        "sliced_tomato",
        "Sliced Tomato",
        0,
        0.3F
    );
    public static final ItemDefinition SLICED_BEETROOT_ITEM = food(
        EDLModule.CORE,
        "sliced_beetroot_item",
        "Sliced Beetroot",
        1,
        0.6F
    );
    public static final ItemDefinition MELON_CHUNKS = item(
        EDLModule.CORE,
        "melon_chunks",
        "Melon Chunks"
    );
    public static final ItemDefinition MELON_RIND = food(
        EDLModule.CORE,
        "melon_rind",
        "Melon Rind",
        1,
        0.2F
    );
    public static final ItemDefinition ICE_CUBES = alwaysEdibleEffectFood(
        EDLModule.CORE,
        "ice_cubes",
        "Ice Cubes",
        0,
        0.0F,
        FIRE_RESISTANCE,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F
    );
    public static final ItemDefinition COOKED_WHEAT_SEEDS = food(
        EDLModule.CORE,
        "cooked_wheat_seeds",
        "Roasted Wheat Berries",
        2,
        0.3F
    );
    public static final ItemDefinition SLICED_CUCUMBER_ITEM = food(
        EDLModule.CORE,
        "sliced_cucumber_item",
        "Sliced Cucumber",
        2,
        0.3F,
        "cucumber"
    );
    public static final ItemDefinition SLICED_LEMON = food(
        EDLModule.CORE,
        "sliced_lemon",
        "Sliced Lemon",
        2,
        0.2F,
        "lemon"
    );
    public static final ItemDefinition LEMON_ZEST = item(
        EDLModule.CORE,
        "lemon_zest",
        "Lemon Zest",
        "lemon",
        "grater"
    );
    public static final ItemDefinition SLICED_LIME = food(
        EDLModule.CORE,
        "sliced_lime",
        "Sliced Lime",
        1,
        0.2F,
        "lime"
    );
    public static final ItemDefinition LIME_ZEST = item(
        EDLModule.CORE,
        "lime_zest",
        "Lime Zest",
        "lime",
        "grater"
    );
    public static final ItemDefinition SLICED_ORANGE = food(
        EDLModule.CORE,
        "sliced_orange",
        "Sliced Orange",
        2,
        0.3F,
        "orange"
    );
    public static final ItemDefinition ORANGE_ZEST = item(
        EDLModule.CORE,
        "orange_zest",
        "Orange Zest",
        "orange",
        "grater"
    );
    public static final ItemDefinition SLICED_GRAPEFRUIT = food(
        EDLModule.CORE,
        "sliced_grapefruit",
        "Sliced Grapefruit",
        3,
        0.3F,
        "grapefruit"
    );
    public static final ItemDefinition CITRUS_RIND_BANNER_ITEM = define(new ItemDefinition(
        EDLModule.HOME_DECOR,
        "citrus_rind_banner_item",
        "Banner Pattern",
        ItemCitrusBannerPattern::new
    ));
    public static final ItemDefinition ROASTED_HAZELNUTS = food(
        EDLModule.CORE,
        "roasted_hazelnuts",
        "Roasted Hazelnuts",
        3,
        0.1F,
        "hazelnuts"
    );
    public static final ItemDefinition CACTUS = effectFood(
        EDLModule.CORE,
        "cactus",
        "Cactus Paddle",
        2,
        0.3F,
        INSTANT_DAMAGE,
        1,
        0,
        1.0F
    );
    public static final ItemDefinition COOKED_CACTUS = food(
        EDLModule.CORE,
        "cooked_cactus",
        "Cooked Cactus Paddle",
        6,
        0.3F,
        "cactus"
    );
    public static final ItemDefinition DRIED_FRUIT = food(
        EDLModule.CORE,
        "dried_fruit",
        "Dried Fruit",
        4,
        0.3F,
        "drying_rack"
    );
    public static final ItemDefinition FISH_FLAKES = item(
        EDLModule.CORE,
        "fish_flakes",
        "Fish Flakes",
        "drying_rack"
    );
    public static final ItemDefinition CRISP_RICE = food(
        EDLModule.CORE,
        "crisp_rice",
        "Puffed Rice",
        3,
        0.6F
    );
    public static final ItemDefinition SOAKED_SOYBEANS = bowlFood(
        EDLModule.PICKLING,
        "soaked_soybeans_item",
        "Soaked Soybeans",
        2,
        0.3F,
        REQUIRES_VAT,
        "soybeans"
    );
    public static final ItemDefinition COOKED_SOYBEANS = bowlFood(
        EDLModule.CORE,
        "cooked_soybeans_item",
        "Cooked Soybeans",
        4,
        0.3F,
        "soaked_soybeans_item"
    );
    public static final ItemDefinition MASHED_SOYBEANS = bowlFood(
        EDLModule.CORE,
        "mashed_soybeans_item",
        "Mashed Soybeans",
        4,
        0.3F,
        "mortar_stone",
        "soaked_soybeans_item"
    );
    public static final ItemDefinition SOY_MILK = define(new ItemDefinition(
        EDLModule.CORE,
        "soy_milk",
        "Soy Milk",
        ItemSoyMilk::new,
        "mashed_soybeans_item"
    ));
    public static final ItemDefinition NATTO = bowlFood(
        EDLModule.PICKLING,
        "natto_item",
        "Natto",
        4,
        0.3F,
        REQUIRES_VAT,
        "cooked_soybeans_item",
        "yeast"
    );
    public static final ItemDefinition MISO_PASTE = containerItem(
        EDLModule.PICKLING,
        "miso_paste_item",
        "Miso Paste",
        Items.GLASS_BOTTLE,
        REQUIRES_VAT,
        "cooked_soybeans_item",
        "yeast",
        "salt"
    );
    public static final ItemDefinition SOY_SAUCE = containerItem(
        EDLModule.PICKLING,
        "soy_sauce_item",
        "Soy Sauce",
        Items.GLASS_BOTTLE,
        REQUIRES_VAT,
        "cooked_soybeans_item",
        "yeast",
        "salt"
    );
    public static final ItemDefinition HOT_SAUCE = containerItem(
        EDLModule.PICKLING,
        "hot_sauce_item",
        "Hot Sauce",
        Items.GLASS_BOTTLE,
        REQUIRES_VAT,
        "vinegar",
        "salt",
        "sliced_chili",
        "grated_garlic",
        "sliced_onion"
    );
    public static final ItemDefinition SHREDDED_CABBAGE = food(
        EDLModule.CORE,
        "shredded_cabbage_item",
        "Shredded Cabbage",
        2,
        0.4F
    );
    public static final ItemDefinition SAUERKRAUT = effectBowlFood(
        EDLModule.PICKLING,
        "sauerkraut_item",
        "Sauerkraut",
        4,
        0.4F,
        new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 0, 1.0F),
        REQUIRES_VAT,
        "shredded_cabbage_item",
        "salt"
    );
    public static final ItemDefinition COOKED_CORN = bowlFood(
        EDLModule.CORE,
        "cooked_corn",
        "Cooked Corn",
        2,
        0.6F,
        "corn_seeds"
    );
    public static final ItemDefinition HASHBROWNS = food(
        EDLModule.CORE,
        "hashbrowns",
        "Hashbrowns",
        6,
        0.6F,
        "grated_potato"
    );
    public static final ItemDefinition BREAD_SLICE = food(
        EDLModule.CORE,
        "bread_slice",
        "Bread Slice",
        1,
        0.6F
    );
    public static final ItemDefinition TOAST = food(
        EDLModule.CORE,
        "toast",
        "Toast",
        1,
        0.6F,
        "bread_slice"
    );
    public static final ItemDefinition CROUTONS = food(
        EDLModule.CORE,
        "croutons",
        "Croutons",
        1,
        0.6F,
        "bread_slice"
    );
    public static final ItemDefinition CRISP_RICE_CEREAL = cerealFood(
        EDLModule.MEALS,
        "crisp_rice_cereal",
        "Bowl of Puffed Rice",
        "crisp_rice"
    );
    public static final ItemDefinition COOKED_PASTA = bowlFood(
        EDLModule.MEALS,
        "cooked_pasta",
        "Cooked Pasta",
        6,
        0.3F
    );
    public static final ItemDefinition MACARONI = item(
        EDLModule.CORE,
        "macaroni",
        "Macaroni",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition PENNE = item(
        EDLModule.CORE,
        "penne",
        "Penne",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition APPLE_SAUCE = bowlFood(
        EDLModule.MEALS,
        "apple_sauce",
        "Apple Sauce",
        4,
        0.3F
    );
    public static final ItemDefinition CONGEE = bowlFood(
        EDLModule.MEALS,
        "congee",
        "Congee",
        6,
        0.4F,
        "sliced_ginger"
    );
    public static final ItemDefinition BOILED_EGG = food(
        EDLModule.CORE,
        "boiled_egg",
        "Hard Boiled Egg",
        5,
        0.6F
    );
    public static final ItemDefinition EGG_MIX = containerItem(
        EDLModule.CORE,
        "egg_mix",
        "Egg Mix",
        Items.BOWL
    );
    public static final ItemDefinition EGG_YOLK = item(
        EDLModule.CORE,
        "egg_yolk",
        "Egg Yolk"
    );
    public static final ItemDefinition EGG_WHITE = containerItem(
        EDLModule.CORE,
        "egg_white",
        "Egg White",
        Items.BOWL
    );
    public static final ItemDefinition SCRAMBLED_EGGS = bowlFood(
        EDLModule.CORE,
        "scrambled_eggs",
        "Scrambled Eggs",
        4,
        0.4F,
        "egg_mix"
    );
    public static final ItemDefinition OMELETTE_MIX = containerItem(
        EDLModule.CORE,
        "omelette_mix",
        "Omelette Mix",
        Items.BOWL,
        "egg_mix"
    );
    public static final ItemDefinition OMELETTE = food(
        EDLModule.MEALS,
        "omelette",
        "Omelette",
        12,
        0.8F,
        "omelette_mix"
    );
    public static final ItemDefinition BUTTER = food(
        EDLModule.CORE,
        "butter",
        "Butter",
        2,
        0.6F,
        REQUIRES_MIXING_BOWL,
        "whisk"
    );
    public static final ItemDefinition CHEESE = food(
        EDLModule.CORE,
        "cheese",
        "Cheese",
        4,
        0.6F,
        "vinegar"
    );
    public static final ItemDefinition GRAVY = containerEffectFood(
        EDLModule.CORE,
        "gravy_boat_item",
        "Gravy",
        2,
        0.7F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F)}
    );
    public static final ItemDefinition VINEGAR = containerItem(
        EDLModule.CORE,
        "vinegar",
        "Vinegar",
        Items.GLASS_BOTTLE
    );
    public static final ItemDefinition SALT = item(
        EDLModule.PICKLING,
        "salt",
        "Salt"
    );
    public static final ItemDefinition YEAST = containerItem(
        EDLModule.CORE,
        "yeast",
        "Yeast",
        Items.GLASS_BOTTLE
    );
    public static final ItemDefinition YEAST_SPREAD = containerEffectFood(
        EDLModule.CORE,
        "yeast_spread",
        "Yeast Spread",
        2,
        0.1F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{
            new FoodEffectEntry(HUNGER, 600, 0, 0.05F),
            new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "yeast"
    );
    public static final ItemDefinition COOKING_OIL = containerItem(
        EDLModule.CORE,
        "cooking_oil",
        "Cooking Oil",
        Items.GLASS_BOTTLE,
        "oil_fluid"
    );
    public static final ItemDefinition BREADING_MISANPLAS = food(
        EDLModule.CORE,
        "breading_misanplas",
        "Breading Mise en place",
        7,
        0.6F,
        "egg_mix",
        "breadcrumbs",
        "cooking_oil",
        "flour"
    );
    public static final ItemDefinition ROASTED_GARLIC = food(
        EDLModule.CORE,
        "roasted_garlic",
        "Roasted Garlic",
        2,
        0.3F,
        "garlic_clove"
    );
    public static final ItemDefinition POTATO_CHIPS = food(
        EDLModule.CORE,
        "potato_chips",
        "Potato Chips",
        6,
        0.6F,
        "sliced_potato",
        "cooking_oil"
    );
    public static final ItemDefinition FRENCH_FRIES = food(
        EDLModule.CORE,
        "french_fries",
        "French Fries",
        6,
        0.6F,
        "potato_sticks",
        "cooking_oil"
    );
    public static final ItemDefinition BUTTERED_TOAST = food(
        EDLModule.CORE,
        "buttered_toast",
        "Buttered Toast",
        3,
        0.6F,
        "toast",
        "butter"
    );
    public static final ItemDefinition GARLIC_BREAD = food(
        EDLModule.CORE,
        "garlic_bread",
        "Garlic Bread",
        4,
        0.6F,
        "bread_slice",
        "roasted_garlic"
    );
    public static final ItemDefinition CHEESY_GARLIC_BREAD = food(
        EDLModule.CORE,
        "cheesy_garlic_bread",
        "Cheesy Garlic Bread",
        4,
        0.6F,
        "garlic_bread",
        "cheese"
    );
    public static final ItemDefinition FISH_CAKES = food(
        EDLModule.MEALS,
        "fish_cakes",
        "Fish Cakes",
        9,
        0.6F,
        "breading_misanplas",
        "grated_potato",
        "sliced_onion"
    );
    public static final ItemDefinition FRIED_MUSHROOMS = food(
        EDLModule.MEALS,
        "fried_mushrooms",
        "Fried Mushrooms",
        5,
        0.6F,
        "breading_misanplas"
    );
    public static final ItemDefinition EGG_IN_THE_BASKET = food(
        EDLModule.CORE,
        "egg_in_the_basket",
        "Egg in a Basket",
        5,
        0.6F,
        "bread_slice",
        REQUIRES_OVEN,
        REQUIRES_SHEET
    );
    public static final ItemDefinition FRENCH_TOAST = effectBowlFood(
        EDLModule.MEALS,
        "french_toast",
        "French Toast",
        2,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "egg_mix",
        "bread_slice",
        REQUIRES_OVEN,
        REQUIRES_TRAY
    );
    public static final ItemDefinition CHEESE_SANDWICH = food(
        EDLModule.MEALS,
        "cheese_sandwich",
        "Cheese Sandwich",
        8,
        0.6F,
        "bread_slice",
        "cheese"
    );
    public static final ItemDefinition GRILLED_CHEESE = food(
        EDLModule.MEALS,
        "grilled_cheese",
        "Grilled Cheese",
        12,
        0.6F,
        "cheese_sandwich"
    );
    public static final ItemDefinition BUTTERED_PASTA = bowlFood(
        EDLModule.MEALS,
        "buttered_pasta",
        "Buttered Pasta",
        8,
        0.6F,
        "cooked_pasta",
        "butter",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition POTATO_SALAD = effectBowlFood(
        EDLModule.MEALS,
        "potato_salad",
        "Potato Salad",
        3,
        0.4F,
        new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "sliced_onion",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition CREAM_CORN = bowlFood(
        EDLModule.MEALS,
        "cream_corn",
        "Creamed Corn",
        6,
        0.6F,
        "cooked_corn",
        "butter"
    );
    public static final ItemDefinition CORN_FRITTERS = effectFood(
        EDLModule.MEALS,
        "corn_fritters",
        "Corn Fritters",
        10,
        0.6F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "cooked_corn",
        "egg_mix",
        "cooking_oil",
        "sliced_onion"
    );
    public static final ItemDefinition STEWED_APPLES = effectBowlFood(
        EDLModule.MEALS,
        "stewed_apples",
        "Stewed Apples",
        6,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "sliced_apple",
        "butter"
    );
    public static final ItemDefinition APPLE_FRITTERS = effectBowlFood(
        EDLModule.MEALS,
        "apple_fritters",
        "Apple Fritters",
        10,
        0.4F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "sliced_apple",
        "egg_mix",
        "cooking_oil"
    );
    public static final ItemDefinition AGLIO_E_OLIO = effectBowlFood(
        EDLModule.MEALS,
        "aglio_e_olio",
        "Spaghetti Aglio e Olio",
        11,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "cooked_pasta",
        "roasted_garlic",
        "sliced_chili",
        "cheese"
    );
    public static final ItemDefinition PENNE_ALL_ARRABBIATA = effectBowlFood(
        EDLModule.MEALS,
        "penne_all_arrabbiata",
        "Penne all'Arrabiatta",
        8,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "penne",
        "sliced_tomato",
        "sliced_chili"
    );
    public static final ItemDefinition JALAPENO_POPPER = effectFood(
        EDLModule.MEALS,
        "jalapeno_popper",
        "Jalapeno Popper",
        6,
        0.6F,
        FIRE_RESISTANCE,
        SHORT_EFFECT_DURATION,
        0,
        1.0F,
        "sliced_chili",
        "cheese",
        REQUIRES_OVEN,
        REQUIRES_TRAY
    );
    public static final ItemDefinition CHILI_CHEESE_CORNBREAD_MUFFIN = effectFood(
        EDLModule.MEALS,
        "chili_cheese_cornbread_muffin",
        "Chili and Cheese Cornbread Muffin",
        3,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(FIRE_RESISTANCE, SHORT_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "sliced_chili",
        "cheese",
        "corn_meal",
        REQUIRES_OVEN,
        REQUIRES_MUFFIN_TIN
    );
    public static final ItemDefinition POTATO_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "potato_soup",
        "Potato Soup",
        7,
        0.7F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F)
    );
    public static final ItemDefinition TOMATO_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "tomato_soup",
        "Tomato Soup",
        7,
        0.7F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F)
    );
    public static final ItemDefinition CARROT_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "carrot_soup",
        "Carrot Soup",
        9,
        0.7F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F)
    );
    public static final ItemDefinition CACTUS_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "cactus_soup",
        "Cactus Soup",
        11,
        0.7F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "cooked_cactus",
        "sliced_tomato",
        "sliced_onion"
    );
    public static final ItemDefinition LUGAW = effectBowlFood(
        EDLModule.MEALS,
        "lugaw",
        "Lugaw",
        13,
        0.7F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "boiled_egg",
        "sliced_ginger",
        "sliced_onion"
    );
    public static final ItemDefinition CORN_CHOWDER = effectBowlFood(
        EDLModule.MEALS,
        "corn_chowder",
        "Corn Chowder",
        11,
        0.8F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "cooked_corn",
        "sliced_potato",
        "sliced_onion"
    );
    public static final ItemDefinition PAMONHA = effectFood(
        EDLModule.MEALS,
        "pamonha",
        "Pamonha",
        3,
        0.6F,
        NOURISHMENT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "corn_seeds",
        "corn_husk"
    );
    public static final ItemDefinition BEEF_SCRAPS = butcheryFood("beef_scraps", "Raw Beef Scraps", 1, 0.1F);
    public static final ItemDefinition COOKED_BEEF_SCRAPS = butcheryFood("cooked_beef_scraps", "Cooked Beef Scraps", 2, 0.2F);
    public static final ItemDefinition GROUND_BEEF = butcheryFood("ground_beef", "Raw Ground Beef", 1, 0.1F);
    public static final ItemDefinition COOKED_GROUND_BEEF = butcheryFood("cooked_ground_beef", "Cooked Ground Beef", 4, 0.4F);
    public static final ItemDefinition CUBED_BEEF = butcheryFood("cubed_beef", "Raw Cubed Beef", 2, 0.2F);
    public static final ItemDefinition COOKED_CUBED_BEEF = butcheryFood("cooked_cubed_beef", "Cooked Cubed Beef", 4, 0.4F);
    public static final ItemDefinition BEEF_RIBS = butcheryFood("beef_ribs", "Raw Beef Ribs", 6, 0.6F);
    public static final ItemDefinition COOKED_BEEF_RIBS = butcheryFood("cooked_beef_ribs", "Cooked Beef Ribs", 12, 1.2F);
    public static final ItemDefinition BEEF_ROAST = butcheryFood("beef_roast", "Raw Beef Roast", 6, 0.6F);
    public static final ItemDefinition COOKED_BEEF_ROAST = butcheryFood("cooked_beef_roast", "Cooked Beef Roast", 12, 1.2F);
    public static final ItemDefinition BEEF_STEWMEAT = butcheryFood("beef_stewmeat", "Raw Beef Stew Meat", 1, 0.1F);
    public static final ItemDefinition COOKED_BEEF_STEWMEAT = butcheryFood("cooked_beef_stewmeat", "Cooked Beef Stew Meat", 3, 0.3F);
    public static final ItemDefinition OXTAIL = butcheryFood("oxtail", "Raw Oxtail", 2, 0.2F);
    public static final ItemDefinition COOKED_OXTAIL = butcheryFood("cooked_oxtail", "Cooked Oxtail", 4, 0.4F);
    public static final ItemDefinition TONGUE = butcheryFood("tongue", "Raw Tongue", 2, 0.1F);
    public static final ItemDefinition COOKED_TONGUE = butcheryFood("cooked_tongue", "Cooked Tongue", 4, 0.2F);
    public static final ItemDefinition PORK_SCRAPS = butcheryFood("pork_scraps", "Raw Pork Scraps", 1, 0.2F);
    public static final ItemDefinition COOKED_PORK_SCRAPS = butcheryFood("cooked_pork_scraps", "Cooked Pork Scraps", 2, 0.2F);
    public static final ItemDefinition GROUND_PORK = butcheryFood("ground_pork", "Raw Ground Pork", 1, 0.2F);
    public static final ItemDefinition COOKED_GROUND_PORK = butcheryFood("cooked_ground_pork", "Cooked Ground Pork", 4, 0.2F);
    public static final ItemDefinition CUBED_PORK = butcheryFood("cubed_pork", "Raw Cubed Pork", 2, 0.2F);
    public static final ItemDefinition COOKED_CUBED_PORK = butcheryFood("cooked_cubed_pork", "Cooked Cubed Pork", 4, 0.2F);
    public static final ItemDefinition PORK_RIBS = butcheryFood("pork_ribs", "Raw Pork Ribs", 6, 0.6F);
    public static final ItemDefinition COOKED_PORK_RIBS = butcheryFood("cooked_pork_ribs", "Cooked Pork Ribs", 12, 1.2F);
    public static final ItemDefinition PORK_ROAST = butcheryFood("pork_roast", "Raw Pork Roast", 6, 0.6F);
    public static final ItemDefinition COOKED_PORK_ROAST = butcheryFood("cooked_pork_roast", "Cooked Pork Roast", 12, 1.2F);
    public static final ItemDefinition PORK_STEWMEAT = butcheryFood("pork_stewmeat", "Raw Pork Stew Meat", 1, 0.1F);
    public static final ItemDefinition COOKED_PORK_STEWMEAT = butcheryFood("cooked_pork_stewmeat", "Cooked Pork Stew Meat", 3, 0.3F);
    public static final ItemDefinition LAMB_SCRAPS = butcheryFood("lamb_scraps", "Raw Mutton Scraps", 2, 0.2F);
    public static final ItemDefinition COOKED_LAMB_SCRAPS = butcheryFood("cooked_lamb_scraps", "Cooked Mutton Scraps", 4, 0.4F);
    public static final ItemDefinition GROUND_LAMB = butcheryFood("ground_lamb", "Raw Ground Mutton", 1, 0.1F);
    public static final ItemDefinition COOKED_GROUND_LAMB = butcheryFood("cooked_ground_lamb", "Cooked Ground Mutton", 2, 0.2F);
    public static final ItemDefinition CUBED_LAMB = butcheryFood("cubed_lamb", "Raw Cubed Mutton", 2, 0.2F);
    public static final ItemDefinition COOKED_CUBED_LAMB = butcheryFood("cooked_cubed_lamb", "Cooked Cubed Mutton", 4, 0.4F);
    public static final ItemDefinition LAMB_RIBS = butcheryFood("lamb_ribs", "Raw Mutton Ribs", 6, 0.6F);
    public static final ItemDefinition COOKED_LAMB_RIBS = butcheryFood("cooked_lamb_ribs", "Cooked Mutton Ribs", 12, 1.2F);
    public static final ItemDefinition LAMB_ROAST = butcheryFood("lamb_roast", "Raw Mutton Roast", 6, 0.6F);
    public static final ItemDefinition COOKED_LAMB_ROAST = butcheryFood("cooked_lamb_roast", "Cooked Mutton Roast", 12, 1.2F);
    public static final ItemDefinition LAMB_STEWMEAT = butcheryFood("lamb_stewmeat", "Raw Mutton Stew Meat", 1, 0.1F);
    public static final ItemDefinition COOKED_LAMB_STEWMEAT = butcheryFood("cooked_lamb_stewmeat", "Cooked Mutton Stew Meat", 3, 0.3F);
    public static final ItemDefinition CHICKEN_BREAST = rawChickenButcheryFood("chicken_breast", "Raw Chicken Breast", 2, 0.3F);
    public static final ItemDefinition COOKED_CHICKEN_BREAST = butcheryFood("cooked_chicken_breast", "Cooked Chicken Breast", 4, 0.4F);
    public static final ItemDefinition CUBED_CHICKEN = rawChickenButcheryFood("cubed_chicken", "Raw Cubed Chicken", 1, 0.2F);
    public static final ItemDefinition COOKED_CUBED_CHICKEN = butcheryFood("cooked_cubed_chicken", "Cooked Cubed Chicken", 2, 0.4F);
    public static final ItemDefinition GROUND_CHICKEN = rawChickenButcheryFood("ground_chicken", "Raw Ground Chicken", 1, 0.1F);
    public static final ItemDefinition COOKED_GROUND_CHICKEN = butcheryFood("cooked_ground_chicken", "Cooked Ground Chicken", 2, 0.4F);
    public static final ItemDefinition CHICKEN_LEG = rawChickenButcheryFood("chicken_leg", "Raw Chicken Leg", 1, 0.2F);
    public static final ItemDefinition COOKED_CHICKEN_LEG = butcheryFood("cooked_chicken_leg", "Cooked Chicken Leg", 2, 0.3F);
    public static final ItemDefinition CHICKEN_SCRAPS = rawChickenButcheryFood("chicken_scraps", "Raw Chicken Scraps", 1, 0.1F);
    public static final ItemDefinition COOKED_CHICKEN_SCRAPS = butcheryFood("cooked_chicken_scraps", "Cooked Chicken Scraps", 2, 0.2F);
    public static final ItemDefinition CHICKEN_THIGH = rawChickenButcheryFood("chicken_thigh", "Raw Chicken Thigh", 1, 0.2F);
    public static final ItemDefinition COOKED_CHICKEN_THIGH = butcheryFood("cooked_chicken_thigh", "Cooked Chicken Thigh", 3, 0.3F);
    public static final ItemDefinition CHICKEN_WING = rawChickenButcheryFood("chicken_wing", "Raw Chicken Wing", 1, 0.1F);
    public static final ItemDefinition COOKED_CHICKEN_WING = butcheryFood("cooked_chicken_wing", "Cooked Chicken Wing", 2, 0.2F);
    public static final ItemDefinition CHICKEN_STEWMEAT = rawChickenButcheryFood("chicken_stewmeat", "Raw Stew Chicken", 1, 0.2F);
    public static final ItemDefinition COOKED_CHICKEN_STEWMEAT = butcheryFood("cooked_chicken_stewmeat", "Cooked Stew Chicken", 2, 0.3F);
    public static final ItemDefinition GOAT_CHOP = butcheryFood("goat_chop", "Raw Chevon Chop", 5, 0.7F);
    public static final ItemDefinition COOKED_GOAT_CHOP = butcheryFood("cooked_goat_chop", "Cooked Chevon Chop", 11, 1.3F);
    public static final ItemDefinition GOAT_RIBS = butcheryFood("goat_ribs", "Raw Chevon Ribs", 5, 0.7F);
    public static final ItemDefinition COOKED_GOAT_RIBS = butcheryFood("cooked_goat_ribs", "Cooked Chevon Ribs", 11, 1.3F);
    public static final ItemDefinition GOAT_ROAST = butcheryFood("goat_roast", "Raw Chevon Roast", 5, 0.7F);
    public static final ItemDefinition COOKED_GOAT_ROAST = butcheryFood("cooked_goat_roast", "Cooked Chevon Roast", 11, 1.3F);
    public static final ItemDefinition GOAT_SCRAPS = butcheryFood("goat_scraps", "Raw Chevon Scraps", 1, 0.3F);
    public static final ItemDefinition COOKED_GOAT_SCRAPS = butcheryFood("cooked_goat_scraps", "Cooked Chevon Scraps", 3, 0.5F);
    public static final ItemDefinition GOAT_STEWMEAT = butcheryFood("goat_stewmeat", "Raw Chevon Stew Meat", 1, 0.1F);
    public static final ItemDefinition COOKED_GOAT_STEWMEAT = butcheryFood("cooked_goat_stewmeat", "Cooked Chevon Stew Meat", 2, 0.4F);
    public static final ItemDefinition GROUND_GOAT = butcheryFood("ground_goat", "Raw Ground Chevon", 1, 0.2F);
    public static final ItemDefinition COOKED_GROUND_GOAT = butcheryFood("cooked_ground_goat", "Cooked Ground Chevon", 2, 0.3F);
    public static final ItemDefinition CUBED_GOAT = butcheryFood("cubed_goat", "Raw Cubed Chevon", 1, 0.3F);
    public static final ItemDefinition COOKED_CUBED_GOAT = butcheryFood("cooked_cubed_goat", "Cooked Cubed Chevon", 3, 0.5F);
    public static final ItemDefinition RABBIT_SADDLE = butcheryFood("rabbit_saddle", "Raw Rabbit Saddle", 1, 0.4F);
    public static final ItemDefinition COOKED_RABBIT_SADDLE = butcheryFood("cooked_rabbit_saddle", "Cooked Rabbit Saddle", 3, 0.5F);
    public static final ItemDefinition RABBIT_THIGH = butcheryFood("rabbit_thigh", "Raw Rabbit Thigh", 1, 0.3F);
    public static final ItemDefinition COOKED_RABBIT_THIGH = butcheryFood("cooked_rabbit_thigh", "Cooked Rabbit Thigh", 2, 0.4F);
    public static final ItemDefinition RABBIT_LEG = butcheryFood("rabbit_leg", "Raw Rabbit Leg", 1, 0.3F);
    public static final ItemDefinition COOKED_RABBIT_LEG = butcheryFood("cooked_rabbit_leg", "Cooked Rabbit Leg", 2, 0.4F);
    public static final ItemDefinition RABBIT_SCRAPS = butcheryFood("rabbit_scraps", "Raw Rabbit Scrap", 1, 0.2F);
    public static final ItemDefinition COOKED_RABBIT_SCRAPS = butcheryFood("cooked_rabbit_scraps", "Cooked Rabbit Scrap", 2, 0.3F);
    public static final ItemDefinition RABBIT_STEWMEAT = butcheryFood("rabbit_stewmeat", "Raw Stew Rabbit", 1, 0.3F);
    public static final ItemDefinition COOKED_RABBIT_STEWMEAT = butcheryFood("cooked_rabbit_stewmeat", "Cooked Stew Rabbit", 2, 0.4F);
    public static final ItemDefinition GROUND_RABBIT = butcheryFood("ground_rabbit", "Raw Ground Rabbit", 1, 0.2F);
    public static final ItemDefinition COOKED_GROUND_RABBIT = butcheryFood("cooked_ground_rabbit", "Cooked Ground Rabbit", 2, 0.5F);
    public static final ItemDefinition CUBED_RABBIT = butcheryFood("cubed_rabbit", "Raw Cubed Rabbit", 1, 0.3F);
    public static final ItemDefinition COOKED_CUBED_RABBIT = butcheryFood("cooked_cubed_rabbit", "Cooked Cubed Rabbit", 2, 0.5F);
    public static final ItemDefinition BRAIN = rawOffalFood("brain", "Raw Brain", 3, 0.1F);
    public static final ItemDefinition COOKED_BRAIN = butcheryFood("cooked_brain", "Cooked Brain", 4, 0.2F);
    public static final ItemDefinition HEART = rawOffalFood("heart", "Raw Heart", 3, 0.1F);
    public static final ItemDefinition COOKED_HEART = butcheryFood("cooked_heart", "Cooked Heart", 6, 0.2F);
    public static final ItemDefinition KIDNEY = rawOffalFood("kidney", "Raw Kidney", 1, 0.1F);
    public static final ItemDefinition COOKED_KIDNEY = butcheryFood("cooked_kidney", "Cooked Kidney", 2, 0.2F);
    public static final ItemDefinition LIVER = rawOffalFood("liver", "Raw Liver", 3, 0.2F);
    public static final ItemDefinition COOKED_LIVER = butcheryFood("cooked_liver", "Cooked Liver", 6, 0.4F);
    public static final ItemDefinition LUNG = rawOffalFood("lung", "Raw Lung", 4, 0.2F);
    public static final ItemDefinition COOKED_LUNG = butcheryFood("cooked_lung", "Cooked Lung", 8, 0.4F);
    public static final ItemDefinition STOMACH = rawOffalFood("stomach", "Raw Stomach", 2, 0.1F);
    public static final ItemDefinition COOKED_STOMACH = butcheryFood("cooked_stomach", "Cooked Stomach", 4, 0.2F);
    public static final ItemDefinition TRIPE = rawOffalFood("tripe", "Raw Intestines", 3, 0.1F);
    public static final ItemDefinition COOKED_TRIPE = butcheryFood("cooked_tripe", "Cooked Intestines", 6, 0.2F);
    public static final ItemDefinition EYEBALL = rawOffalFood("eyeball", "Eyeball", 1, 0.1F);
    public static final ItemDefinition COOKED_EYEBALL = butcheryFood("cooked_eyeball", "Cooked Eyeball", 2, 0.2F);
    public static final ItemDefinition SAUSAGE = rawSausageFood("sausage", "Raw Sausage");
    public static final ItemDefinition COOKED_SAUSAGE = butcheryFood("cooked_sausage", "Cooked Sausage", 6, 0.2F);
    public static final ItemDefinition FAT = butcheryItem("fat", "Fat");
    public static final ItemDefinition GELATIN = butcheryItem("gelatin", "Gelatin");
    public static final ItemDefinition SALISBURY_STEAK = effectBowlFood(
        EDLModule.MEALS,
        "salisbury_steak",
        "Plate of Salisbury Steak",
        4,
        0.8F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "salisbury_steak_block"
    );
    public static final ItemDefinition MASHED_POTATO_GRAVY = effectBowlFood(
        EDLModule.MEALS,
        "mashed_potato_gravy",
        "Bowl of Mashed Potatoes and Gravy",
        5,
        0.7F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "mashed_potato_gravy_block"
    );
    public static final ItemDefinition HASH = effectBowlFood(
        EDLModule.MEALS,
        "hash",
        "Bowl of Hash",
        3,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "hash_block"
    );
    public static final ItemDefinition POTROAST = effectBowlFood(
        EDLModule.MEALS,
        "potroast",
        "Plate of Pot Roast",
        6,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "potroast_block"
    );
    public static final ItemDefinition MEATLOAF = effectBowlFood(
        EDLModule.MEALS,
        "meatloaf",
        "Plate of Meatloaf",
        5,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "meatloaf_block"
    );
    public static final ItemDefinition MEATLOAF_SANDWICH = effectFood(
        EDLModule.MEALS,
        "meatloaf_sandwich",
        "Meatloaf Sandwich",
        10,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "meatloaf_block"
    );
    public static final ItemDefinition BBQ_RIBS = effectBowlFood(
        EDLModule.MEALS,
        "bbq_ribs",
        "Plate of BBQ Ribs",
        8,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "bbq_ribs_block"
    );
    public static final ItemDefinition PULLED_PORK_SANDWICH = effectFood(
        EDLModule.MEALS,
        "pulled_pork_sandwich",
        "Pulled Pork Sandwich",
        15,
        0.8F,
        new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "pulled_pork_block"
    );
    public static final ItemDefinition RACK_LAMB = effectBowlFood(
        EDLModule.MEALS,
        "rack_lamb",
        "Plate of Rack of Lamb",
        7,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "rack_lamb_block"
    );
    public static final ItemDefinition STIRFRY = effectBowlFood(
        EDLModule.MEALS,
        "stirfry",
        "Bowl of Stir Fry",
        13,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "stirfry_block"
    );
    public static final ItemDefinition BEEF_WELLINGTON = effectBowlFood(
        EDLModule.MEALS,
        "beef_wellington",
        "Plate of Beef Wellington",
        16,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "beef_wellington_block"
    );
    public static final ItemDefinition HAGGIS = effectBowlFood(
        EDLModule.MEALS,
        "haggis",
        "Plate of Haggis",
        6,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "haggis_block"
    );
    public static final ItemDefinition MACARONI_CHEESE = effectBowlFood(
        EDLModule.MEALS,
        "macaroni_cheese",
        "Macaroni and Cheese",
        4,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "macaroni_cheese_block"
    );
    public static final ItemDefinition LASAGNA = effectBowlFood(
        EDLModule.MEALS,
        "lasagna",
        "Lasagna",
        9,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "lasagna_block"
    );
    public static final ItemDefinition HOTDISH = effectBowlFood(
        EDLModule.MEALS,
        "hotdish",
        "Hotdish",
        10,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "hotdish_block"
    );
    public static final ItemDefinition CURRY = effectBowlFood(
        EDLModule.MEALS,
        "curry",
        "Curry",
        7,
        0.7F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "curry_block"
    );
    public static final ItemDefinition CURRY_RICE = effectBowlFood(
        EDLModule.MEALS,
        "curry_rice",
        "Curry Rice",
        13,
        0.7F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "curry_block"
    );
    public static final ItemDefinition PORK_STEW = effectBowlFood(
        EDLModule.MEALS,
        "pork_stew",
        "Pork Stew",
        7,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "pork_stew_block"
    );
    public static final ItemDefinition LAMB_STEW = effectBowlFood(
        EDLModule.MEALS,
        "lamb_stew",
        "Lamb Stew",
        7,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "lamb_stew_block"
    );
    public static final ItemDefinition CHICKEN_STEW = effectBowlFood(
        EDLModule.MEALS,
        "chicken_stew",
        "Chicken Stew",
        7,
        0.7F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "chicken_stew_block"
    );
    public static final ItemDefinition BEEF_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "beef_stew_rice",
        "Beef Stew and Rice",
        18,
        0.8F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "beef_stew_block"
    );
    public static final ItemDefinition PORK_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "pork_stew_rice",
        "Pork Stew and Rice",
        13,
        0.8F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "pork_stew_block"
    );
    public static final ItemDefinition LAMB_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "lamb_stew_rice",
        "Lamb Stew and Rice",
        13,
        0.8F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "lamb_stew_block"
    );
    public static final ItemDefinition RABBIT_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "rabbit_stew_rice",
        "Rabbit Stew and Rice",
        16,
        0.6F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "rabbit_stew_block"
    );
    public static final ItemDefinition CHICKEN_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "chicken_stew_rice",
        "Chicken Stew and Rice",
        13,
        0.7F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "chicken_stew_block"
    );
    public static final ItemDefinition FISH_STEW_RICE = effectBowlFood(
        EDLModule.MEALS,
        "fish_stew_rice",
        "Fish Stew and Rice",
        18,
        0.8F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "fish_stew_block"
    );
    public static final ItemDefinition BEEF_BULGOGI = effectBowlFood(
        EDLModule.MEALS,
        "beef_bulgogi",
        "Beef Bulgogi",
        8,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "cubed_beef",
        "soy_sauce_item",
        "hot_sauce"
    );
    public static final ItemDefinition CARAMEL_CHICKEN = effectBowlFood(
        EDLModule.MEALS,
        "caramel_chicken",
        "Caramel Chicken",
        7,
        0.4F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "chicken_thigh",
        "fish_sauce",
        "soy_sauce_item"
    );
    public static final ItemDefinition RICEBALL = effectFood(
        EDLModule.MEALS,
        "riceball",
        "Riceball",
        7,
        0.4F,
        new FoodEffectEntry[]{new FoodEffectEntry(WATER_BREATHING, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "fish_flakes"
    );
    public static final ItemDefinition RICEBALL_FILLED = effectFood(
        EDLModule.MEALS,
        "riceball_filled",
        "Filled Riceball",
        11,
        0.4F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(WATER_BREATHING, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "riceball",
        "fish_flakes"
    );
    public static final ItemDefinition EDAMAME = bowlFood(
        EDLModule.MEALS,
        "edamame",
        "Edamame",
        5,
        0.1F,
        "soybean_pod",
        "salt"
    );
    public static final ItemDefinition HONEY_CHILI_CHICKEN = effectBowlFood(
        EDLModule.MEALS,
        "honey_chili_chicken",
        "Honey Chili Chicken",
        10,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "cubed_chicken",
        "soy_sauce_item"
    );
    public static final ItemDefinition KIMCHI = effectBowlFood(
        EDLModule.PICKLING,
        "kimchi_item",
        "Kimchi",
        4,
        0.6F,
        new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 1, 1.0F),
        "shredded_cabbage_item",
        "grated_ginger",
        "grated_garlic",
        "chili_powder",
        REQUIRES_VAT
    );
    public static final ItemDefinition KIMCHI_FRIED_RICE = effectBowlFood(
        EDLModule.MEALS,
        "kimchi_fried_rice",
        "Kimchi Fried Rice",
        9,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "kimchi_item",
        "hot_sauce",
        "cooking_oil"
    );
    public static final ItemDefinition KONGJANG = effectBowlFood(
        EDLModule.MEALS,
        "kongjang",
        "Kongjang",
        5,
        0.3F,
        new FoodEffectEntry(RESISTANCE, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "soaked_soybeans",
        "soy_sauce_item",
        "cooking_oil"
    );
    public static final ItemDefinition KIWIBURGER = effectFood(
        EDLModule.MEALS,
        "kiwiburger",
        "Kiwiburger",
        16,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "pickled_beet_item"
    );
    public static final ItemDefinition SALAMI_MIX = effectFood(
        EDLModule.CORE,
        "salami_mix",
        "Salami Mix",
        0,
        0.3F,
        HUNGER,
        BRIEF_EFFECT_DURATION,
        0,
        0.75F,
        "ground_beef",
        "fat",
        "grated_garlic"
    );
    public static final ItemDefinition UNRIPE_SALAMI_ITEM = define(new ItemDefinition(
        EDLModule.CORE,
        "unripe_salami_item",
        "Unripe Salami",
        () -> new ItemEffectFoodBlock(
            EDLBlocks.UNRIPE_SALAMI_BLOCK.getBlock(),
            0,
            0.3F,
            new FoodEffectEntry(HUNGER, BRIEF_EFFECT_DURATION, 0, 0.75F)
        ),
        "salami_mix"
    ));
    public static final ItemDefinition SALAMI_ITEM = define(new ItemDefinition(
        EDLModule.CORE,
        "salami_item",
        "Salami",
        () -> new ItemEffectFoodBlock(
            EDLBlocks.SALAMI_BLOCK.getBlock(),
            4,
            0.3F,
            ExtraDelightLegacy.MODID + ".salami.tooltip",
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        ),
        "unripe_salami_item"
    ));
    public static final ItemDefinition NAEM_MOO = effectFood(
        EDLModule.MEALS,
        "naem_moo_item",
        "Naem Moo",
        7,
        0.8F,
        PICKLED,
        LONG_EFFECT_DURATION,
        3,
        1.0F,
        "ground_pork",
        "grated_garlic",
        "salt"
    );
    public static final ItemDefinition CHEESEBURGER_PICKLE = effectFood(
        EDLModule.MEALS,
        "cheeseburger_pickle",
        "Cheeseburger Pickle Popper",
        5,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 1, 1.0F)
        },
        "ground_beef",
        "cheese",
        "sliced_gherkin_item",
        REQUIRES_OVEN,
        REQUIRES_SHEET
    );
    public static final ItemDefinition HOT_WINGS = containerEffectFood(
        EDLModule.MEALS,
        "hot_wings",
        "Hot Wings",
        5,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{
            new FoodEffectEntry(FIRE_RESISTANCE, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 0.0F)
        },
        "cooked_chicken_wing",
        "butter",
        "hot_sauce",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition SUGAR_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "sugar_cookie_dough",
        "Sugar Cookie Dough",
        1,
        0.1F,
        "flour",
        "butter",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition SUGAR_COOKIE = food(
        EDLModule.BAKING,
        "sugar_cookie",
        "Sugar Cookie",
        2,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_ALEX = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_alex",
        "Raw Sugar Cookie Alex",
        1,
        0.1F,
        "sugar_cookie_dough",
        "cocoa_powder",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_CREEPER = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_creeper",
        "Raw Sugar Cookie Creeper",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_PICKAXE = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_pickaxe",
        "Raw Sugar Cookie Pickaxe",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_STEVE = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_steve",
        "Raw Sugar Cookie Steve",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_SWORD = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_sword",
        "Raw Sugar Cookie Sword",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_VILLAGER = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_villager",
        "Raw Sugar Cookie Villager",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_DIAMOND = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_diamond",
        "Raw Sugar Cookie Diamond",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_SUGAR_COOKIE_EMERALD = food(
        EDLModule.BAKING,
        "raw_sugar_cookie_emerald",
        "Raw Sugar Cookie Emerald",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition SUGAR_COOKIE_ALEX = food(
        EDLModule.BAKING,
        "sugar_cookie_alex",
        "Sugar Cookie Alex",
        2,
        0.1F,
        "raw_sugar_cookie_alex",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_CREEPER = food(
        EDLModule.BAKING,
        "sugar_cookie_creeper",
        "Sugar Cookie Creeper",
        2,
        0.1F,
        "raw_sugar_cookie_creeper",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_PICKAXE = food(
        EDLModule.BAKING,
        "sugar_cookie_pickaxe",
        "Sugar Cookie Pickaxe",
        2,
        0.1F,
        "raw_sugar_cookie_pickaxe",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_STEVE = food(
        EDLModule.BAKING,
        "sugar_cookie_steve",
        "Sugar Cookie Steve",
        2,
        0.1F,
        "raw_sugar_cookie_steve",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_SWORD = food(
        EDLModule.BAKING,
        "sugar_cookie_sword",
        "Sugar Cookie Sword",
        2,
        0.1F,
        "raw_sugar_cookie_sword",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_VILLAGER = food(
        EDLModule.BAKING,
        "sugar_cookie_villager",
        "Sugar Cookie Villager",
        2,
        0.1F,
        "raw_sugar_cookie_villager",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_DIAMOND = food(
        EDLModule.BAKING,
        "sugar_cookie_diamond",
        "Sugar Cookie Diamond",
        2,
        0.1F,
        "raw_sugar_cookie_diamond",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SUGAR_COOKIE_EMERALD = food(
        EDLModule.BAKING,
        "sugar_cookie_emerald",
        "Sugar Cookie Emerald",
        2,
        0.1F,
        "raw_sugar_cookie_emerald",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GRAHAM_CRACKER = food(
        EDLModule.BAKING,
        "graham_cracker",
        "Graham Cracker",
        2,
        0.4F,
        "flour",
        "butter",
        REQUIRES_MIXING_BOWL,
        REQUIRES_OVEN
    );
    public static final ItemDefinition PANFORTE_SLICE = effectFood(
        EDLModule.BAKING,
        "panforte_slice",
        "Slice of Panforte",
        5,
        0.3F,
        COMFORT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "dried_fruit",
        "roasted_hazelnuts",
        REQUIRES_PANFORTE,
        REQUIRES_OVEN,
        REQUIRES_PIE_DISH
    );
    public static final ItemDefinition MARSHMALLOW = food(
        EDLModule.SWEETS,
        "marshmallow",
        "Marshmallow",
        1,
        0.4F,
        "mallow_powder",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition SMORE = effectFood(
        EDLModule.SWEETS,
        "smore",
        "S'more",
        7,
        0.4F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "graham_cracker",
        "marshmallow",
        "cocoa_solids"
    );
    public static final ItemDefinition TRAIL_MIX = effectFood(
        EDLModule.SWEETS,
        "trail_mix",
        "Trail Mix",
        2,
        0.3F,
        NOURISHMENT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "dried_fruit",
        "roasted_peanuts"
    );
    public static final ItemDefinition NOUGAT = food(
        EDLModule.SWEETS,
        "nougat",
        "Nougat",
        2,
        0.6F,
        "butter",
        "roasted_peanuts",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition PEANUT_BRITTLE = food(
        EDLModule.SWEETS,
        "peanut_brittle",
        "Nut Brittle",
        1,
        0.6F,
        "butter",
        "roasted_peanuts"
    );
    public static final ItemDefinition BUTTERSCOTCH = food(
        EDLModule.SWEETS,
        "butterscotch",
        "Butterscotch",
        1,
        0.6F,
        "butter"
    );
    public static final ItemDefinition CARAMEL_SAUCE = alwaysEdibleContainerFood(
        EDLModule.SWEETS,
        "caramel_sauce",
        "Caramel Sauce",
        4,
        0.6F,
        Items.GLASS_BOTTLE,
        "butter"
    );
    public static final ItemDefinition CARAMEL_CANDY = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "caramel_candy",
        "Caramel Candy",
        1,
        0.6F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "caramel_sauce"
    );
    public static final ItemDefinition CARAMEL_APPLE = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "caramel_apple",
        "Caramel Apple",
        8,
        0.6F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "caramel_sauce"
    );
    public static final ItemDefinition CARAMEL_GOLDEN_APPLE = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "caramel_golden_apple",
        "Caramel Golden Apple",
        8,
        1.2F,
        new FoodEffectEntry[]{new FoodEffectEntry(REGENERATION, 100, 1, 1.0F),
            new FoodEffectEntry(ABSORPTION, 2400, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "caramel_sauce"
    );
    public static final ItemDefinition CARAMEL_POPCORN = alwaysEdibleEffectBowlFood(
        EDLModule.SWEETS,
        "caramel_popcorn",
        "Caramel Popcorn",
        6,
        0.6F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "popcorn",
        "caramel_sauce"
    );
    public static final ItemDefinition CARAMEL_CUSTARD = alwaysEdibleContainerFood(
        EDLModule.SWEETS,
        "caramel_custard",
        "Caramel Custard",
        7,
        0.6F,
        Items.GLASS_BOTTLE,
        "caramel_sauce"
    );
    public static final ItemDefinition CHOCOLATE_CUSTARD = custard("chocolate_custard", "Chocolate Custard", "cocoa_powder");
    public static final ItemDefinition PUMPKIN_CUSTARD = custard("pumpkin_custard", "Pumpkin Custard");
    public static final ItemDefinition HONEY_CUSTARD = custard("honey_custard", "Honey Custard");
    public static final ItemDefinition SWEET_BERRY_CUSTARD = custard("sweet_berry_custard", "Sweet Berry Custard");
    public static final ItemDefinition APPLE_CUSTARD = custard("apple_custard", "Apple Custard", "sliced_apple");
    public static final ItemDefinition NUT_BUTTER_CUSTARD = custard("nut_butter_custard", "Nut Butter Custard", "peanut_butter_bottle");
    public static final ItemDefinition APPLE_POPSICLE = popsicle("apple_popsicle", "Apple Popsicle", "apple_cider_fluid", REQUIRES_CHILLER);
    public static final ItemDefinition GLOW_BERRY_POPSICLE = glowBerryFood(
        EDLModule.SWEETS,
        "glow_berry_popsicle",
        "Glow Berry Popsicle",
        3,
        0.3F,
        REQUIRES_CHILLER
    );
    public static final ItemDefinition SWEET_BERRY_POPSICLE = popsicle("sweet_berry_popsicle", "Sweet Berry Popsicle", REQUIRES_CHILLER);
    public static final ItemDefinition FUDGE_POPSICLE = popsicle("fudge_popsicle", "Fudge Popsicle", "ice_cream", "cocoa_powder", REQUIRES_CHILLER);
    public static final ItemDefinition HONEY_POPSICLE = popsicle("honey_popsicle", "Honey Popsicle", "ice_cream", REQUIRES_CHILLER);
    public static final ItemDefinition CARAMEL_POPSICLE = popsicle("caramel_popsicle", "Caramel Popsicle", "ice_cream", "caramel_sauce", REQUIRES_CHILLER, REQUIRES_CARAMEL_SAUCE_FLUID);
    public static final ItemDefinition CINNAMON_POPSICLE = popsicle("cinnamon_popsicle", "Cinnamon Swirl Popsicle", "ice_cream", "ground_cinnamon", REQUIRES_CHILLER);
    public static final ItemDefinition WHIPPED_CREAM = alwaysEdibleContainerFood(
        EDLModule.SWEETS,
        "whipped_cream",
        "Whipped Cream",
        0,
        0.1F,
        Items.BOWL,
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition BBQ_SAUCE = containerFood(
        EDLModule.CORE,
        "bbq_jar_item",
        "BBQ Sauce",
        1,
        0.4F,
        Items.GLASS_BOTTLE,
        "vinegar"
    );
    public static final ItemDefinition KETCHUP = containerFood(
        EDLModule.CORE,
        "ketchup_jar_item",
        "Ketchup",
        0,
        0.3F,
        Items.GLASS_BOTTLE,
        "vinegar"
    );
    public static final ItemDefinition MAYO = containerFood(
        EDLModule.CORE,
        "mayo_jar_item",
        "Mayo",
        2,
        0.4F,
        Items.GLASS_BOTTLE,
        "cooking_oil",
        "vinegar",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition CARROT_SALAD = bowlFood(
        EDLModule.MEALS,
        "carrot_salad",
        "Carrot Salad",
        4,
        0.6F,
        "mayo_jar_item"
    );
    public static final ItemDefinition EGG_SALAD = bowlFood(
        EDLModule.MEALS,
        "egg_salad",
        "Egg Salad",
        3,
        0.4F,
        "mayo_jar_item",
        "boiled_egg"
    );
    public static final ItemDefinition FISH_SALAD = bowlFood(
        EDLModule.MEALS,
        "fish_salad",
        "Fish Salad",
        12,
        0.6F,
        "mayo_jar_item"
    );
    public static final ItemDefinition SEAWEED_PASTE = item(
        EDLModule.CORE,
        "seaweed_paste",
        "Seaweed Paste"
    );
    public static final ItemDefinition AGAR_SHEETS = item(
        EDLModule.CORE,
        "agar_sheets",
        "Agar Sheets",
        "seaweed_paste",
        "drying_rack"
    );
    public static final ItemDefinition AGAR_AGAR = food(
        EDLModule.CORE,
        "agar_agar",
        "Agar Agar",
        3,
        0.3F,
        "agar_sheets",
        "mortar_stone",
        "pestle_stone"
    );
    public static final ItemDefinition SEAWEED_CRISPS = food(
        EDLModule.CORE,
        "seaweed_crisps",
        "Seaweed Crisps",
        4,
        0.3F,
        "flour",
        "cooking_oil"
    );
    public static final ItemDefinition SEAWEED_SALAD = bowlFood(
        EDLModule.MEALS,
        "seaweed_salad",
        "Seaweed Salad",
        6,
        0.6F,
        "vinegar"
    );
    public static final ItemDefinition FURIKAKE = item(
        EDLModule.CORE,
        "furikake",
        "Furikake",
        "fish_flakes"
    );
    public static final ItemDefinition FURIKAKE_RICE = bowlFood(
        EDLModule.MEALS,
        "furikake_rice",
        "Furikake Rice",
        6,
        0.4F,
        "furikake"
    );
    public static final ItemDefinition TEA = define(new ItemDefinition(
        EDLModule.CORE,
        "tea",
        "Tea",
        ItemTea::new
    ));
    public static final ItemDefinition CURRY_POWDER = item(
        EDLModule.CORE,
        "curry_powder",
        "Curry Powder",
        "chili_powder",
        "ground_cinnamon",
        "mortar_stone",
        "pestle_stone"
    );
    public static final ItemDefinition RICE_PUDDING = alwaysEdibleContainerFood(
        EDLModule.SWEETS,
        "rice_pudding",
        "Rice Pudding",
        7,
        0.6F,
        Items.GLASS_BOTTLE,
        "ground_cinnamon"
    );
    public static final ItemDefinition MUFFIN_GINGER = food(EDLModule.BAKING, "muffin_ginger", "Ginger Muffin", 1, 0.6F, "ginger", REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition MUFFIN_CINNAMON = food(EDLModule.BAKING, "muffin_cinnamon", "Cinnamon Muffin", 1, 0.6F, "ground_cinnamon", REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition MUFFIN_SWEET_BERRY = food(EDLModule.BAKING, "muffin_sweet_berry", "Sweet Berry Muffin", 1, 0.6F, REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition MUFFIN_APPLE = food(EDLModule.BAKING, "muffin_apple", "Apple Muffin", 1, 0.6F, "sliced_apple", REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition FRUIT_BREAD = food(
        EDLModule.BAKING,
        "fruit_bread",
        "Fruit Bread",
        7,
        0.6F,
        "dried_fruit",
        REQUIRES_OVEN,
        REQUIRES_LOAF_PAN
    );
    public static final ItemDefinition BEET_MINT_SALAD = bowlFood(
        EDLModule.MEALS,
        "beet_mint_salad",
        "Beet Mint Salad",
        4,
        0.6F,
        "mint",
        "vinegar",
        "cooking_oil"
    );
    public static final ItemDefinition GUMMIES = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "gummies",
        "Gummies",
        1,
        0.3F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "agar_agar",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition FUDGE = effectFood(
        EDLModule.SWEETS,
        "fudge",
        "Fudge",
        2,
        0.6F,
        COMFORT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "marshmallow",
        "butter",
        REQUIRES_CHILLER,
        REQUIRES_TRAY
    );
    public static final ItemDefinition ALFREDO_SAUCE = bowlFood(
        EDLModule.CORE,
        "alfredo_sauce",
        "Alfredo Sauce",
        8,
        0.6F,
        "cheese",
        "butter"
    );
    public static final ItemDefinition PASTA_ALFREDO = effectBowlFood(
        EDLModule.MEALS,
        "pasta_alfredo",
        "Pasta with Alfredo Sauce",
        14,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "alfredo_sauce",
        "cooked_pasta"
    );
    public static final ItemDefinition CHICKEN_ALFREDO = effectBowlFood(
        EDLModule.MEALS,
        "chicken_alfredo",
        "Chicken Alfredo Pasta",
        20,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "pasta_alfredo"
    );
    public static final ItemDefinition PASTA_TOMATO = effectBowlFood(
        EDLModule.MEALS,
        "pasta_tomato",
        "Tomato Pasta",
        10,
        0.4F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "cooked_pasta"
    );
    public static final ItemDefinition MUSHROOM_RISOTTO = effectBowlFood(
        EDLModule.MEALS,
        "mushroom_risotto",
        "Mushroom Risotto",
        12,
        0.7F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "cheese",
        "butter"
    );
    public static final ItemDefinition STUFFED_CACTUS = effectFood(
        EDLModule.MEALS,
        "stuffed_cactus",
        "Stuffed Cactus",
        17,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "cooked_cactus",
        "cheese",
        "breading_misanplas"
    );
    public static final ItemDefinition EGGNOG = alwaysEdibleContainerFood(
        EDLModule.SWEETS,
        "eggnog",
        "Eggnog",
        7,
        0.6F,
        Items.GLASS_BOTTLE,
        "ground_cinnamon"
    );
    public static final ItemDefinition GINGER_BEER = containerFood(
        EDLModule.SWEETS,
        "ginger_beer",
        "Ginger Beer",
        4,
        0.6F,
        Items.GLASS_BOTTLE,
        "yeast",
        "ginger"
    );
    public static final ItemDefinition HORCHATA = containerEffectFood(
        EDLModule.SWEETS,
        "horchata",
        "Horchata",
        7,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "ground_cinnamon"
    );
    public static final ItemDefinition XOCOLATL = xocolatl(
        EDLModule.SWEETS,
        "xocolatl",
        "Xocolatl",
        7,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(FIRE_RESISTANCE, SHORT_EFFECT_DURATION, 0, 1.0F)},
        "chili_powder",
        "cocoa_powder"
    );
    public static final ItemDefinition CHOCOLATE_MILK = drink(EDLModule.SWEETS, "chocolate_milk", "Chocolate Milk", 2, 0.1F, "cocoa_powder");
    public static final ItemDefinition LEMONADE = drink(EDLModule.SWEETS, "lemonade", "Lemonade", 1, 0.1F, "lemon_juice", "ice_cubes", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition LIMEADE = drink(EDLModule.SWEETS, "limeade", "Limeade", 1, 0.1F, "lime_juice", "ice_cubes", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition ORANGEADE = drink(EDLModule.SWEETS, "orangeade", "Orangeade", 1, 0.1F, "orange_juice", "ice_cubes", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition CINNAMON_TOAST = effectFood(EDLModule.CORE, "cinnamon_toast", "Cinnamon Toast", 5, 0.6F, COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F, "toast", "butter", "ground_cinnamon");
    public static final ItemDefinition CORN_FLAKES = food(EDLModule.CORE, "corn_flakes", "Corn Flakes", 1, 0.3F, "corn_seeds", REQUIRES_DOUGH_SHAPING);
    public static final ItemDefinition CORN_FLAKES_CEREAL = cerealFood(EDLModule.CORE, "corn_flakes_cereal", "Bowl of Corn Flakes", "corn_flakes");
    public static final ItemDefinition LASAGNA_NOODLES = item(EDLModule.CORE, "lasagna_noodles", "Lasagna Noodles", REQUIRES_DOUGH_SHAPING);
    public static final ItemDefinition APPLE_CHIPS = food(EDLModule.CORE, "apple_chips", "Apple Chips", 1, 0.3F, "sliced_apple", "ground_cinnamon", REQUIRES_OVEN, REQUIRES_SHEET);
    public static final ItemDefinition GRILLED_GRAPEFRUIT = effectFood(EDLModule.CORE, "grilled_grapefruit", "Grilled Grapefruit", 4, 0.3F, SUNSHINE, LONG_EFFECT_DURATION, 1, 1.0F, "grapefruit", REQUIRES_OVEN, REQUIRES_TRAY);
    public static final ItemDefinition ROLL = food(EDLModule.BAKING, "roll", "Bread Roll", 1, 0.6F, REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition ROMBOSSE = effectFood(EDLModule.BAKING, "rombosse", "Rombosse", 9, 0.6F, COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F, "cinnamon_stick", REQUIRES_OVEN, REQUIRES_TRAY);
    public static final ItemDefinition STUFFED_MUSHROOMS = food(EDLModule.MEALS, "stuffed_mushrooms", "Stuffed Mushrooms", 5, 0.6F, "cheese", "breadcrumbs", "butter", REQUIRES_OVEN, REQUIRES_SHEET);
    public static final ItemDefinition CHICKEN_KIEV = effectFood(EDLModule.MEALS, "chicken_kiev", "Chicken Kiev", 8, 0.6F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)}, "butter", "garlic_clove", REQUIRES_OVEN, REQUIRES_TRAY);
    public static final ItemDefinition SAUSAGE_ROLL = food(EDLModule.MEALS, "sausage_roll", "Sausage Roll", 11, 0.8F, REQUIRES_OVEN, REQUIRES_SHEET);
    public static final ItemDefinition FRIED_FISH = food(EDLModule.MEALS, "fried_fish", "Fried Fish", 12, 0.6F, "breading_misanplas");
    public static final ItemDefinition FRIED_BRAINS = food(EDLModule.MEALS, "fried_brains", "Fried Brains", 11, 0.8F, "brain", "breading_misanplas");
    public static final ItemDefinition PORK_TENDERLOIN = effectFood(
        EDLModule.MEALS,
        "pork_tenderloin",
        "Pork Tenderloin",
        10,
        0.6F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "breading_misanplas"
    );
    public static final ItemDefinition PORK_TENDERLOIN_SANDWICH = effectFood(
        EDLModule.MEALS,
        "pork_tenderloin_sandwich",
        "Tenderloin Sandwich",
        15,
        0.6F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "pork_tenderloin"
    );
    public static final ItemDefinition SOS = bowlFood(
        EDLModule.MEALS,
        "sos",
        "SOS",
        12,
        0.8F,
        "beef_scraps",
        "flour",
        "bread_slice"
    );
    public static final ItemDefinition FISH_SOUP = effectBowlFood(EDLModule.MEALS, "fish_soup", "Fish Soup", 8, 0.7F, new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F));
    public static final ItemDefinition FISH_CHIPS = effectFood(EDLModule.MEALS, "fish_chips", "Fish n' Chips", 18, 0.6F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)}, "french_fries", "vinegar");
    public static final ItemDefinition MUSHROOM_BURGER = food(EDLModule.MEALS, "mushroom_burger", "Mushroom and Swiss Burger", 15, 0.8F, "cheese");
    public static final ItemDefinition CHEESEBURGER = effectFood(EDLModule.MEALS, "cheeseburger", "Cheeseburger", 15, 0.8F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 0, 1.0F)}, "sliced_tomato", "sliced_onion", "cheese", "sliced_gherkin_item");
    public static final ItemDefinition BACON_CHEESEBURGER = effectFood(EDLModule.MEALS, "bacon_cheeseburger", "Bacon Cheeseburger", 19, 0.8F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 0, 1.0F)}, "cheeseburger");
    public static final ItemDefinition BACON_EGG_SANDWICH = food(EDLModule.MEALS, "bacon_egg_sandwich", "Bacon Egg Sandwich", 12, 0.8F);
    public static final ItemDefinition BACON_EGG_CHEESE_SANDWICH = food(EDLModule.MEALS, "bacon_egg_cheese_sandwich", "Bacon Egg and Cheese Sandwich", 16, 0.8F, "cheese");
    public static final ItemDefinition EGG_SALAD_SANDWICH = food(EDLModule.MEALS, "egg_salad_sandwich", "Egg Salad Sandwich", 8, 0.6F, "egg_salad");
    public static final ItemDefinition FISH_SALAD_SANDWICH = food(EDLModule.MEALS, "fish_salad_sandwich", "Fish Salad Sandwich", 17, 0.6F, "fish_salad");
    public static final ItemDefinition OXTAIL_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "oxtail_soup",
        "Oxtail Soup",
        12,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F)
        },
        "oxtail",
        "sliced_tomato"
    );
    public static final ItemDefinition LIVER_ONIONS = effectBowlFood(EDLModule.MEALS, "liver_onions", "Liver and Onions", 8, 0.8F, new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "liver", "sliced_onion");
    public static final ItemDefinition MULLIGATAWNY_SOUP = effectBowlFood(EDLModule.MEALS, "mulligatawny_soup", "Mulligatawny Soup", 11, 0.7F, new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "curry_powder", "sliced_apple");
    public static final ItemDefinition AEBLEFLAESK = effectBowlFood(EDLModule.MEALS, "aebleflaesk", "Aebleflaesk", 8, 0.4F, new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "sliced_apple", "sliced_onion");
    public static final ItemDefinition CACTUS_SALAD = effectBowlFood(EDLModule.MEALS, "cactus_salad", "Cactus Salad", 13, 0.6F, new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F), "cooked_cactus", "sliced_tomato", "sliced_onion", "cheese", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition CUCUMBER_SALAD = effectBowlFood(EDLModule.MEALS, "cucumber_salad", "Cucumber Salad", 6, 0.3F, new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F), "sliced_cucumber_item", "garlic_clove", "sliced_ginger", "soy_sauce", "vinegar", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition APPLE_SLAW = bowlFood(EDLModule.MEALS, "apple_slaw", "Apple Slaw", 7, 0.6F, "sliced_apple", "mayo_jar_item", "vinegar", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition CITRUS_ONION_SALAD = effectBowlFood(EDLModule.MEALS, "citrus_onion_salad", "Citrus, Onion and Mint Salad", 5, 0.4F, new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "orange_juice", "sliced_onion", "mint", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition GRAPEFRUIT_BEETROOT_SALAD = bowlFood(EDLModule.MEALS, "grapefruit_beetroot_salad", "Grapefruit and Beetroot Salad", 6, 0.4F, "sliced_grapefruit", "sliced_beetroot_item", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition GAZPACHO = effectBowlFood(EDLModule.MEALS, "gazpacho", "Gazpacho", 8, 0.6F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)}, "sliced_tomato", "sliced_cucumber_item", "breadcrumbs", "vinegar", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition SHIRAZI_SALAD = effectBowlFood(EDLModule.MEALS, "shirazi_salad", "Shirazi Salad", 6, 0.4F, new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F), "sliced_tomato", "sliced_cucumber_item", "sliced_onion", "mint", "lemon_juice", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition MORKOVCHA = effectBowlFood(EDLModule.MEALS, "morkovcha", "Morkovcha", 3, 0.6F, new FoodEffectEntry[]{new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 0, 1.0F)}, "grated_carrot", "grated_garlic", "chili_powder", "salt", "vinegar", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition THAI_MELON_SALAD = effectBowlFood(EDLModule.MEALS, "thai_melon_salad", "Thai Melon Salad", 2, 0.4F, new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "melon_chunks", "sliced_cucumber_item", "sliced_onion", "sliced_ginger", "mint", "fish_sauce", "lime_juice", "vinegar", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition MELON_GAZPACHO = effectBowlFood(EDLModule.MEALS, "melon_gazpacho", "Melon Gazpacho", 3, 0.6F, new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "melon_chunks", "sliced_tomato", "sliced_cucumber_item", "sliced_onion", "garlic_clove", "mint", "breadcrumbs", "vinegar", "cooking_oil", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition CROQUE_MONSIEUR = effectBowlFood(EDLModule.MEALS, "croque_monsieur", "Croque Monsieur", 9, 0.8F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)}, "toast", "butter", "cheese", "flour");
    public static final ItemDefinition CROQUE_MADAME = effectBowlFood(EDLModule.MEALS, "croque_madame", "Croque Madam", 13, 0.8F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)}, "croque_monsieur");
    public static final ItemDefinition JALAPENO_STUFFED_POTATO = effectFood(EDLModule.MEALS, "jalapeno_stuffed_potato", "Jalapeño Stuffed Potato", 13, 0.7F, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), new FoodEffectEntry(FIRE_RESISTANCE, MEDIUM_EFFECT_DURATION, 0, 1.0F)}, "sliced_chili");
    public static final ItemDefinition CURRYWURST = food(EDLModule.MEALS, "currywurst", "Currywurst", 8, 0.6F, "ketchup_jar_item", "curry_powder");
    public static final ItemDefinition HAZELNUT_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "hazelnut_soup",
        "Hazelnut Soup",
        7,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "roasted_hazelnuts",
        "butter"
    );
    public static final ItemDefinition ONION_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "onion_soup",
        "French Onion Soup",
        7,
        0.7F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "sliced_onion",
        "butter",
        "bread_slice",
        "cheese"
    );
    public static final ItemDefinition ONION_BHAJI = food(EDLModule.MEALS, "onion_bhaji", "Onion Bhaji", 3, 0.4F, "sliced_onion", "flour", "curry_powder", "cooking_oil");
    public static final ItemDefinition BORSCHT = effectBowlFood(
        EDLModule.MEALS,
        "borscht",
        "Borscht",
        5,
        0.7F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "sliced_beetroot_item",
        "grated_carrot",
        "grated_potato",
        "sliced_tomato"
    );
    public static final ItemDefinition MISO_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "miso_soup",
        "Miso Soup",
        8,
        0.7F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "miso_paste_item"
    );
    public static final ItemDefinition NATTO_AND_RICE = effectBowlFood(
        EDLModule.MEALS,
        "natto_and_rice",
        "Natto and Rice",
        11,
        0.4F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(HUNGER, SHORT_EFFECT_DURATION, 0, 0.01F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "natto_item",
        "soy_sauce_item"
    );
    public static final ItemDefinition SAUERKRAUT_SOUP = effectBowlFood(
        EDLModule.MEALS,
        "sauerkraut_soup",
        "Sauerkraut Soup",
        6,
        0.7F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "sauerkraut_item"
    );
    public static final ItemDefinition SAUERKRAUT_SAUSAGE = effectBowlFood(
        EDLModule.MEALS,
        "sauerkraut_and_sausage",
        "Sauerkraut and Sausage",
        9,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "sauerkraut_item",
        "sausage"
    );
    public static final ItemDefinition ZUPA_OGORKOWA = effectBowlFood(
        EDLModule.MEALS,
        "zupa_ogorkowa",
        "Zupa Ogórkowa",
        6,
        0.7F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(PICKLED, LONG_EFFECT_DURATION, 3, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "sliced_gherkin_item",
        "pickle_juice"
    );
    public static final ItemDefinition PRESERVED_LEMON_PASTA = effectBowlFood(
        EDLModule.MEALS,
        "preserved_lemon_pasta",
        "Preserved Lemon Pasta",
        11,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, LONG_EFFECT_DURATION, 3, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 0.0F)
        },
        "preserved_lemon_item",
        "cooked_pasta",
        "butter",
        "cheese"
    );
    public static final ItemDefinition STUFFED_HEART = food(EDLModule.MEALS, "stuffed_heart", "Stuffed Heart", 16, 0.8F, "heart", "breadcrumbs", REQUIRES_OVEN, REQUIRES_TRAY);
    public static final ItemDefinition FAT_POTATOES = effectBowlFood(EDLModule.MEALS, "fat_potatoes", "Fat Potatoes", 7, 0.6F, new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F), "fat", REQUIRES_OVEN, REQUIRES_SQUARE_PAN);
    public static final ItemDefinition DEVILLED_SAUSAGES = effectBowlFood(
        EDLModule.MEALS,
        "devilled_sausages",
        "Devilled Sausages",
        10,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "sausage",
        "sliced_apple",
        "garlic_clove"
    );
    public static final ItemDefinition JERKY = food(EDLModule.CORE, "jerky", "Jerky", 8, 0.8F, "drying_rack");
    public static final ItemDefinition AIOLI = containerFood(EDLModule.CORE, "aioli_jar_item", "Aioli", 3, 0.4F, Items.GLASS_BOTTLE, "mayo_jar_item", "garlic_clove", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition FRIED_CHICKEN = effectFood(EDLModule.MEALS, "fried_chicken", "Fried Chicken", 13, 0.6F, COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F, "breading_misanplas");
    public static final ItemDefinition CHICKEN_PARM = effectBowlFood(
        EDLModule.MEALS,
        "chicken_parm",
        "Chicken Parmesan",
        10,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "fried_chicken",
        "cheese",
        REQUIRES_OVEN,
        REQUIRES_TRAY
    );
    public static final ItemDefinition GOURMET_HOT_CHOCOLATE = containerEffectFood(EDLModule.SWEETS, "gourmet_hot_chocolate", "Gourmet Hot Chocolate", 4, 0.6F, Items.GLASS_BOTTLE, new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)}, "whipped_cream", "marshmallow", "ground_cinnamon");
    public static final ItemDefinition FLUFFER_NUTTER = food(EDLModule.MEALS, "fluffer_nutter", "Fluffer Nutter", 8, 0.6F, "bread_slice", "peanut_butter_bottle", "marshmallow_fluff_bottle");
    public static final ItemDefinition ICE_CREAM_SUNDAE = alwaysEdibleContainerFood(EDLModule.SWEETS, "ice_cream_sundae", "Ice Cream Sundae", 8, 0.6F, Items.GLASS_BOTTLE, "ice_cream", "roasted_hazelnuts");
    public static final ItemDefinition FROSTING_BLACK = frosting("frosting_black", "Black Frosting");
    public static final ItemDefinition FROSTING_BLUE = frosting("frosting_blue", "Blue Frosting");
    public static final ItemDefinition FROSTING_BROWN = frosting("frosting_brown", "Brown Frosting");
    public static final ItemDefinition FROSTING_CYAN = frosting("frosting_cyan", "Cyan Frosting");
    public static final ItemDefinition FROSTING_GRAY = frosting("frosting_gray", "Gray Frosting");
    public static final ItemDefinition FROSTING_GREEN = frosting("frosting_green", "Green Frosting");
    public static final ItemDefinition FROSTING_LIGHT_BLUE = frosting("frosting_light_blue", "Light Blue Frosting");
    public static final ItemDefinition FROSTING_LIGHT_GRAY = frosting("frosting_light_gray", "Light Gray Frosting");
    public static final ItemDefinition FROSTING_LIME = frosting("frosting_lime", "Lime Frosting");
    public static final ItemDefinition FROSTING_MAGENTA = frosting("frosting_magenta", "Magenta Frosting");
    public static final ItemDefinition FROSTING_ORANGE = frosting("frosting_orange", "Orange Frosting");
    public static final ItemDefinition FROSTING_PINK = frosting("frosting_pink", "Pink Frosting");
    public static final ItemDefinition FROSTING_PURPLE = frosting("frosting_purple", "Purple Frosting");
    public static final ItemDefinition FROSTING_RED = frosting("frosting_red", "Red Frosting");
    public static final ItemDefinition FROSTING_WHITE = frosting("frosting_white", "White Frosting");
    public static final ItemDefinition FROSTING_YELLOW = frosting("frosting_yellow", "Yellow Frosting");
    public static final ItemDefinition CRACKERS = alwaysEdibleFood(
        EDLModule.BAKING,
        "crackers",
        "Crackers",
        0,
        0.6F,
        "flour",
        "butter",
        REQUIRES_OVEN,
        REQUIRES_SHEET
    );
    public static final ItemDefinition GLAZED_CARROT = food(
        EDLModule.MEALS,
        "glazed_carrot",
        "Glazed Carrot",
        3,
        0.6F,
        "butter"
    );
    public static final ItemDefinition CACTUS_EGGS = bowlFood(
        EDLModule.MEALS,
        "cactus_eggs",
        "Cactus and Eggs",
        10,
        0.4F,
        "cooked_cactus",
        "scrambled_eggs"
    );
    public static final ItemDefinition CHOCOLATE_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "chocolate_cookie_dough",
        "Chocolate Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition CHOCOLATE_COOKIE = food(
        EDLModule.BAKING,
        "chocolate_cookie",
        "Chocolate Cookie",
        2,
        0.1F,
        "chocolate_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition NUT_BUTTER_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "nut_butter_cookie_dough",
        "Nut Butter Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        "peanut_butter_bottle",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition NUT_BUTTER_COOKIE = food(
        EDLModule.BAKING,
        "nut_butter_cookie",
        "Nut Butter Cookie",
        2,
        0.1F,
        "nut_butter_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition APPLE_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "apple_cookie_dough",
        "Apple Cinnamon Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        "ground_cinnamon",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition APPLE_COOKIE = food(
        EDLModule.BAKING,
        "apple_cookie",
        "Apple Cinnamon Cookie",
        2,
        0.1F,
        "apple_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition CHOCOLATE_CHIP_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "chocolate_chip_cookie_dough",
        "Chocolate Chip Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        "dark_chocolate_chips",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition GINGERBREAD_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "gingerbread_cookie_dough",
        "Gingerbread Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        "grated_ginger",
        "ground_cinnamon",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition GINGERBREAD_COOKIE = food(
        EDLModule.BAKING,
        "gingerbread_cookie",
        "Gingerbread Cookie",
        2,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GLOW_BERRY_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "glow_berry_cookie_dough",
        "Glow Berry Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition GLOW_BERRY_COOKIE = glowBerryFood(
        EDLModule.BAKING,
        "glow_berry_cookie",
        "Glow Berry Cookie",
        2,
        0.1F,
        "glow_berry_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition HONEY_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "honey_cookie_dough",
        "Honey Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition PUMPKIN_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "pumpkin_cookie_dough",
        "Pumpkin Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition PUMPKIN_COOKIE = food(
        EDLModule.BAKING,
        "pumpkin_cookie",
        "Pumpkin Cookie",
        2,
        0.1F,
        "pumpkin_cookie_dough",
        REQUIRES_OVEN
    );
    public static final ItemDefinition SWEET_BERRY_COOKIE_DOUGH = food(
        EDLModule.BAKING,
        "sweet_berry_cookie_dough",
        "Sweet Berry Cookie Dough",
        1,
        0.1F,
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition RAW_GINGERBREAD_ALEX = food(
        EDLModule.BAKING,
        "raw_gingerbread_alex",
        "Raw Gingerbread Alex",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_CREEPER = food(
        EDLModule.BAKING,
        "raw_gingerbread_creeper",
        "Raw Gingerbread Creeper",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_PICKAXE = food(
        EDLModule.BAKING,
        "raw_gingerbread_pickaxe",
        "Raw Gingerbread Pickaxe",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_STEVE = food(
        EDLModule.BAKING,
        "raw_gingerbread_steve",
        "Raw Gingerbread Steve",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_SWORD = food(
        EDLModule.BAKING,
        "raw_gingerbread_sword",
        "Raw Gingerbread Sword",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_VILLAGER = food(
        EDLModule.BAKING,
        "raw_gingerbread_villager",
        "Raw Gingerbread Villager",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_DIAMOND = food(
        EDLModule.BAKING,
        "raw_gingerbread_diamond",
        "Raw Gingerbread Diamond",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition RAW_GINGERBREAD_EMERALD = food(
        EDLModule.BAKING,
        "raw_gingerbread_emerald",
        "Raw Gingerbread Emerald",
        1,
        0.1F,
        "gingerbread_cookie_dough",
        REQUIRES_DOUGH_SHAPING
    );
    public static final ItemDefinition GINGERBREAD_ALEX = food(
        EDLModule.BAKING,
        "gingerbread_alex",
        "Gingerbread Alex",
        2,
        0.1F,
        "raw_gingerbread_alex",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_CREEPER = food(
        EDLModule.BAKING,
        "gingerbread_creeper",
        "Gingerbread Creeper",
        2,
        0.1F,
        "raw_gingerbread_creeper",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_PICKAXE = food(
        EDLModule.BAKING,
        "gingerbread_pickaxe",
        "Gingerbread Pickaxe",
        2,
        0.1F,
        "raw_gingerbread_pickaxe",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_STEVE = food(
        EDLModule.BAKING,
        "gingerbread_steve",
        "Gingerbread Steve",
        2,
        0.1F,
        "raw_gingerbread_steve",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_SWORD = food(
        EDLModule.BAKING,
        "gingerbread_sword",
        "Gingerbread Sword",
        2,
        0.1F,
        "raw_gingerbread_sword",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_VILLAGER = food(
        EDLModule.BAKING,
        "gingerbread_villager",
        "Gingerbread Villager",
        2,
        0.1F,
        "raw_gingerbread_villager",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_DIAMOND = food(
        EDLModule.BAKING,
        "gingerbread_diamond",
        "Gingerbread Diamond",
        2,
        0.1F,
        "raw_gingerbread_diamond",
        REQUIRES_OVEN
    );
    public static final ItemDefinition GINGERBREAD_EMERALD = food(
        EDLModule.BAKING,
        "gingerbread_emerald",
        "Gingerbread Emerald",
        2,
        0.1F,
        "raw_gingerbread_emerald",
        REQUIRES_OVEN
    );
    public static final ItemDefinition ICE_CREAM = iceCream(
        "ice_cream",
        "Ice Cream",
        4,
        0.6F,
        REQUIRES_CHILLER
    );
    public static final ItemDefinition CHOCOLATE_ICE_CREAM = iceCream(
        "chocolate_ice_cream",
        "Chocolate Ice Cream",
        6,
        0.6F,
        "ice_cream",
        "cocoa_powder",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition GLOW_BERRY_ICE_CREAM = glowBerryIceCream(
        "glow_berry_ice_cream",
        "Glow Berry Ice Cream",
        "ice_cream",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition SWEET_BERRY_ICE_CREAM = iceCream(
        "sweet_berry_ice_cream",
        "Sweet Berry Ice Cream",
        6,
        0.6F,
        "ice_cream",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition PUMPKIN_ICE_CREAM = iceCream(
        "pumpkin_ice_cream",
        "Pumpkin Ice Cream",
        6,
        0.6F,
        "ice_cream",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition HONEY_ICE_CREAM = iceCream(
        "honey_ice_cream",
        "Honey Ice Cream",
        6,
        0.6F,
        "ice_cream",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition APPLE_ICE_CREAM = iceCream(
        "apple_ice_cream",
        "Apple Ice Cream",
        6,
        0.6F,
        "ice_cream",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition COOKIE_DOUGH_ICE_CREAM = iceCream(
        "cookie_dough_ice_cream",
        "Cookie Dough Ice Cream",
        6,
        0.6F,
        "ice_cream",
        "sugar_cookie_dough",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition MINT_CHIP_ICE_CREAM = iceCream(
        "mint_chip_ice_cream",
        "Mint Chip Ice Cream",
        6,
        0.6F,
        "ice_cream",
        "mint",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition NUT_BUTTER_ICE_CREAM = iceCream(
        "nut_butter_ice_cream",
        "Nut Butter Ice Cream",
        6,
        0.6F,
        "ice_cream",
        "peanut_butter_bottle",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition MILKSHAKE = milkshake(
        "milkshake",
        "Milkshake",
        2.0F,
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition CHOCOLATE_MILKSHAKE = milkshake(
        "chocolate_milkshake",
        "Chocolate Milkshake",
        4.0F,
        "ice_cream",
        "cocoa_powder",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition GLOW_BERRY_MILKSHAKE = glowBerryMilkshake(
        "glow_berry_milkshake",
        "Glow Berry Milkshake",
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition SWEET_BERRY_MILKSHAKE = milkshake(
        "sweet_berry_milkshake",
        "Sweet Berry Milkshake",
        4.0F,
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition PUMPKIN_MILKSHAKE = milkshake(
        "pumpkin_milkshake",
        "Pumpkin Milkshake",
        4.0F,
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition HONEY_MILKSHAKE = milkshake(
        "honey_milkshake",
        "Honey Milkshake",
        4.0F,
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition APPLE_MILKSHAKE = milkshake(
        "apple_milkshake",
        "Apple Milkshake",
        4.0F,
        "ice_cream",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition COOKIE_DOUGH_MILKSHAKE = milkshake(
        "cookie_dough_milkshake",
        "Cookie Dough Milkshake",
        4.0F,
        "ice_cream",
        "sugar_cookie_dough",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition MINT_CHIP_MILKSHAKE = milkshake(
        "mint_chip_milkshake",
        "Mint Chip Milkshake",
        4.0F,
        "ice_cream",
        "mint",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition NUT_BUTTER_MILKSHAKE = milkshake(
        "nut_butter_milkshake",
        "Nut Butter Milkshake",
        4.0F,
        "ice_cream",
        "peanut_butter_bottle",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition CRISP_RICE_TREAT = effectFood(
        EDLModule.SWEETS,
        "crisp_rice_treat",
        "Puffed Rice Treat",
        2,
        0.4F,
        COMFORT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "crisp_rice",
        "marshmallow",
        "butter",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition COFFEE = coffeeDrink();
    public static final ItemDefinition COFFEE_JELLY = containerEffectFood(
        EDLModule.SWEETS,
        "coffee_jelly",
        "Coffee Jelly",
        1,
        0.3F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(JUMP_BOOST, BRIEF_EFFECT_DURATION, 0, 0.5F)},
        "coffee_fluid",
        "whipped_cream_fluid",
        "gelatin",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition JELLY_WHITE = jelly("jelly_white", "Bowl of Coconut Jelly");
    public static final ItemDefinition JELLY_ORANGE = jelly("jelly_orange", "Bowl of Orange Jelly");
    public static final ItemDefinition JELLY_MAGENTA = jelly("jelly_magenta", "Bowl of Mixed Berry Jelly");
    public static final ItemDefinition JELLY_LIGHT_BLUE = jelly("jelly_light_blue", "Bowl of Blue Raspberry Jelly");
    public static final ItemDefinition JELLY_YELLOW = jelly("jelly_yellow", "Bowl of Lemon Jelly");
    public static final ItemDefinition JELLY_LIME = jelly("jelly_lime", "Bowl of Lime Jelly");
    public static final ItemDefinition JELLY_PINK = jelly("jelly_pink", "Bowl of Strawberry Jelly");
    public static final ItemDefinition JELLY_GREY = jelly("jelly_grey", "Bowl of Mystery Jelly");
    public static final ItemDefinition JELLY_LIGHT_GREY = jelly("jelly_light_grey", "Bowl of Dragonfruit Jelly");
    public static final ItemDefinition JELLY_CYAN = jelly("jelly_cyan", "Bowl of Punch Jelly");
    public static final ItemDefinition JELLY_PURPLE = jelly("jelly_purple", "Bowl of Grape Jelly");
    public static final ItemDefinition JELLY_BLUE = jelly("jelly_blue", "Bowl of Blueberry Jelly");
    public static final ItemDefinition JELLY_BROWN = jelly("jelly_brown", "Bowl of Cola Jelly");
    public static final ItemDefinition JELLY_GREEN = jelly("jelly_green", "Bowl of Apple Jelly");
    public static final ItemDefinition JELLY_RED = jelly("jelly_red", "Bowl of Cherry Jelly");
    public static final ItemDefinition JELLY_BLACK = jelly("jelly_black", "Bowl of Blackberry Jelly");
    public static final ItemDefinition BAKED_COD_SERVING = effectBowlFood(
        EDLModule.MEALS,
        "baked_cod_serving",
        "Plate of Baked Cod",
        6,
        0.3F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "baked_cod"
    );
    public static final ItemDefinition CANDY_BAR_SALAD = effectBowlFood(
        EDLModule.SWEETS,
        "candy_bar_salad",
        "Candy Bar Salad",
        8,
        0.3F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "sliced_apple",
        "marshmallow",
        "whipped_cream_fluid",
        "caramel_sauce_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition AFFOGATO = containerEffectFood(
        EDLModule.SWEETS,
        "affogato",
        "Affogato",
        6,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "coffee",
        "ice_cream"
    );
    public static final ItemDefinition STIFF_PEAKS = containerItem(EDLModule.SWEETS, "stiff_peaks", "Stiff Peaks", Items.BOWL, "egg_white_fluid", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition MERINGUE = food(EDLModule.SWEETS, "meringue", "Meringue", 0, 0.4F, "stiff_peaks", REQUIRES_OVEN, REQUIRES_SHEET);
    public static final ItemDefinition LEMON_CURD = containerEffectFood(
        EDLModule.SWEETS,
        "lemon_curd",
        "Lemon Curd",
        4,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "lemon_juice",
        "lemon_zest",
        "butter"
    );
    public static final ItemDefinition DALGONA_COFFEE = containerFood(EDLModule.SWEETS, "dalgona_coffee", "Dalgona Coffee", 1, 0.1F, Items.GLASS_BOTTLE, "ground_coffee", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition GRAPEFRUIT_SORBET = effectBowlFood(EDLModule.SWEETS, "grapefruit_sorbet", "Grapefruit Sorbet", 3, 0.3F, new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F), "grapefruit_juice_fluid", REQUIRES_CHILLER);
    public static final ItemDefinition CHOCOLATE_ORANGE = effectBowlFood(
        EDLModule.SWEETS,
        "chocolate_orange",
        "Chocolate Orange",
        4,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "orange_zest",
        "milk_chocolate_syrup_fluid",
        REQUIRES_CHILLER,
        REQUIRES_MUFFIN_TIN
    );
    public static final ItemDefinition CHOCOLATE_MOUSSE = containerEffectFood(
        EDLModule.SWEETS,
        "chocolate_mousse",
        "Chocolate Mousse",
        4,
        0.4F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "stiff_peaks",
        "whipped_cream_fluid",
        REQUIRES_CHILLER
    );
    public static final ItemDefinition LEMON_DELICIOUS = effectBowlFood(
        EDLModule.SWEETS,
        "lemon_delicious",
        "Lemon Delicious",
        11,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 1, 1.0F),
            new FoodEffectEntry(SOUR_PUCKER, BRIEF_EFFECT_DURATION, 0, 0.0F)
        },
        "stiff_peaks",
        "lemon_juice",
        "lemon_zest",
        REQUIRES_OVEN
    );
    public static final ItemDefinition LIME_SOUFFLE = effectBowlFood(
        EDLModule.SWEETS,
        "lime_souffle",
        "Lime Souffle",
        9,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "stiff_peaks",
        "lime_juice",
        "lime_zest",
        REQUIRES_OVEN
    );
    public static final ItemDefinition CHEESE_SOUFFLE = effectBowlFood(
        EDLModule.MEALS,
        "cheese_souffle",
        "Cheese Souffle",
        13,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F)
        },
        "stiff_peaks",
        "cheese",
        REQUIRES_OVEN
    );
    public static final ItemDefinition ORANGE_CHICKEN = effectBowlFood(
        EDLModule.MEALS,
        "orange_chicken",
        "Orange Chicken",
        12,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 1, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "orange_juice",
        "breading_misanplas"
    );
    public static final ItemDefinition MELON_RIND_STIRFRY = effectBowlFood(
        EDLModule.MEALS,
        "melon_rind_stirfry",
        "Melon Rind Stir Fry",
        14,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "melon_rind",
        "fish_sauce",
        "soy_sauce"
    );
    public static final ItemDefinition LEMON_POSSET = containerEffectFood(
        EDLModule.SWEETS,
        "lemon_posset",
        "Lemon Posset",
        2,
        0.2F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(SOUR_PUCKER, BRIEF_EFFECT_DURATION, 0, 0.0F)
        },
        "lemon_juice",
        "lemon_zest"
    );
    public static final ItemDefinition MELON_LIME_GLAZED_CHICKEN = effectBowlFood(
        EDLModule.MEALS,
        "melon_lime_glazed_chicken",
        "Melon Lime Glazed Chicken",
        11,
        0.3F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(SUNSHINE, BRIEF_EFFECT_DURATION, 2, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 0.0F)
        },
        "lime_juice",
        "melon_chunks"
    );
    public static final ItemDefinition CHEESECAKE_SLICE = pieSlice("cheesecake_slice", "Slice of Cheesecake", "cheesecake");
    public static final ItemDefinition CHOCOLATE_CHEESECAKE_SLICE = pieSlice("chocolate_cheesecake_slice", "Slice of Chocolate Cheesecake", "chocolate_cheesecake");
    public static final ItemDefinition HONEY_CHEESECAKE_SLICE = pieSlice("honey_cheesecake_slice", "Slice of Honey Cheesecake", "honey_cheesecake");
    public static final ItemDefinition GLOW_BERRY_CHEESECAKE_SLICE = glowBerryPieSlice("glow_berry_cheesecake_slice", "Slice of Glow Berry Cheesecake", "glow_berry_cheesecake");
    public static final ItemDefinition PUMPKIN_CHEESECAKE_SLICE = pieSlice("pumpkin_cheesecake_slice", "Slice of Pumpkin Cheesecake", "pumpkin_cheesecake");
    public static final ItemDefinition APPLE_CHEESECAKE_SLICE = pieSlice("apple_cheesecake_slice", "Slice of Apple Cheesecake", "apple_cheesecake");
    public static final ItemDefinition SWEET_BERRY_PIE_SLICE = pieSlice("sweet_berry_pie_slice", "Slice of Sweet Berry Pie", "sweet_berry_pie");
    public static final ItemDefinition GLOW_BERRY_PIE_SLICE = glowBerryPieSlice("glow_berry_pie_slice", "Slice of Glow Berry Pie", "glow_berry_pie");
    public static final ItemDefinition KEY_LIME_PIE_SLICE = effectFood(
        EDLModule.SWEETS,
        "key_lime_pie_slice",
        "Slice of Key Lime Pie",
        3,
        0.4F,
        SUNSHINE,
        BRIEF_EFFECT_DURATION,
        1,
        1.0F,
        "key_lime_pie"
    );
    public static final ItemDefinition LEMON_MERINGUE_PIE_SLICE = effectFood(
        EDLModule.SWEETS,
        "lemon_meringue_pie_slice",
        "Slice of Lemon Meringue Pie",
        3,
        0.6F,
        SUNSHINE,
        BRIEF_EFFECT_DURATION,
        1,
        1.0F,
        "lemon_meringue_pie"
    );
    public static final ItemDefinition CARAMEL_CHEESECAKE_SLICE = pieSlice("caramel_cheesecake_slice", "Slice of Caramel Cheesecake", "caramel_cheesecake");
    public static final ItemDefinition PUMPKIN_PIE_SLICE = pieSlice("pumpkin_pie_slice", "Slice of Pumpkin Pie", "pumpkin_pie");
    public static final ItemDefinition MEAT_PIE_SLICE = effectFood(
        EDLModule.MEALS,
        "meat_pie_slice",
        "Slice of Meat Pie",
        4,
        0.8F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "meat_pie"
    );
    public static final ItemDefinition BACON_EGG_PIE_SLICE = effectFood(
        EDLModule.MEALS,
        "bacon_egg_pie_slice",
        "Slice of Bacon and Egg Pie",
        4,
        0.4F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "bacon_egg_pie"
    );
    public static final ItemDefinition STEAK_PICKLED_ONION_PIE_SLICE = effectFood(
        EDLModule.MEALS,
        "steak_pickled_onion_pie_slice",
        "Slice of Steak and Pickled Onion Pie",
        4,
        0.8F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "steak_pickled_onion_pie"
    );
    public static final ItemDefinition MISSISSIPPI_MUD_PIE_SLICE = effectFood(
        EDLModule.SWEETS,
        "mississippi_mud_pie_slice",
        "Slice of Mississippi Mud Pie",
        4,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "mississippi_mud_pie"
    );
    public static final ItemDefinition GRASSHOPPER_PIE_SLICE = effectFood(
        EDLModule.SWEETS,
        "grasshopper_pie_slice",
        "Slice of Grasshopper Pie",
        1,
        0.4F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "grasshopper_pie"
    );
    public static final ItemDefinition COFFEE_CAKE_SLICE = pieSlice("coffee_cake_slice", "Slice of Coffee Cake", "coffee_cake");
    public static final ItemDefinition CHOCOLATE_CAKE_SLICE = pieSlice("chocolate_cake_slice", "Slice of Chocolate Cake", "chocolate_cake");
    public static final ItemDefinition KYIV_CAKE_SLICE = effectFood(
        EDLModule.SWEETS,
        "kyiv_cake_slice",
        "Slice of Kyiv Cake",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "kyiv_cake"
    );
    public static final ItemDefinition QUICHE_SLICE = food(EDLModule.MEALS, "quiche_slice", "Slice of Quiche", 4, 0.8F, "quiche");
    public static final ItemDefinition MILK_TART_SLICE = effectFood(
        EDLModule.SWEETS,
        "milk_tart_slice",
        "Slice of Milk Tart",
        2,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "milk_tart"
    );
    public static final ItemDefinition TARTE_TATIN_IN_PAN = blockPlacingItem(EDLModule.SWEETS, "tarte_tatin_in_pan", "Tarte Tatin", () -> EDLBlocks.TARTE_TATIN.getBlock(), "tarte_tatin");
    public static final ItemDefinition TARTE_TATIN_SLICE = effectFood(
        EDLModule.SWEETS,
        "tarte_tatin_slice",
        "Slice of Tarte Tatin",
        5,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "tarte_tatin"
    );
    public static final ItemDefinition LEMON_CUCUMBER_CAKE_SLICE = pieSlice("lemon_cucumber_cake_slice", "Slice of Lemon Cucumber Cake", "lemon_cucumber_cake");
    public static final ItemDefinition MELON_LAYER_CAKE_SLICE = pieSlice("melon_layer_cake_slice", "Slice of Melon Layer Cake", "melon_layer_cake");
    public static final ItemDefinition PAVLOVA_SLICE = effectFood(
        EDLModule.SWEETS,
        "pavlova_slice",
        "Slice of Pavlova",
        3,
        0.4F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "pavlova"
    );
    public static final ItemDefinition MELON_FRUIT_SALAD_SERVING = effectBowlFood(
        EDLModule.SWEETS,
        "melon_fruit_salad_serving",
        "Bowl of Melon Fruit Salad",
        8,
        0.6F,
        new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "melon_fruit_salad"
    );
    public static final ItemDefinition MARSHMALLOW_SLICE = effectFood(
        EDLModule.SWEETS,
        "marshmallow_slice",
        "Piece of Marshmallow Slice",
        1,
        0.4F,
        COMFORT,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F,
        "marshmallow_slice_feast"
    );
    public static final ItemDefinition BAKED_ALASKA_SERVING = effectFood(
        EDLModule.SWEETS,
        "baked_alaska_serving",
        "Portion of Baked Alaska",
        3,
        0.6F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "baked_alaska"
    );
    public static final ItemDefinition SOY_GLAZED_SALMON_ITEM = effectBowlFood(
        EDLModule.MEALS,
        "soy_glazed_salmon_item",
        "Plate of Soy-Glazed Salmon",
        4,
        0.4F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "soy_glazed_salmon_block"
    );
    public static final ItemDefinition CHEESYMITE_SCROLL = effectBowlFood(
        EDLModule.MEALS,
        "cheesymite_scroll",
        "Cheesymite Scroll",
        4,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "cheesymite_scroll_block"
    );
    public static final ItemDefinition CORNBREAD = effectBowlFood(
        EDLModule.BAKING,
        "cornbread",
        "Slice of Cornbread",
        4,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "cornbread_feast"
    );
    public static final ItemDefinition CORN_PUDDING = effectBowlFood(
        EDLModule.MEALS,
        "corn_pudding",
        "Plate of Corn Pudding",
        5,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F)},
        "corn_pudding_feast"
    );
    public static final ItemDefinition APPLE_CRISP = effectBowlFood(
        EDLModule.SWEETS,
        "apple_crisp",
        "Bowl of Apple Crisp",
        6,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "apple_crisp_feast"
    );
    public static final ItemDefinition STUFFING = effectBowlFood(
        EDLModule.MEALS,
        "stuffing",
        "Bowl of Stuffing",
        5,
        0.7F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "stuffing_feast"
    );
    public static final ItemDefinition POTATO_AU_GRATIN = effectBowlFood(
        EDLModule.MEALS,
        "potato_au_gratin",
        "Plate of Potatoes Au Gratin",
        4,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "potato_au_gratin_feast"
    );
    public static final ItemDefinition CINNAMON_ROLLS = effectFood(
        EDLModule.BAKING,
        "cinnamon_rolls",
        "Cinnamon Roll",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "cinnamon_rolls_feast"
    );
    public static final ItemDefinition MONKEY_BREAD = effectBowlFood(
        EDLModule.SWEETS,
        "monkey_bread",
        "Plate of Monkey Bread",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "monkey_bread_feast"
    );
    public static final ItemDefinition CHRISTMAS_PUDDING = effectBowlFood(
        EDLModule.SWEETS,
        "christmas_pudding",
        "Slice of Christmas Pudding",
        3,
        0.4F,
        new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "christmas_pudding_feast"
    );
    public static final ItemDefinition PUNCH = drink(EDLModule.SWEETS, "punch", "Glass of Punch", 4, 0.0F, "punch_feast");
    public static final ItemDefinition MINT_LAMB = effectBowlFood(
        EDLModule.MEALS,
        "mint_lamb",
        "Plate of Mint Encrusted Lamb",
        15,
        0.8F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "mint_lamb_feast"
    );
    public static final ItemDefinition CHARCUTERIE_BOARD = effectBowlFood(
        EDLModule.MEALS,
        "charcuterie_board",
        "Plate of Charcuterie",
        4,
        0.8F,
        new FoodEffectEntry(NOURISHMENT, BRIEF_EFFECT_DURATION, 0, 1.0F),
        "charcuterie_board_feast"
    );
    public static final ItemDefinition BROWNIE = effectFood(
        EDLModule.SWEETS,
        "brownie",
        "Brownie",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "brownies_block"
    );
    public static final ItemDefinition BLONDIE = effectFood(
        EDLModule.SWEETS,
        "blondie",
        "Blondie",
        2,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "blondies_block"
    );
    public static final ItemDefinition STICKY_TOFFEE_PUDDING_SLICE = effectBowlFood(
        EDLModule.SWEETS,
        "sticky_toffee_pudding_slice",
        "Bowl of Sticky Toffee Pudding",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "sticky_toffee_pudding_block"
    );
    public static final ItemDefinition SCOTCHAROO = effectFood(
        EDLModule.SWEETS,
        "scotcharoo",
        "Scotcharoo",
        3,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "scotcharoo_block"
    );
    public static final ItemDefinition BLACK_FOREST_TRIFLE = effectBowlFood(
        EDLModule.SWEETS,
        "black_forest_trifle",
        "Bowl of Black Forest Trifle",
        4,
        0.1F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "black_forest_trifle_block"
    );
    public static final ItemDefinition CHILI_CON_CARNE = effectBowlFood(
        EDLModule.MEALS,
        "chili_con_carne",
        "Bowl of Chili con Carne",
        6,
        0.7F,
        new FoodEffectEntry[]{new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(FIRE_RESISTANCE, LONG_EFFECT_DURATION, 0, 1.0F)},
        "chili_con_carne_feast"
    );
    public static final ItemDefinition WHITE_CHILI = effectBowlFood(
        EDLModule.MEALS,
        "white_chili",
        "Bowl of White Chili",
        5,
        0.7F,
        new FoodEffectEntry[]{new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(FIRE_RESISTANCE, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "white_chili_feast"
    );
    public static final ItemDefinition BRUSCHETTA = effectFood(
        EDLModule.MEALS,
        "bruschetta",
        "Bruschetta",
        2,
        0.6F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "bruschetta_feast"
    );
    public static final ItemDefinition SALAD = effectBowlFood(
        EDLModule.MEALS,
        "salad",
        "Chef Salad",
        3,
        0.6F,
        new FoodEffectEntry(NOURISHMENT, LONG_EFFECT_DURATION, 0, 1.0F),
        "salad_block"
    );
    public static final ItemDefinition PORK_AND_APPLES = effectBowlFood(
        EDLModule.MEALS,
        "pork_and_apples",
        "Bowl of Pork and Apples",
        9,
        0.4F,
        new FoodEffectEntry[]{new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)},
        "pork_and_apples_feast"
    );
    public static final ItemDefinition STUFFED_APPLE = effectBowlFood(
        EDLModule.SWEETS,
        "stuffed_apple",
        "Stuffed Apple",
        4,
        0.6F,
        new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
        "stuffed_apples_feast"
    );
    public static final ItemDefinition STUFFED_APPLE_ICE_CREAM = effectBowlFood(
        EDLModule.SWEETS,
        "stuffed_apple_ice_cream",
        "Stuffed Apple with Ice Cream",
        12,
        0.6F,
        new FoodEffectEntry(COMFORT, LONG_EFFECT_DURATION, 0, 1.0F),
        "stuffed_apples_feast",
        "ice_cream"
    );
    public static final ItemDefinition PUMPKIN_ROLL = pieSlice("pumpkin_roll", "Slice of Pumpkin Roll", "pumpkin_roll_feast");
    public static final ItemDefinition LAVA_CAKE = food(EDLModule.SWEETS, "lava_cake", "Lava Cake", 2, 0.1F, "flour", REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition PEANUT_BUTTER_CUP = chocolateTruffle("peanut_butter_cup", "Nut Butter Cup", "peanut_butter_bottle");
    public static final ItemDefinition MALLOW_CUP = chocolateTruffle("mallow_cup", "Mallow Cup", "marshmallow_fluff_bottle");
    public static final ItemDefinition TOFFEE = food(EDLModule.SWEETS, "toffee", "Toffee", 1, 0.6F, "butter", "roasted_peanuts", REQUIRES_CHILLER, REQUIRES_TRAY);
    public static final ItemDefinition PEPPERMINT_BARK = effectFood(EDLModule.SWEETS, "peppermint_bark", "Peppermint Bark", 2, 0.1F, COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F, "mint_candy_red", "cooking_oil", REQUIRES_CHILLER, REQUIRES_TRAY);
    public static final ItemDefinition ROCKY_ROAD = food(EDLModule.SWEETS, "rocky_road", "Rocky Road", 3, 0.4F, "gummies", "marshmallow", REQUIRES_CHILLER, REQUIRES_TRAY);
    public static final ItemDefinition ETON_MESS = food(EDLModule.SWEETS, "eton_mess", "Eton Mess", 8, 0.6F, "meringue", "whipped_cream", "lemon_zest", "orange", REQUIRES_MIXING_BOWL);
    public static final ItemDefinition JAFFA_CAKE = food(EDLModule.BAKING, "jaffa_cake", "Jaffa Cake", 3, 0.6F, "dark_chocolate_syrup_bottle", "dynamic_jam", "orange_zest", "butter", REQUIRES_OVEN, REQUIRES_MUFFIN_TIN);
    public static final ItemDefinition PEANUT_BUTTER_JELLY = effectFood(
        EDLModule.MEALS,
        "peanut_butter_jelly",
        "PBJ",
        8,
        0.6F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(NOURISHMENT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        },
        "bread_slice",
        "peanut_butter_bottle",
        "dynamic_jam"
    );
    public static final ItemDefinition BAD_FOOD = effectFood(EDLModule.CORE, "bad_food", "Bad Food", 4, 0.1F, new ResourceLocation("minecraft", "nausea"), SHORT_EFFECT_DURATION, 0, 0.8F);
    public static final ItemDefinition CHICKEN_FRIED_STEAK = effectFood(
        EDLModule.MEALS,
        "chicken_fried_steak",
        "Chicken Fried Steak",
        17,
        0.8F,
        COMFORT,
        MEDIUM_EFFECT_DURATION,
        0,
        1.0F,
        "breading_misanplas",
        "gravy_boat_item"
    );
    public static final ItemDefinition DIRT_CAKE = containerEffectFood(
        EDLModule.SWEETS,
        "dirt_cake",
        "Dirt Cake",
        10,
        0.6F,
        Items.GLASS_BOTTLE,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "chocolate_custard",
        "gummies"
    );
    public static final ItemDefinition DARK_CHOCOLATE_BAR = chocolateBar("dark_chocolate_bar", "Dark Chocolate Bar", "cocoa_solids", "cocoa_butter_bottle");
    public static final ItemDefinition MILK_CHOCOLATE_BAR = chocolateBar("milk_chocolate_bar", "Milk Chocolate Bar", "cocoa_powder", "cocoa_butter_bottle");
    public static final ItemDefinition WHITE_CHOCOLATE_BAR = chocolateBar("white_chocolate_bar", "White Chocolate Bar", "cocoa_butter_bottle");
    public static final ItemDefinition BLOOD_CHOCOLATE_BAR = chocolateBar("blood_chocolate_bar", "Blood Chocolate Bar", "blood_chocolate_syrup_bottle");
    public static final ItemDefinition DARK_CHOCOLATE_FILLED_BAR = filledChocolateBar("dark_chocolate_filled_bar", "Dark Chocolate Filled Bar", "dark_chocolate_bar");
    public static final ItemDefinition MILK_CHOCOLATE_FILLED_BAR = filledChocolateBar("milk_chocolate_filled_bar", "Milk Chocolate Filled Bar", "milk_chocolate_bar");
    public static final ItemDefinition WHITE_CHOCOLATE_FILLED_BAR = filledChocolateBar("white_chocolate_filled_bar", "White Chocolate Filled Bar", "white_chocolate_bar");
    public static final ItemDefinition BLOOD_CHOCOLATE_FILLED_BAR = filledChocolateBar("blood_chocolate_filled_bar", "Blood Chocolate Filled Bar", "blood_chocolate_bar");
    public static final ItemDefinition DARK_CHOCOLATE_CHIPS = chocolateChips("dark_chocolate_chips", "Dark Chocolate Chips", "dark_chocolate_bar");
    public static final ItemDefinition MILK_CHOCOLATE_CHIPS = chocolateChips("milk_chocolate_chips", "Milk Chocolate Chips", "milk_chocolate_bar");
    public static final ItemDefinition WHITE_CHOCOLATE_CHIPS = chocolateChips("white_chocolate_chips", "White Chocolate Chips", "white_chocolate_bar");
    public static final ItemDefinition BLOOD_CHOCOLATE_CHIPS = chocolateChips("blood_chocolate_chips", "Blood Chocolate Chips", "blood_chocolate_bar");
    public static final ItemDefinition DARK_CHOCOLATE_TRUFFLE = chocolateTruffle("dark_chocolate_truffle", "Dark Chocolate Truffle", "dark_chocolate_bar");
    public static final ItemDefinition MILK_CHOCOLATE_TRUFFLE = chocolateTruffle("milk_chocolate_truffle", "Milk Chocolate Truffle", "milk_chocolate_bar");
    public static final ItemDefinition WHITE_CHOCOLATE_TRUFFLE = chocolateTruffle("white_chocolate_truffle", "White Chocolate Truffle", "white_chocolate_bar");
    public static final ItemDefinition BLOOD_CHOCOLATE_TRUFFLE = chocolateTruffle("blood_chocolate_truffle", "Blood Chocolate Truffle", "blood_chocolate_bar");
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_APPLE_SLICE = dippedChocolateFood(
        "dark_chocolate_dipped_apple_slice",
        "Dark Chocolate-Dipped Apple Slice",
        4,
        0.3F,
        "dark_chocolate_bar",
        "sliced_apple"
    );
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_APPLE_SLICE = dippedChocolateFood(
        "milk_chocolate_dipped_apple_slice",
        "Milk Chocolate-Dipped Apple Slice",
        4,
        0.3F,
        "milk_chocolate_bar",
        "sliced_apple"
    );
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_APPLE_SLICE = dippedChocolateFood(
        "white_chocolate_dipped_apple_slice",
        "White Chocolate-Dipped Apple Slice",
        4,
        0.3F,
        "white_chocolate_bar",
        "sliced_apple"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_APPLE_SLICE = dippedChocolateFood(
        "blood_chocolate_dipped_apple_slice",
        "Blood Chocolate-Dipped Apple Slice",
        4,
        0.3F,
        "blood_chocolate_bar",
        "sliced_apple"
    );
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_MARSHMALLOW = dippedChocolateFood(
        "dark_chocolate_dipped_marshmallow",
        "Dark Chocolate-Dipped Marshmallow",
        1,
        0.4F,
        "dark_chocolate_bar",
        "marshmallow"
    );
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_MARSHMALLOW = dippedChocolateFood(
        "milk_chocolate_dipped_marshmallow",
        "Milk Chocolate-Dipped Marshmallow",
        1,
        0.4F,
        "milk_chocolate_bar",
        "marshmallow"
    );
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_MARSHMALLOW = dippedChocolateFood(
        "white_chocolate_dipped_marshmallow",
        "White Chocolate-Dipped Marshmallow",
        1,
        0.4F,
        "white_chocolate_bar",
        "marshmallow"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_MARSHMALLOW = dippedChocolateFood(
        "blood_chocolate_dipped_marshmallow",
        "Blood Chocolate-Dipped Marshmallow",
        1,
        0.4F,
        "blood_chocolate_bar",
        "marshmallow"
    );
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_GRAHAM_CRACKER = dippedChocolateFood(
        "dark_chocolate_dipped_graham_cracker",
        "Dark Chocolate-Dipped Graham Cracker",
        2,
        0.4F,
        "dark_chocolate_bar",
        "graham_cracker"
    );
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_GRAHAM_CRACKER = dippedChocolateFood(
        "milk_chocolate_dipped_graham_cracker",
        "Milk Chocolate-Dipped Graham Cracker",
        2,
        0.4F,
        "milk_chocolate_bar",
        "graham_cracker"
    );
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_GRAHAM_CRACKER = dippedChocolateFood(
        "white_chocolate_dipped_graham_cracker",
        "White Chocolate-Dipped Graham Cracker",
        2,
        0.4F,
        "white_chocolate_bar",
        "graham_cracker"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_GRAHAM_CRACKER = dippedChocolateFood(
        "blood_chocolate_dipped_graham_cracker",
        "Blood Chocolate-Dipped Graham Cracker",
        2,
        0.4F,
        "blood_chocolate_bar",
        "graham_cracker"
    );
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_COFFEE_BEAN = dippedChocolateFood(
        "dark_chocolate_dipped_coffee_bean",
        "Dark Chocolate-Dipped Coffee Bean",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "dark_chocolate_bar",
        "coffee_beans"
    );
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_COFFEE_BEAN = dippedChocolateFood(
        "milk_chocolate_dipped_coffee_bean",
        "Milk Chocolate-Dipped Coffee Bean",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "milk_chocolate_bar",
        "coffee_beans"
    );
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_COFFEE_BEAN = dippedChocolateFood(
        "white_chocolate_dipped_coffee_bean",
        "White Chocolate-Dipped Coffee Bean",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "white_chocolate_bar",
        "coffee_beans"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_COFFEE_BEAN = dippedChocolateFood(
        "blood_chocolate_dipped_coffee_bean",
        "Blood Chocolate-Dipped Coffee Bean",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)
        },
        "blood_chocolate_bar",
        "coffee_beans"
    );
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_SWEET_BERRY = dippedChocolateFood("dark_chocolate_dipped_sweet_berry", "Dark Chocolate-Dipped Sweet Berry", 2, 0.1F, "dark_chocolate_bar");
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_GLOW_BERRY = dippedChocolateFood(
        "dark_chocolate_dipped_glow_berry",
        "Dark Chocolate-Dipped Glow Berry",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(GLOWING, SHORT_EFFECT_DURATION, 0, 1.0F)
        },
        "dark_chocolate_bar"
    );
    public static final ItemDefinition DARK_CHOCOLATE_DIPPED_BACON = dippedChocolateFood("dark_chocolate_dipped_bacon", "Dark Chocolate-Dipped Cooked Bacon", 2, 0.3F, "dark_chocolate_bar");
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_SWEET_BERRY = dippedChocolateFood("milk_chocolate_dipped_sweet_berry", "Milk Chocolate-Dipped Sweet Berry", 2, 0.1F, "milk_chocolate_bar");
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_GLOW_BERRY = dippedChocolateFood(
        "milk_chocolate_dipped_glow_berry",
        "Milk Chocolate-Dipped Glow Berry",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(GLOWING, SHORT_EFFECT_DURATION, 0, 1.0F)
        },
        "milk_chocolate_bar"
    );
    public static final ItemDefinition MILK_CHOCOLATE_DIPPED_BACON = dippedChocolateFood("milk_chocolate_dipped_bacon", "Milk Chocolate-Dipped Cooked Bacon", 2, 0.3F, "milk_chocolate_bar");
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_SWEET_BERRY = dippedChocolateFood("white_chocolate_dipped_sweet_berry", "White Chocolate-Dipped Sweet Berry", 2, 0.1F, "white_chocolate_bar");
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_GLOW_BERRY = dippedChocolateFood(
        "white_chocolate_dipped_glow_berry",
        "White Chocolate-Dipped Glow Berry",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(GLOWING, SHORT_EFFECT_DURATION, 0, 1.0F)
        },
        "white_chocolate_bar"
    );
    public static final ItemDefinition WHITE_CHOCOLATE_DIPPED_BACON = dippedChocolateFood("white_chocolate_dipped_bacon", "White Chocolate-Dipped Cooked Bacon", 2, 0.3F, "white_chocolate_bar");
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_SWEET_BERRY = dippedChocolateFood("blood_chocolate_dipped_sweet_berry", "Blood Chocolate-Dipped Sweet Berry", 2, 0.1F, "blood_chocolate_bar");
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_GLOW_BERRY = dippedChocolateFood(
        "blood_chocolate_dipped_glow_berry",
        "Blood Chocolate-Dipped Glow Berry",
        2,
        0.1F,
        new FoodEffectEntry[]{
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(GLOWING, SHORT_EFFECT_DURATION, 0, 1.0F)
        },
        "blood_chocolate_bar"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_DIPPED_BACON = dippedChocolateFood("blood_chocolate_dipped_bacon", "Blood Chocolate-Dipped Cooked Bacon", 2, 0.3F, "blood_chocolate_bar");
    public static final ItemDefinition CANDY_BLACK = candy(
        "candy_black",
        "Black Candy"
    );
    public static final ItemDefinition CANDY_BLUE = candy(
        "candy_blue",
        "Blue Candy"
    );
    public static final ItemDefinition CANDY_BROWN = candy(
        "candy_brown",
        "Brown Candy"
    );
    public static final ItemDefinition CANDY_CYAN = candy(
        "candy_cyan",
        "Cyan Candy"
    );
    public static final ItemDefinition CANDY_GRAY = candy(
        "candy_gray",
        "Gray Candy"
    );
    public static final ItemDefinition CANDY_GREEN = candy(
        "candy_green",
        "Green Candy"
    );
    public static final ItemDefinition CANDY_LIGHT_BLUE = candy(
        "candy_light_blue",
        "Light Blue Candy"
    );
    public static final ItemDefinition CANDY_LIGHT_GRAY = candy(
        "candy_light_gray",
        "Light Gray Candy"
    );
    public static final ItemDefinition CANDY_LIME = candy(
        "candy_lime",
        "Lime Candy"
    );
    public static final ItemDefinition CANDY_MAGENTA = candy(
        "candy_magenta",
        "Magenta Candy"
    );
    public static final ItemDefinition CANDY_ORANGE = candy(
        "candy_orange",
        "Orange Candy"
    );
    public static final ItemDefinition CANDY_PINK = candy(
        "candy_pink",
        "Pink Candy"
    );
    public static final ItemDefinition CANDY_PURPLE = candy(
        "candy_purple",
        "Purple Candy"
    );
    public static final ItemDefinition CANDY_RED = candy(
        "candy_red",
        "Red Candy"
    );
    public static final ItemDefinition CANDY_WHITE = candy(
        "candy_white",
        "White Candy"
    );
    public static final ItemDefinition CANDY_YELLOW = candy(
        "candy_yellow",
        "Yellow Candy"
    );
    public static final ItemDefinition MINT_CANDY_BLUE = alwaysEdibleFood(
        EDLModule.SWEETS,
        "mint_candy_blue",
        "Wintergreen Candy",
        0,
        0.1F,
        "mint"
    );
    public static final ItemDefinition MINT_CANDY_GREEN = alwaysEdibleFood(
        EDLModule.SWEETS,
        "mint_candy_green",
        "Spearmint Candy",
        0,
        0.1F,
        "mint"
    );
    public static final ItemDefinition MINT_CANDY_RED = alwaysEdibleFood(
        EDLModule.SWEETS,
        "mint_candy_red",
        "Peppermint Candy",
        0,
        0.1F,
        "mint"
    );
    public static final ItemDefinition CANDY_CANE_BLUE = candy(
        "candy_cane_blue",
        "Wintergreen Candy Cane",
        "mint_candy_blue"
    );
    public static final ItemDefinition CANDY_CANE_GREEN = candy(
        "candy_cane_green",
        "Spearmint Candy Cane",
        "mint_candy_green"
    );
    public static final ItemDefinition CANDY_CANE_RED = candy(
        "candy_cane_red",
        "Peppermint Candy Cane",
        "mint_candy_red"
    );
    public static final ItemDefinition CANDY_APPLE = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "candy_apple",
        "Candy Apple",
        4,
        0.3F,
        MOVEMENT_SPEED,
        BRIEF_EFFECT_DURATION,
        0,
        1.0F
    );
    public static final ItemDefinition CANDY_GOLDEN_APPLE = alwaysEdibleEffectFood(
        EDLModule.SWEETS,
        "candy_golden_apple",
        "Candy Golden Apple",
        4,
        1.2F,
        new FoodEffectEntry[]{new FoodEffectEntry(REGENERATION, 100, 1, 1.0F),
            new FoodEffectEntry(ABSORPTION, 2400, 0, 1.0F),
            new FoodEffectEntry(MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F)}
    );
    public static final ItemDefinition CANDIED_GINGER = alwaysEdibleFood(
        EDLModule.SWEETS,
        "candied_ginger",
        "Candied Ginger",
        0,
        0.1F
    );
    public static final ItemDefinition CANDIED_CITRUS_ZEST = effectFood(
        EDLModule.SWEETS,
        "candied_citrus_zest",
        "Candied Citrus Zest",
        1,
        0.2F,
        SUNSHINE,
        LONG_EFFECT_DURATION,
        0,
        1.0F,
        "lemon_zest",
        "lime_zest",
        "orange_zest"
    );
    public static final ItemDefinition FLOUR = item(
        EDLModule.CORE,
        "flour",
        "Flour",
        "mortar_stone",
        "pestle_stone"
    );
    public static final ItemDefinition CORN_MEAL = item(
        EDLModule.CORE,
        "corn_meal",
        "Cornmeal",
        () -> new ItemFoodBlock(EDLBlocks.CORNMEAL.getBlock(), 1, 0.6F),
        "mortar_stone",
        "pestle_stone",
        "corn_seeds"
    );
    public static final ItemDefinition CHILI_POWDER = item(
        EDLModule.CORE,
        "chili_powder",
        "Chili Powder",
        "mortar_stone",
        "pestle_stone",
        "dried_chili"
    );
    public static final ItemDefinition MALLOW_POWDER = item(
        EDLModule.CORE,
        "mallow_powder",
        "Mallow Root Powder",
        "mortar_stone",
        "pestle_stone",
        "mallow_root"
    );
    public static final ItemDefinition PEANUT_BUTTER_BOTTLE = containerFood(
        EDLModule.CORE,
        "peanut_butter_bottle",
        "Nut Butter",
        3,
        0.1F,
        Items.GLASS_BOTTLE,
        "nut_butter_fluid"
    );
    public static final ItemDefinition HAZELNUT_SPREAD_BOTTLE = containerFood(
        EDLModule.CORE,
        "hazelnut_spread_bottle",
        "Chocolate Nut Butter Spread",
        2,
        0.1F,
        Items.GLASS_BOTTLE,
        "cocoa_nut_butter_spread_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition ROASTED_COCOA_BEANS = food(
        EDLModule.CORE,
        "roasted_cocoa_beans",
        "Roasted Cocoa Beans",
        2,
        0.1F
    );
    public static final ItemDefinition COCOA_SOLIDS = item(
        EDLModule.CORE,
        "cocoa_solids",
        "Cocoa Solids",
        "mortar_stone",
        "pestle_stone",
        "roasted_cocoa_beans"
    );
    public static final ItemDefinition COCOA_POWDER = item(
        EDLModule.CORE,
        "cocoa_powder",
        "Cocoa Powder",
        "mortar_stone",
        "pestle_stone",
        "cocoa_solids"
    );
    public static final ItemDefinition COCOA_BUTTER_BOTTLE = containerItem(
        EDLModule.CORE,
        "cocoa_butter_bottle",
        "Cocoa Butter Bottle",
        Items.GLASS_BOTTLE,
        "cocoa_butter_fluid"
    );
    public static final ItemDefinition DARK_CHOCOLATE_SYRUP_BOTTLE = containerItem(
        EDLModule.SWEETS,
        "dark_chocolate_syrup_bottle",
        "Liquid Dark Chocolate",
        Items.GLASS_BOTTLE,
        "dark_chocolate_syrup_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition MILK_CHOCOLATE_SYRUP_BOTTLE = containerItem(
        EDLModule.SWEETS,
        "milk_chocolate_syrup_bottle",
        "Liquid Milk Chocolate",
        Items.GLASS_BOTTLE,
        "milk_chocolate_syrup_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition WHITE_CHOCOLATE_SYRUP_BOTTLE = containerItem(
        EDLModule.SWEETS,
        "white_chocolate_syrup_bottle",
        "Liquid White Chocolate",
        Items.GLASS_BOTTLE,
        "white_chocolate_syrup_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition BLOOD = containerItem(
        EDLModule.MEAT,
        "blood_fluid_bottle",
        "Bottle of Blood",
        Items.GLASS_BOTTLE,
        "blood_fluid"
    );
    public static final ItemDefinition BLOOD_CHOCOLATE_SYRUP_BOTTLE = containerItem(
        EDLModule.SWEETS,
        "blood_chocolate_syrup_bottle",
        "Liquid Blood Chocolate",
        Items.GLASS_BOTTLE,
        "cocoa_solids",
        "cocoa_butter_bottle",
        "blood_fluid",
        "blood_chocolate_syrup_fluid",
        REQUIRES_MIXING_BOWL
    );
    public static final ItemDefinition MARSHMALLOW_FLUFF_BOTTLE = containerFood(
        EDLModule.SWEETS,
        "marshmallow_fluff_bottle",
        "Marshmallow Fluff",
        2,
        0.4F,
        Items.GLASS_BOTTLE,
        "marshmallow_fluff_fluid",
        REQUIRES_MELTING_POT
    );
    public static final ItemDefinition DYNAMIC_JAM = dynamicJam();
    public static final ItemDefinition JAM = hiddenItem("jam", "Jam",
        () -> new ItemDeprecatedJam(ItemDynamicJam.Flavor.SWEET_BERRIES));
    public static final ItemDefinition GLOW_BERRY_JAM = hiddenItem("glow_berry_jam", "Glow Berry Jam",
        () -> new ItemDeprecatedJam(ItemDynamicJam.Flavor.GLOW_BERRIES));
    public static final ItemDefinition GOLDEN_APPLE_JAM = hiddenItem("golden_apple_jam", "Golden Apple Jam",
        () -> new ItemDeprecatedJam(ItemDynamicJam.Flavor.GOLDEN_APPLE));
    public static final ItemDefinition MINT_JELLY = hiddenItem("mint_jelly", "Mint Jelly",
        () -> new ItemDeprecatedJam(ItemDynamicJam.Flavor.MINT));
    public static final ItemDefinition EASTER_EGG = hiddenFood(
        "easter_egg",
        "Easter Egg",
        3,
        0.3F
    );
    public static final ItemDefinition DYNAMIC_TOAST = dynamicToast();
    public static final ItemDefinition SWEET_BERRY_JUICE = containerFood(
        EDLModule.CORE,
        "sweet_berry_juice",
        "Sweet Berry Juice",
        1,
        0.1F,
        Items.GLASS_BOTTLE,
        "sweet_berry_juice_fluid",
        REQUIRES_JUICER
    );
    public static final ItemDefinition GLOW_BERRY_JUICE = define(new ItemDefinition(
        EDLModule.CORE,
        "glow_berry_juice",
        "Glow Berry Juice",
        () -> new ItemGlowBerryJuice(1, 0.1F, Items.GLASS_BOTTLE),
        "glow_berry_juice_fluid",
        REQUIRES_JUICER
    ));
    public static final ItemDefinition TOMATO_JUICE = containerFood(
        EDLModule.CORE,
        "tomato_juice",
        "Tomato Juice",
        1,
        0.1F,
        Items.GLASS_BOTTLE,
        "tomato_juice_fluid",
        REQUIRES_JUICER
    );
    public static final ItemDefinition CACTUS_JUICE = define(new ItemDefinition(
        EDLModule.CORE,
        "cactus_juice",
        "Cactus Juice",
        () -> new ItemCactusJuice(1, 0.1F, Items.GLASS_BOTTLE),
        "cactus_juice_fluid",
        REQUIRES_JUICER
    ));
    public static final ItemDefinition MELON_JUICE = containerFood(
        EDLModule.CORE,
        "melon_juice",
        "Melon Juice",
        1,
        0.1F,
        Items.GLASS_BOTTLE,
        "melon_juice_fluid",
        REQUIRES_JUICER
    );
    public static final ItemDefinition PICKLE_JUICE = containerFood(
        EDLModule.CORE,
        "pickle_juice",
        "Pickle Juice",
        1,
        0.1F,
        Items.GLASS_BOTTLE,
        "pickle_juice_fluid",
        REQUIRES_JUICER
    );
    public static final ItemDefinition GHERKIN_ITEM = food(
        EDLModule.PICKLING,
        "gherkin_item",
        "Pickled Cucumber",
        3,
        0.6F,
        REQUIRES_VAT,
        "cucumber",
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_BEET_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_beet_item",
        "Pickled Sliced Beetroot",
        5,
        0.6F,
        PICKLED,
        LONG_EFFECT_DURATION,
        0,
        1.0F,
        REQUIRES_VAT,
        "sliced_beetroot_item",
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_CARROT_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_carrot_item",
        "Pickled Carrot",
        7,
        0.6F,
        PICKLED,
        LONG_EFFECT_DURATION,
        0,
        1.0F,
        REQUIRES_VAT,
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_EGG_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_egg_item",
        "Pickled Egg",
        4,
        0.4F,
        PICKLED,
        LONG_EFFECT_DURATION,
        1,
        1.0F,
        REQUIRES_VAT,
        "boiled_egg",
        "vinegar"
    );
    public static final ItemDefinition PICKLED_FISH_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_fish_item",
        "Pickled Fish",
        6,
        0.1F,
        PICKLED,
        LONG_EFFECT_DURATION,
        1,
        1.0F,
        REQUIRES_VAT,
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_SAUSAGE_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_sausage_item",
        "Pickled Sausage",
        6,
        0.8F,
        PICKLED,
        LONG_EFFECT_DURATION,
        1,
        1.0F,
        REQUIRES_VAT,
        "cooked_sausage",
        "vinegar"
    );
    public static final ItemDefinition PICKLED_GINGER = alwaysEdibleEffectFood(
        EDLModule.PICKLING,
        "pickled_ginger",
        "Pickled Ginger",
        1,
        0.1F,
        PICKLED,
        LONG_EFFECT_DURATION,
        0,
        1.0F,
        REQUIRES_VAT,
        "sliced_ginger",
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_ONION_ITEM = effectFood(
        EDLModule.PICKLING,
        "pickled_onion_item",
        "Pickled Onion",
        6,
        0.4F,
        PICKLED,
        LONG_EFFECT_DURATION,
        0,
        1.0F,
        REQUIRES_VAT,
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PICKLED_RIND_ITEM = food(
        EDLModule.PICKLING,
        "pickled_rind_item",
        "Pickled Melon Rind",
        2,
        0.0F,
        REQUIRES_VAT,
        "melon_rind",
        "vinegar",
        "salt"
    );
    public static final ItemDefinition PRESERVED_LEMON_ITEM = effectFood(
        EDLModule.PICKLING,
        "preserved_lemon_item",
        "Preserved Lemon",
        2,
        0.2F,
        SUNSHINE,
        LONG_EFFECT_DURATION,
        2,
        1.0F,
        REQUIRES_VAT,
        "lemon",
        "lemon_juice",
        "salt"
    );
    public static final ItemDefinition FISH_SAUCE = containerFood(
        EDLModule.PICKLING,
        "fish_sauce_item",
        "Fish Sauce",
        2,
        0.1F,
        Items.GLASS_BOTTLE,
        REQUIRES_VAT,
        "salt"
    );
    public static final ItemDefinition SLICED_GHERKIN_ITEM = food(
        EDLModule.PICKLING,
        "sliced_gherkin_item",
        "Sliced Pickled Cucumber",
        3,
        0.6F,
        "gherkin_item"
    );
    public static final ItemDefinition APPLE_CIDER = containerFood(
        EDLModule.CORE,
        "apple_cider",
        "Apple Cider",
        1,
        0.1F,
        Items.GLASS_BOTTLE,
        "apple_cider_fluid",
        REQUIRES_JUICER
    );
    public static final ItemDefinition LEMON_JUICE = sourJuice("lemon_juice", "Lemon Juice", "lemon_juice_fluid", 3, 100);
    public static final ItemDefinition LIME_JUICE = sourJuice("lime_juice", "Lime Juice", "lime_juice_fluid", 4, 100);
    public static final ItemDefinition ORANGE_JUICE = sourJuice("orange_juice", "Orange Juice", "orange_juice_fluid", 1, 50);
    public static final ItemDefinition GRAPEFRUIT_JUICE = sourJuice("grapefruit_juice", "Grapefruit Juice", "grapefruit_juice_fluid", 1, 50);

    private EDLItems() {
    }

    public static void ensureLoaded() {
        // Intentionally empty: calling this method forces static item definitions to exist.
    }

    public static void register(IForgeRegistry<Item> registry) {
        boolean goatEntityAvailable = hasGoatEntityProvider();
        boolean registeredInPass;
        do {
            registeredInPass = false;
            for (ItemDefinition definition : DEFINITIONS) {
                if (definition.isRegistered()
                    || !definition.entry.canRegister()
                    || !definition.hasRequiredExternalItems()
                    || (definition.requiresGoatEntity() && !goatEntityAvailable)) {
                    continue;
                }

                Item item = definition.createItem();
                item.setRegistryName(ExtraDelightLegacy.MODID, definition.id);
                item.setUnlocalizedName(ExtraDelightLegacy.MODID + "." + definition.id);
                item.setCreativeTab(EDLCreativeTabs.getTab(definition.module));

                registry.register(item);
                definition.item = item;
                definition.entry.markRegistered();
                REGISTERED_ITEMS.add(item);
                if (definition.hiddenFromCreative()) {
                    // Source hides migration items and easter egg from normal tabs/JEI.
                } else if (item instanceof ItemDynamicJam) {
                    for (ItemDynamicJam.Flavor flavor : ItemDynamicJam.Flavor.values()) {
                        EDLContentRegistry.addCreativeStack(definition.module, () -> ItemDynamicJam.stack(flavor, 1));
                    }
                } else if (item instanceof ItemDynamicToast) {
                    for (ItemDynamicToast.Topping topping : ItemDynamicToast.Topping.values()) {
                        EDLContentRegistry.addCreativeStack(definition.module, () -> ItemDynamicToast.stack(topping, 1));
                    }
                } else {
                    EDLContentRegistry.addCreativeStack(definition.module, () -> new ItemStack(item));
                }
                registeredInPass = true;
            }
        } while (registeredInPass);
    }

    public static Item getItem(String id) {
        ItemDefinition definition = DEFINITIONS_BY_ID.get(id);
        return definition == null ? null : definition.getItem();
    }

    public static boolean isRegistered(String id) {
        ItemDefinition definition = DEFINITIONS_BY_ID.get(id);
        return definition != null && definition.isRegistered();
    }

    public static boolean hasGoatEntityProvider() {
        return firstGoatEntityId() != null || hasKnownGoatBackportMod();
    }

    public static ResourceLocation firstGoatEntityId() {
        for (ResourceLocation id : ForgeRegistries.ENTITIES.getKeys()) {
            if (isGoatEntityId(id)) {
                return id;
            }
        }
        return null;
    }

    public static boolean isGoatEntityId(ResourceLocation id) {
        return id != null && id.toString().toLowerCase(java.util.Locale.ROOT).contains("goat");
    }

    private static boolean hasKnownGoatBackportMod() {
        return Loader.isModLoaded("betteranimalsplus")
            || Loader.isModLoaded("animania")
            || Loader.isModLoaded("mocreatures")
            || Loader.isModLoaded("goatmod")
            || Loader.isModLoaded("alexsmobs");
    }

    public static List<Item> getRegisteredItems() {
        return Collections.unmodifiableList(REGISTERED_ITEMS);
    }

    private static ItemDefinition item(EDLModule module, String id, String configName, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, Item::new, dependencies));
    }

    private static ItemDefinition item(EDLModule module, String id, String configName, Supplier<Item> factory,
                                       String... dependencies) {
        return define(new ItemDefinition(module, id, configName, factory, dependencies));
    }

    private static ItemDefinition hiddenItem(String id, String configName, Supplier<Item> factory, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName, factory, dependencies).hideFromCreative());
    }

    private static ItemDefinition blockPlacingItem(EDLModule module, String id, String configName,
                                                   Supplier<net.minecraft.block.Block> blockSupplier,
                                                   String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemBlock(blockSupplier.get()), dependencies));
    }

    private static ItemDefinition containerItem(EDLModule module, String id, String configName, Item container, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemCraftingContainer(container), dependencies));
    }

    private static ItemDefinition fuelItem(EDLModule module, String id, String configName, int burnTime, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemFuel(burnTime), dependencies));
    }

    private static ItemDefinition tool(EDLModule module, String id, String configName, int durability, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemDamageableTool(durability), dependencies));
    }

    private static ItemDefinition offsetSpatula(EDLModule module, String id, String configName, int durability, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemOffsetSpatula(durability), dependencies));
    }

    private static ItemDefinition externalTool(EDLModule module, String id, String configName, int durability, String externalItem, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemDamageableTool(durability), dependencies).requireExternalItem(externalItem));
    }

    private static ItemDefinition seed(EDLModule module, String id, String configName, Supplier<net.minecraft.block.Block> cropSupplier, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemSeeds(cropSupplier.get(), Blocks.FARMLAND), dependencies));
    }

    private static ItemDefinition shuckableCorn(EDLModule module, String id, String configName, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, ItemShuckableCorn::new, dependencies));
    }

    private static ItemDefinition shuckableOutput(EDLModule module, String id, String configName,
                                                  Supplier<ItemStack[]> outputSupplier, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemShuckableOutput(outputSupplier), dependencies));
    }

    private static ItemDefinition plantableFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                Supplier<net.minecraft.block.Block> cropSupplier, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemPlantableAddonFood(healAmount, saturation, cropSupplier), dependencies));
    }

    private static ItemDefinition groundPlantableFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                      Supplier<net.minecraft.block.Block> blockSupplier, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemGroundPlantableFood(healAmount, saturation, blockSupplier), dependencies));
    }

    private static ItemDefinition groundPlantableFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                      Supplier<net.minecraft.block.Block> blockSupplier,
                                                      FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            return new ItemGroundPlantableFood(healAmount, saturation, blockSupplier, entries);
        }, dependencies));
    }

    private static ItemDefinition plantableItem(EDLModule module, String id, String configName,
                                                Supplier<net.minecraft.block.Block> blockSupplier,
                                                ItemPlantableBlock.SoilMode soilMode,
                                                String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemPlantableBlock(blockSupplier, soilMode), dependencies));
    }

    private static ItemDefinition food(EDLModule module, String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new AddonFoodItem(healAmount, saturation, false), dependencies));
    }

    private static ItemDefinition hiddenFood(String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName, () -> new AddonFoodItem(healAmount, saturation, false), dependencies).hideFromCreative());
    }

    private static ItemDefinition butcheryFood(String id, String configName, int healAmount, float saturation) {
        return food(EDLModule.MEAT, id, configName, healAmount, saturation);
    }

    private static ItemDefinition butcheryItem(String id, String configName) {
        return item(EDLModule.MEAT, id, configName);
    }

    private static ItemDefinition rawChickenButcheryFood(String id, String configName, int healAmount, float saturation) {
        return effectFood(EDLModule.MEAT, id, configName, healAmount, saturation, HUNGER, 600, 0, 0.3F);
    }

    private static ItemDefinition rawOffalFood(String id, String configName, int healAmount, float saturation) {
        return effectFood(EDLModule.MEAT, id, configName, healAmount, saturation, HUNGER, 600, 0, 0.6F);
    }

    private static ItemDefinition rawSausageFood(String id, String configName) {
        return effectFood(EDLModule.MEAT, id, configName, 3, 0.1F, HUNGER, 600, 0, 0.3F);
    }

    private static ItemDefinition glowBerryFood(EDLModule module, String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new AddonFoodItem(healAmount, saturation, false, GLOWING, 100, 0, 1.0F), dependencies));
    }

    private static ItemDefinition alwaysEdibleFood(EDLModule module, String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            AddonFoodItem item = new AddonFoodItem(healAmount, saturation, false);
            item.setAlwaysEdible();
            return item;
        }, dependencies));
    }

    private static ItemDefinition containerFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                Item container, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemContainerFood(healAmount, saturation, container), dependencies));
    }

    private static ItemDefinition dynamicJam() {
        return define(new ItemDefinition(EDLModule.SWEETS, "dynamic_jam", "Dynamic Jam", ItemDynamicJam::new));
    }

    private static ItemDefinition dynamicToast() {
        return define(new ItemDefinition(
            EDLModule.CORE,
            "dynamic_toast",
            "Dynamic Toast",
            ItemDynamicToast::new,
            "toast",
            "dynamic_jam",
            "lemon_curd",
            "peanut_butter_bottle",
            "hazelnut_spread_bottle",
            "marshmallow_fluff_bottle",
            "yeast_spread",
            "butter"
        ));
    }

    private static ItemDefinition alwaysEdibleContainerFood(EDLModule module, String id, String configName, int healAmount,
                                                            float saturation, Item container, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            ItemContainerFood item = new ItemContainerFood(healAmount, saturation, container);
            item.setAlwaysEdible();
            return item;
        }, dependencies));
    }

    private static ItemDefinition effectFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                             ResourceLocation effect, int duration, int amplifier, float chance, String... dependencies) {
        return define(new ItemDefinition(module, id, configName,
            () -> new AddonFoodItem(healAmount, saturation, false, effect, duration, amplifier, chance),
            dependencies
        ));
    }

    private static ItemDefinition effectFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                             FoodEffectEntry effect, String... dependencies) {
        return effectFood(module, id, configName, healAmount, saturation, new FoodEffectEntry[]{effect}, dependencies);
    }

    private static ItemDefinition effectFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                             FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            return new AddonFoodItem(healAmount, saturation, false, entries);
        }, dependencies));
    }

    private static ItemDefinition alwaysEdibleEffectFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                         ResourceLocation effect, int duration, int amplifier, float chance, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            AddonFoodItem item = new AddonFoodItem(healAmount, saturation, false, effect, duration, amplifier, chance);
            item.setAlwaysEdible();
            return item;
        }, dependencies));
    }

    private static ItemDefinition containerEffectFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                      Item container, FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            return new ItemContainerEffectFood(healAmount, saturation, container, entries);
        }, dependencies));
    }

    private static ItemDefinition xocolatl(EDLModule module, String id, String configName, int healAmount, float saturation,
                                           Item container, FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            return new ItemXocolatl(healAmount, saturation, entries);
        }, dependencies));
    }

    private static ItemDefinition drink(EDLModule module, String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new ItemMilkshake(healAmount), dependencies));
    }

    private static ItemDefinition sourJuice(String id, String configName, String fluidDependency, int amplifier, int duration) {
        return define(new ItemDefinition(
            EDLModule.CORE,
            id,
            configName,
            () -> new ItemSourJuice(1, 0.1F, Items.GLASS_BOTTLE, amplifier, duration),
            fluidDependency,
            REQUIRES_JUICER
        ));
    }

    private static ItemDefinition candy(String id, String configName, String... dependencies) {
        return alwaysEdibleEffectFood(EDLModule.SWEETS, id, configName, 0, 0.1F, MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F, dependencies);
    }

    private static ItemDefinition pieSlice(String id, String configName, String... dependencies) {
        return food(EDLModule.SWEETS, id, configName, 3, 0.4F, dependencies);
    }

    private static ItemDefinition glowBerryPieSlice(String id, String configName, String... dependencies) {
        return glowBerryFood(EDLModule.SWEETS, id, configName, 3, 0.4F, dependencies);
    }

    private static ItemDefinition frosting(String id, String configName) {
        String color = id.startsWith("frosting_") ? id.substring("frosting_".length()) : id;
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName,
            () -> new ItemFrosting(color, 0, 0.6F, MOVEMENT_SPEED, BRIEF_EFFECT_DURATION, 0, 1.0F),
            "butter",
            REQUIRES_MIXING_BOWL
        ));
    }

    private static ItemDefinition custard(String id, String configName, String... dependencies) {
        return alwaysEdibleContainerFood(EDLModule.SWEETS, id, configName, 7, 0.6F, Items.GLASS_BOTTLE, dependencies);
    }

    private static ItemDefinition popsicle(String id, String configName, String... dependencies) {
        return food(EDLModule.SWEETS, id, configName, 3, 0.3F, dependencies);
    }

    private static ItemDefinition jelly(String id, String configName) {
        return containerEffectFood(
            EDLModule.SWEETS,
            id,
            configName,
            1,
            0.3F,
            Items.BOWL,
            new FoodEffectEntry[]{new FoodEffectEntry(JUMP_BOOST, BRIEF_EFFECT_DURATION, 0, 0.5F)}
        );
    }

    private static ItemDefinition chocolateBar(String id, String configName, String... dependencies) {
        FoodEffectEntry[] effects = new FoodEffectEntry[]{
            new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        };
        return effectFood(EDLModule.SWEETS, id, configName, 4, 0.1F, effects, appendDependencies(dependencies, REQUIRES_CHILLER, REQUIRES_BAR_MOLD));
    }

    private static ItemDefinition filledChocolateBar(String id, String configName, String... dependencies) {
        FoodEffectEntry[] effects = new FoodEffectEntry[]{
            new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, MEDIUM_EFFECT_DURATION, 0, 1.0F)
        };
        return effectFood(EDLModule.SWEETS, id, configName, 6, 0.1F, effects, appendDependencies(dependencies, REQUIRES_CHILLER, REQUIRES_BAR_MOLD));
    }

    private static ItemDefinition chocolateChips(String id, String configName, String... dependencies) {
        FoodEffectEntry[] effects = new FoodEffectEntry[]{
            new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        };
        return effectFood(EDLModule.SWEETS, id, configName, 0, 0.1F, effects, appendDependencies(dependencies, REQUIRES_CHILLER));
    }

    private static ItemDefinition chocolateTruffle(String id, String configName, String... dependencies) {
        FoodEffectEntry[] effects = new FoodEffectEntry[]{
            new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F),
            new FoodEffectEntry(COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F)
        };
        return effectFood(EDLModule.SWEETS, id, configName, 0, 0.1F, effects, appendDependencies(dependencies, REQUIRES_CHILLER));
    }

    private static ItemDefinition dippedChocolateFood(String id, String configName, int healAmount, float saturation, String... dependencies) {
        return alwaysEdibleEffectFood(EDLModule.SWEETS, id, configName, healAmount, saturation, COMFORT, BRIEF_EFFECT_DURATION, 0, 1.0F, appendDependencies(dependencies, REQUIRES_MIXING_BOWL));
    }

    private static ItemDefinition dippedChocolateFood(String id, String configName, int healAmount, float saturation,
                                                      FoodEffectEntry[] effects, String... dependencies) {
        return alwaysEdibleEffectFood(EDLModule.SWEETS, id, configName, healAmount, saturation, effects, appendDependencies(dependencies, REQUIRES_MIXING_BOWL));
    }

    private static ItemDefinition coffeeDrink() {
        return define(new ItemDefinition(
            EDLModule.CORE,
            "coffee",
            "Coffee",
            () -> {
                List<FoodEffectEntry> entries = new ArrayList<>();
                entries.add(new FoodEffectEntry(MOVEMENT_SPEED, MEDIUM_EFFECT_DURATION, 0, 1.0F));
                return new ItemCoffeeDrink(1, 0.1F, entries);
            },
            "ground_coffee"
        ));
    }

    private static String[] appendDependencies(String[] base, String... extra) {
        String[] result = new String[base.length + extra.length];
        System.arraycopy(base, 0, result, 0, base.length);
        System.arraycopy(extra, 0, result, base.length, extra.length);
        return result;
    }

    private static ItemDefinition alwaysEdibleEffectFood(EDLModule module, String id, String configName, int healAmount,
                                                         float saturation, FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            AddonFoodItem item = new AddonFoodItem(healAmount, saturation, false, entries);
            item.setAlwaysEdible();
            return item;
        }, dependencies));
    }

    private static ItemDefinition bowlFood(EDLModule module, String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> new AddonBowlFoodItem(healAmount, saturation, false), dependencies));
    }

    private static ItemDefinition effectBowlFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                 FoodEffectEntry effect, String... dependencies) {
        return effectBowlFood(module, id, configName, healAmount, saturation, new FoodEffectEntry[]{effect}, dependencies);
    }

    private static ItemDefinition effectBowlFood(EDLModule module, String id, String configName, int healAmount, float saturation,
                                                 FoodEffectEntry[] effects, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> entries = new ArrayList<>();
            Collections.addAll(entries, effects);
            return new AddonBowlFoodItem(healAmount, saturation, false, entries);
        }, dependencies));
    }

    private static ItemDefinition alwaysEdibleEffectBowlFood(EDLModule module, String id, String configName, int healAmount,
                                                             float saturation, ResourceLocation effect, int duration,
                                                             int amplifier, float chance, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            AddonBowlFoodItem item = new AddonBowlFoodItem(healAmount, saturation, false, effect, duration, amplifier, chance);
            item.setAlwaysEdible();
            return item;
        }, dependencies));
    }

    private static ItemDefinition cerealFood(EDLModule module, String id, String configName, String... dependencies) {
        return define(new ItemDefinition(module, id, configName, () -> {
            List<FoodEffectEntry> effects = new ArrayList<>();
            effects.add(new FoodEffectEntry(REGENERATION, BRIEF_EFFECT_DURATION, 0, 1.0F));
            effects.add(new FoodEffectEntry(ABSORPTION, SHORT_EFFECT_DURATION, 0, 1.0F));
            return new AddonBowlFoodItem(8, 0.4F, false, effects);
        }, dependencies));
    }

    private static ItemDefinition iceCream(String id, String configName, int healAmount, float saturation, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName, () -> {
            return new AddonBowlFoodItem(healAmount, saturation, true);
        }, dependencies));
    }

    private static ItemDefinition glowBerryIceCream(String id, String configName, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName,
            () -> new ItemGlowBerryBowlFood(6, 0.6F, true), dependencies));
    }

    private static ItemDefinition milkshake(String id, String configName, float healAmount, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName, () -> new ItemMilkshake(healAmount), dependencies));
    }

    private static ItemDefinition glowBerryMilkshake(String id, String configName, String... dependencies) {
        return define(new ItemDefinition(EDLModule.SWEETS, id, configName, () -> new ItemGlowBerryMilkshake(4.0F), dependencies));
    }

    private static ItemDefinition define(ItemDefinition definition) {
        DEFINITIONS.add(definition);
        DEFINITIONS_BY_ID.put(definition.id, definition);
        return definition;
    }

    public static final class ItemDefinition {
        private final EDLModule module;
        private final String id;
        private final EDLContentEntry entry;
        private final Supplier<Item> factory;
        private final List<String> requiredExternalItems = new ArrayList<>();
        private boolean hiddenFromCreative;
        private Item item;

        private ItemDefinition(EDLModule module, String id, String configName, Supplier<Item> factory, String... dependencies) {
            this.module = module;
            this.id = id;
            this.entry = EDLContentRegistry.define(module, id, configName, dependencies);
            this.factory = factory;
        }

        public String getId() {
            return id;
        }

        public EDLModule getModule() {
            return module;
        }

        public boolean isRegistered() {
            return item != null;
        }

        public Item getItem() {
            return item;
        }

        public ItemStack stack(int count) {
            return item == null ? ItemStack.EMPTY : new ItemStack(item, count);
        }

        private ItemDefinition requireExternalItem(String itemId) {
            this.requiredExternalItems.add(itemId);
            return this;
        }

        private boolean hasRequiredExternalItems() {
            for (String itemId : requiredExternalItems) {
                if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)) == null) {
                    return false;
                }
            }
            return true;
        }

        private ItemDefinition hideFromCreative() {
            this.hiddenFromCreative = true;
            return this;
        }

        private boolean hiddenFromCreative() {
            return hiddenFromCreative;
        }

        private boolean requiresGoatEntity() {
            return id.equals("goat_chop")
                || id.equals("cooked_goat_chop")
                || id.equals("goat_ribs")
                || id.equals("cooked_goat_ribs")
                || id.equals("goat_roast")
                || id.equals("cooked_goat_roast")
                || id.equals("goat_scraps")
                || id.equals("cooked_goat_scraps")
                || id.equals("goat_stewmeat")
                || id.equals("cooked_goat_stewmeat")
                || id.equals("ground_goat")
                || id.equals("cooked_ground_goat")
                || id.equals("cubed_goat")
                || id.equals("cooked_cubed_goat");
        }

        private Item createItem() {
            return factory.get();
        }
    }
}
