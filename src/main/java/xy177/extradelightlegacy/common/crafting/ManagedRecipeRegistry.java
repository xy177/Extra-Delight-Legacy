package xy177.extradelightlegacy.common.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

final class ManagedRecipeRegistry<T> {
    private final Map<String, T> recipes = new LinkedHashMap<>();
    private final Map<String, T> baseline = new LinkedHashMap<>();
    private boolean baselineCaptured;

    void clear() {
        recipes.clear();
    }

    boolean register(String id, T recipe) {
        String normalized = RecipeManagerUtil.normalizeId(id);
        if (normalized == null || recipe == null) {
            return false;
        }
        recipes.put(normalized, recipe);
        return true;
    }

    boolean remove(String id) {
        String normalized = RecipeManagerUtil.normalizeId(id);
        return normalized != null && recipes.remove(normalized) != null;
    }

    int removeIf(Predicate<T> predicate) {
        if (predicate == null) {
            return 0;
        }
        int before = recipes.size();
        recipes.values().removeIf(predicate);
        return before - recipes.size();
    }

    int removeAll() {
        int removed = recipes.size();
        recipes.clear();
        return removed;
    }

    List<T> getRecipes() {
        return Collections.unmodifiableList(new ArrayList<>(recipes.values()));
    }

    void captureBaseline() {
        baseline.clear();
        baseline.putAll(recipes);
        baselineCaptured = true;
    }

    void restoreBaseline() {
        if (!baselineCaptured) {
            return;
        }
        recipes.clear();
        recipes.putAll(baseline);
    }
}
