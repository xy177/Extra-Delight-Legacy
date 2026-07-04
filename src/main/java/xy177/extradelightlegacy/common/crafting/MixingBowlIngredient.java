package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class MixingBowlIngredient {
    private final ItemStack stack;
    private final String oreName;

    private MixingBowlIngredient(ItemStack stack, String oreName) {
        this.stack = stack == null ? ItemStack.EMPTY : stack.copy();
        this.oreName = oreName;
    }

    public static MixingBowlIngredient stack(ItemStack stack) {
        return new MixingBowlIngredient(stack, null);
    }

    public static MixingBowlIngredient ore(String oreName) {
        return new MixingBowlIngredient(ItemStack.EMPTY, oreName);
    }

    public boolean matches(ItemStack input) {
        if (input.isEmpty()) {
            return false;
        }
        if (oreName != null && !oreName.isEmpty()) {
            if (!stack.isEmpty() && input.getCount() < stack.getCount()) {
                return false;
            }
            for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
                if (OreDictionary.itemMatches(oreStack, input, false)) {
                    return true;
                }
            }
            return false;
        }
        return !stack.isEmpty() && input.getCount() >= stack.getCount() && OreDictionary.itemMatches(stack, input, false);
    }

    public ItemStack getDisplayStack() {
        if (!stack.isEmpty()) {
            return stack.copy();
        }
        if (oreName != null && !oreName.isEmpty()) {
            for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
                if (!oreStack.isEmpty()) {
                    return oreStack.copy();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public int getRequiredCount() {
        return stack.isEmpty() ? 1 : stack.getCount();
    }
}
