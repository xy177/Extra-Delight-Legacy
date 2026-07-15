package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.VatRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatStage;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Vat")
public final class ZenVat {
    private ZenVat() {
    }

    @ZenMethod
    public static void add(String id, IIngredient[] baseIngredients, ILiquidStack fluidInput,
                           IItemStack container, IItemStack output, IIngredient[] stageIngredients,
                           boolean[] stageRequiresLid, int[] stageTimes) {
        String recipeId = ZenRecipeUtil.id(id, "vat recipe");
        List<MixingBowlIngredient> ingredients = ZenRecipeUtil.ingredients(baseIngredients, 1, 6, "Vat recipe");
        FluidStack fluid = ZenRecipeUtil.fluid(fluidInput, false, "Vat fluid input");
        ItemStack containerStack = ZenRecipeUtil.item(container, false, "Vat container");
        ItemStack result = ZenRecipeUtil.item(output, false, "Vat output");
        List<VatStage> stages = stages(stageIngredients, stageRequiresLid, stageTimes);
        if (recipeId == null || ingredients == null || fluid == null || containerStack == null || result == null || stages == null) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy vat recipe " + recipeId,
            () -> VatRecipeManager.register(recipeId, ingredients, fluid, containerStack, result, stages));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "vat recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("vat recipe " + recipeId, () -> VatRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Vat output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy vat recipes by output",
                () -> VatRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy vat recipes", VatRecipeManager::removeAll);
    }

    private static List<VatStage> stages(IIngredient[] ingredients, boolean[] requiresLid, int[] times) {
        if (ingredients == null || requiresLid == null || times == null || ingredients.length == 0
            || ingredients.length != requiresLid.length || ingredients.length != times.length) {
            CraftTweakerAPI.logError("Vat stage ingredient, lid and time arrays must have the same non-zero length");
            return null;
        }
        List<VatStage> stages = new ArrayList<>();
        for (int i = 0; i < ingredients.length; i++) {
            if (times[i] <= 0) {
                CraftTweakerAPI.logError("Vat stage time must be greater than zero ticks");
                return null;
            }
            MixingBowlIngredient ingredient = ingredients[i] == null
                ? null
                : ZenRecipeUtil.ingredient(ingredients[i], "Vat stage " + (i + 1) + " ingredient");
            if (ingredients[i] != null && ingredient == null) {
                return null;
            }
            stages.add(new VatStage(ingredient, requiresLid[i], times[i]));
        }
        return stages;
    }
}
