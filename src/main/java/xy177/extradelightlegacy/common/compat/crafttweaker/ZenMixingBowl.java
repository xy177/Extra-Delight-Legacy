package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.MixingBowlFluidIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;

import java.util.List;

@ZenRegister
@ZenClass("mods.extradelightlegacy.MixingBowl")
public final class ZenMixingBowl {
    private ZenMixingBowl() {
    }

    @ZenMethod
    public static void add(String id, IIngredient[] itemInputs, ILiquidStack[] fluidInputs,
                           IItemStack container, String utensilOre, IItemStack output, int stirs) {
        String recipeId = ZenRecipeUtil.id(id, "mixing bowl recipe");
        List<MixingBowlIngredient> items = ZenRecipeUtil.ingredients(itemInputs, 0, 9, "Mixing bowl recipe");
        List<MixingBowlFluidIngredient> fluids = ZenRecipeUtil.mixingFluids(fluidInputs, "Mixing bowl recipe");
        ItemStack containerStack = ZenRecipeUtil.item(container, true, "Mixing bowl container");
        ItemStack result = ZenRecipeUtil.item(output, false, "Mixing bowl output");
        if (recipeId == null || items == null || fluids == null || containerStack == null || result == null || stirs <= 0) {
            return;
        }
        if (items.isEmpty() && fluids.isEmpty()) {
            CraftTweakerAPI.logError("Mixing bowl recipes require at least one item or fluid input");
            return;
        }
        if (utensilOre == null || utensilOre.trim().isEmpty()) {
            CraftTweakerAPI.logError("Mixing bowl recipe requires a utensil Ore Dictionary name");
            return;
        }
        String utensil = utensilOre.trim();
        CraftTweakerActions.add("add Extra Delight Legacy mixing bowl recipe " + recipeId,
            () -> MixingBowlRecipeManager.register(recipeId, items, fluids, containerStack, utensil, result, stirs));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "mixing bowl recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("mixing bowl recipe " + recipeId, () -> MixingBowlRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Mixing bowl output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy mixing bowl recipes by output",
                () -> MixingBowlRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy mixing bowl recipes", MixingBowlRecipeManager::removeAll);
    }
}
