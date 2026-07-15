package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlFluidIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.registry.EDLFluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ZenRecipeUtil {
    private ZenRecipeUtil() {
    }

    static String id(String id, String context) {
        if (id == null || id.trim().isEmpty()) {
            CraftTweakerAPI.logError(context + " id cannot be empty");
            return null;
        }
        String value = id.trim();
        if (value.indexOf(':') < 0) {
            value = ExtraDelightLegacy.MODID + ":" + value;
        }
        try {
            return new ResourceLocation(value).toString();
        } catch (RuntimeException ignored) {
            CraftTweakerAPI.logError("Invalid " + context + " id: " + id);
            return null;
        }
    }

    static ItemStack item(IItemStack stack, boolean allowEmpty, String context) {
        ItemStack result = stack == null ? ItemStack.EMPTY : CraftTweakerMC.getItemStack(stack).copy();
        if (!allowEmpty && result.isEmpty()) {
            CraftTweakerAPI.logError(context + " cannot be empty");
            return null;
        }
        return result;
    }

    static FluidStack fluid(ILiquidStack stack, boolean allowEmpty, String context) {
        FluidStack result = stack == null ? null : CraftTweakerMC.getLiquidStack(stack);
        if (result != null) {
            result = result.copy();
        }
        if (!allowEmpty && (result == null || result.getFluid() == null || result.amount <= 0)) {
            CraftTweakerAPI.logError(context + " cannot be empty");
            return null;
        }
        return result;
    }

    static MixingBowlIngredient ingredient(IIngredient ingredient, String context) {
        if (ingredient == null || ingredient.getAmount() <= 0) {
            CraftTweakerAPI.logError(context + " cannot be empty");
            return null;
        }
        if (ingredient.hasTransformers() || ingredient.hasNewTransformers()) {
            CraftTweakerAPI.logError(context + " does not support ingredient transformers: " + ingredient.toCommandString());
            return null;
        }
        String command = ingredient.toCommandString();
        if (command != null && (command.contains(".only(") || command.contains(".onlyWithTag(")
            || command.contains(".onlyDamaged(") || command.contains(".marked("))) {
            CraftTweakerAPI.logError(context + " does not support conditional or marked ingredients: " + command);
            return null;
        }

        int amount = ingredient.getAmount();
        String oreName = oreName(ingredient, command);
        if (oreName != null) {
            return MixingBowlIngredient.ore(oreName, amount);
        }

        IItemStack[] itemOptions = ingredient.getItemArray();
        if (itemOptions == null || itemOptions.length == 0) {
            CraftTweakerAPI.logError("Unsupported " + context + ": " + command);
            return null;
        }
        List<ItemStack> options = new ArrayList<>();
        for (IItemStack option : itemOptions) {
            ItemStack nativeStack = option == null ? ItemStack.EMPTY : CraftTweakerMC.getItemStack(option).copy();
            if (!nativeStack.isEmpty()) {
                nativeStack.setCount(amount);
                options.add(nativeStack);
            }
        }
        if (options.isEmpty()) {
            CraftTweakerAPI.logError("Unsupported " + context + ": " + command);
            return null;
        }
        return MixingBowlIngredient.stacks(options);
    }

    static List<MixingBowlIngredient> ingredients(IIngredient[] ingredients, int min, int max, String context) {
        if (ingredients == null) {
            ingredients = new IIngredient[0];
        }
        if (ingredients.length < min || ingredients.length > max) {
            CraftTweakerAPI.logError(context + " requires between " + min + " and " + max + " item ingredients");
            return null;
        }
        List<MixingBowlIngredient> result = new ArrayList<>();
        for (int i = 0; i < ingredients.length; i++) {
            MixingBowlIngredient converted = ingredient(ingredients[i], context + " ingredient " + (i + 1));
            if (converted == null) {
                return null;
            }
            result.add(converted);
        }
        return Collections.unmodifiableList(result);
    }

    static List<MixingBowlFluidIngredient> mixingFluids(ILiquidStack[] fluids, String context) {
        if (fluids == null || fluids.length == 0) {
            return Collections.emptyList();
        }
        List<MixingBowlFluidIngredient> result = new ArrayList<>();
        for (int i = 0; i < fluids.length; i++) {
            FluidStack fluid = fluid(fluids[i], false, context + " fluid " + (i + 1));
            if (fluid == null) {
                return null;
            }
            String logicalId = EDLFluids.logicalIdForFluid(fluid.getFluid());
            if (logicalId.isEmpty()) {
                CraftTweakerAPI.logError(context + " cannot store fluid " + fluid.getFluid().getName() + " in the mixing bowl");
                return null;
            }
            result.add(new MixingBowlFluidIngredient(logicalId, fluid.amount, fluidDisplay(fluid)));
        }
        return Collections.unmodifiableList(result);
    }

    static ResourceLocation entityId(String entityId, String context) {
        try {
            return new ResourceLocation(entityId);
        } catch (RuntimeException ignored) {
            CraftTweakerAPI.logError(context + " has an invalid entity id: " + entityId);
            return null;
        }
    }

    private static String oreName(IIngredient ingredient, String command) {
        if (ingredient instanceof IOreDictEntry) {
            return ((IOreDictEntry) ingredient).getName();
        }
        if (command == null) {
            return null;
        }
        int start = command.indexOf("<ore:");
        int end = command.indexOf('>', start + 5);
        if (start < 0 || end < 0 || command.indexOf('|') >= 0) {
            return null;
        }
        String suffix = command.substring(end + 1).trim();
        if (!suffix.isEmpty() && !suffix.matches("\\*\\s*\\d+")) {
            return null;
        }
        return command.substring(start + 5, end);
    }

    private static ItemStack fluidDisplay(FluidStack fluid) {
        BottleFluidRecipe bottle = BottleFluidRecipeManager.findByFluidStack(fluid);
        if (bottle != null && !bottle.getBottle().isEmpty()) {
            return bottle.getBottle();
        }
        ItemStack bucket = FluidUtil.getFilledBucket(fluid);
        return bucket.isEmpty() ? ItemStack.EMPTY : bucket;
    }
}
