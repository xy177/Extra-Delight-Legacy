package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.ExtraDelightLegacy;

final class RecipeManagerUtil {
    private RecipeManagerUtil() {
    }

    static String normalizeId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        String value = id.trim();
        if (value.indexOf(':') < 0) {
            value = ExtraDelightLegacy.MODID + ":" + value;
        }
        try {
            return new ResourceLocation(value).toString();
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    static String generatedId(String type, Object... values) {
        StringBuilder key = new StringBuilder(type);
        if (values != null) {
            for (Object value : values) {
                key.append('|').append(valueKey(value));
            }
        }
        return ExtraDelightLegacy.MODID + ":generated/" + type + "/"
            + Integer.toUnsignedString(key.toString().hashCode(), 16);
    }

    static boolean stackMatches(ItemStack expected, ItemStack actual) {
        if (expected == null || actual == null || expected.isEmpty() || actual.isEmpty()) {
            return false;
        }
        return OreDictionary.itemMatches(expected, actual, false)
            && (!expected.hasTagCompound() || ItemStack.areItemStackTagsEqual(expected, actual));
    }

    static boolean fluidMatches(FluidStack expected, FluidStack actual) {
        return expected != null && actual != null && expected.getFluid() != null && actual.getFluid() != null
            && expected.isFluidEqual(actual);
    }

    private static String valueKey(Object value) {
        if (value instanceof ItemStack) {
            ItemStack stack = (ItemStack) value;
            if (stack.isEmpty() || stack.getItem().getRegistryName() == null) {
                return "empty";
            }
            return stack.getItem().getRegistryName() + ":" + stack.getMetadata() + ":"
                + (stack.hasTagCompound() ? stack.getTagCompound().toString() : "");
        }
        if (value instanceof FluidStack) {
            FluidStack stack = (FluidStack) value;
            return stack.getFluid() == null ? "empty" : stack.getFluid().getName() + ":" + stack.amount;
        }
        return String.valueOf(value);
    }
}
