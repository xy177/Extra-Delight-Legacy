package xy177.extradelightlegacy.common.registry;

import com.wdcftgg.farmersdelightlegacy.api.knife.IKnifeItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.content.EDLContentEntry;
import xy177.extradelightlegacy.common.content.EDLContentRegistry;
import xy177.extradelightlegacy.common.module.EDLModule;

public final class EDLEnchantments {
    private static final EDLContentEntry BUTCHER_ENTRY =
        EDLContentRegistry.define(EDLModule.MEAT, "butcher", "Butcher Enchantment");

    public static Enchantment BUTCHER;

    private EDLEnchantments() {
    }

    public static void register(IForgeRegistry<Enchantment> registry) {
        if (!EDLConfig.isModuleEnabled(EDLModule.MEAT)
            || !EDLConfig.isEntryEnabled(BUTCHER_ENTRY)
            || !EDLContentRegistry.areDependenciesEnabled(BUTCHER_ENTRY)) {
            return;
        }

        BUTCHER = new EnchantmentButcher();
        registry.register(BUTCHER);
        BUTCHER_ENTRY.markRegistered();
    }

    public static boolean hasButcher(ItemStack stack) {
        return BUTCHER != null
            && !stack.isEmpty()
            && net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(BUTCHER, stack) > 0;
    }

    public static int getButcherLevel(ItemStack stack) {
        return BUTCHER == null || stack.isEmpty()
            ? 0
            : net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(BUTCHER, stack);
    }

    public static boolean isKnife(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        if (stack.getItem() instanceof IKnifeItem) {
            return true;
        }

        for (ItemStack knife : OreDictionary.getOres("toolKnife")) {
            if (OreDictionary.itemMatches(knife, stack, false)) {
                return true;
            }
        }

        ResourceLocation name = stack.getItem().getRegistryName();
        return name != null
            && "farmersdelight".equals(name.getResourceDomain())
            && name.getResourcePath().endsWith("_knife");
    }

    private static final class EnchantmentButcher extends Enchantment {
        private EnchantmentButcher() {
            super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
            setRegistryName(ExtraDelightLegacy.MODID, "butcher");
            setName(ExtraDelightLegacy.MODID + ".butcher");
        }

        @Override
        public int getMinEnchantability(int enchantmentLevel) {
            return 12 + (enchantmentLevel - 1) * 14;
        }

        @Override
        public int getMaxEnchantability(int enchantmentLevel) {
            return getMinEnchantability(enchantmentLevel) + 24;
        }

        @Override
        public int getMaxLevel() {
            return 3;
        }

        @Override
        public boolean canApply(ItemStack stack) {
            return isKnife(stack);
        }

        @Override
        public boolean canApplyAtEnchantingTable(ItemStack stack) {
            return canApply(stack);
        }

        @Override
        public boolean isAllowedOnBooks() {
            return true;
        }
    }
}
