package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import xy177.extradelightlegacy.common.crafting.EDLCustomRecipeLifecycle;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

final class CraftTweakerActions {
    private CraftTweakerActions() {
    }

    static void add(String description, BooleanSupplier operation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                EDLCustomRecipeLifecycle.enqueueAction(() -> {
                    if (!operation.getAsBoolean()) {
                        CraftTweakerAPI.logError("Failed to " + description);
                    }
                });
            }

            @Override
            public String describe() {
                return description;
            }
        });
    }

    static void remove(String description, BooleanSupplier operation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                EDLCustomRecipeLifecycle.enqueueAction(() -> {
                    if (!operation.getAsBoolean()) {
                        CraftTweakerAPI.logWarning("No Extra Delight Legacy entry matched: " + description);
                    }
                });
            }

            @Override
            public String describe() {
                return description;
            }
        });
    }

    static void removeMany(String description, IntSupplier operation) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                EDLCustomRecipeLifecycle.enqueueAction(() ->
                    CraftTweakerAPI.logInfo(description + ": removed " + operation.getAsInt() + " entries"));
            }

            @Override
            public String describe() {
                return description;
            }
        });
    }
}
