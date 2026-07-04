package xy177.extradelightlegacy.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.Constants;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlFluidIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipe;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.event.EDLAdvancements;
import xy177.extradelightlegacy.common.registry.EDLFluids;
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TileEntityMixingBowl extends TileEntity implements IStyleableTile {
    private static final int STYLE_COUNT = 50;
    public static final int INGREDIENT_SLOTS = 9;
    public static final int CONTAINER_SLOT = 9;
    public static final int SLOT_COUNT = 10;
    private static final int TANK_CAPACITY = 6000;
    private static final int BUCKET_VOLUME = 1000;

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private final LinkedHashMap<String, Integer> fluids = new LinkedHashMap<>();
    private int stirs;
    private int style;

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public String getFluidId() {
        for (Map.Entry<String, Integer> entry : fluids.entrySet()) {
            if (entry.getValue() != null && entry.getValue() > 0) {
                return entry.getKey();
            }
        }
        return "";
    }

    public int getFluidAmount() {
        int amount = 0;
        for (Integer value : fluids.values()) {
            if (value != null && value > 0) {
                amount += value;
            }
        }
        return amount;
    }

    public int getFluidAmount(String id) {
        Integer amount = fluids.get(id);
        return amount == null ? 0 : amount;
    }

    public Map<String, Integer> getFluids() {
        return Collections.unmodifiableMap(fluids);
    }

    public int getStirs() {
        return stirs;
    }

    public MixingBowlRecipe getCurrentRecipe() {
        return MixingBowlRecipeManager.find(getIngredientStacks(), items.get(CONTAINER_SLOT), fluids);
    }

    public boolean canFillFromContainer(ItemStack stack) {
        FluidContainerResult result = getFillResult(stack);
        return result != null && canAcceptFluid(result.fluidId, result.amount);
    }

    public boolean canDrainToContainer(ItemStack stack) {
        return !getDrainResult(stack).isEmpty();
    }

    public boolean canSeparateEgg(ItemStack stack) {
        return stack != null
            && !stack.isEmpty()
            && stack.getItem() == Items.EGG
            && EDLItems.EGG_YOLK.isRegistered()
            && canAcceptFluid(EDLFluids.EGG_WHITE.getFluidId(), 250);
    }

    public ItemStack trySeparateEgg(ItemStack stack, boolean consume) {
        if (!canSeparateEgg(stack)) {
            return ItemStack.EMPTY;
        }
        if (consume) {
            stack.shrink(1);
        }
        addFluid(EDLFluids.EGG_WHITE.getFluidId(), 250);
        stirs = 0;
        markUpdated();
        return EDLItems.EGG_YOLK.stack(1);
    }

    public FluidContainerResult tryFillFromContainer(ItemStack stack) {
        FluidContainerResult result = getFillResult(stack);
        if (result == null || !canAcceptFluid(result.fluidId, result.amount)) {
            return null;
        }

        addFluid(result.fluidId, result.amount);
        stirs = 0;
        markUpdated();
        return result;
    }

    public ItemStack tryDrainToContainer(ItemStack emptyContainer) {
        String id = getDrainFluidId(emptyContainer);
        ItemStack filled = getDrainResult(emptyContainer, id);
        if (filled.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int amount = emptyContainer.getItem() == Items.BUCKET ? BUCKET_VOLUME : BottleFluidRecipeManager.BOTTLE_VOLUME;
        removeFluid(id, amount);
        stirs = 0;
        markUpdated();
        return filled;
    }

    public boolean handleFluidContainer(EntityPlayer player, ItemStack held) {
        if (held.isEmpty()) {
            return false;
        }

        if (held.getItem() == Items.MILK_BUCKET) {
            return addLogicalFluid(player, held, MixingBowlRecipeManager.FLUID_MILK, 1000, new ItemStack(Items.BUCKET));
        }
        if (held.getItem() == Items.WATER_BUCKET) {
            return addLogicalFluid(player, held, MixingBowlRecipeManager.FLUID_WATER, 1000, new ItemStack(Items.BUCKET));
        }

        EDLFluids.FluidDefinition definition = EDLFluids.findByBucket(held.getItem());
        if (definition != null && definition.getFluid() != null) {
            return addLogicalFluid(player, held, definition.getFluidId(), 1000, new ItemStack(Items.BUCKET));
        }

        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByBottle(held);
        if (bottleRecipe != null && bottleRecipe.getFluid().getFluid() != null) {
            String logicalId = EDLFluids.logicalIdForFluid(bottleRecipe.getFluid().getFluid());
            if (!logicalId.isEmpty()) {
                return addLogicalFluid(player, held, logicalId, bottleRecipe.getFluid().amount, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return false;
    }

    public boolean handleEgg(EntityPlayer player, ItemStack held) {
        ItemStack yolk = trySeparateEgg(held, !player.capabilities.isCreativeMode);
        if (yolk.isEmpty()) {
            return false;
        }
        givePlayerItem(player, yolk);
        return true;
    }

    public boolean stir(EntityPlayer player, ItemStack utensil) {
        MixingBowlRecipe recipe = getCurrentRecipe();
        if (!MixingBowlRecipeManager.matchesUtensil(utensil, recipe)) {
            return false;
        }

        if (stirs + 1 < recipe.getStirs()) {
            stirs++;
            spawnStirEffects(utensil);
            markUpdated();
            return true;
        }

        ItemStack output = recipe.getOutput();
        givePlayerItem(player, output);
        consumeRecipe(recipe, player);
        if (!player.capabilities.isCreativeMode && utensil.isItemStackDamageable()) {
            utensil.damageItem(1, player);
        }
        stirs = 0;
        spawnStirEffects(utensil);
        EDLAdvancements.trigger(EDLAdvancements.MIXING_BOWL, player);
        markUpdated();
        return true;
    }

    public void clear() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        fluids.clear();
        stirs = 0;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, items);
        NBTTagList fluidList = new NBTTagList();
        for (Map.Entry<String, Integer> entry : fluids.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isEmpty() || entry.getValue() == null || entry.getValue() <= 0) {
                continue;
            }
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidTag.setString("Id", entry.getKey());
            fluidTag.setInteger("Amount", entry.getValue());
            fluidList.appendTag(fluidTag);
        }
        compound.setTag("Fluids", fluidList);
        compound.setInteger("Stirs", stirs);
        compound.setInteger("Style", style);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ItemStackHelper.loadAllItems(compound, items);
        fluids.clear();
        if (compound.hasKey("Fluids", Constants.NBT.TAG_LIST)) {
            NBTTagList fluidList = compound.getTagList("Fluids", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < fluidList.tagCount(); i++) {
                NBTTagCompound fluidTag = fluidList.getCompoundTagAt(i);
                addFluid(fluidTag.getString("Id"), fluidTag.getInteger("Amount"));
            }
        } else if (compound.hasKey("FluidId", Constants.NBT.TAG_STRING)) {
            addFluid(compound.getString("FluidId"), compound.getInteger("FluidAmount"));
        }
        stirs = compound.getInteger("Stirs");
        style = Math.max(0, Math.min(STYLE_COUNT - 1, compound.getInteger("Style")));
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

    private boolean addLogicalFluid(EntityPlayer player, ItemStack held, String id, int amount, ItemStack emptyContainer) {
        if (id == null || id.isEmpty() || amount <= 0 || getFluidAmount() + amount > TANK_CAPACITY) {
            return false;
        }

        if (!player.capabilities.isCreativeMode) {
            held.shrink(1);
            givePlayerItem(player, emptyContainer);
        }
        addFluid(id, amount);
        stirs = 0;
        markUpdated();
        return true;
    }

    private FluidContainerResult getFillResult(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        if (stack.getItem() == Items.MILK_BUCKET) {
            return new FluidContainerResult(MixingBowlRecipeManager.FLUID_MILK, BUCKET_VOLUME, new ItemStack(Items.BUCKET));
        }
        if (stack.getItem() == Items.WATER_BUCKET) {
            return new FluidContainerResult(MixingBowlRecipeManager.FLUID_WATER, BUCKET_VOLUME, new ItemStack(Items.BUCKET));
        }

        EDLFluids.FluidDefinition definition = EDLFluids.findByBucket(stack.getItem());
        if (definition != null && definition.getFluid() != null) {
            return new FluidContainerResult(definition.getFluidId(), BUCKET_VOLUME, new ItemStack(Items.BUCKET));
        }

        BottleFluidRecipe bottleRecipe = BottleFluidRecipeManager.findByBottle(stack);
        if (bottleRecipe != null && bottleRecipe.getFluid().getFluid() != null) {
            String logicalId = EDLFluids.logicalIdForFluid(bottleRecipe.getFluid().getFluid());
            if (!logicalId.isEmpty()) {
                return new FluidContainerResult(logicalId, bottleRecipe.getFluid().amount, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return null;
    }

    private boolean canAcceptFluid(String id, int amount) {
        return id != null
            && !id.isEmpty()
            && amount > 0
            && getFluidAmount() + amount <= TANK_CAPACITY;
    }

    private ItemStack getDrainResult(ItemStack emptyContainer) {
        return getDrainResult(emptyContainer, getDrainFluidId(emptyContainer));
    }

    private ItemStack getDrainResult(ItemStack emptyContainer, String id) {
        if (emptyContainer.isEmpty() || id.isEmpty() || getFluidAmount(id) <= 0) {
            return ItemStack.EMPTY;
        }
        int available = getFluidAmount(id);
        if (emptyContainer.getItem() == Items.BUCKET && available >= BUCKET_VOLUME) {
            if (MixingBowlRecipeManager.FLUID_WATER.equals(id)) {
                return new ItemStack(Items.WATER_BUCKET);
            }
            if (MixingBowlRecipeManager.FLUID_MILK.equals(id)) {
                return new ItemStack(Items.MILK_BUCKET);
            }
            EDLFluids.FluidDefinition definition = findFluidDefinition(id);
            return definition == null || definition.getBucket() == null ? ItemStack.EMPTY : definition.bucketStack();
        }
        if (emptyContainer.getItem() == Items.GLASS_BOTTLE && available >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
            net.minecraftforge.fluids.FluidStack fluidStack = EDLFluids.stackForLogical(id, BottleFluidRecipeManager.BOTTLE_VOLUME);
            if (fluidStack == null || fluidStack.getFluid() == null) {
                return ItemStack.EMPTY;
            }
            BottleFluidRecipe recipe = BottleFluidRecipeManager.findByFluid(fluidStack.getFluid());
            return recipe == null ? ItemStack.EMPTY : recipe.getBottle();
        }
        return ItemStack.EMPTY;
    }

    private String getDrainFluidId(ItemStack emptyContainer) {
        if (emptyContainer.isEmpty()) {
            return "";
        }
        for (Map.Entry<String, Integer> entry : fluids.entrySet()) {
            if (entry.getValue() == null || entry.getValue() <= 0) {
                continue;
            }
            if (emptyContainer.getItem() == Items.BUCKET && entry.getValue() >= BUCKET_VOLUME) {
                return entry.getKey();
            }
            if (emptyContainer.getItem() == Items.GLASS_BOTTLE && entry.getValue() >= BottleFluidRecipeManager.BOTTLE_VOLUME) {
                return entry.getKey();
            }
        }
        return "";
    }

    private EDLFluids.FluidDefinition findFluidDefinition(String id) {
        for (EDLFluids.FluidDefinition definition : EDLFluids.getDefinitions()) {
            if (id.equals(definition.getFluidId())) {
                return definition;
            }
        }
        return null;
    }

    private void consumeRecipe(MixingBowlRecipe recipe, EntityPlayer player) {
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                ItemStack container = stack.getItem().getContainerItem(stack);
                stack.shrink(1);
                if (!container.isEmpty()) {
                    givePlayerItem(player, container);
                }
                if (stack.isEmpty()) {
                    items.set(i, ItemStack.EMPTY);
                }
            }
        }

        ItemStack requiredContainer = recipe.getContainer();
        if (!requiredContainer.isEmpty()) {
            items.get(CONTAINER_SLOT).shrink(requiredContainer.getCount());
            if (items.get(CONTAINER_SLOT).isEmpty()) {
                items.set(CONTAINER_SLOT, ItemStack.EMPTY);
            }
        }

        for (MixingBowlFluidIngredient fluid : recipe.getFluids()) {
            removeFluid(fluid.getFluidId(), fluid.getAmount());
        }
    }

    private void addFluid(String id, int amount) {
        if (id == null || id.isEmpty() || amount <= 0) {
            return;
        }
        fluids.put(id, getFluidAmount(id) + amount);
    }

    private void removeFluid(String id, int amount) {
        if (id == null || id.isEmpty() || amount <= 0) {
            return;
        }
        int remaining = getFluidAmount(id) - amount;
        if (remaining <= 0) {
            fluids.remove(id);
        } else {
            fluids.put(id, remaining);
        }
    }

    private ItemStack[] getIngredientStacks() {
        ItemStack[] stacks = new ItemStack[INGREDIENT_SLOTS];
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            stacks[i] = items.get(i);
        }
        return stacks;
    }

    private void spawnStirEffects(ItemStack utensil) {
        if (world == null) {
            return;
        }
        world.playSound(null, pos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, 0.8F, 1.0F);
        if (!utensil.isEmpty()) {
            world.spawnParticle(
                EnumParticleTypes.ITEM_CRACK,
                pos.getX() + 0.5D,
                pos.getY() + 0.45D,
                pos.getZ() + 0.5D,
                0.0D,
                0.05D,
                0.0D,
                net.minecraft.item.Item.getIdFromItem(utensil.getItem()),
                utensil.getMetadata()
            );
        }
    }

    private void givePlayerItem(EntityPlayer player, ItemStack stack) {
        if (!stack.isEmpty() && !player.inventory.addItemStackToInventory(stack.copy())) {
            player.dropItem(stack.copy(), false);
        }
    }

    public void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    @Override
    public int getStyleCount() {
        return STYLE_COUNT;
    }

    @Override
    public int getStyle() {
        return style;
    }

    @Override
    public void setStyle(int style) {
        this.style = Math.max(0, Math.min(STYLE_COUNT - 1, style));
        markUpdated();
    }

    public static final class FluidContainerResult {
        private final String fluidId;
        private final int amount;
        private final ItemStack emptyContainer;

        private FluidContainerResult(String fluidId, int amount, ItemStack emptyContainer) {
            this.fluidId = fluidId;
            this.amount = amount;
            this.emptyContainer = emptyContainer;
        }

        public String getFluidId() {
            return fluidId;
        }

        public int getAmount() {
            return amount;
        }

        public ItemStack getEmptyContainer() {
            return emptyContainer.copy();
        }
    }
}
