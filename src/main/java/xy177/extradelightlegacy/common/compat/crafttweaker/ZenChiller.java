package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.ChillerRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;

import java.util.List;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Chiller")
public final class ZenChiller {
    private ZenChiller() {
    }

    @ZenMethod
    public static void add(String id, IIngredient[] itemInputs, ILiquidStack fluidInput,
                           IItemStack container, IItemStack output, int cookingTime, boolean consumeContainer) {
        String recipeId = ZenRecipeUtil.id(id, "chiller recipe");
        List<MixingBowlIngredient> items = ZenRecipeUtil.ingredients(itemInputs, 0, 4, "Chiller recipe");
        FluidStack fluid = ZenRecipeUtil.fluid(fluidInput, true, "Chiller fluid input");
        ItemStack containerStack = ZenRecipeUtil.item(container, true, "Chiller container");
        ItemStack result = ZenRecipeUtil.item(output, false, "Chiller output");
        if (recipeId == null || items == null || containerStack == null || result == null || cookingTime <= 0) {
            return;
        }
        if (items.isEmpty() && fluid == null && containerStack.isEmpty()) {
            crafttweaker.CraftTweakerAPI.logError("Chiller recipes require at least one item, fluid or container input");
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy chiller recipe " + recipeId,
            () -> ChillerRecipeManager.register(recipeId, items, fluid, containerStack, result, cookingTime, consumeContainer));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "chiller recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("chiller recipe " + recipeId, () -> ChillerRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Chiller output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy chiller recipes by output",
                () -> ChillerRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy chiller recipes", ChillerRecipeManager::removeAll);
    }
}
