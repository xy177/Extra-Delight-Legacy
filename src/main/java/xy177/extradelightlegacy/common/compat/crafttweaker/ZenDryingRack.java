package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;

@ZenRegister
@ZenClass("mods.extradelightlegacy.DryingRack")
public final class ZenDryingRack {
    private ZenDryingRack() {
    }

    @ZenMethod
    public static void add(String id, IIngredient input, IItemStack output, int dryingTime) {
        String recipeId = ZenRecipeUtil.id(id, "drying rack recipe");
        MixingBowlIngredient ingredient = ZenRecipeUtil.ingredient(input, "Drying rack input");
        ItemStack result = ZenRecipeUtil.item(output, false, "Drying rack output");
        if (recipeId == null || ingredient == null || result == null || ingredient.getRequiredCount() != 1 || dryingTime <= 0) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy drying rack recipe " + recipeId,
            () -> DryingRackRecipeManager.register(recipeId, ingredient, result, dryingTime));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "drying rack recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("drying rack recipe " + recipeId, () -> DryingRackRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Drying rack output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy drying rack recipes by output",
                () -> DryingRackRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy drying rack recipes", DryingRackRecipeManager::removeAll);
    }
}
