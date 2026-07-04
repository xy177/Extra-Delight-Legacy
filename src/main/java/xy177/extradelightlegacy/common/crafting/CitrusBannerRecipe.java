package xy177.extradelightlegacy.common.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.DyeUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;
import xy177.extradelightlegacy.common.registry.EDLBannerPatterns;
import xy177.extradelightlegacy.common.registry.EDLItems;

public class CitrusBannerRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return !getBanner(inv).isEmpty() && getPatternCount(inv) == 1 && getDyeColor(inv) >= 0;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack banner = getBanner(inv);
        int color = getDyeColor(inv);
        if (banner.isEmpty() || color < 0 || EDLBannerPatterns.CITRUS_PITH == null || EDLBannerPatterns.CITRUS_FRUIT == null) {
            return ItemStack.EMPTY;
        }

        ItemStack result = banner.copy();
        result.setCount(1);
        NBTTagCompound blockEntity = result.getOrCreateSubCompound("BlockEntityTag");
        NBTTagList patterns;
        if (blockEntity.hasKey("Patterns", 9)) {
            patterns = blockEntity.getTagList("Patterns", 10);
        } else {
            patterns = new NBTTagList();
            blockEntity.setTag("Patterns", patterns);
        }

        appendPattern(patterns, EDLBannerPatterns.CITRUS_PITH, 15);
        appendPattern(patterns, EDLBannerPatterns.CITRUS_FRUIT, color);
        return result;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 3;
    }

    private static void appendPattern(NBTTagList patterns, BannerPattern pattern, int color) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Pattern", pattern.getHashname());
        tag.setInteger("Color", color);
        patterns.appendTag(tag);
    }

    private static ItemStack getBanner(InventoryCrafting inv) {
        ItemStack found = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.BANNER) {
                if (!found.isEmpty() || TileEntityBanner.getPatterns(stack) > 4) {
                    return ItemStack.EMPTY;
                }
                found = stack;
            }
        }
        return found;
    }

    private static int getPatternCount(InventoryCrafting inv) {
        int count = 0;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()
                && EDLItems.CITRUS_RIND_BANNER_ITEM.getItem() != null
                && stack.getItem() == EDLItems.CITRUS_RIND_BANNER_ITEM.getItem()) {
                count++;
            }
        }
        return count;
    }

    private static int getDyeColor(InventoryCrafting inv) {
        int found = -1;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem() == Items.BANNER
                || stack.getItem() == EDLItems.CITRUS_RIND_BANNER_ITEM.getItem()) {
                continue;
            }

            int color = DyeUtils.rawDyeDamageFromStack(stack);
            if (color < 0 || found >= 0) {
                return -1;
            }
            found = color;
        }
        return found;
    }
}
