package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.MortarRecipeManager;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Mortar")
public final class ZenMortar {
    private ZenMortar() {
    }

    @ZenMethod
    public static void add(String id, IIngredient input, IItemStack output, int grinds) {
        addWithFluid(id, input, output, null, grinds);
    }

    @ZenMethod
    public static void addWithFluid(String id, IIngredient input, IItemStack output, ILiquidStack fluidOutput, int grinds) {
        String recipeId = ZenRecipeUtil.id(id, "mortar recipe");
        MixingBowlIngredient ingredient = ZenRecipeUtil.ingredient(input, "Mortar input");
        ItemStack result = ZenRecipeUtil.item(output, true, "Mortar item output");
        FluidStack fluid = ZenRecipeUtil.fluid(fluidOutput, true, "Mortar fluid output");
        if (recipeId == null || ingredient == null || result == null || ingredient.getRequiredCount() != 1
            || (result.isEmpty() && fluid == null) || grinds <= 0) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy mortar recipe " + recipeId,
            () -> MortarRecipeManager.register(recipeId, ingredient, result, fluid, grinds));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "mortar recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("mortar recipe " + recipeId, () -> MortarRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByItemOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Mortar item output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy mortar recipes by item output",
                () -> MortarRecipeManager.removeByItemOutput(result));
        }
    }

    @ZenMethod
    public static void removeByFluidOutput(ILiquidStack output) {
        FluidStack result = ZenRecipeUtil.fluid(output, false, "Mortar fluid output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy mortar recipes by fluid output",
                () -> MortarRecipeManager.removeByFluidOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy mortar recipes", MortarRecipeManager::removeAll);
    }
}
