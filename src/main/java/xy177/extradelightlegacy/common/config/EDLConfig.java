package xy177.extradelightlegacy.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import xy177.extradelightlegacy.common.content.EDLContentEntry;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

public final class EDLConfig {
    private static final String MODULE_CATEGORY = "modules";
    private static final String ENTRY_CATEGORY_PREFIX = "entries.";
    private static final String MINT_CATEGORY = "mint_spread";
    private static final String WORLD_GENERATION_CATEGORY = "world_generation";

    private static final Map<EDLModule, Boolean> MODULE_ENABLED = new EnumMap<>(EDLModule.class);
    private static Configuration config;
    private static boolean mintSpread = true;
    private static int mintSpreadRate = 6;
    private static int mintSpreadMaxNearby = 16;
    private static int mintSpreadMaxPerChunk = 50;
    private static boolean generateWildCrops = true;
    private static boolean generateOrchardTrees = true;
    private static int wildCornChance = 40;
    private static int wildCoffeeChance = 40;
    private static int wildGingerChance = 20;
    private static int wildChiliChance = 50;
    private static int wildPeanutChance = 50;
    private static int wildMallowChance = 70;
    private static int wildMintChance = 20;
    private static int wildGarlicChance = 30;
    private static int wildCucumberChance = 40;
    private static int wildSoybeanChance = 80;
    private static int cinnamonTreeChance = 10;
    private static int hazelnutTreeChance = 50;
    private static int appleTreeChance = 50;
    private static int lemonTreeChance = 50;
    private static int limeTreeChance = 50;
    private static int orangeTreeChance = 50;
    private static int grapefruitTreeChance = 50;

    private EDLConfig() {
    }

    public static void load(File file) {
        config = new Configuration(file);
        config.load();

        for (EDLModule module : EDLModule.values()) {
            Property property = config.get(MODULE_CATEGORY, module.getId(), true, module.getConfigComment());
            MODULE_ENABLED.put(module, property.getBoolean(true));
        }

        loadMintSpreadConfig();
        loadWorldGenerationConfig();
        saveIfChanged();
    }

    public static boolean isModuleEnabled(EDLModule module) {
        Boolean enabled = MODULE_ENABLED.get(module);
        return enabled == null || enabled;
    }

    public static boolean isEntryEnabled(EDLContentEntry entry) {
        ensureLoaded();
        String category = ENTRY_CATEGORY_PREFIX + entry.getModule().getId();
        String comment = "注册内容：" + entry.getConfigName() + "\nRegister content entry: " + entry.getId();
        Property property = config.get(category, entry.getId(), entry.isDefaultEnabled(), comment);
        boolean enabled = property.getBoolean(entry.isDefaultEnabled());
        saveIfChanged();
        return enabled;
    }

    public static boolean shouldMintSpread() {
        ensureLoaded();
        return mintSpread;
    }

    public static int getMintSpreadRate() {
        ensureLoaded();
        return mintSpreadRate;
    }

    public static int getMintSpreadMaxNearby() {
        ensureLoaded();
        return mintSpreadMaxNearby;
    }

    public static int getMintSpreadMaxPerChunk() {
        ensureLoaded();
        return mintSpreadMaxPerChunk;
    }

    public static boolean shouldGenerateWildCrops() {
        ensureLoaded();
        return generateWildCrops;
    }

    public static boolean shouldGenerateOrchardTrees() {
        ensureLoaded();
        return generateOrchardTrees;
    }

    public static int getWildCornChance() {
        ensureLoaded();
        return wildCornChance;
    }

    public static int getWildCoffeeChance() {
        ensureLoaded();
        return wildCoffeeChance;
    }

    public static int getWildGingerChance() {
        ensureLoaded();
        return wildGingerChance;
    }

    public static int getWildChiliChance() {
        ensureLoaded();
        return wildChiliChance;
    }

    public static int getWildPeanutChance() {
        ensureLoaded();
        return wildPeanutChance;
    }

    public static int getWildMallowChance() {
        ensureLoaded();
        return wildMallowChance;
    }

    public static int getWildMintChance() {
        ensureLoaded();
        return wildMintChance;
    }

    public static int getWildGarlicChance() {
        ensureLoaded();
        return wildGarlicChance;
    }

    public static int getWildCucumberChance() {
        ensureLoaded();
        return wildCucumberChance;
    }

    public static int getWildSoybeanChance() {
        ensureLoaded();
        return wildSoybeanChance;
    }

    public static int getCinnamonTreeChance() {
        ensureLoaded();
        return cinnamonTreeChance;
    }

    public static int getHazelnutTreeChance() {
        ensureLoaded();
        return hazelnutTreeChance;
    }

    public static int getAppleTreeChance() {
        ensureLoaded();
        return appleTreeChance;
    }

    public static int getLemonTreeChance() {
        ensureLoaded();
        return lemonTreeChance;
    }

    public static int getLimeTreeChance() {
        ensureLoaded();
        return limeTreeChance;
    }

    public static int getOrangeTreeChance() {
        ensureLoaded();
        return orangeTreeChance;
    }

    public static int getGrapefruitTreeChance() {
        ensureLoaded();
        return grapefruitTreeChance;
    }

    public static void saveIfChanged() {
        if (config != null && config.hasChanged()) {
            config.save();
        }
    }

    private static void loadMintSpreadConfig() {
        Property mintSpreadProperty = config.get(
            MINT_CATEGORY,
            "shouldMintSpread",
            true,
            "薄荷是否会随机扩散\nShould mint spread randomly"
        );
        mintSpread = mintSpreadProperty.getBoolean(true);

        Property mintSpreadRateProperty = config.get(
            MINT_CATEGORY,
            "mintSpreadRate",
            6,
            "薄荷扩散速率，数字越大越慢\nHow fast mint spreads; higher number means slower"
        );
        mintSpreadRate = Math.max(1, Math.min(100, mintSpreadRateProperty.getInt(6)));

        Property mintSpreadMaxNearbyProperty = config.get(
            MINT_CATEGORY,
            "mintSpreadMaxNearby",
            16,
            "薄荷局部扩散上限，附近薄荷达到该数量后停止继续扩散\nMaximum nearby mint blocks before mint stops spreading locally"
        );
        mintSpreadMaxNearby = Math.max(1, Math.min(256, mintSpreadMaxNearbyProperty.getInt(16)));

        Property mintSpreadMaxPerChunkProperty = config.get(
            MINT_CATEGORY,
            "mintSpreadMaxPerChunk",
            50,
            "薄荷单区块扩散上限，区块内达到该数量后停止继续扩散\nMaximum mint blocks per chunk before mint stops spreading in that chunk"
        );
        mintSpreadMaxPerChunk = Math.max(1, Math.min(4096, mintSpreadMaxPerChunkProperty.getInt(50)));
    }

    private static void loadWorldGenerationConfig() {
        generateWildCrops = config.getBoolean(
            "generateWildCrops",
            WORLD_GENERATION_CATEGORY,
            true,
            "Generate Extra Delight wild crops in new Overworld chunks"
        );
        generateOrchardTrees = config.getBoolean(
            "generateOrchardTrees",
            WORLD_GENERATION_CATEGORY,
            true,
            "Generate Extra Delight orchard trees in new Overworld chunks"
        );

        wildCornChance = worldGenerationChance("wildCornChance", 40);
        wildCoffeeChance = worldGenerationChance("wildCoffeeChance", 40);
        wildGingerChance = worldGenerationChance("wildGingerChance", 20);
        wildChiliChance = worldGenerationChance("wildChiliChance", 50);
        wildPeanutChance = worldGenerationChance("wildPeanutChance", 50);
        wildMallowChance = worldGenerationChance("wildMallowChance", 70);
        wildMintChance = worldGenerationChance("wildMintChance", 20);
        wildGarlicChance = worldGenerationChance("wildGarlicChance", 30);
        wildCucumberChance = worldGenerationChance("wildCucumberChance", 40);
        wildSoybeanChance = worldGenerationChance("wildSoybeanChance", 80);
        cinnamonTreeChance = worldGenerationChance("cinnamonTreeChance", 10);
        hazelnutTreeChance = worldGenerationChance("hazelnutTreeChance", 50);
        appleTreeChance = worldGenerationChance("appleTreeChance", 50);
        lemonTreeChance = worldGenerationChance("lemonTreeChance", 50);
        limeTreeChance = worldGenerationChance("limeTreeChance", 50);
        orangeTreeChance = worldGenerationChance("orangeTreeChance", 50);
        grapefruitTreeChance = worldGenerationChance("grapefruitTreeChance", 50);
    }

    private static int worldGenerationChance(String name, int defaultValue) {
        return config.getInt(
            name,
            WORLD_GENERATION_CATEGORY,
            defaultValue,
            0,
            100000,
            "Average qualifying chunks per generation attempt; lower is more common, 0 disables generation"
        );
    }

    private static void ensureLoaded() {
        if (config == null) {
            throw new IllegalStateException("ExtraDelightLegacy config has not been loaded yet");
        }
    }
}
