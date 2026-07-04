package xy177.extradelightlegacy.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.Map;

public final class MixingBowlFluidIngredient {
    private final String fluidId;
    private final int amount;
    private final ItemStack displayStack;

    public MixingBowlFluidIngredient(String fluidId, int amount, ItemStack displayStack) {
        this.fluidId = fluidId;
        this.amount = amount;
        this.displayStack = displayStack == null ? ItemStack.EMPTY : displayStack.copy();
    }

    public String getFluidId() {
        return fluidId;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getDisplayStack() {
        return displayStack.copy();
    }

    public boolean matches(String storedFluidId, int storedAmount) {
        return fluidId != null && fluidId.equals(storedFluidId) && storedAmount >= amount;
    }

    public boolean matches(Map<String, Integer> storedFluids) {
        if (fluidId == null || storedFluids == null) {
            return false;
        }
        Integer storedAmount = storedFluids.get(fluidId);
        return storedAmount != null && storedAmount >= amount;
    }
}
