package xy177.extradelightlegacy.common.crafting;

import java.util.ArrayList;
import java.util.List;

public final class EDLCustomRecipeLifecycle {
    private static final List<Runnable> PENDING_ACTIONS = new ArrayList<>();
    private static boolean baselineCaptured;

    private EDLCustomRecipeLifecycle() {
    }

    public static synchronized void enqueueAction(Runnable action) {
        if (action != null) {
            PENDING_ACTIONS.add(action);
        }
    }

    public static void applyPendingActions() {
        List<Runnable> actions;
        synchronized (EDLCustomRecipeLifecycle.class) {
            if (!baselineCaptured) {
                return;
            }
            actions = new ArrayList<>(PENDING_ACTIONS);
            PENDING_ACTIONS.clear();
        }
        for (Runnable action : actions) {
            action.run();
        }
    }

    public static synchronized void clearPendingActions() {
        PENDING_ACTIONS.clear();
    }

    public static void captureBaseline() {
        DryingRackRecipeManager.captureBaseline();
        MortarRecipeManager.captureBaseline();
        DoughShapingRecipeManager.captureBaseline();
        BottleFluidRecipeManager.captureBaseline();
        MixingBowlRecipeManager.captureBaseline();
        ChillerRecipeManager.captureBaseline();
        MeltingPotRecipeManager.captureBaseline();
        OvenRecipeManager.captureBaseline();
        JuicerRecipeManager.captureBaseline();
        VatRecipeManager.captureBaseline();
        EvaporatorRecipeManager.captureBaseline();
        ButcherDropRegistry.captureBaseline();
        synchronized (EDLCustomRecipeLifecycle.class) {
            baselineCaptured = true;
        }
    }

    public static void restoreBaseline() {
        synchronized (EDLCustomRecipeLifecycle.class) {
            if (!baselineCaptured) {
                return;
            }
        }
        DryingRackRecipeManager.restoreBaseline();
        MortarRecipeManager.restoreBaseline();
        DoughShapingRecipeManager.restoreBaseline();
        BottleFluidRecipeManager.restoreBaseline();
        MixingBowlRecipeManager.restoreBaseline();
        ChillerRecipeManager.restoreBaseline();
        MeltingPotRecipeManager.restoreBaseline();
        OvenRecipeManager.restoreBaseline();
        JuicerRecipeManager.restoreBaseline();
        VatRecipeManager.restoreBaseline();
        EvaporatorRecipeManager.restoreBaseline();
        ButcherDropRegistry.restoreBaseline();
    }
}
