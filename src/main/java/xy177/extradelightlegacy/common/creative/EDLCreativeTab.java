package xy177.extradelightlegacy.common.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.content.EDLContentRegistry;
import xy177.extradelightlegacy.common.module.EDLModule;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.registry.EDLItems;

public final class EDLCreativeTab extends CreativeTabs {
    private final EDLModule module;

    EDLCreativeTab(EDLModule module) {
        super(ExtraDelightLegacy.MODID + "." + module.getId());
        this.module = module;
    }

    @Override
    public ItemStack getTabIconItem() {
        ItemStack icon = getConfiguredIcon();
        if (!icon.isEmpty()) {
            return icon;
        }

        ItemStack stack = EDLContentRegistry.getFirstCreativeStack(module);
        return stack.isEmpty() ? module.createFallbackIcon() : stack;
    }

    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        EDLContentRegistry.addCreativeStacks(module, items);
    }

    private ItemStack getConfiguredIcon() {
        switch (module) {
            case CROPS:
                return EDLItems.CUCUMBER.stack(1);
            case CORE:
                return EDLItems.SLICED_APPLE.stack(1);
            case ORCHARD:
                return EDLBlocks.APPLE_SAPLING.stack(1);
            case MEAT:
                return EDLItems.BEEF_RIBS.stack(1);
            case KITCHEN:
                return EDLItems.DIAMOND_SPOON.stack(1);
            case BAKING:
                return EDLItems.SUGAR_COOKIE_CREEPER.stack(1);
            case PICKLING:
                return EDLBlocks.GHERKINS_BLOCK.stack(1);
            case SWEETS:
                return EDLItems.CARAMEL_CUSTARD.stack(1);
            case MEALS:
                return EDLBlocks.CURRY_BLOCK.stack(1);
            case KITCHEN_DECOR:
                return EDLBlocks.SINK_CABINET_BLOCKS.get(0).stack(1);
            case HOME_DECOR:
                return EDLBlocks.GINGHAM_BLOCKS.get(3).stack(1);
            default:
                return ItemStack.EMPTY;
        }
    }
}
