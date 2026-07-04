package xy177.extradelightlegacy.common.registry;

import net.minecraft.tileentity.BannerPattern;
import net.minecraftforge.common.util.EnumHelper;

public final class EDLBannerPatterns {
    public static BannerPattern CITRUS_PITH;
    public static BannerPattern CITRUS_FRUIT;

    private static boolean registered;

    private EDLBannerPatterns() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        Class<?>[] types = new Class<?>[]{String.class, String.class};
        CITRUS_PITH = EnumHelper.addEnum(
            BannerPattern.class,
            "EXTRADELIGHTLEGACY_CITRUS_PITH",
            types,
            "extradelightlegacy/citrus_pith",
            "edl_cp"
        );
        CITRUS_FRUIT = EnumHelper.addEnum(
            BannerPattern.class,
            "EXTRADELIGHTLEGACY_CITRUS_FRUIT",
            types,
            "extradelightlegacy/citrus_fruit",
            "edl_cf"
        );
        registered = true;
    }
}
