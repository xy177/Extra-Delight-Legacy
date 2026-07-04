package xy177.extradelightlegacy.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipe;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.tile.TileEntityMixingBowl;

import java.util.List;

public class ContainerMixingBowl extends Container {
    private static final int INPUT_START = 0;
    private static final int INPUT_END = 9;
    private static final int CONTAINER_SLOT = 9;
    private static final int LIQUID_IN_SLOT = 10;
    private static final int LIQUID_OUT_SLOT = 11;
    private static final int RESULT_SLOT = 12;
    private static final int PLAYER_INV_START = 13;
    private static final int PLAYER_INV_END = 40;
    private static final int HOTBAR_START = 40;
    private static final int HOTBAR_END = 49;

    private final World world;
    private final BlockPos pos;
    private final TileEntityMixingBowl bowl;
    private final BowlInventory inventory = new BowlInventory();
    private final InventoryPlayer playerInventory;
    private List<MixingBowlRecipe> cachedRecipes;

    public ContainerMixingBowl(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.playerInventory = playerInventory;
        this.world = world;
        this.pos = pos;
        this.bowl = (TileEntityMixingBowl) world.getTileEntity(pos);
        syncInventoryFromTile();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addSlotToContainer(new IngredientSlot(inventory, i * 3 + j, 64 + j * 18, 5 + i * 18));
            }
        }
        addSlotToContainer(new ContainerSlot(inventory, CONTAINER_SLOT, 122, 46));
        addSlotToContainer(new LiquidInputSlot(inventory, LIQUID_IN_SLOT, 17, -5));
        addSlotToContainer(new LiquidOutputSlot(inventory, LIQUID_OUT_SLOT, 17, 51));
        addSlotToContainer(new ResultSlot(inventory, RESULT_SLOT, 147, 23));

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 83 + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return EDLBlocks.MIXING_BOWL.getBlock() != null
            && world.getBlockState(pos).getBlock() == EDLBlocks.MIXING_BOWL.getBlock()
            && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        updatePreview();
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
            return ItemStack.EMPTY;
        } else if (index == LIQUID_IN_SLOT || index == LIQUID_OUT_SLOT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= INPUT_START && index < INPUT_END) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index == CONTAINER_SLOT) {
            if (!mergeItemStack(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
            if (tryMergeIntoBowl(stack)) {
                slot.onSlotChanged();
            } else if (!mergeItemStack(stack, HOTBAR_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index >= HOTBAR_START && index < HOTBAR_END) {
            if (tryMergeIntoBowl(stack)) {
                slot.onSlotChanged();
            } else if (!mergeItemStack(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        }
        slot.onSlotChanged();
        return copied;
    }

    private boolean tryMergeIntoBowl(ItemStack stack) {
        if (bowl != null && bowl.canSeparateEgg(stack) && mergeItemStack(stack, LIQUID_IN_SLOT, LIQUID_IN_SLOT + 1, false)) {
            return true;
        }
        if (bowl != null && bowl.canFillFromContainer(stack) && mergeItemStack(stack, LIQUID_IN_SLOT, LIQUID_IN_SLOT + 1, false)) {
            return true;
        }
        if (bowl != null && bowl.canDrainToContainer(stack) && mergeItemStack(stack, LIQUID_OUT_SLOT, LIQUID_OUT_SLOT + 1, false)) {
            return true;
        }
        if (MixingBowlRecipeManager.isValidContainer(stack) && mergeItemStack(stack, CONTAINER_SLOT, CONTAINER_SLOT + 1, false)) {
            return true;
        }
        return MixingBowlRecipeManager.isValidIngredient(stack) && mergeItemStack(stack, INPUT_START, INPUT_END, false);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote && bowl != null) {
            returnTransientSlot(playerIn, LIQUID_IN_SLOT);
            returnTransientSlot(playerIn, LIQUID_OUT_SLOT);
            inventory.setInventorySlotContents(RESULT_SLOT, ItemStack.EMPTY);
            syncTileFromInventory();
        }
    }

    private void returnTransientSlot(EntityPlayer player, int slotIndex) {
        ItemStack stack = inventory.getStackInSlot(slotIndex);
        if (!stack.isEmpty()) {
            inventory.setInventorySlotContents(slotIndex, ItemStack.EMPTY);
            if (!player.inventory.addItemStackToInventory(stack)) {
                player.dropItem(stack, false);
            }
        }
    }

    public List<MixingBowlRecipe> getRecipes() {
        syncInventoryFromTile();
        return cachedRecipes;
    }

    public boolean hasInputItem() {
        syncInventoryFromTile();
        return !inventory.getStackInSlot(CONTAINER_SLOT).isEmpty() || cachedRecipes != null && !cachedRecipes.isEmpty();
    }

    public int getRequiredStirs() {
        MixingBowlRecipe recipe = bowl == null ? null : bowl.getCurrentRecipe();
        return recipe == null ? 0 : recipe.getStirs();
    }

    public ItemStack getUtensilPreview() {
        MixingBowlRecipe recipe = bowl == null ? null : bowl.getCurrentRecipe();
        if (recipe == null || recipe.getUtensilOre() == null || recipe.getUtensilOre().isEmpty()) {
            return ItemStack.EMPTY;
        }

        for (ItemStack stack : OreDictionary.getOres(recipe.getUtensilOre(), false)) {
            if (!stack.isEmpty()) {
                return stack.copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public String getFluidId() {
        return bowl == null ? "" : bowl.getFluidId();
    }

    public int getFluidAmount() {
        return bowl == null ? 0 : bowl.getFluidAmount();
    }

    public java.util.Map<String, Integer> getFluids() {
        return bowl == null ? java.util.Collections.emptyMap() : bowl.getFluids();
    }

    private void syncInventoryFromTile() {
        if (bowl == null) {
            return;
        }
        inventory.suppressChange = true;
        for (int i = 0; i < TileEntityMixingBowl.INGREDIENT_SLOTS; i++) {
            inventory.setInventorySlotContents(i, bowl.getItems().get(i).copy());
        }
        inventory.setInventorySlotContents(CONTAINER_SLOT, bowl.getItems().get(TileEntityMixingBowl.CONTAINER_SLOT).copy());
        cachedRecipes = MixingBowlRecipeManager.getRecipes();
        inventory.suppressChange = false;
        updatePreview();
    }

    private void syncTileFromInventory() {
        if (bowl == null) {
            return;
        }
        for (int i = 0; i < TileEntityMixingBowl.INGREDIENT_SLOTS; i++) {
            bowl.getItems().set(i, inventory.getStackInSlot(i).copy());
        }
        bowl.getItems().set(TileEntityMixingBowl.CONTAINER_SLOT, inventory.getStackInSlot(CONTAINER_SLOT).copy());
        bowl.markUpdated();
        updatePreview();
    }

    private void updatePreview() {
        if (bowl == null) {
            return;
        }
        MixingBowlRecipe recipe = bowl.getCurrentRecipe();
        inventory.suppressChange = true;
        inventory.setInventorySlotContents(RESULT_SLOT, recipe == null ? ItemStack.EMPTY : recipe.getOutput());
        inventory.suppressChange = false;
        cachedRecipes = MixingBowlRecipeManager.getRecipes();
    }

    private final class BowlInventory extends InventoryBasic {
        private boolean suppressChange;

        private BowlInventory() {
            super("extradelightlegacy.mixing_bowl", false, 13);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            if (!suppressChange) {
                syncTileFromInventory();
            }
        }
    }

    private static final class IngredientSlot extends Slot {
        private IngredientSlot(InventoryBasic inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return MixingBowlRecipeManager.isValidIngredient(stack);
        }
    }

    private static final class ContainerSlot extends Slot {
        private ContainerSlot(InventoryBasic inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return MixingBowlRecipeManager.isValidContainer(stack);
        }
    }

    private final class LiquidInputSlot extends Slot {
        private LiquidInputSlot(InventoryBasic inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return bowl != null
                && inventory.getStackInSlot(LIQUID_OUT_SLOT).isEmpty()
                && (bowl.canSeparateEgg(stack) || bowl.canFillFromContainer(stack));
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerMixingBowl.this.inventory.suppressChange && bowl != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    ItemStack yolk = bowl.trySeparateEgg(stack, true);
                    if (!yolk.isEmpty()) {
                        ContainerMixingBowl.this.inventory.suppressChange = true;
                        putStack(ItemStack.EMPTY);
                        ContainerMixingBowl.this.inventory.setInventorySlotContents(LIQUID_OUT_SLOT, yolk);
                        ContainerMixingBowl.this.inventory.suppressChange = false;
                        super.onSlotChanged();
                        return;
                    }
                    TileEntityMixingBowl.FluidContainerResult result = bowl.tryFillFromContainer(stack);
                    if (result != null) {
                        ContainerMixingBowl.this.inventory.suppressChange = true;
                        putStack(ItemStack.EMPTY);
                        ContainerMixingBowl.this.inventory.setInventorySlotContents(LIQUID_OUT_SLOT, result.getEmptyContainer());
                        ContainerMixingBowl.this.inventory.suppressChange = false;
                    }
                }
            }
            super.onSlotChanged();
        }
    }

    private final class LiquidOutputSlot extends Slot {
        private LiquidOutputSlot(InventoryBasic inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return bowl != null && bowl.canDrainToContainer(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public void onSlotChanged() {
            if (!ContainerMixingBowl.this.inventory.suppressChange && bowl != null) {
                ItemStack stack = getStack();
                if (!stack.isEmpty()) {
                    ItemStack filled = bowl.tryDrainToContainer(stack);
                    if (!filled.isEmpty()) {
                        putStack(filled);
                    }
                }
            }
            super.onSlotChanged();
        }
    }

    private final class ResultSlot extends Slot {
        private ResultSlot(InventoryBasic inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeStack(EntityPlayer playerIn) {
            return false;
        }
    }
}
