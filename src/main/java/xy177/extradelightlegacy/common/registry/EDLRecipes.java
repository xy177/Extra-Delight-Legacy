package xy177.extradelightlegacy.common.registry;

import com.wdcftgg.farmersdelightlegacy.api.recipe.CookingPotRecipeApi;
import com.wdcftgg.farmersdelightlegacy.api.recipe.CuttingBoardRecipeApi;
import com.wdcftgg.farmersdelightlegacy.common.recipe.manager.CampfireCookingRecipeManager;
import com.wdcftgg.farmersdelightlegacy.common.registry.ModBlocks;
import com.wdcftgg.farmersdelightlegacy.common.registry.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.ButcherDropRegistry;
import xy177.extradelightlegacy.common.crafting.ChillerRecipeManager;
import xy177.extradelightlegacy.common.crafting.CitrusBannerRecipe;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipeManager;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipeManager;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipeManager;
import xy177.extradelightlegacy.common.crafting.EDLCustomRecipeLifecycle;
import xy177.extradelightlegacy.common.crafting.FeastServingRegistry;
import xy177.extradelightlegacy.common.crafting.JuicerRecipeManager;
import xy177.extradelightlegacy.common.crafting.MortarRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlFluidIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlIngredient;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipeManager;
import xy177.extradelightlegacy.common.crafting.OvenRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatStage;
import xy177.extradelightlegacy.common.item.ItemDynamicJam;
import xy177.extradelightlegacy.common.item.ItemDynamicToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class EDLRecipes {
    private static boolean initialized;

    private EDLRecipes() {
    }

    public static void register(IForgeRegistry<IRecipe> registry) {
        registerShapeless(registry, "chili_seeds", EDLItems.CHILI_SEEDS.stack(1), EDLItems.CHILI.getItem());
        registerShapeless(registry, "crisp_rice_cereal", EDLItems.CRISP_RICE_CEREAL.stack(1), EDLItems.CRISP_RICE.getItem(), Items.MILK_BUCKET, Items.BOWL);
        registerShapeless(registry, "omelette_mix_cheese", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "cheese");
        registerShapeless(registry, "omelette_mix_meat", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "meat", "meat");
        registerShapeless(registry, "omelette_mix_meat_cheese", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "meat", "meat", "cheese");
        registerShapeless(registry, "omelette_mix_mix", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "processedVegetable", "meat");
        registerShapeless(registry, "omelette_mix_mix_cheese", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "processedVegetable", "cheese", "meat");
        registerShapeless(registry, "omelette_mix_veg", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "processedVegetable", "processedVegetable");
        registerShapeless(registry, "omelette_mix_veg_cheese", EDLItems.OMELETTE_MIX.stack(1), EDLItems.EGG_MIX.getItem(), "processedVegetable", "processedVegetable", "cheese");
        registerShaped(registry, "corn_cob_pipe", EDLItems.CORN_COB_PIPE.stack(1), "cs", 'c', EDLItems.CORN_COB.getItem(), 's', "stickWood");
        registerShapeless(registry, "breading_misanplas", EDLItems.BREADING_MISANPLAS.stack(1), EDLItems.FLOUR.getItem(), EDLItems.EGG_MIX.getItem(), EDLBlocks.BREADCRUMBS.stack(1), EDLItems.COOKING_OIL.getItem());
        registerShapeless(registry, "buttered_toast", EDLItems.BUTTERED_TOAST.stack(1), EDLItems.TOAST.getItem(), EDLItems.BUTTER.getItem());
        registerCheeseSandwichRecipes(registry);
        registerShapeless(registry, "smore", EDLItems.SMORE.stack(1), EDLItems.GRAHAM_CRACKER.getItem(), EDLItems.MARSHMALLOW.getItem(), EDLItems.COCOA_SOLIDS.getItem());
        registerShapeless(registry, "trail_mix", EDLItems.TRAIL_MIX.stack(3), EDLItems.DRIED_FRUIT.getItem(), EDLItems.ROASTED_PEANUTS.getItem(), EDLItems.COCOA_SOLIDS.getItem());
        registerShapeless(registry, "cactus_eggs", EDLItems.CACTUS_EGGS.stack(1), EDLItems.COOKED_CACTUS.getItem(), EDLItems.SCRAMBLED_EGGS.getItem());
        registerShapeless(registry, "caramel_popcorn", EDLItems.CARAMEL_POPCORN.stack(1), EDLItems.POPCORN.getItem(), EDLItems.CARAMEL_SAUCE.getItem(), Items.BOWL);
        registerShapeless(registry, "furikake_rice", EDLItems.FURIKAKE_RICE.stack(1), EDLItems.FURIKAKE.getItem(), itemStack("farmersdelight:cooked_rice"), Items.BOWL);
        registerShapeless(registry, "xocolatl_crafted", EDLItems.XOCOLATL.stack(1), itemStack("farmersdelight:hot_cocoa"), EDLItems.CHILI_POWDER.getItem());
        registerShapeless(registry, "chocolate_milk", EDLItems.CHOCOLATE_MILK.stack(1), itemStack("farmersdelight:milk_bottle"), "cocoaPowder");
        registerShapeless(registry, "chocolate_milk_bucket", EDLItems.CHOCOLATE_MILK.stack(4), Items.MILK_BUCKET, "cocoaPowder", "cocoaPowder", "cocoaPowder", "cocoaPowder", Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
        registerShapeless(registry, "cinnamon_toast", EDLItems.CINNAMON_TOAST.stack(4), "cinnamonGround", "edlSweetener", "butter", EDLItems.TOAST.getItem(), EDLItems.TOAST.getItem(), EDLItems.TOAST.getItem(), EDLItems.TOAST.getItem());
        registerShapeless(registry, "corn_flakes_cereal", EDLItems.CORN_FLAKES_CEREAL.stack(1), EDLItems.CORN_FLAKES.getItem(), Items.MILK_BUCKET, Items.BOWL);
        registerShapeless(registry, "fish_chips", EDLItems.FISH_CHIPS.stack(1), EDLItems.FRIED_FISH.getItem(), EDLItems.FRENCH_FRIES.getItem(), EDLItems.VINEGAR.getItem());
        registerShapeless(registry, "mushroom_burger", EDLItems.MUSHROOM_BURGER.stack(1), Items.BREAD, itemStack("farmersdelight:beef_patty"), Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, EDLItems.CHEESE.getItem());
        registerShapeless(registry, "cheeseburger", EDLItems.CHEESEBURGER.stack(1), Items.BREAD, itemStack("farmersdelight:beef_patty"), itemStack("farmersdelight:cabbage"), EDLItems.CHEESE.getItem(), EDLItems.SLICED_TOMATO.getItem(), EDLItems.SLICED_ONION.getItem(), EDLItems.SLICED_GHERKIN_ITEM.getItem());
        registerShapeless(registry, "cheeseburger_burger", EDLItems.CHEESEBURGER.stack(1), itemStack("farmersdelight:hamburger"), EDLItems.SLICED_GHERKIN_ITEM.getItem(), EDLItems.CHEESE.getItem());
        registerShapeless(registry, "bacon_cheeseburger", EDLItems.BACON_CHEESEBURGER.stack(1), EDLItems.CHEESEBURGER.getItem(), "foodBaconCooked");
        registerShapeless(registry, "bacon_cheeseburger_full", EDLItems.BACON_CHEESEBURGER.stack(1), Items.BREAD, itemStack("farmersdelight:beef_patty"), itemStack("farmersdelight:cabbage"), "cheese", EDLItems.SLICED_TOMATO.getItem(), EDLItems.SLICED_ONION.getItem(), "foodBaconCooked", EDLItems.SLICED_GHERKIN_ITEM.getItem());
        registerShapeless(registry, "bacon_cheeseburger_burger", EDLItems.BACON_CHEESEBURGER.stack(1), itemStack("farmersdelight:hamburger"), "cheese", "foodBaconCooked", EDLItems.SLICED_GHERKIN_ITEM.getItem());
        registerShapeless(registry, "bacon_egg_sandwich", EDLItems.BACON_EGG_SANDWICH.stack(1), itemStack("farmersdelight:egg_sandwich"), "foodBaconCooked", "foodBaconCooked");
        registerShapeless(registry, "bacon_egg_sandwich_full", EDLItems.BACON_EGG_SANDWICH.stack(1), Items.BREAD, "foodBaconCooked", "foodBaconCooked", "foodEggCooked", "foodEggCooked");
        registerShapeless(registry, "bacon_egg_cheese_sandwich", EDLItems.BACON_EGG_CHEESE_SANDWICH.stack(1), EDLItems.BACON_EGG_SANDWICH.getItem(), EDLItems.CHEESE.getItem());
        registerShapeless(registry, "egg_salad_sandwich", EDLItems.EGG_SALAD_SANDWICH.stack(1), EDLItems.EGG_SALAD.getItem(), Items.BREAD);
        registerShapeless(registry, "egg_salad_sandwich_slice", EDLItems.EGG_SALAD_SANDWICH.stack(1), EDLItems.EGG_SALAD.getItem(), EDLItems.BREAD_SLICE.getItem(), EDLItems.BREAD_SLICE.getItem());
        registerShapeless(registry, "fish_salad_sandwich", EDLItems.FISH_SALAD_SANDWICH.stack(1), EDLItems.FISH_SALAD.getItem(), Items.BREAD);
        registerShapeless(registry, "fish_salad_sandwich_slice", EDLItems.FISH_SALAD_SANDWICH.stack(1), EDLItems.FISH_SALAD.getItem(), EDLItems.BREAD_SLICE.getItem(), EDLItems.BREAD_SLICE.getItem());
        registerShapeless(registry, "croque_madame", EDLItems.CROQUE_MADAME.stack(1), EDLItems.CROQUE_MONSIEUR.getItem(), "foodEggCooked");
        registerShapeless(registry, "jalapeno_stuffed_potato", EDLItems.JALAPENO_STUFFED_POTATO.stack(1), itemStack("farmersdelight:stuffed_potato"), EDLItems.SLICED_CHILI.getItem());
        registerShapeless(registry, "jalapeno_stuffed_potato_full", EDLItems.JALAPENO_STUFFED_POTATO.stack(1), Items.BAKED_POTATO, Items.MILK_BUCKET, "foodBeefCooked", EDLItems.SLICED_CHILI.getItem());
        registerShapeless(registry, "currywurst", EDLItems.CURRYWURST.stack(2), EDLItems.BREAD_SLICE.getItem(), EDLItems.BREAD_SLICE.getItem(), EDLItems.KETCHUP.getItem(), EDLItems.CURRY_POWDER.getItem(), "foodSausageCooked", "foodSausageCooked");
        registerShapeless(registry, "gourmet_hot_chocolate", EDLItems.GOURMET_HOT_CHOCOLATE.stack(1), itemStack("farmersdelight:hot_cocoa"), EDLItems.WHIPPED_CREAM.getItem(), "foodCookie", "cinnamonGround", EDLItems.MARSHMALLOW.getItem());
        registerShapeless(registry, "ice_cream_sundae", EDLItems.ICE_CREAM_SUNDAE.stack(1), "iceCream", "chocolateSyrup", "nutHazelnutRoasted", "cropBerrySweet");
        registerShapeless(registry, "dirt_cake", EDLItems.DIRT_CAKE.stack(1), "foodCookie", EDLItems.CHOCOLATE_CUSTARD.getItem(), "gummies");
        registerShapeless(registry, "fluffer_nutter", EDLItems.FLUFFER_NUTTER.stack(1), "breadSliced", "nutButter", "marshmallowFluff", "breadSliced");
        registerShapeless(registry, "peanut_butter_jelly", EDLItems.PEANUT_BUTTER_JELLY.stack(1), "breadSliced", "nutButter", "jam", "breadSliced");
        registerShapeless(registry, "affogato", EDLItems.AFFOGATO.stack(1), "coffee", "iceCream");
        registerShapeless(registry, "pork_tenderloin_sandwich", EDLItems.PORK_TENDERLOIN_SANDWICH.stack(1), "condiment", Items.BREAD, EDLItems.PORK_TENDERLOIN.getItem());
        registerShaped(registry, "pork_tenderloin_sandwich_bread_slice", EDLItems.PORK_TENDERLOIN_SANDWICH.stack(1),
            " b ", "cm ", " b ", 'b', "breadSliced", 'c', "condiment", 'm', EDLItems.PORK_TENDERLOIN.getItem());
        registerShaped(registry, "baked_alaska", EDLBlocks.RAW_BAKED_ALASKA.stack(1),
            "sps", "pip", "ccc",
            's', "edlSweetener",
            'p', EDLItems.STIFF_PEAKS.getItem(),
            'i', "iceCream",
            'c', "foodCakeSlice");
        registerShapeless(registry, "natto_and_rice", EDLItems.NATTO_AND_RICE.stack(1),
            "foodNatto", itemStack("farmersdelight:cooked_rice"), "soySauce", itemStack("farmersdelight:fried_egg"));
        registerShapeless(registry, "riceball", EDLItems.RICEBALL.stack(2),
            itemStack("farmersdelight:cooked_rice"), "foodKelpDried", EDLItems.FISH_FLAKES.getItem());
        registerShapeless(registry, "riceball_filled", EDLItems.RICEBALL_FILLED.stack(1),
            itemStack("farmersdelight:cooked_rice"), "foodKelpDried", EDLItems.FISH_FLAKES.getItem(), "riceballFilling");
        registerShapeless(registry, "kiwiburger", EDLItems.KIWIBURGER.stack(1),
            Items.BREAD, itemStack("farmersdelight:beef_patty"), itemStack("farmersdelight:cabbage"),
            EDLItems.SLICED_TOMATO.getItem(), EDLItems.SLICED_ONION.getItem(), EDLItems.PICKLED_BEET_ITEM.getItem(),
            itemStack("farmersdelight:fried_egg"));
        registerShapeless(registry, "kiwiburger_burger", EDLItems.KIWIBURGER.stack(1),
            itemStack("farmersdelight:hamburger"), EDLItems.PICKLED_BEET_ITEM.getItem(), itemStack("farmersdelight:fried_egg"));
        registerCitrusBannerRecipes(registry);
        registerButcheryCraftingRecipes(registry);
        registerPieSliceRecipes(registry);
        registerShaped(registry, "croque_monsieur", EDLItems.CROQUE_MONSIEUR.stack(1),
            "BFM", "TCT", " H ", 'B', EDLItems.BUTTER.getItem(), 'F', EDLItems.FLOUR.getItem(), 'M', itemStack("farmersdelight:milk_bottle"),
            'T', EDLItems.TOAST.getItem(), 'C', EDLItems.CHEESE.getItem(), 'H', "foodHamCooked");
        registerShaped(registry, "croque_madame_full", EDLItems.CROQUE_MADAME.stack(1),
            "BFM", "TET", "C H", 'B', EDLItems.BUTTER.getItem(), 'F', EDLItems.FLOUR.getItem(), 'M', itemStack("farmersdelight:milk_bottle"),
            'T', EDLItems.TOAST.getItem(), 'E', "foodEggCooked", 'C', EDLItems.CHEESE.getItem(), 'H', "foodHamCooked");
        registerToolRecipes(registry);
        registerStorageBlock(registry, "corn", EDLItems.CORN_ON_COB, EDLBlocks.CORN_CRATE);
        registerStorageBlock(registry, "corn_kernels", EDLItems.CORN_SEEDS, EDLBlocks.CORN_SACK);
        registerStorageBlock(registry, "corn_silk", EDLItems.CORN_SILK, EDLBlocks.CORN_SILK_SACK);
        registerStorageBlock(registry, "corn_husk", EDLItems.CORN_HUSK, EDLBlocks.CORN_HUSK_BUNDLE);
        registerStorageBlock(registry, "corn_cob", EDLItems.CORN_COB, EDLBlocks.CORN_COB_BUNDLE);
        registerStorageBlock(registry, "garlic", EDLItems.GARLIC, EDLBlocks.GARLIC_CRATE);
        registerStorageBlock(registry, "ginger", EDLItems.GINGER, EDLBlocks.GINGER_CRATE);
        registerStorageBlock(registry, "mint", EDLItems.MINT, EDLBlocks.MINT_SACK);
        registerStorageBlock(registry, "peanut_in_shell", EDLItems.PEANUTS_IN_SHELL, EDLBlocks.PEANUT_IN_SHELL_SACK);
        registerStorageBlock(registry, "peanut", EDLItems.PEANUTS, EDLBlocks.PEANUT_SACK);
        registerStorageBlock(registry, "roasted_peanut", EDLItems.ROASTED_PEANUTS, EDLBlocks.ROASTED_PEANUT_SACK);
        registerStorageBlock(registry, "mallow_root", EDLItems.MALLOW_ROOT, EDLBlocks.MALLOW_ROOT_CRATE);
        registerStorageBlock(registry, "sugar", new ItemStack(Items.SUGAR), EDLBlocks.SUGAR_SACK);
        registerStorageBlock(registry, "egg", new ItemStack(Items.EGG), EDLBlocks.EGG_CRATE);
        registerStorageBlock(registry, "brown_mushroom", new ItemStack(Blocks.BROWN_MUSHROOM), EDLBlocks.BROWN_MUSHROOM_CRATE);
        registerStorageBlock(registry, "red_mushroom", new ItemStack(Blocks.RED_MUSHROOM), EDLBlocks.RED_MUSHROOM_CRATE);
        registerStorageBlock(registry, "apple", new ItemStack(Items.APPLE), EDLBlocks.APPLE_CRATE);
        registerStorageBlock(registry, "golden_apple", new ItemStack(Items.GOLDEN_APPLE), EDLBlocks.GOLDEN_APPLE_CRATE);
        registerStorageBlock(registry, "golden_carrot", new ItemStack(Items.GOLDEN_CARROT), EDLBlocks.GOLDEN_CARROT_CRATE);
        registerStorageBlock(registry, "sweet_berry", oreStack("cropBerrySweet"), EDLBlocks.SWEET_BERRY_CRATE);
        registerStorageBlock(registry, "glow_berry", oreStack("cropBerryGlow"), EDLBlocks.GLOW_BERRY_CRATE);
        registerStorageBlock(registry, "breadcrumbs", EDLBlocks.BREADCRUMBS, EDLBlocks.BREADCRUMB_SACK);
        registerStorageBlock(registry, "dried_corn_husk", EDLItems.DRIED_CORN_HUSK, EDLBlocks.DRIED_CORN_HUSK_BUNDLE);
        registerStorageBlock(registry, "flour", EDLItems.FLOUR, EDLBlocks.FLOUR_SACK);
        registerStorageBlock(registry, "cornmeal", EDLItems.CORN_MEAL, EDLBlocks.CORNMEAL_SACK);
        registerStorageBlock(registry, "chili_powder", EDLItems.CHILI_POWDER, EDLBlocks.CHILI_POWDER_SACK);
        registerStorageBlock(registry, "mallow_powder", EDLItems.MALLOW_POWDER, EDLBlocks.MALLOW_POWDER_SACK);
        registerStorageBlock(registry, "cocoa_beans", new ItemStack(Items.DYE, 1, 3), EDLBlocks.COCOA_BEANS_SACK);
        registerStorageBlock(registry, "roasted_cocoa_beans", EDLItems.ROASTED_COCOA_BEANS, EDLBlocks.ROASTED_COCOA_BEANS_SACK);
        registerStorageBlock(registry, "cocoa_solids", EDLItems.COCOA_SOLIDS, EDLBlocks.COCOA_SOLIDS_SACK);
        registerStorageBlock(registry, "cocoa_powder", EDLItems.COCOA_POWDER, EDLBlocks.COCOA_POWDER_SACK);
        registerStorageBlock(registry, "cucumber", EDLItems.CUCUMBER, EDLBlocks.CUCUMBER_CRATE);
        registerStorageBlock(registry, "soybean", EDLItems.SOYBEANS, EDLBlocks.SOYBEAN_SACK);
        registerStorageBlock(registry, "salt", EDLItems.SALT, EDLBlocks.SALT_BLOCK);
        registerStorageBlock(registry, "lemon", EDLItems.LEMON, EDLBlocks.LEMON_CRATE);
        registerStorageBlock(registry, "lime", EDLItems.LIME, EDLBlocks.LIME_CRATE);
        registerStorageBlock(registry, "orange", EDLItems.ORANGE, EDLBlocks.ORANGE_CRATE);
        registerStorageBlock(registry, "grapefruit", EDLItems.GRAPEFRUIT, EDLBlocks.GRAPEFRUIT_CRATE);
        registerStorageBlock(registry, "hazelnut_in_shell", EDLItems.HAZELNUTS_IN_SHELL, EDLBlocks.HAZELNUT_IN_SHELL_SACK);
        registerStorageBlock(registry, "hazelnut", EDLItems.HAZELNUTS, EDLBlocks.HAZELNUT_SACK);
        registerStorageBlock(registry, "roasted_hazelnut", EDLItems.ROASTED_HAZELNUTS, EDLBlocks.ROASTED_HAZELNUT_SACK);
        registerStorageBlock(registry, "chili", EDLItems.CHILI, EDLBlocks.CHILI_CRATE);
        registerShaped(registry, "corn_husk_doll", EDLBlocks.CORN_HUSK_DOLL.stack(1),
            " C ", " S ", "CCC",
            'C', EDLItems.DRIED_CORN_HUSK.getItem(),
            'S', Items.STRING);
        registerShapeless(registry, "unripe_salami_item", EDLItems.UNRIPE_SALAMI_ITEM.stack(4),
            EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem(),
            EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem(), EDLItems.SALAMI_MIX.getItem());
        registerStorageBlock(registry, "coffee_cherries", EDLItems.COFFEE_CHERRIES, EDLBlocks.COFFEE_CHERRY_CRATE);
        registerStorageBlock(registry, "coffee_beans", EDLItems.COFFEE_BEANS, EDLBlocks.COFFEE_BEAN_SACK);
        registerStorageBlock(registry, "green_coffee", EDLItems.GREEN_COFFEE, EDLBlocks.GREEN_COFFEE_BEANS_SACK);
        registerStorageBlock(registry, "ground_coffee", EDLItems.GROUND_COFFEE, EDLBlocks.GROUND_COFFEE_SACK);
        registerStorageBlock(registry, "ground_cinnamon", EDLItems.GROUND_CINNAMON, EDLBlocks.GROUND_CINNAMON_SACK);
        registerStorageBlock(registry, "raw_cinnamon", EDLItems.RAW_CINNAMON, EDLBlocks.RAW_CINNAMON_BLOCK);
        registerStorageBlock(registry, "cinnamon_stick", EDLItems.CINNAMON_STICK, EDLBlocks.CINNAMON_STICK_BLOCK);
        registerStorageBlock(registry, "marshmallow", EDLItems.MARSHMALLOW, EDLBlocks.MARSHMALLOW_BLOCK);
        registerCompact4(registry, "mint_candy_blue", EDLItems.MINT_CANDY_BLUE, EDLItems.CANDY_CANE_BLUE);
        registerCompact4(registry, "mint_candy_green", EDLItems.MINT_CANDY_GREEN, EDLItems.CANDY_CANE_GREEN);
        registerCompact4(registry, "mint_candy_red", EDLItems.MINT_CANDY_RED, EDLItems.CANDY_CANE_RED);
        registerCompact4(registry, "candy_cane_blue", EDLItems.CANDY_CANE_BLUE, EDLBlocks.CANDY_CANE_BLUE_BLOCK);
        registerCompact4(registry, "candy_cane_green", EDLItems.CANDY_CANE_GREEN, EDLBlocks.CANDY_CANE_GREEN_BLOCK);
        registerCompact4(registry, "candy_cane_red", EDLItems.CANDY_CANE_RED, EDLBlocks.CANDY_CANE_RED_BLOCK);
        registerChocolateBoxRecipes(registry);
        registerShaped(registry, "candy_bowl", EDLBlocks.CANDY_BOWL.stack(1),
            "gbg", " g ", 'g', Blocks.GLASS, 'b', Items.BOWL);
        registerFoodDisplayRecipes(registry);
        registerKnifeBlockRecipes(registry);
        registerCabinetRecipes(registry);
        registerAestheticRecipes(registry);
        registerCosmeticBuildingBlockRecipes(registry);
        registerSpecialDecorRecipes(registry);
        registerHangingBlockRecipes(registry);
        registerFeastCraftingRecipes(registry);
        registerDrinkTrayRecipes(registry);
        registerBottleFluidRecipes(registry);
        registerDynamicToastRecipes(registry);
    }

    private static void registerDrinkTrayRecipes(IForgeRegistry<IRecipe> registry) {
        registerDrinkTray(registry, "lemonade_tray", EDLItems.LEMONADE, EDLBlocks.LEMONADE_TRAY);
        registerDrinkTray(registry, "limeade_tray", EDLItems.LIMEADE, EDLBlocks.LIMEADE_TRAY);
        registerDrinkTray(registry, "orangeade_tray", EDLItems.ORANGEADE, EDLBlocks.ORANGEADE_TRAY);
    }

    private static void registerCheeseSandwichRecipes(IForgeRegistry<IRecipe> registry) {
        registerShaped(registry, "cheese_sandwich_mayo", EDLItems.CHEESE_SANDWICH.stack(1),
            " b ", "mc ", " b ", 'b', "breadSliced", 'm', "mayo", 'c', "cheese");
        registerShaped(registry, "cheese_sandwich_butter", EDLItems.CHEESE_SANDWICH.stack(1),
            " b ", "mc ", " b ", 'b', "breadSliced", 'm', "butter", 'c', "cheese");
        registerShaped(registry, "cheese_sandwich_aioli", EDLItems.CHEESE_SANDWICH.stack(1),
            " b ", "mc ", " b ", 'b', "breadSliced", 'm', "aioli", 'c', "cheese");
    }

    private static void registerDrinkTray(IForgeRegistry<IRecipe> registry, String name,
                                          EDLItems.ItemDefinition drink, EDLBlocks.BlockDefinition tray) {
        if (!drink.isRegistered() || !tray.isRegistered()) {
            return;
        }
        registerShapeless(registry, name, tray.stack(1),
            drink.getItem(), drink.getItem(), drink.getItem(), drink.getItem(), Items.GLASS_BOTTLE);
    }

    private static void registerFeastCraftingRecipes(IForgeRegistry<IRecipe> registry) {
        if (!EDLBlocks.PUNCH_FEAST.isRegistered()
            && !EDLBlocks.CHARCUTERIE_BOARD_FEAST.isRegistered()
            && !EDLBlocks.BLACK_FOREST_TRIFLE_BLOCK.isRegistered()) {
            return;
        }

        registerShaped(registry, "punch_feast", EDLBlocks.PUNCH_FEAST.stack(1),
            "fff", "sws", " b ",
            'f', "processedFruit", 's', "edlSweetener", 'w', Items.WATER_BUCKET, 'b', Items.BOWL);
        registerShaped(registry, "charcuterie_board_feast", EDLBlocks.CHARCUTERIE_BOARD_FEAST.stack(1),
            "cmh", "cmh", "pbp",
            'c', EDLItems.CRACKERS.getItem(), 'm', "foodMeatCooked", 'h', "cheese", 'p', "foodPickledVegetable", 'b', Items.BOWL);
        registerShaped(registry, "black_forest_trifle_block", EDLBlocks.BLACK_FOREST_TRIFLE_BLOCK.stack(1),
            " w ", "scs", " b ",
            'w', "whippedCream", 's', "cropBerrySweet", 'c', EDLBlocks.CHOCOLATE_CAKE.stack(1), 'b', Items.BOWL);
    }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        registerSmelting("corn_seeds_to_popcorn", EDLItems.CORN_SEEDS, EDLItems.POPCORN, 0.35F);
        registerSmelting("corn_on_cob_to_grilled", EDLItems.CORN_ON_COB, EDLItems.GRILLED_CORN_ON_COB, 0.35F);
        registerSmelting("peanuts_to_roasted_peanuts", EDLItems.PEANUTS, EDLItems.ROASTED_PEANUTS, 0.35F);
        registerSmelting("green_coffee_to_coffee_beans", EDLItems.GREEN_COFFEE, EDLItems.COFFEE_BEANS, 0.35F);
        registerSmelting("hazelnuts_to_roasted_hazelnuts", EDLItems.HAZELNUTS, EDLItems.ROASTED_HAZELNUTS, 0.35F);
        registerSmelting("bread_slice_to_toast", EDLItems.BREAD_SLICE, EDLItems.TOAST, 1.0F);
        registerSmelting("egg_mix_to_scrambled_eggs", EDLItems.EGG_MIX, EDLItems.SCRAMBLED_EGGS, 0.35F);
        registerSmelting("omelette_mix_to_omelette", EDLItems.OMELETTE_MIX, EDLItems.OMELETTE, 0.35F);
        registerSmelting("garlic_clove_to_roasted_garlic", EDLItems.GARLIC_CLOVE, EDLItems.ROASTED_GARLIC, 0.35F);
        registerSmelting("cheese_sandwich_to_grilled_cheese", EDLItems.CHEESE_SANDWICH, EDLItems.GRILLED_CHEESE, 0.35F);
        registerSmelting(EDLItems.SUGAR_COOKIE_DOUGH.stack(1), EDLItems.SUGAR_COOKIE.stack(8), 0.35F);
        registerSmelting("raw_sugar_cookie_alex", EDLItems.RAW_SUGAR_COOKIE_ALEX, EDLItems.SUGAR_COOKIE_ALEX, 0.35F);
        registerSmelting("raw_sugar_cookie_creeper", EDLItems.RAW_SUGAR_COOKIE_CREEPER, EDLItems.SUGAR_COOKIE_CREEPER, 0.35F);
        registerSmelting("raw_sugar_cookie_pickaxe", EDLItems.RAW_SUGAR_COOKIE_PICKAXE, EDLItems.SUGAR_COOKIE_PICKAXE, 0.35F);
        registerSmelting("raw_sugar_cookie_steve", EDLItems.RAW_SUGAR_COOKIE_STEVE, EDLItems.SUGAR_COOKIE_STEVE, 0.35F);
        registerSmelting("raw_sugar_cookie_sword", EDLItems.RAW_SUGAR_COOKIE_SWORD, EDLItems.SUGAR_COOKIE_SWORD, 0.35F);
        registerSmelting("raw_sugar_cookie_villager", EDLItems.RAW_SUGAR_COOKIE_VILLAGER, EDLItems.SUGAR_COOKIE_VILLAGER, 0.35F);
        registerSmelting("raw_sugar_cookie_diamond", EDLItems.RAW_SUGAR_COOKIE_DIAMOND, EDLItems.SUGAR_COOKIE_DIAMOND, 0.35F);
        registerSmelting("raw_sugar_cookie_emerald", EDLItems.RAW_SUGAR_COOKIE_EMERALD, EDLItems.SUGAR_COOKIE_EMERALD, 0.35F);
        registerSmelting(EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1), EDLItems.CHOCOLATE_COOKIE.stack(8), 0.35F);
        registerSmelting(EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1), EDLItems.NUT_BUTTER_COOKIE.stack(8), 0.35F);
        registerSmelting(EDLItems.APPLE_COOKIE_DOUGH.stack(1), EDLItems.APPLE_COOKIE.stack(8), 0.35F);
        registerSmelting(EDLItems.GINGERBREAD_COOKIE_DOUGH.stack(1), EDLItems.GINGERBREAD_COOKIE.stack(8), 0.35F);
        registerSmelting(EDLItems.GLOW_BERRY_COOKIE_DOUGH.stack(1), EDLItems.GLOW_BERRY_COOKIE.stack(8), 0.35F);
        registerSmelting(EDLItems.PUMPKIN_COOKIE_DOUGH.stack(1), EDLItems.PUMPKIN_COOKIE.stack(8), 0.35F);
        registerSmelting("raw_gingerbread_alex", EDLItems.RAW_GINGERBREAD_ALEX, EDLItems.GINGERBREAD_ALEX, 0.35F);
        registerSmelting("raw_gingerbread_creeper", EDLItems.RAW_GINGERBREAD_CREEPER, EDLItems.GINGERBREAD_CREEPER, 0.35F);
        registerSmelting("raw_gingerbread_pickaxe", EDLItems.RAW_GINGERBREAD_PICKAXE, EDLItems.GINGERBREAD_PICKAXE, 0.35F);
        registerSmelting("raw_gingerbread_steve", EDLItems.RAW_GINGERBREAD_STEVE, EDLItems.GINGERBREAD_STEVE, 0.35F);
        registerSmelting("raw_gingerbread_sword", EDLItems.RAW_GINGERBREAD_SWORD, EDLItems.GINGERBREAD_SWORD, 0.35F);
        registerSmelting("raw_gingerbread_villager", EDLItems.RAW_GINGERBREAD_VILLAGER, EDLItems.GINGERBREAD_VILLAGER, 0.35F);
        registerSmelting("raw_gingerbread_diamond", EDLItems.RAW_GINGERBREAD_DIAMOND, EDLItems.GINGERBREAD_DIAMOND, 0.35F);
        registerSmelting("raw_gingerbread_emerald", EDLItems.RAW_GINGERBREAD_EMERALD, EDLItems.GINGERBREAD_EMERALD, 0.35F);
        registerSmelting("cactus_to_cooked_cactus", EDLItems.CACTUS, EDLItems.COOKED_CACTUS, 0.35F);
        registerSmelting(optionalStack(ModItems.RICE), EDLItems.CRISP_RICE.stack(1), 0.35F);
        registerSmelting(Items.PUMPKIN_SEEDS, EDLItems.ROASTED_PUMPKIN_SEEDS.stack(1), 0.35F);
        registerSmelting(new ItemStack(Items.DYE, 1, 3), EDLItems.ROASTED_COCOA_BEANS.stack(1), 0.35F);
        registerSmelting(Items.CARROT, EDLItems.ROASTED_CARROT.stack(1), 0.35F);
        registerSmelting(Items.APPLE, EDLItems.ROASTED_APPLE.stack(1), 0.35F);
        registerSmelting(EDLItems.GRATED_POTATO.stack(1), EDLItems.HASHBROWNS.stack(1), 0.35F);
        registerSmelting(Items.WHEAT_SEEDS, EDLItems.COOKED_WHEAT_SEEDS.stack(1), 0.35F);
        registerButcherySmeltingRecipes();

        registerDryingRackRecipes();
        registerMortarRecipes();
        registerDoughShapingRecipes();
        registerBottleFluidMappings();
        registerMixingBowlRecipes();
        registerChillerRecipes();
        registerMeltingPotRecipes();
        registerOvenRecipes();
        registerJuicerRecipes();
        registerVatRecipes();
        registerEvaporatorRecipes();
        registerCookingPotRecipes();
        registerFeastServingRecipes();
        ButcherDropRegistry.registerDefaults();

        registerCampfire("campfire/popcorn", EDLItems.CORN_SEEDS, EDLItems.POPCORN, 600);
        registerCampfire("campfire/roasted_peanuts", EDLItems.PEANUTS, EDLItems.ROASTED_PEANUTS, 600);
        registerCampfire("campfire/coffee_beans", EDLItems.GREEN_COFFEE, EDLItems.COFFEE_BEANS, 600);
        registerCampfire("campfire/roasted_hazelnuts", EDLItems.HAZELNUTS, EDLItems.ROASTED_HAZELNUTS, 600);
        registerCampfire("campfire/toast", EDLItems.BREAD_SLICE, EDLItems.TOAST, 600);
        registerCampfire("campfire/scrambled_eggs", EDLItems.EGG_MIX, EDLItems.SCRAMBLED_EGGS, 600);
        registerCampfire("campfire/omelette", EDLItems.OMELETTE_MIX, EDLItems.OMELETTE, 600);
        registerCampfire("campfire/roasted_garlic", EDLItems.GARLIC_CLOVE, EDLItems.ROASTED_GARLIC, 600);
        registerCampfire("campfire/grilled_cheese", EDLItems.CHEESE_SANDWICH, EDLItems.GRILLED_CHEESE, 600);
        registerCampfire("campfire/sugar_cookie", EDLItems.SUGAR_COOKIE_DOUGH.stack(1), EDLItems.SUGAR_COOKIE.stack(8), 600);
        registerCampfire("campfire/sugar_cookie_alex", EDLItems.RAW_SUGAR_COOKIE_ALEX, EDLItems.SUGAR_COOKIE_ALEX, 600);
        registerCampfire("campfire/sugar_cookie_creeper", EDLItems.RAW_SUGAR_COOKIE_CREEPER, EDLItems.SUGAR_COOKIE_CREEPER, 600);
        registerCampfire("campfire/sugar_cookie_pickaxe", EDLItems.RAW_SUGAR_COOKIE_PICKAXE, EDLItems.SUGAR_COOKIE_PICKAXE, 600);
        registerCampfire("campfire/sugar_cookie_steve", EDLItems.RAW_SUGAR_COOKIE_STEVE, EDLItems.SUGAR_COOKIE_STEVE, 600);
        registerCampfire("campfire/sugar_cookie_sword", EDLItems.RAW_SUGAR_COOKIE_SWORD, EDLItems.SUGAR_COOKIE_SWORD, 600);
        registerCampfire("campfire/sugar_cookie_villager", EDLItems.RAW_SUGAR_COOKIE_VILLAGER, EDLItems.SUGAR_COOKIE_VILLAGER, 600);
        registerCampfire("campfire/sugar_cookie_diamond", EDLItems.RAW_SUGAR_COOKIE_DIAMOND, EDLItems.SUGAR_COOKIE_DIAMOND, 600);
        registerCampfire("campfire/sugar_cookie_emerald", EDLItems.RAW_SUGAR_COOKIE_EMERALD, EDLItems.SUGAR_COOKIE_EMERALD, 600);
        registerCampfire("campfire/chocolate_cookie", EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1), EDLItems.CHOCOLATE_COOKIE.stack(8), 600);
        registerCampfire("campfire/nut_butter_cookie", EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1), EDLItems.NUT_BUTTER_COOKIE.stack(8), 600);
        registerCampfire("campfire/apple_cookie", EDLItems.APPLE_COOKIE_DOUGH.stack(1), EDLItems.APPLE_COOKIE.stack(8), 600);
        registerCampfire("campfire/gingerbread_cookie", EDLItems.GINGERBREAD_COOKIE_DOUGH.stack(1), EDLItems.GINGERBREAD_COOKIE.stack(8), 600);
        registerCampfire("campfire/glow_berry_cookie", EDLItems.GLOW_BERRY_COOKIE_DOUGH.stack(1), EDLItems.GLOW_BERRY_COOKIE.stack(8), 600);
        registerCampfire("campfire/pumpkin_cookie", EDLItems.PUMPKIN_COOKIE_DOUGH.stack(1), EDLItems.PUMPKIN_COOKIE.stack(8), 600);
        registerCampfire("campfire/gingerbread_alex", EDLItems.RAW_GINGERBREAD_ALEX, EDLItems.GINGERBREAD_ALEX, 600);
        registerCampfire("campfire/gingerbread_creeper", EDLItems.RAW_GINGERBREAD_CREEPER, EDLItems.GINGERBREAD_CREEPER, 600);
        registerCampfire("campfire/gingerbread_pickaxe", EDLItems.RAW_GINGERBREAD_PICKAXE, EDLItems.GINGERBREAD_PICKAXE, 600);
        registerCampfire("campfire/gingerbread_steve", EDLItems.RAW_GINGERBREAD_STEVE, EDLItems.GINGERBREAD_STEVE, 600);
        registerCampfire("campfire/gingerbread_sword", EDLItems.RAW_GINGERBREAD_SWORD, EDLItems.GINGERBREAD_SWORD, 600);
        registerCampfire("campfire/gingerbread_villager", EDLItems.RAW_GINGERBREAD_VILLAGER, EDLItems.GINGERBREAD_VILLAGER, 600);
        registerCampfire("campfire/gingerbread_diamond", EDLItems.RAW_GINGERBREAD_DIAMOND, EDLItems.GINGERBREAD_DIAMOND, 600);
        registerCampfire("campfire/gingerbread_emerald", EDLItems.RAW_GINGERBREAD_EMERALD, EDLItems.GINGERBREAD_EMERALD, 600);
        registerCampfire("campfire/cooked_cactus", EDLItems.CACTUS, EDLItems.COOKED_CACTUS, 600);
        registerCampfire("campfire/crisp_rice", optionalStack(ModItems.RICE), EDLItems.CRISP_RICE.stack(1), 600);
        registerCampfire("campfire/roasted_pumpkin_seeds", new ItemStack(Items.PUMPKIN_SEEDS), EDLItems.ROASTED_PUMPKIN_SEEDS.stack(1), 600);
        registerCampfire("campfire/roasted_cocoa_beans", new ItemStack(Items.DYE, 1, 3), EDLItems.ROASTED_COCOA_BEANS.stack(1), 600);
        registerCampfire("campfire/roasted_carrot", new ItemStack(Items.CARROT), EDLItems.ROASTED_CARROT.stack(1), 600);
        registerCampfire("campfire/roasted_apple", new ItemStack(Items.APPLE), EDLItems.ROASTED_APPLE.stack(1), 600);
        registerCampfire("campfire/hashbrowns", EDLItems.GRATED_POTATO.stack(1), EDLItems.HASHBROWNS.stack(1), 600);
        registerCampfire("campfire/cooked_wheat_seeds", new ItemStack(Items.WHEAT_SEEDS), EDLItems.COOKED_WHEAT_SEEDS.stack(1), 600);
        registerButcheryCampfireRecipes();

        registerCutting("cutting/bread_slice", "minecraft:bread", itemId("bread_slice"), 4, 1.0F);
        registerCutting("cutting/croutons", itemId("bread_slice"), itemId("croutons"), 4, 1.0F);
        registerCutting("cutting/dark_chocolate_chips", itemId("dark_chocolate_bar"), itemId("dark_chocolate_chips"), 5, 1.0F);
        registerCutting("cutting/milk_chocolate_chips", itemId("milk_chocolate_bar"), itemId("milk_chocolate_chips"), 5, 1.0F);
        registerCutting("cutting/white_chocolate_chips", itemId("white_chocolate_bar"), itemId("white_chocolate_chips"), 5, 1.0F);
        registerCutting("cutting/blood_chocolate_chips", itemId("blood_chocolate_bar"), itemId("blood_chocolate_chips"), 5, 1.0F);
        registerCuttingWithTools(
            "cutting/sliced_cactus",
            new ItemStack(Blocks.CACTUS),
            null,
            new ItemStack[]{EDLItems.CACTUS.stack(2)},
            new float[]{1.0F}
        );
        registerCutting("cutting/chili", itemId("chili"), itemId("sliced_chili"), 3, 1.0F);
        registerCutting(
            "cutting/garlic",
            new String[]{itemId("garlic")},
            null,
            new String[]{itemId("garlic_clove"), itemId("garlic_clove")},
            new int[]{3, 1},
            new float[]{1.0F, 0.75F}
        );
        registerCutting(
            "cutting/corn_on_cob",
            new String[]{itemId("corn_on_cob")},
            null,
            new String[]{itemId("corn_seeds"), itemId("corn_cob"), itemId("corn_seeds")},
            new int[]{1, 1, 1},
            new float[]{1.0F, 1.0F, 0.75F}
        );
        registerCutting(
            "cutting/ginger_cutting",
            new String[]{itemId("ginger")},
            null,
            new String[]{itemId("ginger_cutting"), itemId("ginger_cutting")},
            new int[]{1, 1},
            new float[]{1.0F, 0.75F}
        );
        registerCutting("cutting/peanuts", itemId("peanuts_in_shell"), itemId("peanuts"), 2, 1.0F);
        registerPieCuttingRecipes();
        registerCuttingWithTools(
            "cutting/strip_cinnamon_log",
            EDLBlocks.CINNAMON_LOG.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.STRIPPED_CINNAMON_LOG.stack(1), EDLItems.CINNAMON_BARK.stack(1)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithTools(
            "cutting/strip_cinnamon_wood",
            EDLBlocks.CINNAMON_WOOD.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.STRIPPED_CINNAMON_WOOD.stack(1), EDLItems.CINNAMON_BARK.stack(1)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/cinnamon_bark",
            EDLItems.CINNAMON_BARK.stack(1),
            null,
            new ItemStack[]{EDLItems.RAW_CINNAMON.stack(2), EDLItems.RAW_CINNAMON.stack(1), itemStack("farmersdelight:tree_bark", 1)},
            new float[]{1.0F, 0.75F, 0.75F}
        );
        registerCutting("cutting/sliced_gherkin", itemId("gherkin_item"), itemId("sliced_gherkin_item"), 3, 1.0F);
        registerCutting("cutting/shredded_cabbage_knife", "farmersdelight:cabbage_leaf", itemId("shredded_cabbage_item"), 2, 1.0F);
        registerCuttingWithTools(
            "cutting/gherkins_block_to_servings",
            EDLBlocks.GHERKINS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.GHERKIN_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_beets_block_to_servings",
            EDLBlocks.PICKLED_BEETS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_BEET_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_carrots_block_to_servings",
            EDLBlocks.PICKLED_CARROTS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_CARROT_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_eggs_block_to_servings",
            EDLBlocks.PICKLED_EGGS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_EGG_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_fish_block_to_servings",
            EDLBlocks.PICKLED_FISH_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_FISH_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_sausage_block_to_servings",
            EDLBlocks.PICKLED_SAUSAGE_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_SAUSAGE_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_ginger_block_to_servings",
            EDLBlocks.PICKLED_GINGER_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_GINGER.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_onions_block_to_servings",
            EDLBlocks.PICKLED_ONIONS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_ONION_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/pickled_rinds_block_to_servings",
            EDLBlocks.PICKLED_RINDS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PICKLED_RIND_ITEM.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/preserved_lemons_block_to_servings",
            EDLBlocks.PRESERVED_LEMONS_BLOCK.stack(1),
            null,
            new ItemStack[]{EDLItems.PRESERVED_LEMON_ITEM.stack(4)},
            new float[]{1.0F}
        );

        registerCuttingWithTools(
            "cutting/spoon_ginger",
            EDLItems.GINGER.stack(1),
            spoonTools(),
            new ItemStack[]{EDLItems.PEELED_GINGER.stack(1), EDLItems.GINGER_CUTTING.stack(1)},
            new float[]{1.0F, 0.25F}
        );
        registerCuttingWithTools(
            "cutting/sliced_ginger",
            EDLItems.PEELED_GINGER.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_GINGER.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/grated_ginger",
            EDLItems.PEELED_GINGER.stack(1),
            graterTool(),
            new ItemStack[]{EDLItems.GRATED_GINGER.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/grate_garlic",
            EDLItems.GARLIC_CLOVE.stack(1),
            graterTool(),
            new ItemStack[]{EDLItems.GRATED_GARLIC.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/grate_potato",
            new ItemStack(Items.POTATO),
            graterTool(),
            new ItemStack[]{EDLItems.GRATED_POTATO.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_potato",
            new ItemStack(Items.POTATO),
            null,
            new ItemStack[]{EDLItems.SLICED_POTATO.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/stick_potato",
            EDLItems.SLICED_POTATO.stack(1),
            null,
            new ItemStack[]{EDLItems.POTATO_STICKS.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/grate_carrot",
            new ItemStack(Items.CARROT),
            graterTool(),
            new ItemStack[]{EDLItems.GRATED_CARROT.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/cut_apples",
            new ItemStack(Items.APPLE),
            null,
            new ItemStack[]{EDLItems.SLICED_APPLE.stack(2)},
            new float[]{1.0F}
        );
        registerWildCropCuttingRecipes();
        registerCuttingWithTools(
            "cutting/sliced_onion",
            optionalStack(ModItems.ONION),
            null,
            new ItemStack[]{EDLItems.SLICED_ONION.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_tomato",
            optionalStack(ModItems.TOMATO),
            null,
            new ItemStack[]{EDLItems.SLICED_TOMATO.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_beetroot",
            new ItemStack(Items.BEETROOT),
            null,
            new ItemStack[]{EDLItems.SLICED_BEETROOT_ITEM.stack(2)},
            new float[]{1.0F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/melon_chunks",
            new ItemStack(Items.MELON),
            null,
            new ItemStack[]{EDLItems.MELON_CHUNKS.stack(2), EDLItems.MELON_RIND.stack(1)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithTools(
            "cutting/ice_cubes",
            new ItemStack(Blocks.ICE),
            axeTools(),
            new ItemStack[]{EDLItems.ICE_CUBES.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/packed_ice",
            new ItemStack(Blocks.PACKED_ICE),
            axeTools(),
            new ItemStack[]{new ItemStack(Blocks.ICE, 9)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/grate_bread",
            new ItemStack(Items.BREAD),
            graterTool(),
            new ItemStack[]{EDLBlocks.BREADCRUMBS.stack(4)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sunflower_seeds",
            new ItemStack(Blocks.DOUBLE_PLANT, 1, 0),
            null,
            new ItemStack[]{EDLItems.SUNFLOWER_SEEDS.stack(2), new ItemStack(Items.DYE, 2, 11)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_cucumber",
            EDLItems.CUCUMBER.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_CUCUMBER_ITEM.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/shucked_soybeans",
            EDLItems.SOYBEAN_POD.stack(1),
            null,
            new ItemStack[]{EDLItems.SOYBEANS.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_lemon",
            EDLItems.LEMON.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_LEMON.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/lemon_zest",
            EDLItems.LEMON.stack(1),
            graterTool(),
            new ItemStack[]{EDLItems.LEMON_ZEST.stack(2)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_lime",
            EDLItems.LIME.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_LIME.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/lime_zest",
            EDLItems.LIME.stack(1),
            graterTool(),
            new ItemStack[]{EDLItems.LIME_ZEST.stack(2)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_orange",
            EDLItems.ORANGE.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_ORANGE.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/orange_zest",
            EDLItems.ORANGE.stack(1),
            graterTool(),
            new ItemStack[]{EDLItems.ORANGE_ZEST.stack(2)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/sliced_grapefruit",
            EDLItems.GRAPEFRUIT.stack(1),
            null,
            new ItemStack[]{EDLItems.SLICED_GRAPEFRUIT.stack(3)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/hazelnuts",
            EDLItems.HAZELNUTS_IN_SHELL.stack(1),
            null,
            new ItemStack[]{EDLItems.HAZELNUTS.stack(2)},
            new float[]{1.0F}
        );

        registerSourceBuildingCuttingRecipes();

        registerHangingCuttingRecipes();
        EDLCustomRecipeLifecycle.captureBaseline();
        EDLCustomRecipeLifecycle.applyPendingActions();
        ButcherDropRegistry.syncHuntingJeiRecipes();
    }

    private static void registerSourceBuildingCuttingRecipes() {
        registerCuttingWithTools(
            "cutting/strip_fruit_log",
            EDLBlocks.FRUIT_LOG.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.STRIPPED_FRUIT_LOG.stack(1), itemStack("farmersdelight:tree_bark", 1)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithTools(
            "cutting/strip_fruit_wood",
            EDLBlocks.FRUIT_WOOD.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.STRIPPED_FRUIT_WOOD.stack(1), itemStack("farmersdelight:tree_bark", 1)},
            new float[]{1.0F, 1.0F}
        );
        registerCuttingWithTools(
            "cutting/cinnamon_door_to_plank",
            EDLBlocks.CINNAMON_DOOR.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.CINNAMON_PLANKS.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/cinnamon_trapdoor_to_plank",
            EDLBlocks.CINNAMON_TRAPDOOR.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.CINNAMON_PLANKS.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/fruit_door_to_plank",
            EDLBlocks.FRUIT_DOOR.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.FRUIT_PLANKS.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/fruit_trapdoor_to_plank",
            EDLBlocks.FRUIT_TRAPDOOR.stack(1),
            axeTools(),
            new ItemStack[]{EDLBlocks.FRUIT_PLANKS.stack(1)},
            new float[]{1.0F}
        );
        registerChocolateBlockCutting("dark_chocolate", EDLBlocks.DARK_CHOCOLATE_DOOR, EDLBlocks.DARK_CHOCOLATE_TRAPDOOR, EDLBlocks.DARK_CHOCOLATE_BLOCK);
        registerChocolateBlockCutting("milk_chocolate", EDLBlocks.MILK_CHOCOLATE_DOOR, EDLBlocks.MILK_CHOCOLATE_TRAPDOOR, EDLBlocks.MILK_CHOCOLATE_BLOCK);
        registerChocolateBlockCutting("white_chocolate", EDLBlocks.WHITE_CHOCOLATE_DOOR, EDLBlocks.WHITE_CHOCOLATE_TRAPDOOR, EDLBlocks.WHITE_CHOCOLATE_BLOCK);
        registerChocolateBlockCutting("blood_chocolate", EDLBlocks.BLOOD_CHOCOLATE_DOOR, EDLBlocks.BLOOD_CHOCOLATE_TRAPDOOR, EDLBlocks.BLOOD_CHOCOLATE_BLOCK);
    }

    private static void registerChocolateBlockCutting(String name,
                                                      EDLBlocks.BlockDefinition door,
                                                      EDLBlocks.BlockDefinition trapdoor,
                                                      EDLBlocks.BlockDefinition block) {
        registerCuttingWithTools(
            "cutting/" + name + "_door_to_block",
            door.stack(1),
            pickaxeTools(),
            new ItemStack[]{block.stack(1)},
            new float[]{1.0F}
        );
        registerCuttingWithTools(
            "cutting/" + name + "_trapdoor_to_block",
            trapdoor.stack(1),
            pickaxeTools(),
            new ItemStack[]{block.stack(1)},
            new float[]{1.0F}
        );
    }

    private static void registerWildCropCuttingRecipes() {
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_ginger",
            EDLBlocks.WILD_GINGER.stack(1),
            null,
            new ItemStack[]{EDLItems.GINGER.stack(1), new ItemStack(Items.DYE, 2, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_peanut",
            EDLBlocks.WILD_PEANUT.stack(1),
            null,
            new ItemStack[]{EDLItems.PEANUTS_IN_SHELL.stack(1), new ItemStack(Items.DYE, 2, 11)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_chili",
            EDLBlocks.WILD_CHILI.stack(1),
            null,
            new ItemStack[]{EDLItems.CHILI_SEEDS.stack(1), EDLItems.CHILI.stack(1), new ItemStack(Items.DYE, 1, 2)},
            new float[]{1.0F, 0.2F, 0.1F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_mallow_root",
            EDLBlocks.WILD_MALLOW_ROOT.stack(1),
            null,
            new ItemStack[]{EDLItems.MALLOW_ROOT.stack(1), new ItemStack(Items.DYE, 2, 9)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_garlic",
            EDLBlocks.WILD_GARLIC.stack(1),
            null,
            new ItemStack[]{EDLItems.GARLIC.stack(1), new ItemStack(Items.DYE, 2, 13), new ItemStack(Items.DYE, 1, 10)},
            new float[]{1.0F, 1.0F, 0.1F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_cucumber",
            EDLBlocks.WILD_CUCUMBER.stack(1),
            null,
            new ItemStack[]{EDLItems.CUCUMBER_SEED.stack(1), EDLItems.CUCUMBER.stack(1), new ItemStack(Items.DYE, 1, 2)},
            new float[]{1.0F, 0.2F, 0.1F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/wild_soybean",
            EDLBlocks.WILD_SOYBEAN.stack(1),
            null,
            new ItemStack[]{EDLItems.SOYBEAN_POD.stack(1), new ItemStack(Items.DYE, 2, 11)},
            new float[]{1.0F, 0.5F}
        );
    }

    private static void registerPieSliceRecipes(IForgeRegistry<IRecipe> registry) {
        registerPieSliceRecipe(registry, "cheesecake", EDLItems.CHEESECAKE_SLICE, EDLBlocks.CHEESECAKE);
        registerPieSliceRecipe(registry, "chocolate_cheesecake", EDLItems.CHOCOLATE_CHEESECAKE_SLICE, EDLBlocks.CHOCOLATE_CHEESECAKE);
        registerPieSliceRecipe(registry, "honey_cheesecake", EDLItems.HONEY_CHEESECAKE_SLICE, EDLBlocks.HONEY_CHEESECAKE);
        registerPieSliceRecipe(registry, "glow_berry_cheesecake", EDLItems.GLOW_BERRY_CHEESECAKE_SLICE, EDLBlocks.GLOW_BERRY_CHEESECAKE);
        registerPieSliceRecipe(registry, "pumpkin_cheesecake", EDLItems.PUMPKIN_CHEESECAKE_SLICE, EDLBlocks.PUMPKIN_CHEESECAKE);
        registerPieSliceRecipe(registry, "apple_cheesecake", EDLItems.APPLE_CHEESECAKE_SLICE, EDLBlocks.APPLE_CHEESECAKE);
        registerPieSliceRecipe(registry, "sweet_berry_pie", EDLItems.SWEET_BERRY_PIE_SLICE, EDLBlocks.SWEET_BERRY_PIE);
        registerPieSliceRecipe(registry, "glow_berry_pie", EDLItems.GLOW_BERRY_PIE_SLICE, EDLBlocks.GLOW_BERRY_PIE);
        registerPieSliceRecipe(registry, "key_lime_pie", EDLItems.KEY_LIME_PIE_SLICE, EDLBlocks.KEY_LIME_PIE);
        registerPieSliceRecipe(registry, "lemon_meringue_pie", EDLItems.LEMON_MERINGUE_PIE_SLICE, EDLBlocks.LEMON_MERINGUE_PIE);
        registerPieSliceRecipe(registry, "caramel_cheesecake", EDLItems.CARAMEL_CHEESECAKE_SLICE, EDLBlocks.CARAMEL_CHEESECAKE);
        registerPieSliceRecipe(registry, "pumpkin_pie", EDLItems.PUMPKIN_PIE_SLICE, EDLBlocks.PUMPKIN_PIE);
        registerPieSliceRecipe(registry, "meat_pie", EDLItems.MEAT_PIE_SLICE, EDLBlocks.MEAT_PIE);
        registerPieSliceRecipe(registry, "bacon_egg_pie", EDLItems.BACON_EGG_PIE_SLICE, EDLBlocks.BACON_EGG_PIE);
        registerPieSliceRecipe(registry, "steak_pickled_onion_pie", EDLItems.STEAK_PICKLED_ONION_PIE_SLICE, EDLBlocks.STEAK_PICKLED_ONION_PIE);
        registerPieSliceRecipe(registry, "mississippi_mud_pie", EDLItems.MISSISSIPPI_MUD_PIE_SLICE, EDLBlocks.MISSISSIPPI_MUD_PIE);
        registerPieSliceRecipe(registry, "grasshopper_pie", EDLItems.GRASSHOPPER_PIE_SLICE, EDLBlocks.GRASSHOPPER_PIE);
        registerSevenSliceRecipe(registry, "coffee_cake", EDLItems.COFFEE_CAKE_SLICE, EDLBlocks.COFFEE_CAKE);
        registerSevenSliceRecipe(registry, "chocolate_cake", EDLItems.CHOCOLATE_CAKE_SLICE, EDLBlocks.CHOCOLATE_CAKE);
        registerPieSliceRecipe(registry, "kyiv_cake", EDLItems.KYIV_CAKE_SLICE, EDLBlocks.KYIV_CAKE);
        registerPieSliceRecipe(registry, "pumpkin_roll", EDLItems.PUMPKIN_ROLL, EDLBlocks.PUMPKIN_ROLL_FEAST);
        registerPieSliceRecipe(registry, "quiche", EDLItems.QUICHE_SLICE, EDLBlocks.QUICHE);
        registerPieSliceRecipe(registry, "milk_tart", EDLItems.MILK_TART_SLICE, EDLBlocks.MILK_TART);
        registerPieSliceRecipe(registry, "tarte_tatin", EDLItems.TARTE_TATIN_SLICE, EDLBlocks.TARTE_TATIN);
        registerPieSliceRecipe(registry, "panforte", EDLItems.PANFORTE_SLICE, EDLBlocks.PANFORTE);
        registerSevenSliceRecipe(registry, "lemon_cucumber_cake", EDLItems.LEMON_CUCUMBER_CAKE_SLICE, EDLBlocks.LEMON_CUCUMBER_CAKE);
        registerSevenSliceRecipe(registry, "melon_layer_cake", EDLItems.MELON_LAYER_CAKE_SLICE, EDLBlocks.MELON_LAYER_CAKE);
        registerPieSliceRecipe(registry, "pavlova", EDLItems.PAVLOVA_SLICE, EDLBlocks.PAVLOVA);
    }

    private static void registerPieSliceRecipe(IForgeRegistry<IRecipe> registry, String name,
                                               EDLItems.ItemDefinition slice,
                                               EDLBlocks.BlockDefinition pie) {
        if (!slice.isRegistered() || pie.getItemBlock() == null) {
            return;
        }
        registerShaped(registry, name + "_from_slices", pie.stack(1), "ff ", "ff ", 'f', slice.getItem());
    }

    private static void registerSevenSliceRecipe(IForgeRegistry<IRecipe> registry, String name,
                                                 EDLItems.ItemDefinition slice,
                                                 EDLBlocks.BlockDefinition cake) {
        if (!slice.isRegistered() || cake.getItemBlock() == null) {
            return;
        }
        registerShapeless(registry, name + "_from_slices", cake.stack(1),
            slice.getItem(), slice.getItem(), slice.getItem(), slice.getItem(),
            slice.getItem(), slice.getItem(), slice.getItem());
    }

    private static void registerShapeless(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... inputs) {
        if (result.isEmpty() || hasNullInput(inputs)) {
            return;
        }

        ResourceLocation id = new ResourceLocation(ExtraDelightLegacy.MODID, name);
        ShapelessOreRecipe recipe = new ShapelessOreRecipe(id, result, inputs);
        recipe.setRegistryName(id);
        registry.register(recipe);
    }

    private static void registerCitrusBannerRecipes(IForgeRegistry<IRecipe> registry) {
        if (!EDLItems.CITRUS_RIND_BANNER_ITEM.isRegistered()) {
            return;
        }

        registerShapeless(registry, "citrus_pattern", EDLItems.CITRUS_RIND_BANNER_ITEM.stack(1),
            "processedCitrus", Items.PAPER);
        ResourceLocation id = new ResourceLocation(ExtraDelightLegacy.MODID, "citrus_banner");
        CitrusBannerRecipe recipe = new CitrusBannerRecipe();
        recipe.setRegistryName(id);
        registry.register(recipe);
    }

    private static void registerStorageBlock(IForgeRegistry<IRecipe> registry, String name,
                                             EDLItems.ItemDefinition material,
                                             EDLBlocks.BlockDefinition storageBlock) {
        if (!material.isRegistered() || storageBlock.getItemBlock() == null) {
            return;
        }

        registerShaped3x3(registry, name + "_to_block", storageBlock.stack(1), material.getItem());
        registerShapeless(registry, name + "_from_block", material.stack(9), storageBlock.getItemBlock());
    }

    private static void registerStorageBlock(IForgeRegistry<IRecipe> registry, String name,
                                             EDLBlocks.BlockDefinition material,
                                             EDLBlocks.BlockDefinition storageBlock) {
        if (material.getItemBlock() == null || storageBlock.getItemBlock() == null) {
            return;
        }

        registerShaped3x3(registry, name + "_to_block", storageBlock.stack(1), material.getItemBlock());
        registerShapeless(registry, name + "_from_block", material.stack(9), storageBlock.getItemBlock());
    }

    private static void registerStorageBlock(IForgeRegistry<IRecipe> registry, String name,
                                             ItemStack material,
                                             EDLBlocks.BlockDefinition storageBlock) {
        if (material.isEmpty() || storageBlock.getItemBlock() == null) {
            return;
        }

        registerShaped3x3(registry, name + "_to_block", storageBlock.stack(1), material.copy());
        registerShapeless(registry, name + "_from_block", copyWithCount(material, 9), storageBlock.getItemBlock());
    }

    private static ItemStack oreStack(String oreName) {
        List<ItemStack> stacks = OreDictionary.getOres(oreName);
        if (stacks.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return stacks.get(0).copy();
    }

    private static void registerCompact4(IForgeRegistry<IRecipe> registry, String name,
                                         EDLItems.ItemDefinition material,
                                         EDLItems.ItemDefinition compact) {
        if (!material.isRegistered() || !compact.isRegistered()) {
            return;
        }

        registerShaped(registry, name + "_to_compact", compact.stack(1), "##", "##", '#', material.getItem());
        registerShapeless(registry, name + "_from_compact", material.stack(4), compact.getItem());
    }

    private static void registerCompact4(IForgeRegistry<IRecipe> registry, String name,
                                         EDLItems.ItemDefinition material,
                                         EDLBlocks.BlockDefinition compact) {
        if (!material.isRegistered() || compact.getItemBlock() == null) {
            return;
        }

        registerShaped(registry, name + "_to_block", compact.stack(1), "##", "##", '#', material.getItem());
        registerShapeless(registry, name + "_from_block", material.stack(4), compact.getItemBlock());
    }

    private static void registerShaped3x3(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object input) {
        if (result.isEmpty() || input == null) {
            return;
        }

        ResourceLocation id = new ResourceLocation(ExtraDelightLegacy.MODID, name);
        ShapedOreRecipe recipe = new ShapedOreRecipe(id, result, "###", "###", "###", '#', input);
        recipe.setRegistryName(id);
        registry.register(recipe);
    }

    private static void registerToolRecipes(IForgeRegistry<IRecipe> registry) {
        registerShaped(registry, "drying_rack", EDLBlocks.DRYING_RACK.stack(1),
            "WSW", "SSS", "WSW", 'W', "plankWood", 'S', "string");
        registerShaped(registry, "mortar_stone", EDLBlocks.MORTAR_STONE.stack(1),
            "S S", " S ", 'S', Blocks.STONE);
        registerShapeless(registry, "dough_shaping", EDLBlocks.DOUGH_SHAPING.stack(1),
            "stickWood", "plankWood", "flour");
        registerShapeless(registry, "sheet", EDLBlocks.SHEET.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        registerShapeless(registry, "tray", EDLBlocks.TRAY.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, "nuggetIron");
        registerShapeless(registry, "loaf_pan", EDLBlocks.LOAF_PAN.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, "nuggetIron", "nuggetIron");
        registerShapeless(registry, "pie_dish", EDLBlocks.PIE_DISH.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, "nuggetIron", "nuggetIron", "nuggetIron");
        registerShapeless(registry, "square_pan", EDLBlocks.SQUARE_PAN.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron");
        registerShapeless(registry, "baking_stone", EDLBlocks.BAKING_STONE.stack(1), Blocks.STONE_PRESSURE_PLATE);
        registerShapeless(registry, "muffin_tin", EDLBlocks.MUFFIN_TIN.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron");
        registerShapeless(registry, "serving_pot", EDLBlocks.SERVING_POT.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron");
        registerShapeless(registry, "bar_mold", EDLBlocks.BAR_MOLD.stack(1), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
            "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron", "nuggetIron");
        registerShapeless(registry, "melting_pot", EDLBlocks.MELTING_POT.stack(1), cookingPotStack(), cookingPotStack());
        registerShaped(registry, "evaporator", EDLBlocks.EVAPORATOR.stack(1),
            "I I", "PPP", 'I', "nuggetIron", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        registerShaped(registry, "juicer", EDLBlocks.JUICER.stack(1),
            " S ", " B ", "CCC", 'S', "stickWood", 'B', barrelOrChestStack(), 'C', "ingotCopper");
        registerShaped(registry, "tap", EDLBlocks.TAP.stack(1),
            " L ", "WCW", 'L', Blocks.LEVER, 'W', Items.WATER_BUCKET, 'C', "ingotCopper");
        registerShapeless(registry, "keg_block", EDLBlocks.KEG_BLOCK.stack(1),
            Blocks.GLASS, barrelOrChestStack());
        registerShaped(registry, "funnel", EDLBlocks.FUNNEL.stack(1),
            "C C", "CKC", " C ", 'C', "ingotCopper", 'K', EDLBlocks.KEG_BLOCK.stack(1));
        registerShaped(registry, "chiller", EDLBlocks.CHILLER.stack(1),
            "I", "C", "K", 'I', Blocks.IRON_BLOCK, 'C', "farmersDelightCabinet", 'K', EDLBlocks.KEG_BLOCK.stack(1));
        ItemStack canvas = itemStack("farmersdelight:canvas");
        registerShapeless(registry, "yeast_pot_water_bucket", EDLBlocks.YEAST_POT.stack(1),
            Items.FLOWER_POT, canvas, Items.WATER_BUCKET, "edlSweetener");
        registerShapeless(registry, "yeast_pot_water_bottle", EDLBlocks.YEAST_POT.stack(1),
            Items.FLOWER_POT, canvas, new ItemStack(Items.POTIONITEM, 1, 0), "edlSweetener");
        registerShapeless(registry, "vinegar_pot_water_bucket", EDLBlocks.VINEGAR_POT.stack(1),
            Items.FLOWER_POT, canvas, Items.WATER_BUCKET, "edlSweetener", "fruitApple", "fruitApple");
        registerShapeless(registry, "vinegar_pot_water_bottle", EDLBlocks.VINEGAR_POT.stack(1),
            Items.FLOWER_POT, canvas, new ItemStack(Items.POTIONITEM, 1, 0), "edlSweetener", "fruitApple", "fruitApple");
        registerShaped(registry, "vat", EDLBlocks.VAT.stack(1),
            "P P", "PCP", "PPP", 'P', "plankWood", 'C', Blocks.CHEST);
        registerShaped(registry, "vat_lid", EDLBlocks.VAT_LID.stack(1),
            "PPP", " P ", 'P', "plankWood");
        registerChocolateFondueRecipes(registry);
        registerShaped(registry, "pestle_stone", EDLItems.PESTLE_STONE.stack(1),
            "S  ", " I ", 'S', Blocks.STONE, 'I', "stickWood");
        registerShaped(registry, "wooden_spoon", EDLItems.WOODEN_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', "plankWood", 'S', "stickWood");
        registerShaped(registry, "stone_spoon", EDLItems.STONE_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', "stone", 'S', "stickWood");
        registerShaped(registry, "iron_spoon", EDLItems.IRON_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', "ingotIron", 'S', "stickWood");
        registerShaped(registry, "gold_spoon", EDLItems.GOLD_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', "ingotGold", 'S', "stickWood");
        registerShaped(registry, "diamond_spoon", EDLItems.DIAMOND_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', "gemDiamond", 'S', "stickWood");
        registerShaped(registry, "netherite_spoon", EDLItems.NETHERITE_SPOON.stack(1),
            "  W", " S ", "S  ", 'W', itemStack("futuremc:netherite_ingot"), 'S', "stickWood");
        registerShaped(registry, "offset_spatula_wood", EDLItems.OFFSET_SPATULA_WOOD.stack(1),
            "  W", " W ", "S  ", 'W', "plankWood", 'S', "stickWood");
        registerShaped(registry, "offset_spatula_iron", EDLItems.OFFSET_SPATULA_IRON.stack(1),
            "  W", " W ", "S  ", 'W', "ingotIron", 'S', "stickWood");
        registerShaped(registry, "offset_spatula_gold", EDLItems.OFFSET_SPATULA_GOLD.stack(1),
            "  W", " W ", "S  ", 'W', "ingotGold", 'S', "stickWood");
        registerShaped(registry, "offset_spatula_diamond", EDLItems.OFFSET_SPATULA_DIAMOND.stack(1),
            "  W", " W ", "S  ", 'W', "gemDiamond", 'S', "stickWood");
        registerShaped(registry, "offset_spatula_netherite", EDLItems.OFFSET_SPATULA_NETHERITE.stack(1),
            "  W", " W ", "S  ", 'W', itemStack("futuremc:netherite_ingot"), 'S', "stickWood");
        registerShaped(registry, "grater", EDLItems.GRATER.stack(1),
            " P ", "PIP", " P ", 'I', "ingotIron", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        registerShaped(registry, "mixing_bowl", EDLBlocks.MIXING_BOWL.stack(1),
            "W W", "W W", " W ", 'W', "plankWood");
        registerShaped(registry, "oven", EDLBlocks.OVEN.stack(1),
            "bBb", "BfB", "BtB", 'b', Items.BRICK, 'B', Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE, 't', Blocks.HARDENED_CLAY);
        registerShaped(registry, "whisk", EDLItems.WHISK.stack(1),
            " I ", "III", " S ", 'I', "nuggetIron", 'S', "stickWood");
        registerShaped(registry, "jar", EDLBlocks.JAR.stack(1),
            " S ", "G G", " G ", 'S', "slabWood", 'G', "blockGlass");
        registerShaped(registry, "pestle_granite", EDLItems.PESTLE_GRANITE.stack(1),
            "S  ", " I ", 'S', new ItemStack(Blocks.STONE, 1, 1), 'I', "stickWood");
        registerShaped(registry, "pestle_diorite", EDLItems.PESTLE_DIORITE.stack(1),
            "S  ", " I ", 'S', new ItemStack(Blocks.STONE, 1, 3), 'I', "stickWood");
        registerShaped(registry, "pestle_andesite", EDLItems.PESTLE_ANDESITE.stack(1),
            "S  ", " I ", 'S', new ItemStack(Blocks.STONE, 1, 5), 'I', "stickWood");
        registerShaped(registry, "pestle_endstone", EDLItems.PESTLE_ENDSTONE.stack(1),
            "S  ", " I ", 'S', Blocks.END_STONE, 'I', "stickWood");
        registerShaped(registry, "pestle_deepslate", EDLItems.PESTLE_DEEPSLATE.stack(1),
            "S  ", " I ", 'S', itemStack("deeperdepths:deepslate"), 'I', "stickWood");
        registerShaped(registry, "pestle_blackstone", EDLItems.PESTLE_BLACKSTONE.stack(1),
            "S  ", " I ", 'S', itemStack("nb:black_stone"), 'I', "stickWood");
        registerShaped(registry, "pestle_basalt", EDLItems.PESTLE_BASALT.stack(1),
            "S  ", " I ", 'S', itemStack("nb:basalt"), 'I', "stickWood");
        registerShaped(registry, "pestle_amethyst", EDLItems.PESTLE_AMETHYST.stack(1),
            "S  ", " I ", 'S', itemStack("deeperdepths:amethyst_block"), 'I', "stickWood");
        registerShaped(registry, "pestle_gilded_blackstone", EDLItems.PESTLE_GILDED_BLACKSTONE.stack(1),
            "S  ", " I ", 'S', itemStack("nb:gilded_blackstone"), 'I', "stickWood");
    }

    private static void registerChocolateFondueRecipes(IForgeRegistry<IRecipe> registry) {
        registerChocolateFondue(registry, "dark_chocolate_fondue_block", EDLBlocks.DARK_CHOCOLATE_FONDUE_BLOCK.stack(1), EDLFluids.DARK_CHOCOLATE_SYRUP.bucketStack());
        registerChocolateFondue(registry, "milk_chocolate_fondue_block", EDLBlocks.MILK_CHOCOLATE_FONDUE_BLOCK.stack(1), EDLFluids.MILK_CHOCOLATE_SYRUP.bucketStack());
        registerChocolateFondue(registry, "white_chocolate_fondue_block", EDLBlocks.WHITE_CHOCOLATE_FONDUE_BLOCK.stack(1), EDLFluids.WHITE_CHOCOLATE_SYRUP.bucketStack());
        registerChocolateFondue(registry, "blood_chocolate_fondue_block", EDLBlocks.BLOOD_CHOCOLATE_FONDUE_BLOCK.stack(1), EDLFluids.BLOOD_CHOCOLATE_SYRUP.bucketStack());
    }

    private static void registerChocolateFondue(IForgeRegistry<IRecipe> registry, String name, ItemStack result, ItemStack syrupBucket) {
        registerShaped(registry, name, result, "s s", "sbs", " c ", 's', "stickWood", 'b', syrupBucket, 'c', "blockCandle");
    }

    private static void registerChocolateBoxRecipes(IForgeRegistry<IRecipe> registry) {
        String[] colors = {
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
        };
        String[] dyeOres = {
            "dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow", "dyeLime", "dyePink", "dyeGray",
            "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen", "dyeRed", "dyeBlack"
        };

        for (int i = 0; i < colors.length; i++) {
            EDLBlocks.BlockDefinition box = EDLBlocks.CHOCOLATE_BOX_BLOCKS.get(i);
            if (box.getItemBlock() == null) {
                continue;
            }
            registerShapeless(registry, colors[i] + "_chocolate_box", box.stack(1),
                "ribbon", Items.PAPER, Blocks.CHEST, dyeOres[i]);
        }
    }

    private static void registerAestheticRecipes(IForgeRegistry<IRecipe> registry) {
        String[] colors = {
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
        };
        int[] dyeMetas = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] blockMetas = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for (int i = 0; i < colors.length; i++) {
            String color = colors[i];
            ItemStack dye = new ItemStack(Items.DYE, 1, dyeMetas[i]);
            ItemStack wool = new ItemStack(Blocks.WOOL, 1, blockMetas[i]);
            ItemStack carpet = new ItemStack(Blocks.CARPET, 1, blockMetas[i]);
            EDLBlocks.BlockDefinition wallpaper = EDLBlocks.WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition oakMoldedWallpaper = EDLBlocks.OAK_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition spruceMoldedWallpaper = EDLBlocks.SPRUCE_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition birchMoldedWallpaper = EDLBlocks.BIRCH_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition darkOakMoldedWallpaper = EDLBlocks.DARK_OAK_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition jungleMoldedWallpaper = EDLBlocks.JUNGLE_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition acaciaMoldedWallpaper = EDLBlocks.ACACIA_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition cinnamonMoldedWallpaper = EDLBlocks.CINNAMON_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition fruitMoldedWallpaper = EDLBlocks.FRUIT_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition crimsonMoldedWallpaper = EDLBlocks.CRIMSON_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition warpedMoldedWallpaper = EDLBlocks.WARPED_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition cherryMoldedWallpaper = EDLBlocks.CHERRY_MOLDED_WALLPAPER_BLOCKS.get(i);
            EDLBlocks.BlockDefinition ribbonBow = EDLBlocks.RIBBON_BOW_BLOCKS.get(i);
            EDLBlocks.BlockDefinition gingham = EDLBlocks.GINGHAM_BLOCKS.get(i);
            EDLBlocks.BlockDefinition ginghamCarpet = EDLBlocks.GINGHAM_CARPET_BLOCKS.get(i);

            registerShapeless(registry, color + "_wallpaper", wallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye);
            registerShapeless(registry, color + "_oak_molded_wallpaper", oakMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 0));
            registerShapeless(registry, color + "_spruce_molded_wallpaper", spruceMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 1));
            registerShapeless(registry, color + "_birch_molded_wallpaper", birchMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 2));
            registerShapeless(registry, color + "_dark_oak_molded_wallpaper", darkOakMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 5));
            registerShapeless(registry, color + "_jungle_molded_wallpaper", jungleMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 3));
            registerShapeless(registry, color + "_acacia_molded_wallpaper", acaciaMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, new ItemStack(Blocks.WOODEN_SLAB, 1, 4));
            registerShapeless(registry, color + "_cinnamon_molded_wallpaper", cinnamonMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, EDLBlocks.CINNAMON_SLAB.stack(1));
            registerShapeless(registry, color + "_fruit_molded_wallpaper", fruitMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye, EDLBlocks.FRUIT_SLAB.stack(1));
            registerShapeless(registry, color + "_crimson_molded_wallpaper", crimsonMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye,
                firstPresentStack("nb:crimson_slab_half", "futuremc:crimson_slab"));
            registerShapeless(registry, color + "_warped_molded_wallpaper", warpedMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye,
                firstPresentStack("nb:warped_slab_half", "futuremc:warped_slab"));
            registerShapeless(registry, color + "_cherry_molded_wallpaper", cherryMoldedWallpaper.stack(4),
                Items.PAPER, Items.PAPER, Items.PAPER, Items.PAPER, dye,
                itemStack("suikecherry:cherry_slab"));
            registerShaped(registry, color + "_ribbon_bow", ribbonBow.stack(1),
                " w ", " w ", "w w", 'w', wool);
            registerShaped(registry, color + "_gingham", gingham.stack(4),
                "cw", "wc", 'c', wool, 'w', new ItemStack(Blocks.WOOL, 1, 0));
            registerShaped(registry, color + "_gingham_carpet", ginghamCarpet.stack(4),
                "cw", "wc", 'c', carpet, 'w', new ItemStack(Blocks.CARPET, 1, 0));
            registerShaped(registry, color + "_gingham_carpet_from_block", ginghamCarpet.stack(3),
                "cc", 'c', gingham.getItemBlock());
            registerShaped(registry, color + "_picnic_basket", EDLBlocks.PICNIC_BASKET_BLOCKS.get(i).stack(1),
                "s", "g", "b", 's', "stickWood", 'g', ginghamCarpet.stack(1), 'b', itemStack("farmersdelight:basket"));
        }

        registerVanillaWoodDecorRecipes(registry);
    }

    private static void registerVanillaWoodDecorRecipes(IForgeRegistry<IRecipe> registry) {
        String[] woods = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia"};
        int[] slabMetas = {0, 1, 2, 5, 3, 4};
        ItemStack[] leaves = {
            new ItemStack(Blocks.LEAVES, 1, 0),
            new ItemStack(Blocks.LEAVES, 1, 1),
            new ItemStack(Blocks.LEAVES, 1, 2),
            new ItemStack(Blocks.LEAVES, 1, 5),
            new ItemStack(Blocks.LEAVES, 1, 3),
            new ItemStack(Blocks.LEAVES2, 1, 0)
        };

        for (int i = 0; i < woods.length; i++) {
            ItemStack slab = new ItemStack(Blocks.WOODEN_SLAB, 1, slabMetas[i]);
            EDLBlocks.BlockDefinition stepStool = EDLBlocks.STEP_STOOL_BLOCKS.get(i);
            EDLBlocks.BlockDefinition spiceRack = EDLBlocks.SPICE_RACK_BLOCKS.get(i);
            EDLBlocks.BlockDefinition spiceRackFull = EDLBlocks.SPICE_RACK_FULL_BLOCKS.get(i);
            EDLBlocks.BlockDefinition wreath = EDLBlocks.WREATH_BLOCKS.get(i);

            registerShaped(registry, woods[i] + "_step_stool", stepStool.stack(1),
                " w ", "s s", "s s", 'w', slab, 's', "stickWood");
            registerShaped(registry, woods[i] + "_spice_rack", spiceRack.stack(1),
                "wsw", "b b", 'w', slab.copy(), 's', "stickWood", 'b', Blocks.IRON_BARS);
            registerShapeless(registry, woods[i] + "_spice_rack_full", spiceRackFull.stack(1),
                spiceRack.getItemBlock(), Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
            registerShaped(registry, woods[i] + "_wreath", wreath.stack(1),
                " l ", "l l", " l ", 'l', leaves[i]);
        }
    }

    private static void registerSpecialDecorRecipes(IForgeRegistry<IRecipe> registry) {
        registerWoodDecorRecipe(registry, "cinnamon", EDLBlocks.CINNAMON_SLAB.stack(1),
            EDLBlocks.CINNAMON_STEP_STOOL, EDLBlocks.CINNAMON_SPICE_RACK, EDLBlocks.CINNAMON_SPICE_RACK_FULL);
        registerWoodDecorRecipe(registry, "fruit", EDLBlocks.FRUIT_SLAB.stack(1),
            EDLBlocks.FRUIT_STEP_STOOL, EDLBlocks.FRUIT_SPICE_RACK, EDLBlocks.FRUIT_SPICE_RACK_FULL);
        registerWoodDecorRecipe(registry, "crimson", firstPresentStack("nb:crimson_slab_half", "futuremc:crimson_slab"),
            EDLBlocks.CRIMSON_STEP_STOOL, EDLBlocks.CRIMSON_SPICE_RACK, EDLBlocks.CRIMSON_SPICE_RACK_FULL);
        registerWoodDecorRecipe(registry, "warped", firstPresentStack("nb:warped_slab_half", "futuremc:warped_slab"),
            EDLBlocks.WARPED_STEP_STOOL, EDLBlocks.WARPED_SPICE_RACK, EDLBlocks.WARPED_SPICE_RACK_FULL);
        registerWoodDecorRecipe(registry, "cherry", itemStack("suikecherry:cherry_slab"),
            EDLBlocks.CHERRY_STEP_STOOL, EDLBlocks.CHERRY_SPICE_RACK, EDLBlocks.CHERRY_SPICE_RACK_FULL);

        registerWreathRecipe(registry, "cinnamon", EDLBlocks.CINNAMON_WREATH, EDLBlocks.CINNAMON_LEAVES.stack(1));
        registerWreathRecipe(registry, "lemon", EDLBlocks.LEMON_WREATH, EDLBlocks.LEMON_LEAVES.stack(1));
        registerWreathRecipe(registry, "lime", EDLBlocks.LIME_WREATH, EDLBlocks.LIME_LEAVES.stack(1));
        registerWreathRecipe(registry, "orange", EDLBlocks.ORANGE_WREATH, EDLBlocks.ORANGE_LEAVES.stack(1));
        registerWreathRecipe(registry, "grapefruit", EDLBlocks.GRAPEFRUIT_WREATH, EDLBlocks.GRAPEFRUIT_LEAVES.stack(1));
        registerWreathRecipe(registry, "hazelnut", EDLBlocks.HAZELNUT_WREATH, EDLBlocks.HAZELNUT_LEAVES.stack(1));
        registerWreathRecipe(registry, "apple", EDLBlocks.APPLE_WREATH, EDLBlocks.APPLE_LEAVES.stack(1));
        registerWreathRecipe(registry, "crimson", EDLBlocks.CRIMSON_WREATH, firstPresentStack("minecraft:nether_wart_block", "nb:crimson_wart"));
        registerWreathRecipe(registry, "warped", EDLBlocks.WARPED_WREATH, firstPresentStack("nb:warped_wart", "futuremc:warped_wart_block"));
        registerWreathRecipe(registry, "cherry", EDLBlocks.CHERRY_WREATH, itemStack("suikecherry:cherry_leaves"));

        registerDriedCornFenceRecipes(registry);
    }

    private static void registerFoodDisplayRecipes(IForgeRegistry<IRecipe> registry) {
        String[] woods = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia"};
        int[] slabMetas = {0, 1, 2, 5, 3, 4};
        for (int i = 0; i < woods.length; i++) {
            registerShaped(registry, woods[i] + "_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(i).stack(1),
                " s ", " S ", " s ",
                's', "stickWood",
                'S', new ItemStack(Blocks.WOODEN_SLAB, 1, slabMetas[i]));
        }

        registerShaped(registry, "cinnamon_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(6).stack(1),
            " s ", " S ", " s ",
            's', "stickWood",
            'S', EDLBlocks.CINNAMON_SLAB.stack(1));
        registerShaped(registry, "fruit_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(7).stack(1),
            " s ", " S ", " s ",
            's', "stickWood",
            'S', EDLBlocks.FRUIT_SLAB.stack(1));
        registerShaped(registry, "crimson_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(8).stack(1),
            " s ", " S ", " s ",
            's', "stickWood",
            'S', firstPresentStack("nb:crimson_slab_half", "futuremc:crimson_slab"));
        registerShaped(registry, "warped_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(9).stack(1),
            " s ", " S ", " s ",
            's', "stickWood",
            'S', firstPresentStack("nb:warped_slab_half", "futuremc:warped_slab"));
        registerShaped(registry, "cherry_food_display", EDLBlocks.FOOD_DISPLAY_BLOCKS.get(10).stack(1),
            " s ", " S ", " s ",
            's', "stickWood",
            'S', itemStack("suikecherry:cherry_slab"));
    }

    private static void registerKnifeBlockRecipes(IForgeRegistry<IRecipe> registry) {
        String[] woods = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia"};
        int[] slabMetas = {0, 1, 2, 5, 3, 4};
        for (int i = 0; i < woods.length; i++) {
            registerShaped(registry, woods[i] + "_knife_block", EDLBlocks.KNIFE_BLOCKS.get(i).stack(1),
                "wkw",
                'w', new ItemStack(Blocks.WOODEN_SLAB, 1, slabMetas[i]),
                'k', "toolKnife");
        }

        registerShaped(registry, "cinnamon_knife_block", EDLBlocks.KNIFE_BLOCKS.get(6).stack(1),
            "wkw", 'w', EDLBlocks.CINNAMON_SLAB.stack(1), 'k', "toolKnife");
        registerShaped(registry, "fruit_knife_block", EDLBlocks.KNIFE_BLOCKS.get(7).stack(1),
            "wkw", 'w', EDLBlocks.FRUIT_SLAB.stack(1), 'k', "toolKnife");
        registerShaped(registry, "crimson_knife_block", EDLBlocks.KNIFE_BLOCKS.get(8).stack(1),
            "wkw", 'w', firstPresentStack("nb:crimson_slab_half", "futuremc:crimson_slab"), 'k', "toolKnife");
        registerShaped(registry, "warped_knife_block", EDLBlocks.KNIFE_BLOCKS.get(9).stack(1),
            "wkw", 'w', firstPresentStack("nb:warped_slab_half", "futuremc:warped_slab"), 'k', "toolKnife");
        registerShaped(registry, "cherry_knife_block", EDLBlocks.KNIFE_BLOCKS.get(10).stack(1),
            "wkw", 'w', itemStack("suikecherry:cherry_slab"), 'k', "toolKnife");
    }

    private static void registerCabinetRecipes(IForgeRegistry<IRecipe> registry) {
        registerShaped(registry, "cinnamon_cabinet", EDLBlocks.CINNAMON_CABINET.stack(1),
            "sss", "t t", "sss",
            's', EDLBlocks.CINNAMON_SLAB.stack(1),
            't', EDLBlocks.CINNAMON_TRAPDOOR.getItemBlock());
        registerShaped(registry, "fruit_cabinet", EDLBlocks.FRUIT_CABINET.stack(1),
            "sss", "t t", "sss",
            's', EDLBlocks.FRUIT_SLAB.stack(1),
            't', EDLBlocks.FRUIT_TRAPDOOR.getItemBlock());
        registerExternalWoodCabinetRecipes(registry, "crimson",
            EDLBlocks.CRIMSON_CABINET,
            EDLBlocks.HALF_CABINET_BLOCKS.get(8),
            EDLBlocks.COUNTER_CABINET_BLOCKS.get(8),
            EDLBlocks.SINK_CABINET_BLOCKS.get(8),
            firstPresentStack("nb:crimson_slab_half", "futuremc:crimson_slab"),
            firstPresentStack("nb:crimson_trapdoor", "futuremc:crimson_trapdoor"));
        registerExternalWoodCabinetRecipes(registry, "warped",
            EDLBlocks.WARPED_CABINET,
            EDLBlocks.HALF_CABINET_BLOCKS.get(9),
            EDLBlocks.COUNTER_CABINET_BLOCKS.get(9),
            EDLBlocks.SINK_CABINET_BLOCKS.get(9),
            firstPresentStack("nb:warped_slab_half", "futuremc:warped_slab"),
            firstPresentStack("nb:warped_trapdoor", "futuremc:warped_trapdoor"));
        registerExternalWoodCabinetRecipes(registry, "cherry",
            EDLBlocks.CHERRY_CABINET,
            EDLBlocks.HALF_CABINET_BLOCKS.get(10),
            EDLBlocks.COUNTER_CABINET_BLOCKS.get(10),
            EDLBlocks.SINK_CABINET_BLOCKS.get(10),
            itemStack("suikecherry:cherry_slab"),
            itemStack("suikecherry:cherry_trapdoor"));

        String[] woods = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia"};
        String[] fdCabinets = {
            "farmersdelight:oak_cabinet",
            "farmersdelight:spruce_cabinet",
            "farmersdelight:birch_cabinet",
            "farmersdelight:dark_oak_cabinet",
            "farmersdelight:jungle_cabinet",
            "farmersdelight:acacia_cabinet"
        };
        for (int i = 0; i < woods.length; i++) {
            ItemStack cabinet = itemStack(fdCabinets[i]);
            registerShapeless(registry, "half_cabinets/" + woods[i] + "_cabinet_to_half",
                EDLBlocks.HALF_CABINET_BLOCKS.get(i).stack(1), cabinet);
            registerShapeless(registry, "half_cabinets/" + woods[i] + "_half_to_cabinet",
                cabinet, EDLBlocks.HALF_CABINET_BLOCKS.get(i).stack(1));
            registerShapeless(registry, "half_cabinets/" + woods[i] + "_cabinet_full_to_counter",
                EDLBlocks.COUNTER_CABINET_BLOCKS.get(i).stack(1), cabinet);
            registerShapeless(registry, "half_cabinets/" + woods[i] + "_cabinet_counter_to_half",
                EDLBlocks.HALF_CABINET_BLOCKS.get(i).stack(1), EDLBlocks.COUNTER_CABINET_BLOCKS.get(i).stack(1));
            registerShapeless(registry, "half_cabinets/" + woods[i] + "_cabinet_to_sink",
                EDLBlocks.SINK_CABINET_BLOCKS.get(i).stack(1), cabinet, EDLBlocks.TAP.stack(1));
        }
        registerShapeless(registry, "half_cabinets/cinnamon_cabinet_to_half",
            EDLBlocks.HALF_CABINET_BLOCKS.get(6).stack(1), EDLBlocks.CINNAMON_CABINET.stack(1));
        registerShapeless(registry, "half_cabinets/cinnamon_half_to_cabinet",
            EDLBlocks.CINNAMON_CABINET.stack(1), EDLBlocks.HALF_CABINET_BLOCKS.get(6).stack(1));
        registerShapeless(registry, "half_cabinets/cinnamon_cabinet_full_to_counter",
            EDLBlocks.COUNTER_CABINET_BLOCKS.get(6).stack(1), EDLBlocks.CINNAMON_CABINET.stack(1));
        registerShapeless(registry, "half_cabinets/cinnamon_cabinet_counter_to_half",
            EDLBlocks.HALF_CABINET_BLOCKS.get(6).stack(1), EDLBlocks.COUNTER_CABINET_BLOCKS.get(6).stack(1));
        registerShapeless(registry, "half_cabinets/cinnamon_cabinet_to_sink",
            EDLBlocks.SINK_CABINET_BLOCKS.get(6).stack(1), EDLBlocks.CINNAMON_CABINET.stack(1), EDLBlocks.TAP.stack(1));
        registerShapeless(registry, "half_cabinets/fruit_cabinet_to_half",
            EDLBlocks.HALF_CABINET_BLOCKS.get(7).stack(1), EDLBlocks.FRUIT_CABINET.stack(1));
        registerShapeless(registry, "half_cabinets/fruit_half_to_cabinet",
            EDLBlocks.FRUIT_CABINET.stack(1), EDLBlocks.HALF_CABINET_BLOCKS.get(7).stack(1));
        registerShapeless(registry, "half_cabinets/fruit_cabinet_full_to_counter",
            EDLBlocks.COUNTER_CABINET_BLOCKS.get(7).stack(1), EDLBlocks.FRUIT_CABINET.stack(1));
        registerShapeless(registry, "half_cabinets/fruit_cabinet_counter_to_half",
            EDLBlocks.HALF_CABINET_BLOCKS.get(7).stack(1), EDLBlocks.COUNTER_CABINET_BLOCKS.get(7).stack(1));
        registerShapeless(registry, "half_cabinets/fruit_cabinet_to_sink",
            EDLBlocks.SINK_CABINET_BLOCKS.get(7).stack(1), EDLBlocks.FRUIT_CABINET.stack(1), EDLBlocks.TAP.stack(1));
    }

    private static void registerExternalWoodCabinetRecipes(IForgeRegistry<IRecipe> registry, String name,
                                                           EDLBlocks.BlockDefinition cabinet,
                                                           EDLBlocks.BlockDefinition halfCabinet,
                                                           EDLBlocks.BlockDefinition counterCabinet,
                                                           EDLBlocks.BlockDefinition sinkCabinet,
                                                           ItemStack slab,
                                                           ItemStack trapdoor) {
        registerShaped(registry, name + "_cabinet", cabinet.stack(1),
            "sss", "t t", "sss", 's', slab, 't', trapdoor);
        registerShapeless(registry, "half_cabinets/" + name + "_cabinet_to_half",
            halfCabinet.stack(1), cabinet.stack(1));
        registerShapeless(registry, "half_cabinets/" + name + "_half_to_cabinet",
            cabinet.stack(1), halfCabinet.stack(1));
        registerShapeless(registry, "half_cabinets/" + name + "_cabinet_full_to_counter",
            counterCabinet.stack(1), cabinet.stack(1));
        registerShapeless(registry, "half_cabinets/" + name + "_cabinet_counter_to_half",
            halfCabinet.stack(1), counterCabinet.stack(1));
        registerShapeless(registry, "half_cabinets/" + name + "_cabinet_to_sink",
            sinkCabinet.stack(1), cabinet.stack(1), EDLBlocks.TAP.stack(1));
    }

    private static void registerWoodDecorRecipe(IForgeRegistry<IRecipe> registry, String name, ItemStack slab,
                                                EDLBlocks.BlockDefinition stepStool,
                                                EDLBlocks.BlockDefinition spiceRack,
                                                EDLBlocks.BlockDefinition spiceRackFull) {
        if (slab.isEmpty() || stepStool.getItemBlock() == null || spiceRack.getItemBlock() == null
            || spiceRackFull.getItemBlock() == null) {
            return;
        }

        registerShaped(registry, name + "_step_stool", stepStool.stack(1),
            " w ", "s s", "s s", 'w', slab.copy(), 's', "stickWood");
        registerShaped(registry, name + "_spice_rack", spiceRack.stack(1),
            "wsw", "b b", 'w', slab.copy(), 's', "stickWood", 'b', Blocks.IRON_BARS);
        registerShapeless(registry, name + "_spice_rack_full", spiceRackFull.stack(1),
            spiceRack.getItemBlock(), Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE);
    }

    private static void registerWreathRecipe(IForgeRegistry<IRecipe> registry, String name,
                                             EDLBlocks.BlockDefinition wreath, ItemStack leaves) {
        if (wreath.getItemBlock() == null || leaves.isEmpty()) {
            return;
        }
        registerShaped(registry, name + "_wreath", wreath.stack(1),
            " l ", "l l", " l ", 'l', leaves);
    }

    private static void registerDriedCornFenceRecipes(IForgeRegistry<IRecipe> registry) {
        ItemStack[] fences = {
            new ItemStack(Blocks.OAK_FENCE),
            new ItemStack(Blocks.SPRUCE_FENCE),
            new ItemStack(Blocks.BIRCH_FENCE),
            new ItemStack(Blocks.DARK_OAK_FENCE),
            new ItemStack(Blocks.JUNGLE_FENCE),
            new ItemStack(Blocks.ACACIA_FENCE),
            EDLBlocks.CINNAMON_FENCE.stack(1),
            EDLBlocks.FRUIT_FENCE.stack(1),
            firstPresentStack("nb:crimson_fence", "futuremc:crimson_fence"),
            firstPresentStack("nb:warped_fence", "futuremc:warped_fence"),
            itemStack("suikecherry:cherry_fence")
        };
        String[] names = {"oak", "spruce", "birch", "dark_oak", "jungle", "acacia", "cinnamon", "fruit", "crimson", "warped", "cherry"};
        for (int i = 0; i < names.length; i++) {
            EDLBlocks.BlockDefinition output = EDLBlocks.DRIED_CORN_FENCE_BLOCKS.get(i);
            ItemStack fence = fences[i];
            if (output.getItemBlock() == null || fence.isEmpty() || EDLItems.DRIED_CORN_HUSK.getItem() == null) {
                continue;
            }
            registerShapeless(registry, names[i] + "_dried_corn_fence", output.stack(1),
                fence.copy(), EDLItems.DRIED_CORN_HUSK.getItem());
            registerShapeless(registry, names[i] + "_dried_corn_fence_back", fence.copy(),
                output.getItemBlock());
        }
    }

    private static void registerCosmeticBuildingBlockRecipes(IForgeRegistry<IRecipe> registry) {
        String[] colors = {
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
        };
        for (int i = 0; i < colors.length; i++) {
            EDLBlocks.BlockDefinition frosted = EDLBlocks.FROSTED_GINGERBREAD_BLOCKS.get(i);
            registerShapeless(registry, colors[i] + "_frosted_gingerbread_block",
                frosted.stack(1), EDLBlocks.GINGERBREAD_COOKIE_BLOCK.stack(1), frostingStack(colors[i]));
        }

        registerBuildingSet(registry, "butter", EDLBlocks.BUTTER_BLOCK, EDLBlocks.BUTTER_SLAB,
            EDLBlocks.BUTTER_STAIRS, null, null, null, null, null, EDLItems.BUTTER.getItem());
        registerBuildingSet(registry, "cheese", EDLBlocks.CHEESE_BLOCK, EDLBlocks.CHEESE_SLAB,
            EDLBlocks.CHEESE_STAIRS, null, null, null, null, null, EDLItems.CHEESE.getItem());
        registerBuildingSet(registry, "dark_chocolate", EDLBlocks.DARK_CHOCOLATE_BLOCK, EDLBlocks.DARK_CHOCOLATE_SLAB,
            EDLBlocks.DARK_CHOCOLATE_STAIRS, EDLBlocks.DARK_CHOCOLATE_FENCE, EDLBlocks.DARK_CHOCOLATE_FENCE_GATE,
            EDLBlocks.DARK_CHOCOLATE_PILLAR, EDLBlocks.DARK_CHOCOLATE_DOOR, EDLBlocks.DARK_CHOCOLATE_TRAPDOOR,
            EDLItems.DARK_CHOCOLATE_BAR.getItem());
        registerBuildingSet(registry, "milk_chocolate", EDLBlocks.MILK_CHOCOLATE_BLOCK, EDLBlocks.MILK_CHOCOLATE_SLAB,
            EDLBlocks.MILK_CHOCOLATE_STAIRS, EDLBlocks.MILK_CHOCOLATE_FENCE, EDLBlocks.MILK_CHOCOLATE_FENCE_GATE,
            EDLBlocks.MILK_CHOCOLATE_PILLAR, EDLBlocks.MILK_CHOCOLATE_DOOR, EDLBlocks.MILK_CHOCOLATE_TRAPDOOR,
            EDLItems.MILK_CHOCOLATE_BAR.getItem());
        registerBuildingSet(registry, "white_chocolate", EDLBlocks.WHITE_CHOCOLATE_BLOCK, EDLBlocks.WHITE_CHOCOLATE_SLAB,
            EDLBlocks.WHITE_CHOCOLATE_STAIRS, EDLBlocks.WHITE_CHOCOLATE_FENCE, EDLBlocks.WHITE_CHOCOLATE_FENCE_GATE,
            EDLBlocks.WHITE_CHOCOLATE_PILLAR, EDLBlocks.WHITE_CHOCOLATE_DOOR, EDLBlocks.WHITE_CHOCOLATE_TRAPDOOR,
            EDLItems.WHITE_CHOCOLATE_BAR.getItem());
        registerBuildingSet(registry, "blood_chocolate", EDLBlocks.BLOOD_CHOCOLATE_BLOCK, EDLBlocks.BLOOD_CHOCOLATE_SLAB,
            EDLBlocks.BLOOD_CHOCOLATE_STAIRS, EDLBlocks.BLOOD_CHOCOLATE_FENCE, EDLBlocks.BLOOD_CHOCOLATE_FENCE_GATE,
            EDLBlocks.BLOOD_CHOCOLATE_PILLAR, EDLBlocks.BLOOD_CHOCOLATE_DOOR, EDLBlocks.BLOOD_CHOCOLATE_TRAPDOOR,
            EDLItems.BLOOD_CHOCOLATE_BAR.getItem());
        registerWoodBuildingSet(registry, "cinnamon", EDLBlocks.CINNAMON_PLANKS, EDLBlocks.CINNAMON_SLAB,
            EDLBlocks.CINNAMON_STAIRS, EDLBlocks.CINNAMON_FENCE, EDLBlocks.CINNAMON_FENCE_GATE,
            EDLBlocks.CINNAMON_BUTTON, EDLBlocks.CINNAMON_PRESSURE_PLATE,
            EDLBlocks.CINNAMON_WOOD, EDLBlocks.STRIPPED_CINNAMON_WOOD,
            EDLBlocks.CINNAMON_LOG, EDLBlocks.STRIPPED_CINNAMON_LOG);
        registerWoodDoorRecipes(registry, "cinnamon", EDLBlocks.CINNAMON_DOOR, EDLBlocks.CINNAMON_TRAPDOOR, EDLBlocks.CINNAMON_PLANKS);
        registerWoodBuildingSet(registry, "fruit", EDLBlocks.FRUIT_PLANKS, EDLBlocks.FRUIT_SLAB,
            EDLBlocks.FRUIT_STAIRS, EDLBlocks.FRUIT_FENCE, EDLBlocks.FRUIT_FENCE_GATE,
            EDLBlocks.FRUIT_BUTTON, EDLBlocks.FRUIT_PRESSURE_PLATE,
            EDLBlocks.FRUIT_WOOD, EDLBlocks.STRIPPED_FRUIT_WOOD,
            EDLBlocks.FRUIT_LOG, EDLBlocks.STRIPPED_FRUIT_LOG);
        registerWoodDoorRecipes(registry, "fruit", EDLBlocks.FRUIT_DOOR, EDLBlocks.FRUIT_TRAPDOOR, EDLBlocks.FRUIT_PLANKS);
    }

    private static void registerWoodDoorRecipes(IForgeRegistry<IRecipe> registry, String name,
                                                EDLBlocks.BlockDefinition door,
                                                EDLBlocks.BlockDefinition trapdoor,
                                                EDLBlocks.BlockDefinition planks) {
        if (door.getItemBlock() == null || trapdoor.getItemBlock() == null || planks.getItemBlock() == null) {
            return;
        }

        registerShaped(registry, name + "_door", door.stack(3),
            "##", "##", "##", '#', planks.getItemBlock());
        registerShaped(registry, name + "_trapdoor", trapdoor.stack(2),
            "###", "###", '#', planks.getItemBlock());
    }

    private static ItemStack frostingStack(String color) {
        switch (color) {
            case "black":
                return EDLItems.FROSTING_BLACK.stack(1);
            case "blue":
                return EDLItems.FROSTING_BLUE.stack(1);
            case "brown":
                return EDLItems.FROSTING_BROWN.stack(1);
            case "cyan":
                return EDLItems.FROSTING_CYAN.stack(1);
            case "gray":
                return EDLItems.FROSTING_GRAY.stack(1);
            case "green":
                return EDLItems.FROSTING_GREEN.stack(1);
            case "light_blue":
                return EDLItems.FROSTING_LIGHT_BLUE.stack(1);
            case "light_gray":
                return EDLItems.FROSTING_LIGHT_GRAY.stack(1);
            case "lime":
                return EDLItems.FROSTING_LIME.stack(1);
            case "magenta":
                return EDLItems.FROSTING_MAGENTA.stack(1);
            case "orange":
                return EDLItems.FROSTING_ORANGE.stack(1);
            case "pink":
                return EDLItems.FROSTING_PINK.stack(1);
            case "purple":
                return EDLItems.FROSTING_PURPLE.stack(1);
            case "red":
                return EDLItems.FROSTING_RED.stack(1);
            case "yellow":
                return EDLItems.FROSTING_YELLOW.stack(1);
            case "white":
            default:
                return EDLItems.FROSTING_WHITE.stack(1);
        }
    }

    private static void registerBuildingSet(IForgeRegistry<IRecipe> registry, String name,
                                            EDLBlocks.BlockDefinition block,
                                            EDLBlocks.BlockDefinition slab,
                                            EDLBlocks.BlockDefinition stairs,
                                            EDLBlocks.BlockDefinition fence,
                                            EDLBlocks.BlockDefinition fenceGate,
                                            EDLBlocks.BlockDefinition pillar,
                                            EDLBlocks.BlockDefinition door,
                                            EDLBlocks.BlockDefinition trapdoor,
                                            Item material) {
        if (material == null || block.getItemBlock() == null || slab.getItemBlock() == null || stairs.getItemBlock() == null) {
            return;
        }
        if ((fence != null && fence.getItemBlock() == null)
            || (fenceGate != null && fenceGate.getItemBlock() == null)
            || (pillar != null && pillar.getItemBlock() == null)
            || (door != null && door.getItemBlock() == null)
            || (trapdoor != null && trapdoor.getItemBlock() == null)) {
            return;
        }

        registerShaped(registry, name + "_block", block.stack(1),
            "###", "###", "###", '#', material);
        registerShapeless(registry, name + "_from_block", new ItemStack(material, 9), block.getItemBlock());
        registerShaped(registry, name + "_slab", slab.stack(6),
            "###", '#', block.getItemBlock());
        registerShaped(registry, name + "_stairs", stairs.stack(4),
            "#  ", "## ", "###", '#', block.getItemBlock());

        if (fence != null && fenceGate != null && pillar != null) {
            registerShaped(registry, name + "_fence", fence.stack(3),
                "#s#", "#s#", '#', block.getItemBlock(), 's', "stickWood");
            registerShaped(registry, name + "_fence_gate", fenceGate.stack(1),
                "s#s", "s#s", '#', block.getItemBlock(), 's', "stickWood");
            registerShaped(registry, name + "_pillar", pillar.stack(3),
                "#", "#", "#", '#', block.getItemBlock());
        }
        if (door != null) {
            registerShaped(registry, name + "_door", door.stack(3),
                "##", "##", "##", '#', block.getItemBlock());
        }
        if (trapdoor != null) {
            registerShaped(registry, name + "_trapdoor", trapdoor.stack(2),
                "###", "###", '#', block.getItemBlock());
        }
    }

    private static void registerWoodBuildingSet(IForgeRegistry<IRecipe> registry, String name,
                                                EDLBlocks.BlockDefinition planks,
                                                EDLBlocks.BlockDefinition slab,
                                                EDLBlocks.BlockDefinition stairs,
                                                EDLBlocks.BlockDefinition fence,
                                                EDLBlocks.BlockDefinition fenceGate,
                                                EDLBlocks.BlockDefinition button,
                                                EDLBlocks.BlockDefinition pressurePlate,
                                                EDLBlocks.BlockDefinition wood,
                                                EDLBlocks.BlockDefinition strippedWood,
                                                EDLBlocks.BlockDefinition log,
                                                EDLBlocks.BlockDefinition strippedLog) {
        if (planks.getItemBlock() == null || slab.getItemBlock() == null || stairs.getItemBlock() == null
            || fence.getItemBlock() == null || fenceGate.getItemBlock() == null || button.getItemBlock() == null
            || pressurePlate.getItemBlock() == null || wood.getItemBlock() == null || strippedWood.getItemBlock() == null
            || log.getItemBlock() == null || strippedLog.getItemBlock() == null) {
            return;
        }

        registerShapeless(registry, name + "_planks", planks.stack(4), log.getItemBlock());
        registerShaped(registry, name + "_wood", wood.stack(3),
            "##", "##", '#', log.getItemBlock());
        registerShaped(registry, "stripped_" + name + "_wood", strippedWood.stack(3),
            "##", "##", '#', strippedLog.getItemBlock());
        registerShaped(registry, name + "_slab", slab.stack(6),
            "###", '#', planks.getItemBlock());
        registerShaped(registry, name + "_stairs", stairs.stack(4),
            "#  ", "## ", "###", '#', planks.getItemBlock());
        registerShaped(registry, name + "_fence", fence.stack(3),
            "#s#", "#s#", '#', planks.getItemBlock(), 's', "stickWood");
        registerShaped(registry, name + "_fence_gate", fenceGate.stack(1),
            "s#s", "s#s", '#', planks.getItemBlock(), 's', "stickWood");
        registerShaped(registry, name + "_pressure_plate", pressurePlate.stack(1),
            "##", '#', planks.getItemBlock());
        registerShapeless(registry, name + "_button", button.stack(1), planks.getItemBlock());
    }

    private static void registerHangingBlockRecipes(IForgeRegistry<IRecipe> registry) {
        ItemStack rope = ropeStack();
        if (rope.isEmpty()) {
            return;
        }

        registerHangingBlockRecipe(registry, "hanging_chili", EDLBlocks.HANGING_CHILI.stack(1), EDLItems.CHILI.stack(1), rope);
        registerHangingBlockRecipe(registry, "hanging_dried_chili", EDLBlocks.HANGING_DRIED_CHILI.stack(1), EDLItems.DRIED_CHILI.stack(1), rope);
        registerHangingBlockRecipe(registry, "hanging_corn", EDLBlocks.HANGING_CORN.stack(1), EDLItems.CORN_ON_COB.stack(1), rope);
        registerHangingBlockRecipe(registry, "hanging_garlic", EDLBlocks.HANGING_GARLIC.stack(1), EDLItems.GARLIC.stack(1), rope);
        registerShaped(registry, "hanging_mint", EDLBlocks.HANGING_MINT.stack(1),
            "C C", " R ", "C C", 'C', EDLItems.MINT.stack(1), 'R', rope.copy());
        registerHangingBlockRecipe(registry, "hanging_onions", EDLBlocks.HANGING_ONIONS.stack(1), optionalStack(ModItems.ONION), rope);
        registerShaped(registry, "hanging_ham", EDLBlocks.HANGING_HAM.stack(1),
            "R", "C", 'R', rope.copy(), 'C', itemStack("farmersdelight:ham"));
    }

    private static void registerHangingBlockRecipe(IForgeRegistry<IRecipe> registry, String name, ItemStack result, ItemStack crop, ItemStack rope) {
        if (crop.isEmpty() || rope.isEmpty()) {
            return;
        }

        registerShaped(registry, name, result, "CCC", "CRC", "CCC", 'C', crop, 'R', rope.copy());
    }

    private static void registerHangingCuttingRecipes() {
        ItemStack rope = ropeStack();
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_chili",
            EDLBlocks.HANGING_CHILI.stack(1),
            null,
            new ItemStack[]{EDLItems.CHILI.stack(8), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_corn",
            EDLBlocks.HANGING_CORN.stack(1),
            null,
            new ItemStack[]{EDLItems.CORN_ON_COB.stack(8), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_dried_chili",
            EDLBlocks.HANGING_DRIED_CHILI.stack(1),
            null,
            new ItemStack[]{EDLItems.DRIED_CHILI.stack(8), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_garlic",
            EDLBlocks.HANGING_GARLIC.stack(1),
            null,
            new ItemStack[]{EDLItems.GARLIC.stack(8), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_mint",
            EDLBlocks.HANGING_MINT.stack(1),
            null,
            new ItemStack[]{EDLItems.MINT.stack(4), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_onions",
            EDLBlocks.HANGING_ONIONS.stack(1),
            null,
            new ItemStack[]{optionalStack(ModItems.ONION, 8), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
        registerCuttingWithToolsSkippingMissingOutputs(
            "cutting/hanging_ham",
            EDLBlocks.HANGING_HAM.stack(1),
            null,
            new ItemStack[]{itemStack("farmersdelight:ham", 1), copyWithCount(rope, 1)},
            new float[]{1.0F, 0.5F}
        );
    }

    private static void registerShaped(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... recipe) {
        if (result.isEmpty() || hasNullInput(recipe)) {
            return;
        }

        ResourceLocation id = new ResourceLocation(ExtraDelightLegacy.MODID, name);
        ShapedOreRecipe shaped = new ShapedOreRecipe(id, result, recipe);
        shaped.setRegistryName(id);
        registry.register(shaped);
    }

    private static boolean hasNullInput(Object[] inputs) {
        for (Object input : inputs) {
            if (input == null) {
                return true;
            }
            if (input instanceof ItemStack && ((ItemStack) input).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void registerSmelting(String name, EDLItems.ItemDefinition input, EDLItems.ItemDefinition output, float experience) {
        if (!input.isRegistered() || !output.isRegistered()) {
            return;
        }

        GameRegistry.addSmelting(input.stack(1), output.stack(1), experience);
    }

    private static void registerSmelting(Item input, ItemStack output, float experience) {
        if (input == null || output.isEmpty()) {
            return;
        }

        registerSmelting(new ItemStack(input), output, experience);
    }

    private static void registerSmelting(ItemStack input, ItemStack output, float experience) {
        if (input.isEmpty() || output.isEmpty()) {
            return;
        }

        GameRegistry.addSmelting(input, output, experience);
    }

    private static void registerButcherySmeltingRecipes() {
        registerButcherySmelting(EDLItems.BEEF_SCRAPS, EDLItems.COOKED_BEEF_SCRAPS);
        registerButcherySmelting(EDLItems.GROUND_BEEF, EDLItems.COOKED_GROUND_BEEF);
        registerButcherySmelting(EDLItems.CUBED_BEEF, EDLItems.COOKED_CUBED_BEEF);
        registerButcherySmelting(EDLItems.BEEF_RIBS, EDLItems.COOKED_BEEF_RIBS);
        registerButcherySmelting(EDLItems.BEEF_ROAST, EDLItems.COOKED_BEEF_ROAST);
        registerButcherySmelting(EDLItems.BEEF_STEWMEAT, EDLItems.COOKED_BEEF_STEWMEAT);
        registerButcherySmelting(EDLItems.OXTAIL, EDLItems.COOKED_OXTAIL);
        registerButcherySmelting(EDLItems.TONGUE, EDLItems.COOKED_TONGUE);
        registerButcherySmelting(EDLItems.PORK_SCRAPS, EDLItems.COOKED_PORK_SCRAPS);
        registerButcherySmelting(EDLItems.GROUND_PORK, EDLItems.COOKED_GROUND_PORK);
        registerButcherySmelting(EDLItems.CUBED_PORK, EDLItems.COOKED_CUBED_PORK);
        registerButcherySmelting(EDLItems.PORK_RIBS, EDLItems.COOKED_PORK_RIBS);
        registerButcherySmelting(EDLItems.PORK_ROAST, EDLItems.COOKED_PORK_ROAST);
        registerButcherySmelting(EDLItems.PORK_STEWMEAT, EDLItems.COOKED_PORK_STEWMEAT);
        registerButcherySmelting(EDLItems.LAMB_SCRAPS, EDLItems.COOKED_LAMB_SCRAPS);
        registerButcherySmelting(EDLItems.GROUND_LAMB, EDLItems.COOKED_GROUND_LAMB);
        registerButcherySmelting(EDLItems.CUBED_LAMB, EDLItems.COOKED_CUBED_LAMB);
        registerButcherySmelting(EDLItems.LAMB_RIBS, EDLItems.COOKED_LAMB_RIBS);
        registerButcherySmelting(EDLItems.LAMB_ROAST, EDLItems.COOKED_LAMB_ROAST);
        registerButcherySmelting(EDLItems.LAMB_STEWMEAT, EDLItems.COOKED_LAMB_STEWMEAT);
        registerButcherySmelting(EDLItems.CHICKEN_BREAST, EDLItems.COOKED_CHICKEN_BREAST);
        registerButcherySmelting(EDLItems.CUBED_CHICKEN, EDLItems.COOKED_CUBED_CHICKEN);
        registerButcherySmelting(EDLItems.GROUND_CHICKEN, EDLItems.COOKED_GROUND_CHICKEN);
        registerButcherySmelting(EDLItems.CHICKEN_LEG, EDLItems.COOKED_CHICKEN_LEG);
        registerButcherySmelting(EDLItems.CHICKEN_SCRAPS, EDLItems.COOKED_CHICKEN_SCRAPS);
        registerButcherySmelting(EDLItems.CHICKEN_THIGH, EDLItems.COOKED_CHICKEN_THIGH);
        registerButcherySmelting(EDLItems.CHICKEN_WING, EDLItems.COOKED_CHICKEN_WING);
        registerButcherySmelting(EDLItems.CHICKEN_STEWMEAT, EDLItems.COOKED_CHICKEN_STEWMEAT);
        registerButcherySmelting(EDLItems.GOAT_CHOP, EDLItems.COOKED_GOAT_CHOP);
        registerButcherySmelting(EDLItems.GOAT_RIBS, EDLItems.COOKED_GOAT_RIBS);
        registerButcherySmelting(EDLItems.GOAT_ROAST, EDLItems.COOKED_GOAT_ROAST);
        registerButcherySmelting(EDLItems.GOAT_SCRAPS, EDLItems.COOKED_GOAT_SCRAPS);
        registerButcherySmelting(EDLItems.GOAT_STEWMEAT, EDLItems.COOKED_GOAT_STEWMEAT);
        registerButcherySmelting(EDLItems.GROUND_GOAT, EDLItems.COOKED_GROUND_GOAT);
        registerButcherySmelting(EDLItems.CUBED_GOAT, EDLItems.COOKED_CUBED_GOAT);
        registerButcherySmelting(EDLItems.RABBIT_SADDLE, EDLItems.COOKED_RABBIT_SADDLE);
        registerButcherySmelting(EDLItems.RABBIT_THIGH, EDLItems.COOKED_RABBIT_THIGH);
        registerButcherySmelting(EDLItems.RABBIT_LEG, EDLItems.COOKED_RABBIT_LEG);
        registerButcherySmelting(EDLItems.RABBIT_SCRAPS, EDLItems.COOKED_RABBIT_SCRAPS);
        registerButcherySmelting(EDLItems.RABBIT_STEWMEAT, EDLItems.COOKED_RABBIT_STEWMEAT);
        registerButcherySmelting(EDLItems.GROUND_RABBIT, EDLItems.COOKED_GROUND_RABBIT);
        registerButcherySmelting(EDLItems.CUBED_RABBIT, EDLItems.COOKED_CUBED_RABBIT);
        registerButcherySmelting(EDLItems.BRAIN, EDLItems.COOKED_BRAIN);
        registerButcherySmelting(EDLItems.HEART, EDLItems.COOKED_HEART);
        registerButcherySmelting(EDLItems.KIDNEY, EDLItems.COOKED_KIDNEY);
        registerButcherySmelting(EDLItems.LIVER, EDLItems.COOKED_LIVER);
        registerButcherySmelting(EDLItems.LUNG, EDLItems.COOKED_LUNG);
        registerButcherySmelting(EDLItems.STOMACH, EDLItems.COOKED_STOMACH);
        registerButcherySmelting(EDLItems.TRIPE, EDLItems.COOKED_TRIPE);
        registerButcherySmelting(EDLItems.EYEBALL, EDLItems.COOKED_EYEBALL);
        registerButcherySmelting(EDLItems.SAUSAGE, EDLItems.COOKED_SAUSAGE);
    }

    private static void registerButcheryCraftingRecipes(IForgeRegistry<IRecipe> registry) {
    }

    private static void registerButcherySmelting(EDLItems.ItemDefinition input, EDLItems.ItemDefinition output) {
        registerSmelting("butchery/" + input.getId(), input, output, 0.35F);
    }

    private static void registerDryingRackRecipes() {
        DryingRackRecipeManager.clear();
        if (!EDLBlocks.DRYING_RACK.isRegistered()) {
            return;
        }

        registerDrying(EDLItems.CORN_HUSK.stack(1), EDLItems.DRIED_CORN_HUSK.stack(1), 1000);
        registerDrying(EDLItems.CHILI.stack(1), EDLItems.DRIED_CHILI.stack(1), 1000);
        registerDrying(EDLItems.COFFEE_CHERRIES.stack(1), EDLItems.GREEN_COFFEE.stack(1), 1000);
        registerDryingOre("processedFruit", EDLItems.DRIED_FRUIT.stack(1), 1000);
        registerDrying(EDLItems.RAW_CINNAMON.stack(1), EDLItems.CINNAMON_STICK.stack(1), 1000);
        registerDrying(EDLItems.SEAWEED_PASTE.stack(1), EDLItems.AGAR_SHEETS.stack(1), 1000);
        registerDryingOre("foodFishCooked", EDLItems.FISH_FLAKES.stack(1), 1000);
        registerDryingOre("meat", EDLItems.JERKY.stack(1), 1000);
        DryingRackRecipeManager.register(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE), 1000);
    }

    private static void registerDrying(ItemStack input, ItemStack output, int cookingTime) {
        if (!input.isEmpty() && !output.isEmpty()) {
            DryingRackRecipeManager.register(input, output, cookingTime);
        }
    }

    private static void registerDryingOre(String oreName, ItemStack output, int cookingTime) {
        for (ItemStack input : OreDictionary.getOres(oreName, false)) {
            registerDrying(input, output, cookingTime);
        }
    }

    private static void registerMortarRecipes() {
        MortarRecipeManager.clear();
        if (!EDLBlocks.MORTAR_STONE.isRegistered() || !EDLItems.PESTLE_STONE.isRegistered()) {
            return;
        }

        registerMortar(new ItemStack(Items.WHEAT_SEEDS), EDLItems.FLOUR.stack(1), EDLFluids.OIL.stack(50), 4);
        registerMortarOre("seedCorn", EDLItems.CORN_MEAL.stack(1), EDLFluids.OIL.stack(100), 4);
        registerMortar(EDLItems.ROASTED_PEANUTS.stack(1), ItemStack.EMPTY, EDLFluids.NUT_BUTTER.stack(250), 4);
        registerMortar(EDLItems.ROASTED_COCOA_BEANS.stack(1), EDLItems.COCOA_SOLIDS.stack(1), EDLFluids.COCOA_BUTTER.stack(50), 4);
        registerMortar(EDLItems.COCOA_SOLIDS.stack(1), EDLItems.COCOA_POWDER.stack(2), 4);
        registerMortar(EDLItems.SUNFLOWER_SEEDS.stack(1), ItemStack.EMPTY, EDLFluids.OIL.stack(250), 4);
        registerMortar(EDLItems.SOYBEANS.stack(1), ItemStack.EMPTY, EDLFluids.OIL.stack(250), 4);
        registerMortar(EDLItems.MALLOW_ROOT.stack(1), EDLItems.MALLOW_POWDER.stack(1), 4);
        registerMortar(EDLItems.DRIED_CHILI.stack(1), EDLItems.CHILI_POWDER.stack(1), 4);
        registerMortar(EDLItems.CINNAMON_STICK.stack(1), EDLItems.GROUND_CINNAMON.stack(2), 4);
        registerMortar(EDLItems.AGAR_SHEETS.stack(1), EDLItems.AGAR_AGAR.stack(1), 8);
        registerMortarOre("soybeansSoaked", EDLItems.MASHED_SOYBEANS.stack(1), 4);
        registerMortar(EDLItems.COFFEE_BEANS.stack(1), EDLItems.GROUND_COFFEE.stack(1), 4);
        registerMortarOre("cropCoffeeGreen", ItemStack.EMPTY, EDLFluids.OIL.stack(150), 4);
        registerMortar(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 4, 15), 8);
        registerMortar(new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 3), 8);
        registerMortar(new ItemStack(Items.PUMPKIN_SEEDS), new ItemStack(Items.DYE, 1, 15), EDLFluids.OIL.stack(100), 4);
        registerMortar(new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.DYE, 1, 15), EDLFluids.OIL.stack(100), 4);
        registerMortar(new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Items.DYE, 1, 15), EDLFluids.OIL.stack(100), 4);
        registerSourceDyeMortarRecipes();
    }

    private static void registerSourceDyeMortarRecipes() {
        registerMortar(new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 2, 0), 4);
        registerMortar(new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 2, 4), 4);
        registerMortar(new ItemStack(Items.DYE, 1, 3), new ItemStack(Items.DYE, 2, 3), 4);
        registerMortar(new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 2, 2), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 2, 12), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 2, 7), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 2, 7), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 2, 7), 4);
        registerMortar(EDLBlocks.LIME_PETAL_LITTER.stack(1), new ItemStack(Items.DYE, 2, 10), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 2, 13), 4);
        registerMortar(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 3, 13), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 2, 14), 4);
        registerMortar(EDLBlocks.ORANGE_PETAL_LITTER.stack(1), new ItemStack(Items.DYE, 2, 14), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 2, 9), 4);
        registerMortar(EDLBlocks.GRAPEFRUIT_PETAL_LITTER.stack(1), new ItemStack(Items.DYE, 2, 9), 4);
        registerMortar(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 3, 9), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 2, 1), 4);
        registerMortar(new ItemStack(Items.BEETROOT), new ItemStack(Items.DYE, 2, 1), 4);
        registerMortar(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 2, 1), 4);
        registerMortar(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 3, 1), 4);
        registerMortar(new ItemStack(Items.DYE, 1, 15), new ItemStack(Items.DYE, 2, 15), 4);
        registerMortar(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.DYE, 2, 11), 4);
        registerMortar(EDLBlocks.LEMON_PETAL_LITTER.stack(1), new ItemStack(Items.DYE, 2, 11), 4);
        registerMortar(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 3, 11), 4);
    }

    private static void registerMortar(ItemStack input, ItemStack output, int grinds) {
        if (!input.isEmpty() && !output.isEmpty()) {
            MortarRecipeManager.register(input, output, grinds);
        }
    }

    private static void registerMortar(ItemStack input, ItemStack output, net.minecraftforge.fluids.FluidStack fluidOutput, int grinds) {
        if (!input.isEmpty() && (!output.isEmpty() || fluidOutput != null)) {
            MortarRecipeManager.register(input, output, fluidOutput, grinds);
        }
    }

    private static void registerMortarOre(String oreName, ItemStack output, int grinds) {
        for (ItemStack input : OreDictionary.getOres(oreName, false)) {
            registerMortar(input, output, grinds);
        }
    }

    private static void registerMortarOre(String oreName, ItemStack output, net.minecraftforge.fluids.FluidStack fluidOutput, int grinds) {
        for (ItemStack input : OreDictionary.getOres(oreName, false)) {
            registerMortar(input, output, fluidOutput, grinds);
        }
    }

    private static void registerDoughShapingRecipes() {
        DoughShapingRecipeManager.clear();
        if (!EDLBlocks.DOUGH_SHAPING.isRegistered()) {
            return;
        }

        registerDoughShaping("raw_pasta", "dough", itemStack("farmersdelight:raw_pasta"));
        registerDoughShaping("penne_pasta", "dough", EDLItems.PENNE.stack(1));
        registerDoughShaping("macaroni_pasta", "dough", EDLItems.MACARONI.stack(1));
        registerDoughShaping("corn_flakes", "seedCorn", EDLItems.CORN_FLAKES.stack(1));
        registerDoughShaping("lasagna_noodles", "dough", EDLItems.LASAGNA_NOODLES.stack(1));
        registerDoughShaping("raw_sugar_cookie_alex", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_ALEX.stack(8));
        registerDoughShaping("raw_sugar_cookie_creeper", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_CREEPER.stack(8));
        registerDoughShaping("raw_sugar_cookie_pickaxe", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_PICKAXE.stack(8));
        registerDoughShaping("raw_sugar_cookie_steve", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_STEVE.stack(8));
        registerDoughShaping("raw_sugar_cookie_sword", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_SWORD.stack(8));
        registerDoughShaping("raw_sugar_cookie_villager", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_VILLAGER.stack(8));
        registerDoughShaping("raw_sugar_cookie_diamond", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_DIAMOND.stack(8));
        registerDoughShaping("raw_sugar_cookie_emerald", "cookieDoughSugar", EDLItems.RAW_SUGAR_COOKIE_EMERALD.stack(8));
        registerDoughShaping("raw_gingerbread_alex", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_ALEX.stack(8));
        registerDoughShaping("raw_gingerbread_creeper", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_CREEPER.stack(8));
        registerDoughShaping("raw_gingerbread_pickaxe", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_PICKAXE.stack(8));
        registerDoughShaping("raw_gingerbread_steve", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_STEVE.stack(8));
        registerDoughShaping("raw_gingerbread_sword", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_SWORD.stack(8));
        registerDoughShaping("raw_gingerbread_villager", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_VILLAGER.stack(8));
        registerDoughShaping("raw_gingerbread_diamond", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_DIAMOND.stack(8));
        registerDoughShaping("raw_gingerbread_emerald", "cookieDoughGingerbread", EDLItems.RAW_GINGERBREAD_EMERALD.stack(8));
    }

    private static void registerDoughShaping(String name, String inputOre, ItemStack output) {
        DoughShapingRecipeManager.register(name, inputOre, output);
    }

    private static void registerMixingBowlRecipes() {
        MixingBowlRecipeManager.clear();
        if (!EDLBlocks.MIXING_BOWL.isRegistered()) {
            return;
        }

        registerMixing(
            "butter",
            Collections.emptyList(),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, itemStack("farmersdelight:milk_bottle"))),
            ItemStack.EMPTY,
            "toolWhisk",
            EDLItems.BUTTER.stack(1),
            8
        );
        registerMixing(
            "egg_mix",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, new ItemStack(Items.MILK_BUCKET))),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.EGG_MIX.stack(1),
            8
        );
        registerMixing(
            "sugar_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.SUGAR_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "chocolate_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("cocoaPowder")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "chocolate_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("cocoaPowder")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "nut_butter_cookie_dough",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughSugar")),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.NUT_BUTTER.getFluidId(), 250, EDLItems.PEANUT_BUTTER_BOTTLE.stack(1))),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "nut_butter_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.NUT_BUTTER.getFluidId(), 250, EDLItems.PEANUT_BUTTER_BOTTLE.stack(1))),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1),
            4
        );
        registerHazelnutSpreadMixing("hazelnut_spread_dark", EDLFluids.DARK_CHOCOLATE_SYRUP.getFluidId(), EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerHazelnutSpreadMixing("hazelnut_spread_milk", EDLFluids.MILK_CHOCOLATE_SYRUP.getFluidId(), EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerHazelnutSpreadMixing("hazelnut_spread_white", EDLFluids.WHITE_CHOCOLATE_SYRUP.getFluidId(), EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerMixing(
            "apple_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.APPLE_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "apple_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.APPLE_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "chocolate_chip_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.CHOCOLATE_CHIP_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "chocolate_chip_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.CHOCOLATE_CHIP_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "gingerbread_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.stack(EDLItems.GRATED_GINGER.stack(1)),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.GINGERBREAD_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "gingerbread_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(EDLItems.GRATED_GINGER.stack(1)),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.GINGERBREAD_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "glow_berry_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("cropBerryGlow")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.GLOW_BERRY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "glow_berry_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("cropBerryGlow")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.GLOW_BERRY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "honey_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("honeyBottle")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.HONEY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "honey_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("honeyBottle")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.HONEY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "pumpkin_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("processedPumpkin")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.PUMPKIN_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "pumpkin_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("processedPumpkin")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.PUMPKIN_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "sweet_berry_cookie_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieDoughSugar"),
                MixingBowlIngredient.ore("cropBerrySweet")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.SWEET_BERRY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMixing(
            "sweet_berry_cookie_dough_full",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("cropBerrySweet")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.SWEET_BERRY_COOKIE_DOUGH.stack(1),
            4
        );
        registerMilkshakeRecipes();
        registerMixing(
            "mayo",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("eggOrYolk")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolWhisk",
            EDLItems.MAYO.stack(1),
            8
        );
        registerMixing(
            "whipped_cream",
            Collections.emptyList(),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, itemStack("farmersdelight:milk_bottle"))),
            new ItemStack(Items.BOWL),
            "toolWhisk",
            EDLItems.WHIPPED_CREAM.stack(1),
            8
        );
        registerFrostingMixingRecipes();
        registerMixing(
            "carrot_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.ore("processedApple")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1))),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.CARROT_SALAD.stack(1),
            8
        );
        registerMixing(
            "egg_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("eggBoiled"),
                MixingBowlIngredient.ore("eggBoiled")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1))),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.EGG_SALAD.stack(1),
            8
        );
        registerMixing(
            "fish_salad",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.COOKED_FISH, 1, 0)),
                MixingBowlIngredient.stack(new ItemStack(Items.COOKED_FISH, 1, 0))
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1))),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.FISH_SALAD.stack(2),
            8
        );
        registerMixing(
            "seaweed_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("cropKelp"),
                MixingBowlIngredient.ore("cropKelp"),
                MixingBowlIngredient.ore("processedCarrot")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.SEAWEED_SALAD.stack(2),
            4
        );
        registerMixing(
            "furikake",
            Arrays.asList(
                MixingBowlIngredient.ore("cropKelp"),
                MixingBowlIngredient.ore("cropKelp"),
                MixingBowlIngredient.ore("fishFlakes"),
                MixingBowlIngredient.ore("fishFlakes")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.FURIKAKE.stack(4),
            4
        );
        registerMixing(
            "pasta_alfredo",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ALFREDO_SAUCE.stack(1)),
                MixingBowlIngredient.ore("foodPastaCooked")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.PASTA_ALFREDO.stack(1),
            2
        );
        registerMixing(
            "buttered_pasta",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.COOKED_PASTA.stack(1)),
                MixingBowlIngredient.ore("butter")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.BUTTERED_PASTA.stack(1),
            2
        );
        registerMixing(
            "chicken_alfredo",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ALFREDO_SAUCE.stack(1)),
                MixingBowlIngredient.ore("foodPastaCooked"),
                MixingBowlIngredient.ore("foodChickenCooked")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.CHICKEN_ALFREDO.stack(1),
            2
        );
        registerMixing(
            "pasta_tomato",
            Arrays.asList(
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.ore("foodPastaCooked")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.PASTA_TOMATO.stack(1),
            2
        );
        registerMixing(
            "cactus_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("foodCactusCooked"),
                MixingBowlIngredient.ore("processedTomato"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("cheese")
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.CACTUS_SALAD.stack(1),
            2
        );
        registerMixing(
            "cucumber_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("processedGinger"),
                MixingBowlIngredient.ore("soySauce")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.CUCUMBER_SALAD.stack(2),
            2
        );
        registerMixing(
            "apple_slaw",
            Arrays.asList(
                MixingBowlIngredient.stack(itemStack("farmersdelight:cabbage")),
                MixingBowlIngredient.ore("processedApple")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.APPLE_SLAW.stack(1),
            4
        );
        registerMixing(
            "citrus_onion_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("processedOrange"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.ore("processedLime")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.ORANGE_JUICE.getFluidId(), 250, EDLItems.ORANGE_JUICE.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.CITRUS_ONION_SALAD.stack(3),
            2
        );
        registerMixing(
            "grapefruit_beetroot_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("processedBeetroot"),
                MixingBowlIngredient.ore("salt"),
                MixingBowlIngredient.ore("honeyBottle")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1))),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.GRAPEFRUIT_BEETROOT_SALAD.stack(2),
            2
        );
        registerMixing(
            "gazpacho",
            Arrays.asList(
                MixingBowlIngredient.ore("processedTomato"),
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.stack(EDLBlocks.BREADCRUMBS.stack(1))
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.GAZPACHO.stack(1),
            8
        );
        registerMixing(
            "shirazi_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedTomato"),
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("cropMint")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.LEMON_JUICE.getFluidId(), 250, EDLItems.LEMON_JUICE.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.SHIRAZI_SALAD.stack(1),
            2
        );
        registerMixing(
            "morkovcha",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("chiliPowder"),
                MixingBowlIngredient.ore("salt")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.MORKOVCHA.stack(3),
            8
        );
        registerMixing(
            "thai_melon_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedMelon"),
                MixingBowlIngredient.ore("cropPeanutCooked"),
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedGinger"),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("fishSauce")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.LIME_JUICE.getFluidId(), 250, EDLItems.LIME_JUICE.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.THAI_MELON_SALAD.stack(4),
            4
        );
        registerMixing(
            "melon_gazpacho",
            Arrays.asList(
                MixingBowlIngredient.ore("processedMelon"),
                MixingBowlIngredient.ore("processedTomato"),
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.ore("salt"),
                MixingBowlIngredient.stack(EDLBlocks.BREADCRUMBS.stack(1))
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.MELON_GAZPACHO.stack(4),
            4
        );
        registerMixing(
            "lemonade",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("iceCubes"),
                MixingBowlIngredient.ore("processedLemon"),
                MixingBowlIngredient.ore("processedLemon")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 750, new ItemStack(Items.WATER_BUCKET)),
                new MixingBowlFluidIngredient(EDLFluids.LEMON_JUICE.getFluidId(), 250, EDLItems.LEMON_JUICE.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE, 4),
            "toolSpoon",
            EDLItems.LEMONADE.stack(4),
            8
        );
        registerMixing(
            "limeade",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("iceCubes"),
                MixingBowlIngredient.ore("processedLime"),
                MixingBowlIngredient.ore("processedLime")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 750, new ItemStack(Items.WATER_BUCKET)),
                new MixingBowlFluidIngredient(EDLFluids.LIME_JUICE.getFluidId(), 250, EDLItems.LIME_JUICE.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE, 4),
            "toolSpoon",
            EDLItems.LIMEADE.stack(4),
            8
        );
        registerMixing(
            "orangeade",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("iceCubes"),
                MixingBowlIngredient.ore("processedOrange"),
                MixingBowlIngredient.ore("processedOrange")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 500, new ItemStack(Items.WATER_BUCKET)),
                new MixingBowlFluidIngredient(EDLFluids.ORANGE_JUICE.getFluidId(), 500, EDLItems.ORANGE_JUICE.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE, 4),
            "toolSpoon",
            EDLItems.ORANGEADE.stack(4),
            8
        );
        registerMixing(
            "beet_mint_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("processedBeetroot"),
                MixingBowlIngredient.ore("processedBeetroot"),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "",
            EDLItems.BEET_MINT_SALAD.stack(2),
            4
        );
        registerMixing(
            "coffee_jelly",
            Arrays.asList(
                MixingBowlIngredient.ore("gelatin"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.COFFEE.getFluidId(), 250, EDLItems.COFFEE.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.WHIPPED_CREAM.getFluidId(), 250, EDLItems.WHIPPED_CREAM.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.COFFEE_JELLY.stack(2),
            4
        );
        registerMixing(
            "eton_mess",
            Arrays.asList(
                MixingBowlIngredient.ore("meringue"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("meringue"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("zestLemon"),
                MixingBowlIngredient.ore("processedOrange")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.WHIPPED_CREAM.getFluidId(), 250, EDLItems.WHIPPED_CREAM.stack(1))),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.ETON_MESS.stack(1),
            8
        );
        registerMixing(
            "candy_bar_salad",
            Arrays.asList(
                MixingBowlIngredient.ore("chocolateBar"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("custard"),
                MixingBowlIngredient.ore("marshmallow")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.WHIPPED_CREAM.getFluidId(), 250, EDLItems.WHIPPED_CREAM.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.CARAMEL_SAUCE.getFluidId(), 250, EDLItems.CARAMEL_SAUCE.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.CANDY_BAR_SALAD.stack(1),
            4
        );
        registerMixing(
            "dark_chocolate_dipped_coffee_bean",
            Arrays.asList(MixingBowlIngredient.ore("chocolateBarDark"), MixingBowlIngredient.ore("coffeeBeans")),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.DARK_CHOCOLATE_DIPPED_COFFEE_BEAN.stack(1),
            4
        );
        registerMixing(
            "milk_chocolate_dipped_coffee_bean",
            Arrays.asList(MixingBowlIngredient.ore("chocolateBarMilk"), MixingBowlIngredient.ore("coffeeBeans")),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.MILK_CHOCOLATE_DIPPED_COFFEE_BEAN.stack(1),
            4
        );
        registerMixing(
            "white_chocolate_dipped_coffee_bean",
            Arrays.asList(MixingBowlIngredient.ore("chocolateBarWhite"), MixingBowlIngredient.ore("coffeeBeans")),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.WHITE_CHOCOLATE_DIPPED_COFFEE_BEAN.stack(1),
            4
        );
        registerMixing(
            "gummies",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("gelatin"),
                MixingBowlIngredient.ore("gelatin"),
                MixingBowlIngredient.ore("dyeRed"),
                MixingBowlIngredient.ore("dyeRed")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 100, new ItemStack(Items.WATER_BUCKET))),
            ItemStack.EMPTY,
            "",
            EDLItems.GUMMIES.stack(4),
            4
        );
        registerMixing(
            "dark_chocolate_syrup_bottle",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cocoaSolids"),
                MixingBowlIngredient.ore("cocoaSolids")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.COCOA_BUTTER.getFluidId(), 100, EDLItems.COCOA_BUTTER_BOTTLE.stack(1))),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE.stack(1),
            4
        );
        registerMixing(
            "milk_chocolate_syrup_bottle",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cocoaSolids")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.COCOA_BUTTER.getFluidId(), 100, EDLItems.COCOA_BUTTER_BOTTLE.stack(1)),
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 50, itemStack("farmersdelight:milk_bottle"))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE.stack(1),
            4
        );
        registerMixing(
            "white_chocolate_syrup_bottle",
            Collections.singletonList(MixingBowlIngredient.ore("edlSweetener")),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.COCOA_BUTTER.getFluidId(), 100, EDLItems.COCOA_BUTTER_BOTTLE.stack(1)),
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 100, itemStack("farmersdelight:milk_bottle"))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE.stack(1),
            4
        );
        registerMixing(
            "blood_chocolate_syrup_bottle",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cocoaSolids")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.COCOA_BUTTER.getFluidId(), 100, EDLItems.COCOA_BUTTER_BOTTLE.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.BLOOD.getFluidId(), 50, EDLItems.BLOOD.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE.stack(1),
            4
        );
        registerMixing(
            "hot_wings",
            Arrays.asList(
                MixingBowlIngredient.ore("foodChickenWingCooked"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("hotSauce")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "",
            EDLItems.HOT_WINGS.stack(1),
            2
        );
        registerMixing(
            "stiff_peaks",
            Collections.emptyList(),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.EGG_WHITE.getFluidId(), 250, EDLItems.EGG_WHITE.stack(1))),
            new ItemStack(Items.BOWL),
            "toolWhisk",
            EDLItems.STIFF_PEAKS.stack(1),
            8
        );
        registerMixing(
            "aioli_from_scratch",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("processedGarlic")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolWhisk",
            EDLItems.AIOLI.stack(1),
            8
        );
        registerMixing(
            "aioli_cheaty",
            Collections.singletonList(MixingBowlIngredient.ore("processedGarlic")),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1))),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolWhisk",
            EDLItems.AIOLI.stack(1),
            8
        );
        registerMixing(
            "dalgona_coffee",
            Arrays.asList(
                MixingBowlIngredient.ore("groundCoffee"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 50, new ItemStack(Items.WATER_BUCKET)),
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, itemStack("farmersdelight:milk_bottle"))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolWhisk",
            EDLItems.DALGONA_COFFEE.stack(1),
            8
        );
        registerMixing(
            "potato_salad",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.BAKED_POTATO)),
                MixingBowlIngredient.stack(new ItemStack(Items.BAKED_POTATO)),
                MixingBowlIngredient.ore("processedOnion")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.MAYO.getFluidId(), 250, EDLItems.MAYO.stack(1))),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.POTATO_SALAD.stack(2),
            8
        );
        registerMixing(
            "aglio_e_olio",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.COOKED_PASTA.stack(1)),
                MixingBowlIngredient.stack(EDLItems.ROASTED_GARLIC.stack(1)),
                MixingBowlIngredient.ore("processedChili"),
                MixingBowlIngredient.ore("cheese")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1))),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLItems.AGLIO_E_OLIO.stack(1),
            2
        );
        registerMixing(
            "marshmallow",
            Arrays.asList(
                MixingBowlIngredient.ore("dustMallowRoot"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("egg")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 250, new ItemStack(Items.WATER_BUCKET))),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.MARSHMALLOW.stack(4),
            4
        );
        registerMixing(
            "nougat",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("egg"),
                MixingBowlIngredient.ore("nutRoasted")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 250, new ItemStack(Items.WATER_BUCKET))),
            ItemStack.EMPTY,
            "toolSpoon",
            EDLItems.NOUGAT.stack(4),
            4
        );
        registerMixing(
            "wheat_dough",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_WATER, 1000, new ItemStack(Items.WATER_BUCKET))),
            ItemStack.EMPTY,
            "toolSpoon",
            itemStack("farmersdelight:wheat_dough", 3),
            8
        );
        registerMixing(
            "wheat_dough_egg",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("egg")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            itemStack("farmersdelight:wheat_dough", 3),
            8
        );
        registerMixing(
            "pasta_meatballs",
            Arrays.asList(
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.stack(EDLItems.COOKED_PASTA.stack(1)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:beef_patty"))
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            itemStack("farmersdelight:pasta_with_meatballs"),
            2
        );
        registerMixing(
            "pasta_mutton",
            Arrays.asList(
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.stack(EDLItems.COOKED_PASTA.stack(1)),
                MixingBowlIngredient.stack(new ItemStack(Items.COOKED_MUTTON))
            ),
            Collections.emptyList(),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            itemStack("farmersdelight:pasta_with_mutton_chop"),
            2
        );
        registerMixing(
            "salami_mix",
            Arrays.asList(
                MixingBowlIngredient.ore("foodGroundBeefRaw"),
                MixingBowlIngredient.ore("fat"),
                MixingBowlIngredient.ore("processedGarlic")
            ),
            Collections.emptyList(),
            ItemStack.EMPTY,
            "",
            EDLItems.SALAMI_MIX.stack(8),
            4
        );
        registerMixing(
            "pulled_pork_block",
            Arrays.asList(
                MixingBowlIngredient.ore("foodPorkScrapsCooked"),
                MixingBowlIngredient.ore("foodPorkScrapsCooked"),
                MixingBowlIngredient.ore("foodPorkScrapsCooked"),
                MixingBowlIngredient.ore("foodPorkScrapsCooked")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.BBQ.getFluidId(), 250, EDLItems.BBQ_SAUCE.stack(1))),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLBlocks.PULLED_PORK_BLOCK.stack(1),
            4
        );
        registerFeastMixingRecipes();
    }

    private static void registerFeastMixingRecipes() {
        registerMixing(
            "stuffing_feast",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CROUTONS.stack(1)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("broth"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            Collections.emptyList(),
            EDLBlocks.SERVING_POT.stack(1),
            "toolSpoon",
            EDLBlocks.STUFFING_FEAST.stack(1),
            8
        );
        registerMixing(
            "salad_veg",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCabbage"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.stack(EDLItems.CROUTONS.stack(1)),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("eggBoiled"),
                MixingBowlIngredient.ore("processedVegetable")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLBlocks.SALAD_BLOCK.stack(1),
            8
        );
        registerMixing(
            "salad_meat",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCabbage"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.stack(EDLItems.CROUTONS.stack(1)),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("eggBoiled"),
                MixingBowlIngredient.ore("foodMeatCooked")
            ),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.OIL.getFluidId(), 250, EDLItems.COOKING_OIL.stack(1)),
                new MixingBowlFluidIngredient(EDLFluids.VINEGAR.getFluidId(), 250, EDLItems.VINEGAR.stack(1))
            ),
            new ItemStack(Items.BOWL),
            "toolSpoon",
            EDLBlocks.SALAD_BLOCK.stack(1),
            8
        );
        registerMixing(
            "chili_con_carne_feast",
            Arrays.asList(
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.ore("broth"),
                MixingBowlIngredient.ore("meat"),
                MixingBowlIngredient.ore("chiliPowder"),
                MixingBowlIngredient.ore("processedVegetable"),
                MixingBowlIngredient.ore("processedVegetable")
            ),
            Collections.emptyList(),
            EDLBlocks.SERVING_POT.stack(1),
            "toolSpoon",
            EDLBlocks.CHILI_CON_CARNE_FEAST.stack(1),
            8
        );
        registerMixing(
            "white_chili_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("broth"),
                MixingBowlIngredient.ore("foodChickenCooked"),
                MixingBowlIngredient.ore("processedChili"),
                MixingBowlIngredient.ore("processedVegetable"),
                MixingBowlIngredient.ore("processedVegetable")
            ),
            Collections.emptyList(),
            EDLBlocks.SERVING_POT.stack(1),
            "toolSpoon",
            EDLBlocks.WHITE_CHILI_FEAST.stack(1),
            8
        );
        registerMixing(
            "crisp_rice_treats_block",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CRISP_RICE.stack(1)),
                MixingBowlIngredient.ore("marshmallow"),
                MixingBowlIngredient.ore("butter")
            ),
            Collections.emptyList(),
            EDLBlocks.TRAY.stack(1),
            "toolSpoon",
            EDLBlocks.CRISP_RICE_TREATS_BLOCK.stack(1),
            8
        );
        registerMixing(
            "scotcharoo_block",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CRISP_RICE.stack(1)),
                MixingBowlIngredient.ore("marshmallow"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("butterscotch"),
                MixingBowlIngredient.ore("liquidChocolate"),
                MixingBowlIngredient.ore("nutButter")
            ),
            Collections.emptyList(),
            EDLBlocks.TRAY.stack(1),
            "toolSpoon",
            EDLBlocks.SCOTCHAROO_BLOCK.stack(1),
            8
        );
        registerMelonFruitSaladMixing("melon_fruit_salad", EDLFluids.LEMON_JUICE, EDLItems.LEMON_JUICE);
        registerMelonFruitSaladMixing("melon_fruit_salad_lime", EDLFluids.LIME_JUICE, EDLItems.LIME_JUICE);
    }

    private static void registerMelonFruitSaladMixing(String name, EDLFluids.FluidDefinition juice, EDLItems.ItemDefinition juiceBottle) {
        registerMixing(
            name,
            Arrays.asList(
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedMelon"),
                MixingBowlIngredient.ore("processedOrange"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("mint")
            ),
            Collections.singletonList(new MixingBowlFluidIngredient(juice.getFluidId(), 250, juiceBottle.stack(1))),
            new ItemStack(Items.MELON),
            "toolSpoon",
            EDLBlocks.MELON_FRUIT_SALAD.stack(1),
            2
        );
    }

    private static void registerMixing(String name, java.util.List<MixingBowlIngredient> ingredients,
                                       java.util.List<MixingBowlFluidIngredient> fluids, ItemStack container,
                                       String utensilOre, ItemStack output, int stirs) {
        if (!output.isEmpty() && !hasEmptyIngredientDisplay(ingredients) && !hasEmptyFluidDisplay(fluids)) {
            MixingBowlRecipeManager.register(name, ingredients, fluids, container, utensilOre, output, stirs);
        }
    }

    private static void registerHazelnutSpreadMixing(String name, String chocolateFluidId, ItemStack chocolateDisplay) {
        registerMixing(
            name,
            Collections.singletonList(MixingBowlIngredient.ore("edlSweetener")),
            Arrays.asList(
                new MixingBowlFluidIngredient(chocolateFluidId, 250, chocolateDisplay),
                new MixingBowlFluidIngredient(EDLFluids.NUT_BUTTER.getFluidId(), 250, EDLItems.PEANUT_BUTTER_BOTTLE.stack(1))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.HAZELNUT_SPREAD_BOTTLE.stack(2),
            4
        );
    }

    private static void registerFrostingMixingRecipes() {
        registerFrostingMixing("frosting_black", EDLItems.FROSTING_BLACK, "dyeBlack", true);
        registerFrostingMixing("frosting_blue", EDLItems.FROSTING_BLUE, "dyeBlue", true);
        registerFrostingMixing("frosting_brown", EDLItems.FROSTING_BROWN, "dyeBrown", false);
        registerFrostingMixing("frosting_cyan", EDLItems.FROSTING_CYAN, "dyeCyan", true);
        registerFrostingMixing("frosting_gray", EDLItems.FROSTING_GRAY, "dyeGray", true);
        registerFrostingMixing("frosting_green", EDLItems.FROSTING_GREEN, "dyeGreen", true);
        registerFrostingMixing("frosting_light_blue", EDLItems.FROSTING_LIGHT_BLUE, "dyeLightBlue", true);
        registerFrostingMixing("frosting_light_gray", EDLItems.FROSTING_LIGHT_GRAY, "dyeLightGray", true);
        registerFrostingMixing("frosting_lime", EDLItems.FROSTING_LIME, "dyeLime", true);
        registerFrostingMixing("frosting_magenta", EDLItems.FROSTING_MAGENTA, "dyeMagenta", true);
        registerFrostingMixing("frosting_orange", EDLItems.FROSTING_ORANGE, "dyeOrange", true);
        registerFrostingMixing("frosting_pink", EDLItems.FROSTING_PINK, "dyePink", true);
        registerFrostingMixing("frosting_purple", EDLItems.FROSTING_PURPLE, "dyePurple", true);
        registerFrostingMixing("frosting_red", EDLItems.FROSTING_RED, "dyeRed", true);
        registerFrostingMixing("frosting_white", EDLItems.FROSTING_WHITE, "dyeWhite", true);
        registerFrostingMixing("frosting_yellow", EDLItems.FROSTING_YELLOW, "dyeYellow", true);
    }

    private static void registerFrostingMixing(String name, EDLItems.ItemDefinition output, String dyeOre, boolean usesWhippedCream) {
        registerMixing(
            name,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.BUTTER.stack(1)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore(dyeOre)
            ),
            usesWhippedCream
                ? Collections.singletonList(new MixingBowlFluidIngredient(EDLFluids.WHIPPED_CREAM.getFluidId(), 250, EDLItems.WHIPPED_CREAM.stack(1)))
                : Collections.emptyList(),
            ItemStack.EMPTY,
            "toolSpoon",
            output.stack(4),
            2
        );
    }

    private static void registerMilkshakeRecipes() {
        registerMilkshake("milkshake", EDLItems.MILKSHAKE, Collections.singletonList(MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1))));
        registerMilkshake(
            "apple_milkshake",
            EDLItems.APPLE_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple")
            )
        );
        registerMilkshake(
            "apple_milkshake_ice_cream",
            EDLItems.APPLE_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.APPLE_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "chocolate_milkshake",
            EDLItems.CHOCOLATE_MILKSHAKE,
            Arrays.asList(MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)), MixingBowlIngredient.ore("cocoaPowder"))
        );
        registerMilkshake(
            "chocolate_milkshake_ice_cream",
            EDLItems.CHOCOLATE_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.CHOCOLATE_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "glow_berry_milkshake",
            EDLItems.GLOW_BERRY_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow")
            )
        );
        registerMilkshake(
            "glow_berry_milkshake_ice_cream",
            EDLItems.GLOW_BERRY_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.GLOW_BERRY_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "honey_milkshake",
            EDLItems.HONEY_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle")
            )
        );
        registerMilkshake(
            "honey_milkshake_ice_cream",
            EDLItems.HONEY_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.HONEY_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "pumpkin_milkshake",
            EDLItems.PUMPKIN_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin")
            )
        );
        registerMilkshake(
            "pumpkin_milkshake_ice_cream",
            EDLItems.PUMPKIN_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.PUMPKIN_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "sweet_berry_milkshake",
            EDLItems.SWEET_BERRY_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet")
            )
        );
        registerMilkshake(
            "sweet_berry_milkshake_ice_cream",
            EDLItems.SWEET_BERRY_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.SWEET_BERRY_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "cookie_dough_milkshake",
            EDLItems.COOKIE_DOUGH_MILKSHAKE,
            Arrays.asList(MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)), MixingBowlIngredient.ore("cookieDough"))
        );
        registerMilkshake(
            "cookie_dough_milkshake_ice_cream",
            EDLItems.COOKIE_DOUGH_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.COOKIE_DOUGH_ICE_CREAM.stack(1)))
        );
        registerMilkshake(
            "mint_chip_milkshake",
            EDLItems.MINT_CHIP_MILKSHAKE,
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1)),
                MixingBowlIngredient.ore("chocolateChips"),
                MixingBowlIngredient.ore("cropMint")
            )
        );
        registerMilkshake(
            "mint_chip_milkshake_ice_cream",
            EDLItems.MINT_CHIP_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.MINT_CHIP_ICE_CREAM.stack(1)))
        );
        registerMixing(
            "nut_butter_milkshake",
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.ICE_CREAM.stack(1))),
            Arrays.asList(
                new MixingBowlFluidIngredient(EDLFluids.NUT_BUTTER.getFluidId(), 250, EDLItems.PEANUT_BUTTER_BOTTLE.stack(1)),
                new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, itemStack("farmersdelight:milk_bottle"))
            ),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            EDLItems.NUT_BUTTER_MILKSHAKE.stack(1),
            4
        );
        registerMilkshake(
            "nut_butter_milkshake_ice_cream",
            EDLItems.NUT_BUTTER_MILKSHAKE,
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.NUT_BUTTER_ICE_CREAM.stack(1)))
        );
    }

    private static void registerMilkshake(String name, EDLItems.ItemDefinition output, java.util.List<MixingBowlIngredient> ingredients) {
        registerMixing(
            name,
            ingredients,
            Collections.singletonList(new MixingBowlFluidIngredient(MixingBowlRecipeManager.FLUID_MILK, 250, itemStack("farmersdelight:milk_bottle"))),
            new ItemStack(Items.GLASS_BOTTLE),
            "toolSpoon",
            output.stack(1),
            4
        );
    }

    private static void registerChillerRecipes() {
        ChillerRecipeManager.clear();
        registerChiller(
            "dark_chocolate_bar",
            Collections.emptyList(),
            EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.BAR_MOLD.stack(1),
            EDLItems.DARK_CHOCOLATE_BAR.stack(1),
            400,
            false
        );
        registerChiller(
            "milk_chocolate_bar",
            Collections.emptyList(),
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.BAR_MOLD.stack(1),
            EDLItems.MILK_CHOCOLATE_BAR.stack(1),
            400,
            false
        );
        registerChiller(
            "white_chocolate_bar",
            Collections.emptyList(),
            EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.BAR_MOLD.stack(1),
            EDLItems.WHITE_CHOCOLATE_BAR.stack(1),
            400,
            false
        );
        registerChiller(
            "blood_chocolate_bar",
            Collections.emptyList(),
            EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.BAR_MOLD.stack(1),
            EDLItems.BLOOD_CHOCOLATE_BAR.stack(1),
            400,
            false
        );
        registerIceCreamChillerRecipes();
        registerPopsicleChillerRecipes();
        registerChiller(
            "fudge",
            Arrays.asList(
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("marshmallowFluff"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.FUDGE_BLOCK.stack(1),
            400,
            true
        );
        registerChillerFudge(EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250), "dark");
        registerChillerFudge(EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250), "white");
        registerChillerFudge(EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250), "blood");
        registerChillerTruffles(
            "dark_chocolate_truffle",
            EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250),
            EDLItems.DARK_CHOCOLATE_TRUFFLE.stack(6)
        );
        registerChillerTruffles(
            "milk_chocolate_truffle",
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250),
            EDLItems.MILK_CHOCOLATE_TRUFFLE.stack(6)
        );
        registerChillerTruffles(
            "white_chocolate_truffle",
            EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250),
            EDLItems.WHITE_CHOCOLATE_TRUFFLE.stack(6)
        );
        registerChillerTruffles(
            "blood_chocolate_truffle",
            EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250),
            EDLItems.BLOOD_CHOCOLATE_TRUFFLE.stack(6)
        );
        registerChillerNutButterCups(EDLFluids.DARK_CHOCOLATE_SYRUP.stack(1000), "dark");
        registerChillerNutButterCups(EDLFluids.MILK_CHOCOLATE_SYRUP.stack(1000), "milk");
        registerChillerNutButterCups(EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(1000), "white");
        registerChillerNutButterCups(EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(1000), "blood");
        registerChillerMallowCups(EDLFluids.DARK_CHOCOLATE_SYRUP.stack(1000), "dark");
        registerChillerMallowCups(EDLFluids.MILK_CHOCOLATE_SYRUP.stack(1000), "milk");
        registerChillerMallowCups(EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(1000), "white");
        registerChillerMallowCups(EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(1000), "blood");
        registerChillerToffee(EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250), "dark");
        registerChillerToffee(EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250), "milk");
        registerChillerToffee(EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250), "white");
        registerChillerToffee(EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250), "blood");
        registerChiller(
            "peppermint_bark",
            Arrays.asList(
                MixingBowlIngredient.ore("cookingOil"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("candyMintRed")
            ),
            EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.TRAY.stack(1),
            EDLItems.PEPPERMINT_BARK.stack(4),
            400,
            false
        );
        registerChillerRockyRoad(EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250), "dark");
        registerChillerRockyRoad(EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250), "milk");
        registerChillerRockyRoad(EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250), "white");
        registerChillerRockyRoad(EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250), "blood");
        registerJellyFeastChillerRecipes();
        registerChiller(
            "grapefruit_sorbet",
            Collections.singletonList(MixingBowlIngredient.ore("edlSweetener")),
            EDLFluids.GRAPEFRUIT_JUICE.stack(250),
            new ItemStack(Items.BOWL),
            EDLItems.GRAPEFRUIT_SORBET.stack(1),
            800,
            true
        );
        registerChiller(
            "chocolate_orange",
            Collections.singletonList(MixingBowlIngredient.ore("zestOrange")),
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.CHOCOLATE_ORANGE.stack(1),
            400,
            false
        );
        registerChiller(
            "chocolate_mousse",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLFluids.WHIPPED_CREAM.stack(250),
            new ItemStack(Items.GLASS_BOTTLE, 2),
            EDLItems.CHOCOLATE_MOUSSE.stack(2),
            800,
            true
        );
        registerChiller(
            "fluff_to_marshmallow",
            Collections.emptyList(),
            EDLFluids.MARSHMALLOW_FLUFF.stack(250),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MARSHMALLOW.stack(1),
            400,
            false
        );
        registerChiller(
            "marshmallow_slice",
            Arrays.asList(
                MixingBowlIngredient.ore("cookieSugar"),
                MixingBowlIngredient.ore("jam"),
                MixingBowlIngredient.ore("cookieSugar")
            ),
            EDLFluids.MARSHMALLOW_FLUFF.stack(250),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.MARSHMALLOW_SLICE_FEAST.stack(1),
            800,
            true
        );
        registerChiller(
            "grasshopper_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("dyeLime"),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.stack(EDLItems.MARSHMALLOW_FLUFF_BOTTLE.stack(1)),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            EDLFluids.WHIPPED_CREAM.stack(250),
            itemStack("farmersdelight:pie_crust"),
            EDLBlocks.GRASSHOPPER_PIE.stack(1),
            800,
            true
        );
        registerChiller(
            "mississippi_mud_pie",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CHOCOLATE_CUSTARD.stack(1)),
                MixingBowlIngredient.stack(EDLItems.MARSHMALLOW_FLUFF_BOTTLE.stack(1)),
                MixingBowlIngredient.ore("nutRoasted"),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            EDLFluids.WHIPPED_CREAM.stack(250),
            itemStack("farmersdelight:pie_crust"),
            EDLBlocks.MISSISSIPPI_MUD_PIE.stack(1),
            800,
            true
        );
        registerFilledChocolateBar("dark_chocolate_filled_bar", EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250), EDLItems.DARK_CHOCOLATE_FILLED_BAR);
        registerFilledChocolateBar("milk_chocolate_filled_bar", EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250), EDLItems.MILK_CHOCOLATE_FILLED_BAR);
        registerFilledChocolateBar("white_chocolate_filled_bar", EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250), EDLItems.WHITE_CHOCOLATE_FILLED_BAR);
        registerFilledChocolateBar("blood_chocolate_filled_bar", EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250), EDLItems.BLOOD_CHOCOLATE_FILLED_BAR);
    }

    private static void registerChillerFudge(net.minecraftforge.fluids.FluidStack fluid, String chocolate) {
        registerChiller(
            "fudge_" + chocolate,
            Arrays.asList(
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("marshmallowFluff"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            fluid,
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.FUDGE_BLOCK.stack(1),
            400,
            true
        );
    }

    private static void registerJellyFeastChillerRecipes() {
        registerJellyFeastChiller("jelly_white", "dyeWhite", EDLBlocks.JELLY_WHITE_BLOCK);
        registerJellyFeastChiller("jelly_orange", "dyeOrange", EDLBlocks.JELLY_ORANGE_BLOCK);
        registerJellyFeastChiller("jelly_magenta", "dyeMagenta", EDLBlocks.JELLY_MAGENTA_BLOCK);
        registerJellyFeastChiller("jelly_light_blue", "dyeLightBlue", EDLBlocks.JELLY_LIGHT_BLUE_BLOCK);
        registerJellyFeastChiller("jelly_yellow", "dyeYellow", EDLBlocks.JELLY_YELLOW_BLOCK);
        registerJellyFeastChiller("jelly_lime", "dyeLime", EDLBlocks.JELLY_LIME_BLOCK);
        registerJellyFeastChiller("jelly_pink", "dyePink", EDLBlocks.JELLY_PINK_BLOCK);
        registerJellyFeastChiller("jelly_grey", "dyeGray", EDLBlocks.JELLY_GREY_BLOCK);
        registerJellyFeastChiller("jelly_light_grey", "dyeLightGray", EDLBlocks.JELLY_LIGHT_GREY_BLOCK);
        registerJellyFeastChiller("jelly_cyan", "dyeCyan", EDLBlocks.JELLY_CYAN_BLOCK);
        registerJellyFeastChiller("jelly_purple", "dyePurple", EDLBlocks.JELLY_PURPLE_BLOCK);
        registerJellyFeastChiller("jelly_blue", "dyeBlue", EDLBlocks.JELLY_BLUE_BLOCK);
        registerJellyFeastChiller("jelly_brown", "dyeBrown", EDLBlocks.JELLY_BROWN_BLOCK);
        registerJellyFeastChiller("jelly_green", "dyeGreen", EDLBlocks.JELLY_GREEN_BLOCK);
        registerJellyFeastChiller("jelly_red", "dyeRed", EDLBlocks.JELLY_RED_BLOCK);
        registerJellyFeastChiller("jelly_black", "dyeBlack", EDLBlocks.JELLY_BLACK_BLOCK);
    }

    private static void registerJellyFeastChiller(String name, String dyeOre, EDLBlocks.BlockDefinition output) {
        registerChiller(
            name + "_feast",
            Arrays.asList(
                MixingBowlIngredient.ore(dyeOre),
                MixingBowlIngredient.ore("gelatin"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cropBerrySweet")
            ),
            EDLFluids.WHIPPED_CREAM.stack(250),
            new ItemStack(Items.BOWL),
            output.stack(1),
            800,
            true
        );
    }

    private static void registerFilledChocolateBar(String name, net.minecraftforge.fluids.FluidStack fluid, EDLItems.ItemDefinition output) {
        registerChiller(
            name,
            Collections.singletonList(MixingBowlIngredient.ore("chocolateBarFilling")),
            fluid,
            EDLBlocks.BAR_MOLD.stack(1),
            output.stack(1),
            400,
            false
        );
    }

    private static void registerChillerTruffles(String name, net.minecraftforge.fluids.FluidStack fluid, ItemStack output) {
        registerChiller(
            name + "_frosting",
            Collections.singletonList(MixingBowlIngredient.ore("frosting")),
            fluid,
            EDLBlocks.MUFFIN_TIN.stack(1),
            output,
            400,
            false
        );
        registerChiller(
            name + "_fudge",
            Collections.singletonList(MixingBowlIngredient.ore("fudge")),
            fluid,
            EDLBlocks.MUFFIN_TIN.stack(1),
            output,
            400,
            false
        );
    }

    private static void registerChillerNutButterCups(net.minecraftforge.fluids.FluidStack fluid, String chocolate) {
        registerChiller(
            chocolate + "_peanut_butter_cup",
            Collections.singletonList(MixingBowlIngredient.ore("nutButter")),
            fluid,
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.PEANUT_BUTTER_CUP.stack(6),
            400,
            false
        );
    }

    private static void registerChillerMallowCups(net.minecraftforge.fluids.FluidStack fluid, String chocolate) {
        registerChiller(
            chocolate + "_mallow_cup",
            Collections.singletonList(MixingBowlIngredient.ore("marshmallowFluff")),
            fluid,
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MALLOW_CUP.stack(6),
            400,
            false
        );
    }

    private static void registerChillerToffee(net.minecraftforge.fluids.FluidStack fluid, String chocolate) {
        registerChiller(
            chocolate + "_toffee",
            Arrays.asList(
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("nutRoasted")
            ),
            fluid,
            EDLBlocks.TRAY.stack(1),
            EDLItems.TOFFEE.stack(4),
            400,
            false
        );
    }

    private static void registerChillerRockyRoad(net.minecraftforge.fluids.FluidStack fluid, String chocolate) {
        registerChiller(
            "rocky_road_" + chocolate,
            Arrays.asList(
                MixingBowlIngredient.ore("gummies"),
                MixingBowlIngredient.ore("foodCookie"),
                MixingBowlIngredient.ore("marshmallow"),
                MixingBowlIngredient.ore("nutRoasted")
            ),
            fluid,
            EDLBlocks.TRAY.stack(1),
            EDLItems.ROCKY_ROAD.stack(4),
            800,
            false
        );
    }

    private static void registerPopsicleChillerRecipes() {
        registerChiller(
            "apple_popsicle",
            Collections.emptyList(),
            EDLFluids.APPLE_CIDER.stack(250),
            new ItemStack(Items.STICK, 4),
            EDLItems.APPLE_POPSICLE.stack(4),
            800,
            true
        );
        registerChiller(
            "glow_berry_popsicle",
            Collections.emptyList(),
            EDLFluids.GLOW_BERRY_JUICE.stack(250),
            new ItemStack(Items.STICK, 4),
            EDLItems.GLOW_BERRY_POPSICLE.stack(4),
            800,
            true
        );
        registerChiller(
            "sweet_berry_popsicle",
            Collections.emptyList(),
            EDLFluids.SWEET_BERRY_JUICE.stack(250),
            new ItemStack(Items.STICK, 4),
            EDLItems.SWEET_BERRY_POPSICLE.stack(4),
            800,
            true
        );
        registerChiller(
            "melon_popsicle",
            Collections.emptyList(),
            EDLFluids.MELON_JUICE.stack(250),
            new ItemStack(Items.STICK, 4),
            itemStack("farmersdelight:melon_popsicle", 4),
            800,
            true
        );
        registerChiller(
            "caramel_popsicle",
            Collections.singletonList(MixingBowlIngredient.ore("iceCream")),
            EDLFluids.CARAMEL_SAUCE.stack(250),
            new ItemStack(Items.STICK, 4),
            EDLItems.CARAMEL_POPSICLE.stack(4),
            800,
            true
        );
        registerChillerNoFluid(
            "fudge_popsicle",
            Arrays.asList(MixingBowlIngredient.ore("iceCream"), MixingBowlIngredient.ore("cocoaPowder")),
            new ItemStack(Items.STICK, 4),
            EDLItems.FUDGE_POPSICLE.stack(4),
            800,
            true
        );
        registerChillerNoFluid(
            "honey_popsicle",
            Arrays.asList(MixingBowlIngredient.ore("iceCream"), MixingBowlIngredient.ore("honeyBottle")),
            new ItemStack(Items.STICK, 4),
            EDLItems.HONEY_POPSICLE.stack(4),
            800,
            true
        );
        registerChillerNoFluid(
            "cinnamon_popsicle",
            Arrays.asList(MixingBowlIngredient.ore("iceCream"), MixingBowlIngredient.ore("cinnamonGround")),
            new ItemStack(Items.STICK, 4),
            EDLItems.CINNAMON_POPSICLE.stack(4),
            800,
            true
        );
    }

    private static void registerIceCreamChillerRecipes() {
        registerIceCream("ice_cream", EDLItems.ICE_CREAM, Collections.singletonList(MixingBowlIngredient.ore("edlSweetener")));
        registerIceCream(
            "glow_berry_ice_cream",
            EDLItems.GLOW_BERRY_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow")
            )
        );
        registerIceCream(
            "chocolate_ice_cream",
            EDLItems.CHOCOLATE_ICE_CREAM,
            Arrays.asList(MixingBowlIngredient.ore("edlSweetener"), MixingBowlIngredient.ore("cocoaPowder"))
        );
        registerIceCream(
            "sweet_berry_ice_cream",
            EDLItems.SWEET_BERRY_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet")
            )
        );
        registerIceCream(
            "pumpkin_ice_cream",
            EDLItems.PUMPKIN_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin")
            )
        );
        registerIceCream(
            "honey_ice_cream",
            EDLItems.HONEY_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle")
            )
        );
        registerIceCream(
            "apple_ice_cream",
            EDLItems.APPLE_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple")
            )
        );
        registerIceCream(
            "cookie_dough_ice_cream",
            EDLItems.COOKIE_DOUGH_ICE_CREAM,
            Arrays.asList(MixingBowlIngredient.ore("edlSweetener"), MixingBowlIngredient.ore("cookieDough"))
        );
        registerIceCream(
            "mint_chip_ice_cream",
            EDLItems.MINT_CHIP_ICE_CREAM,
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("chocolateChips"),
                MixingBowlIngredient.ore("cropMint")
            )
        );
        registerIceCream(
            "nut_butter_ice_cream",
            EDLItems.NUT_BUTTER_ICE_CREAM,
            Arrays.asList(MixingBowlIngredient.ore("edlSweetener"), MixingBowlIngredient.ore("nutButter"))
        );
    }

    private static void registerIceCream(String name, EDLItems.ItemDefinition output, java.util.List<MixingBowlIngredient> ingredients) {
        registerChiller(
            name,
            ingredients,
            EDLFluids.milkStack(250),
            new ItemStack(Items.BOWL),
            output.stack(1),
            800,
            true
        );
    }

    private static void registerChiller(String name, java.util.List<MixingBowlIngredient> ingredients,
                                        net.minecraftforge.fluids.FluidStack fluid, ItemStack container,
                                        ItemStack output, int cookingTime, boolean consumeContainer) {
        if (!output.isEmpty() && !container.isEmpty() && fluid != null && !hasEmptyIngredientDisplay(ingredients)) {
            ChillerRecipeManager.register(name, ingredients, fluid, container, output, cookingTime, consumeContainer);
        }
    }

    private static void registerChillerNoFluid(String name, java.util.List<MixingBowlIngredient> ingredients,
                                               ItemStack container, ItemStack output, int cookingTime, boolean consumeContainer) {
        if (!output.isEmpty() && !container.isEmpty() && !hasEmptyIngredientDisplay(ingredients)) {
            ChillerRecipeManager.register(name, ingredients, null, container, output, cookingTime, consumeContainer);
        }
    }

    private static void registerMeltingPotRecipes() {
        MeltingPotRecipeManager.clear();
        MeltingPotRecipeManager.register(
            "ice_to_water",
            MixingBowlIngredient.stack(new ItemStack(Blocks.ICE)),
            new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 1000),
            400
        );
        registerMeltingPot(
            "dark_chocolate_bar",
            EDLItems.DARK_CHOCOLATE_BAR.stack(1),
            EDLFluids.DARK_CHOCOLATE_SYRUP.stack(250),
            400
        );
        registerMeltingPot(
            "dark_chocolate_block",
            EDLBlocks.DARK_CHOCOLATE_BLOCK.stack(1),
            EDLFluids.DARK_CHOCOLATE_SYRUP.stack(1000),
            400
        );
        registerMeltingPot(
            "milk_chocolate_bar",
            EDLItems.MILK_CHOCOLATE_BAR.stack(1),
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(250),
            400
        );
        registerMeltingPot(
            "milk_chocolate_block",
            EDLBlocks.MILK_CHOCOLATE_BLOCK.stack(1),
            EDLFluids.MILK_CHOCOLATE_SYRUP.stack(1000),
            400
        );
        registerMeltingPot(
            "white_chocolate_bar",
            EDLItems.WHITE_CHOCOLATE_BAR.stack(1),
            EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(250),
            400
        );
        registerMeltingPot(
            "white_chocolate_block",
            EDLBlocks.WHITE_CHOCOLATE_BLOCK.stack(1),
            EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(1000),
            400
        );
        registerMeltingPot(
            "blood_chocolate_bar",
            EDLItems.BLOOD_CHOCOLATE_BAR.stack(1),
            EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(250),
            400
        );
        registerMeltingPot(
            "blood_chocolate_block",
            EDLBlocks.BLOOD_CHOCOLATE_BLOCK.stack(1),
            EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(1000),
            400
        );
        registerMeltingPot(
            "marshmallow_fluff",
            EDLItems.MARSHMALLOW.stack(1),
            EDLFluids.MARSHMALLOW_FLUFF.stack(250),
            400
        );
        registerMeltingPot(
            "marshmallow_fluff_block",
            EDLBlocks.MARSHMALLOW_BLOCK.stack(1),
            EDLFluids.MARSHMALLOW_FLUFF.stack(1000),
            400
        );
    }

    private static void registerMeltingPot(String name, ItemStack input, net.minecraftforge.fluids.FluidStack output, int cookingTime) {
        if (!input.isEmpty() && output != null && output.amount > 0) {
            MeltingPotRecipeManager.register(name, MixingBowlIngredient.stack(input), output, cookingTime);
        }
    }

    private static void registerOvenRecipes() {
        OvenRecipeManager.clear();
        if (!EDLBlocks.OVEN.isRegistered()) {
            return;
        }

        registerOven(
            "sugar_cookie",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughSugar")),
            EDLBlocks.SHEET.stack(1),
            EDLItems.SUGAR_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("sugar_cookie_block", MixingBowlIngredient.ore("cookieDoughSugar"), EDLBlocks.SUGAR_COOKIE_BLOCK, 800);
        registerBulkOven("sugar_cookie_alex", EDLItems.RAW_SUGAR_COOKIE_ALEX, EDLItems.SUGAR_COOKIE_ALEX, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_creeper", EDLItems.RAW_SUGAR_COOKIE_CREEPER, EDLItems.SUGAR_COOKIE_CREEPER, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_pickaxe", EDLItems.RAW_SUGAR_COOKIE_PICKAXE, EDLItems.SUGAR_COOKIE_PICKAXE, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_steve", EDLItems.RAW_SUGAR_COOKIE_STEVE, EDLItems.SUGAR_COOKIE_STEVE, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_sword", EDLItems.RAW_SUGAR_COOKIE_SWORD, EDLItems.SUGAR_COOKIE_SWORD, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_villager", EDLItems.RAW_SUGAR_COOKIE_VILLAGER, EDLItems.SUGAR_COOKIE_VILLAGER, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_diamond", EDLItems.RAW_SUGAR_COOKIE_DIAMOND, EDLItems.SUGAR_COOKIE_DIAMOND, EDLBlocks.SHEET, 800);
        registerBulkOven("sugar_cookie_emerald", EDLItems.RAW_SUGAR_COOKIE_EMERALD, EDLItems.SUGAR_COOKIE_EMERALD, EDLBlocks.SHEET, 800);
        registerOven(
            "chocolate_cookie",
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1))),
            EDLBlocks.SHEET.stack(1),
            EDLItems.CHOCOLATE_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("chocolate_cookie_block", MixingBowlIngredient.stack(EDLItems.CHOCOLATE_COOKIE_DOUGH.stack(1)), EDLBlocks.CHOCOLATE_COOKIE_BLOCK, 400);
        registerOven(
            "nut_butter_cookie",
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1))),
            EDLBlocks.SHEET.stack(1),
            EDLItems.NUT_BUTTER_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("nut_butter_cookie_block", MixingBowlIngredient.stack(EDLItems.NUT_BUTTER_COOKIE_DOUGH.stack(1)), EDLBlocks.NUT_BUTTER_COOKIE_BLOCK, 400);
        registerOven(
            "apple_cookie",
            Collections.singletonList(MixingBowlIngredient.stack(EDLItems.APPLE_COOKIE_DOUGH.stack(1))),
            EDLBlocks.SHEET.stack(1),
            EDLItems.APPLE_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("apple_cookie_block", MixingBowlIngredient.stack(EDLItems.APPLE_COOKIE_DOUGH.stack(1)), EDLBlocks.APPLE_COOKIE_BLOCK, 800);
        registerOven(
            "gingerbread_cookie",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughGingerbread")),
            EDLBlocks.SHEET.stack(1),
            EDLItems.GINGERBREAD_COOKIE.stack(8),
            800,
            false
        );
        registerCookieBlockOven("gingerbread_cookie_block", MixingBowlIngredient.ore("cookieDoughGingerbread"), EDLBlocks.GINGERBREAD_COOKIE_BLOCK, 800);
        registerOven(
            "glow_berry_cookie",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughGlowBerry")),
            EDLBlocks.SHEET.stack(1),
            EDLItems.GLOW_BERRY_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("glow_berry_cookie_block", MixingBowlIngredient.ore("cookieDoughGlowBerry"), EDLBlocks.GLOW_BERRY_COOKIE_BLOCK, 800);
        registerOven(
            "pumpkin_cookie",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughPumpkin")),
            EDLBlocks.SHEET.stack(1),
            EDLItems.PUMPKIN_COOKIE.stack(8),
            400,
            false
        );
        registerCookieBlockOven("pumpkin_cookie_block", MixingBowlIngredient.ore("cookieDoughPumpkin"), EDLBlocks.PUMPKIN_COOKIE_BLOCK, 800);
        registerOven(
            "chocolate_chip_cookie",
            Collections.singletonList(MixingBowlIngredient.ore("cookieDoughChocolateChip")),
            EDLBlocks.SHEET.stack(1),
            new ItemStack(Items.COOKIE, 8),
            400,
            false
        );
        registerCookieBlockOven("chocolate_chip_cookie_block", MixingBowlIngredient.ore("cookieDoughChocolateChip"), EDLBlocks.CHOCOLATE_CHIP_COOKIE_BLOCK, 800);
        registerCookieBlockOven("honey_cookie_block", MixingBowlIngredient.ore("cookieDoughHoney"), EDLBlocks.HONEY_COOKIE_BLOCK, 800);
        registerCookieBlockOven("sweet_berry_cookie_block", MixingBowlIngredient.ore("cookieDoughSweetBerry"), EDLBlocks.SWEET_BERRY_COOKIE_BLOCK, 800);
        registerBulkOven("gingerbread_alex", EDLItems.RAW_GINGERBREAD_ALEX, EDLItems.GINGERBREAD_ALEX, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_creeper", EDLItems.RAW_GINGERBREAD_CREEPER, EDLItems.GINGERBREAD_CREEPER, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_pickaxe", EDLItems.RAW_GINGERBREAD_PICKAXE, EDLItems.GINGERBREAD_PICKAXE, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_steve", EDLItems.RAW_GINGERBREAD_STEVE, EDLItems.GINGERBREAD_STEVE, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_sword", EDLItems.RAW_GINGERBREAD_SWORD, EDLItems.GINGERBREAD_SWORD, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_villager", EDLItems.RAW_GINGERBREAD_VILLAGER, EDLItems.GINGERBREAD_VILLAGER, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_diamond", EDLItems.RAW_GINGERBREAD_DIAMOND, EDLItems.GINGERBREAD_DIAMOND, EDLBlocks.SHEET, 800);
        registerBulkOven("gingerbread_emerald", EDLItems.RAW_GINGERBREAD_EMERALD, EDLItems.GINGERBREAD_EMERALD, EDLBlocks.SHEET, 800);
        registerOven(
            "baked_cod",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.FISH, 1, 0)),
                MixingBowlIngredient.ore("cookingOil"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("processedLemon"),
                MixingBowlIngredient.ore("salt")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.BAKED_COD.stack(1),
            800,
            true
        );
        registerNewFeastOvenRecipes();
        registerOven(
            "crackers",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.CRACKERS.stack(4),
            800,
            false
        );
        registerBulkOven("roasted_apple", new ItemStack(Items.APPLE), EDLItems.ROASTED_APPLE.stack(1), EDLBlocks.SHEET, 800);
        registerBulkOven("roasted_carrot", new ItemStack(Items.CARROT), EDLItems.ROASTED_CARROT.stack(1), EDLBlocks.SHEET, 800);
        registerBulkOven("roasted_garlic", EDLItems.GARLIC_CLOVE, EDLItems.ROASTED_GARLIC, EDLBlocks.SHEET, 800);
        registerBulkOven("roasted_cocoa_beans", new ItemStack(Items.DYE, 1, 3), EDLItems.ROASTED_COCOA_BEANS.stack(1), EDLBlocks.TRAY, 800);
        registerBulkOven("roasted_peanuts", "cropPeanutShelled", EDLItems.ROASTED_PEANUTS, EDLBlocks.TRAY, 800);
        registerBulkOven("roasted_hazelnuts", "nutHazelnut", EDLItems.ROASTED_HAZELNUTS, EDLBlocks.TRAY, 800);
        registerBulkOven("crisp_rice", "cropRice", EDLItems.CRISP_RICE, EDLBlocks.TRAY, 800);
        registerBulkOven("coffee_beans", "cropCoffeeGreen", EDLItems.COFFEE_BEANS, EDLBlocks.TRAY, 800);
        registerBulkOven("cooked_cactus", EDLItems.CACTUS, EDLItems.COOKED_CACTUS, EDLBlocks.SHEET, 800);
        registerBulkOven("grilled_corn_on_cob", "cropCorn", EDLItems.GRILLED_CORN_ON_COB, EDLBlocks.SHEET, 800);
        registerBulkOven("roasted_pumpkin_seeds", new ItemStack(Items.PUMPKIN_SEEDS), EDLItems.ROASTED_PUMPKIN_SEEDS.stack(1), EDLBlocks.TRAY, 800);
        registerBulkOven("toast", "breadSliced", EDLItems.TOAST, EDLBlocks.SHEET, 800);
        registerBulkOven("grilled_cheese", EDLItems.CHEESE_SANDWICH, EDLItems.GRILLED_CHEESE, EDLBlocks.SHEET, 800);
        registerBulkOven("bread", "dough", new ItemStack(Items.BREAD), EDLBlocks.LOAF_PAN, 800);
        registerBulkOven("baked_potato", new ItemStack(Items.POTATO), new ItemStack(Items.BAKED_POTATO), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_beef", new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_chicken", new ItemStack(Items.CHICKEN), new ItemStack(Items.COOKED_CHICKEN), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_porkchop", new ItemStack(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_mutton", new ItemStack(Items.MUTTON), new ItemStack(Items.COOKED_MUTTON), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_rabbit", new ItemStack(Items.RABBIT), new ItemStack(Items.COOKED_RABBIT), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_cod", new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.COOKED_FISH, 1, 0), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_salmon", new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), EDLBlocks.SHEET, 800);
        registerBulkOven("cooked_bacon", itemStack("farmersdelight:bacon"), itemStack("farmersdelight:cooked_bacon"), EDLBlocks.TRAY, 800);
        registerBulkOven("cooked_chicken_cuts", itemStack("farmersdelight:chicken_cuts"), itemStack("farmersdelight:cooked_chicken_cuts"), EDLBlocks.TRAY, 800);
        registerBulkOven("cooked_cod_slice", itemStack("farmersdelight:cod_slice"), itemStack("farmersdelight:cooked_cod_slice"), EDLBlocks.TRAY, 800);
        registerBulkOven("cooked_salmon_slice", itemStack("farmersdelight:salmon_slice"), itemStack("farmersdelight:cooked_salmon_slice"), EDLBlocks.TRAY, 800);
        registerBulkOven("fried_egg", "egg", itemStack("farmersdelight:fried_egg"), EDLBlocks.TRAY, 800);
        registerBulkOven("beef_patty", itemStack("farmersdelight:minced_beef"), itemStack("farmersdelight:beef_patty"), EDLBlocks.TRAY, 800);
        registerBulkOven("mutton_chops", itemStack("farmersdelight:mutton_chops"), itemStack("farmersdelight:cooked_mutton_chops"), EDLBlocks.TRAY, 800);
        registerBulkOven("ham", itemStack("farmersdelight:ham"), itemStack("farmersdelight:smoked_ham"), EDLBlocks.TRAY, 800);
        registerOven(
            "garlic_bread",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.BREAD)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("processedGarlic")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.GARLIC_BREAD.stack(2),
            400,
            false
        );
        registerOven(
            "cheesy_garlic_bread",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.BREAD)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("cheese")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.CHEESY_GARLIC_BREAD.stack(2),
            400,
            false
        );
        registerOven(
            "panforte",
            Arrays.asList(
                MixingBowlIngredient.ore("driedFruit"),
                MixingBowlIngredient.ore("driedFruit"),
                MixingBowlIngredient.ore("nutHazelnutRoasted"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.stack(new ItemStack(Items.SUGAR)),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.PANFORTE.stack(1),
            800,
            false
        );
        registerOven(
            "egg_in_the_basket",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("breadSliced")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.EGG_IN_THE_BASKET.stack(1),
            800,
            false
        );
        registerOven(
            "french_toast",
            Arrays.asList(
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(new ItemStack(Items.MILK_BUCKET)),
                MixingBowlIngredient.stack(EDLItems.BREAD_SLICE.stack(4))
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.FRENCH_TOAST.stack(4),
            800,
            false
        );
        registerOven(
            "graham_cracker",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.stack(new ItemStack(Items.MILK_BUCKET)),
                MixingBowlIngredient.ore("cookingOil"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.GRAHAM_CRACKER.stack(4),
            800,
            false
        );
        registerOven(
            "jalapeno_popper",
            Arrays.asList(
                MixingBowlIngredient.ore("cropChili"),
                MixingBowlIngredient.ore("cropChili"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:bacon"))
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.JALAPENO_POPPER.stack(2),
            800,
            false
        );
        registerOven(
            "chili_cheese_cornbread_muffin",
            Arrays.asList(
                MixingBowlIngredient.ore("cornMeal"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.EGG_MIX.stack(1)),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("processedChili")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.CHILI_CHEESE_CORNBREAD_MUFFIN.stack(6),
            800,
            false
        );
        registerOven(
            "muffin_ginger",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("eggMix"),
                MixingBowlIngredient.ore("processedGinger")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MUFFIN_GINGER.stack(6),
            800,
            false
        );
        registerOven(
            "muffin_cinnamon",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("eggMix"),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MUFFIN_CINNAMON.stack(6),
            800,
            false
        );
        registerOven(
            "muffin_sweet_berry",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("eggMix"),
                MixingBowlIngredient.ore("cropBerrySweet")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MUFFIN_SWEET_BERRY.stack(6),
            800,
            false
        );
        registerOven(
            "muffin_apple",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("eggMix"),
                MixingBowlIngredient.ore("processedApple")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.MUFFIN_APPLE.stack(6),
            800,
            false
        );
        registerOven(
            "fruit_bread",
            Arrays.asList(
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("fruitDried"),
                MixingBowlIngredient.ore("fruitDried")
            ),
            EDLBlocks.LOAF_PAN.stack(1),
            EDLItems.FRUIT_BREAD.stack(1),
            800,
            false
        );
        registerOven(
            "apple_chips",
            Arrays.asList(
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.APPLE_CHIPS.stack(8),
            800,
            false
        );
        registerOven(
            "grilled_grapefruit",
            Arrays.asList(
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("processedGrapefruit"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.GRILLED_GRAPEFRUIT.stack(8),
            800,
            false
        );
        registerOven(
            "roll",
            Collections.singletonList(MixingBowlIngredient.ore("dough")),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.ROLL.stack(6),
            800,
            false
        );
        registerOven(
            "rombosse",
            Arrays.asList(
                MixingBowlIngredient.ore("fruitApple"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("cinnamonStick")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.ROMBOSSE.stack(1),
            800,
            false
        );
        registerOven(
            "stuffed_mushrooms",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Blocks.BROWN_MUSHROOM)),
                MixingBowlIngredient.stack(new ItemStack(Blocks.BROWN_MUSHROOM)),
                MixingBowlIngredient.stack(new ItemStack(Blocks.BROWN_MUSHROOM)),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.stack(EDLBlocks.BREADCRUMBS.stack(1)),
                MixingBowlIngredient.ore("butter")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.STUFFED_MUSHROOMS.stack(3),
            800,
            false
        );
        registerOven(
            "chicken_kiev",
            Arrays.asList(
                MixingBowlIngredient.ore("foodChickenRaw"),
                MixingBowlIngredient.ore("processedGarlic"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(EDLBlocks.BREADCRUMBS.stack(1))
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.CHICKEN_KIEV.stack(2),
            800,
            false
        );
        registerOven(
            "sausage_roll",
            Arrays.asList(
                MixingBowlIngredient.ore("foodSausageCooked"),
                MixingBowlIngredient.ore("dough")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.SAUSAGE_ROLL.stack(1),
            800,
            false
        );
        registerOven(
            "stuffed_heart",
            Arrays.asList(
                MixingBowlIngredient.ore("heart"),
                MixingBowlIngredient.ore("listAllmushroom"),
                MixingBowlIngredient.stack(EDLBlocks.BREADCRUMBS.stack(1)),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.STUFFED_HEART.stack(1),
            800,
            false
        );
        registerOven(
            "fat_potatoes",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.POTATO)),
                MixingBowlIngredient.stack(new ItemStack(Items.POTATO)),
                MixingBowlIngredient.stack(new ItemStack(Items.POTATO)),
                MixingBowlIngredient.stack(new ItemStack(Items.POTATO)),
                MixingBowlIngredient.ore("fat"),
                MixingBowlIngredient.stack(new ItemStack(Items.BOWL)),
                MixingBowlIngredient.stack(new ItemStack(Items.BOWL)),
                MixingBowlIngredient.stack(new ItemStack(Items.BOWL)),
                MixingBowlIngredient.stack(new ItemStack(Items.BOWL))
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLItems.FAT_POTATOES.stack(4),
            800,
            false
        );
        registerOven(
            "cheeseburger_pickle",
            Arrays.asList(
                MixingBowlIngredient.ore("foodGroundBeefRaw"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("foodGroundBeefRaw"),
                MixingBowlIngredient.ore("foodBaconRaw"),
                MixingBowlIngredient.ore("foodBaconRaw"),
                MixingBowlIngredient.ore("foodBaconRaw"),
                MixingBowlIngredient.ore("foodPickledCucumber"),
                MixingBowlIngredient.ore("foodPickledCucumber"),
                MixingBowlIngredient.ore("foodPickledCucumber")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.CHEESEBURGER_PICKLE.stack(3),
            800,
            false
        );
        registerOven(
            "meringue",
            Arrays.asList(
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("stiffPeaks")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLItems.MERINGUE.stack(6),
            400,
            false
        );
        registerOven(
            "lemon_delicious",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("zestLemon"),
                MixingBowlIngredient.ore("lemonJuice"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("stiffPeaks")
            ),
            new ItemStack(Items.BOWL),
            EDLItems.LEMON_DELICIOUS.stack(1),
            400,
            true
        );
        registerOven(
            "lime_souffle",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("zestLime"),
                MixingBowlIngredient.ore("limeJuice"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("stiffPeaks")
            ),
            new ItemStack(Items.BOWL),
            EDLItems.LIME_SOUFFLE.stack(1),
            400,
            true
        );
        registerOven(
            "cheese_souffle",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("chiliPowder"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("stiffPeaks")
            ),
            new ItemStack(Items.BOWL),
            EDLItems.CHEESE_SOUFFLE.stack(1),
            400,
            true
        );
        registerOven(
            "chicken_parm",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.FRIED_CHICKEN.stack(1)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.ore("cheese")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLItems.CHICKEN_PARM.stack(2),
            800,
            false
        );
        registerOven(
            "lava_cake",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.stack(new ItemStack(Items.MILK_BUCKET)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.LAVA_CAKE.stack(6),
            800,
            false
        );
        registerOven(
            "jaffa_cake",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE.stack(1)),
                MixingBowlIngredient.ore("jamOrange"),
                MixingBowlIngredient.ore("zestOrange")
            ),
            EDLBlocks.MUFFIN_TIN.stack(1),
            EDLItems.JAFFA_CAKE.stack(6),
            800,
            false
        );
        registerOven(
            "cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "chocolate_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("cocoaPowder"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.CHOCOLATE_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "honey_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("honeyBottle"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.HONEY_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "glow_berry_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.GLOW_BERRY_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "pumpkin_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.PUMPKIN_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "apple_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.APPLE_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "sweet_berry_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.SWEET_BERRY_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "glow_berry_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("cropBerryGlow"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.GLOW_BERRY_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "key_lime_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("eggOrYolk"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("limeJuice"),
                MixingBowlIngredient.ore("zestLime"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("processedLime"),
                MixingBowlIngredient.ore("whippedCream"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.KEY_LIME_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "lemon_meringue_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("stiffPeaks"),
                MixingBowlIngredient.stack(EDLItems.LEMON_CURD.stack(1)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.stack(EDLItems.LEMON_CURD.stack(1))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.LEMON_MERINGUE_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "caramel_cheesecake",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CARAMEL_SAUCE.stack(1)),
                MixingBowlIngredient.stack(EDLItems.CARAMEL_SAUCE.stack(1)),
                MixingBowlIngredient.stack(EDLItems.CARAMEL_SAUCE.stack(1)),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.CARAMEL_CHEESECAKE.stack(1),
            800,
            false
        );
        registerOven(
            "pumpkin_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.stack(EDLItems.EGG_MIX.stack(1))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.PUMPKIN_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "meat_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("listAllmushroom"),
                MixingBowlIngredient.ore("processedPotato"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.MEAT_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "bacon_egg_pie",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:bacon")),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.stack(itemStack("farmersdelight:bacon"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.BACON_EGG_PIE.stack(1),
            400,
            false
        );
        registerOven(
            "steak_pickled_onion_pie",
            Arrays.asList(
                MixingBowlIngredient.ore("foodBeefCubedRaw"),
                MixingBowlIngredient.ore("foodPickledOnion"),
                MixingBowlIngredient.ore("gravy"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.STEAK_PICKLED_ONION_PIE.stack(1),
            800,
            false
        );
        registerOven(
            "chocolate_cake",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.CHOCOLATE_CAKE.stack(1),
            800,
            false
        );
        registerOven(
            "coffee_cake",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.COFFEE_CAKE.stack(1),
            800,
            false
        );
        registerOven(
            "kyiv_cake",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.STIFF_PEAKS.stack(1)),
                MixingBowlIngredient.stack(EDLItems.STIFF_PEAKS.stack(1)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("nutHazelnutRoasted"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("chocolateSyrup"),
                MixingBowlIngredient.ore("whippedCream")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLBlocks.KYIV_CAKE.stack(1),
            800,
            false
        );
        registerOven(
            "meatloaf_block",
            Arrays.asList(
                MixingBowlIngredient.ore("ketchup"),
                MixingBowlIngredient.ore("breadCrumbs"),
                MixingBowlIngredient.ore("eggMix"),
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("groundMeatRaw"),
                MixingBowlIngredient.ore("processedOnion")
            ),
            EDLBlocks.LOAF_PAN.stack(1),
            EDLBlocks.MEATLOAF_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "potroast_block",
            Arrays.asList(
                MixingBowlIngredient.ore("broth"),
                MixingBowlIngredient.ore("roastRaw"),
                MixingBowlIngredient.ore("cookingOil"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedPotato"),
                MixingBowlIngredient.ore("processedCarrot"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("processedPotato"),
                MixingBowlIngredient.ore("processedCarrot")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.POTROAST_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "bbq_ribs_block",
            Arrays.asList(
                MixingBowlIngredient.ore("ribsRaw"),
                MixingBowlIngredient.ore("ribsRaw"),
                MixingBowlIngredient.ore("ribsRaw"),
                MixingBowlIngredient.ore("ribsRaw"),
                MixingBowlIngredient.ore("bbqSauce")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.BBQ_RIBS_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "rack_lamb_block",
            Arrays.asList(
                MixingBowlIngredient.ore("muttonRibs"),
                MixingBowlIngredient.ore("cookingOil"),
                MixingBowlIngredient.ore("muttonRibs"),
                MixingBowlIngredient.ore("muttonRibs"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("muttonRibs"),
                MixingBowlIngredient.ore("cropBerrySweet"),
                MixingBowlIngredient.ore("breadCrumbs"),
                MixingBowlIngredient.ore("cropBerrySweet")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.RACK_LAMB_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "beef_wellington_block",
            Arrays.asList(
                MixingBowlIngredient.ore("listAllmushroom"),
                MixingBowlIngredient.ore("beefRoast"),
                MixingBowlIngredient.ore("listAllmushroom"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("liver"),
                MixingBowlIngredient.ore("foodBaconCooked"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("processedOnion")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.BEEF_WELLINGTON_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "haggis_block",
            Arrays.asList(
                MixingBowlIngredient.ore("offal"),
                MixingBowlIngredient.ore("offal"),
                MixingBowlIngredient.ore("offal"),
                MixingBowlIngredient.ore("lung"),
                MixingBowlIngredient.ore("stomach"),
                MixingBowlIngredient.ore("scrapMeat"),
                MixingBowlIngredient.ore("seed"),
                MixingBowlIngredient.ore("seed"),
                MixingBowlIngredient.ore("seed")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.HAGGIS_BLOCK.stack(1),
            1600,
            false
        );
        registerOven(
            "lasagna_block",
            Arrays.asList(
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.stack(EDLItems.LASAGNA_NOODLES.stack(1)),
                MixingBowlIngredient.stack(EDLItems.LASAGNA_NOODLES.stack(1)),
                MixingBowlIngredient.stack(EDLItems.LASAGNA_NOODLES.stack(1)),
                MixingBowlIngredient.ore("groundBeefCooked"),
                MixingBowlIngredient.stack(itemStack("farmersdelight:tomato_sauce")),
                MixingBowlIngredient.ore("groundBeefCooked")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.LASAGNA_BLOCK.stack(1),
            1600,
            true
        );
        registerOven(
            "hotdish_block",
            Arrays.asList(
                MixingBowlIngredient.ore("starch"),
                MixingBowlIngredient.ore("starch"),
                MixingBowlIngredient.ore("starch"),
                MixingBowlIngredient.ore("cookedMeat"),
                MixingBowlIngredient.ore("soup"),
                MixingBowlIngredient.ore("cookedMeat"),
                MixingBowlIngredient.ore("vegetable"),
                MixingBowlIngredient.ore("soup"),
                MixingBowlIngredient.ore("vegetable")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.HOTDISH_BLOCK.stack(1),
            1600,
            true
        );
    }

    private static void registerCookieBlockOven(String name, MixingBowlIngredient dough,
                                                EDLBlocks.BlockDefinition output, int cookingTime) {
        if (output.getItemBlock() == null) {
            return;
        }
        registerOven(
            name,
            repeat(dough, 4),
            EDLBlocks.SHEET.stack(1),
            output.stack(4),
            cookingTime,
            false
        );
    }

    private static void registerNewFeastOvenRecipes() {
        registerOven(
            "cornbread_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("cornMeal"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.EGG_MIX.stack(1))
            ),
            itemStack("farmersdelight:skillet"),
            EDLBlocks.CORNBREAD_FEAST.stack(1),
            800,
            true
        );
        registerOven(
            "corn_pudding_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("cornMeal"),
                MixingBowlIngredient.stack(EDLItems.COOKED_CORN.stack(1)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.EGG_MIX.stack(1)),
                MixingBowlIngredient.ore("foodMilk")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.CORN_PUDDING_FEAST.stack(1),
            800,
            true
        );
        registerOven(
            "apple_crisp_feast",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.WHEAT_SEEDS)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("processedApple")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.APPLE_CRISP_FEAST.stack(1),
            800,
            true
        );
        registerOven(
            "potato_au_gratin_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("processedPotato"),
                MixingBowlIngredient.ore("processedPotato"),
                MixingBowlIngredient.ore("processedPotato")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.POTATO_AU_GRATIN_FEAST.stack(1),
            800,
            true
        );
        registerOven(
            "cinnamon_rolls_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.stack(EDLItems.FROSTING_WHITE.stack(1))
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.CINNAMON_ROLLS_FEAST.stack(1),
            800,
            true
        );
        registerOven(
            "monkey_bread_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.MONKEY_BREAD_FEAST.stack(1),
            800,
            false
        );
        registerOven(
            "christmas_pudding_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("fruitDried"),
                MixingBowlIngredient.ore("fruitDried"),
                MixingBowlIngredient.ore("fruitDried"),
                MixingBowlIngredient.ore("breadCrumbs"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("edlSweetener")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.CHRISTMAS_PUDDING_FEAST.stack(1),
            800,
            false
        );
        registerOven(
            "mint_lamb_feast",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.MUTTON)),
                MixingBowlIngredient.stack(new ItemStack(Items.MUTTON)),
                MixingBowlIngredient.stack(new ItemStack(Items.MUTTON)),
                MixingBowlIngredient.stack(new ItemStack(Items.MUTTON)),
                MixingBowlIngredient.ore("cropMint"),
                MixingBowlIngredient.ore("breadCrumbs"),
                MixingBowlIngredient.ore("cookingOil")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.MINT_LAMB_FEAST.stack(1),
            800,
            false
        );
        registerOven(
            "brownies_block",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("liquidChocolate"),
                MixingBowlIngredient.ore("chocolateChips"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.BROWNIES_BLOCK.stack(1),
            800,
            true
        );
        registerOven(
            "blondies_block",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butterscotch"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.BLONDIES_BLOCK.stack(1),
            800,
            true
        );
        registerOven(
            "sticky_toffee_pudding_block",
            Arrays.asList(
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.stack(EDLItems.CARAMEL_SAUCE.stack(1)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG))
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.STICKY_TOFFEE_PUDDING_BLOCK.stack(1),
            800,
            false
        );
        registerOven(
            "pork_and_apples_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("foodPorkRoastRaw"),
                MixingBowlIngredient.ore("processedApple"),
                MixingBowlIngredient.ore("processedOnion"),
                MixingBowlIngredient.ore("broth"),
                MixingBowlIngredient.ore("butter")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.PORK_AND_APPLES_FEAST.stack(1),
            1600,
            false
        );
        registerOven(
            "stuffed_apples_feast",
            Arrays.asList(
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.ore("fruitDried"),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.ore("nutHazelnutRoasted"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("cinnamonGround")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.STUFFED_APPLES_FEAST.stack(1),
            800,
            false
        );
        registerOven(
            "pumpkin_roll_feast",
            Arrays.asList(
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.stack(EDLItems.EGG_MIX.stack(1)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("processedPumpkin"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("processedPumpkin")
            ),
            EDLBlocks.TRAY.stack(1),
            EDLBlocks.PUMPKIN_ROLL_FEAST.stack(1),
            800,
            false
        );
        registerOven(
            "quiche",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.OMELETTE_MIX.stack(1)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.QUICHE.stack(1),
            800,
            false
        );
        registerOven(
            "milk_tart",
            Arrays.asList(
                MixingBowlIngredient.ore("cinnamonGround"),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust"))
            ),
            EDLBlocks.PIE_DISH.stack(1),
            EDLBlocks.MILK_TART.stack(1),
            800,
            false
        );
        registerOven(
            "tarte_tatin_in_pan",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.CARAMEL_SAUCE.stack(1)),
                MixingBowlIngredient.stack(itemStack("farmersdelight:pie_crust")),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE)),
                MixingBowlIngredient.stack(new ItemStack(Items.APPLE))
            ),
            itemStack("farmersdelight:skillet"),
            EDLItems.TARTE_TATIN_IN_PAN.stack(1),
            800,
            true
        );
        registerOven(
            "lemon_cucumber_cake",
            Arrays.asList(
                MixingBowlIngredient.ore("processedCucumber"),
                MixingBowlIngredient.ore("zestLemon"),
                MixingBowlIngredient.stack(EDLItems.LEMON_JUICE.stack(1)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.FROSTING_WHITE.stack(1)),
                MixingBowlIngredient.ore("processedLemon")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.LEMON_CUCUMBER_CAKE.stack(1),
            800,
            false
        );
        registerOven(
            "melon_layer_cake",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.MELON_JUICE.stack(1)),
                MixingBowlIngredient.ore("foodMilk"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.stack(new ItemStack(Items.EGG)),
                MixingBowlIngredient.ore("flour"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.stack(EDLItems.FROSTING_PINK.stack(1)),
                MixingBowlIngredient.ore("frostingGreen"),
                MixingBowlIngredient.ore("chocolateChips")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.MELON_LAYER_CAKE.stack(1),
            800,
            false
        );
        registerOven(
            "pavlova",
            Arrays.asList(
                MixingBowlIngredient.stack(EDLItems.STIFF_PEAKS.stack(1)),
                MixingBowlIngredient.stack(EDLItems.STIFF_PEAKS.stack(1)),
                MixingBowlIngredient.stack(EDLItems.STIFF_PEAKS.stack(1)),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("edlSweetener"),
                MixingBowlIngredient.ore("whippedCream"),
                MixingBowlIngredient.ore("processedFruit"),
                MixingBowlIngredient.ore("processedFruit")
            ),
            EDLBlocks.SHEET.stack(1),
            EDLBlocks.PAVLOVA.stack(1),
            800,
            false
        );
        registerOven(
            "cheesymite_scroll_block",
            Arrays.asList(
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("cheese"),
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("yeastSpread"),
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("dough"),
                MixingBowlIngredient.ore("butter"),
                MixingBowlIngredient.ore("dough")
            ),
            EDLBlocks.SQUARE_PAN.stack(1),
            EDLBlocks.CHEESYMITE_SCROLL_BLOCK.stack(1),
            800,
            false
        );
    }

    private static void registerFeastServingRecipes() {
        FeastServingRegistry.clear();
        FeastServingRegistry.register(EDLBlocks.MEATLOAF_BLOCK.getBlock(), Items.BOWL, EDLItems.MEATLOAF.stack(1));
        FeastServingRegistry.register(EDLBlocks.MEATLOAF_BLOCK.getBlock(), Items.BREAD, EDLItems.MEATLOAF_SANDWICH.stack(1));
        FeastServingRegistry.register(EDLBlocks.MEATLOAF_BLOCK.getBlock(), "breadSliced", EDLItems.MEATLOAF_SANDWICH.stack(1));
        FeastServingRegistry.register(EDLBlocks.HASH_BLOCK.getBlock(), Items.BOWL, EDLItems.HASH.stack(1));
        FeastServingRegistry.register(EDLBlocks.POTROAST_BLOCK.getBlock(), Items.BOWL, EDLItems.POTROAST.stack(1));
        FeastServingRegistry.register(EDLBlocks.SALISBURY_STEAK_BLOCK.getBlock(), Items.BOWL, EDLItems.SALISBURY_STEAK.stack(1));
        FeastServingRegistry.register(EDLBlocks.PULLED_PORK_BLOCK.getBlock(), Items.BREAD, EDLItems.PULLED_PORK_SANDWICH.stack(1));
        FeastServingRegistry.register(EDLBlocks.PULLED_PORK_BLOCK.getBlock(), "breadSliced", EDLItems.PULLED_PORK_SANDWICH.stack(1));
        FeastServingRegistry.register(EDLBlocks.RACK_LAMB_BLOCK.getBlock(), Items.BOWL, EDLItems.RACK_LAMB.stack(1));
        FeastServingRegistry.register(EDLBlocks.BEEF_WELLINGTON_BLOCK.getBlock(), Items.BOWL, EDLItems.BEEF_WELLINGTON.stack(1));
        FeastServingRegistry.register(EDLBlocks.HAGGIS_BLOCK.getBlock(), Items.BOWL, EDLItems.HAGGIS.stack(1));
        FeastServingRegistry.register(EDLBlocks.STIRFRY_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.STIRFRY.stack(1));
        FeastServingRegistry.register(EDLBlocks.BBQ_RIBS_BLOCK.getBlock(), Items.BOWL, EDLItems.BBQ_RIBS.stack(1));
        FeastServingRegistry.register(EDLBlocks.MASHED_POTATO_GRAVY_BLOCK.getBlock(), Items.BOWL, EDLItems.MASHED_POTATO_GRAVY.stack(1));
        FeastServingRegistry.register(EDLBlocks.MACARONI_CHEESE_BLOCK.getBlock(), Items.BOWL, EDLItems.MACARONI_CHEESE.stack(1));
        FeastServingRegistry.register(EDLBlocks.LASAGNA_BLOCK.getBlock(), Items.BOWL, EDLItems.LASAGNA.stack(1));
        FeastServingRegistry.register(EDLBlocks.HOTDISH_BLOCK.getBlock(), Items.BOWL, EDLItems.HOTDISH.stack(1));
        FeastServingRegistry.register(EDLBlocks.CURRY_BLOCK.getBlock(), Items.BOWL, EDLItems.CURRY.stack(1));
        FeastServingRegistry.register(EDLBlocks.CURRY_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.CURRY_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.BEEF_STEW_BLOCK.getBlock(), Items.BOWL, itemStack("farmersdelight:beef_stew"));
        FeastServingRegistry.register(EDLBlocks.BEEF_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.BEEF_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.PORK_STEW_BLOCK.getBlock(), Items.BOWL, EDLItems.PORK_STEW.stack(1));
        FeastServingRegistry.register(EDLBlocks.PORK_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.PORK_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.LAMB_STEW_BLOCK.getBlock(), Items.BOWL, EDLItems.LAMB_STEW.stack(1));
        FeastServingRegistry.register(EDLBlocks.LAMB_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.LAMB_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.RABBIT_STEW_BLOCK.getBlock(), Items.BOWL, new ItemStack(Items.RABBIT_STEW));
        FeastServingRegistry.register(EDLBlocks.RABBIT_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.RABBIT_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHICKEN_STEW_BLOCK.getBlock(), Items.BOWL, EDLItems.CHICKEN_STEW.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHICKEN_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.CHICKEN_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.FISH_STEW_BLOCK.getBlock(), Items.BOWL, itemStack("farmersdelight:fish_stew"));
        FeastServingRegistry.register(EDLBlocks.FISH_STEW_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.FISH_STEW_RICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.CORNBREAD_FEAST.getBlock(), Items.BOWL, EDLItems.CORNBREAD.stack(1));
        FeastServingRegistry.register(EDLBlocks.CORN_PUDDING_FEAST.getBlock(), Items.BOWL, EDLItems.CORN_PUDDING.stack(1));
        FeastServingRegistry.register(EDLBlocks.APPLE_CRISP_FEAST.getBlock(), Items.BOWL, EDLItems.APPLE_CRISP.stack(1));
        FeastServingRegistry.register(EDLBlocks.STUFFING_FEAST.getBlock(), Items.BOWL, EDLItems.STUFFING.stack(1));
        FeastServingRegistry.register(EDLBlocks.POTATO_AU_GRATIN_FEAST.getBlock(), Items.BOWL, EDLItems.POTATO_AU_GRATIN.stack(1));
        FeastServingRegistry.register(EDLBlocks.CINNAMON_ROLLS_FEAST.getBlock(), "toolSpoon", EDLItems.CINNAMON_ROLLS.stack(1));
        FeastServingRegistry.register(EDLBlocks.MONKEY_BREAD_FEAST.getBlock(), Items.BOWL, EDLItems.MONKEY_BREAD.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHRISTMAS_PUDDING_FEAST.getBlock(), Items.BOWL, EDLItems.CHRISTMAS_PUDDING.stack(1));
        FeastServingRegistry.register(EDLBlocks.PUNCH_FEAST.getBlock(), Items.GLASS_BOTTLE, EDLItems.PUNCH.stack(1));
        FeastServingRegistry.register(EDLBlocks.MINT_LAMB_FEAST.getBlock(), Items.BOWL, EDLItems.MINT_LAMB.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHARCUTERIE_BOARD_FEAST.getBlock(), Items.BOWL, EDLItems.CHARCUTERIE_BOARD.stack(1));
        FeastServingRegistry.register(EDLBlocks.BROWNIES_BLOCK.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.BROWNIE.stack(1));
        FeastServingRegistry.register(EDLBlocks.BLONDIES_BLOCK.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.BLONDIE.stack(1));
        FeastServingRegistry.register(EDLBlocks.FUDGE_BLOCK.getBlock(), Items.PAPER, EDLItems.FUDGE.stack(1));
        FeastServingRegistry.register(EDLBlocks.STICKY_TOFFEE_PUDDING_BLOCK.getBlock(), Items.BOWL, EDLItems.STICKY_TOFFEE_PUDDING_SLICE.stack(1));
        FeastServingRegistry.register(EDLBlocks.CRISP_RICE_TREATS_BLOCK.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.CRISP_RICE_TREAT.stack(1));
        FeastServingRegistry.register(EDLBlocks.SCOTCHAROO_BLOCK.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.SCOTCHAROO.stack(1));
        FeastServingRegistry.register(EDLBlocks.BLACK_FOREST_TRIFLE_BLOCK.getBlock(), Items.BOWL, EDLItems.BLACK_FOREST_TRIFLE.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHILI_CON_CARNE_FEAST.getBlock(), Items.BOWL, EDLItems.CHILI_CON_CARNE.stack(1));
        FeastServingRegistry.register(EDLBlocks.WHITE_CHILI_FEAST.getBlock(), Items.BOWL, EDLItems.WHITE_CHILI.stack(1));
        FeastServingRegistry.register(EDLBlocks.BRUSCHETTA_FEAST.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.BRUSCHETTA.stack(1));
        FeastServingRegistry.register(EDLBlocks.SALAD_BLOCK.getBlock(), Items.BOWL, EDLItems.SALAD.stack(1));
        FeastServingRegistry.register(EDLBlocks.PORK_AND_APPLES_FEAST.getBlock(), Items.BOWL, EDLItems.PORK_AND_APPLES.stack(1));
        FeastServingRegistry.register(EDLBlocks.STUFFED_APPLES_FEAST.getBlock(), Items.BOWL, EDLItems.STUFFED_APPLE.stack(1));
        FeastServingRegistry.register(EDLBlocks.STUFFED_APPLES_FEAST.getBlock(), "iceCream", EDLItems.STUFFED_APPLE_ICE_CREAM.stack(1));
        FeastServingRegistry.register(EDLBlocks.PUMPKIN_ROLL_FEAST.getBlock(), "toolKnife", EDLItems.PUMPKIN_ROLL.stack(1));
        registerPieFeastServing(EDLBlocks.CHEESECAKE, EDLItems.CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.CHOCOLATE_CHEESECAKE, EDLItems.CHOCOLATE_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.HONEY_CHEESECAKE, EDLItems.HONEY_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.GLOW_BERRY_CHEESECAKE, EDLItems.GLOW_BERRY_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.PUMPKIN_CHEESECAKE, EDLItems.PUMPKIN_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.APPLE_CHEESECAKE, EDLItems.APPLE_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.SWEET_BERRY_PIE, EDLItems.SWEET_BERRY_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.GLOW_BERRY_PIE, EDLItems.GLOW_BERRY_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.KEY_LIME_PIE, EDLItems.KEY_LIME_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.LEMON_MERINGUE_PIE, EDLItems.LEMON_MERINGUE_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.CARAMEL_CHEESECAKE, EDLItems.CARAMEL_CHEESECAKE_SLICE);
        registerPieFeastServing(EDLBlocks.PUMPKIN_PIE, EDLItems.PUMPKIN_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.MEAT_PIE, EDLItems.MEAT_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.BACON_EGG_PIE, EDLItems.BACON_EGG_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.STEAK_PICKLED_ONION_PIE, EDLItems.STEAK_PICKLED_ONION_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.MISSISSIPPI_MUD_PIE, EDLItems.MISSISSIPPI_MUD_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.GRASSHOPPER_PIE, EDLItems.GRASSHOPPER_PIE_SLICE);
        registerPieFeastServing(EDLBlocks.COFFEE_CAKE, EDLItems.COFFEE_CAKE_SLICE);
        registerPieFeastServing(EDLBlocks.CHOCOLATE_CAKE, EDLItems.CHOCOLATE_CAKE_SLICE);
        registerPieFeastServing(EDLBlocks.KYIV_CAKE, EDLItems.KYIV_CAKE_SLICE);
        registerPieFeastServing(EDLBlocks.QUICHE, EDLItems.QUICHE_SLICE);
        registerPieFeastServing(EDLBlocks.MILK_TART, EDLItems.MILK_TART_SLICE);
        registerPieFeastServing(EDLBlocks.TARTE_TATIN, EDLItems.TARTE_TATIN_SLICE);
        registerPieFeastServing(EDLBlocks.PANFORTE, EDLItems.PANFORTE_SLICE);
        registerPieFeastServing(EDLBlocks.LEMON_CUCUMBER_CAKE, EDLItems.LEMON_CUCUMBER_CAKE_SLICE);
        registerPieFeastServing(EDLBlocks.MELON_LAYER_CAKE, EDLItems.MELON_LAYER_CAKE_SLICE);
        registerPieFeastServing(EDLBlocks.PAVLOVA, EDLItems.PAVLOVA_SLICE);
        registerBowlFeastServing(EDLBlocks.MELON_FRUIT_SALAD, EDLItems.MELON_FRUIT_SALAD_SERVING);
        registerPieFeastServing(EDLBlocks.BAKED_ALASKA, EDLItems.BAKED_ALASKA_SERVING);
        registerBowlFeastServing(EDLBlocks.JELLY_WHITE_BLOCK, EDLItems.JELLY_WHITE);
        registerBowlFeastServing(EDLBlocks.JELLY_ORANGE_BLOCK, EDLItems.JELLY_ORANGE);
        registerBowlFeastServing(EDLBlocks.JELLY_MAGENTA_BLOCK, EDLItems.JELLY_MAGENTA);
        registerBowlFeastServing(EDLBlocks.JELLY_LIGHT_BLUE_BLOCK, EDLItems.JELLY_LIGHT_BLUE);
        registerBowlFeastServing(EDLBlocks.JELLY_YELLOW_BLOCK, EDLItems.JELLY_YELLOW);
        registerBowlFeastServing(EDLBlocks.JELLY_LIME_BLOCK, EDLItems.JELLY_LIME);
        registerBowlFeastServing(EDLBlocks.JELLY_PINK_BLOCK, EDLItems.JELLY_PINK);
        registerBowlFeastServing(EDLBlocks.JELLY_GREY_BLOCK, EDLItems.JELLY_GREY);
        registerBowlFeastServing(EDLBlocks.JELLY_LIGHT_GREY_BLOCK, EDLItems.JELLY_LIGHT_GREY);
        registerBowlFeastServing(EDLBlocks.JELLY_CYAN_BLOCK, EDLItems.JELLY_CYAN);
        registerBowlFeastServing(EDLBlocks.JELLY_PURPLE_BLOCK, EDLItems.JELLY_PURPLE);
        registerBowlFeastServing(EDLBlocks.JELLY_BLUE_BLOCK, EDLItems.JELLY_BLUE);
        registerBowlFeastServing(EDLBlocks.JELLY_BROWN_BLOCK, EDLItems.JELLY_BROWN);
        registerBowlFeastServing(EDLBlocks.JELLY_GREEN_BLOCK, EDLItems.JELLY_GREEN);
        registerBowlFeastServing(EDLBlocks.JELLY_RED_BLOCK, EDLItems.JELLY_RED);
        registerBowlFeastServing(EDLBlocks.JELLY_BLACK_BLOCK, EDLItems.JELLY_BLACK);
        FeastServingRegistry.register(EDLBlocks.LEMONADE_TRAY.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.LEMONADE.stack(1));
        FeastServingRegistry.register(EDLBlocks.LIMEADE_TRAY.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.LIMEADE.stack(1));
        FeastServingRegistry.register(EDLBlocks.ORANGEADE_TRAY.getBlock(), FeastServingRegistry.EMPTY_HAND, EDLItems.ORANGEADE.stack(1));
        registerBowlFeastServing(EDLBlocks.BAKED_COD, EDLItems.BAKED_COD_SERVING);
        FeastServingRegistry.register(EDLBlocks.SOY_GLAZED_SALMON_BLOCK.getBlock(), itemStack("farmersdelight:cooked_rice"), EDLItems.SOY_GLAZED_SALMON_ITEM.stack(1));
        FeastServingRegistry.register(EDLBlocks.CHEESYMITE_SCROLL_BLOCK.getBlock(), "toolSpoon", EDLItems.CHEESYMITE_SCROLL.stack(1));
        registerFeastLeftovers();
    }

    private static void registerPieFeastServing(EDLBlocks.BlockDefinition pie, EDLItems.ItemDefinition slice) {
        FeastServingRegistry.register(pie.getBlock(), "toolKnife", slice.stack(1));
    }

    private static void registerBowlFeastServing(EDLBlocks.BlockDefinition feast, EDLItems.ItemDefinition serving) {
        FeastServingRegistry.register(feast.getBlock(), Items.BOWL, serving.stack(1));
    }

    private static void registerFeastLeftovers() {
        ItemStack bowl = new ItemStack(Items.BOWL);
        FeastServingRegistry.registerLeftover(EDLBlocks.SALISBURY_STEAK_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.MASHED_POTATO_GRAVY_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.HASH_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.POTROAST_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.MEATLOAF_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.BBQ_RIBS_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.PULLED_PORK_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.RACK_LAMB_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.STIRFRY_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.SOY_GLAZED_SALMON_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.CHEESYMITE_SCROLL_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.BEEF_WELLINGTON_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.HAGGIS_BLOCK.getBlock(), bowl);

        ItemStack servingPot = EDLBlocks.SERVING_POT.stack(1);
        FeastServingRegistry.registerLeftover(EDLBlocks.MACARONI_CHEESE_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.CURRY_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.BEEF_STEW_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.PORK_STEW_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.LAMB_STEW_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.RABBIT_STEW_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.CHICKEN_STEW_BLOCK.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.FISH_STEW_BLOCK.getBlock(), servingPot);

        ItemStack squarePan = EDLBlocks.SQUARE_PAN.stack(1);
        FeastServingRegistry.registerLeftover(EDLBlocks.LASAGNA_BLOCK.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.HOTDISH_BLOCK.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.CORN_PUDDING_FEAST.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.APPLE_CRISP_FEAST.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.POTATO_AU_GRATIN_FEAST.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.CINNAMON_ROLLS_FEAST.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.BROWNIES_BLOCK.getBlock(), squarePan);
        FeastServingRegistry.registerLeftover(EDLBlocks.BLONDIES_BLOCK.getBlock(), squarePan);

        ItemStack skillet = itemStack("farmersdelight:skillet");
        if (!skillet.isEmpty()) {
            FeastServingRegistry.registerLeftover(EDLBlocks.CORNBREAD_FEAST.getBlock(), skillet);
        }

        FeastServingRegistry.registerLeftover(EDLBlocks.STUFFING_FEAST.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.CHILI_CON_CARNE_FEAST.getBlock(), servingPot);
        FeastServingRegistry.registerLeftover(EDLBlocks.WHITE_CHILI_FEAST.getBlock(), servingPot);

        ItemStack tray = EDLBlocks.TRAY.stack(1);
        FeastServingRegistry.registerLeftover(EDLBlocks.FUDGE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.CRISP_RICE_TREATS_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.SCOTCHAROO_BLOCK.getBlock(), tray);

        FeastServingRegistry.registerLeftover(EDLBlocks.PUNCH_FEAST.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.MINT_LAMB_FEAST.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.CHARCUTERIE_BOARD_FEAST.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.STICKY_TOFFEE_PUDDING_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.BLACK_FOREST_TRIFLE_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.BRUSCHETTA_FEAST.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.SALAD_BLOCK.getBlock(), bowl);
        FeastServingRegistry.registerLeftover(EDLBlocks.MELON_FRUIT_SALAD.getBlock(), EDLItems.MELON_RIND.stack(4));

        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_WHITE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_ORANGE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_MAGENTA_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_LIGHT_BLUE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_YELLOW_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_LIME_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_PINK_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_GREY_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_LIGHT_GREY_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_CYAN_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_PURPLE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_BLUE_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_BROWN_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_GREEN_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_RED_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.JELLY_BLACK_BLOCK.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.BAKED_COD.getBlock(), tray);
        FeastServingRegistry.registerLeftover(EDLBlocks.BAKED_ALASKA.getBlock(), tray);

        ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
        FeastServingRegistry.registerLeftover(EDLBlocks.LEMONADE_TRAY.getBlock(), glassBottle);
        FeastServingRegistry.registerLeftover(EDLBlocks.LIMEADE_TRAY.getBlock(), glassBottle);
        FeastServingRegistry.registerLeftover(EDLBlocks.ORANGEADE_TRAY.getBlock(), glassBottle);
    }

    private static void registerOven(String name, java.util.List<MixingBowlIngredient> ingredients,
                                     ItemStack container, ItemStack output, int cookingTime, boolean consumeContainer) {
        if (!output.isEmpty() && !container.isEmpty() && !hasEmptyIngredientDisplay(ingredients)) {
            OvenRecipeManager.register(name, ingredients, container, output, cookingTime, consumeContainer);
        }
    }

    private static void registerBulkOven(String name, EDLItems.ItemDefinition input, EDLItems.ItemDefinition output,
                                         EDLBlocks.BlockDefinition container, int cookingTime) {
        if (!input.isRegistered() || !output.isRegistered() || !container.isRegistered()) {
            return;
        }

        for (int count = 9; count >= 1; count--) {
            registerOven(
                name + "_" + count,
                Collections.singletonList(MixingBowlIngredient.stack(input.stack(count))),
                container.stack(1),
                output.stack(count),
                cookingTime,
                false
            );
        }
    }

    private static void registerBulkOven(String name, String inputOre, EDLItems.ItemDefinition output,
                                         EDLBlocks.BlockDefinition container, int cookingTime) {
        if (!output.isRegistered()) {
            return;
        }
        registerBulkOven(name, inputOre, output.stack(1), container, cookingTime);
    }

    private static void registerBulkOven(String name, String inputOre, ItemStack output,
                                         EDLBlocks.BlockDefinition container, int cookingTime) {
        if (output.isEmpty() || !container.isRegistered()) {
            return;
        }

        for (int count = 9; count >= 1; count--) {
            registerOven(
                name + "_" + count,
                repeat(MixingBowlIngredient.ore(inputOre), count),
                container.stack(1),
                copyWithCount(output, count),
                cookingTime,
                false
            );
        }
    }

    private static void registerBulkOven(String name, ItemStack input, ItemStack output,
                                         EDLBlocks.BlockDefinition container, int cookingTime) {
        if (input.isEmpty() || output.isEmpty() || !container.isRegistered()) {
            return;
        }

        for (int count = 9; count >= 1; count--) {
            registerOven(
                name + "_" + count,
                Collections.singletonList(MixingBowlIngredient.stack(copyWithCount(input, count))),
                container.stack(1),
                copyWithCount(output, count),
                cookingTime,
                false
            );
        }
    }

    private static boolean hasEmptyIngredientDisplay(java.util.List<MixingBowlIngredient> ingredients) {
        for (MixingBowlIngredient ingredient : ingredients) {
            if (ingredient.getDisplayStack().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasEmptyFluidDisplay(java.util.List<MixingBowlFluidIngredient> fluids) {
        for (MixingBowlFluidIngredient fluid : fluids) {
            if (fluid.getDisplayStack().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void registerBottleFluidMappings() {
        BottleFluidRecipeManager.clear();
        registerBottleFluid(EDLItems.COOKING_OIL.stack(1), EDLFluids.OIL.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.OIL.bucketStack());
        registerBottleFluid(EDLItems.PEANUT_BUTTER_BOTTLE.stack(1), EDLFluids.NUT_BUTTER.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.NUT_BUTTER.bucketStack());
        registerBottleFluid(EDLItems.HAZELNUT_SPREAD_BOTTLE.stack(1), EDLFluids.COCOA_NUT_BUTTER_SPREAD.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.COCOA_NUT_BUTTER_SPREAD.bucketStack());
        registerBottleFluid(EDLItems.COCOA_BUTTER_BOTTLE.stack(1), EDLFluids.COCOA_BUTTER.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.COCOA_BUTTER.bucketStack());
        registerBottleFluid(EDLItems.VINEGAR.stack(1), EDLFluids.VINEGAR.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.VINEGAR.bucketStack());
        registerBottleFluid(EDLItems.BBQ_SAUCE.stack(1), EDLFluids.BBQ.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.BBQ.bucketStack());
        registerBottleFluid(EDLItems.KETCHUP.stack(1), EDLFluids.KETCHUP.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.KETCHUP.bucketStack());
        registerBottleFluid(EDLItems.MAYO.stack(1), EDLFluids.MAYO.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.MAYO.bucketStack());
        registerBottleFluid(EDLItems.WHIPPED_CREAM.stack(1), EDLFluids.WHIPPED_CREAM.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.WHIPPED_CREAM.bucketStack());
        registerBottleFluid(EDLItems.CARAMEL_SAUCE.stack(1), EDLFluids.CARAMEL_SAUCE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.CARAMEL_SAUCE.bucketStack());
        registerBottleFluid(EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE.stack(1), EDLFluids.DARK_CHOCOLATE_SYRUP.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.DARK_CHOCOLATE_SYRUP.bucketStack());
        registerBottleFluid(EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE.stack(1), EDLFluids.MILK_CHOCOLATE_SYRUP.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.MILK_CHOCOLATE_SYRUP.bucketStack());
        registerBottleFluid(EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE.stack(1), EDLFluids.WHITE_CHOCOLATE_SYRUP.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.WHITE_CHOCOLATE_SYRUP.bucketStack());
        registerBottleFluid(EDLItems.BLOOD.stack(1), EDLFluids.BLOOD.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.BLOOD.bucketStack());
        registerBottleFluid(EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE.stack(1), EDLFluids.BLOOD_CHOCOLATE_SYRUP.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.BLOOD_CHOCOLATE_SYRUP.bucketStack());
        registerBottleFluid(EDLItems.MARSHMALLOW_FLUFF_BOTTLE.stack(1), EDLFluids.MARSHMALLOW_FLUFF.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.MARSHMALLOW_FLUFF.bucketStack());
        registerBottleFluid(EDLItems.SWEET_BERRY_JUICE.stack(1), EDLFluids.SWEET_BERRY_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.SWEET_BERRY_JUICE.bucketStack());
        registerBottleFluid(EDLItems.GLOW_BERRY_JUICE.stack(1), EDLFluids.GLOW_BERRY_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.GLOW_BERRY_JUICE.bucketStack());
        registerBottleFluid(EDLItems.TOMATO_JUICE.stack(1), EDLFluids.TOMATO_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.TOMATO_JUICE.bucketStack());
        registerBottleFluid(EDLItems.CACTUS_JUICE.stack(1), EDLFluids.CACTUS_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.CACTUS_JUICE.bucketStack());
        registerBottleFluid(EDLItems.MELON_JUICE.stack(1), EDLFluids.MELON_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.MELON_JUICE.bucketStack());
        registerBottleFluid(EDLItems.PICKLE_JUICE.stack(1), EDLFluids.PICKLE_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.PICKLE_JUICE.bucketStack());
        registerBottleFluid(EDLItems.APPLE_CIDER.stack(1), EDLFluids.APPLE_CIDER.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.APPLE_CIDER.bucketStack());
        registerBottleFluid(EDLItems.LEMON_JUICE.stack(1), EDLFluids.LEMON_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.LEMON_JUICE.bucketStack());
        registerBottleFluid(EDLItems.LIME_JUICE.stack(1), EDLFluids.LIME_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.LIME_JUICE.bucketStack());
        registerBottleFluid(EDLItems.ORANGE_JUICE.stack(1), EDLFluids.ORANGE_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.ORANGE_JUICE.bucketStack());
        registerBottleFluid(EDLItems.GRAPEFRUIT_JUICE.stack(1), EDLFluids.GRAPEFRUIT_JUICE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.GRAPEFRUIT_JUICE.bucketStack());
        registerBottleFluid(EDLItems.COFFEE.stack(1), EDLFluids.COFFEE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.COFFEE.bucketStack());
        registerBottleFluid(EDLItems.EGG_WHITE.stack(1), EDLFluids.EGG_WHITE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.EGG_WHITE.bucketStack());
        registerBottleFluid(EDLItems.EGG_MIX.stack(1), EDLFluids.EGG_MIX.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.EGG_MIX.bucketStack());
        registerBottleFluid(EDLItems.GRAVY.stack(1), EDLFluids.GRAVY.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.GRAVY.bucketStack());
        registerBottleFluid(itemStack("farmersdelight:hot_cocoa"), EDLFluids.HOT_COCOA.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.HOT_COCOA.bucketStack());
        registerBottleFluid(EDLItems.MILKSHAKE.stack(1), EDLFluids.MILKSHAKE.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.MILKSHAKE.bucketStack());
        registerBottleFluid(ItemDynamicJam.stack(ItemDynamicJam.Flavor.SWEET_BERRIES, 1), EDLFluids.JAM.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.JAM.bucketStack());
        registerBottleFluid(ItemDynamicJam.stack(ItemDynamicJam.Flavor.GLOW_BERRIES, 1), EDLFluids.GLOW_JAM.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.GLOW_JAM.bucketStack());
        registerBottleFluid(ItemDynamicJam.stack(ItemDynamicJam.Flavor.GOLDEN_APPLE, 1), EDLFluids.GOLDEN_JAM.stack(BottleFluidRecipeManager.BOTTLE_VOLUME), EDLFluids.GOLDEN_JAM.bucketStack());
        registerBottleFluid(itemStack("farmersdelight:milk_bottle"), EDLFluids.milkStack(BottleFluidRecipeManager.BOTTLE_VOLUME), new ItemStack(Items.MILK_BUCKET));
    }

    private static void registerJuicerRecipes() {
        JuicerRecipeManager.clear();
        if (!EDLBlocks.JUICER.isRegistered()) {
            return;
        }

        registerJuicer("sweet_berries", MixingBowlIngredient.ore("cropBerrySweet"), new ItemStack(Items.DYE, 1, 1), EDLFluids.SWEET_BERRY_JUICE.stack(100), 25);
        registerJuicer("glow_berries", MixingBowlIngredient.ore("cropBerryGlow"), new ItemStack(Items.DYE, 1, 14), EDLFluids.GLOW_BERRY_JUICE.stack(100), 25);
        registerJuicer("tomato", MixingBowlIngredient.ore("processedTomato"), new ItemStack(Items.DYE, 1, 1), EDLFluids.TOMATO_JUICE.stack(250), 25);
        registerJuicer("cactus", MixingBowlIngredient.ore("cropCactus"), new ItemStack(Items.DYE, 1, 2), EDLFluids.CACTUS_JUICE.stack(100), 25);
        registerJuicer("melon", MixingBowlIngredient.ore("processedMelon"), new ItemStack(Items.DYE, 1, 9), EDLFluids.MELON_JUICE.stack(100), 25);
        registerJuicer("pickle", MixingBowlIngredient.ore("foodPickled"), new ItemStack(Items.DYE, 1, 15), EDLFluids.PICKLE_JUICE.stack(250), 25);
        registerJuicer("apple", MixingBowlIngredient.ore("processedApple"), new ItemStack(Items.DYE, 1, 1), EDLFluids.APPLE_CIDER.stack(250), 25);
        registerJuicer("lemon_juice", MixingBowlIngredient.ore("processedLemon"), new ItemStack(Items.DYE, 1, 11), EDLFluids.LEMON_JUICE.stack(250), 25);
        registerJuicer("lime_juice", MixingBowlIngredient.ore("processedLime"), new ItemStack(Items.DYE, 1, 10), EDLFluids.LIME_JUICE.stack(250), 25);
        registerJuicer("orange_juice", MixingBowlIngredient.ore("processedOrange"), new ItemStack(Items.DYE, 1, 14), EDLFluids.ORANGE_JUICE.stack(250), 25);
        registerJuicer("grapefruit_juice", MixingBowlIngredient.ore("processedGrapefruit"), new ItemStack(Items.DYE, 1, 9), EDLFluids.GRAPEFRUIT_JUICE.stack(250), 25);
    }

    private static void registerJuicer(String name, MixingBowlIngredient input, ItemStack output,
                                       net.minecraftforge.fluids.FluidStack fluidOutput, int chance) {
        if (input == null || input.getDisplayStack().isEmpty() || fluidOutput == null || fluidOutput.amount <= 0) {
            return;
        }
        JuicerRecipeManager.register(name, input, output, fluidOutput, chance);
    }

    private static void registerVatRecipes() {
        VatRecipeManager.clear();
        if (!EDLBlocks.VAT.isRegistered()) {
            return;
        }
        registerVatYeastAndVinegarRecipes();
        if (EDLBlocks.GHERKINS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "gherkins_block_item",
                repeat(MixingBowlIngredient.ore("cropCucumber"), 4),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.GHERKINS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 24000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLBlocks.PICKLED_BEETS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_beets_block_item",
                append(repeat(MixingBowlIngredient.ore("processedBeetroot"), 4), MixingBowlIngredient.ore("edlSweetener")),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_BEETS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), true, 168000)
                )
            );
        }
        if (EDLBlocks.PICKLED_CARROTS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_carrots_block_item",
                repeat(MixingBowlIngredient.ore("processedCarrot"), 4),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_CARROTS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(null, true, 168000),
                    new VatStage(MixingBowlIngredient.ore("salt"), true, 72000)
                )
            );
        }
        if (EDLBlocks.PICKLED_EGGS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_eggs_block_item",
                append(repeat(MixingBowlIngredient.ore("eggBoiled"), 4), MixingBowlIngredient.ore("edlSweetener")),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_EGGS_BLOCK.stack(1),
                Collections.singletonList(new VatStage(null, true, 168000))
            );
        }
        if (EDLBlocks.PICKLED_FISH_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_fish_block_item",
                append(repeat(MixingBowlIngredient.ore("fishRaw"), 4), MixingBowlIngredient.ore("edlSweetener")),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_FISH_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 24000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLBlocks.PICKLED_SAUSAGE_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_sausage_block_item",
                repeat(MixingBowlIngredient.ore("foodSausageCooked"), 4),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_SAUSAGE_BLOCK.stack(1),
                Collections.singletonList(new VatStage(null, true, 168000))
            );
        }
        if (EDLBlocks.PICKLED_GINGER_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_ginger_block_item",
                repeat(MixingBowlIngredient.ore("processedGinger"), 4),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_GINGER_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 1000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLBlocks.PICKLED_ONIONS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_onions_block_item",
                repeat(MixingBowlIngredient.ore("processedOnion"), 4),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_ONIONS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 24000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLBlocks.PICKLED_RINDS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "pickled_rinds_block_item",
                append(repeat(MixingBowlIngredient.ore("foodMelonRind"), 4), MixingBowlIngredient.ore("edlSweetener")),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PICKLED_RINDS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 12000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLBlocks.PRESERVED_LEMONS_BLOCK.isRegistered()) {
            VatRecipeManager.register(
                "preserved_lemons_block_item",
                repeat(MixingBowlIngredient.ore("processedLemon"), 4),
                EDLFluids.LEMON_JUICE.stack(250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLBlocks.PRESERVED_LEMONS_BLOCK.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 24000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLItems.FISH_SAUCE.isRegistered()) {
            VatRecipeManager.register(
                "fish_sauce_item",
                Collections.singletonList(MixingBowlIngredient.ore("fishRaw")),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLItems.FISH_SAUCE.stack(1),
                Collections.singletonList(new VatStage(MixingBowlIngredient.ore("salt"), true, 336000))
            );
        }
        if (EDLItems.SOAKED_SOYBEANS.isRegistered()) {
            VatRecipeManager.register(
                "soaked_soybeans_item",
                Collections.singletonList(MixingBowlIngredient.ore("cropSoybean")),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 1000),
                new ItemStack(Items.BOWL),
                EDLItems.SOAKED_SOYBEANS.stack(1),
                Collections.singletonList(new VatStage(null, true, 24000))
            );
        }
        if (EDLItems.NATTO.isRegistered()) {
            VatRecipeManager.register(
                "natto_item",
                Collections.singletonList(MixingBowlIngredient.ore("soybeansCooked")),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.BOWL),
                EDLItems.NATTO.stack(1),
                Collections.singletonList(new VatStage(MixingBowlIngredient.ore("yeast"), true, 72000))
            );
        }
        if (EDLItems.SOY_SAUCE.isRegistered()) {
            VatRecipeManager.register(
                "soy_sauce_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("soybeansCooked"),
                    MixingBowlIngredient.ore("foodWheatSeedsCooked")
                ),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.GLASS_BOTTLE),
                EDLItems.SOY_SAUCE.stack(1),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("yeast"), true, 72000),
                    new VatStage(MixingBowlIngredient.ore("salt"), true, 336000)
                )
            );
        }
        if (EDLItems.MISO_PASTE.isRegistered()) {
            VatRecipeManager.register(
                "miso_paste_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("soybeansCooked"),
                    MixingBowlIngredient.stack(itemStack("farmersdelight:cooked_rice")),
                    MixingBowlIngredient.ore("soybeansCooked"),
                    MixingBowlIngredient.stack(itemStack("farmersdelight:cooked_rice")),
                    MixingBowlIngredient.ore("soybeansCooked"),
                    MixingBowlIngredient.stack(itemStack("farmersdelight:cooked_rice"))
                ),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.GLASS_BOTTLE, 6),
                EDLItems.MISO_PASTE.stack(6),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 48000),
                    new VatStage(MixingBowlIngredient.ore("yeast"), true, 336000)
                )
            );
        }
        if (EDLItems.HOT_SAUCE.isRegistered()) {
            VatRecipeManager.register(
                "hot_sauce_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("processedChili"),
                    MixingBowlIngredient.ore("processedGarlic"),
                    MixingBowlIngredient.ore("processedOnion")
                ),
                EDLFluids.VINEGAR.stack(250),
                new ItemStack(Items.GLASS_BOTTLE, 4),
                EDLItems.HOT_SAUCE.stack(4),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 24000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLItems.KIMCHI.isRegistered()) {
            VatRecipeManager.register(
                "kimchi_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("processedCabbage"),
                    MixingBowlIngredient.ore("processedCabbage"),
                    MixingBowlIngredient.ore("processedGinger"),
                    MixingBowlIngredient.ore("processedGarlic"),
                    MixingBowlIngredient.ore("chiliPowder")
                ),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.BOWL, 2),
                EDLItems.KIMCHI.stack(2),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 7000),
                    new VatStage(null, true, 168000)
                )
            );
        }
        if (EDLItems.NAEM_MOO.isRegistered()) {
            VatRecipeManager.register(
                "naem_moo_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("foodGroundPorkRaw"),
                    MixingBowlIngredient.ore("processedGarlic"),
                    MixingBowlIngredient.stack(itemStack("farmersdelight:cooked_rice")),
                    MixingBowlIngredient.ore("processedChili")
                ),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Blocks.WATERLILY, 2),
                EDLItems.NAEM_MOO.stack(2),
                Collections.singletonList(new VatStage(MixingBowlIngredient.ore("salt"), true, 120000))
            );
        }
        if (EDLItems.SAUERKRAUT.isRegistered()) {
            VatRecipeManager.register(
                "sauerkraut_item",
                repeat(MixingBowlIngredient.ore("processedCabbage"), 2),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 250),
                new ItemStack(Items.BOWL, 2),
                EDLItems.SAUERKRAUT.stack(2),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("salt"), false, 1000),
                    new VatStage(null, true, 168000)
                )
            );
        }
    }

    private static void registerVatYeastAndVinegarRecipes() {
        if (EDLItems.YEAST.isRegistered()) {
            VatRecipeManager.register(
                "yeast_item",
                Collections.singletonList(MixingBowlIngredient.ore("processedFruit")),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 1000),
                new ItemStack(Items.GLASS_BOTTLE, 16),
                EDLItems.YEAST.stack(16),
                Collections.singletonList(new VatStage(MixingBowlIngredient.ore("yeast"), false, 168000))
            );
        }
        if (EDLItems.VINEGAR.isRegistered() && EDLItems.YEAST.isRegistered()) {
            VatRecipeManager.register(
                "vinegar_item",
                Arrays.asList(
                    MixingBowlIngredient.ore("vinegarFermentable"),
                    MixingBowlIngredient.stack(new ItemStack(Items.SUGAR))
                ),
                new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 1000),
                new ItemStack(Items.GLASS_BOTTLE, 4),
                EDLItems.VINEGAR.stack(4),
                Arrays.asList(
                    new VatStage(MixingBowlIngredient.ore("yeast"), true, 72000),
                    new VatStage(null, false, 168000)
                )
            );
        }
    }

    private static List<MixingBowlIngredient> repeat(MixingBowlIngredient ingredient, int count) {
        List<MixingBowlIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private static List<MixingBowlIngredient> append(List<MixingBowlIngredient> ingredients, MixingBowlIngredient ingredient) {
        List<MixingBowlIngredient> result = new ArrayList<>(ingredients);
        result.add(ingredient);
        return result;
    }

    private static void registerEvaporatorRecipes() {
        EvaporatorRecipeManager.clear();
        if (!EDLBlocks.EVAPORATOR.isRegistered() || !EDLItems.SALT.isRegistered()) {
            return;
        }
        EvaporatorRecipeManager.register(
            "evaporate_water",
            new net.minecraftforge.fluids.FluidStack(net.minecraftforge.fluids.FluidRegistry.WATER, 1000),
            EDLItems.SALT.stack(1),
            10000
        );
    }

    private static void registerCookingPotRecipes() {
        registerDynamicJamCookingPotRecipes();
        registerCookingPot(
            "cooking_pot/cheese_vinegar",
            new String[]{"minecraft:milk_bucket", "ore:vinegar"},
            EDLItems.CHEESE.stack(2),
            1600,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/yeast_spread",
            new String[]{"ore:yeast", "ore:yeast", "ore:yeast", itemId("salt")},
            EDLItems.YEAST_SPREAD.stack(1),
            new ItemStack(Items.BOWL),
            1600,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/gravy",
            new String[]{"ore:flour", "ore:broth"},
            EDLItems.GRAVY.stack(1),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/cooked_corn",
            new String[]{itemId("corn_seeds")},
            EDLItems.COOKED_CORN.stack(1),
            new ItemStack(Items.BOWL),
            100,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/cooked_soybeans",
            new String[]{"ore:soybeansSoaked"},
            EDLItems.COOKED_SOYBEANS.stack(1),
            new ItemStack(Items.BOWL),
            800,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/soy_milk",
            new String[]{"ore:soybeansMashed"},
            EDLItems.SOY_MILK.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            1600,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/cooked_pasta",
            new String[]{"farmersdelight:raw_pasta"},
            EDLItems.COOKED_PASTA.stack(1),
            new ItemStack(Items.BOWL),
            100,
            0.15F
        );
        registerCookingPot(
            "cooking_pot/apple_sauce",
            new String[]{"minecraft:apple", "minecraft:apple", "minecraft:apple", "minecraft:sugar"},
            EDLItems.APPLE_SAUCE.stack(4),
            new ItemStack(Items.BOWL),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/congee",
            new String[]{"farmersdelight:rice", "ore:processedGinger"},
            EDLItems.CONGEE.stack(1),
            new ItemStack(Items.BOWL),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/boiled_egg",
            new String[]{"minecraft:egg"},
            EDLItems.BOILED_EGG.stack(1),
            100,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/coffee",
            new String[]{"ore:groundCoffee"},
            EDLItems.COFFEE.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            100,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/tea",
            new String[]{"ore:teaIngredient", "ore:teaIngredient", "ore:edlSweetener"},
            EDLItems.TEA.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            100,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/pork_tenderloin",
            new String[]{"minecraft:porkchop", itemId("breading_misanplas")},
            EDLItems.PORK_TENDERLOIN.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/sos",
            new String[]{"ore:foodBeefScrapsRaw", "ore:flour", "ore:foodMilk"},
            EDLItems.SOS.stack(1),
            EDLItems.BREAD_SLICE.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/miso_soup",
            new String[]{"ore:broth", "ore:misoPaste", "ore:foodKelpDried", "ore:misoSoupIngredient"},
            EDLItems.MISO_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/sauerkraut_soup",
            new String[]{"ore:broth", "ore:sauerkraut", "ore:foodBaconRaw", "ore:processedCarrot", "ore:processedOnion", "ore:processedPotato"},
            EDLItems.SAUERKRAUT_SOUP.stack(3),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/sauerkraut_and_sausage",
            new String[]{"ore:foodSausageRaw", "ore:sauerkraut", "ore:foodBaconRaw", "minecraft:sugar", "ore:processedOnion", "ore:processedApple"},
            EDLItems.SAUERKRAUT_SAUSAGE.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/zupa_ogorkowa",
            new String[]{"ore:processedOnion", "ore:processedCarrot", "ore:processedPickledCucumber", "ore:processedPotato", "ore:broth", "ore:pickleJuice"},
            EDLItems.ZUPA_OGORKOWA.stack(3),
            new ItemStack(Items.BOWL),
            100,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/preserved_lemon_pasta",
            new String[]{"ore:pasta", "ore:butter", itemId("preserved_lemon_item"), "ore:cheese", "ore:processedGarlic"},
            EDLItems.PRESERVED_LEMON_PASTA.stack(1),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/beef_bulgogi",
            new String[]{"ore:foodBeefCubedRaw", "ore:processedOnion", "ore:chiliPowder", "ore:hotSauce", "ore:processedGarlic", "ore:soySauce"},
            EDLItems.BEEF_BULGOGI.stack(2),
            itemStack("farmersdelight:cooked_rice"),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/caramel_chicken",
            new String[]{"ore:foodChickenThighRaw", "ore:edlSweetener", "ore:processedOnion", "ore:processedGarlic", "ore:fishSauce", "ore:soySauce"},
            EDLItems.CARAMEL_CHICKEN.stack(2),
            itemStack("farmersdelight:cooked_rice"),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/edamame",
            new String[]{itemId("soybean_pod"), "ore:salt"},
            EDLItems.EDAMAME.stack(1),
            new ItemStack(Items.BOWL),
            100,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/honey_chili_chicken",
            new String[]{"ore:foodCubedChickenRaw", "ore:honeyBottle", "ore:processedChili", "ore:processedGarlic", "ore:processedGinger", "ore:soySauce"},
            EDLItems.HONEY_CHILI_CHICKEN.stack(2),
            itemStack("farmersdelight:cooked_rice"),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/kimchi_fried_rice",
            new String[]{"farmersdelight:cooked_rice", "ore:kimchi", "ore:hotSauce", "ore:foodKelpDried", "ore:cookingOil", "farmersdelight:fried_egg"},
            EDLItems.KIMCHI_FRIED_RICE.stack(2),
            new ItemStack(Items.BOWL),
            100,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/kongjang",
            new String[]{"ore:soybeansSoaked", "ore:soybeansSoaked", "ore:soySauce", "minecraft:sugar", "ore:processedGarlic", "ore:cookingOil"},
            EDLItems.KONGJANG.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/potato_chips",
            new String[]{"ore:processedPotato", itemId("cooking_oil")},
            EDLItems.POTATO_CHIPS.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/french_fries",
            new String[]{itemId("potato_sticks"), itemId("cooking_oil")},
            EDLItems.FRENCH_FRIES.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/potato_soup",
            new String[]{"minecraft:potato", "minecraft:potato", "farmersdelight:onion", "farmersdelight:bone_broth", "minecraft:milk_bucket"},
            EDLItems.POTATO_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/tomato_soup",
            new String[]{"farmersdelight:tomato", "farmersdelight:tomato", "farmersdelight:onion", "farmersdelight:bone_broth", "minecraft:milk_bucket"},
            EDLItems.TOMATO_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/carrot_soup",
            new String[]{"minecraft:carrot", "minecraft:carrot", "farmersdelight:onion", "farmersdelight:bone_broth", "minecraft:milk_bucket"},
            EDLItems.CARROT_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/cactus_soup",
            new String[]{itemId("cooked_cactus"), itemId("cooked_cactus"), "ore:processedTomato", "ore:processedOnion", "farmersdelight:bone_broth"},
            EDLItems.CACTUS_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/lugaw",
            new String[]{"farmersdelight:rice", "ore:processedGinger", "ore:processedOnion", "farmersdelight:bone_broth", itemId("boiled_egg"), "minecraft:cooked_chicken"},
            EDLItems.LUGAW.stack(1),
            new ItemStack(Items.BOWL),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/lugaw_chicken_cuts",
            new String[]{"farmersdelight:rice", "ore:processedGinger", "ore:processedOnion", "farmersdelight:bone_broth", itemId("boiled_egg"), "farmersdelight:cooked_chicken_cuts"},
            EDLItems.LUGAW.stack(1),
            new ItemStack(Items.BOWL),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/corn_chowder",
            new String[]{itemId("cooked_corn"), "ore:processedPotato", "farmersdelight:cooked_bacon", "ore:processedOnion", "minecraft:milk_bucket", "farmersdelight:bone_broth"},
            EDLItems.CORN_CHOWDER.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/corn_chowder_raw_bacon",
            new String[]{itemId("cooked_corn"), "ore:processedPotato", "farmersdelight:bacon", "ore:processedOnion", "minecraft:milk_bucket", "farmersdelight:bone_broth"},
            EDLItems.CORN_CHOWDER.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/pamonha",
            new String[]{itemId("corn_seeds"), itemId("corn_seeds"), "minecraft:milk_bucket", itemId("corn_husk")},
            EDLItems.PAMONHA.stack(2),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/fish_cakes_cod",
            new String[]{"minecraft:cooked_fish", "ore:cropOnion", itemId("grated_potato"), itemId("breading_misanplas")},
            EDLItems.FISH_CAKES.stack(3),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fish_cakes_salmon",
            new String[]{"minecraft:cooked_fish@1", "ore:cropOnion", itemId("grated_potato"), itemId("breading_misanplas")},
            EDLItems.FISH_CAKES.stack(3),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fried_mushrooms_brown",
            new String[]{"minecraft:brown_mushroom", "minecraft:brown_mushroom", itemId("breading_misanplas")},
            EDLItems.FRIED_MUSHROOMS.stack(3),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fried_mushrooms_red",
            new String[]{"minecraft:red_mushroom", "minecraft:red_mushroom", itemId("breading_misanplas")},
            EDLItems.FRIED_MUSHROOMS.stack(3),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fried_fish",
            new String[]{"ore:foodFishRaw", itemId("breading_misanplas")},
            EDLItems.FRIED_FISH.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fish_soup",
            new String[]{"ore:foodFishRaw", "ore:foodFishRaw", "ore:processedOnion", "farmersdelight:bone_broth", "ore:foodKelpDried"},
            EDLItems.FISH_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fried_brains",
            new String[]{"ore:brain", itemId("breading_misanplas")},
            EDLItems.FRIED_BRAINS.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/oxtail_soup",
            new String[]{"ore:foodOxtailRaw", "minecraft:carrot", "farmersdelight:bone_broth", "ore:processedTomato"},
            EDLItems.OXTAIL_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/liver_onions",
            new String[]{"ore:foodOffalLiverRaw", "ore:processedOnion"},
            EDLItems.LIVER_ONIONS.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/mulligatawny_soup",
            new String[]{itemId("curry_powder"), "ore:processedApple", "ore:processedOnion", "farmersdelight:bone_broth", "ore:foodChickenRaw", "farmersdelight:rice"},
            EDLItems.MULLIGATAWNY_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/aebleflaesk",
            new String[]{"ore:processedApple", "ore:processedOnion", "farmersdelight:bacon", "ore:edlSweetener"},
            EDLItems.AEBLEFLAESK.stack(1),
            EDLItems.TOAST.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/cream_corn",
            new String[]{itemId("cooked_corn"), "minecraft:milk_bucket", itemId("butter")},
            EDLItems.CREAM_CORN.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/corn_fritters",
            new String[]{itemId("cooked_corn"), itemId("flour"), "ore:edlSweetener", itemId("egg_mix"), itemId("cooking_oil"), "ore:processedOnion"},
            EDLItems.CORN_FRITTERS.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/stewed_apples",
            new String[]{"ore:appleSliced", "ore:appleSliced", "ore:edlSweetener", itemId("butter")},
            EDLItems.STEWED_APPLES.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/apple_fritters",
            new String[]{"ore:processedApple", itemId("flour"), "ore:edlSweetener", itemId("egg_mix"), itemId("cooking_oil"), "ore:edlSweetener"},
            EDLItems.APPLE_FRITTERS.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/penne_all_arrabbiata",
            new String[]{itemId("penne"), "ore:processedGarlic", "ore:processedChili", "farmersdelight:tomato_sauce", itemId("cooking_oil")},
            EDLItems.PENNE_ALL_ARRABBIATA.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/peanut_brittle",
            new String[]{"ore:edlSweetener", "ore:edlSweetener", itemId("butter"), itemId("roasted_peanuts")},
            EDLItems.PEANUT_BRITTLE.stack(4),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/butterscotch",
            new String[]{"ore:edlSweetener", itemId("butter")},
            EDLItems.BUTTERSCOTCH.stack(2),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/caramel_sauce",
            new String[]{"ore:edlSweetener", itemId("butter"), "minecraft:milk_bucket"},
            EDLItems.CARAMEL_SAUCE.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/caramel_candy",
            new String[]{itemId("caramel_sauce")},
            EDLItems.CARAMEL_CANDY.stack(2),
            new ItemStack(Items.PAPER),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/caramel_apple",
            new String[]{"minecraft:apple", itemId("caramel_sauce")},
            EDLItems.CARAMEL_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/caramel_golden_apple",
            new String[]{"minecraft:golden_apple", itemId("caramel_sauce")},
            EDLItems.CARAMEL_GOLDEN_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/caramel_custard",
            new String[]{itemId("caramel_sauce"), "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.CARAMEL_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/chocolate_custard",
            new String[]{itemId("cocoa_powder"), "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.CHOCOLATE_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/pumpkin_custard",
            new String[]{"ore:processedPumpkin", "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.PUMPKIN_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/honey_custard",
            new String[]{"ore:honeyBottle", "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.HONEY_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/sweet_berry_custard",
            new String[]{"ore:cropBerrySweet", "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.SWEET_BERRY_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/apple_custard",
            new String[]{"ore:processedApple", "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.APPLE_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/nut_butter_custard",
            new String[]{"ore:nutButter", "minecraft:milk_bucket", "minecraft:egg", "ore:edlSweetener"},
            EDLItems.NUT_BUTTER_CUSTARD.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/ketchup_jar",
            new String[]{"ore:processedTomato", "ore:processedTomato", "ore:edlSweetener", itemId("vinegar")},
            EDLItems.KETCHUP.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/bbq_sugar",
            new String[]{"ore:processedTomato", "ore:processedOnion", "ore:edlSweetener", itemId("vinegar")},
            EDLItems.BBQ_SAUCE.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/seaweed_paste",
            new String[]{"ore:foodKelpDried", "ore:foodKelpDried", "ore:foodKelpDried"},
            EDLItems.SEAWEED_PASTE.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/seaweed_crisps",
            new String[]{"ore:foodKelpDried", "ore:foodKelpDried", itemId("flour"), itemId("cooking_oil")},
            EDLItems.SEAWEED_CRISPS.stack(1),
            100,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/salisbury_steak_block",
            new String[]{"ore:groundBeefRaw", "ore:groundBeefRaw", "ore:groundBeefRaw", "ore:cropOnion", "ore:listAllmushroom", "ore:gravy"},
            EDLBlocks.SALISBURY_STEAK_BLOCK.stack(1),
            new ItemStack(Items.BOWL),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/mashed_potato_gravy_block",
            new String[]{"minecraft:potato", "minecraft:potato", "minecraft:potato", "ore:gravy", "ore:butter", "ore:foodMilk"},
            EDLBlocks.MASHED_POTATO_GRAVY_BLOCK.stack(1),
            new ItemStack(Items.BOWL),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/hash_block",
            new String[]{"ore:cropPotatoGrated", "ore:cropPotatoGrated", "ore:cropOnion", "ore:cookingOil", "ore:meat"},
            EDLBlocks.HASH_BLOCK.stack(1),
            itemStack("farmersdelight:skillet"),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/beef_stew_block",
            new String[]{"minecraft:beef", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.BEEF_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/pork_stew_block",
            new String[]{"minecraft:porkchop", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.PORK_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/lamb_stew_block",
            new String[]{"minecraft:mutton", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.LAMB_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/rabbit_stew_block",
            new String[]{"minecraft:rabbit", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.RABBIT_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/chicken_stew_block",
            new String[]{"minecraft:chicken", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.CHICKEN_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/fish_stew_block",
            new String[]{"ore:foodFishRaw", "minecraft:carrot", "minecraft:potato", "ore:cropOnion", "ore:broth", "ore:flour"},
            EDLBlocks.FISH_STEW_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/curry_block",
            new String[]{"ore:foodChickenRaw", itemId("curry_powder"), "ore:broth", "ore:cropOnion"},
            EDLBlocks.CURRY_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/macaroni_cheese_block",
            new String[]{itemId("macaroni"), itemId("macaroni"), "ore:butter", "ore:foodMilk", "ore:cheese", "ore:cheese"},
            EDLBlocks.MACARONI_CHEESE_BLOCK.stack(1),
            EDLBlocks.SERVING_POT.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/alfredo_sauce",
            new String[]{"minecraft:milk_bucket", "ore:cheese", "ore:butter"},
            EDLItems.ALFREDO_SAUCE.stack(1),
            new ItemStack(Items.BOWL),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/mushroom_risotto_brown",
            new String[]{"minecraft:brown_mushroom", "ore:cheese", "ore:butter", "farmersdelight:bone_broth", "farmersdelight:rice", "ore:processedOnion"},
            EDLItems.MUSHROOM_RISOTTO.stack(2),
            new ItemStack(Items.BOWL),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/mushroom_risotto_red",
            new String[]{"minecraft:red_mushroom", "ore:cheese", "ore:butter", "farmersdelight:bone_broth", "farmersdelight:rice", "ore:processedOnion"},
            EDLItems.MUSHROOM_RISOTTO.stack(2),
            new ItemStack(Items.BOWL),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/stuffed_cactus",
            new String[]{itemId("cooked_cactus"), itemId("cooked_cactus"), "ore:cheese", itemId("breading_misanplas")},
            EDLItems.STUFFED_CACTUS.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/hazelnut_soup",
            new String[]{"ore:nutHazelnutRoasted", "minecraft:milk_bucket", "ore:processedOnion", "ore:processedPotato", itemId("butter"), "ore:foodBaconCooked"},
            EDLItems.HAZELNUT_SOUP.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/onion_soup",
            new String[]{"ore:processedOnion", "ore:processedOnion", itemId("butter"), "farmersdelight:bone_broth", "ore:breadSliced", "ore:cheese"},
            EDLItems.ONION_SOUP.stack(3),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/onion_bhaji",
            new String[]{"ore:processedOnion", "ore:processedOnion", itemId("flour"), itemId("curry_powder"), itemId("cooking_oil")},
            EDLItems.ONION_BHAJI.stack(2),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/borscht",
            new String[]{"farmersdelight:bone_broth", "ore:processedBeetroot", "ore:processedCarrot", "farmersdelight:cabbage", "ore:processedPotato", "ore:processedTomato"},
            EDLItems.BORSCHT.stack(4),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/devilled_sausages",
            new String[]{"farmersdelight:bone_broth", "ore:processedOnion", "ore:foodSausageRaw", "ore:processedApple", "ore:processedGarlic"},
            EDLItems.DEVILLED_SAUSAGES.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/fried_chicken",
            new String[]{"ore:foodChickenRaw", itemId("breading_misanplas")},
            EDLItems.FRIED_CHICKEN.stack(1),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/lemon_curd",
            new String[]{"ore:butter", "ore:edlSweetener", itemId("lemon_juice"), itemId("lemon_zest"), "ore:eggOrYolk", "ore:eggOrYolk"},
            EDLItems.LEMON_CURD.stack(2),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/orange_chicken",
            new String[]{"ore:foodCubedChickenRaw", "ore:edlSweetener", "ore:orangeJuice", itemId("breading_misanplas"), "ore:processedGarlic", "ore:processedGinger"},
            EDLItems.ORANGE_CHICKEN.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/orange_chicken_zest",
            new String[]{"ore:foodCubedChickenRaw", "ore:edlSweetener", "ore:zestOrange", itemId("breading_misanplas"), "ore:processedGarlic", "ore:processedGinger"},
            EDLItems.ORANGE_CHICKEN.stack(2),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/melon_rind_stirfry",
            new String[]{itemId("melon_rind"), "ore:processedCarrot", "ore:fishSauce", "ore:soySauce", "ore:processedGarlic", "ore:processedGinger"},
            EDLItems.MELON_RIND_STIRFRY.stack(1),
            itemStack("farmersdelight:cooked_rice"),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/lemon_posset",
            new String[]{"ore:zestLemon", "ore:foodMilk", "ore:edlSweetener", "ore:lemonJuice", "ore:cropBerrySweet"},
            EDLItems.LEMON_POSSET.stack(3),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/melon_lime_glazed_chicken_breast",
            new String[]{"ore:foodChickenBreastRaw", "ore:limeJuice", "ore:processedMelon", "ore:chiliPowder", "ore:honeyBottle"},
            EDLItems.MELON_LIME_GLAZED_CHICKEN.stack(1),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/melon_lime_glazed_chicken_thigh",
            new String[]{"ore:foodChickenThighRaw", "ore:limeJuice", "ore:processedMelon", "ore:chiliPowder", "ore:honeyBottle"},
            EDLItems.MELON_LIME_GLAZED_CHICKEN.stack(1),
            new ItemStack(Items.BOWL),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/rice_pudding",
            new String[]{"farmersdelight:rice", "farmersdelight:rice", "minecraft:milk_bucket", "ore:edlSweetener", "ore:cinnamonGround"},
            EDLItems.RICE_PUDDING.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/eggnog",
            new String[]{"farmersdelight:milk_bottle", "ore:cinnamonGround", "minecraft:egg", "ore:edlSweetener", "minecraft:egg"},
            EDLItems.EGGNOG.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/ginger_beer",
            new String[]{"ore:processedGinger", "ore:edlSweetener", "ore:yeast"},
            EDLItems.GINGER_BEER.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/horchata",
            new String[]{"farmersdelight:rice", "ore:edlSweetener", "ore:cinnamonGround", "minecraft:milk_bucket"},
            EDLItems.HORCHATA.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            1600,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/xocolatl",
            new String[]{"minecraft:milk_bucket", "ore:edlSweetener", "ore:chocolateSyrup", "ore:chiliPowder"},
            EDLItems.XOCOLATL.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/xocolatl_beans",
            new String[]{"minecraft:milk_bucket", "ore:edlSweetener", "ore:cocoaPowder", "ore:chiliPowder"},
            EDLItems.XOCOLATL.stack(1),
            new ItemStack(Items.GLASS_BOTTLE),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/glazed_carrot",
            new String[]{"minecraft:carrot", "minecraft:carrot", "minecraft:carrot", itemId("butter"), "ore:edlSweetener"},
            EDLItems.GLAZED_CARROT.stack(3),
            200,
            0.35F
        );
        registerCandyCookingPot("candy_black", EDLItems.CANDY_BLACK, "dyeBlack");
        registerCandyCookingPot("candy_blue", EDLItems.CANDY_BLUE, "dyeBlue");
        registerCandyCookingPot("candy_brown", EDLItems.CANDY_BROWN, "dyeBrown");
        registerCandyCookingPot("candy_cyan", EDLItems.CANDY_CYAN, "dyeCyan");
        registerCandyCookingPot("candy_gray", EDLItems.CANDY_GRAY, "dyeGray");
        registerCandyCookingPot("candy_green", EDLItems.CANDY_GREEN, "dyeGreen");
        registerCandyCookingPot("candy_light_blue", EDLItems.CANDY_LIGHT_BLUE, "dyeLightBlue");
        registerCandyCookingPot("candy_light_gray", EDLItems.CANDY_LIGHT_GRAY, "dyeLightGray");
        registerCandyCookingPot("candy_lime", EDLItems.CANDY_LIME, "dyeLime");
        registerCandyCookingPot("candy_magenta", EDLItems.CANDY_MAGENTA, "dyeMagenta");
        registerCandyCookingPot("candy_orange", EDLItems.CANDY_ORANGE, "dyeOrange");
        registerCandyCookingPot("candy_pink", EDLItems.CANDY_PINK, "dyePink");
        registerCandyCookingPot("candy_purple", EDLItems.CANDY_PURPLE, "dyePurple");
        registerCandyCookingPot("candy_red", EDLItems.CANDY_RED, "dyeRed");
        registerCandyCookingPot("candy_white", EDLItems.CANDY_WHITE, "dyeWhite");
        registerCandyCookingPot("candy_yellow", EDLItems.CANDY_YELLOW, "dyeYellow");
        registerMintCandyCookingPot("mint_candy_blue", EDLItems.MINT_CANDY_BLUE, "dyeBlue");
        registerMintCandyCookingPot("mint_candy_green", EDLItems.MINT_CANDY_GREEN, "dyeGreen");
        registerMintCandyCookingPot("mint_candy_red", EDLItems.MINT_CANDY_RED, "dyeRed");
        registerCookingPot(
            "cooking_pot/candy_apple",
            new String[]{"minecraft:apple", "ore:candy", "ore:candy"},
            EDLItems.CANDY_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/candy_apple_sugar",
            new String[]{"minecraft:apple", "ore:edlSweetener"},
            EDLItems.CANDY_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/candy_golden_apple",
            new String[]{"minecraft:golden_apple", "ore:candy", "ore:candy"},
            EDLItems.CANDY_GOLDEN_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/candy_golden_apple_sugar",
            new String[]{"minecraft:golden_apple", "ore:edlSweetener"},
            EDLItems.CANDY_GOLDEN_APPLE.stack(1),
            new ItemStack(Items.STICK),
            400,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/candied_ginger",
            new String[]{"ore:processedGinger", "ore:edlSweetener"},
            EDLItems.CANDIED_GINGER.stack(4),
            new ItemStack(Items.PAPER),
            200,
            0.35F
        );
        registerCookingPot(
            "cooking_pot/candied_citrus_zest",
            new String[]{itemId("lemon_zest"), itemId("lime_zest"), itemId("orange_zest"), "ore:edlSweetener"},
            EDLItems.CANDIED_CITRUS_ZEST.stack(3),
            200,
            1.0F
        );
        registerCookingPot(
            "cooking_pot/chicken_fried_steak",
            new String[]{"minecraft:beef", itemId("breading_misanplas"), itemId("gravy_boat_item")},
            EDLItems.CHICKEN_FRIED_STEAK.stack(1),
            200,
            0.35F
        );
    }

    private static void registerCandyCookingPot(String name, EDLItems.ItemDefinition result, String dyeOre) {
        registerCookingPot(
            "cooking_pot/" + name,
            new String[]{"ore:edlSweetener", "ore:" + dyeOre},
            result.stack(4),
            new ItemStack(Items.PAPER),
            100,
            0.35F
        );
    }

    private static void registerMintCandyCookingPot(String name, EDLItems.ItemDefinition result, String dyeOre) {
        registerCookingPot(
            "cooking_pot/" + name,
            new String[]{"ore:edlSweetener", "ore:" + dyeOre, itemId("mint")},
            result.stack(4),
            100,
            0.35F
        );
    }

    private static void registerDynamicJamCookingPotRecipes() {
        registerDynamicJam("apple", ItemDynamicJam.Flavor.APPLE, "ore:processedApple");
        registerDynamicJam("carrot", ItemDynamicJam.Flavor.CARROT, "ore:processedCarrot");
        registerDynamicJam("chorus_fruit", ItemDynamicJam.Flavor.CHORUS_FRUIT, "minecraft:chorus_fruit");
        registerDynamicJam("glow_berries", ItemDynamicJam.Flavor.GLOW_BERRIES, "ore:cropBerryGlow");
        registerDynamicJam("golden_apple", ItemDynamicJam.Flavor.GOLDEN_APPLE, "minecraft:golden_apple");
        registerDynamicJam("grapefruit", ItemDynamicJam.Flavor.GRAPEFRUIT, "ore:processedGrapefruit");
        registerDynamicJam("lemon", ItemDynamicJam.Flavor.LEMON, "ore:processedLemon");
        registerDynamicJam("lime", ItemDynamicJam.Flavor.LIME, "ore:processedLime");
        registerDynamicJam("melon", ItemDynamicJam.Flavor.MELON, "ore:processedMelon");
        registerDynamicJam("orange", ItemDynamicJam.Flavor.ORANGE, "ore:processedOrange");
        registerDynamicJam("sweet_berries", ItemDynamicJam.Flavor.SWEET_BERRIES, "ore:cropBerrySweet");
        registerCookingPot(
            "cooking_pot/dynamic_jam_mint",
            new String[]{"ore:cropMint", "ore:cropMint", "ore:cropMint", "minecraft:sugar", "minecraft:sugar", "ore:gelatin"},
            ItemDynamicJam.stack(ItemDynamicJam.Flavor.MINT, 1),
            new ItemStack(Items.GLASS_BOTTLE),
            800,
            1.0F
        );
    }

    private static void registerDynamicJam(String name, ItemDynamicJam.Flavor flavor, String ingredient) {
        registerCookingPot(
            "cooking_pot/dynamic_jam_" + name,
            new String[]{ingredient, ingredient, ingredient, "minecraft:sugar", "minecraft:sugar", "minecraft:sugar"},
            ItemDynamicJam.stack(flavor, 1),
            new ItemStack(Items.GLASS_BOTTLE),
            800,
            1.0F
        );
    }

    private static void registerCookingPot(String name, String[] inputs, ItemStack result, ItemStack container, int cookingTime, float experience) {
        if (result.isEmpty() || container.isEmpty() || !hasLocalItems(inputs) || !hasExistingItemTokens(inputs)) {
            return;
        }

        CookingPotRecipeApi.registerRecipe(recipeId(name), inputs, result, container, cookingTime, experience);
    }

    private static void registerCookingPot(String name, String[] inputs, ItemStack result, int cookingTime, float experience) {
        if (result.isEmpty() || !hasLocalItems(inputs) || !hasExistingItemTokens(inputs)) {
            return;
        }

        CookingPotRecipeApi.registerRecipe(recipeId(name), inputs, result, cookingTime, experience);
    }

    private static void registerBottleFluid(ItemStack bottle, net.minecraftforge.fluids.FluidStack fluid, ItemStack bucket) {
        BottleFluidRecipeManager.register(bottle, fluid, bucket);
    }

    private static void registerBottleFluidRecipes(IForgeRegistry<IRecipe> registry) {
        registerBottleBucketRecipe(registry, "oil", EDLFluids.OIL.bucketStack(), EDLItems.COOKING_OIL.stack(1));
        registerBottleBucketRecipe(registry, "nut_butter", EDLFluids.NUT_BUTTER.bucketStack(), EDLItems.PEANUT_BUTTER_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "cocoa_nut_butter_spread", EDLFluids.COCOA_NUT_BUTTER_SPREAD.bucketStack(), EDLItems.HAZELNUT_SPREAD_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "cocoa_butter", EDLFluids.COCOA_BUTTER.bucketStack(), EDLItems.COCOA_BUTTER_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "vinegar", EDLFluids.VINEGAR.bucketStack(), EDLItems.VINEGAR.stack(1));
        registerBottleBucketRecipe(registry, "bbq", EDLFluids.BBQ.bucketStack(), EDLItems.BBQ_SAUCE.stack(1));
        registerBottleBucketRecipe(registry, "ketchup", EDLFluids.KETCHUP.bucketStack(), EDLItems.KETCHUP.stack(1));
        registerBottleBucketRecipe(registry, "mayo", EDLFluids.MAYO.bucketStack(), EDLItems.MAYO.stack(1));
        registerBottleBucketRecipe(registry, "whipped_cream", EDLFluids.WHIPPED_CREAM.bucketStack(), EDLItems.WHIPPED_CREAM.stack(1), Items.BOWL);
        registerBottleBucketRecipe(registry, "caramel_sauce", EDLFluids.CARAMEL_SAUCE.bucketStack(), EDLItems.CARAMEL_SAUCE.stack(1));
        registerBottleBucketRecipe(registry, "dark_chocolate_syrup", EDLFluids.DARK_CHOCOLATE_SYRUP.bucketStack(), EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "milk_chocolate_syrup", EDLFluids.MILK_CHOCOLATE_SYRUP.bucketStack(), EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "white_chocolate_syrup", EDLFluids.WHITE_CHOCOLATE_SYRUP.bucketStack(), EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "blood", EDLFluids.BLOOD.bucketStack(), EDLItems.BLOOD.stack(1));
        registerBottleBucketRecipe(registry, "blood_chocolate_syrup", EDLFluids.BLOOD_CHOCOLATE_SYRUP.bucketStack(), EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "marshmallow_fluff", EDLFluids.MARSHMALLOW_FLUFF.bucketStack(), EDLItems.MARSHMALLOW_FLUFF_BOTTLE.stack(1));
        registerBottleBucketRecipe(registry, "broth", EDLFluids.BROTH.bucketStack(), itemStack("farmersdelight:bone_broth"), Items.BOWL);
        registerBottleBucketRecipe(registry, "tea", EDLFluids.TEA.bucketStack(), EDLItems.TEA.stack(1));
        registerBottleBucketRecipe(registry, "sweet_berry_juice", EDLFluids.SWEET_BERRY_JUICE.bucketStack(), EDLItems.SWEET_BERRY_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "glow_berry_juice", EDLFluids.GLOW_BERRY_JUICE.bucketStack(), EDLItems.GLOW_BERRY_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "tomato_juice", EDLFluids.TOMATO_JUICE.bucketStack(), EDLItems.TOMATO_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "cactus_juice", EDLFluids.CACTUS_JUICE.bucketStack(), EDLItems.CACTUS_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "melon_juice", EDLFluids.MELON_JUICE.bucketStack(), EDLItems.MELON_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "pickle_juice", EDLFluids.PICKLE_JUICE.bucketStack(), EDLItems.PICKLE_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "apple_cider", EDLFluids.APPLE_CIDER.bucketStack(), EDLItems.APPLE_CIDER.stack(1));
        registerBottleBucketRecipe(registry, "lemon_juice", EDLFluids.LEMON_JUICE.bucketStack(), EDLItems.LEMON_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "lime_juice", EDLFluids.LIME_JUICE.bucketStack(), EDLItems.LIME_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "orange_juice", EDLFluids.ORANGE_JUICE.bucketStack(), EDLItems.ORANGE_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "grapefruit_juice", EDLFluids.GRAPEFRUIT_JUICE.bucketStack(), EDLItems.GRAPEFRUIT_JUICE.stack(1));
        registerBottleBucketRecipe(registry, "coffee", EDLFluids.COFFEE.bucketStack(), EDLItems.COFFEE.stack(1));
        registerBottleBucketRecipe(registry, "egg_white", EDLFluids.EGG_WHITE.bucketStack(), EDLItems.EGG_WHITE.stack(1), Items.BOWL);
        registerBottleBucketRecipe(registry, "egg_mix", EDLFluids.EGG_MIX.bucketStack(), EDLItems.EGG_MIX.stack(1), Items.BOWL);
        registerBottleBucketRecipe(registry, "gravy", EDLFluids.GRAVY.bucketStack(), EDLItems.GRAVY.stack(1), Items.BOWL);
        registerBottleBucketRecipe(registry, "hot_cocoa", EDLFluids.HOT_COCOA.bucketStack(), itemStack("farmersdelight:hot_cocoa"));
        registerBottleBucketRecipe(registry, "milkshake", EDLFluids.MILKSHAKE.bucketStack(), EDLItems.MILKSHAKE.stack(1));
        registerBottleBucketRecipe(registry, "jam", EDLFluids.JAM.bucketStack(), ItemDynamicJam.stack(ItemDynamicJam.Flavor.SWEET_BERRIES, 1));
        registerBottleBucketRecipe(registry, "glow_jam", EDLFluids.GLOW_JAM.bucketStack(), ItemDynamicJam.stack(ItemDynamicJam.Flavor.GLOW_BERRIES, 1));
        registerBottleBucketRecipe(registry, "golden_jam", EDLFluids.GOLDEN_JAM.bucketStack(), ItemDynamicJam.stack(ItemDynamicJam.Flavor.GOLDEN_APPLE, 1));
    }

    private static void registerBottleBucketRecipe(IForgeRegistry<IRecipe> registry, String name, ItemStack bucket, ItemStack bottle) {
        registerBottleBucketRecipe(registry, name, bucket, bottle, Items.GLASS_BOTTLE);
    }

    private static void registerBottleBucketRecipe(IForgeRegistry<IRecipe> registry, String name, ItemStack bucket, ItemStack bottle, Item emptyContainer) {
        if (bucket.isEmpty() || bottle.isEmpty()) {
            return;
        }

        registerShapeless(registry, name + "_to_bucket", bucket.copy(), bottle.copy(), bottle.copy(), bottle.copy(), bottle.copy(), Items.BUCKET);
        registerShapeless(registry, name + "_from_bucket", copyWithCount(bottle, 4), bucket.copy(), emptyContainer, emptyContainer, emptyContainer, emptyContainer);
    }

    private static void registerDynamicToastRecipes(IForgeRegistry<IRecipe> registry) {
        if (!EDLItems.DYNAMIC_TOAST.isRegistered()) {
            return;
        }

        for (ItemDynamicJam.Flavor flavor : ItemDynamicJam.Flavor.values()) {
            ItemDynamicToast.Topping topping = ItemDynamicToast.Topping.byJamFlavor(flavor);
            registerShapeless(
                registry,
                "dynamic_toast_" + flavor.getId(),
                ItemDynamicToast.stack(topping, 1),
                EDLItems.TOAST.getItem(),
                ItemDynamicJam.stack(flavor, 1)
            );
        }
        registerDynamicToastRecipe(registry, "lemon_curd", ItemDynamicToast.Topping.LEMON_CURD, EDLItems.LEMON_CURD.stack(1));
        registerDynamicToastRecipe(registry, "nut_butter", ItemDynamicToast.Topping.NUT_BUTTER, EDLItems.PEANUT_BUTTER_BOTTLE.stack(1));
        registerDynamicToastRecipe(registry, "hazelnut_spread", ItemDynamicToast.Topping.HAZELNUT_SPREAD, EDLItems.HAZELNUT_SPREAD_BOTTLE.stack(1));
        registerDynamicToastRecipe(registry, "marshmallow_fluff", ItemDynamicToast.Topping.MARSHMALLOW_FLUFF, EDLItems.MARSHMALLOW_FLUFF_BOTTLE.stack(1));
        registerDynamicToastRecipe(registry, "yeast_spread", ItemDynamicToast.Topping.YEAST_SPREAD, EDLItems.YEAST_SPREAD.stack(1));
        registerDynamicToastRecipe(registry, "butter", ItemDynamicToast.Topping.BUTTER, EDLItems.BUTTER.stack(1));
    }

    private static void registerDynamicToastRecipe(IForgeRegistry<IRecipe> registry, String name,
                                                   ItemDynamicToast.Topping topping, ItemStack spread) {
        if (spread.isEmpty()) {
            return;
        }
        registerShapeless(registry, "dynamic_toast_" + name, ItemDynamicToast.stack(topping, 1), EDLItems.TOAST.getItem(), spread);
    }

    private static ItemStack copyWithCount(ItemStack stack, int count) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack copy = stack.copy();
        copy.setCount(count);
        return copy;
    }

    private static void registerCampfire(String name, EDLItems.ItemDefinition input, EDLItems.ItemDefinition output, int cookingTime) {
        if (!input.isRegistered() || !output.isRegistered()) {
            return;
        }

        CampfireCookingRecipeManager.registerScriptRecipe(
            recipeId(name),
            new String[]{itemId(input.getId())},
            output.stack(1),
            cookingTime
        );
    }

    private static void registerCampfire(String name, ItemStack input, ItemStack output, int cookingTime) {
        if (input.isEmpty() || output.isEmpty()) {
            return;
        }

        CampfireCookingRecipeManager.registerScriptRecipe(
            recipeId(name),
            new String[]{stackId(input)},
            output,
            cookingTime
        );
    }

    private static void registerButcheryCampfireRecipes() {
        registerButcheryCampfire(EDLItems.BEEF_SCRAPS, EDLItems.COOKED_BEEF_SCRAPS);
        registerButcheryCampfire(EDLItems.GROUND_BEEF, EDLItems.COOKED_GROUND_BEEF);
        registerButcheryCampfire(EDLItems.CUBED_BEEF, EDLItems.COOKED_CUBED_BEEF);
        registerButcheryCampfire(EDLItems.BEEF_RIBS, EDLItems.COOKED_BEEF_RIBS);
        registerButcheryCampfire(EDLItems.BEEF_ROAST, EDLItems.COOKED_BEEF_ROAST);
        registerButcheryCampfire(EDLItems.BEEF_STEWMEAT, EDLItems.COOKED_BEEF_STEWMEAT);
        registerButcheryCampfire(EDLItems.OXTAIL, EDLItems.COOKED_OXTAIL);
        registerButcheryCampfire(EDLItems.TONGUE, EDLItems.COOKED_TONGUE);
        registerButcheryCampfire(EDLItems.PORK_SCRAPS, EDLItems.COOKED_PORK_SCRAPS);
        registerButcheryCampfire(EDLItems.GROUND_PORK, EDLItems.COOKED_GROUND_PORK);
        registerButcheryCampfire(EDLItems.CUBED_PORK, EDLItems.COOKED_CUBED_PORK);
        registerButcheryCampfire(EDLItems.PORK_RIBS, EDLItems.COOKED_PORK_RIBS);
        registerButcheryCampfire(EDLItems.PORK_ROAST, EDLItems.COOKED_PORK_ROAST);
        registerButcheryCampfire(EDLItems.PORK_STEWMEAT, EDLItems.COOKED_PORK_STEWMEAT);
        registerButcheryCampfire(EDLItems.LAMB_SCRAPS, EDLItems.COOKED_LAMB_SCRAPS);
        registerButcheryCampfire(EDLItems.GROUND_LAMB, EDLItems.COOKED_GROUND_LAMB);
        registerButcheryCampfire(EDLItems.CUBED_LAMB, EDLItems.COOKED_CUBED_LAMB);
        registerButcheryCampfire(EDLItems.LAMB_RIBS, EDLItems.COOKED_LAMB_RIBS);
        registerButcheryCampfire(EDLItems.LAMB_ROAST, EDLItems.COOKED_LAMB_ROAST);
        registerButcheryCampfire(EDLItems.LAMB_STEWMEAT, EDLItems.COOKED_LAMB_STEWMEAT);
        registerButcheryCampfire(EDLItems.CHICKEN_BREAST, EDLItems.COOKED_CHICKEN_BREAST);
        registerButcheryCampfire(EDLItems.CUBED_CHICKEN, EDLItems.COOKED_CUBED_CHICKEN);
        registerButcheryCampfire(EDLItems.GROUND_CHICKEN, EDLItems.COOKED_GROUND_CHICKEN);
        registerButcheryCampfire(EDLItems.CHICKEN_LEG, EDLItems.COOKED_CHICKEN_LEG);
        registerButcheryCampfire(EDLItems.CHICKEN_SCRAPS, EDLItems.COOKED_CHICKEN_SCRAPS);
        registerButcheryCampfire(EDLItems.CHICKEN_THIGH, EDLItems.COOKED_CHICKEN_THIGH);
        registerButcheryCampfire(EDLItems.CHICKEN_WING, EDLItems.COOKED_CHICKEN_WING);
        registerButcheryCampfire(EDLItems.CHICKEN_STEWMEAT, EDLItems.COOKED_CHICKEN_STEWMEAT);
        registerButcheryCampfire(EDLItems.GOAT_CHOP, EDLItems.COOKED_GOAT_CHOP);
        registerButcheryCampfire(EDLItems.GOAT_RIBS, EDLItems.COOKED_GOAT_RIBS);
        registerButcheryCampfire(EDLItems.GOAT_ROAST, EDLItems.COOKED_GOAT_ROAST);
        registerButcheryCampfire(EDLItems.GOAT_SCRAPS, EDLItems.COOKED_GOAT_SCRAPS);
        registerButcheryCampfire(EDLItems.GOAT_STEWMEAT, EDLItems.COOKED_GOAT_STEWMEAT);
        registerButcheryCampfire(EDLItems.GROUND_GOAT, EDLItems.COOKED_GROUND_GOAT);
        registerButcheryCampfire(EDLItems.CUBED_GOAT, EDLItems.COOKED_CUBED_GOAT);
        registerButcheryCampfire(EDLItems.RABBIT_SADDLE, EDLItems.COOKED_RABBIT_SADDLE);
        registerButcheryCampfire(EDLItems.RABBIT_THIGH, EDLItems.COOKED_RABBIT_THIGH);
        registerButcheryCampfire(EDLItems.RABBIT_LEG, EDLItems.COOKED_RABBIT_LEG);
        registerButcheryCampfire(EDLItems.RABBIT_SCRAPS, EDLItems.COOKED_RABBIT_SCRAPS);
        registerButcheryCampfire(EDLItems.RABBIT_STEWMEAT, EDLItems.COOKED_RABBIT_STEWMEAT);
        registerButcheryCampfire(EDLItems.GROUND_RABBIT, EDLItems.COOKED_GROUND_RABBIT);
        registerButcheryCampfire(EDLItems.CUBED_RABBIT, EDLItems.COOKED_CUBED_RABBIT);
        registerButcheryCampfire(EDLItems.BRAIN, EDLItems.COOKED_BRAIN);
        registerButcheryCampfire(EDLItems.HEART, EDLItems.COOKED_HEART);
        registerButcheryCampfire(EDLItems.KIDNEY, EDLItems.COOKED_KIDNEY);
        registerButcheryCampfire(EDLItems.LIVER, EDLItems.COOKED_LIVER);
        registerButcheryCampfire(EDLItems.LUNG, EDLItems.COOKED_LUNG);
        registerButcheryCampfire(EDLItems.STOMACH, EDLItems.COOKED_STOMACH);
        registerButcheryCampfire(EDLItems.TRIPE, EDLItems.COOKED_TRIPE);
        registerButcheryCampfire(EDLItems.EYEBALL, EDLItems.COOKED_EYEBALL);
        registerButcheryCampfire(EDLItems.SAUSAGE, EDLItems.COOKED_SAUSAGE);
    }

    private static void registerButcheryCampfire(EDLItems.ItemDefinition input, EDLItems.ItemDefinition output) {
        registerCampfire("campfire/butchery/" + input.getId(), input, output, 600);
    }

    private static void registerCutting(String name, String input, String output, int count, float chance) {
        if (!hasLocalItems(input, output)) {
            return;
        }

        CuttingBoardRecipeApi.registerRecipe(recipeId(name), input, null, output, count, chance);
    }

    private static void registerPieCuttingRecipes() {
        registerPieCutting("cheesecake", EDLBlocks.CHEESECAKE, EDLItems.CHEESECAKE_SLICE);
        registerPieCutting("chocolate_cheesecake", EDLBlocks.CHOCOLATE_CHEESECAKE, EDLItems.CHOCOLATE_CHEESECAKE_SLICE);
        registerPieCutting("honey_cheesecake", EDLBlocks.HONEY_CHEESECAKE, EDLItems.HONEY_CHEESECAKE_SLICE);
        registerPieCutting("glow_berry_cheesecake", EDLBlocks.GLOW_BERRY_CHEESECAKE, EDLItems.GLOW_BERRY_CHEESECAKE_SLICE);
        registerPieCutting("pumpkin_cheesecake", EDLBlocks.PUMPKIN_CHEESECAKE, EDLItems.PUMPKIN_CHEESECAKE_SLICE);
        registerPieCutting("apple_cheesecake", EDLBlocks.APPLE_CHEESECAKE, EDLItems.APPLE_CHEESECAKE_SLICE);
        registerPieCutting("sweet_berry_pie", EDLBlocks.SWEET_BERRY_PIE, EDLItems.SWEET_BERRY_PIE_SLICE);
        registerPieCutting("glow_berry_pie", EDLBlocks.GLOW_BERRY_PIE, EDLItems.GLOW_BERRY_PIE_SLICE);
        registerPieCutting("key_lime_pie", EDLBlocks.KEY_LIME_PIE, EDLItems.KEY_LIME_PIE_SLICE);
        registerPieCutting("lemon_meringue_pie", EDLBlocks.LEMON_MERINGUE_PIE, EDLItems.LEMON_MERINGUE_PIE_SLICE);
        registerPieCutting("caramel_cheesecake", EDLBlocks.CARAMEL_CHEESECAKE, EDLItems.CARAMEL_CHEESECAKE_SLICE);
        registerPieCutting("pumpkin_pie", EDLBlocks.PUMPKIN_PIE, EDLItems.PUMPKIN_PIE_SLICE);
        registerPieCutting("meat_pie", EDLBlocks.MEAT_PIE, EDLItems.MEAT_PIE_SLICE);
        registerPieCutting("bacon_egg_pie", EDLBlocks.BACON_EGG_PIE, EDLItems.BACON_EGG_PIE_SLICE);
        registerPieCutting("steak_pickled_onion_pie", EDLBlocks.STEAK_PICKLED_ONION_PIE, EDLItems.STEAK_PICKLED_ONION_PIE_SLICE);
        registerPieCutting("mississippi_mud_pie", EDLBlocks.MISSISSIPPI_MUD_PIE, EDLItems.MISSISSIPPI_MUD_PIE_SLICE);
        registerPieCutting("grasshopper_pie", EDLBlocks.GRASSHOPPER_PIE, EDLItems.GRASSHOPPER_PIE_SLICE);
        registerCakeCutting("coffee_cake", EDLBlocks.COFFEE_CAKE, EDLItems.COFFEE_CAKE_SLICE);
        registerCakeCutting("chocolate_cake", EDLBlocks.CHOCOLATE_CAKE, EDLItems.CHOCOLATE_CAKE_SLICE);
        registerPieCutting("kyiv_cake", EDLBlocks.KYIV_CAKE, EDLItems.KYIV_CAKE_SLICE);
        registerPieCutting("pumpkin_roll", EDLBlocks.PUMPKIN_ROLL_FEAST, EDLItems.PUMPKIN_ROLL);
        registerPieCutting("quiche", EDLBlocks.QUICHE, EDLItems.QUICHE_SLICE);
        registerPieCutting("milk_tart", EDLBlocks.MILK_TART, EDLItems.MILK_TART_SLICE);
        registerPieCutting("tarte_tatin", EDLBlocks.TARTE_TATIN, EDLItems.TARTE_TATIN_SLICE);
        registerPieCutting("panforte", EDLBlocks.PANFORTE, EDLItems.PANFORTE_SLICE);
        registerCakeCutting("lemon_cucumber_cake", EDLBlocks.LEMON_CUCUMBER_CAKE, EDLItems.LEMON_CUCUMBER_CAKE_SLICE);
        registerCakeCutting("melon_layer_cake", EDLBlocks.MELON_LAYER_CAKE, EDLItems.MELON_LAYER_CAKE_SLICE);
        registerPieCutting("pavlova", EDLBlocks.PAVLOVA, EDLItems.PAVLOVA_SLICE);
    }

    private static void registerPieCutting(String name, EDLBlocks.BlockDefinition pie, EDLItems.ItemDefinition slice) {
        if (pie.getItemBlock() == null || !slice.isRegistered()) {
            return;
        }
        CuttingBoardRecipeApi.registerRecipe(
            recipeId("cutting/" + name + "_knife"),
            new ItemStack[]{pie.stack(1)},
            null,
            new ItemStack[]{slice.stack(4)},
            new float[]{1.0F}
        );
    }

    private static void registerCakeCutting(String name, EDLBlocks.BlockDefinition cake, EDLItems.ItemDefinition slice) {
        if (cake.getItemBlock() == null || !slice.isRegistered()) {
            return;
        }
        CuttingBoardRecipeApi.registerRecipe(
            recipeId("cutting/" + name + "_knife"),
            new ItemStack[]{cake.stack(1)},
            null,
            new ItemStack[]{slice.stack(7)},
            new float[]{1.0F}
        );
    }

    private static void registerCutting(String name, String[] inputs, String[] tools, String[] outputs, int[] counts, float[] chances) {
        if (!hasLocalItems(inputs) || !hasLocalItems(outputs)) {
            return;
        }

        CuttingBoardRecipeApi.registerRecipe(recipeId(name), inputs, tools, outputs, counts, chances);
    }

    private static void registerCuttingWithTools(String name, ItemStack input, ItemStack[] tools, ItemStack[] outputs, float[] chances) {
        if (input.isEmpty() || hasEmptyOutput(outputs) || chances.length != outputs.length) {
            return;
        }

        if (tools != null && tools.length == 0) {
            return;
        }

        CuttingBoardRecipeApi.registerRecipe(recipeId(name), new ItemStack[]{input}, tools, outputs, chances);
    }

    private static void registerCuttingWithToolsSkippingMissingOutputs(String name, ItemStack input, ItemStack[] tools, ItemStack[] outputs, float[] chances) {
        if (chances.length != outputs.length) {
            return;
        }

        java.util.List<ItemStack> presentOutputs = new java.util.ArrayList<>();
        java.util.List<Float> presentChances = new java.util.ArrayList<>();
        for (int i = 0; i < outputs.length; i++) {
            if (!outputs[i].isEmpty()) {
                presentOutputs.add(outputs[i]);
                presentChances.add(chances[i]);
            }
        }

        float[] activeChances = new float[presentChances.size()];
        for (int i = 0; i < presentChances.size(); i++) {
            activeChances[i] = presentChances.get(i);
        }

        registerCuttingWithTools(name, input, tools, presentOutputs.toArray(new ItemStack[0]), activeChances);
    }

    private static ItemStack[] spoonTools() {
        java.util.List<ItemStack> stacks = new java.util.ArrayList<>();
        addToolStack(stacks, EDLItems.WOODEN_SPOON);
        addToolStack(stacks, EDLItems.STONE_SPOON);
        addToolStack(stacks, EDLItems.IRON_SPOON);
        addToolStack(stacks, EDLItems.GOLD_SPOON);
        addToolStack(stacks, EDLItems.DIAMOND_SPOON);
        return stacks.toArray(new ItemStack[0]);
    }

    private static ItemStack[] graterTool() {
        return EDLItems.GRATER.isRegistered() ? new ItemStack[]{EDLItems.GRATER.stack(1)} : new ItemStack[0];
    }

    private static ItemStack[] axeTools() {
        return new ItemStack[]{
            new ItemStack(Items.WOODEN_AXE),
            new ItemStack(Items.STONE_AXE),
            new ItemStack(Items.IRON_AXE),
            new ItemStack(Items.GOLDEN_AXE),
            new ItemStack(Items.DIAMOND_AXE)
        };
    }

    private static ItemStack[] pickaxeTools() {
        return new ItemStack[]{
            new ItemStack(Items.WOODEN_PICKAXE),
            new ItemStack(Items.STONE_PICKAXE),
            new ItemStack(Items.IRON_PICKAXE),
            new ItemStack(Items.GOLDEN_PICKAXE),
            new ItemStack(Items.DIAMOND_PICKAXE)
        };
    }

    private static ItemStack optionalStack(Item item) {
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static ItemStack optionalStack(Item item, int count) {
        return item == null ? ItemStack.EMPTY : new ItemStack(item, count);
    }

    private static ItemStack itemStack(String itemId) {
        ResourceLocation id = new ResourceLocation(itemId);
        Item item = ForgeRegistries.ITEMS.getValue(id);
        if (item == null || item == Items.AIR) {
            net.minecraft.block.Block block = ForgeRegistries.BLOCKS.getValue(id);
            if (block != null && block != Blocks.AIR) {
                item = Item.getItemFromBlock(block);
            }
        }
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static ItemStack itemStack(String itemId, int count) {
        ItemStack stack = itemStack(itemId);
        if (!stack.isEmpty()) {
            stack.setCount(count);
        }
        return stack;
    }

    private static ItemStack firstPresentStack(String... itemIds) {
        for (String itemId : itemIds) {
            ItemStack stack = itemStack(itemId);
            if (!stack.isEmpty()) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack ropeStack() {
        Item item = Item.getItemFromBlock(ModBlocks.ROPE);
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static ItemStack cookingPotStack() {
        Item item = Item.getItemFromBlock(ModBlocks.COOKING_POT);
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static ItemStack barrelOrChestStack() {
        ItemStack futureBarrel = itemStack("futuremc:barrel");
        return futureBarrel.isEmpty() ? new ItemStack(Blocks.CHEST) : futureBarrel;
    }

    private static void addToolStack(java.util.List<ItemStack> stacks, EDLItems.ItemDefinition definition) {
        if (definition.isRegistered()) {
            stacks.add(definition.stack(1));
        }
    }

    private static boolean hasEmptyOutput(ItemStack[] outputs) {
        if (outputs == null || outputs.length == 0) {
            return true;
        }

        for (ItemStack output : outputs) {
            if (output.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasLocalItems(String... ids) {
        for (String id : ids) {
            if (id != null && id.startsWith(ExtraDelightLegacy.MODID + ":")) {
                String path = id.substring((ExtraDelightLegacy.MODID + ":").length());
                if (!EDLItems.isRegistered(path)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasExistingItemTokens(String... ids) {
        for (String id : ids) {
            if (id == null || id.startsWith("ore:")) {
                continue;
            }

            String itemId = id;
            int metaIndex = itemId.indexOf('@');
            if (metaIndex >= 0) {
                itemId = itemId.substring(0, metaIndex);
            }

            ResourceLocation location = new ResourceLocation(itemId);
            if (!ForgeRegistries.ITEMS.containsKey(location)) {
                return false;
            }
        }
        return true;
    }

    private static String itemId(String path) {
        return ExtraDelightLegacy.MODID + ":" + path;
    }

    private static String stackId(ItemStack stack) {
        ResourceLocation id = stack.getItem().getRegistryName();
        if (id == null) {
            return "";
        }

        return stack.getMetadata() == 0 ? id.toString() : id + "@" + stack.getMetadata();
    }

    private static String recipeId(String path) {
        return ExtraDelightLegacy.MODID + ":" + path;
    }
}
