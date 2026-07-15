package xy177.extradelightlegacy.common.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xy177.extradelightlegacy.common.crafting.ButcherDropRegistry;

@ZenRegister
@ZenClass("mods.extradelightlegacy.ButcherDrops")
public final class ZenButcherDrops {
    private ZenButcherDrops() {
    }

    @ZenMethod
    public static void add(String id, String entityId, IItemStack rawOutput, int minCount, int maxCount, float chance) {
        addWithCooked(id, entityId, rawOutput, null, minCount, maxCount, chance);
    }

    @ZenMethod
    public static void addWithCooked(String id, String entityId, IItemStack rawOutput, IItemStack cookedOutput,
                                     int minCount, int maxCount, float chance) {
        String dropId = ZenRecipeUtil.id(id, "butcher drop");
        ResourceLocation entity = ZenRecipeUtil.entityId(entityId, "Butcher drop");
        ItemStack raw = ZenRecipeUtil.item(rawOutput, false, "Butcher raw output");
        ItemStack cooked = ZenRecipeUtil.item(cookedOutput, true, "Butcher cooked output");
        if (dropId == null || entity == null || raw == null || cooked == null || minCount < 0 || maxCount < minCount
            || chance < 0.0F || chance > 1.0F) {
            return;
        }
        CraftTweakerActions.add("add Extra Delight Legacy butcher drop " + dropId,
            () -> ButcherDropRegistry.register(dropId, entity, raw, cooked, minCount, maxCount, chance));
    }

    @ZenMethod
    public static void remove(String id) {
        String dropId = ZenRecipeUtil.id(id, "butcher drop");
        if (dropId != null) {
            CraftTweakerActions.remove("butcher drop " + dropId, () -> ButcherDropRegistry.remove(dropId));
        }
    }

    @ZenMethod
    public static void removeByEntity(String entityId) {
        ResourceLocation entity = ZenRecipeUtil.entityId(entityId, "Butcher drop removal");
        if (entity != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy butcher drops by entity",
                () -> ButcherDropRegistry.removeByEntity(entity));
        }
    }

    @ZenMethod
    public static void removeByOutput(IItemStack output) {
        ItemStack result = ZenRecipeUtil.item(output, false, "Butcher output");
        if (result != null) {
            CraftTweakerActions.removeMany("Remove Extra Delight Legacy butcher drops by output",
                () -> ButcherDropRegistry.removeByOutput(result));
        }
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerActions.removeMany("Remove all Extra Delight Legacy butcher drops", ButcherDropRegistry::removeAll);
    }
}
