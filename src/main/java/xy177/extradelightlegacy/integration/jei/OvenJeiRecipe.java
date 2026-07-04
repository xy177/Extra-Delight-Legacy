package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.OvenRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OvenJeiRecipe implements IRecipeWrapper {
    private final List<OvenRecipe> recipes;

    public OvenJeiRecipe(OvenRecipe recipe) {
        this(Collections.singletonList(recipe));
    }

    public OvenJeiRecipe(List<OvenRecipe> recipes) {
        this.recipes = new ArrayList<>(recipes);
    }

    public List<ItemStack> getInputs() {
        List<List<ItemStack>> slots = getInputSlots();
        List<ItemStack> stacks = new ArrayList<>();
        for (List<ItemStack> slot : slots) {
            if (!slot.isEmpty()) {
                stacks.add(slot.get(0).copy());
            }
        }
        return stacks;
    }

    public List<List<ItemStack>> getInputSlots() {
        if (isCondensedSingleInput()) {
            List<ItemStack> variants = new ArrayList<>();
            for (OvenRecipe recipe : recipes) {
                ItemStack stack = getCondensedInput(recipe);
                if (!stack.isEmpty()) {
                    variants.add(stack);
                }
            }
            return Collections.singletonList(variants);
        }

        OvenRecipe recipe = recipes.get(0);
        List<List<ItemStack>> slots = new ArrayList<>();
        for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
            ItemStack stack = ingredient.getDisplayStack();
            if (!stack.isEmpty()) {
                slots.add(Collections.singletonList(stack));
            }
        }
        return slots;
    }

    public List<ItemStack> getContainerVariants() {
        List<ItemStack> stacks = new ArrayList<>();
        for (OvenRecipe recipe : recipes) {
            ItemStack stack = recipe.getContainer();
            if (!stack.isEmpty() && !containsItemStack(stacks, stack)) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public ItemStack getContainer() {
        return recipes.get(0).getContainer();
    }

    public List<ItemStack> getOutputVariants() {
        List<ItemStack> outputs = new ArrayList<>();
        for (OvenRecipe recipe : recipes) {
            outputs.add(recipe.getOutput());
        }
        return outputs;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = getInputs();
        inputs.addAll(getContainerVariants());
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutputs(ItemStack.class, getOutputVariants());
    }

    private boolean isCondensedSingleInput() {
        return recipes.size() > 1 && !getCondensedInput(recipes.get(0)).isEmpty();
    }

    private static ItemStack getCondensedInput(OvenRecipe recipe) {
        if (recipe.getIngredients().isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack first = recipe.getIngredients().get(0).getDisplayStack();
        if (first.isEmpty()) {
            return ItemStack.EMPTY;
        }

        for (MixingBowlIngredient ingredient : recipe.getIngredients()) {
            ItemStack current = ingredient.getDisplayStack();
            if (current.isEmpty() || !ItemStack.areItemsEqual(first, current)
                || !ItemStack.areItemStackTagsEqual(first, current)) {
                return ItemStack.EMPTY;
            }
        }

        ItemStack condensed = first.copy();
        condensed.setCount(first.getCount() * recipe.getIngredients().size());
        return condensed;
    }

    private static boolean containsItemStack(List<ItemStack> stacks, ItemStack target) {
        for (ItemStack stack : stacks) {
            if (ItemStack.areItemsEqual(stack, target)
                && ItemStack.areItemStackTagsEqual(stack, target)
                && stack.getCount() == target.getCount()) {
                return true;
            }
        }
        return false;
    }
}
