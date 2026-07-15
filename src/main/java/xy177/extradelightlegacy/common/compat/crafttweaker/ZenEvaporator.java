package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipeManager;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Evaporator")
public final class ZenEvaporator {
    private ZenEvaporator() {
    }

    @ZenMethod
    public static void add(String id, ILiquidStack input, IItemStack output, int cookingTime) {
        String recipeId = ZenRecipeUtil.id(id, "evaporator recipe");
        FluidStack fluid = ZenRecipeUtil.fluid(input, false, "Evaporator input");
        ItemStack result = ZenRecipeUtil.item(output, false, "Evaporator output");
        if (recipeId == null || fluid == null || result == null || cookingTime <= 0) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy evaporator recipe " + recipeId,
            () -> EvaporatorRecipeManager.register(recipeId, fluid, result, cookingTime));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "evaporator recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("evaporator recipe " + recipeId, () -> EvaporatorRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Evaporator output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy evaporator recipes by output",
                () -> EvaporatorRecipeManager.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy evaporator recipes", EvaporatorRecipeManager::removeAll);
    }
}
