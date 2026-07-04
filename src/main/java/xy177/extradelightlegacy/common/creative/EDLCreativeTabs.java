package xy177.extradelightlegacy.common.creative;

import net.minecraft.creativetab.CreativeTabs;
import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.util.EnumMap;
import java.util.Map;

public final class EDLCreativeTabs {
    private static final Map<EDLModule, CreativeTabs> TABS = new EnumMap<>(EDLModule.class);
    private static boolean initialized;

    private EDLCreativeTabs() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        for (EDLModule module : EDLModule.values()) {
            if (module.hasCreativeTab() && EDLConfig.isModuleEnabled(module)) {
                TABS.put(module, new EDLCreativeTab(module));
            }
        }
        initialized = true;
    }

    public static CreativeTabs getTab(EDLModule module) {
        init();
        return TABS.get(module);
    }
}
