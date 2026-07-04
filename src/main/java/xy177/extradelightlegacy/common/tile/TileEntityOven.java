package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.crafting.OvenRecipe;
import xy177.extradelightlegacy.common.crafting.OvenRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.util.EDLHeatSources;

public class TileEntityOven extends TileEntity implements ITickable {
    public static final int INGREDIENT_SLOTS = 9;
    public static final int CONTAINER_SLOT = 9;
    public static final int RESULT_SLOT = 10;
    public static final int SLOT_COUNT = 11;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int cookTime;
    private int cookTimeTotal;

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public int getCookTime() {
        return cookTime;
    }

    public int getCookTimeTotal() {
        return cookTimeTotal;
    }

    public OvenRecipe getCurrentRecipe() {
        return OvenRecipeManager.find(getIngredientStacks(), items.get(CONTAINER_SLOT));
    }

    @Override
    public void update() {
        if (world == null || world.isRemote) {
            return;
        }

        OvenRecipe recipe = getCurrentRecipe();
        if (recipe == null || !isHeated() || !canOutput(recipe.getOutput())) {
            cookTime = 0;
            cookTimeTotal = recipe == null ? 0 : recipe.getCookingTime();
            markUpdated();
            return;
        }

        cookTimeTotal = recipe.getCookingTime();
        cookTime++;
        if (cookTime >= cookTimeTotal) {
            craft(recipe);
        }
        markUpdated();
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (slot < INGREDIENT_SLOTS) {
            return OvenRecipeManager.isValidIngredient(stack);
        }
        if (slot == CONTAINER_SLOT) {
            return OvenRecipeManager.isValidContainer(stack);
        }
        return false;
    }

    public boolean isHeated() {
        return EDLHeatSources.isCookwareHeated(world, pos);
    }

    public void clear() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        cookTime = 0;
        cookTimeTotal = 0;
    }

    private boolean canOutput(ItemStack output) {
        ItemStack current = items.get(RESULT_SLOT);
        if (current.isEmpty()) {
            return true;
        }
        return ItemStack.areItemsEqual(current, output)
            && ItemStack.areItemStackTagsEqual(current, output)
            && current.getCount() + output.getCount() <= current.getMaxStackSize();
    }

    private void craft(OvenRecipe recipe) {
        int[] matchedSlots = recipe.matchIngredientSlots(getIngredientStacks());
        if (matchedSlots == null) {
            cookTime = 0;
            return;
        }

        ItemStack output = recipe.getOutput();
        ItemStack current = items.get(RESULT_SLOT);
        if (current.isEmpty()) {
            items.set(RESULT_SLOT, output);
        } else {
            current.grow(output.getCount());
        }

        for (int i = 0; i < matchedSlots.length; i++) {
            ItemStack stack = items.get(matchedSlots[i]);
            if (stack.isEmpty()) {
                continue;
            }
            MixingBowlIngredient ingredient = recipe.getIngredients().get(i);
            ItemStack container = stack.getItem().getContainerItem(stack);
            stack.shrink(ingredient.getRequiredCount());
            if (!container.isEmpty()) {
                EntityItem entity = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.7D, pos.getZ() + 0.5D, container);
                entity.setDefaultPickupDelay();
                world.spawnEntity(entity);
            }
            if (stack.isEmpty()) {
                items.set(matchedSlots[i], ItemStack.EMPTY);
            }
        }

        if (recipe.shouldConsumeContainer()) {
            ItemStack container = items.get(CONTAINER_SLOT);
            container.shrink(recipe.getContainer().getCount());
            if (container.isEmpty()) {
                items.set(CONTAINER_SLOT, ItemStack.EMPTY);
            }
        }
        cookTime = 0;
    }

    private ItemStack[] getIngredientStacks() {
        ItemStack[] stacks = new ItemStack[INGREDIENT_SLOTS];
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            stacks[i] = items.get(i);
        }
        return stacks;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        compound.setInteger("CookTime", cookTime);
        compound.setInteger("CookTimeTotal", cookTimeTotal);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ItemStackHelper.loadAllItems(compound, items);
        cookTime = compound.getInteger("CookTime");
        cookTimeTotal = compound.getInteger("CookTimeTotal");
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    public void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}
