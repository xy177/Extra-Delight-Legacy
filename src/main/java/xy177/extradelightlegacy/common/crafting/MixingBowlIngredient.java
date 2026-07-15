package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MixingBowlIngredient {
    private final List<ItemStack> stacks;
    private final String oreName;
    private final int oreCount;

    private MixingBowlIngredient(List<ItemStack> stacks, String oreName, int oreCount) {
        List<ItemStack> copies = new ArrayList<>();
        if (stacks != null) {
            for (ItemStack stack : stacks) {
                if (stack != null && !stack.isEmpty()) {
                    copies.add(stack.copy());
                }
            }
        }
        this.stacks = Collections.unmodifiableList(copies);
        this.oreName = oreName;
        this.oreCount = Math.max(1, oreCount);
    }

    public static MixingBowlIngredient stack(ItemStack stack) {
        return new MixingBowlIngredient(Collections.singletonList(stack), null, 1);
    }

    public static MixingBowlIngredient stacks(List<ItemStack> stacks) {
        return new MixingBowlIngredient(stacks, null, 1);
    }

    public static MixingBowlIngredient ore(String oreName) {
        return ore(oreName, 1);
    }

    public static MixingBowlIngredient ore(String oreName, int count) {
        return new MixingBowlIngredient(Collections.emptyList(), oreName, count);
    }

    public boolean matches(ItemStack input) {
        if (input.isEmpty()) {
            return false;
        }
        if (oreName != null && !oreName.isEmpty()) {
            if (input.getCount() < oreCount) {
                return false;
            }
            return matchesIgnoringCount(input);
        }
        for (ItemStack stack : stacks) {
            if (input.getCount() >= stack.getCount() && RecipeManagerUtil.stackMatches(stack, input)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesIgnoringCount(ItemStack input) {
        if (input.isEmpty()) {
            return false;
        }
        if (oreName != null && !oreName.isEmpty()) {
            for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
                if (RecipeManagerUtil.stackMatches(oreStack, input)) {
                    return true;
                }
            }
            return false;
        }
        for (ItemStack stack : stacks) {
            if (RecipeManagerUtil.stackMatches(stack, input)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getDisplayStack() {
        List<ItemStack> matching = getMatchingStacks();
        return matching.isEmpty() ? ItemStack.EMPTY : matching.get(0).copy();
    }

    public List<ItemStack> getMatchingStacks() {
        List<ItemStack> matching = new ArrayList<>();
        for (ItemStack stack : stacks) {
            matching.add(stack.copy());
        }
        if (oreName != null && !oreName.isEmpty()) {
            for (ItemStack oreStack : OreDictionary.getOres(oreName, false)) {
                if (!oreStack.isEmpty()) {
                    ItemStack copy = oreStack.copy();
                    copy.setCount(oreCount);
                    matching.add(copy);
                }
            }
        }
        return Collections.unmodifiableList(matching);
    }

    public int getRequiredCount() {
        return stacks.isEmpty() ? oreCount : stacks.get(0).getCount();
    }

    public String getOreName() {
        return oreName;
    }
}
