package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;

@ZenRegister
@ZenClass("mods.extradelightlegacy.MeltingPot")
public final class ZenMeltingPot {
    private ZenMeltingPot() {
    }

    @ZenMethod
    public static void add(String id, IIngredient input, ILiquidStack output, int cookingTime) {
        String recipeId = ZenRecipeUtil.id(id, "melting pot recipe");
        MixingBowlIngredient ingredient = ZenRecipeUtil.ingredient(input, "Melting pot input");
        FluidStack result = ZenRecipeUtil.fluid(output, false, "Melting pot output");
        if (recipeId == null || ingredient == null || result == null || cookingTime <= 0) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy melting pot recipe " + recipeId,
            () -> MeltingPotRecipeManager.register(recipeId, ingredient, result, cookingTime));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "melting pot recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("melting pot recipe " + recipeId, () -> MeltingPotRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByFluidOutput(ILiquidStack output) {
        FluidStack result = ZenRecipeUtil.fluid(output, false, "Melting pot output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy melting pot recipes by output",
                () -> MeltingPotRecipeManager.removeByFluidOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy melting pot recipes", MeltingPotRecipeManager::removeAll);
    }
}
