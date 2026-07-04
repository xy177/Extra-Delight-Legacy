package xy177.extradelightlegacy.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public class ItemDynamicJam extends ItemContainerFood {
    public enum Flavor {
        APPLE("apple", "apple"),
        CARROT("carrot", "carrot"),
        CHORUS_FRUIT("chorus_fruit", "chorus"),
        GLOW_BERRIES("glow_berries", "glow_berry"),
        GOLDEN_APPLE("golden_apple", "golden_apple"),
        GRAPEFRUIT("grapefruit", "grapefruit"),
        LEMON("lemon", "lemon"),
        LIME("lime", "lime"),
        MELON("melon", "melon"),
        MINT("mint", "mint"),
        ORANGE("orange", "orange"),
        SWEET_BERRIES("sweet_berries", "sweet_berry");

        private final String id;
        private final String modelSuffix;

        Flavor(String id, String modelSuffix) {
            this.id = id;
            this.modelSuffix = modelSuffix;
        }

        public String getId() {
            return id;
        }

        public String getModelName() {
            return "dynamic_jam_" + modelSuffix;
        }

        public int getMeta() {
            return ordinal();
        }

        public static Flavor byMeta(int meta) {
            Flavor[] values = values();
            if (meta < 0 || meta >= values.length) {
                return APPLE;
            }
            return values[meta];
        }
    }

    public ItemDynamicJam() {
        super(1, 0.1F, Items.GLASS_BOTTLE);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + ExtraDelightLegacy.MODID + ".dynamic_jam";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Flavor flavor = Flavor.byMeta(stack.getMetadata());
        String key = ExtraDelightLegacy.MODID + ".jam." + flavor.getId();
        return I18n.canTranslate(key) ? I18n.translateToLocal(key) : super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Flavor flavor : Flavor.values()) {
                items.add(stack(flavor, 1));
            }
        }
    }

    public static ItemStack stack(Flavor flavor, int count) {
        return new ItemStack(xy177.extradelightlegacy.common.registry.EDLItems.DYNAMIC_JAM.getItem(), count, flavor.getMeta());
    }
}
