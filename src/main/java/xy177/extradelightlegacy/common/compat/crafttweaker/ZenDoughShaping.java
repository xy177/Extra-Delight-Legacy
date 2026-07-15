package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;

@ZenRegister
@ZenClass("mods.extradelightlegacy.DoughShaping")
public final class ZenDoughShaping {
    private ZenDoughShaping() {
    }

    @ZenMethod
    public static void add(String id, IIngredient input, IItemStack output) {
        String recipeId = ZenRecipeUtil.id(id, "dough shaping recipe");
        MixingBowlIngredient ingredient = ZenRecipeUtil.ingredient(input, "Dough shaping input");
        ItemStack result = ZenRecipeUtil.item(output, false, "Dough shaping output");
        if (recipeId == null || ingredient == null || result == null || ingredient.getRequiredCount() != 1) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy dough shaping recipe " + recipeId,
            () -> DoughShapingRecipeManager.register(recipeId, ingredient, result));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "dough shaping recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("dough shaping recipe " + recipeId, () -> DoughShapingRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Dough shaping output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy dough shaping recipes by output",
                () -> DoughShapingRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy dough shaping recipes", DoughShapingRecipeManager::removeAll);
    }
}
