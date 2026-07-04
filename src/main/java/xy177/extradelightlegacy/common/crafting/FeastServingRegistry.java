package xy177.extradelightlegacy.common.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.registry.EDLEnchantments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FeastServingRegistry {
    public static final Object EMPTY_HAND = new Object();

    private static final List<FeastServingRecipe> RECIPES = new ArrayList<>();
    private static final List<FeastLeftover> LEFTOVERS = new ArrayList<>();

    private FeastServingRegistry() {
    }

    public static void clear() {
        RECIPES.clear();
        LEFTOVERS.clear();
    }

    public static void register(Block feast, Object container, ItemStack result) {
        if (feast == null || result.isEmpty()) {
            return;
        }
        RECIPES.add(new FeastServingRecipe(feast, container, result.copy()));
    }

    public static void registerLeftover(Block feast, ItemStack leftover) {
        if (feast == null || leftover.isEmpty()) {
            return;
        }
        LEFTOVERS.add(new FeastLeftover(feast, leftover.copy()));
    }

    public static ItemStack findResult(Block feast, ItemStack container) {
        FeastServingRecipe recipe = findRecipe(feast, container);
        return recipe == null ? ItemStack.EMPTY : recipe.getResult();
    }

    public static FeastServingRecipe findRecipe(Block feast, ItemStack container) {
        for (FeastServingRecipe recipe : RECIPES) {
            if (recipe.matches(feast, container)) {
                return recipe;
            }
        }
        return null;
    }

    public static ItemStack findRequiredContainer(Block feast) {
        for (FeastServingRecipe recipe : RECIPES) {
            if (recipe.getFeast() == feast) {
                return recipe.getDisplayContainer();
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack findLeftover(Block feast) {
        for (FeastLeftover leftover : LEFTOVERS) {
            if (leftover.feast == feast) {
                return leftover.leftover.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public static List<FeastServingRecipe> getRecipes() {
        return Collections.unmodifiableList(RECIPES);
    }

    public static final class FeastServingRecipe {
        private final Block feast;
        private final Object container;
        private final ItemStack result;

        private FeastServingRecipe(Block feast, Object container, ItemStack result) {
            this.feast = feast;
            this.container = container;
            this.result = result;
        }

        public Block getFeast() {
            return feast;
        }

        public Object getContainer() {
            return container;
        }

        public ItemStack getResult() {
            return result.copy();
        }

        private ItemStack getDisplayContainer() {
            if (container instanceof Item) {
                return new ItemStack((Item) container);
            }
            if (container instanceof ItemStack) {
                return ((ItemStack) container).copy();
            }
            if (container instanceof String) {
                List<ItemStack> ores = OreDictionary.getOres((String) container);
                return ores.isEmpty() ? ItemStack.EMPTY : ores.get(0).copy();
            }
            return ItemStack.EMPTY;
        }

        private boolean matches(Block feast, ItemStack stack) {
            if (this.feast != feast) {
                return false;
            }
            if (container == EMPTY_HAND) {
                return stack.isEmpty();
            }
            if (container instanceof String) {
                return matchesOre(stack, (String) container);
            }
            if (container instanceof Item) {
                return !stack.isEmpty() && stack.getItem() == container;
            }
            if (container instanceof ItemStack) {
                return OreDictionary.itemMatches((ItemStack) container, stack, false);
            }
            return false;
        }

        public boolean consumesContainer() {
            return container != EMPTY_HAND;
        }

        private static boolean matchesOre(ItemStack stack, String oreName) {
            if (stack.isEmpty()) {
                return false;
            }
            if ("toolKnife".equals(oreName)) {
                return EDLEnchantments.isKnife(stack);
            }
            int oreId = OreDictionary.getOreID(oreName);
            for (int stackOreId : OreDictionary.getOreIDs(stack)) {
                if (stackOreId == oreId) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class FeastLeftover {
        private final Block feast;
        private final ItemStack leftover;

        private FeastLeftover(Block feast, ItemStack leftover) {
            this.feast = feast;
            this.leftover = leftover;
        }
    }
}
