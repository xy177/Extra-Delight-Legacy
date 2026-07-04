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

    private static final Map<EDLModule, Boolean> MODULE_ENABLED = new EnumMap<>(EDLModule.class);
    private static Configuration config;
    private static boolean mintSpread = true;
    private static int mintSpreadRate = 6;
    private static int mintSpreadMaxNearby = 16;
    private static int mintSpreadMaxPerChunk = 50;

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

    private static void ensureLoaded() {
        if (config == null) {
            throw new IllegalStateException("ExtraDelightLegacy config has not been loaded yet");
        }
    }
}
