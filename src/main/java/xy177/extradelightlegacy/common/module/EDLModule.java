package xy177.extradelightlegacy.common.module;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public enum EDLModule {
    CROPS("crops", "农作", "Farming", true, () -> new ItemStack(Items.WHEAT)),
    CORE("core", "简餐", "Light Meals", true, () -> new ItemStack(Items.SUGAR)),
    ORCHARD("orchard", "果园", "Orchard", true, () -> new ItemStack(Items.APPLE)),
    MEAT("meat", "屠宰", "Butchery", true, () -> new ItemStack(Items.BEEF)),
    KITCHEN("kitchen", "厨具", "Kitchenware", true, () -> new ItemStack(Items.CAULDRON)),
    BAKING("baking", "烘焙", "Baking", true, () -> new ItemStack(Items.BREAD)),
    PICKLING("pickling", "腌制", "Pickling", true, () -> new ItemStack(Items.WATER_BUCKET)),
    SWEETS("sweets", "甜食", "Sweets", true, () -> new ItemStack(Items.CAKE)),
    MEALS("meals", "正餐", "Meals", true, () -> new ItemStack(Items.MUSHROOM_STEW)),
    KITCHEN_DECOR("kitchen_decor", "厨房", "Kitchen", true, () -> new ItemStack(Items.ITEM_FRAME)),
    HOME_DECOR("home_decor", "雅饰", "Decor", true, () -> new ItemStack(Items.PAINTING)),
    FLUIDS("fluids", "流体", "Fluids", false, () -> new ItemStack(Items.WATER_BUCKET)),
    WORLDGEN("worldgen", "世界生成", "Worldgen", false, () -> new ItemStack(Blocks.SAPLING)),
    COMPAT("compat", "兼容配方", "Compat Recipes", false, () -> new ItemStack(Items.COMPARATOR));

    private final String id;
    private final String chineseName;
    private final String englishName;
    private final boolean hasCreativeTab;
    private final Supplier<ItemStack> fallbackIconSupplier;

    EDLModule(String id, String chineseName, String englishName, boolean hasCreativeTab, Supplier<ItemStack> fallbackIconSupplier) {
        this.id = id;
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.hasCreativeTab = hasCreativeTab;
        this.fallbackIconSupplier = fallbackIconSupplier;
    }

    public String getId() {
        return id;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public boolean hasCreativeTab() {
        return hasCreativeTab;
    }

    public ItemStack createFallbackIcon() {
        ItemStack stack = fallbackIconSupplier.get();
        return stack.isEmpty() ? new ItemStack(Items.APPLE) : stack;
    }

    public String getConfigComment() {
        return "启用模块：" + chineseName + "\nEnable module: " + englishName;
    }
}
