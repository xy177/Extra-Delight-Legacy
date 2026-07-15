package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.OvenRecipeManager;

import java.util.List;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Oven")
public final class ZenOven {
    private ZenOven() {
    }

    @ZenMethod
    public static void add(String id, IIngredient[] inputs, IItemStack container, IItemStack output,
                           int cookingTime, boolean consumeContainer) {
        String recipeId = ZenRecipeUtil.id(id, "oven recipe");
        List<MixingBowlIngredient> ingredients = ZenRecipeUtil.ingredients(inputs, 1, 9, "Oven recipe");
        ItemStack containerStack = ZenRecipeUtil.item(container, true, "Oven container");
        ItemStack result = ZenRecipeUtil.item(output, false, "Oven output");
        if (recipeId == null || ingredients == null || containerStack == null || result == null || cookingTime <= 0) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy oven recipe " + recipeId,
            () -> OvenRecipeManager.register(recipeId, ingredients, containerStack, result, cookingTime, consumeContainer));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "oven recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("oven recipe " + recipeId, () -> OvenRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Oven output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy oven recipes by output",
                () -> OvenRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy oven recipes", OvenRecipeManager::removeAll);
    }
}
