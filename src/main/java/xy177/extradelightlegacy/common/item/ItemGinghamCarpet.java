package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemGinghamCarpet extends ItemBlock {
    private static final String[] COLOR_NAMES = {
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
        "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };

    public ItemGinghamCarpet(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.extradelightlegacy.gingham_carpet_" + getColorName(stack.getMetadata());
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        for (int meta = 0; meta < COLOR_NAMES.length; meta++) {
            items.add(new ItemStack(this, 1, meta));
        }
    }

    public static String getColorName(int meta) {
        return COLOR_NAMES[meta & 15];
    }
}
