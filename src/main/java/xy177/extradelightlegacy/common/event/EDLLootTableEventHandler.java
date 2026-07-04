package xy177.extradelightlegacy.common.event;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.ArrayList;
import java.util.List;

public class EDLLootTableEventHandler {
    private static final LootCondition[] NO_CONDITIONS = new LootCondition[0];
    private static final LootFunction[] NO_FUNCTIONS = new LootFunction[0];

    private static final String[] TARGET_TABLES = {
        "minecraft:chests/simple_dungeon",
        "minecraft:chests/abandoned_mineshaft",
        "minecraft:chests/desert_pyramid",
        "minecraft:chests/jungle_temple",
        "minecraft:chests/stronghold_corridor"
    };

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation table = event.getName();

        if (EDLItems.EASTER_EGG.isRegistered() && isTargetTable(table)) {
            addEasterEggPool(event);
        }

        String id = table.toString();
        if ("minecraft:chests/simple_dungeon".equals(id)) {
            addDungeonFoodPool(event);
            addSingleItemPool(event, "pestle_amethyst", 0.1F, 0, 1, EDLItems.PESTLE_AMETHYST);
        } else if ("minecraft:chests/igloo_chest".equals(id)) {
            addMealsPool(event, "igloo_meals", 0.5F);
        } else if ("minecraft:chests/woodland_mansion".equals(id)) {
            addMealsPool(event, "mansion_meals", 0.5F);
            addSingleItemPool(event, "curry_powder_mansion", 0.75F, 1, 4, EDLItems.CURRY_POWDER);
        } else if ("minecraft:chests/desert_pyramid".equals(id)) {
            addSingleItemPool(event, "curry_powder_desert_pyramid", 0.5F, 1, 4, EDLItems.CURRY_POWDER);
            addSingleItemPool(event, "cactus_juice_desert_pyramid", 1.0F, 0, 5, EDLItems.CACTUS_JUICE);
        }
    }

    private static void addEasterEggPool(LootTableLoadEvent event) {
        LootPool pool = new LootPool(
            new net.minecraft.world.storage.loot.LootEntry[]{
                new LootEntryItem(
                    EDLItems.EASTER_EGG.getItem(),
                    1,
                    0,
                    new LootFunction[0],
                    new LootCondition[0],
                    ExtraDelightLegacy.MODID + ":easter_egg"
                ),
                new LootEntryItem(
                    Items.GOLDEN_APPLE,
                    3,
                    0,
                    new LootFunction[0],
                    new LootCondition[0],
                    ExtraDelightLegacy.MODID + ":easter_egg_gold_apple"
                )
            },
            new LootCondition[0],
            new RandomValueRange(0, 1),
            new RandomValueRange(0, 0),
            ExtraDelightLegacy.MODID + ":easter_egg_bonus"
        );
        event.getTable().addPool(pool);
    }

    private static void addDungeonFoodPool(LootTableLoadEvent event) {
        List<LootEntry> entries = new ArrayList<>();
        addWeighted(entries, EDLItems.BAD_FOOD, 20, count(1, 64));
        addMealEntries(entries, 1);
        addPool(event, "dungeon_rot", 0.5F, 0, 1, entries);
    }

    private static void addMealsPool(LootTableLoadEvent event, String name, float chance) {
        List<LootEntry> entries = new ArrayList<>();
        addMealEntries(entries, 1);
        addPool(event, name, chance, 1, 15, entries);
    }

    private static void addMealEntries(List<LootEntry> entries, int weightScale) {
        int common = 100 * weightScale;
        int uncommon = 50 * weightScale;
        int rare = 10 * weightScale;
        int legendary = weightScale;

        addWeighted(entries, EDLItems.APPLE_SAUCE, common);
        addWeighted(entries, EDLItems.BOILED_EGG, common);
        addWeighted(entries, EDLItems.CARROT_SALAD, common);
        addWeighted(entries, EDLItems.FRENCH_FRIES, common);
        addWeighted(entries, EDLItems.GLOW_BERRY_COOKIE, common);
        addWeighted(entries, EDLItems.GLOW_BERRY_JUICE, common);
        addWeighted(entries, EDLItems.JERKY, common);
        addWeighted(entries, EDLItems.POTATO_CHIPS, common);
        addWeighted(entries, EDLItems.PUMPKIN_COOKIE, common);
        addWeighted(entries, EDLItems.RICEBALL, common);
        addWeighted(entries, EDLItems.ROASTED_APPLE, common);
        addWeighted(entries, EDLItems.ROASTED_CARROT, common);
        addWeighted(entries, EDLItems.SCRAMBLED_EGGS, common);
        addWeighted(entries, EDLItems.SEAWEED_CRISPS, common);
        addWeighted(entries, EDLItems.SEAWEED_SALAD, common);
        addWeighted(entries, EDLItems.SUGAR_COOKIE, common);
        addWeighted(entries, EDLItems.BBQ_SAUCE, common);
        addWeighted(entries, EDLBlocks.BREADCRUMBS.getItemBlock(), common, "breadcrumbs");
        addWeighted(entries, EDLItems.BREAD_SLICE, common);
        addWeighted(entries, EDLItems.BUTTER, common);
        addWeighted(entries, EDLItems.FLOUR, common);
        addWeighted(entries, EDLItems.GRATED_CARROT, common);
        addWeighted(entries, EDLItems.GRATED_POTATO, common);
        addWeighted(entries, EDLItems.KETCHUP, common);
        addWeighted(entries, EDLItems.MAYO, common);
        addWeighted(entries, EDLItems.POTATO_STICKS, common);
        addWeighted(entries, EDLItems.SLICED_APPLE, common);
        addWeighted(entries, EDLItems.SLICED_ONION, common);
        addWeighted(entries, EDLItems.SLICED_POTATO, common);
        addWeighted(entries, EDLItems.SLICED_TOMATO, common);
        addWeighted(entries, EDLItems.CACTUS, common);
        addWeighted(entries, EDLItems.COOKED_PASTA, common);
        addWeighted(entries, EDLItems.COOKING_OIL, common);
        addWeighted(entries, EDLItems.HASHBROWNS, common);

        addWeighted(entries, EDLItems.SWEET_BERRY_CUSTARD, uncommon);
        addWeighted(entries, EDLItems.SWEET_BERRY_JUICE, uncommon);
        addWeighted(entries, EDLItems.SWEET_BERRY_PIE_SLICE, uncommon);
        addWeighted(entries, EDLItems.SWEET_BERRY_POPSICLE, uncommon);
        addWeighted(entries, EDLItems.TOMATO_JUICE, uncommon);
        addWeighted(entries, EDLItems.TOMATO_SOUP, uncommon);
        addWeighted(entries, EDLItems.CACTUS_EGGS, uncommon);
        addWeighted(entries, EDLItems.CACTUS_SALAD, uncommon);
        addWeighted(entries, EDLItems.CACTUS_SOUP, uncommon);
        addWeighted(entries, EDLItems.BBQ_RIBS, uncommon);
        addWeighted(entries, EDLItems.BUTTERED_PASTA, uncommon);
        addWeighted(entries, EDLItems.CARROT_SOUP, uncommon);
        addWeighted(entries, EDLItems.CHEESE_SANDWICH, uncommon);
        addWeighted(entries, EDLItems.CHICKEN_STEW, uncommon);
        addWeighted(entries, EDLItems.CHOCOLATE_CHEESECAKE_SLICE, uncommon);
        addWeighted(entries, EDLItems.CHOCOLATE_CUSTARD, uncommon);
        addWeighted(entries, EDLItems.EGG_IN_THE_BASKET, uncommon);
        addWeighted(entries, EDLItems.EGG_SALAD, uncommon);
        addWeighted(entries, EDLItems.EGG_SALAD_SANDWICH, uncommon);
        addWeighted(entries, EDLItems.FRIED_CHICKEN, uncommon);
        addWeighted(entries, EDLItems.FRIED_FISH, uncommon);
        addWeighted(entries, EDLItems.FRIED_MUSHROOMS, uncommon);
        addWeighted(entries, EDLItems.FUDGE_POPSICLE, uncommon);
        addWeighted(entries, EDLItems.FURIKAKE_RICE, uncommon);
        addWeighted(entries, EDLItems.GLAZED_CARROT, uncommon);
        addWeighted(entries, EDLItems.GLOW_BERRY_CHEESECAKE_SLICE, uncommon);
        addWeighted(entries, EDLItems.GLOW_BERRY_PIE_SLICE, uncommon);
        addWeighted(entries, EDLItems.GLOW_BERRY_POPSICLE, uncommon);
        addWeighted(entries, EDLItems.GRILLED_CHEESE, uncommon);
        addWeighted(entries, EDLItems.HASH, uncommon);
        addWeighted(entries, EDLItems.HONEY_CHEESECAKE_SLICE, uncommon);
        addWeighted(entries, EDLItems.HONEY_CUSTARD, uncommon);
        addWeighted(entries, EDLItems.HONEY_POPSICLE, uncommon);
        addWeighted(entries, EDLItems.MACARONI_CHEESE, uncommon);
        addWeighted(entries, EDLItems.MASHED_POTATO_GRAVY, uncommon);
        addWeighted(entries, EDLItems.MEAT_PIE_SLICE, uncommon);
        addWeighted(entries, EDLItems.OMELETTE, uncommon);
        addWeighted(entries, EDLItems.PASTA_TOMATO, uncommon);
        addWeighted(entries, EDLItems.POTATO_SOUP, uncommon);
        addWeighted(entries, EDLItems.PUMPKIN_CHEESECAKE_SLICE, uncommon);
        addWeighted(entries, EDLItems.PUMPKIN_CUSTARD, uncommon);
        addWeighted(entries, EDLItems.QUICHE_SLICE, uncommon);
        addWeighted(entries, EDLItems.RICEBALL_FILLED, uncommon);
        addWeighted(entries, EDLItems.SALAD, uncommon);
        addWeighted(entries, EDLItems.SAUSAGE_ROLL, uncommon);
        addWeighted(entries, EDLItems.STUFFED_MUSHROOMS, uncommon);
        addWeighted(entries, EDLItems.ALFREDO_SAUCE, uncommon);
        addWeighted(entries, EDLItems.CHEESE, uncommon);
        addWeighted(entries, EDLItems.CROUTONS, uncommon);
        addWeighted(entries, EDLItems.EGG_MIX, uncommon);
        addWeighted(entries, EDLItems.MACARONI, uncommon);
        addWeighted(entries, EDLItems.TOAST, uncommon);
        addWeighted(entries, EDLItems.VINEGAR, uncommon);
        addWeighted(entries, EDLItems.WHIPPED_CREAM, uncommon);
        addWeighted(entries, EDLItems.COOKED_CACTUS, uncommon);

        addWeighted(entries, EDLItems.BACON_CHEESEBURGER, rare);
        addWeighted(entries, EDLItems.BACON_EGG_CHEESE_SANDWICH, rare);
        addWeighted(entries, EDLItems.BACON_EGG_SANDWICH, rare);
        addWeighted(entries, EDLItems.BEEF_STEW_RICE, rare);
        addWeighted(entries, EDLItems.BEEF_WELLINGTON, rare);
        addWeighted(entries, EDLItems.CHEESEBURGER, rare);
        addWeighted(entries, EDLItems.CHEESECAKE_SLICE, rare);
        addWeighted(entries, EDLItems.CHICKEN_ALFREDO, rare);
        addWeighted(entries, EDLItems.CHICKEN_FRIED_STEAK, rare);
        addWeighted(entries, EDLItems.CHICKEN_PARM, rare);
        addWeighted(entries, EDLItems.CHICKEN_STEW_RICE, rare);
        addWeighted(entries, EDLItems.CURRY, rare);
        addWeighted(entries, EDLItems.FISH_CHIPS, rare);
        addWeighted(entries, EDLItems.FISH_CAKES, rare);
        addWeighted(entries, EDLItems.FISH_SALAD, uncommon);
        addWeighted(entries, EDLItems.FISH_SALAD_SANDWICH, uncommon);
        addWeighted(entries, EDLItems.FISH_SOUP, uncommon);
        addWeighted(entries, EDLItems.FISH_STEW_RICE, rare);
        addWeighted(entries, EDLItems.FRIED_BRAINS, rare);
        addWeighted(entries, EDLItems.HAGGIS, rare);
        addWeighted(entries, EDLItems.HOTDISH, rare);
        addWeighted(entries, EDLItems.LAMB_STEW, rare);
        addWeighted(entries, EDLItems.LAMB_STEW_RICE, rare);
        addWeighted(entries, EDLItems.LASAGNA, rare);
        addWeighted(entries, EDLItems.LIVER_ONIONS, rare);
        addWeighted(entries, EDLItems.MEATLOAF, rare);
        addWeighted(entries, EDLItems.MEATLOAF_SANDWICH, rare);
        addWeighted(entries, EDLItems.MUSHROOM_BURGER, rare);
        addWeighted(entries, EDLItems.MUSHROOM_RISOTTO, rare);
        addWeighted(entries, EDLItems.PASTA_ALFREDO, rare);
        addWeighted(entries, EDLItems.PORK_STEW, rare);
        addWeighted(entries, EDLItems.PORK_STEW_RICE, rare);
        addWeighted(entries, EDLItems.PORK_TENDERLOIN, rare);
        addWeighted(entries, EDLItems.PORK_TENDERLOIN_SANDWICH, rare);
        addWeighted(entries, EDLItems.POTROAST, rare);
        addWeighted(entries, EDLItems.PULLED_PORK_SANDWICH, rare);
        addWeighted(entries, EDLItems.RABBIT_STEW_RICE, rare);
        addWeighted(entries, EDLItems.RACK_LAMB, rare);
        addWeighted(entries, EDLItems.SALISBURY_STEAK, rare);
        addWeighted(entries, EDLItems.SOS, rare);
        addWeighted(entries, EDLItems.STIRFRY, rare);
        addWeighted(entries, EDLItems.STUFFED_HEART, rare);
        addWeighted(entries, EDLItems.STUFFED_CACTUS, rare);
        addWeighted(entries, EDLItems.AGAR_AGAR, rare);
        addWeighted(entries, EDLItems.FISH_FLAKES, rare);
        addWeighted(entries, EDLItems.GRAVY, rare);
        addWeighted(entries, EDLItems.OMELETTE_MIX, rare);
        addWeighted(entries, EDLItems.SUNFLOWER_SEEDS, rare);
        addWeighted(entries, EDLItems.YEAST, rare);

        addWeighted(entries, EDLItems.CURRY_RICE, legendary);
        addWeighted(entries, EDLItems.GOLDEN_APPLE_JAM, legendary);
        addWeighted(entries, EDLItems.OXTAIL_SOUP, legendary);

        addWeighted(entries, EDLBlocks.BBQ_RIBS_BLOCK.getItemBlock(), rare, "bbq_ribs_block");
        addWeighted(entries, EDLBlocks.BEEF_STEW_BLOCK.getItemBlock(), rare, "beef_stew_block");
        addWeighted(entries, EDLBlocks.CHICKEN_STEW_BLOCK.getItemBlock(), rare, "chicken_stew_block");
        addWeighted(entries, EDLBlocks.FISH_STEW_BLOCK.getItemBlock(), rare, "fish_stew_block");
        addWeighted(entries, EDLBlocks.HASH_BLOCK.getItemBlock(), rare, "hash_block");
        addWeighted(entries, EDLBlocks.HOTDISH_BLOCK.getItemBlock(), rare, "hotdish_block");
        addWeighted(entries, EDLBlocks.LAMB_STEW_BLOCK.getItemBlock(), rare, "lamb_stew_block");
        addWeighted(entries, EDLBlocks.LASAGNA_BLOCK.getItemBlock(), rare, "lasagna_block");
        addWeighted(entries, EDLBlocks.MACARONI_CHEESE_BLOCK.getItemBlock(), rare, "macaroni_cheese_block");
        addWeighted(entries, EDLBlocks.MASHED_POTATO_GRAVY_BLOCK.getItemBlock(), rare, "mashed_potato_gravy_block");
        addWeighted(entries, EDLBlocks.PORK_STEW_BLOCK.getItemBlock(), rare, "pork_stew_block");
        addWeighted(entries, EDLBlocks.POTROAST_BLOCK.getItemBlock(), rare, "potroast_block");
        addWeighted(entries, EDLBlocks.PULLED_PORK_BLOCK.getItemBlock(), rare, "pulled_pork_block");
        addWeighted(entries, EDLBlocks.RABBIT_STEW_BLOCK.getItemBlock(), rare, "rabbit_stew_block");
        addWeighted(entries, EDLBlocks.RACK_LAMB_BLOCK.getItemBlock(), legendary, "rack_lamb_block");
        addWeighted(entries, EDLBlocks.SALAD_BLOCK.getItemBlock(), rare, "salad_block");
        addWeighted(entries, EDLBlocks.SALISBURY_STEAK_BLOCK.getItemBlock(), rare, "salisbury_steak_block");
        addWeighted(entries, EDLBlocks.STIRFRY_BLOCK.getItemBlock(), rare, "stirfry_block");
        addWeighted(entries, EDLBlocks.CURRY_BLOCK.getItemBlock(), legendary, "curry_block");
        addWeighted(entries, EDLBlocks.MEATLOAF_BLOCK.getItemBlock(), legendary, "meatloaf_block");
        addWeighted(entries, EDLBlocks.BEEF_WELLINGTON_BLOCK.getItemBlock(), legendary, "beef_wellington_block");
        addWeighted(entries, EDLBlocks.HAGGIS_BLOCK.getItemBlock(), legendary, "haggis_block");
    }

    private static void addSingleItemPool(LootTableLoadEvent event, String name, float chance, int min, int max,
                                          EDLItems.ItemDefinition definition) {
        List<LootEntry> entries = new ArrayList<>();
        addWeighted(entries, definition, 1, count(min, max));
        addPool(event, name, chance, min == 0 && max == 0 ? 0 : 1, 1, entries);
    }

    private static void addPool(LootTableLoadEvent event, String name, float chance, int minRolls, int maxRolls,
                                List<LootEntry> entries) {
        if (entries.isEmpty()) {
            return;
        }
        LootPool pool = new LootPool(
            entries.toArray(new LootEntry[0]),
            new LootCondition[]{new RandomChance(chance)},
            new RandomValueRange(minRolls, maxRolls),
            new RandomValueRange(0),
            ExtraDelightLegacy.MODID + ":" + name
        );
        event.getTable().addPool(pool);
    }

    private static void addWeighted(List<LootEntry> entries, EDLItems.ItemDefinition definition, int weight) {
        addWeighted(entries, definition, weight, NO_FUNCTIONS);
    }

    private static void addWeighted(List<LootEntry> entries, EDLItems.ItemDefinition definition, int weight,
                                    LootFunction... functions) {
        if (definition == null || !definition.isRegistered()) {
            return;
        }
        addWeighted(entries, definition.getItem(), weight, definition.getId(), functions);
    }

    private static void addWeighted(List<LootEntry> entries, Item item, int weight, String id) {
        addWeighted(entries, item, weight, id, NO_FUNCTIONS);
    }

    private static void addWeighted(List<LootEntry> entries, Item item, int weight, String id,
                                    LootFunction... functions) {
        if (item == null || item == Items.AIR) {
            return;
        }
        entries.add(new LootEntryItem(
            item,
            weight,
            0,
            functions == null ? NO_FUNCTIONS : functions,
            NO_CONDITIONS,
            ExtraDelightLegacy.MODID + ":" + id
        ));
    }

    private static LootFunction count(int min, int max) {
        return new SetCount(NO_CONDITIONS, new RandomValueRange(min, max));
    }

    private static boolean isTargetTable(ResourceLocation table) {
        String id = table.toString();
        for (String target : TARGET_TABLES) {
            if (target.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
