package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.mc1120.events.ActionApplyEvent;
import crafttweaker.mc1120.events.ScriptRunEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.ButcherDropRegistry;
import xy177.extradelightlegacy.common.crafting.EDLCustomRecipeLifecycle;

public final class CraftTweakerEventHandler {
    @SubscribeEvent
    public void onScriptRunPre(ScriptRunEvent.Pre event) {
        EDLCustomRecipeLifecycle.clearPendingActions();
        EDLCustomRecipeLifecycle.restoreBaseline();
    }

    @SubscribeEvent
    public void onScriptRunPost(ScriptRunEvent.Post event) {
        EDLCustomRecipeLifecycle.applyPendingActions();
        synchronizeRuntimeViews();
    }

    @SubscribeEvent
    public void onActionsApplied(ActionApplyEvent.Post event) {
        EDLCustomRecipeLifecycle.applyPendingActions();
        synchronizeRuntimeViews();
    }

    private static void synchronizeRuntimeViews() {
        ButcherDropRegistry.syncHuntingJeiRecipes();
        if (!Loader.isModLoaded("jei")) {
            return;
        }
        try {
            Class<?> plugin = Class.forName("xy177.extradelightlegacy.integration.jei.ExtraDelightLegacyJeiPlugin");
            plugin.getMethod("refreshAllRecipes").invoke(null);
        } catch (ReflectiveOperationException exception) {
            ExtraDelightLegacy.logger.error("Failed to refresh JEI after CraftTweaker changes", exception);
        }
    }
}
