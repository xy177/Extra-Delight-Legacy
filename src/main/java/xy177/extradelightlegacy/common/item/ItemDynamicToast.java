package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public class ItemDynamicToast extends AddonFoodItem {
    public enum Topping {
        APPLE_JAM("apple_jam", "apple_jam", ItemDynamicJam.Flavor.APPLE),
        CARROT_JAM("carrot_jam", "carrot_jam", ItemDynamicJam.Flavor.CARROT),
        CHORUS_FRUIT_JAM("chorus_fruit_jam", "chorus_fruit_jam", ItemDynamicJam.Flavor.CHORUS_FRUIT),
        GLOW_BERRIES_JAM("glow_berries_jam", "glow_berries_jam", ItemDynamicJam.Flavor.GLOW_BERRIES),
        GOLDEN_APPLE_JAM("golden_apple_jam", "golden_apple_jam", ItemDynamicJam.Flavor.GOLDEN_APPLE),
        GRAPEFRUIT_JAM("grapefruit_jam", "grapefruit_jam", ItemDynamicJam.Flavor.GRAPEFRUIT),
        LEMON_JAM("lemon_jam", "lemon_jam", ItemDynamicJam.Flavor.LEMON),
        LIME_JAM("lime_jam", "lime_jam", ItemDynamicJam.Flavor.LIME),
        MELON_JAM("melon_jam", "melon_jam", ItemDynamicJam.Flavor.MELON),
        MINT_JELLY("mint_jelly", "mint_jelly", ItemDynamicJam.Flavor.MINT),
        ORANGE_JAM("orange_jam", "orange_jam", ItemDynamicJam.Flavor.ORANGE),
        SWEET_BERRIES_JAM("sweet_berries_jam", "sweet_berries_jam", ItemDynamicJam.Flavor.SWEET_BERRIES),
        LEMON_CURD("lemon_curd", "lemon_curd", null),
        NUT_BUTTER("nut_butter", "nut_butter", null),
        HAZELNUT_SPREAD("hazelnut_spread", "hazelnut_spread", null),
        MARSHMALLOW_FLUFF("marshmallow_fluff", "marshmallow_fluff", null),
        YEAST_SPREAD("yeast_spread", "yeast_spread", null),
        BUTTER("butter", "butter", null);

        private final String id;
        private final String modelSuffix;
        private final ItemDynamicJam.Flavor jamFlavor;

        Topping(String id, String modelSuffix, ItemDynamicJam.Flavor jamFlavor) {
            this.id = id;
            this.modelSuffix = modelSuffix;
            this.jamFlavor = jamFlavor;
        }

        public String getId() {
            return id;
        }

        public String getModelName() {
            return "dynamic_toast_" + modelSuffix;
        }

        public int getMeta() {
            return ordinal();
        }

        public boolean isJam() {
            return jamFlavor != null;
        }

        public ItemDynamicJam.Flavor getJamFlavor() {
            return jamFlavor;
        }

        public static Topping byMeta(int meta) {
            Topping[] values = values();
            if (meta < 0 || meta >= values.length) {
                return APPLE_JAM;
            }
            return values[meta];
        }

        public static Topping byJamFlavor(ItemDynamicJam.Flavor flavor) {
            for (Topping topping : values()) {
                if (topping.jamFlavor == flavor) {
                    return topping;
                }
            }
            return APPLE_JAM;
        }
    }

    public ItemDynamicToast() {
        super(2, 0.6F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + ExtraDelightLegacy.MODID + ".dynamic_toast";
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Topping topping = Topping.byMeta(stack.getMetadata());
        String toppingKey = ExtraDelightLegacy.MODID + ".dynamic_toast.topping." + topping.getId();
        String toppingName = I18n.canTranslate(toppingKey) ? I18n.translateToLocal(toppingKey) : topping.getId();
        String nameKey = "item." + ExtraDelightLegacy.MODID + ".dynamic_toast.name";
        return I18n.canTranslate(nameKey) ? I18n.translateToLocalFormatted(nameKey, toppingName) : super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Topping topping : Topping.values()) {
                items.add(stack(topping, 1));
            }
        }
    }

    public static ItemStack stack(Topping topping, int count) {
        return new ItemStack(xy177.extradelightlegacy.common.registry.EDLItems.DYNAMIC_TOAST.getItem(), count, topping.getMeta());
    }
}
