package xy177.extradelightlegacy.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipe;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContainerDoughShaping extends Container {
    private static final int INPUT_SLOT = 0;
    private static final int RESULT_SLOT = 1;
    private static final int PLAYER_INV_START = 2;
    private static final int PLAYER_INV_END = 29;
    private static final int HOTBAR_START = 29;
    private static final int HOTBAR_END = 37;

    private final World world;
    private final BlockPos pos;
    private final InventoryPlayer playerInventory;
    private final DoughInventory doughInventory = new DoughInventory();
    private final Slot inputSlot;
    private final Slot resultSlot;
    private List<DoughShapingRecipe> recipes = Collections.emptyList();
    private ItemStack lastInput = ItemStack.EMPTY;
    private int selectedIndex = -1;
    private int lastSelectedIndex = -1;
    private long lastCraftSoundTime;

    public ContainerDoughShaping(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
        this.playerInventory = playerInventory;
        this.inputSlot = addSlotToContainer(new Slot(doughInventory, INPUT_SLOT, 20, 33));
        this.resultSlot = addSlotToContainer(new ResultSlot(doughInventory, RESULT_SLOT, 143, 33));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    public List<DoughShapingRecipe> getRecipes() {
        refreshRecipeListIfNeeded();
        return new ArrayList<>(recipes);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public boolean hasInputItem() {
        refreshRecipeListIfNeeded();
        return inputSlot.getHasStack() && !recipes.isEmpty();
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int id) {
        if (id >= 0 && id < recipes.size()) {
            selectedIndex = id;
            updateResult();
            return true;
        }
        return false;
    }

    @Override
    public void onCraftMatrixChanged(net.minecraft.inventory.IInventory inventoryIn) {
        if (inventoryIn == doughInventory) {
            updateRecipeList();
        }
        super.onCraftMatrixChanged(inventoryIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return EDLBlocks.DOUGH_SHAPING.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.DOUGH_SHAPING.getBlock()
            && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            if (lastSelectedIndex != selectedIndex) {
                listener.sendWindowProperty(this, 0, selectedIndex);
            }
        }
        lastSelectedIndex = selectedIndex;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == 0) {
            selectedIndex = data;
            syncResultPreview();
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack copied = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        copied = stack.copy();
        if (index == RESULT_SLOT) {
            if (!quickCraftSelectedResult(playerIn)) {
                return ItemStack.EMPTY;
            }
            detectAndSendChanges();
            return copied;
        } else if (index == INPUT_SLOT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (DoughShapingRecipeManager.countMatches(stack) > 0) {
            if (!mergeItemStack(stack, INPUT_SLOT, RESULT_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            if (!mergeItemStack(stack, HOTBAR_START, HOTBAR_END + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= HOTBAR_START && index <= HOTBAR_END && !mergeItemStack(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        }

        slot.onSlotChanged();
        if (stack.getCount() == copied.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(playerIn, stack);
        detectAndSendChanges();
        return copied;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != doughInventory || slotIn.getSlotIndex() != RESULT_SLOT;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote) {
            ItemStack input = doughInventory.removeStackFromSlot(INPUT_SLOT);
            doughInventory.setInventorySlotContents(RESULT_SLOT, ItemStack.EMPTY);
            if (!input.isEmpty()) {
                if (!playerIn.isEntityAlive() || playerIn instanceof EntityPlayerMP && ((EntityPlayerMP) playerIn).hasDisconnected()) {
                    playerIn.entityDropItem(input, 0.5F);
                } else {
                    playerInventory.placeItemBackInInventory(world, input);
                }
            }
        }
    }

    private void updateRecipeList() {
        ItemStack input = doughInventory.getStackInSlot(INPUT_SLOT);
        lastInput = input.copy();
        List<DoughShapingRecipe> newRecipes = DoughShapingRecipeManager.findMatchingRecipes(input);
        boolean sameRecipes = recipes.size() == newRecipes.size();
        if (sameRecipes) {
            for (int i = 0; i < recipes.size(); i++) {
                if (!recipes.get(i).getName().equals(newRecipes.get(i).getName())) {
                    sameRecipes = false;
                    break;
                }
            }
        }

        recipes = newRecipes;
        if (!sameRecipes) {
            selectedIndex = -1;
        }
        syncResultPreview();
        detectAndSendChanges();
    }

    private void refreshRecipeListIfNeeded() {
        ItemStack input = doughInventory.getStackInSlot(INPUT_SLOT);
        if (!ItemStack.areItemsEqual(input, lastInput) || !ItemStack.areItemStackTagsEqual(input, lastInput)) {
            updateRecipeList();
        }
    }

    private void updateResult() {
        syncResultPreview();
        detectAndSendChanges();
    }

    private void syncResultPreview() {
        if (selectedIndex >= 0 && selectedIndex < recipes.size()) {
            doughInventory.setResult(recipes.get(selectedIndex).getOutput());
        } else {
            doughInventory.setResult(ItemStack.EMPTY);
        }
    }

    private void craftResult(EntityPlayer player, ItemStack crafted) {
        ItemStack input = doughInventory.getStackInSlot(INPUT_SLOT);
        if (!input.isEmpty()) {
            input.shrink(1);
            doughInventory.setInput(input);
        }
        crafted.getItem().onCreated(crafted, player.world, player);
        if (input.isEmpty()) {
            recipes = Collections.emptyList();
            selectedIndex = -1;
            syncResultPreview();
        } else {
            updateRecipeList();
        }

        long time = world.getTotalWorldTime();
        if (lastCraftSoundTime != time) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            lastCraftSoundTime = time;
        }
    }

    private boolean quickCraftSelectedResult(EntityPlayer player) {
        boolean craftedAny = false;
        while (selectedIndex >= 0 && selectedIndex < recipes.size()) {
            ItemStack input = doughInventory.getStackInSlot(INPUT_SLOT);
            if (input.isEmpty()) {
                break;
            }

            ItemStack output = recipes.get(selectedIndex).getOutput();
            if (output.isEmpty() || !canFullyMergeOutput(output)) {
                break;
            }

            ItemStack toMerge = output.copy();
            if (!mergeItemStack(toMerge, PLAYER_INV_START, HOTBAR_END + 1, true) || !toMerge.isEmpty()) {
                break;
            }

            craftResult(player, output.copy());
            craftedAny = true;
        }
        return craftedAny;
    }

    private boolean canFullyMergeOutput(ItemStack output) {
        int remaining = output.getCount();
        for (int i = PLAYER_INV_START; i <= HOTBAR_END; i++) {
            Slot slot = inventorySlots.get(i);
            if (slot == null || !slot.isItemValid(output)) {
                continue;
            }

            ItemStack stack = slot.getStack();
            if (stack.isEmpty()) {
                remaining -= Math.min(output.getMaxStackSize(), slot.getSlotStackLimit());
            } else if (ItemStack.areItemsEqual(stack, output) && ItemStack.areItemStackTagsEqual(stack, output)) {
                remaining -= Math.min(output.getMaxStackSize(), slot.getSlotStackLimit()) - stack.getCount();
            }

            if (remaining <= 0) {
                return true;
            }
        }
        return false;
    }

    private final class ResultSlot extends Slot {
        private ResultSlot(DoughInventory inventory, int index, int xPosition, int yPosition) {
            super(inventory, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack onTake(EntityPlayer player, ItemStack stack) {
            craftResult(player, stack);
            return super.onTake(player, stack);
        }
    }

    private final class DoughInventory extends InventoryBasic {
        private boolean suppressChange;

        private DoughInventory() {
            super("extradelightlegacy.dough_shaping", false, 2);
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return index == INPUT_SLOT;
        }

        @Override
        public void markDirty() {
            if (!suppressChange) {
                ContainerDoughShaping.this.onCraftMatrixChanged(this);
            }
            super.markDirty();
        }

        private void setInput(ItemStack stack) {
            suppressChange = true;
            setInventorySlotContents(INPUT_SLOT, stack);
            suppressChange = false;
        }

        private void setResult(ItemStack stack) {
            suppressChange = true;
            setInventorySlotContents(RESULT_SLOT, stack);
            suppressChange = false;
        }
    }
}
