package xy177.extradelightlegacy.common.content;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class EDLContentRegistry {
    private static final Map<String, EDLContentEntry> ENTRIES = new LinkedHashMap<>();
    private static final Map<EDLModule, List<Supplier<ItemStack>>> CREATIVE_STACKS = new LinkedHashMap<>();

    private EDLContentRegistry() {
    }

    public static EDLContentEntry define(EDLModule module, String id, String configName, String... dependencies) {
        return define(module, id, configName, true, dependencies);
    }

    public static EDLContentEntry define(EDLModule module, String id, String configName, boolean defaultEnabled, String... dependencies) {
        if (ENTRIES.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate ExtraDelightLegacy content entry: " + id);
        }

        EDLContentEntry entry = new EDLContentEntry(module, id, configName, defaultEnabled, Arrays.asList(dependencies));
        ENTRIES.put(id, entry);
        return entry;
    }

    public static boolean areDependenciesRegistered(EDLContentEntry entry) {
        for (String dependency : entry.getDependencies()) {
            EDLContentEntry dependencyEntry = ENTRIES.get(dependency);
            if (dependencyEntry == null || !dependencyEntry.isRegistered()) {
                if (ExtraDelightLegacy.logger != null) {
                    ExtraDelightLegacy.logger.debug(
                        "Skipping {} because dependency {} is unavailable",
                        entry.getId(),
                        dependency
                    );
                }
                return false;
            }
        }
        return true;
    }

    public static boolean areDependenciesEnabled(EDLContentEntry entry) {
        return areDependenciesEnabled(entry, new HashSet<String>());
    }

    private static boolean areDependenciesEnabled(EDLContentEntry entry, Set<String> visiting) {
        if (!visiting.add(entry.getId())) {
            return false;
        }

        for (String dependency : entry.getDependencies()) {
            EDLContentEntry dependencyEntry = ENTRIES.get(dependency);
            if (dependencyEntry == null
                || !EDLConfig.isModuleEnabled(dependencyEntry.getModule())
                || !EDLConfig.isEntryEnabled(dependencyEntry)
                || !areDependenciesEnabled(dependencyEntry, visiting)) {
                if (ExtraDelightLegacy.logger != null) {
                    ExtraDelightLegacy.logger.debug(
                        "Skipping {} because dependency {} is disabled or unavailable",
                        entry.getId(),
                        dependency
                    );
                }
                visiting.remove(entry.getId());
                return false;
            }
        }

        visiting.remove(entry.getId());
        return true;
    }

    public static boolean isRegistered(String id) {
        EDLContentEntry entry = ENTRIES.get(id);
        return entry != null && entry.isRegistered();
    }

    public static List<EDLContentEntry> getEntries() {
        return Collections.unmodifiableList(new ArrayList<>(ENTRIES.values()));
    }

    public static void addCreativeStack(EDLModule module, Supplier<ItemStack> stackSupplier) {
        CREATIVE_STACKS.computeIfAbsent(module, key -> new ArrayList<>()).add(stackSupplier);
    }

    public static void addCreativeStacks(EDLModule module, NonNullList<ItemStack> target) {
        List<Supplier<ItemStack>> suppliers = CREATIVE_STACKS.get(module);
        if (suppliers == null) {
            return;
        }

        for (Supplier<ItemStack> supplier : suppliers) {
            ItemStack stack = supplier.get();
            if (!stack.isEmpty()) {
                target.add(stack);
            }
        }
    }

    public static ItemStack getFirstCreativeStack(EDLModule module) {
        List<Supplier<ItemStack>> suppliers = CREATIVE_STACKS.get(module);
        if (suppliers == null) {
            return ItemStack.EMPTY;
        }

        for (Supplier<ItemStack> supplier : suppliers) {
            ItemStack stack = supplier.get();
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
