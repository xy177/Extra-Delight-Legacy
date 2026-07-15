package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;

@ZenRegister
@ZenClass("mods.extradelightlegacy.BottleFluid")
public final class ZenBottleFluid {
    private ZenBottleFluid() {
    }

    @ZenMethod
    public static void add(String id, IItemStack bottle, ILiquidStack fluid, IItemStack bucket) {
        String recipeId = ZenRecipeUtil.id(id, "bottle fluid mapping");
        ItemStack bottleStack = ZenRecipeUtil.item(bottle, false, "Bottle fluid bottle");
        FluidStack fluidStack = ZenRecipeUtil.fluid(fluid, false, "Bottle fluid fluid");
        ItemStack bucketStack = ZenRecipeUtil.item(bucket, false, "Bottle fluid bucket");
        if (recipeId == null || bottleStack == null || fluidStack == null || bucketStack == null) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy bottle fluid mapping " + recipeId,
            () -> BottleFluidRecipeManager.register(recipeId, bottleStack, fluidStack, bucketStack));
    }

    @ZenMethod
    public static void remove(String id) {
        String recipeId = ZenRecipeUtil.id(id, "bottle fluid mapping");
        if (recipeId != null) {
            CraftTweakerActions.remove("bottle fluid mapping " + recipeId, () -> BottleFluidRecipeManager.remove(recipeId));
        }
    }

    @ZenMethod
    public static void removeByBottle(IItemStack bottle) {
        ItemStack stack = ZenRecipeUtil.item(bottle, false, "Bottle fluid bottle");
        if (stack != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy bottle fluid mappings by bottle",
                () -> BottleFluidRecipeManager.removeByBottle(stack));
        }
    }

    @ZenMethod
    public static void removeByFluid(ILiquidStack fluid) {
        FluidStack stack = ZenRecipeUtil.fluid(fluid, false, "Bottle fluid fluid");
        if (stack != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy bottle fluid mappings by fluid",
                () -> BottleFluidRecipeManager.removeByFluid(stack));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy bottle fluid mappings", BottleFluidRecipeManager::removeAll);
    }
}
