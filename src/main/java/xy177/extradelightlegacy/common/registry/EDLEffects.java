package xy177.extradelightlegacy.common.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.effect.PotionPickled;
import xy177.extradelightlegacy.common.effect.PotionSourPucker;
import xy177.extradelightlegacy.common.effect.PotionSunshine;

public final class EDLEffects {
    public static Potion SUNSHINE;
    public static Potion SOUR_PUCKER;
    public static Potion PICKLED;

    private EDLEffects() {
    }

    public static void register(IForgeRegistry<Potion> registry) {
        SUNSHINE = new PotionSunshine();
        SUNSHINE.setRegistryName(ExtraDelightLegacy.MODID, "sunshine");
        registry.register(SUNSHINE);
        SOUR_PUCKER = new PotionSourPucker();
        SOUR_PUCKER.setRegistryName(ExtraDelightLegacy.MODID, "sour_pucker");
        registry.register(SOUR_PUCKER);
        PICKLED = new PotionPickled();
        PICKLED.setRegistryName(ExtraDelightLegacy.MODID, "pickled");
        registry.register(PICKLED);
    }
}
