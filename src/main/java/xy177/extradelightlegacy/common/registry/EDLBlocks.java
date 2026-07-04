package xy177.extradelightlegacy.common.registry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.BlockCandyBowl;
import xy177.extradelightlegacy.common.block.BlockChocolateBox;
import xy177.extradelightlegacy.common.block.BlockChocolateFondue;
import xy177.extradelightlegacy.common.block.BlockCornmealLayer;
import xy177.extradelightlegacy.common.block.BlockCornHuskDoll;
import xy177.extradelightlegacy.common.block.BlockCutoutStyleable;
import xy177.extradelightlegacy.common.block.BlockDecorCarpet;
import xy177.extradelightlegacy.common.block.BlockCornBottom;
import xy177.extradelightlegacy.common.block.BlockCornTop;
import xy177.extradelightlegacy.common.block.BlockChiller;
import xy177.extradelightlegacy.common.block.BlockCinnamonLog;
import xy177.extradelightlegacy.common.block.BlockCounterCabinet;
import xy177.extradelightlegacy.common.block.BlockCoffeeBush;
import xy177.extradelightlegacy.common.block.BlockDoughShaping;
import xy177.extradelightlegacy.common.block.BlockDryingRack;
import xy177.extradelightlegacy.common.block.BlockEvaporator;
import xy177.extradelightlegacy.common.block.BlockBubblePot;
import xy177.extradelightlegacy.common.block.BlockFruitLog;
import xy177.extradelightlegacy.common.block.BlockFruitBowl;
import xy177.extradelightlegacy.common.block.BlockFoodDisplay;
import xy177.extradelightlegacy.common.block.BlockFunnel;
import xy177.extradelightlegacy.common.block.BlockHanging;
import xy177.extradelightlegacy.common.block.BlockHalfCabinet;
import xy177.extradelightlegacy.common.block.BlockHorizontalPan;
import xy177.extradelightlegacy.common.block.BlockJar;
import xy177.extradelightlegacy.common.block.BlockJuicer;
import xy177.extradelightlegacy.common.block.BlockKeg;
import xy177.extradelightlegacy.common.block.BlockKnifeBlock;
import xy177.extradelightlegacy.common.block.BlockMeltingPot;
import xy177.extradelightlegacy.common.block.BlockMintCrop;
import xy177.extradelightlegacy.common.block.BlockMixingBowl;
import xy177.extradelightlegacy.common.block.BlockMoldedWallpaper;
import xy177.extradelightlegacy.common.block.BlockMortar;
import xy177.extradelightlegacy.common.block.BlockOrchardLeaves;
import xy177.extradelightlegacy.common.block.BlockOrchardSapling;
import xy177.extradelightlegacy.common.block.BlockOven;
import xy177.extradelightlegacy.common.block.BlockPetalLitter;
import xy177.extradelightlegacy.common.block.BlockPicnicBasket;
import xy177.extradelightlegacy.common.block.BlockPickleJar;
import xy177.extradelightlegacy.common.block.BlockPickleJarDisplay;
import xy177.extradelightlegacy.common.block.BlockPottedSapling;
import xy177.extradelightlegacy.common.block.BlockRawBakedAlaska;
import xy177.extradelightlegacy.common.block.BlockRecipeFeast;
import xy177.extradelightlegacy.common.block.BlockRibbonBow;
import xy177.extradelightlegacy.common.block.BlockSalami;
import xy177.extradelightlegacy.common.block.BlockSimpleButton;
import xy177.extradelightlegacy.common.block.BlockSimpleFence;
import xy177.extradelightlegacy.common.block.BlockSimpleFenceGate;
import xy177.extradelightlegacy.common.block.BlockSimplePillar;
import xy177.extradelightlegacy.common.block.BlockSimplePressurePlate;
import xy177.extradelightlegacy.common.block.BlockSimpleSlab;
import xy177.extradelightlegacy.common.block.BlockSimpleStairs;
import xy177.extradelightlegacy.common.block.BlockSimpleDoor;
import xy177.extradelightlegacy.common.block.BlockSimpleTrapDoor;
import xy177.extradelightlegacy.common.block.BlockSinkCabinet;
import xy177.extradelightlegacy.common.block.BlockSlowCrop;
import xy177.extradelightlegacy.common.block.BlockSimpleCrop;
import xy177.extradelightlegacy.common.block.BlockStepStool;
import xy177.extradelightlegacy.common.block.BlockStorage;
import xy177.extradelightlegacy.common.block.BlockStyleable;
import xy177.extradelightlegacy.common.block.BlockStrippableFruitLog;
import xy177.extradelightlegacy.common.block.BlockTap;
import xy177.extradelightlegacy.common.block.BlockThinLayer;
import xy177.extradelightlegacy.common.block.BlockTranslucentRecipeFeast;
import xy177.extradelightlegacy.common.block.BlockVat;
import xy177.extradelightlegacy.common.block.BlockVatLid;
import xy177.extradelightlegacy.common.block.BlockWallDecor;
import xy177.extradelightlegacy.common.block.BlockWildCrop;
import xy177.extradelightlegacy.common.content.EDLContentEntry;
import xy177.extradelightlegacy.common.content.EDLContentRegistry;
import xy177.extradelightlegacy.common.creative.EDLCreativeTabs;
import xy177.extradelightlegacy.common.item.ItemFoodBlock;
import xy177.extradelightlegacy.common.item.ItemFeastBlock;
import xy177.extradelightlegacy.common.item.ItemBlockTooltip;
import xy177.extradelightlegacy.common.item.ItemJarBlock;
import xy177.extradelightlegacy.common.item.ItemPickleJarBlock;
import xy177.extradelightlegacy.common.item.ItemStyleableBlock;
import xy177.extradelightlegacy.common.module.EDLModule;
import com.wdcftgg.farmersdelightlegacy.common.block.BlockCabinet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class EDLBlocks {
    private static final List<BlockDefinition> DEFINITIONS = new ArrayList<>();
    private static final List<Item> REGISTERED_ITEM_BLOCKS = new ArrayList<>();
    private static final String[] COLOR_IDS = {
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
        "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };
    private static final String[] VANILLA_WOOD_IDS = {
        "oak", "spruce", "birch", "dark_oak", "jungle", "acacia"
    };
    private static final String[] VANILLA_WOOD_NAMES = {
        "Oak", "Spruce", "Birch", "Dark Oak", "Jungle", "Acacia"
    };

    public static final BlockDefinition CORN_TOP_CROP = crop("corn_top_crop", "Corn Top Crop",
        () -> new BlockCornTop(
            () -> EDLItems.CORN_SEEDS.getItem(),
            () -> EDLItems.UNSHUCKED_CORN.getItem(),
            EDLBlocks::getCornCropBlock
        )
    );
    public static final BlockDefinition CORN_CROP = crop("corn_crop", "Corn Crop",
        () -> new BlockCornBottom(
            () -> EDLItems.CORN_SEEDS.getItem(),
            EDLBlocks::getCornTopCropBlock
        ),
        "corn_top_crop"
    );
    public static final BlockDefinition CHILI_CROP = crop("chili_crop", "Chili Crop",
        () -> new BlockSimpleCrop(
            () -> EDLItems.CHILI_SEEDS.getItem(),
            () -> EDLItems.CHILI.getItem(),
            6
        )
    );
    public static final BlockDefinition GARLIC_CROP = crop("garlic_crop", "Garlic Crop",
        () -> new BlockSimpleCrop(
            () -> EDLItems.GARLIC_CLOVE.getItem(),
            () -> EDLItems.GARLIC.getItem(),
            3
        )
    );
    public static final BlockDefinition GINGER_CROP = crop("ginger_crop", "Ginger Crop",
        () -> new BlockSlowCrop(
            () -> EDLItems.GINGER_CUTTING.getItem(),
            () -> EDLItems.GINGER.getItem(),
            3
        )
    );
    public static final BlockDefinition MINT_CROP = crop("mint_crop", "Mint Crop",
        () -> new BlockMintCrop(() -> EDLItems.MINT.getItem())
    );
    public static final BlockDefinition PEANUT_CROP = crop("peanut_crop", "Peanut Crop",
        () -> new BlockSlowCrop(
            () -> EDLItems.PEANUTS.getItem(),
            () -> EDLItems.PEANUTS_IN_SHELL.getItem(),
            5
        )
    );
    public static final BlockDefinition COFFEE_BUSH = crop("coffee_bush", "Coffee Bush",
        () -> new BlockCoffeeBush(() -> EDLItems.COFFEE_CHERRIES.getItem())
    );
    public static final BlockDefinition MALLOW_ROOT_CROP = crop("mallow_root_crop", "Mallow Root Crop",
        () -> new BlockSlowCrop(
            () -> EDLItems.MALLOW_ROOT.getItem(),
            () -> EDLItems.MALLOW_ROOT.getItem(),
            7
        )
    );
    public static final BlockDefinition CUCUMBER_CROP = crop("cucumber_crop", "Cucumber Crop",
        () -> new BlockSlowCrop(
            () -> EDLItems.CUCUMBER_SEED.getItem(),
            () -> EDLItems.CUCUMBER.getItem(),
            7
        )
    );
    public static final BlockDefinition SOYBEAN_CROP = crop("soybean_crop", "Soybean Crop",
        () -> new BlockSlowCrop(
            () -> EDLItems.SOYBEANS.getItem(),
            () -> EDLItems.SOYBEAN_POD.getItem(),
            7
        )
    );
    public static final BlockDefinition CORN_CRATE = storageBlock("corn_crate", "Corn Crate", Material.WOOD, SoundType.WOOD, "corn_on_cob");
    public static final BlockDefinition CORN_SACK = storageBlock("corn_sack", "Corn Sack", Material.CLOTH, SoundType.CLOTH, "corn_seeds");
    public static final BlockDefinition CORN_SILK_SACK = storageBlock("corn_silk_sack", "Corn Silk Sack", Material.CLOTH, SoundType.CLOTH, "corn_silk");
    public static final BlockDefinition CORN_HUSK_BUNDLE = storageBlock("corn_husk_bundle", "Corn Husk Bundle", Material.WOOD, SoundType.PLANT, "corn_husk");
    public static final BlockDefinition CORN_COB_BUNDLE = storageBlock("corn_cob_bundle", "Corn Cob Bundle", Material.WOOD, SoundType.PLANT, "corn_cob");
    public static final BlockDefinition GARLIC_CRATE = storageBlock("garlic_crate", "Garlic Crate", Material.WOOD, SoundType.WOOD, "garlic");
    public static final BlockDefinition GINGER_CRATE = storageBlock("ginger_crate", "Ginger Crate", Material.WOOD, SoundType.WOOD, "ginger");
    public static final BlockDefinition MINT_SACK = storageBlock("mint_sack", "Mint Sack", Material.CLOTH, SoundType.CLOTH, "mint");
    public static final BlockDefinition PEANUT_IN_SHELL_SACK = storageBlock("peanut_in_shell_sack", "Peanut in Shell Sack", Material.CLOTH, SoundType.CLOTH, "peanuts_in_shell");
    public static final BlockDefinition PEANUT_SACK = storageBlock("peanut_sack", "Peanut Sack", Material.CLOTH, SoundType.CLOTH, "peanuts");
    public static final BlockDefinition ROASTED_PEANUT_SACK = storageBlock("roasted_peanut_sack", "Roasted Peanut Sack", Material.CLOTH, SoundType.CLOTH, "roasted_peanuts");
    public static final BlockDefinition MALLOW_ROOT_CRATE = storageBlock("mallow_root_crate", "Mallow Root Crate", Material.WOOD, SoundType.WOOD, "mallow_root");
    public static final BlockDefinition SUGAR_SACK = storageBlock("sugar_sack", "Sugar Sack", Material.CLOTH, SoundType.CLOTH);
    public static final BlockDefinition EGG_CRATE = storageBlock("egg_crate", "Egg Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition BROWN_MUSHROOM_CRATE = storageBlock("brown_mushroom_crate", "Brown Mushroom Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition RED_MUSHROOM_CRATE = storageBlock("red_mushroom_crate", "Red Mushroom Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition APPLE_CRATE = storageBlock("apple_crate", "Apple Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition GOLDEN_APPLE_CRATE = storageBlock("golden_apple_crate", "Golden Apple Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition GOLDEN_CARROT_CRATE = storageBlock("golden_carrot_crate", "Golden Carrot Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition SWEET_BERRY_CRATE = storageBlock("sweet_berry_crate", "Sweet Berry Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition GLOW_BERRY_CRATE = storageBlock("glow_berry_crate", "Glow Berry Crate", Material.WOOD, SoundType.WOOD);
    public static final BlockDefinition BREADCRUMBS = foodLayerBlock("breadcrumbs", "Breadcrumbs", "grater");
    public static final BlockDefinition CORNMEAL = noItemBlock(EDLModule.CORE, "cornmeal", "Cornmeal", BlockCornmealLayer::new, "corn_meal");
    public static final BlockDefinition BREADCRUMB_SACK = storageBlock("breadcrumb_sack", "Breadcrumb Sack", Material.CLOTH, SoundType.CLOTH, "breadcrumbs");
    public static final BlockDefinition DRYING_RACK = simpleBlock(EDLModule.KITCHEN, "drying_rack", "Drying Rack", BlockDryingRack::new);
    public static final BlockDefinition MORTAR_STONE = simpleBlock(EDLModule.KITCHEN, "mortar_stone", "Mortar", BlockMortar::new);
    public static final BlockDefinition DOUGH_SHAPING = simpleBlock(EDLModule.KITCHEN, "dough_shaping", "Dough Shaping Station", BlockDoughShaping::new, "flour");
    public static final BlockDefinition MIXING_BOWL = simpleBlock(
        EDLModule.KITCHEN,
        "mixing_bowl",
        "Mixing Bowl",
        BlockMixingBowl::new,
        ItemStyleableBlock::new
    );
    public static final BlockDefinition CHILLER = simpleBlock(EDLModule.KITCHEN, "chiller", "Chiller", BlockChiller::new);
    public static final BlockDefinition MELTING_POT = simpleBlock(EDLModule.KITCHEN, "melting_pot", "Melting Pot", BlockMeltingPot::new);
    public static final BlockDefinition OVEN = simpleBlock(EDLModule.KITCHEN, "oven", "Oven", BlockOven::new);
    public static final BlockDefinition JUICER = simpleBlock(EDLModule.KITCHEN, "juicer", "Juicer", BlockJuicer::new);
    public static final BlockDefinition TAP = simpleBlock(EDLModule.KITCHEN, "tap", "Tap", BlockTap::new);
    public static final BlockDefinition FUNNEL = simpleBlock(EDLModule.KITCHEN, "funnel", "Funnel", BlockFunnel::new, "keg_block");
    public static final BlockDefinition KEG_BLOCK = simpleBlock(EDLModule.KITCHEN, "keg_block", "Keg", BlockKeg::new);
    public static final BlockDefinition JAR = simpleBlock(EDLModule.KITCHEN, "jar", "Jar", BlockJar::new, ItemJarBlock::new);
    public static final BlockDefinition EVAPORATOR = simpleBlock(EDLModule.KITCHEN, "evaporator", "Evaporator",
        BlockEvaporator::new, ItemStyleableBlock::new, "salt");
    public static final BlockDefinition VAT = simpleBlock(EDLModule.KITCHEN, "vat", "Fermentation Vat", BlockVat::new, ItemStyleableBlock::new, "cucumber", "vinegar", "salt");
    public static final BlockDefinition VAT_LID = simpleBlock(EDLModule.KITCHEN, "vat_lid", "Fermentation Vat Lid", BlockVatLid::new, ItemStyleableBlock::new, "vat");
    public static final BlockDefinition VINEGAR_POT = simpleBlock(
        EDLModule.KITCHEN,
        "vinegar_pot",
        "Vinegar Pot",
        () -> new BlockBubblePot(() -> EDLItems.VINEGAR.stack(1), 8, EDLBlocks::bubblePotRemainders),
        block -> new ItemBlockTooltip(block, ExtraDelightLegacy.MODID + ".vinegarpot.tooltip", TextFormatting.AQUA)
    );
    public static final BlockDefinition YEAST_POT = simpleBlock(
        EDLModule.KITCHEN,
        "yeast_pot",
        "Yeast Pot",
        () -> new BlockBubblePot(() -> EDLItems.YEAST.stack(1), 4, EDLBlocks::bubblePotRemainders),
        block -> new ItemBlockTooltip(block, ExtraDelightLegacy.MODID + ".yeastpot.tooltip", TextFormatting.AQUA)
    );
    public static final BlockDefinition UNRIPE_SALAMI_BLOCK = noItemBlock(
        EDLModule.CORE,
        "unripe_salami_block",
        "Unripe Salami",
        () -> new BlockSalami(true, () -> EDLItems.UNRIPE_SALAMI_ITEM.getItem(), () -> EDLBlocks.SALAMI_BLOCK.getBlock()),
        "salami_mix"
    );
    public static final BlockDefinition SALAMI_BLOCK = noItemBlock(
        EDLModule.CORE,
        "salami_block",
        "Salami",
        () -> new BlockSalami(false, () -> EDLItems.SALAMI_ITEM.getItem(), () -> EDLBlocks.SALAMI_BLOCK.getBlock()),
        "unripe_salami_item"
    );
    public static final BlockDefinition SHEET = horizontalPan("sheet", "Sheet Tray",
        new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.0625D, 1.0D),
        new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 1.0D, 0.0625D, 0.9375D)
    );
    public static final BlockDefinition TRAY = horizontalPan("tray", "Baking Tray",
        new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.125D, 1.0D),
        new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 1.0D, 0.125D, 0.9375D)
    );
    public static final BlockDefinition LOAF_PAN = horizontalPan("loaf_pan", "Loaf Pan",
        new AxisAlignedBB(0.25D, 0.0D, 0.125D, 0.75D, 0.25D, 0.875D),
        new AxisAlignedBB(0.125D, 0.0D, 0.25D, 0.875D, 0.25D, 0.75D)
    );
    public static final BlockDefinition PIE_DISH = horizontalPan("pie_dish", "Pie Dish",
        new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D),
        new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D)
    );
    public static final BlockDefinition SQUARE_PAN = horizontalPan("square_pan", "Square Pan",
        new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D),
        new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D)
    );
    public static final BlockDefinition BAKING_STONE = simpleBlock(EDLModule.KITCHEN, "baking_stone", "Baking Stone",
        () -> new BlockStyleable(
            Material.ROCK,
            SoundType.STONE,
            16,
            new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D)
        )
    );
    public static final BlockDefinition MUFFIN_TIN = horizontalPan("muffin_tin", "Muffin Tin",
        new AxisAlignedBB(0.21875D, 0.0D, 0.09375D, 0.78125D, 0.125D, 0.90625D),
        new AxisAlignedBB(0.09375D, 0.0D, 0.21875D, 0.90625D, 0.125D, 0.78125D)
    );
    public static final BlockDefinition SERVING_POT = horizontalPan("serving_pot", "Serving Pot",
        new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.375D, 0.8125D),
        new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.375D, 0.8125D),
        Material.IRON,
        SoundType.METAL
    );
    public static final BlockDefinition BAR_MOLD = horizontalPan("bar_mold", "Bar Mold",
        new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.125D, 1.0D),
        new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 1.0D, 0.125D, 0.9375D)
    );
    public static final BlockDefinition DRIED_CORN_HUSK_BUNDLE = storageBlock("dried_corn_husk_bundle", "Dried Corn Husk Bundle", Material.WOOD, SoundType.PLANT, "dried_corn_husk");
    public static final BlockDefinition FLOUR_SACK = storageBlock("flour_sack", "Flour Sack", Material.CLOTH, SoundType.CLOTH, "flour");
    public static final BlockDefinition CORNMEAL_SACK = storageBlock("cornmeal_sack", "Cornmeal Sack", Material.CLOTH, SoundType.CLOTH, "corn_meal");
    public static final BlockDefinition CHILI_POWDER_SACK = storageBlock("chili_powder_sack", "Chili Powder Sack", Material.CLOTH, SoundType.CLOTH, "chili_powder");
    public static final BlockDefinition MALLOW_POWDER_SACK = storageBlock("mallow_powder_sack", "Mallow Root Powder Sack", Material.CLOTH, SoundType.CLOTH, "mallow_powder");
    public static final BlockDefinition COCOA_BEANS_SACK = storageBlock("cocoa_beans_sack", "Cocoa Bean Sack", Material.CLOTH, SoundType.CLOTH);
    public static final BlockDefinition ROASTED_COCOA_BEANS_SACK = storageBlock("roasted_cocoa_beans_sack", "Roasted Cocoa Beans Sack", Material.CLOTH, SoundType.CLOTH, "roasted_cocoa_beans");
    public static final BlockDefinition COCOA_SOLIDS_SACK = storageBlock("cocoa_solids_sack", "Cocoa Solids Sack", Material.CLOTH, SoundType.CLOTH, "cocoa_solids");
    public static final BlockDefinition COCOA_POWDER_SACK = storageBlock("cocoa_powder_sack", "Cocoa Powder Sack", Material.CLOTH, SoundType.CLOTH, "cocoa_powder");
    public static final BlockDefinition HANGING_CHILI = hangingBlock("hanging_chili", "Hanging Chilis", "chili");
    public static final BlockDefinition HANGING_DRIED_CHILI = hangingBlock("hanging_dried_chili", "Hanging Dried Chilis", "dried_chili");
    public static final BlockDefinition HANGING_CORN = hangingBlock("hanging_corn", "Hanging Corn", "corn_on_cob");
    public static final BlockDefinition HANGING_GARLIC = hangingBlock("hanging_garlic", "Hanging Garlic", "garlic");
    public static final BlockDefinition HANGING_MINT = hangingBlock("hanging_mint", "Hanging Mint", "mint");
    public static final BlockDefinition HANGING_ONIONS = hangingBlock("hanging_onions", "Hanging Onions");
    public static final BlockDefinition HANGING_HAM = hangingBlock("hanging_ham", "Hanging Ham");
    public static final BlockDefinition CUCUMBER_CRATE = storageBlock("cucumber_crate", "Cucumber Crate", Material.WOOD, SoundType.WOOD, "cucumber");
    public static final BlockDefinition SOYBEAN_SACK = storageBlock("soybean_sack", "Soybean Sack", Material.CLOTH, SoundType.CLOTH, "soybeans");
    public static final BlockDefinition SALT_BLOCK = storageBlock(EDLModule.KITCHEN_DECOR, "salt_block", "Salt Block", Material.ROCK, SoundType.STONE, "salt");
    public static final BlockDefinition FRUIT_LOG = simpleBlock(EDLModule.ORCHARD, "fruit_log", "Fruit Log", BlockStrippableFruitLog::new);
    public static final BlockDefinition STRIPPED_FRUIT_LOG = simpleBlock(EDLModule.ORCHARD, "stripped_fruit_log", "Stripped Fruit Log", BlockFruitLog::new, "fruit_log");
    public static final BlockDefinition APPLE_LEAVES = orchardLeaves("apple_leaves", "Apple Leaves",
        () -> Items.APPLE,
        () -> EDLBlocks.APPLE_SAPLING.getItemBlock(),
        () -> EDLBlocks.APPLE_PETAL_LITTER.getBlock()
    );
    public static final BlockDefinition APPLE_SAPLING = orchardSapling("apple_sapling", "Apple Sapling",
        () -> EDLBlocks.APPLE_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        3,
        "fruit_log", "apple_leaves"
    );
    public static final BlockDefinition LEMON_LEAVES = orchardLeaves("lemon_leaves", "Lemon Leaves",
        () -> EDLItems.LEMON.getItem(),
        () -> EDLBlocks.LEMON_SAPLING.getItemBlock()
    );
    public static final BlockDefinition LEMON_SAPLING = orchardSapling("lemon_sapling", "Lemon Sapling",
        () -> EDLBlocks.LEMON_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        3,
        "fruit_log", "lemon_leaves"
    );
    public static final BlockDefinition LIME_LEAVES = orchardLeaves("lime_leaves", "Lime Leaves",
        () -> EDLItems.LIME.getItem(),
        () -> EDLBlocks.LIME_SAPLING.getItemBlock()
    );
    public static final BlockDefinition LIME_SAPLING = orchardSapling("lime_sapling", "Lime Sapling",
        () -> EDLBlocks.LIME_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        3,
        "fruit_log", "lime_leaves"
    );
    public static final BlockDefinition ORANGE_LEAVES = orchardLeaves("orange_leaves", "Orange Leaves",
        () -> EDLItems.ORANGE.getItem(),
        () -> EDLBlocks.ORANGE_SAPLING.getItemBlock()
    );
    public static final BlockDefinition ORANGE_SAPLING = orchardSapling("orange_sapling", "Orange Sapling",
        () -> EDLBlocks.ORANGE_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        3,
        "fruit_log", "orange_leaves"
    );
    public static final BlockDefinition GRAPEFRUIT_LEAVES = orchardLeaves("grapefruit_leaves", "Grapefruit Leaves",
        () -> EDLItems.GRAPEFRUIT.getItem(),
        () -> EDLBlocks.GRAPEFRUIT_SAPLING.getItemBlock()
    );
    public static final BlockDefinition GRAPEFRUIT_SAPLING = orchardSapling("grapefruit_sapling", "Grapefruit Sapling",
        () -> EDLBlocks.GRAPEFRUIT_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        3,
        "fruit_log", "grapefruit_leaves"
    );
    public static final BlockDefinition HAZELNUT_LEAVES = orchardLeaves("hazelnut_leaves", "Hazelnut Leaves",
        () -> EDLItems.HAZELNUTS_IN_SHELL.getItem(),
        () -> EDLBlocks.HAZELNUT_SAPLING.getItemBlock()
    );
    public static final BlockDefinition HAZELNUT_SAPLING = orchardSapling("hazelnut_sapling", "Hazelnut Sapling",
        () -> EDLBlocks.HAZELNUT_LEAVES.getBlock(),
        () -> EDLBlocks.FRUIT_LOG.getBlock(),
        4,
        "fruit_log", "hazelnut_leaves"
    );
    public static final BlockDefinition CINNAMON_LOG = simpleBlock(EDLModule.ORCHARD, "cinnamon_log", "Cinnamon Log", () -> new BlockCinnamonLog(false));
    public static final BlockDefinition STRIPPED_CINNAMON_LOG = simpleBlock(EDLModule.ORCHARD, "stripped_cinnamon_log", "Stripped Cinnamon Log", () -> new BlockCinnamonLog(true), "cinnamon_log");
    public static final BlockDefinition CINNAMON_LEAVES = orchardLeaves("cinnamon_leaves", "Cinnamon Leaves",
        () -> EDLItems.CINNAMON_BARK.getItem(),
        () -> EDLBlocks.CINNAMON_SAPLING.getItemBlock(),
        false
    );
    public static final BlockDefinition CINNAMON_SAPLING = orchardSapling("cinnamon_sapling", "Cinnamon Sapling",
        () -> EDLBlocks.CINNAMON_LEAVES.getBlock(),
        () -> EDLBlocks.CINNAMON_LOG.getBlock(),
        4,
        "cinnamon_log", "cinnamon_leaves"
    );
    public static final BlockDefinition CINNAMON_WOOD = woodPillarBlock(EDLModule.ORCHARD, "cinnamon_wood", "Cinnamon Wood", "cinnamon_log");
    public static final BlockDefinition STRIPPED_CINNAMON_WOOD = woodPillarBlock(EDLModule.ORCHARD, "stripped_cinnamon_wood", "Stripped Cinnamon Wood", "stripped_cinnamon_log");
    public static final BlockDefinition CINNAMON_PLANKS = woodStorageBlock("cinnamon_planks", "Cinnamon Planks", "cinnamon_log");
    public static final BlockDefinition CINNAMON_SLAB_DOUBLE = woodDoubleSlabBlock("cinnamon_slab_double", "Cinnamon Slab Double", "cinnamon_planks");
    public static final BlockDefinition CINNAMON_SLAB = woodSlabBlock("cinnamon_slab", "Cinnamon Slab", CINNAMON_SLAB_DOUBLE, "cinnamon_planks");
    public static final BlockDefinition CINNAMON_STAIRS = woodStairsBlock("cinnamon_stairs", "Cinnamon Stairs", () -> CINNAMON_PLANKS.getBlock(), "cinnamon_planks");
    public static final BlockDefinition CINNAMON_FENCE = woodFenceBlock("cinnamon_fence", "Cinnamon Fence", "cinnamon_planks");
    public static final BlockDefinition CINNAMON_FENCE_GATE = woodFenceGateBlock("cinnamon_fence_gate", "Cinnamon Fence Gate", "cinnamon_planks");
    public static final BlockDefinition CINNAMON_BUTTON = simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_button", "Cinnamon Button", BlockSimpleButton::new, "cinnamon_planks").vanillaCreativeTab(CreativeTabs.REDSTONE);
    public static final BlockDefinition CINNAMON_PRESSURE_PLATE = simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_pressure_plate", "Cinnamon Pressure Plate", BlockSimplePressurePlate::new, "cinnamon_planks").vanillaCreativeTab(CreativeTabs.REDSTONE);
    public static final BlockDefinition CINNAMON_DOOR = woodDoorBlock("cinnamon_door", "Cinnamon Door", "cinnamon_planks");
    public static final BlockDefinition CINNAMON_TRAPDOOR = woodTrapdoorBlock("cinnamon_trapdoor", "Cinnamon Trapdoor", "cinnamon_planks");
    public static final BlockDefinition FRUIT_WOOD = woodPillarBlock(EDLModule.ORCHARD, "fruit_wood", "Fruit Wood", "fruit_log");
    public static final BlockDefinition STRIPPED_FRUIT_WOOD = woodPillarBlock(EDLModule.ORCHARD, "stripped_fruit_wood", "Stripped Fruit Wood", "stripped_fruit_log");
    public static final BlockDefinition FRUIT_PLANKS = woodStorageBlock("fruit_planks", "Fruit Planks", "fruit_log");
    public static final BlockDefinition FRUIT_SLAB_DOUBLE = woodDoubleSlabBlock("fruit_slab_double", "Fruit Slab Double", "fruit_planks");
    public static final BlockDefinition FRUIT_SLAB = woodSlabBlock("fruit_slab", "Fruit Slab", FRUIT_SLAB_DOUBLE, "fruit_planks");
    public static final BlockDefinition FRUIT_STAIRS = woodStairsBlock("fruit_stairs", "Fruit Stairs", () -> FRUIT_PLANKS.getBlock(), "fruit_planks");
    public static final BlockDefinition FRUIT_FENCE = woodFenceBlock("fruit_fence", "Fruit Fence", "fruit_planks");
    public static final BlockDefinition FRUIT_FENCE_GATE = woodFenceGateBlock("fruit_fence_gate", "Fruit Fence Gate", "fruit_planks");
    public static final BlockDefinition FRUIT_BUTTON = simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_button", "Fruit Button", BlockSimpleButton::new, "fruit_planks").vanillaCreativeTab(CreativeTabs.REDSTONE);
    public static final BlockDefinition FRUIT_PRESSURE_PLATE = simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_pressure_plate", "Fruit Pressure Plate", BlockSimplePressurePlate::new, "fruit_planks").vanillaCreativeTab(CreativeTabs.REDSTONE);
    public static final BlockDefinition FRUIT_DOOR = woodDoorBlock("fruit_door", "Fruit Door", "fruit_planks");
    public static final BlockDefinition FRUIT_TRAPDOOR = woodTrapdoorBlock("fruit_trapdoor", "Fruit Trapdoor", "fruit_planks");
    public static final BlockDefinition LEMON_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "lemon_crate", "Lemon Crate", Material.WOOD, SoundType.WOOD, "lemon");
    public static final BlockDefinition LIME_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "lime_crate", "Lime Crate", Material.WOOD, SoundType.WOOD, "lime");
    public static final BlockDefinition ORANGE_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "orange_crate", "Orange Crate", Material.WOOD, SoundType.WOOD, "orange");
    public static final BlockDefinition GRAPEFRUIT_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "grapefruit_crate", "Grapefruit Crate", Material.WOOD, SoundType.WOOD, "grapefruit");
    public static final BlockDefinition HAZELNUT_IN_SHELL_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "hazelnut_in_shell_sack", "Hazelnut in Shell Sack", Material.CLOTH, SoundType.CLOTH, "hazelnuts_in_shell");
    public static final BlockDefinition HAZELNUT_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "hazelnut_sack", "Hazelnut Sack", Material.CLOTH, SoundType.CLOTH, "hazelnuts");
    public static final BlockDefinition ROASTED_HAZELNUT_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "roasted_hazelnut_sack", "Roasted Hazelnut Sack", Material.CLOTH, SoundType.CLOTH, "roasted_hazelnuts");
    public static final BlockDefinition CHILI_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "chili_crate", "Chili Crate", Material.WOOD, SoundType.WOOD, "chili");
    public static final BlockDefinition COFFEE_CHERRY_CRATE = storageBlock(EDLModule.KITCHEN_DECOR, "coffee_cherry_crate", "Coffee Cherry Crate", Material.WOOD, SoundType.WOOD, "coffee_cherries");
    public static final BlockDefinition COFFEE_BEAN_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "coffee_bean_sack", "Coffee Bean Sack", Material.CLOTH, SoundType.CLOTH, "coffee_beans");
    public static final BlockDefinition GREEN_COFFEE_BEANS_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "green_coffee_beans_sack", "Green Coffee Beans Sack", Material.CLOTH, SoundType.CLOTH, "green_coffee");
    public static final BlockDefinition GROUND_COFFEE_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "ground_coffee_sack", "Ground Coffee Sack", Material.CLOTH, SoundType.CLOTH, "ground_coffee");
    public static final BlockDefinition GROUND_CINNAMON_SACK = storageBlock(EDLModule.KITCHEN_DECOR, "ground_cinnamon_sack", "Ground Cinnamon Sack", Material.CLOTH, SoundType.CLOTH, "ground_cinnamon");
    public static final BlockDefinition RAW_CINNAMON_BLOCK = storageBlock(EDLModule.KITCHEN_DECOR, "raw_cinnamon_block", "Raw Cinnamon Block", Material.WOOD, SoundType.WOOD, "raw_cinnamon");
    public static final BlockDefinition CINNAMON_STICK_BLOCK = woodPillarBlock("cinnamon_stick_block", "Cinnamon Stick Block", "cinnamon_stick");
    public static final BlockDefinition CANDY_CANE_BLUE_BLOCK = storageBlock(EDLModule.SWEETS, "candy_cane_blue_block", "Wintergreen Candy Cane Block", Material.ROCK, SoundType.STONE, "candy_cane_blue");
    public static final BlockDefinition CANDY_CANE_GREEN_BLOCK = storageBlock(EDLModule.SWEETS, "candy_cane_green_block", "Spearmint Candy Cane Block", Material.ROCK, SoundType.STONE, "candy_cane_green");
    public static final BlockDefinition CANDY_CANE_RED_BLOCK = storageBlock(EDLModule.SWEETS, "candy_cane_red_block", "Peppermint Candy Cane Block", Material.ROCK, SoundType.STONE, "candy_cane_red");
    public static final List<BlockDefinition> CHOCOLATE_BOX_BLOCKS = chocolateBoxBlocks();
    public static final BlockDefinition CANDY_BOWL = simpleBlock(EDLModule.KITCHEN_DECOR, "candy_bowl", "Candy Bowl", BlockCandyBowl::new, "candy_white");
    public static final BlockDefinition FRUIT_BOWL = simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_bowl", "Fruit Bowl", BlockFruitBowl::new, ItemStyleableBlock::new);
    public static final List<BlockDefinition> FOOD_DISPLAY_BLOCKS = foodDisplayBlocks();
    public static final List<BlockDefinition> KNIFE_BLOCKS = knifeBlocks();
    public static final List<BlockDefinition> HALF_CABINET_BLOCKS = halfCabinetBlocks();
    public static final List<BlockDefinition> COUNTER_CABINET_BLOCKS = counterCabinetBlocks();
    public static final List<BlockDefinition> SINK_CABINET_BLOCKS = sinkCabinetBlocks();
    public static final BlockDefinition CINNAMON_CABINET = simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_cabinet", "Cinnamon Cabinet", BlockCabinet::new, "cinnamon_slab", "cinnamon_trapdoor");
    public static final BlockDefinition FRUIT_CABINET = simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_cabinet", "Fruit Cabinet", BlockCabinet::new, "fruit_slab", "fruit_trapdoor");
    public static final BlockDefinition CRIMSON_CABINET = externalBlock(
        EDLModule.KITCHEN_DECOR,
        "crimson_cabinet",
        "Crimson Cabinet",
        BlockCabinet::new,
        new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"},
        new String[]{"nb:crimson_trapdoor", "futuremc:crimson_trapdoor"}
    );
    public static final BlockDefinition WARPED_CABINET = externalBlock(
        EDLModule.KITCHEN_DECOR,
        "warped_cabinet",
        "Warped Cabinet",
        BlockCabinet::new,
        new String[]{"nb:warped_slab_half", "futuremc:warped_slab"},
        new String[]{"nb:warped_trapdoor", "futuremc:warped_trapdoor"}
    );
    public static final BlockDefinition CHERRY_CABINET = externalBlock(
        EDLModule.KITCHEN_DECOR,
        "cherry_cabinet",
        "Cherry Cabinet",
        BlockCabinet::new,
        new String[]{"suikecherry:cherry_slab"},
        new String[]{"suikecherry:cherry_trapdoor"}
    );
    public static final List<BlockDefinition> FROSTED_GINGERBREAD_BLOCKS = frostedGingerbreadBlocks();
    public static final BlockDefinition BUTTER_BLOCK = storageBlock(EDLModule.KITCHEN_DECOR, "butter_block", "Butter Block", Material.CAKE, SoundType.CLOTH, "butter");
    public static final BlockDefinition BUTTER_SLAB_DOUBLE = doubleSlabBlock("butter_slab_block_double", "Butter Slab Double", "butter");
    public static final BlockDefinition BUTTER_SLAB = slabBlock("butter_slab_block", "Butter Slab", BUTTER_SLAB_DOUBLE, "butter");
    public static final BlockDefinition BUTTER_STAIRS = stairsBlock("butter_stairs_block", "Butter Stairs", () -> BUTTER_BLOCK.getBlock(), "butter");
    public static final BlockDefinition CHEESE_BLOCK = storageBlock(EDLModule.KITCHEN_DECOR, "cheese_block", "Cheese Block", Material.CAKE, SoundType.CLOTH, "cheese");
    public static final BlockDefinition CHEESE_SLAB_DOUBLE = doubleSlabBlock("cheese_slab_block_double", "Cheese Slab Double", "cheese");
    public static final BlockDefinition CHEESE_SLAB = slabBlock("cheese_slab_block", "Cheese Slab", CHEESE_SLAB_DOUBLE, "cheese");
    public static final BlockDefinition CHEESE_STAIRS = stairsBlock("cheese_stairs_block", "Cheese Stairs", () -> CHEESE_BLOCK.getBlock(), "cheese");
    public static final BlockDefinition DARK_CHOCOLATE_BLOCK = chocolateStorageBlock("dark_chocolate_block", "Dark Chocolate Block", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_SLAB_DOUBLE = doubleSlabBlock("dark_chocolate_slab_double", "Dark Chocolate Slab Double", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_SLAB = slabBlock("dark_chocolate_slab", "Dark Chocolate Slab", DARK_CHOCOLATE_SLAB_DOUBLE, "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_STAIRS = stairsBlock("dark_chocolate_stairs", "Dark Chocolate Stairs", () -> DARK_CHOCOLATE_BLOCK.getBlock(), "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_FENCE = fenceBlock("dark_chocolate_fence", "Dark Chocolate Fence", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_FENCE_GATE = fenceGateBlock("dark_chocolate_fence_gate", "Dark Chocolate Fence Gate", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_PILLAR = pillarBlock("dark_chocolate_pillar", "Dark Chocolate Pillar", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_DOOR = doorBlock("dark_chocolate_door", "Dark Chocolate Door", "dark_chocolate_bar");
    public static final BlockDefinition DARK_CHOCOLATE_TRAPDOOR = trapdoorBlock("dark_chocolate_trapdoor", "Dark Chocolate Trapdoor", "dark_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_BLOCK = chocolateStorageBlock("milk_chocolate_block", "Milk Chocolate Block", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_SLAB_DOUBLE = doubleSlabBlock("milk_chocolate_slab_double", "Milk Chocolate Slab Double", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_SLAB = slabBlock("milk_chocolate_slab", "Milk Chocolate Slab", MILK_CHOCOLATE_SLAB_DOUBLE, "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_STAIRS = stairsBlock("milk_chocolate_stairs", "Milk Chocolate Stairs", () -> MILK_CHOCOLATE_BLOCK.getBlock(), "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_FENCE = fenceBlock("milk_chocolate_fence", "Milk Chocolate Fence", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_FENCE_GATE = fenceGateBlock("milk_chocolate_fence_gate", "Milk Chocolate Fence Gate", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_PILLAR = pillarBlock("milk_chocolate_pillar", "Milk Chocolate Pillar", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_DOOR = doorBlock("milk_chocolate_door", "Milk Chocolate Door", "milk_chocolate_bar");
    public static final BlockDefinition MILK_CHOCOLATE_TRAPDOOR = trapdoorBlock("milk_chocolate_trapdoor", "Milk Chocolate Trapdoor", "milk_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_BLOCK = chocolateStorageBlock("white_chocolate_block", "White Chocolate Block", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_SLAB_DOUBLE = doubleSlabBlock("white_chocolate_slab_double", "White Chocolate Slab Double", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_SLAB = slabBlock("white_chocolate_slab", "White Chocolate Slab", WHITE_CHOCOLATE_SLAB_DOUBLE, "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_STAIRS = stairsBlock("white_chocolate_stairs", "White Chocolate Stairs", () -> WHITE_CHOCOLATE_BLOCK.getBlock(), "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_FENCE = fenceBlock("white_chocolate_fence", "White Chocolate Fence", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_FENCE_GATE = fenceGateBlock("white_chocolate_fence_gate", "White Chocolate Fence Gate", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_PILLAR = pillarBlock("white_chocolate_pillar", "White Chocolate Pillar", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_DOOR = doorBlock("white_chocolate_door", "White Chocolate Door", "white_chocolate_bar");
    public static final BlockDefinition WHITE_CHOCOLATE_TRAPDOOR = trapdoorBlock("white_chocolate_trapdoor", "White Chocolate Trapdoor", "white_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_BLOCK = chocolateStorageBlock("blood_chocolate_block", "Blood Chocolate Block", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_SLAB_DOUBLE = doubleSlabBlock("blood_chocolate_slab_double", "Blood Chocolate Slab Double", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_SLAB = slabBlock("blood_chocolate_slab", "Blood Chocolate Slab", BLOOD_CHOCOLATE_SLAB_DOUBLE, "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_STAIRS = stairsBlock("blood_chocolate_stairs", "Blood Chocolate Stairs", () -> BLOOD_CHOCOLATE_BLOCK.getBlock(), "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_FENCE = fenceBlock("blood_chocolate_fence", "Blood Chocolate Fence", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_FENCE_GATE = fenceGateBlock("blood_chocolate_fence_gate", "Blood Chocolate Fence Gate", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_PILLAR = pillarBlock("blood_chocolate_pillar", "Blood Chocolate Pillar", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_DOOR = doorBlock("blood_chocolate_door", "Blood Chocolate Door", "blood_chocolate_bar");
    public static final BlockDefinition BLOOD_CHOCOLATE_TRAPDOOR = trapdoorBlock("blood_chocolate_trapdoor", "Blood Chocolate Trapdoor", "blood_chocolate_bar");
    public static final BlockDefinition MARSHMALLOW_BLOCK = storageBlock(EDLModule.KITCHEN_DECOR, "marshmallow_block", "Marshmallow Block", Material.CLOTH, SoundType.CLOTH, "marshmallow");
    public static final BlockDefinition APPLE_COOKIE_BLOCK = cookieBlock("apple_cookie_block", "Apple Cinnamon Cookie Block", "apple_cookie_dough", "oven", "sheet");
    public static final BlockDefinition CHOCOLATE_CHIP_COOKIE_BLOCK = cookieBlock("chocolate_chip_cookie_block", "Chocolate Chip Cookie Block", "chocolate_chip_cookie_dough", "oven", "sheet");
    public static final BlockDefinition CHOCOLATE_COOKIE_BLOCK = cookieBlock("chocolate_cookie_block", "Chocolate Cookie Block", "chocolate_cookie_dough", "oven", "sheet");
    public static final BlockDefinition GINGERBREAD_COOKIE_BLOCK = cookieBlock("gingerbread_cookie_block", "Gingerbread Cookie Block", "gingerbread_cookie_dough", "oven", "sheet");
    public static final BlockDefinition GLOW_BERRY_COOKIE_BLOCK = cookieBlock("glow_berry_cookie_block", "Glow Berry Cookie Block", "glow_berry_cookie_dough", "oven", "sheet");
    public static final BlockDefinition HONEY_COOKIE_BLOCK = cookieBlock("honey_cookie_block", "Honey Cookie Block", "honey_cookie_dough", "oven", "sheet");
    public static final BlockDefinition NUT_BUTTER_COOKIE_BLOCK = cookieBlock("nut_butter_cookie_block", "Nut Butter Cookie Block", "nut_butter_cookie_dough", "oven", "sheet");
    public static final BlockDefinition PUMPKIN_COOKIE_BLOCK = cookieBlock("pumpkin_cookie_block", "Pumpkin Cookie Block", "pumpkin_cookie_dough", "oven", "sheet");
    public static final BlockDefinition SUGAR_COOKIE_BLOCK = cookieBlock("sugar_cookie_block", "Sugar Cookie Block", "sugar_cookie_dough", "oven", "sheet");
    public static final BlockDefinition SWEET_BERRY_COOKIE_BLOCK = cookieBlock("sweet_berry_cookie_block", "Sweet Berry Cookie Block", "sweet_berry_cookie_dough", "oven", "sheet");
    public static final List<BlockDefinition> WALLPAPER_BLOCKS = coloredDecorBlocks("wallpaper", "Wallpaper", Material.WOOD, SoundType.PLANT);
    public static final List<BlockDefinition> OAK_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("oak", "Oak");
    public static final List<BlockDefinition> SPRUCE_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("spruce", "Spruce");
    public static final List<BlockDefinition> BIRCH_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("birch", "Birch");
    public static final List<BlockDefinition> DARK_OAK_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("dark_oak", "Dark Oak");
    public static final List<BlockDefinition> JUNGLE_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("jungle", "Jungle");
    public static final List<BlockDefinition> ACACIA_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("acacia", "Acacia");
    public static final List<BlockDefinition> CINNAMON_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("cinnamon", "Cinnamon", "cinnamon_slab");
    public static final List<BlockDefinition> FRUIT_MOLDED_WALLPAPER_BLOCKS = moldedWallpaperBlocks("fruit", "Fruit", "fruit_slab");
    public static final List<BlockDefinition> CRIMSON_MOLDED_WALLPAPER_BLOCKS = externalMoldedWallpaperBlocks(
        "crimson",
        "Crimson",
        new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"}
    );
    public static final List<BlockDefinition> WARPED_MOLDED_WALLPAPER_BLOCKS = externalMoldedWallpaperBlocks(
        "warped",
        "Warped",
        new String[]{"nb:warped_slab_half", "futuremc:warped_slab"}
    );
    public static final List<BlockDefinition> CHERRY_MOLDED_WALLPAPER_BLOCKS = externalMoldedWallpaperBlocks(
        "cherry",
        "Cherry",
        new String[]{"suikecherry:cherry_slab"}
    );
    public static final List<BlockDefinition> RIBBON_BOW_BLOCKS = ribbonBowBlocks();
    public static final List<BlockDefinition> STEP_STOOL_BLOCKS = woodDecorBlocks("step_stool", "Step Stool", BlockStepStool::new);
    public static final List<BlockDefinition> SPICE_RACK_BLOCKS = woodDecorBlocks(EDLModule.KITCHEN_DECOR, "spice_rack", "Spice Rack", () -> BlockWallDecor.spiceRack(false));
    public static final List<BlockDefinition> SPICE_RACK_FULL_BLOCKS = woodDecorBlocks(EDLModule.KITCHEN_DECOR, "spice_rack_full", "Spice Rack", () -> BlockWallDecor.spiceRack(true));
    public static final List<BlockDefinition> WREATH_BLOCKS = woodDecorBlocks("wreath", "Wreath", BlockWallDecor::wreath);
    public static final BlockDefinition CINNAMON_STEP_STOOL = homeDecorBlock("cinnamon_step_stool", "Cinnamon Step Stool", BlockStepStool::new, "cinnamon_slab");
    public static final BlockDefinition CINNAMON_SPICE_RACK = kitchenDecorBlock("cinnamon_spice_rack", "Cinnamon Spice Rack", () -> BlockWallDecor.spiceRack(false), "cinnamon_slab");
    public static final BlockDefinition CINNAMON_SPICE_RACK_FULL = kitchenDecorBlock("cinnamon_spice_rack_full", "Cinnamon Spice Rack", () -> BlockWallDecor.spiceRack(true), "cinnamon_spice_rack");
    public static final BlockDefinition CINNAMON_WREATH = homeDecorBlock("cinnamon_wreath", "Cinnamon Wreath", BlockWallDecor::wreath, "cinnamon_leaves");
    public static final BlockDefinition FRUIT_STEP_STOOL = homeDecorBlock("fruit_step_stool", "Fruit Step Stool", BlockStepStool::new, "fruit_slab");
    public static final BlockDefinition FRUIT_SPICE_RACK = kitchenDecorBlock("fruit_spice_rack", "Fruit Spice Rack", () -> BlockWallDecor.spiceRack(false), "fruit_slab");
    public static final BlockDefinition FRUIT_SPICE_RACK_FULL = kitchenDecorBlock("fruit_spice_rack_full", "Fruit Spice Rack", () -> BlockWallDecor.spiceRack(true), "fruit_spice_rack");
    public static final BlockDefinition CRIMSON_STEP_STOOL = externalHomeDecorBlock("crimson_step_stool", "Crimson Step Stool", BlockStepStool::new,
        new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"});
    public static final BlockDefinition WARPED_STEP_STOOL = externalHomeDecorBlock("warped_step_stool", "Warped Step Stool", BlockStepStool::new,
        new String[]{"nb:warped_slab_half", "futuremc:warped_slab"});
    public static final BlockDefinition CHERRY_STEP_STOOL = externalHomeDecorBlock("cherry_step_stool", "Cherry Step Stool", BlockStepStool::new,
        new String[]{"suikecherry:cherry_slab"});
    public static final BlockDefinition CRIMSON_SPICE_RACK = externalKitchenDecorBlock("crimson_spice_rack", "Crimson Spice Rack", () -> BlockWallDecor.spiceRack(false),
        new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"});
    public static final BlockDefinition WARPED_SPICE_RACK = externalKitchenDecorBlock("warped_spice_rack", "Warped Spice Rack", () -> BlockWallDecor.spiceRack(false),
        new String[]{"nb:warped_slab_half", "futuremc:warped_slab"});
    public static final BlockDefinition CHERRY_SPICE_RACK = externalKitchenDecorBlock("cherry_spice_rack", "Cherry Spice Rack", () -> BlockWallDecor.spiceRack(false),
        new String[]{"suikecherry:cherry_slab"});
    public static final BlockDefinition CRIMSON_SPICE_RACK_FULL = kitchenDecorBlock("crimson_spice_rack_full", "Crimson Spice Rack", () -> BlockWallDecor.spiceRack(true), "crimson_spice_rack");
    public static final BlockDefinition WARPED_SPICE_RACK_FULL = kitchenDecorBlock("warped_spice_rack_full", "Warped Spice Rack", () -> BlockWallDecor.spiceRack(true), "warped_spice_rack");
    public static final BlockDefinition CHERRY_SPICE_RACK_FULL = externalBlock(
        EDLModule.KITCHEN_DECOR,
        "cherry_spice_rack_full",
        "Cherry Spice Rack",
        () -> BlockWallDecor.spiceRack(true),
        new String[]{"suikecherry:cherry_slab"},
        "cherry_spice_rack"
    );
    public static final BlockDefinition LEMON_WREATH = homeDecorBlock("lemon_wreath", "Lemon Wreath", BlockWallDecor::wreath, "lemon_leaves");
    public static final BlockDefinition LIME_WREATH = homeDecorBlock("lime_wreath", "Lime Wreath", BlockWallDecor::wreath, "lime_leaves");
    public static final BlockDefinition ORANGE_WREATH = homeDecorBlock("orange_wreath", "Orange Wreath", BlockWallDecor::wreath, "orange_leaves");
    public static final BlockDefinition GRAPEFRUIT_WREATH = homeDecorBlock("grapefruit_wreath", "Grapefruit Wreath", BlockWallDecor::wreath, "grapefruit_leaves");
    public static final BlockDefinition HAZELNUT_WREATH = homeDecorBlock("hazelnut_wreath", "Hazelnut Wreath", BlockWallDecor::wreath, "hazelnut_leaves");
    public static final BlockDefinition APPLE_WREATH = homeDecorBlock("apple_wreath", "Apple Wreath", BlockWallDecor::wreath, "apple_leaves");
    public static final BlockDefinition CRIMSON_WREATH = externalHomeDecorBlock("crimson_wreath", "Crimson Wreath", BlockWallDecor::wreath,
        new String[]{"minecraft:nether_wart_block", "nb:crimson_wart"});
    public static final BlockDefinition WARPED_WREATH = externalHomeDecorBlock("warped_wreath", "Warped Wreath", BlockWallDecor::wreath,
        new String[]{"nb:warped_wart", "futuremc:warped_wart_block"});
    public static final BlockDefinition CHERRY_WREATH = externalHomeDecorBlock("cherry_wreath", "Cherry Wreath", BlockWallDecor::wreath,
        new String[]{"suikecherry:cherry_leaves"});
    public static final BlockDefinition WILD_GINGER = wildCrop("wild_ginger", "Wild Ginger", "ginger");
    public static final BlockDefinition WILD_PEANUT = wildCrop("wild_peanut", "Wild Peanut", "peanuts_in_shell");
    public static final BlockDefinition WILD_CHILI = wildCrop("wild_chili", "Wild Chilis", "chili_seeds");
    public static final BlockDefinition WILD_MALLOW_ROOT = wildCrop("wild_mallow_root", "Wild Mallow Root", "mallow_root");
    public static final BlockDefinition WILD_GARLIC = wildCrop("wild_garlic", "Wild Garlic", "garlic");
    public static final BlockDefinition WILD_CUCUMBER = wildCrop("wild_cucumber", "Wild Cucumber", "cucumber");
    public static final BlockDefinition WILD_SOYBEAN = wildCrop("wild_soybean", "Wild Soybean", "soybeans");
    public static final BlockDefinition APPLE_PETAL_LITTER = petalLitter("apple_petal_litter", "Apple Petals", "apple_leaves");
    public static final BlockDefinition HAZELNUT_PETAL_LITTER = petalLitter("hazelnut_petal_litter", "Hazelnut Petals", "hazelnut_leaves");
    public static final BlockDefinition LEMON_PETAL_LITTER = petalLitter("lemon_petal_litter", "Lemon Petals", "lemon_leaves");
    public static final BlockDefinition LIME_PETAL_LITTER = petalLitter("lime_petal_litter", "Lime Petals", "lime_leaves");
    public static final BlockDefinition ORANGE_PETAL_LITTER = petalLitter("orange_petal_litter", "Orange Petals", "orange_leaves");
    public static final BlockDefinition GRAPEFRUIT_PETAL_LITTER = petalLitter("grapefruit_petal_litter", "Grapefruit Petals", "grapefruit_leaves");
    public static final BlockDefinition POTTED_APPLE_SAPLING = pottedSapling("potted_apple_sapling", "Potted Apple Sapling", () -> APPLE_SAPLING.getItemBlock(), "apple_sapling");
    public static final BlockDefinition POTTED_CINNAMON_SAPLING = pottedSapling("potted_cinnamon_sapling", "Potted Cinnamon Sapling", () -> CINNAMON_SAPLING.getItemBlock(), "cinnamon_sapling");
    public static final BlockDefinition POTTED_HAZELNUT_SAPLING = pottedSapling("potted_hazelnut_sapling", "Potted Hazelnut Sapling", () -> HAZELNUT_SAPLING.getItemBlock(), "hazelnut_sapling");
    public static final BlockDefinition POTTED_LEMON_SAPLING = pottedSapling("potted_lemon_sapling", "Potted Lemon Sapling", () -> LEMON_SAPLING.getItemBlock(), "lemon_sapling");
    public static final BlockDefinition POTTED_LIME_SAPLING = pottedSapling("potted_lime_sapling", "Potted Lime Sapling", () -> LIME_SAPLING.getItemBlock(), "lime_sapling");
    public static final BlockDefinition POTTED_ORANGE_SAPLING = pottedSapling("potted_orange_sapling", "Potted Orange Sapling", () -> ORANGE_SAPLING.getItemBlock(), "orange_sapling");
    public static final BlockDefinition POTTED_GRAPEFRUIT_SAPLING = pottedSapling("potted_grapefruit_sapling", "Potted Grapefruit Sapling", () -> GRAPEFRUIT_SAPLING.getItemBlock(), "grapefruit_sapling");
    public static final List<BlockDefinition> DRIED_CORN_FENCE_BLOCKS = driedCornFenceBlocks();
    public static final BlockDefinition CORN_HUSK_DOLL = homeDecorBlock("corn_husk_doll", "Corn Husk Doll", BlockCornHuskDoll::new, "dried_corn_husk");
    public static final List<BlockDefinition> GINGHAM_BLOCKS = coloredDecorBlocks("gingham", "Gingham", Material.CLOTH, SoundType.CLOTH);
    public static final List<BlockDefinition> GINGHAM_CARPET_BLOCKS = coloredCarpetBlocks();
    public static final List<BlockDefinition> PICNIC_BASKET_BLOCKS = picnicBasketBlocks();
    public static final BlockDefinition GHERKINS_BLOCK = simpleBlock(EDLModule.PICKLING, "gherkins_block", "Jar of Pickled Cucumbers",
        () -> new BlockPickleJar(() -> EDLItems.GHERKIN_ITEM.getItem()),
        ItemPickleJarBlock::new,
        "vat", "cucumber", "vinegar", "salt"
    );
    public static final BlockDefinition PICKLED_BEETS_BLOCK = pickleJar("pickled_beets_block", "Jar of Pickled Sliced Beetroot",
        () -> EDLItems.PICKLED_BEET_ITEM.getItem(), "pickled_beet_item", "sliced_beetroot_item", "vinegar", "salt");
    public static final BlockDefinition PICKLED_CARROTS_BLOCK = pickleJar("pickled_carrots_block", "Jar of Pickled Carrot",
        () -> EDLItems.PICKLED_CARROT_ITEM.getItem(), "pickled_carrot_item", "vinegar", "salt");
    public static final BlockDefinition PICKLED_EGGS_BLOCK = pickleJar("pickled_eggs_block", "Jar of Pickled Eggs",
        () -> EDLItems.PICKLED_EGG_ITEM.getItem(), "pickled_egg_item", "boiled_egg", "vinegar");
    public static final BlockDefinition PICKLED_FISH_BLOCK = pickleJar("pickled_fish_block", "Jar of Pickled Fish",
        () -> EDLItems.PICKLED_FISH_ITEM.getItem(), "pickled_fish_item", "vinegar", "salt");
    public static final BlockDefinition PICKLED_SAUSAGE_BLOCK = pickleJar("pickled_sausage_block", "Jar of Pickled Sausage",
        () -> EDLItems.PICKLED_SAUSAGE_ITEM.getItem(), "pickled_sausage_item", "cooked_sausage", "vinegar");
    public static final BlockDefinition PICKLED_GINGER_BLOCK = pickleJar("pickled_ginger_block", "Jar of Pickled Ginger",
        () -> EDLItems.PICKLED_GINGER.getItem(), "pickled_ginger", "sliced_ginger", "vinegar", "salt");
    public static final BlockDefinition PICKLED_ONIONS_BLOCK = pickleJar("pickled_onions_block", "Jar of Pickled Onions",
        () -> EDLItems.PICKLED_ONION_ITEM.getItem(), "pickled_onion_item", "vinegar", "salt");
    public static final BlockDefinition PICKLED_RINDS_BLOCK = pickleJar("pickled_rinds_block", "Jar of Pickled Melon Rinds",
        () -> EDLItems.PICKLED_RIND_ITEM.getItem(), "pickled_rind_item", "melon_rind", "vinegar", "salt");
    public static final BlockDefinition PRESERVED_LEMONS_BLOCK = pickleJar("preserved_lemons_block", "Jar of Preserved Lemons",
        () -> EDLItems.PRESERVED_LEMON_ITEM.getItem(), "preserved_lemon_item", "lemon", "lemon_juice", "salt");
    public static final BlockDefinition JAR_DISPLAY_BLOCK = technicalBlock("jar_display_block", "Jar Display", BlockPickleJarDisplay::new);
    public static final BlockDefinition DARK_CHOCOLATE_FONDUE_BLOCK = simpleBlock(EDLModule.SWEETS, "dark_chocolate_fondue_block", "Dark Chocolate Fondue",
        () -> new BlockChocolateFondue(
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_APPLE_SLICE.getItem(),
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_MARSHMALLOW.getItem(),
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_GRAHAM_CRACKER.getItem(),
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_SWEET_BERRY.getItem(),
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_GLOW_BERRY.getItem(),
            () -> EDLItems.DARK_CHOCOLATE_DIPPED_BACON.getItem()
        ),
        "dark_chocolate_syrup_fluid"
    );
    public static final BlockDefinition MILK_CHOCOLATE_FONDUE_BLOCK = simpleBlock(EDLModule.SWEETS, "milk_chocolate_fondue_block", "Milk Chocolate Fondue",
        () -> new BlockChocolateFondue(
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_APPLE_SLICE.getItem(),
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_MARSHMALLOW.getItem(),
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_GRAHAM_CRACKER.getItem(),
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_SWEET_BERRY.getItem(),
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_GLOW_BERRY.getItem(),
            () -> EDLItems.MILK_CHOCOLATE_DIPPED_BACON.getItem()
        ),
        "milk_chocolate_syrup_fluid"
    );
    public static final BlockDefinition WHITE_CHOCOLATE_FONDUE_BLOCK = simpleBlock(EDLModule.SWEETS, "white_chocolate_fondue_block", "White Chocolate Fondue",
        () -> new BlockChocolateFondue(
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_APPLE_SLICE.getItem(),
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_MARSHMALLOW.getItem(),
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_GRAHAM_CRACKER.getItem(),
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_SWEET_BERRY.getItem(),
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_GLOW_BERRY.getItem(),
            () -> EDLItems.WHITE_CHOCOLATE_DIPPED_BACON.getItem()
        ),
        "white_chocolate_syrup_fluid"
    );
    public static final BlockDefinition BLOOD_CHOCOLATE_FONDUE_BLOCK = simpleBlock(EDLModule.SWEETS, "blood_chocolate_fondue_block", "Blood Chocolate Fondue",
        () -> new BlockChocolateFondue(
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_APPLE_SLICE.getItem(),
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_MARSHMALLOW.getItem(),
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_GRAHAM_CRACKER.getItem(),
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_SWEET_BERRY.getItem(),
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_GLOW_BERRY.getItem(),
            () -> EDLItems.BLOOD_CHOCOLATE_DIPPED_BACON.getItem()
        ),
        "blood_chocolate_syrup_fluid"
    );
    public static final BlockDefinition SALISBURY_STEAK_BLOCK = feastBlock("salisbury_steak_block", "Salisbury Steak", "ground_beef");
    public static final BlockDefinition MASHED_POTATO_GRAVY_BLOCK = feastBlock("mashed_potato_gravy_block", "Mashed Potatoes and Gravy", "butter");
    public static final BlockDefinition HASH_BLOCK = feastBlock("hash_block", "Hash", "serving_pot");
    public static final BlockDefinition POTROAST_BLOCK = feastBlock("potroast_block", "Pot Roast", "square_pan");
    public static final BlockDefinition MEATLOAF_BLOCK = feastBlock("meatloaf_block", "Meatloaf", "ground_beef", "breading_misanplas");
    public static final BlockDefinition BBQ_RIBS_BLOCK = feastBlock("bbq_ribs_block", "BBQ Ribs", "tray", "bbq_jar_item");
    public static final BlockDefinition PULLED_PORK_BLOCK = feastBlock("pulled_pork_block", "Pulled Pork", "pork_roast", "bbq_jar_item");
    public static final BlockDefinition RACK_LAMB_BLOCK = feastBlock("rack_lamb_block", "Rack of Lamb", "tray", "lamb_ribs");
    public static final BlockDefinition STIRFRY_BLOCK = feastBlock("stirfry_block", "Stir Fry", "serving_pot");
    public static final BlockDefinition BEEF_WELLINGTON_BLOCK = feastBlock("beef_wellington_block", "Beef Wellington", "tray", "beef_roast", "liver");
    public static final BlockDefinition HAGGIS_BLOCK = feastBlock("haggis_block", "Haggis", "tray", "stomach", "lung");
    public static final BlockDefinition MACARONI_CHEESE_BLOCK = feastBlock("macaroni_cheese_block", "Macaroni and Cheese", "serving_pot", "macaroni", "cheese");
    public static final BlockDefinition LASAGNA_BLOCK = feastBlock("lasagna_block", "Lasagna", "square_pan", "lasagna_noodles", "cheese", "cooked_ground_beef");
    public static final BlockDefinition HOTDISH_BLOCK = feastBlock("hotdish_block", "Hotdish", "square_pan");
    public static final BlockDefinition CURRY_BLOCK = feastBlock("curry_block", "Curry", "serving_pot", "curry_powder");
    public static final BlockDefinition BEEF_STEW_BLOCK = feastBlock("beef_stew_block", "Beef Stew", "serving_pot");
    public static final BlockDefinition PORK_STEW_BLOCK = feastBlock("pork_stew_block", "Pork Stew", "serving_pot");
    public static final BlockDefinition LAMB_STEW_BLOCK = feastBlock("lamb_stew_block", "Lamb Stew", "serving_pot");
    public static final BlockDefinition RABBIT_STEW_BLOCK = feastBlock("rabbit_stew_block", "Rabbit Stew", "serving_pot");
    public static final BlockDefinition CHICKEN_STEW_BLOCK = feastBlock("chicken_stew_block", "Chicken Stew", "serving_pot");
    public static final BlockDefinition FISH_STEW_BLOCK = feastBlock("fish_stew_block", "Fish Stew", "serving_pot");
    public static final BlockDefinition SALAD_BLOCK = feastBlock("salad_block", "Chef Salad Feast", "mixing_bowl", "cooking_oil", "vinegar");
    public static final BlockDefinition CORNBREAD_FEAST = feastBlock(EDLModule.BAKING, "cornbread_feast", "Cornbread", "corn_meal", "flour", "butter", "egg_mix", "oven");
    public static final BlockDefinition CORN_PUDDING_FEAST = feastBlock("corn_pudding_feast", "Corn Pudding", "corn_meal", "cooked_corn", "butter", "egg_mix", "oven", "square_pan");
    public static final BlockDefinition APPLE_CRISP_FEAST = feastBlock(EDLModule.SWEETS, "apple_crisp_feast", "Apple Crisp", "flour", "butter", "sliced_apple", "oven", "square_pan");
    public static final BlockDefinition STUFFING_FEAST = feastBlock("stuffing_feast", "Stuffing", "serving_pot", "croutons", "butter");
    public static final BlockDefinition POTATO_AU_GRATIN_FEAST = feastBlock("potato_au_gratin_feast", "Potatoes Au Gratin", "flour", "cheese", "butter", "sliced_potato", "oven", "square_pan");
    public static final BlockDefinition CINNAMON_ROLLS_FEAST = feastBlock(EDLModule.BAKING, "cinnamon_rolls_feast", "Cinnamon Rolls", "butter", "ground_cinnamon", "frosting_white", "oven", "square_pan");
    public static final BlockDefinition MONKEY_BREAD_FEAST = feastBlock(EDLModule.SWEETS, "monkey_bread_feast", "Monkey Bread", "butter", "ground_cinnamon", "oven", "square_pan");
    public static final BlockDefinition CHRISTMAS_PUDDING_FEAST = feastBlock(EDLModule.SWEETS, "christmas_pudding_feast", "Christmas Pudding", "dried_fruit", "breadcrumbs", "flour", "ground_cinnamon", "oven", "square_pan");
    public static final BlockDefinition PUNCH_FEAST = feastBlock(EDLModule.SWEETS, "punch_feast", "Punch Bowl");
    public static final BlockDefinition MINT_LAMB_FEAST = feastBlock("mint_lamb_feast", "Mint Encrusted Lamb", "mint", "breadcrumbs", "cooking_oil", "oven", "tray");
    public static final BlockDefinition CHARCUTERIE_BOARD_FEAST = feastBlock("charcuterie_board_feast", "Charcuterie Board", "crackers", "cheese");
    public static final BlockDefinition BROWNIES_BLOCK = feastBlock(EDLModule.SWEETS, "brownies_block", "Brownies", "flour", "dark_chocolate_chips", "butter", "oven", "square_pan");
    public static final BlockDefinition BLONDIES_BLOCK = feastBlock(EDLModule.SWEETS, "blondies_block", "Blondies", "flour", "butterscotch", "butter", "oven", "square_pan");
    public static final BlockDefinition FUDGE_BLOCK = feastBlock(EDLModule.SWEETS, "fudge_block", "Fudge", "fudge", "tray");
    public static final BlockDefinition STICKY_TOFFEE_PUDDING_BLOCK = feastBlock(EDLModule.SWEETS, "sticky_toffee_pudding_block", "Sticky Toffee Pudding", "caramel_sauce", "flour", "butter", "oven", "square_pan");
    public static final BlockDefinition CRISP_RICE_TREATS_BLOCK = feastBlock(EDLModule.SWEETS, "crisp_rice_treats_block", "Puffed Rice Treats", "crisp_rice_treat", "tray");
    public static final BlockDefinition SCOTCHAROO_BLOCK = feastBlock(EDLModule.SWEETS, "scotcharoo_block", "Scotcharoos", "crisp_rice", "marshmallow", "butterscotch", "peanut_butter_bottle", "tray");
    public static final BlockDefinition BLACK_FOREST_TRIFLE_BLOCK = feastBlock(EDLModule.SWEETS, "black_forest_trifle_block", "Black Forest Trifle", "chocolate_cake", "whipped_cream");
    public static final BlockDefinition PORK_AND_APPLES_FEAST = feastBlock("pork_and_apples_feast", "Pork and Apples", "pork_roast", "sliced_apple", "sliced_onion", "broth", "butter", "oven", "square_pan");
    public static final BlockDefinition STUFFED_APPLES_FEAST = feastBlock(EDLModule.SWEETS, "stuffed_apples_feast", "Stuffed Apples", "dried_fruit", "butter", "roasted_hazelnuts", "ground_cinnamon", "oven", "square_pan");
    public static final BlockDefinition CHILI_CON_CARNE_FEAST = feastBlock("chili_con_carne_feast", "Chili con Carne", "chili_powder", "broth");
    public static final BlockDefinition WHITE_CHILI_FEAST = feastBlock("white_chili_feast", "White Chili", "sliced_chili", "broth");
    public static final BlockDefinition BRUSCHETTA_FEAST = feastBlock("bruschetta_feast", "Bruschetta Board", "bread_slice", "sliced_onion", "sliced_tomato", "cooking_oil", "vinegar", "garlic_clove", "cheese");
    public static final BlockDefinition PUMPKIN_ROLL_FEAST = pieFeastBlock(EDLModule.BAKING, "pumpkin_roll_feast", "Pumpkin Roll", "egg_mix", "sheet", "oven");
    public static final BlockDefinition CHEESECAKE = pieFeastBlock("cheesecake", "Cheesecake", "pie_dish");
    public static final BlockDefinition CHOCOLATE_CHEESECAKE = pieFeastBlock("chocolate_cheesecake", "Chocolate Cheesecake", "pie_dish", "cocoa_powder");
    public static final BlockDefinition HONEY_CHEESECAKE = pieFeastBlock("honey_cheesecake", "Honey Cheesecake", "pie_dish");
    public static final BlockDefinition GLOW_BERRY_CHEESECAKE = pieFeastBlock("glow_berry_cheesecake", "Glow Berry Cheesecake", "pie_dish");
    public static final BlockDefinition PUMPKIN_CHEESECAKE = pieFeastBlock("pumpkin_cheesecake", "Pumpkin Cheesecake", "pie_dish");
    public static final BlockDefinition APPLE_CHEESECAKE = pieFeastBlock("apple_cheesecake", "Apple Cheesecake", "pie_dish");
    public static final BlockDefinition SWEET_BERRY_PIE = pieFeastBlock("sweet_berry_pie", "Sweet Berry Pie", "pie_dish", "flour");
    public static final BlockDefinition GLOW_BERRY_PIE = pieFeastBlock("glow_berry_pie", "Glow Berry Pie", "pie_dish", "flour");
    public static final BlockDefinition KEY_LIME_PIE = pieFeastBlock("key_lime_pie", "Key Lime Pie", "pie_dish", "lime_juice", "lime_zest", "stiff_peaks", "whipped_cream");
    public static final BlockDefinition LEMON_MERINGUE_PIE = pieFeastBlock("lemon_meringue_pie", "Lemon Meringue Pie", "pie_dish", "stiff_peaks", "lemon_curd");
    public static final BlockDefinition CARAMEL_CHEESECAKE = pieFeastBlock("caramel_cheesecake", "Caramel Cheesecake", "pie_dish", "caramel_sauce");
    public static final BlockDefinition PUMPKIN_PIE = pieFeastBlock("pumpkin_pie", "Pumpkin Pie", "pie_dish", "egg_mix");
    public static final BlockDefinition MEAT_PIE = pieFeastBlock(EDLModule.MEALS, "meat_pie", "Meat Pie", "pie_dish", "ground_beef");
    public static final BlockDefinition BACON_EGG_PIE = pieFeastBlock(EDLModule.MEALS, "bacon_egg_pie", "Bacon and Egg Pie", "pie_dish");
    public static final BlockDefinition STEAK_PICKLED_ONION_PIE = pieFeastBlock(EDLModule.MEALS, "steak_pickled_onion_pie", "Steak and Pickled Onion Pie", "pie_dish", "cubed_beef", "pickled_onion_item", "gravy_boat_item", "cheese");
    public static final BlockDefinition MISSISSIPPI_MUD_PIE = pieFeastBlock("mississippi_mud_pie", "Mississippi Mud Pie", "pie_dish", "chocolate_cookie", "chocolate_custard", "whipped_cream");
    public static final BlockDefinition GRASSHOPPER_PIE = pieFeastBlock("grasshopper_pie", "Grasshopper Pie", "pie_dish", "mint", "chocolate_cookie", "whipped_cream");
    public static final BlockDefinition COFFEE_CAKE = cakeFeastBlock("coffee_cake", "Coffee Cake", "square_pan", "ground_cinnamon");
    public static final BlockDefinition CHOCOLATE_CAKE = cakeFeastBlock("chocolate_cake", "Chocolate Cake", "square_pan");
    public static final BlockDefinition KYIV_CAKE = pieFeastBlock("kyiv_cake", "Kyiv Cake", "sheet", "stiff_peaks", "roasted_hazelnuts", "whipped_cream");
    public static final BlockDefinition QUICHE = pieFeastBlock(EDLModule.MEALS, "quiche", "Quiche", "pie_dish", "omelette_mix");
    public static final BlockDefinition MILK_TART = pieFeastBlock("milk_tart", "Milk Tart", "pie_dish", "ground_cinnamon");
    public static final BlockDefinition TARTE_TATIN = pieFeastBlock("tarte_tatin", "Tarte Tatin", "caramel_sauce");
    public static final BlockDefinition PANFORTE = pieFeastBlock(EDLModule.BAKING, "panforte", "Panforte", "pie_dish", "dried_fruit", "roasted_hazelnuts", "flour", "ground_cinnamon", "dark_chocolate_chips", "oven");
    public static final BlockDefinition LEMON_CUCUMBER_CAKE = cakeFeastBlock("lemon_cucumber_cake", "Lemon Cucumber Cake", "square_pan", "sliced_cucumber_item", "lemon_zest", "lemon_juice", "frosting_white");
    public static final BlockDefinition MELON_LAYER_CAKE = cakeFeastBlock("melon_layer_cake", "Melon Layer Cake", "square_pan", "melon_juice", "frosting_pink", "frosting_green", "dark_chocolate_chips");
    public static final BlockDefinition PAVLOVA = pieFeastBlock("pavlova", "Pavlova", "sheet", "stiff_peaks", "whipped_cream");
    public static final BlockDefinition MELON_FRUIT_SALAD = feastBlock(EDLModule.SWEETS, "melon_fruit_salad", "Melon Fruit Salad",
        "melon_chunks", "sliced_apple", "sliced_orange", "mint", "honey_bottle", "mixing_bowl");
    public static final BlockDefinition MARSHMALLOW_SLICE_FEAST = feastBlock(EDLModule.SWEETS, "marshmallow_slice_feast", "Marshmallow Slice",
        "dynamic_jam", "marshmallow_fluff_bottle", "sugar_cookie", "chiller", "square_pan");
    public static final BlockDefinition RAW_BAKED_ALASKA = simpleBlock(
        EDLModule.SWEETS,
        "raw_baked_alaska",
        "Un-Baked Alaska",
        () -> new BlockRawBakedAlaska(() -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ExtraDelightLegacy.MODID, "baked_alaska"))),
        block -> new ItemBlockTooltip(block, ExtraDelightLegacy.MODID + ".raw_baked_alaska.tooltip", TextFormatting.BLUE),
        "stiff_peaks",
        "ice_cream"
    );
    public static final BlockDefinition BAKED_ALASKA = feastBlock(EDLModule.SWEETS, "baked_alaska", "Baked Alaska", "raw_baked_alaska");
    public static final BlockDefinition SOY_GLAZED_SALMON_BLOCK = feastBlock("soy_glazed_salmon_block", "Soy-Glazed Salmon", "soy_sauce", "cooking_oil");
    public static final BlockDefinition CHEESYMITE_SCROLL_BLOCK = feastBlock(EDLModule.BAKING, "cheesymite_scroll_block", "Cheesymite Scrolls", "cheese", "yeast_spread", "dough", "butter", "square_pan");
    public static final BlockDefinition JELLY_WHITE_BLOCK = jellyBlock("jelly_white_block", "Coconut Jelly", "jelly_white");
    public static final BlockDefinition JELLY_ORANGE_BLOCK = jellyBlock("jelly_orange_block", "Orange Jelly", "jelly_orange");
    public static final BlockDefinition JELLY_MAGENTA_BLOCK = jellyBlock("jelly_magenta_block", "Mixed Berry Jelly", "jelly_magenta");
    public static final BlockDefinition JELLY_LIGHT_BLUE_BLOCK = jellyBlock("jelly_light_blue_block", "Blue Raspberry Jelly", "jelly_light_blue");
    public static final BlockDefinition JELLY_YELLOW_BLOCK = jellyBlock("jelly_yellow_block", "Lemon Jelly", "jelly_yellow");
    public static final BlockDefinition JELLY_LIME_BLOCK = jellyBlock("jelly_lime_block", "Lime Jelly", "jelly_lime");
    public static final BlockDefinition JELLY_PINK_BLOCK = jellyBlock("jelly_pink_block", "Strawberry Jelly", "jelly_pink");
    public static final BlockDefinition JELLY_GREY_BLOCK = jellyBlock("jelly_grey_block", "Mystery Jelly", "jelly_grey");
    public static final BlockDefinition JELLY_LIGHT_GREY_BLOCK = jellyBlock("jelly_light_grey_block", "Dragonfruit Jelly", "jelly_light_grey");
    public static final BlockDefinition JELLY_CYAN_BLOCK = jellyBlock("jelly_cyan_block", "Punch Jelly", "jelly_cyan");
    public static final BlockDefinition JELLY_PURPLE_BLOCK = jellyBlock("jelly_purple_block", "Grape Jelly", "jelly_purple");
    public static final BlockDefinition JELLY_BLUE_BLOCK = jellyBlock("jelly_blue_block", "Blueberry Jelly", "jelly_blue");
    public static final BlockDefinition JELLY_BROWN_BLOCK = jellyBlock("jelly_brown_block", "Cola Jelly", "jelly_brown");
    public static final BlockDefinition JELLY_GREEN_BLOCK = jellyBlock("jelly_green_block", "Apple Jelly", "jelly_green");
    public static final BlockDefinition JELLY_RED_BLOCK = jellyBlock("jelly_red_block", "Cherry Jelly", "jelly_red");
    public static final BlockDefinition JELLY_BLACK_BLOCK = jellyBlock("jelly_black_block", "Blackberry Jelly", "jelly_black");
    public static final BlockDefinition LEMONADE_TRAY = feastBlock(EDLModule.SWEETS, "lemonade_tray", "Tray of Lemonade", "lemonade");
    public static final BlockDefinition LIMEADE_TRAY = feastBlock(EDLModule.SWEETS, "limeade_tray", "Tray of Limeade", "limeade");
    public static final BlockDefinition ORANGEADE_TRAY = feastBlock(EDLModule.SWEETS, "orangeade_tray", "Tray of Orangeade", "orangeade");
    public static final BlockDefinition BAKED_COD = feastBlock("baked_cod", "Baked Cod", "tray", "cooking_oil", "sliced_lemon", "garlic_clove", "salt", "oven");

    private EDLBlocks() {
    }

    public static void register(IForgeRegistry<Block> registry) {
        EDLItems.ensureLoaded();
        for (BlockDefinition definition : DEFINITIONS) {
            if (!definition.canRegister()) {
                logFondue("Skipped block {}", definition);
                continue;
            }

            Block block = definition.factory.get();
            block.setRegistryName(ExtraDelightLegacy.MODID, definition.id);
            block.setUnlocalizedName(ExtraDelightLegacy.MODID + "." + definition.id);
            registry.register(block);
            definition.block = block;
            definition.entry.markRegistered();
            logFondue("Registered block {}", definition);
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        for (BlockDefinition definition : DEFINITIONS) {
            if (!definition.hasItemBlock || definition.block == null) {
                continue;
            }

            Item itemBlock = definition.itemBlockFactory == null
                ? new ItemBlock(definition.block)
                : definition.itemBlockFactory.create(definition.block);
            itemBlock.setRegistryName(definition.block.getRegistryName());
            itemBlock.setUnlocalizedName(itemBlock instanceof ItemDoor
                ? ExtraDelightLegacy.MODID + "." + definition.id
                : definition.block.getUnlocalizedName());
            itemBlock.setCreativeTab(definition.getCreativeTab());
            registry.register(itemBlock);
            definition.itemBlock = itemBlock;
            REGISTERED_ITEM_BLOCKS.add(itemBlock);
            logFondue("Registered ItemBlock {}", definition);
        }
    }

    public static void registerCreativeStacks() {
        for (BlockDefinition definition : DEFINITIONS) {
            if (definition.itemBlock != null && !definition.hasVanillaCreativeTab()) {
                EDLContentRegistry.addCreativeStack(definition.module, () -> new ItemStack(definition.itemBlock));
                logFondue("Added creative stack {}", definition);
            }
        }
    }

    public static List<Block> getRegisteredBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (BlockDefinition definition : DEFINITIONS) {
            if (definition.block != null) {
                blocks.add(definition.block);
            }
        }
        return Collections.unmodifiableList(blocks);
    }

    public static List<Item> getRegisteredItemBlocks() {
        return Collections.unmodifiableList(REGISTERED_ITEM_BLOCKS);
    }

    private static BlockDefinition crop(String id, String configName, Supplier<Block> factory, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(EDLModule.CROPS, id, configName, factory, false, false, null, dependencies);
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition storageBlock(String id, String configName, Material material, SoundType soundType, String... dependencies) {
        return storageBlock(EDLModule.KITCHEN_DECOR, id, configName, material, soundType, dependencies);
    }

    private static BlockDefinition storageBlock(EDLModule module, String id, String configName,
                                                Material material, SoundType soundType, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            module,
            id,
            configName,
            () -> new BlockStorage(material, soundType),
            true,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition chocolateStorageBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockStyleable(Material.CAKE, SoundType.CLOTH, 6),
            dependencies
        );
    }

    private static BlockDefinition woodStorageBlock(String id, String configName, String... dependencies) {
        return storageBlock(EDLModule.KITCHEN_DECOR, id, configName, Material.WOOD, SoundType.WOOD, dependencies)
            .vanillaCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    private static BlockDefinition woodPillarBlock(String id, String configName, String... dependencies) {
        return woodPillarBlock(EDLModule.KITCHEN_DECOR, id, configName, dependencies);
    }

    private static BlockDefinition woodPillarBlock(EDLModule module, String id, String configName, String... dependencies) {
        return simpleBlock(module, id, configName, () -> new BlockSimplePillar(Material.WOOD, SoundType.WOOD), dependencies);
    }

    private static BlockDefinition woodDoubleSlabBlock(String id, String configName, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleSlab(Material.WOOD, SoundType.WOOD, true),
            false,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition woodSlabBlock(String id, String configName, BlockDefinition doubleSlab, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleSlab(Material.WOOD, SoundType.WOOD, false),
            block -> new ItemSlab(block, (BlockSimpleSlab) block, (BlockSimpleSlab) doubleSlab.getBlock()),
            dependencies
        ).vanillaCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    private static BlockDefinition woodStairsBlock(String id, String configName, Supplier<Block> baseBlock, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleStairs(baseBlock.get().getDefaultState(), SoundType.WOOD),
            dependencies
        ).vanillaCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    private static BlockDefinition woodFenceBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleFence(Material.WOOD, SoundType.WOOD),
            dependencies
        ).vanillaCreativeTab(CreativeTabs.DECORATIONS);
    }

    private static BlockDefinition woodFenceGateBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleFenceGate(SoundType.WOOD),
            dependencies
        ).vanillaCreativeTab(CreativeTabs.REDSTONE);
    }

    private static BlockDefinition woodDoorBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleDoor(Material.WOOD, SoundType.WOOD),
            ItemDoor::new,
            dependencies
        ).vanillaCreativeTab(CreativeTabs.REDSTONE);
    }

    private static BlockDefinition woodTrapdoorBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleTrapDoor(Material.WOOD, SoundType.WOOD),
            dependencies
        ).vanillaCreativeTab(CreativeTabs.REDSTONE);
    }

    private static BlockDefinition doubleSlabBlock(String id, String configName, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleSlab(Material.CAKE, SoundType.CLOTH, true),
            false,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition slabBlock(String id, String configName, BlockDefinition doubleSlab, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleSlab(Material.CAKE, SoundType.CLOTH, false),
            block -> new ItemSlab(block, (BlockSimpleSlab) block, (BlockSimpleSlab) doubleSlab.getBlock()),
            dependencies
        );
    }

    private static BlockDefinition stairsBlock(String id, String configName, Supplier<Block> baseBlock, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleStairs(baseBlock.get().getDefaultState(), SoundType.CLOTH),
            dependencies
        );
    }

    private static BlockDefinition fenceBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleFence(Material.CAKE, SoundType.CLOTH),
            dependencies
        );
    }

    private static BlockDefinition fenceGateBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleFenceGate(SoundType.CLOTH),
            dependencies
        );
    }

    private static BlockDefinition doorBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleDoor(Material.CAKE, SoundType.CLOTH),
            ItemDoor::new,
            dependencies
        );
    }

    private static BlockDefinition trapdoorBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimpleTrapDoor(Material.CAKE, SoundType.CLOTH),
            dependencies
        );
    }

    private static BlockDefinition pillarBlock(String id, String configName, String... dependencies) {
        return simpleBlock(
            EDLModule.KITCHEN_DECOR,
            id,
            configName,
            () -> new BlockSimplePillar(Material.CAKE, SoundType.CLOTH),
            dependencies
        );
    }

    private static BlockDefinition simpleBlock(EDLModule module, String id, String configName, Supplier<Block> factory, String... dependencies) {
        return simpleBlock(module, id, configName, factory, null, dependencies);
    }

    private static BlockDefinition simpleBlock(EDLModule module, String id, String configName, Supplier<Block> factory,
                                               ItemBlockFactory itemBlockFactory, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            module,
            id,
            configName,
            factory,
            true,
            true,
            itemBlockFactory,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition technicalBlock(String id, String configName, Supplier<Block> factory) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.PICKLING,
            id,
            configName,
            factory,
            false,
            true,
            null
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition noItemBlock(EDLModule module, String id, String configName,
                                               Supplier<Block> factory, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            module,
            id,
            configName,
            factory,
            false,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition foodLayerBlock(String id, String configName, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.CORE,
            id,
            configName,
            () -> new BlockThinLayer(Material.CLOTH, SoundType.CLOTH),
            true,
            true,
            block -> new ItemFoodBlock(block, 1, 0.6F),
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition hangingBlock(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.KITCHEN_DECOR, id, configName, BlockHanging::new, dependencies);
    }

    private static List<BlockDefinition> coloredDecorBlocks(String suffix, String configSuffix, Material material, SoundType soundType) {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.HOME_DECOR,
                suffix + "_" + color,
                toTitle(color) + " " + configSuffix,
                () -> new BlockStorage(material, soundType)
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> moldedWallpaperBlocks(String woodId, String woodName) {
        return moldedWallpaperBlocks(woodId, woodName, new String[0]);
    }

    private static List<BlockDefinition> moldedWallpaperBlocks(String woodId, String woodName, String... dependencies) {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.HOME_DECOR,
                woodId + "_molded_" + color + "_wallpaper",
                woodName + " Molded " + toTitle(color) + " Wallpaper",
                BlockMoldedWallpaper::new,
                dependencies
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> externalMoldedWallpaperBlocks(String woodId, String woodName,
                                                                        String[] externalItemAlternatives) {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(externalBlock(
                EDLModule.HOME_DECOR,
                woodId + "_molded_" + color + "_wallpaper",
                woodName + " Molded " + toTitle(color) + " Wallpaper",
                BlockMoldedWallpaper::new,
                externalItemAlternatives
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> ribbonBowBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.HOME_DECOR,
                "ribbon_bow_" + color,
                toTitle(color) + " Bow",
                BlockRibbonBow::new
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> woodDecorBlocks(String suffix, String configSuffix, Supplier<Block> factory) {
        return woodDecorBlocks(EDLModule.HOME_DECOR, suffix, configSuffix, factory);
    }

    private static List<BlockDefinition> woodDecorBlocks(EDLModule module, String suffix, String configSuffix, Supplier<Block> factory) {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                module,
                VANILLA_WOOD_IDS[i] + "_" + suffix,
                VANILLA_WOOD_NAMES[i] + " " + configSuffix,
                factory
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> foodDisplayBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                VANILLA_WOOD_IDS[i] + "_food_display",
                VANILLA_WOOD_NAMES[i] + " Food Display",
                BlockFoodDisplay::new
            ));
        }
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_food_display", "Cinnamon Food Display", BlockFoodDisplay::new, "cinnamon_slab"));
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_food_display", "Fruit Food Display", BlockFoodDisplay::new, "fruit_slab"));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_food_display",
            "Crimson Food Display",
            BlockFoodDisplay::new,
            new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"}
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_food_display",
            "Warped Food Display",
            BlockFoodDisplay::new,
            new String[]{"nb:warped_slab_half", "futuremc:warped_slab"}
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_food_display",
            "Cherry Food Display",
            BlockFoodDisplay::new,
            new String[]{"suikecherry:cherry_slab"}
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> knifeBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                VANILLA_WOOD_IDS[i] + "_knife_block",
                VANILLA_WOOD_NAMES[i] + " Knife Block",
                BlockKnifeBlock::new
            ));
        }
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_knife_block", "Cinnamon Knife Block", BlockKnifeBlock::new, "cinnamon_slab"));
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_knife_block", "Fruit Knife Block", BlockKnifeBlock::new, "fruit_slab"));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_knife_block",
            "Crimson Knife Block",
            BlockKnifeBlock::new,
            new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"}
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_knife_block",
            "Warped Knife Block",
            BlockKnifeBlock::new,
            new String[]{"nb:warped_slab_half", "futuremc:warped_slab"}
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_knife_block",
            "Cherry Knife Block",
            BlockKnifeBlock::new,
            new String[]{"suikecherry:cherry_slab"}
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> halfCabinetBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                VANILLA_WOOD_IDS[i] + "_half_cabinet",
                VANILLA_WOOD_NAMES[i] + " Half Cabinet",
                BlockHalfCabinet::new
            ));
        }
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_half_cabinet", "Cinnamon Half Cabinet", BlockHalfCabinet::new, "cinnamon_cabinet"));
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_half_cabinet", "Fruit Half Cabinet", BlockHalfCabinet::new, "fruit_cabinet"));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_half_cabinet",
            "Crimson Half Cabinet",
            BlockHalfCabinet::new,
            new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"},
            "crimson_cabinet"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_half_cabinet",
            "Warped Half Cabinet",
            BlockHalfCabinet::new,
            new String[]{"nb:warped_slab_half", "futuremc:warped_slab"},
            "warped_cabinet"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_half_cabinet",
            "Cherry Half Cabinet",
            BlockHalfCabinet::new,
            new String[]{"suikecherry:cherry_slab"},
            "cherry_cabinet"
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> counterCabinetBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                VANILLA_WOOD_IDS[i] + "_counter_cabinet",
                VANILLA_WOOD_NAMES[i] + " Counter Cabinet",
                BlockCounterCabinet::new
            ));
        }
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_counter_cabinet", "Cinnamon Counter Cabinet", BlockCounterCabinet::new, "cinnamon_cabinet"));
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_counter_cabinet", "Fruit Counter Cabinet", BlockCounterCabinet::new, "fruit_cabinet"));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_counter_cabinet",
            "Crimson Counter Cabinet",
            BlockCounterCabinet::new,
            new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"},
            "crimson_cabinet"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_counter_cabinet",
            "Warped Counter Cabinet",
            BlockCounterCabinet::new,
            new String[]{"nb:warped_slab_half", "futuremc:warped_slab"},
            "warped_cabinet"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_counter_cabinet",
            "Cherry Counter Cabinet",
            BlockCounterCabinet::new,
            new String[]{"suikecherry:cherry_slab"},
            "cherry_cabinet"
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> sinkCabinetBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < VANILLA_WOOD_IDS.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                VANILLA_WOOD_IDS[i] + "_sink",
                VANILLA_WOOD_NAMES[i] + " Sink Cabinet",
                BlockSinkCabinet::new,
                "tap"
            ));
        }
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "cinnamon_sink", "Cinnamon Sink Cabinet", BlockSinkCabinet::new, "cinnamon_cabinet", "tap"));
        definitions.add(simpleBlock(EDLModule.KITCHEN_DECOR, "fruit_sink", "Fruit Sink Cabinet", BlockSinkCabinet::new, "fruit_cabinet", "tap"));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_sink",
            "Crimson Sink Cabinet",
            BlockSinkCabinet::new,
            new String[]{"nb:crimson_slab_half", "futuremc:crimson_slab"},
            "crimson_cabinet",
            "tap"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_sink",
            "Warped Sink Cabinet",
            BlockSinkCabinet::new,
            new String[]{"nb:warped_slab_half", "futuremc:warped_slab"},
            "warped_cabinet",
            "tap"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_sink",
            "Cherry Sink Cabinet",
            BlockSinkCabinet::new,
            new String[]{"suikecherry:cherry_slab"},
            "cherry_cabinet",
            "tap"
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static BlockDefinition externalBlock(EDLModule module, String id, String configName, Supplier<Block> factory,
                                                 String[] externalItemAlternatives, String... dependencies) {
        return externalBlock(module, id, configName, factory, new String[][]{externalItemAlternatives}, dependencies);
    }

    private static BlockDefinition externalBlock(EDLModule module, String id, String configName, Supplier<Block> factory,
                                                 String[] firstExternalAlternatives, String[] secondExternalAlternatives,
                                                 String... dependencies) {
        return externalBlock(module, id, configName, factory,
            new String[][]{firstExternalAlternatives, secondExternalAlternatives}, dependencies);
    }

    private static BlockDefinition externalBlock(EDLModule module, String id, String configName, Supplier<Block> factory,
                                                 String[][] externalItemAlternatives, String... dependencies) {
        BlockDefinition definition = simpleBlock(module, id, configName, factory, dependencies);
        for (String[] alternatives : externalItemAlternatives) {
            definition.requireAnyExternalItem(alternatives);
        }
        return definition;
    }

    private static BlockDefinition homeDecorBlock(String id, String configName, Supplier<Block> factory, String... dependencies) {
        return simpleBlock(EDLModule.HOME_DECOR, id, configName, factory, dependencies);
    }

    private static BlockDefinition kitchenDecorBlock(String id, String configName, Supplier<Block> factory, String... dependencies) {
        return simpleBlock(EDLModule.KITCHEN_DECOR, id, configName, factory, dependencies);
    }

    private static BlockDefinition externalHomeDecorBlock(String id, String configName, Supplier<Block> factory,
                                                          String[] externalItemAlternatives, String... dependencies) {
        return externalBlock(EDLModule.HOME_DECOR, id, configName, factory, externalItemAlternatives, dependencies);
    }

    private static BlockDefinition externalKitchenDecorBlock(String id, String configName, Supplier<Block> factory,
                                                             String[] externalItemAlternatives, String... dependencies) {
        return externalBlock(EDLModule.KITCHEN_DECOR, id, configName, factory, externalItemAlternatives, dependencies);
    }

    private static BlockDefinition wildCrop(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.CROPS, id, configName, BlockWildCrop::new, dependencies);
    }

    private static BlockDefinition petalLitter(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.HOME_DECOR, id, configName, BlockPetalLitter::new, dependencies);
    }

    private static BlockDefinition pottedSapling(String id, String configName, Supplier<Item> saplingSupplier, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.ORCHARD,
            id,
            configName,
            () -> new BlockPottedSapling(saplingSupplier),
            false,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static List<BlockDefinition> driedCornFenceBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        String[] ids = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia", "cinnamon", "fruit"};
        String[] names = {"Oak", "Spruce", "Birch", "Dark Oak", "Jungle", "Acacia", "Cinnamon", "Fruit"};
        for (int i = 0; i < ids.length; i++) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                ids[i] + "_dried_corn_fence",
                names[i] + " Dried Corn Fence",
                () -> new BlockSimpleFence(Material.WOOD, SoundType.PLANT),
                "dried_corn_husk"
            ));
        }
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "crimson_dried_corn_fence",
            "Dried Corn Crimson Fence",
            () -> new BlockSimpleFence(Material.WOOD, SoundType.PLANT),
            new String[]{"nb:crimson_fence", "futuremc:crimson_fence"},
            "dried_corn_husk"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "warped_dried_corn_fence",
            "Dried Corn Warped Fence",
            () -> new BlockSimpleFence(Material.WOOD, SoundType.PLANT),
            new String[]{"nb:warped_fence", "futuremc:warped_fence"},
            "dried_corn_husk"
        ));
        definitions.add(externalBlock(
            EDLModule.KITCHEN_DECOR,
            "cherry_dried_corn_fence",
            "Dried Corn Cherry Fence",
            () -> new BlockSimpleFence(Material.WOOD, SoundType.PLANT),
            new String[]{"suikecherry:cherry_fence"},
            "dried_corn_husk"
        ));
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> coloredCarpetBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.HOME_DECOR,
                "gingham_carpet_" + color,
                toTitle(color) + " Gingham Carpet",
                BlockDecorCarpet::new,
                "gingham_" + color
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> picnicBasketBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.KITCHEN_DECOR,
                color + "_picnic_basket",
                toTitle(color) + " Picnic Basket",
                BlockPicnicBasket::new,
                "gingham_carpet_" + color
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> frostedGingerbreadBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.HOME_DECOR,
                color + "_frosted_gingerbread_block",
                toTitle(color) + " Frosted Gingerbread Block",
                () -> new BlockCutoutStyleable(Material.CAKE, SoundType.CLOTH, 11),
                "gingerbread_cookie_block",
                "frosting_" + color
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static List<BlockDefinition> chocolateBoxBlocks() {
        List<BlockDefinition> definitions = new ArrayList<>();
        for (String color : COLOR_IDS) {
            definitions.add(simpleBlock(
                EDLModule.SWEETS,
                color + "_chocolate_box",
                toTitle(color) + " Chocolate Box",
                BlockChocolateBox::new,
                "candy_white",
                "dark_chocolate_bar"
            ));
        }
        return Collections.unmodifiableList(definitions);
    }

    private static String toTitle(String id) {
        String[] parts = id.split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return builder.toString();
    }

    private static BlockDefinition pickleJar(String id, String configName, Supplier<Item> servingSupplier, String... dependencies) {
        String[] fullDependencies = new String[dependencies.length + 1];
        fullDependencies[0] = "vat";
        System.arraycopy(dependencies, 0, fullDependencies, 1, dependencies.length);
        return simpleBlock(EDLModule.PICKLING, id, configName, () -> new BlockPickleJar(servingSupplier), ItemPickleJarBlock::new, fullDependencies);
    }

    private static BlockDefinition cookieBlock(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.BAKING, id, configName, () -> new BlockStorage(Material.CLOTH, SoundType.CLOTH), dependencies);
    }

    private static BlockDefinition feastBlock(String id, String configName, String... dependencies) {
        return feastBlock(EDLModule.MEALS, id, configName, dependencies);
    }

    private static BlockDefinition feastBlock(EDLModule module, String id, String configName, String... dependencies) {
        return simpleBlock(module, id, configName, () -> new BlockRecipeFeast(true), ItemFeastBlock::new, dependencies);
    }

    private static BlockDefinition pieFeastBlock(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.SWEETS, id, configName, () -> new BlockRecipeFeast(false), ItemFeastBlock::new, dependencies);
    }

    private static BlockDefinition pieFeastBlock(EDLModule module, String id, String configName, String... dependencies) {
        return simpleBlock(module, id, configName, () -> new BlockRecipeFeast(false), ItemFeastBlock::new, dependencies);
    }

    private static BlockDefinition cakeFeastBlock(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.SWEETS, id, configName, () -> new BlockRecipeFeast(false, 7), ItemFeastBlock::new, dependencies);
    }

    private static BlockDefinition jellyBlock(String id, String configName, String... dependencies) {
        return simpleBlock(EDLModule.SWEETS, id, configName, () -> new BlockTranslucentRecipeFeast(true), ItemFeastBlock::new, dependencies);
    }

    private static BlockDefinition horizontalPan(String id, String configName, AxisAlignedBB northSouthBox, AxisAlignedBB eastWestBox) {
        return horizontalPan(id, configName, northSouthBox, eastWestBox, Material.IRON, SoundType.METAL);
    }

    private static BlockDefinition horizontalPan(String id, String configName, AxisAlignedBB northSouthBox,
                                                 AxisAlignedBB eastWestBox, Material material, SoundType soundType) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.KITCHEN,
            id,
            configName,
            () -> new BlockHorizontalPan(material, soundType, northSouthBox, eastWestBox),
            true,
            true,
            ItemStyleableBlock::new
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition orchardSapling(String id, String configName, Supplier<Block> leavesSupplier,
                                                  Supplier<Block> logSupplier, int baseHeight, String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.ORCHARD,
            id,
            configName,
            () -> new BlockOrchardSapling(leavesSupplier, logSupplier, baseHeight),
            true,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static BlockDefinition orchardLeaves(String id, String configName,
                                                 Supplier<Item> fruitSupplier,
                                                 Supplier<Item> saplingSupplier,
                                                 String... dependencies) {
        return orchardLeaves(id, configName, fruitSupplier, saplingSupplier, null, true, dependencies);
    }

    private static BlockDefinition orchardLeaves(String id, String configName,
                                                 Supplier<Item> fruitSupplier,
                                                 Supplier<Item> saplingSupplier,
                                                 Supplier<Block> petalLitterSupplier,
                                                 String... dependencies) {
        return orchardLeaves(id, configName, fruitSupplier, saplingSupplier, petalLitterSupplier, true, dependencies);
    }

    private static BlockDefinition orchardLeaves(String id, String configName,
                                                 Supplier<Item> fruitSupplier,
                                                 Supplier<Item> saplingSupplier,
                                                 boolean fruiting,
                                                 String... dependencies) {
        return orchardLeaves(id, configName, fruitSupplier, saplingSupplier, null, fruiting, dependencies);
    }

    private static BlockDefinition orchardLeaves(String id, String configName,
                                                 Supplier<Item> fruitSupplier,
                                                 Supplier<Item> saplingSupplier,
                                                 Supplier<Block> petalLitterSupplier,
                                                 boolean fruiting,
                                                 String... dependencies) {
        BlockDefinition definition = new BlockDefinition(
            EDLModule.ORCHARD,
            id,
            configName,
            () -> new BlockOrchardLeaves(fruitSupplier, saplingSupplier, petalLitterSupplier, fruiting),
            true,
            true,
            null,
            dependencies
        );
        DEFINITIONS.add(definition);
        return definition;
    }

    private static Block getCornCropBlock() {
        return CORN_CROP.getBlock();
    }

    private static Block getCornTopCropBlock() {
        return CORN_TOP_CROP.getBlock();
    }

    private static void logFondue(String message, BlockDefinition definition) {
        if (definition != null && definition.id.contains("fondue") && ExtraDelightLegacy.logger != null) {
            ExtraDelightLegacy.logger.info(message, definition.id);
        }
    }

    private static ItemStack[] bubblePotRemainders() {
        return new ItemStack[]{new ItemStack(Items.FLOWER_POT), canvasStack()};
    }

    private static ItemStack canvasStack() {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("farmersdelight", "canvas"));
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    public static final class BlockDefinition {
        private final String id;
        private final EDLModule module;
        private final EDLContentEntry entry;
        private final Supplier<Block> factory;
        private final boolean hasItemBlock;
        private final boolean allowPendingDependencies;
        private final ItemBlockFactory itemBlockFactory;
        private final List<String[]> requiredExternalAlternatives = new ArrayList<>();
        private CreativeTabs creativeTabOverride;
        private Block block;
        private Item itemBlock;

        private BlockDefinition(EDLModule module, String id, String configName, Supplier<Block> factory,
                                boolean hasItemBlock, boolean allowPendingDependencies,
                                ItemBlockFactory itemBlockFactory, String... dependencies) {
            this.id = id;
            this.module = module;
            this.entry = EDLContentRegistry.define(module, id, configName, dependencies);
            this.factory = factory;
            this.hasItemBlock = hasItemBlock;
            this.allowPendingDependencies = allowPendingDependencies;
            this.itemBlockFactory = itemBlockFactory;
        }

        public Block getBlock() {
            return block;
        }

        public Item getItemBlock() {
            return itemBlock;
        }

        public boolean isRegistered() {
            return block != null;
        }

        public ItemStack stack(int count) {
            return itemBlock == null ? ItemStack.EMPTY : new ItemStack(itemBlock, count);
        }

        private CreativeTabs getCreativeTab() {
            return creativeTabOverride == null ? EDLCreativeTabs.getTab(module) : creativeTabOverride;
        }

        private boolean hasVanillaCreativeTab() {
            return creativeTabOverride != null;
        }

        private BlockDefinition vanillaCreativeTab(CreativeTabs creativeTab) {
            this.creativeTabOverride = creativeTab;
            return this;
        }

        private BlockDefinition requireAnyExternalItem(String... itemIds) {
            this.requiredExternalAlternatives.add(itemIds);
            return this;
        }

        private boolean hasRequiredExternalItems() {
            for (String[] alternatives : requiredExternalAlternatives) {
                boolean found = false;
                for (String itemId : alternatives) {
                    ResourceLocation id = new ResourceLocation(itemId);
                    Item item = ForgeRegistries.ITEMS.getValue(id);
                    if (item != null && item != Items.AIR) {
                        found = true;
                        break;
                    }
                    Block externalBlock = ForgeRegistries.BLOCKS.getValue(id);
                    if (externalBlock != null && externalBlock != Blocks.AIR) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }

        private boolean canRegister() {
            if (!hasRequiredExternalItems()) {
                return false;
            }

            if (!allowPendingDependencies) {
                return entry.canRegister();
            }

            return xy177.extradelightlegacy.common.config.EDLConfig.isModuleEnabled(entry.getModule())
                && xy177.extradelightlegacy.common.config.EDLConfig.isEntryEnabled(entry)
                && EDLContentRegistry.areDependenciesEnabled(entry);
        }
    }

    private interface ItemBlockFactory {
        Item create(Block block);
    }
}
