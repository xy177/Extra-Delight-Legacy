package xy177.extradelightlegacy.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.content.EDLContentEntry;
import xy177.extradelightlegacy.common.content.EDLContentRegistry;
import xy177.extradelightlegacy.common.creative.EDLCreativeTabs;
import xy177.extradelightlegacy.common.fluid.BlockEdibleFluid;
import xy177.extradelightlegacy.common.item.ItemEDLFluidBucket;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EDLFluids {
    public static final String MILK_ID = "milk";
    public static final String VINEGAR_ID = "vinegar";
    private static final ResourceLocation MILK_STILL = new ResourceLocation(ExtraDelightLegacy.MODID, "liquid/milk_still");
    private static final ResourceLocation MILK_FLOWING = new ResourceLocation(ExtraDelightLegacy.MODID, "liquid/milk_flowing");
    private static final List<FluidDefinition> DEFINITIONS = new ArrayList<>();
    private static final List<Item> REGISTERED_BUCKETS = new ArrayList<>();
    private static Fluid milkFluid;

    public static final FluidDefinition OIL = define(
        "oil_fluid",
        "cooking_oil_fluid_block",
        "oil_fluid_bucket",
        "Cooking Oil",
        "liquid/oil_still",
        "liquid/oil_flow",
        0xffd3b945,
        1800,
        5000
    );
    public static final FluidDefinition NUT_BUTTER = define(
        "nut_butter_fluid",
        "peanut_butter_fluid_block",
        "peanut_butter_fluid_bucket",
        "Nut Butter",
        "liquid/nut_butter_still",
        "liquid/nut_butter_flow",
        0xff9e632b,
        6000,
        3000
    );
    public static final FluidDefinition COCOA_NUT_BUTTER_SPREAD = define(
        EDLModule.CORE,
        "cocoa_nut_butter_spread_fluid",
        "cocoa_nut_butter_spread_fluid_block",
        "cocoa_nut_butter_spread_fluid_bucket",
        "Chocolate Nut Butter Spread",
        "liquid/cocoa_nut_butter_spread_still",
        "liquid/cocoa_nut_butter_spread_flow",
        0xff2e1b11,
        3000,
        6000
    );
    public static final FluidDefinition COCOA_BUTTER = define(
        EDLModule.CORE,
        "cocoa_butter_fluid",
        "cocoa_butter_fluid_block",
        "cocoa_butter_fluid_bucket",
        "Cocoa Butter",
        "liquid/cocoa_butter_still",
        "liquid/cocoa_butter_flow",
        0xffffd68f,
        3000,
        6000
    );
    public static final FluidDefinition VINEGAR = define(
        EDLModule.CORE,
        VINEGAR_ID,
        "vinegar_fluid_block",
        "vinegar_fluid_bucket",
        "Vinegar",
        "liquid/vinegar_still",
        "liquid/vinegar_flow",
        0xffedeac9,
        1100,
        1200
    );
    public static final FluidDefinition BBQ = define(
        EDLModule.CORE,
        "bbq_fluid",
        "bbq_fluid_block",
        "bbq_fluid_bucket",
        "BBQ Sauce",
        "liquid/bbq_still",
        "liquid/bbq_flow",
        0xff180600,
        3000,
        6000
    );
    public static final FluidDefinition KETCHUP = define(
        EDLModule.CORE,
        "ketchup_fluid",
        "ketchup_fluid_block",
        "ketchup_fluid_bucket",
        "Ketchup",
        "liquid/ketchup_still",
        "liquid/ketchup_flow",
        0xff9b0000,
        3000,
        6000
    );
    public static final FluidDefinition MAYO = define(
        EDLModule.CORE,
        "mayo_fluid",
        "mayo_fluid_block",
        "mayo_fluid_bucket",
        "Mayo",
        "liquid/mayo_still",
        "liquid/mayo_flow",
        0xffded6ab,
        3000,
        6000
    );
    public static final FluidDefinition WHIPPED_CREAM = define(
        EDLModule.SWEETS,
        "whipped_cream_fluid",
        "whipped_cream_fluid_block",
        "whipped_cream_fluid_bucket",
        "Whipped Cream",
        "liquid/whipped_cream_still",
        "liquid/whipped_cream_flow",
        0xfff3f3f3,
        300,
        6000
    );
    public static final FluidDefinition CARAMEL_SAUCE = define(
        EDLModule.SWEETS,
        "caramel_sauce_fluid",
        "caramel_sauce_fluid_block",
        "caramel_sauce_fluid_bucket",
        "Caramel Sauce",
        "liquid/caramel_sauce_still",
        "liquid/caramel_sauce_flow",
        0xff431500,
        3000,
        6000
    );
    public static final FluidDefinition CUSTARD = define(
        EDLModule.SWEETS,
        "custard_fluid",
        "custard_fluid_block",
        "custard_fluid_bucket",
        "Custard",
        "liquid/custard_still",
        "liquid/custard_flow",
        0xfffff0a0,
        3000,
        6000
    );
    public static final FluidDefinition DARK_CHOCOLATE_SYRUP = define(
        EDLModule.SWEETS,
        "dark_chocolate_syrup_fluid",
        "dark_chocolate_syrup_fluid_block",
        "dark_chocolate_syrup_fluid_bucket",
        "Liquid Dark Chocolate",
        "liquid/dark_chocolate_syrup_still",
        "liquid/dark_chocolate_syrup_flow",
        0xff382014,
        3000,
        6000
    );
    public static final FluidDefinition MILK_CHOCOLATE_SYRUP = define(
        EDLModule.SWEETS,
        "milk_chocolate_syrup_fluid",
        "milk_chocolate_syrup_fluid_block",
        "milk_chocolate_syrup_fluid_bucket",
        "Liquid Milk Chocolate",
        "liquid/milk_chocolate_syrup_still",
        "liquid/milk_chocolate_syrup_flow",
        0xff573b2c,
        3000,
        6000
    );
    public static final FluidDefinition WHITE_CHOCOLATE_SYRUP = define(
        EDLModule.SWEETS,
        "white_chocolate_syrup_fluid",
        "white_chocolate_syrup_fluid_block",
        "white_chocolate_syrup_fluid_bucket",
        "Liquid White Chocolate",
        "liquid/white_chocolate_syrup_still",
        "liquid/white_chocolate_syrup_flow",
        0xffffd290,
        3000,
        6000
    );
    public static final FluidDefinition BLOOD = define(
        EDLModule.MEAT,
        "blood_fluid",
        "blood_fluid_block",
        "blood_fluid_bucket",
        "Blood",
        "liquid/blood_still",
        "liquid/blood_flow",
        0xff7a0505,
        2000,
        1600
    );
    public static final FluidDefinition BLOOD_CHOCOLATE_SYRUP = define(
        EDLModule.SWEETS,
        "blood_chocolate_syrup_fluid",
        "blood_chocolate_syrup_fluid_block",
        "blood_chocolate_syrup_fluid_bucket",
        "Liquid Blood Chocolate",
        "liquid/blood_chocolate_syrup_still",
        "liquid/blood_chocolate_syrup_flow",
        0xff45070a,
        3000,
        6000
    );
    public static final FluidDefinition MARSHMALLOW_FLUFF = define(
        EDLModule.SWEETS,
        "marshmallow_fluff_fluid",
        "marshmallow_fluff_fluid_block",
        "marshmallow_fluff_fluid_bucket",
        "Marshmallow Fluff",
        "liquid/marshmallow_fluff_still",
        "liquid/marshmallow_fluff_flow",
        0xfff9f1e8,
        3000,
        6000
    );
    public static final FluidDefinition BROTH = define(
        EDLModule.CORE,
        "broth_fluid",
        "broth_fluid_block",
        "broth_fluid_bucket",
        "Broth",
        "liquid/broth_still",
        "liquid/broth_flow",
        0xff9b6a30,
        1100,
        1200
    );
    public static final FluidDefinition TEA = define(
        EDLModule.CORE,
        "tea_fluid",
        "tea_fluid_block",
        "tea_fluid_bucket",
        "Tea",
        "liquid/tea_still",
        "liquid/tea_flow",
        0xff6d4d1f,
        1100,
        1200
    );
    public static final FluidDefinition SWEET_BERRY_JUICE = define(
        EDLModule.CORE,
        "sweet_berry_juice_fluid",
        "sweet_berry_juice_fluid_block",
        "sweet_berry_juice_fluid_bucket",
        "Sweet Berry Juice",
        "liquid/sweet_berry_juice_still",
        "liquid/sweet_berry_juice_flow",
        0xffc53246,
        1100,
        1200
    );
    public static final FluidDefinition GLOW_BERRY_JUICE = define(
        EDLModule.CORE,
        "glow_berry_juice_fluid",
        "glow_berry_juice_fluid_block",
        "glow_berry_juice_fluid_bucket",
        "Glow Berry Juice",
        "liquid/glow_berry_juice_still",
        "liquid/glow_berry_juice_flow",
        0xffff9b38,
        1100,
        1200
    );
    public static final FluidDefinition TOMATO_JUICE = define(
        EDLModule.CORE,
        "tomato_juice_fluid",
        "tomato_juice_fluid_block",
        "tomato_juice_fluid_bucket",
        "Tomato Juice",
        "liquid/tomato_juice_still",
        "liquid/tomato_juice_flow",
        0xffb0271d,
        1100,
        1200
    );
    public static final FluidDefinition CACTUS_JUICE = define(
        EDLModule.CORE,
        "cactus_juice_fluid",
        "cactus_juice_fluid_block",
        "cactus_juice_fluid_bucket",
        "Cactus Juice",
        "liquid/cactus_juice_still",
        "liquid/cactus_juice_flow",
        0xff5ba83b,
        1100,
        1200
    );
    public static final FluidDefinition MELON_JUICE = define(
        EDLModule.CORE,
        "melon_juice_fluid",
        "melon_juice_fluid_block",
        "melon_juice_fluid_bucket",
        "Melon Juice",
        "liquid/melon_juice_still",
        "liquid/melon_juice_flow",
        0xffff6f7d,
        1100,
        1200
    );
    public static final FluidDefinition PICKLE_JUICE = define(
        EDLModule.CORE,
        "pickle_juice_fluid",
        "pickle_juice_fluid_block",
        "pickle_juice_fluid_bucket",
        "Pickle Juice",
        "liquid/pickle_juice_still",
        "liquid/pickle_juice_flow",
        0xffb8b661,
        1100,
        1200
    );
    public static final FluidDefinition APPLE_CIDER = define(
        EDLModule.CORE,
        "apple_cider_fluid",
        "apple_cider_fluid_block",
        "apple_cider_fluid_bucket",
        "Apple Cider",
        "liquid/apple_cider_still",
        "liquid/apple_cider_flow",
        0xffcf7a2a,
        1100,
        1200
    );
    public static final FluidDefinition LEMON_JUICE = define(
        EDLModule.CORE,
        "lemon_juice_fluid",
        "lemon_juice_fluid_block",
        "lemon_juice_fluid_bucket",
        "Lemon Juice",
        "liquid/lemon_juice_still",
        "liquid/lemon_juice_flow",
        0xfff6df3b,
        1100,
        1200
    );
    public static final FluidDefinition LIME_JUICE = define(
        EDLModule.CORE,
        "lime_juice_fluid",
        "lime_juice_fluid_block",
        "lime_juice_fluid_bucket",
        "Lime Juice",
        "liquid/lime_juice_still",
        "liquid/lime_juice_flow",
        0xff9bcf3b,
        1100,
        1200
    );
    public static final FluidDefinition ORANGE_JUICE = define(
        EDLModule.CORE,
        "orange_juice_fluid",
        "orange_juice_fluid_block",
        "orange_juice_fluid_bucket",
        "Orange Juice",
        "liquid/orange_juice_still",
        "liquid/orange_juice_flow",
        0xfff49321,
        1100,
        1200
    );
    public static final FluidDefinition GRAPEFRUIT_JUICE = define(
        EDLModule.CORE,
        "grapefruit_juice_fluid",
        "grapefruit_juice_fluid_block",
        "grapefruit_juice_fluid_bucket",
        "Grapefruit Juice",
        "liquid/grapefruit_juice_still",
        "liquid/grapefruit_juice_flow",
        0xffff6a64,
        1100,
        1200
    );
    public static final FluidDefinition COFFEE = define(
        EDLModule.CORE,
        "coffee_fluid",
        "coffee_fluid_block",
        "coffee_fluid_bucket",
        "Coffee",
        "liquid/coffee_still",
        "liquid/coffee_flow",
        0xff30160d,
        1100,
        1200
    );
    public static final FluidDefinition EGG_WHITE = define(
        EDLModule.CORE,
        "egg_white_fluid",
        "egg_white_fluid_block",
        "egg_white_fluid_bucket",
        "Egg White",
        "liquid/egg_white_still",
        "liquid/egg_white_flow",
        0xfffdfdd0,
        1100,
        1200
    );
    public static final FluidDefinition EGG_MIX = define(
        EDLModule.CORE,
        "egg_mix_fluid",
        "egg_mix_fluid_block",
        "egg_mix_fluid_bucket",
        "Egg Mix",
        "liquid/egg_mix_still",
        "liquid/egg_mix_flow",
        0xffc99937,
        3000,
        6000
    );
    public static final FluidDefinition GRAVY = define(
        EDLModule.CORE,
        "gravy_fluid",
        "gravy_fluid_block",
        "gravy_fluid_bucket",
        "Gravy",
        "liquid/gravy_still",
        "liquid/gravy_flow",
        0xff461600,
        3000,
        6000
    );
    public static final FluidDefinition HOT_COCOA = define(
        EDLModule.SWEETS,
        "hot_cocoa_fluid",
        "hot_cocoa_fluid_block",
        "hot_cocoa_fluid_bucket",
        "Hot Cocoa",
        "minecraft:blocks/water_still",
        "minecraft:blocks/water_flow",
        0xff8f563b,
        1100,
        1200
    );
    public static final FluidDefinition MILKSHAKE = define(
        EDLModule.SWEETS,
        "milkshake_fluid",
        "milkshake_fluid_block",
        "milkshake_fluid_bucket",
        "Milkshake",
        "liquid/milkshake_still",
        "liquid/milkshake_flow",
        0xffeae9d5,
        3000,
        6000
    );
    public static final FluidDefinition JAM = define(
        EDLModule.SWEETS,
        "jam_fluid",
        "jam_fluid_block",
        "jam_fluid_bucket",
        "Jam",
        "liquid/jam_still",
        "liquid/jam_flow",
        0xffd23b3b,
        3000,
        6000
    );
    public static final FluidDefinition GOLDEN_JAM = define(
        EDLModule.SWEETS,
        "golden_jam_fluid",
        "golden_jam_fluid_block",
        "golden_jam_fluid_bucket",
        "Golden Jam",
        "liquid/golden_jam_still",
        "liquid/golden_jam_flow",
        0xfff2c000,
        3000,
        6000
    );
    public static final FluidDefinition GLOW_JAM = define(
        EDLModule.SWEETS,
        "glow_jam_fluid",
        "glow_jam_fluid_block",
        "glow_jam_fluid_bucket",
        "Glow Jam",
        "liquid/glow_jam_still",
        "liquid/glow_jam_flow",
        0xfff18b00,
        3000,
        6000
    );

    private EDLFluids() {
    }

    public static void registerFluids() {
        registerMilkBridge();
        for (FluidDefinition definition : DEFINITIONS) {
            definition.registerFluid();
        }
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        for (FluidDefinition definition : DEFINITIONS) {
            if (!definition.isEnabled() || definition.fluid == null) {
                continue;
            }

            BlockEdibleFluid block = new BlockEdibleFluid(definition.fluid);
            block.setRegistryName(ExtraDelightLegacy.MODID, definition.blockId);
            block.setUnlocalizedName(ExtraDelightLegacy.MODID + "." + definition.blockId);
            registry.register(block);
            definition.block = block;
            definition.entry.markRegistered();
        }
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        for (FluidDefinition definition : DEFINITIONS) {
            if (definition.block == null) {
                continue;
            }

            Item bucket = new ItemEDLFluidBucket(definition.block);
            bucket.setRegistryName(ExtraDelightLegacy.MODID, definition.bucketId);
            bucket.setUnlocalizedName(ExtraDelightLegacy.MODID + "." + definition.bucketId);
            bucket.setCreativeTab(EDLCreativeTabs.getTab(definition.module));
            registry.register(bucket);
            definition.bucket = bucket;
            REGISTERED_BUCKETS.add(bucket);
            EDLContentRegistry.addCreativeStack(definition.module, () -> new ItemStack(bucket));
            FluidRegistry.addBucketForFluid(definition.fluid);
        }
    }

    public static List<FluidDefinition> getDefinitions() {
        return Collections.unmodifiableList(DEFINITIONS);
    }

    public static List<Item> getRegisteredBuckets() {
        return Collections.unmodifiableList(REGISTERED_BUCKETS);
    }

    public static FluidDefinition find(Fluid fluid) {
        if (fluid == null) {
            return null;
        }

        for (FluidDefinition definition : DEFINITIONS) {
            if (definition.fluid == fluid) {
                return definition;
            }
        }
        return null;
    }

    public static Fluid getMilkFluid() {
        if (milkFluid == null) {
            milkFluid = FluidRegistry.getFluid(MILK_ID);
        }
        return milkFluid;
    }

    public static FluidStack milkStack(int amount) {
        Fluid fluid = getMilkFluid();
        return fluid == null || amount <= 0 ? null : new FluidStack(fluid, amount);
    }

    public static FluidStack stackForLogical(String fluidId, int amount) {
        if (fluidId == null || fluidId.isEmpty() || amount <= 0) {
            return null;
        }
        if (MixingBowlRecipeManager.FLUID_WATER.equals(fluidId)) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }
        if (MILK_ID.equals(fluidId)) {
            return milkStack(amount);
        }
        for (FluidDefinition definition : DEFINITIONS) {
            if (fluidId.equals(definition.fluidId)) {
                return definition.stack(amount);
            }
        }
        return null;
    }

    public static String logicalIdForFluid(Fluid fluid) {
        if (fluid == null) {
            return "";
        }
        if (fluid == FluidRegistry.WATER) {
            return MixingBowlRecipeManager.FLUID_WATER;
        }
        if (fluid == getMilkFluid()) {
            return MILK_ID;
        }
        if (fluid == VINEGAR.getFluid()) {
            return VINEGAR_ID;
        }
        FluidDefinition definition = find(fluid);
        return definition == null ? "" : definition.fluidId;
    }

    public static FluidDefinition findByBucket(Item item) {
        if (item == null) {
            return null;
        }

        for (FluidDefinition definition : DEFINITIONS) {
            if (definition.bucket == item) {
                return definition;
            }
        }
        return null;
    }

    private static FluidDefinition define(String fluidId, String blockId, String bucketId, String configName,
                                          String stillTexture, String flowingTexture, int color, int density, int viscosity) {
        return define(EDLModule.CORE, fluidId, blockId, bucketId, configName, stillTexture, flowingTexture, color, density, viscosity);
    }

    private static FluidDefinition define(EDLModule module, String fluidId, String blockId, String bucketId, String configName,
                                          String stillTexture, String flowingTexture, int color, int density, int viscosity) {
        FluidDefinition definition = new FluidDefinition(module, fluidId, blockId, bucketId, configName, stillTexture, flowingTexture, color, density, viscosity);
        DEFINITIONS.add(definition);
        return definition;
    }

    private static void registerMilkBridge() {
        milkFluid = FluidRegistry.getFluid(MILK_ID);
        if (milkFluid != null) {
            return;
        }

        Fluid fallback = new Fluid(MILK_ID, MILK_STILL, MILK_FLOWING)
            .setUnlocalizedName(ExtraDelightLegacy.MODID + ".milk")
            .setColor(0xfffff9df)
            .setDensity(1100)
            .setTemperature(305)
            .setViscosity(1300);
        if (!FluidRegistry.registerFluid(fallback)) {
            milkFluid = FluidRegistry.getFluid(MILK_ID);
        } else {
            milkFluid = fallback;
        }
    }

    public static final class FluidDefinition {
        private final EDLModule module;
        private final String fluidId;
        private final String blockId;
        private final String bucketId;
        private final String stillTexture;
        private final String flowingTexture;
        private final int color;
        private final int density;
        private final int viscosity;
        private final EDLContentEntry entry;
        private Fluid fluid;
        private Block block;
        private Item bucket;

        private FluidDefinition(EDLModule module, String fluidId, String blockId, String bucketId, String configName,
                                String stillTexture, String flowingTexture, int color, int density, int viscosity) {
            this.module = module;
            this.fluidId = fluidId;
            this.blockId = blockId;
            this.bucketId = bucketId;
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
            this.color = color;
            this.density = density;
            this.viscosity = viscosity;
            String entryId = VINEGAR_ID.equals(fluidId) ? "vinegar_fluid" : fluidId;
            this.entry = EDLContentRegistry.define(module, entryId, configName);
        }

        public String getFluidId() {
            return fluidId;
        }

        public String getBlockId() {
            return blockId;
        }

        public String getBucketId() {
            return bucketId;
        }

        public Fluid getFluid() {
            return fluid;
        }

        public Block getBlock() {
            return block;
        }

        public Item getBucket() {
            return bucket;
        }

        public boolean isRegistered() {
            return fluid != null && block != null && bucket != null;
        }

        public FluidStack stack(int amount) {
            return fluid == null || amount <= 0 ? null : new FluidStack(fluid, amount);
        }

        public ItemStack bucketStack() {
            return bucket == null ? ItemStack.EMPTY : new ItemStack(bucket);
        }

        private boolean isEnabled() {
            return xy177.extradelightlegacy.common.config.EDLConfig.isModuleEnabled(entry.getModule())
                && xy177.extradelightlegacy.common.config.EDLConfig.isEntryEnabled(entry);
        }

        private void registerFluid() {
            if (!isEnabled()) {
                return;
            }

            ResourceLocation still = texture(stillTexture);
            ResourceLocation flowing = texture(flowingTexture);
            String forgeFluidName = ExtraDelightLegacy.MODID + "." + fluidId;
            fluid = new Fluid(forgeFluidName, still, flowing)
                .setUnlocalizedName(ExtraDelightLegacy.MODID + "." + fluidId)
                .setColor(color)
                .setDensity(density)
                .setViscosity(viscosity);
            if (!FluidRegistry.registerFluid(fluid)) {
                fluid = FluidRegistry.getFluid(forgeFluidName);
            }
        }

        private ResourceLocation texture(String path) {
            return path.indexOf(':') >= 0
                ? new ResourceLocation(path)
                : new ResourceLocation(ExtraDelightLegacy.MODID, path);
        }
    }
}
