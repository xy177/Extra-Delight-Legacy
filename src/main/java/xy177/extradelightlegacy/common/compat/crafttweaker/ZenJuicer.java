package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.JuicerRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;

@ZenRegister
@ZenClass("mods.extradelightlegacy.Juicer")
public final class ZenJuicer {
    private ZenJuicer() {
    }

    @ZenMethod
    public static void add(String id, IIngredient input, IItemStack itemOutput,
                           ILiquidStack fluidOutput, int itemChance) {
        String recipeId = ZenRecipeUtil.id(id, "juicer recipe");
        MixingBowlIngredient ingredient = ZenRecipeUtil.ingredient(input, "Juicer input");
        ItemStack item = ZenRecipeUtil.item(itemOutput, true, "Juicer item output");
        FluidStack fluid = ZenRecipeUtil.fluid(fluidOutput, false, "Juicer fluid output");
        if (recipeId == null || ingredient == null || item == null || fluid == null
            || ingredient.getRequiredCount() != 1 || itemChance < 0 || itemChance > 100) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy juicer recipe " + recipeId,
            () -> JuicerRecipeManager.register(recipeId, ingredient, item, fluid, itemChance));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "juicer recipe");
        if (recipeId != null) {
            CraftTweakerActions.remove("juicer recipe " + recipeId, () -> JuicerRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByItemOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Juicer item output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy juicer recipes by item output",
                () -> JuicerRecipeManager.removeByItemOutput(result));
        }
    }

    @ZenMethod
    public static void removeByFluidOutput(ILiquidStack output) {
        FluidStack result = ZenRecipeUtil.fluid(output, false, "Juicer fluid output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy juicer recipes by fluid output",
                () -> JuicerRecipeManager.removeByFluidOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy juicer recipes", JuicerRecipeManager::removeAll);
    }
}
