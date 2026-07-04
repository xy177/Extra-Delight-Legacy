package xy177.extradelightlegacy.common.registry;

import com.wdcftgg.farmersdelightlegacy.api.knife.IKnifeItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.item.ItemDynamicJam;

public final class EDLOreDictionary {
    private EDLOreDictionary() {
    }

    public static void register() {
        ore("flower", new ItemStack(Blocks.YELLOW_FLOWER, 1, OreDictionary.WILDCARD_VALUE));
        ore("flower", new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE));
        ore("listAllmushroom", new ItemStack(Blocks.BROWN_MUSHROOM));
        ore("listAllmushroom", new ItemStack(Blocks.RED_MUSHROOM));
        ore("mushroomBrown", new ItemStack(Blocks.BROWN_MUSHROOM));
        ore("mushroomRed", new ItemStack(Blocks.RED_MUSHROOM));
        ore("seedCorn", EDLItems.CORN_SEEDS);
        ore("cropCorn", EDLItems.CORN_ON_COB);
        ore("seedChili", EDLItems.CHILI_SEEDS);
        ore("cropChili", EDLItems.CHILI);
        ore("cropChiliDried", EDLItems.DRIED_CHILI);
        ore("driedChili", EDLItems.DRIED_CHILI);
        ore("cropGarlic", EDLItems.GARLIC);
        ore("cropGinger", EDLItems.GINGER);
        ore("cropMint", EDLItems.MINT);
        ore("cropPeanut", EDLItems.PEANUTS);
        ore("cropMallowRoot", EDLItems.MALLOW_ROOT);
        ore("seedCucumber", EDLItems.CUCUMBER_SEED);
        ore("cropCucumber", EDLItems.CUCUMBER);
        ore("cropCucumberSliced", EDLItems.SLICED_CUCUMBER_ITEM);
        ore("foodCucumberSliced", EDLItems.SLICED_CUCUMBER_ITEM);
        ore("processedCucumber", EDLItems.CUCUMBER);
        ore("processedCucumber", EDLItems.SLICED_CUCUMBER_ITEM);
        ore("cropSoybean", EDLItems.SOYBEANS);
        ore("seedSoybean", EDLItems.SOYBEANS);
        ore("cropSoybeanPod", EDLItems.SOYBEAN_POD);
        ore("treeSaplingLemon", EDLBlocks.LEMON_SAPLING);
        ore("treeLeavesLemon", EDLBlocks.LEMON_LEAVES);
        ore("cropLemon", EDLItems.LEMON);
        ore("fruitLemon", EDLItems.LEMON);
        ore("treeSaplingLime", EDLBlocks.LIME_SAPLING);
        ore("treeLeavesLime", EDLBlocks.LIME_LEAVES);
        ore("cropLime", EDLItems.LIME);
        ore("fruitLime", EDLItems.LIME);
        ore("treeSaplingOrange", EDLBlocks.ORANGE_SAPLING);
        ore("treeLeavesOrange", EDLBlocks.ORANGE_LEAVES);
        ore("cropOrange", EDLItems.ORANGE);
        ore("fruitOrange", EDLItems.ORANGE);
        ore("treeSaplingGrapefruit", EDLBlocks.GRAPEFRUIT_SAPLING);
        ore("treeLeavesGrapefruit", EDLBlocks.GRAPEFRUIT_LEAVES);
        ore("cropGrapefruit", EDLItems.GRAPEFRUIT);
        ore("fruitGrapefruit", EDLItems.GRAPEFRUIT);
        ore("treeSaplingHazelnut", EDLBlocks.HAZELNUT_SAPLING);
        ore("treeLeavesHazelnut", EDLBlocks.HAZELNUT_LEAVES);
        ore("cropHazelnutInShell", EDLItems.HAZELNUTS_IN_SHELL);
        ore("cropHazelnut", EDLItems.HAZELNUTS);
        ore("nutHazelnut", EDLItems.HAZELNUTS);
        ore("nutHazelnutRoasted", EDLItems.ROASTED_HAZELNUTS);
        ore("nutPeanut", EDLItems.PEANUTS);
        ore("nutPeanutRoasted", EDLItems.ROASTED_PEANUTS);
        ore("cropPeanutShelled", EDLItems.PEANUTS);
        ore("cropPeanutCooked", EDLItems.ROASTED_PEANUTS);
        optionalOre("cropRice", "farmersdelight:rice");
        ore("cropCoffeeGreen", EDLItems.GREEN_COFFEE);
        ore("treeSaplingCinnamon", EDLBlocks.CINNAMON_SAPLING);
        ore("treeLeavesCinnamon", EDLBlocks.CINNAMON_LEAVES);
        ore("blockCornHuskDoll", EDLBlocks.CORN_HUSK_DOLL);
        ore("candyBowl", EDLBlocks.CANDY_BOWL);
        ore("fruitBowl", EDLBlocks.FRUIT_BOWL);
        ore("tap", EDLBlocks.TAP);
        ore("faucet", EDLBlocks.TAP);
        ore("funnel", EDLBlocks.FUNNEL);
        for (EDLBlocks.BlockDefinition ribbonBow : EDLBlocks.RIBBON_BOW_BLOCKS) {
            ore("ribbon", ribbonBow);
        }
        ore("logWood", EDLBlocks.CINNAMON_LOG);
        ore("logWood", EDLBlocks.STRIPPED_CINNAMON_LOG);
        ore("treeLog", EDLBlocks.CINNAMON_LOG);
        ore("treeLog", EDLBlocks.STRIPPED_CINNAMON_LOG);
        ore("cinnamonBark", EDLItems.CINNAMON_BARK);
        ore("rawCinnamon", EDLItems.RAW_CINNAMON);
        ore("cinnamonRaw", EDLItems.RAW_CINNAMON);
        ore("cinnamonStick", EDLItems.CINNAMON_STICK);
        ore("cinnamonGround", EDLItems.GROUND_CINNAMON);
        ore("dustCinnamon", EDLItems.GROUND_CINNAMON);
        ore("seedPumpkinRoasted", EDLItems.ROASTED_PUMPKIN_SEEDS);
        ore("seedSunflower", EDLItems.SUNFLOWER_SEEDS);
        ore("foodSeedSunflower", EDLItems.SUNFLOWER_SEEDS);
        ore("foodToast", EDLItems.TOAST);
        ore("foodCroutons", EDLItems.CROUTONS);
        ore("breadSliced", EDLItems.BREAD_SLICE);
        ore("cropPotatoGrated", EDLItems.GRATED_POTATO);
        ore("foodPotatoGrated", EDLItems.GRATED_POTATO);
        ore("cropPotato", new ItemStack(Items.POTATO));
        ore("processedPotato", EDLItems.GRATED_POTATO);
        ore("processedPotato", new ItemStack(Items.POTATO));
        ore("cropPotatoSliced", EDLItems.SLICED_POTATO);
        ore("foodPotatoSliced", EDLItems.SLICED_POTATO);
        ore("processedPotato", EDLItems.SLICED_POTATO);
        ore("cropPotatoSticks", EDLItems.POTATO_STICKS);
        ore("foodPotatoSticks", EDLItems.POTATO_STICKS);
        ore("processedPotato", EDLItems.POTATO_STICKS);
        ore("cropCarrot", new ItemStack(Items.CARROT));
        ore("cropCarrotGrated", EDLItems.GRATED_CARROT);
        ore("foodCarrotGrated", EDLItems.GRATED_CARROT);
        ore("processedCarrot", new ItemStack(Items.CARROT));
        ore("processedCarrot", EDLItems.GRATED_CARROT);
        ore("processedVegetable", new ItemStack(Items.CARROT));
        ore("processedVegetable", EDLItems.GRATED_CARROT);
        ore("fruitApple", new ItemStack(Items.APPLE));
        ore("processedApple", new ItemStack(Items.APPLE));
        ore("processedFruit", new ItemStack(Items.APPLE));
        ore("fruitAppleSliced", EDLItems.SLICED_APPLE);
        ore("foodAppleSliced", EDLItems.SLICED_APPLE);
        ore("appleSliced", EDLItems.SLICED_APPLE);
        ore("processedApple", EDLItems.SLICED_APPLE);
        ore("processedFruit", EDLItems.SLICED_APPLE);
        ore("cropOnionSliced", EDLItems.SLICED_ONION);
        ore("foodOnionSliced", EDLItems.SLICED_ONION);
        optionalOre("cropOnion", "farmersdelight:onion");
        ore("processedOnion", EDLItems.SLICED_ONION);
        optionalOre("processedOnion", "farmersdelight:onion");
        ore("processedVegetable", EDLItems.SLICED_ONION);
        optionalOre("processedVegetable", "farmersdelight:onion");
        optionalOre("cropTomato", "farmersdelight:tomato");
        ore("cropTomatoSliced", EDLItems.SLICED_TOMATO);
        ore("foodTomatoSliced", EDLItems.SLICED_TOMATO);
        ore("processedTomato", EDLItems.SLICED_TOMATO);
        optionalOre("processedTomato", "farmersdelight:tomato");
        ore("processedVegetable", EDLItems.SLICED_TOMATO);
        optionalOre("processedVegetable", "farmersdelight:tomato");
        ore("cropBeetrootSliced", EDLItems.SLICED_BEETROOT_ITEM);
        ore("foodBeetrootSliced", EDLItems.SLICED_BEETROOT_ITEM);
        ore("processedBeetroot", EDLItems.SLICED_BEETROOT_ITEM);
        ore("processedVegetable", EDLItems.SLICED_BEETROOT_ITEM);
        ore("cropMelonProcessed", EDLItems.MELON_CHUNKS);
        ore("processedMelon", new ItemStack(Items.MELON));
        ore("processedMelon", EDLItems.MELON_CHUNKS);
        ore("processedFruit", new ItemStack(Items.MELON));
        ore("processedFruit", EDLItems.MELON_CHUNKS);
        ore("foodMelonRind", EDLItems.MELON_RIND);
        optionalOre("processedPumpkin", "farmersdelight:pumpkin_slice");
        optionalOre("cropPumpkinSliced", "farmersdelight:pumpkin_slice");
        optionalOre("foodPumpkinSliced", "farmersdelight:pumpkin_slice");
        ore("processedCabbage", EDLItems.SHREDDED_CABBAGE);
        optionalOre("processedCabbage", "farmersdelight:cabbage_leaf");
        optionalOre("processedCabbage", "farmersdelight:cabbage");
        ore("processedVegetable", EDLItems.SHREDDED_CABBAGE);
        optionalOre("processedVegetable", "farmersdelight:cabbage_leaf");
        optionalOre("processedVegetable", "farmersdelight:cabbage");
        ore("iceCubes", EDLItems.ICE_CUBES);
        ore("foodWheatSeedsCooked", EDLItems.COOKED_WHEAT_SEEDS);
        optionalOre("cropBerrySweet", "futuremc:sweet_berries");
        optionalOre("cropBerrySweet", "brewinandchewinlegacy:sweet_berries");
        optionalOre("cropBerrySweet", "brewinandchewinlegacy:sweet_berry");
        optionalOre("processedFruit", "futuremc:sweet_berries");
        optionalOre("processedFruit", "brewinandchewinlegacy:sweet_berries");
        optionalOre("processedFruit", "brewinandchewinlegacy:sweet_berry");
        optionalOre("cropBerryGlow", "brewinandchewinlegacy:glow_berries");
        optionalOre("cropBerryGlow", "da:glow_berry");
        optionalOre("processedFruit", "brewinandchewinlegacy:glow_berries");
        optionalOre("processedFruit", "da:glow_berry");
        ore("foodCarrotRoasted", EDLItems.ROASTED_CARROT);
        ore("foodAppleRoasted", EDLItems.ROASTED_APPLE);
        ore("foodHashbrowns", EDLItems.HASHBROWNS);
        ore("fruitLemonSliced", EDLItems.SLICED_LEMON);
        ore("foodLemonSliced", EDLItems.SLICED_LEMON);
        ore("processedLemon", EDLItems.LEMON);
        ore("processedLemon", EDLItems.SLICED_LEMON);
        ore("processedCitrus", EDLItems.LEMON);
        ore("processedCitrus", EDLItems.SLICED_LEMON);
        ore("processedFruit", EDLItems.LEMON);
        ore("processedFruit", EDLItems.SLICED_LEMON);
        ore("zestLemon", EDLItems.LEMON_ZEST);
        ore("fruitLimeSliced", EDLItems.SLICED_LIME);
        ore("foodLimeSliced", EDLItems.SLICED_LIME);
        ore("processedLime", EDLItems.LIME);
        ore("processedLime", EDLItems.SLICED_LIME);
        ore("processedCitrus", EDLItems.LIME);
        ore("processedCitrus", EDLItems.SLICED_LIME);
        ore("processedFruit", EDLItems.LIME);
        ore("processedFruit", EDLItems.SLICED_LIME);
        ore("zestLime", EDLItems.LIME_ZEST);
        ore("fruitOrangeSliced", EDLItems.SLICED_ORANGE);
        ore("foodOrangeSliced", EDLItems.SLICED_ORANGE);
        ore("processedOrange", EDLItems.ORANGE);
        ore("processedOrange", EDLItems.SLICED_ORANGE);
        ore("processedCitrus", EDLItems.ORANGE);
        ore("processedCitrus", EDLItems.SLICED_ORANGE);
        ore("processedFruit", EDLItems.ORANGE);
        ore("processedFruit", EDLItems.SLICED_ORANGE);
        ore("zestOrange", EDLItems.ORANGE_ZEST);
        ore("fruitGrapefruitSliced", EDLItems.SLICED_GRAPEFRUIT);
        ore("foodGrapefruitSliced", EDLItems.SLICED_GRAPEFRUIT);
        ore("processedGrapefruit", EDLItems.GRAPEFRUIT);
        ore("processedGrapefruit", EDLItems.SLICED_GRAPEFRUIT);
        ore("processedCitrus", EDLItems.GRAPEFRUIT);
        ore("processedCitrus", EDLItems.SLICED_GRAPEFRUIT);
        ore("processedFruit", EDLItems.GRAPEFRUIT);
        ore("processedFruit", EDLItems.SLICED_GRAPEFRUIT);
        ore("foodPickled", EDLItems.GHERKIN_ITEM);
        ore("foodPickled", EDLItems.SLICED_GHERKIN_ITEM);
        ore("foodPickled", EDLItems.PICKLED_BEET_ITEM);
        ore("foodPickled", EDLItems.PICKLED_CARROT_ITEM);
        ore("foodPickled", EDLItems.PICKLED_EGG_ITEM);
        ore("foodPickled", EDLItems.PICKLED_FISH_ITEM);
        ore("foodPickled", EDLItems.PICKLED_SAUSAGE_ITEM);
        ore("foodPickled", EDLItems.PICKLED_GINGER);
        ore("foodPickled", EDLItems.PICKLED_ONION_ITEM);
        ore("foodPickled", EDLItems.PICKLED_RIND_ITEM);
        ore("foodPickled", EDLItems.PRESERVED_LEMON_ITEM);
        ore("foodPickledVegetable", EDLItems.GHERKIN_ITEM);
        ore("foodPickledVegetable", EDLItems.SLICED_GHERKIN_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_BEET_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_CARROT_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_GINGER);
        ore("foodPickledVegetable", EDLItems.PICKLED_ONION_ITEM);
        ore("foodPickledCucumber", EDLItems.GHERKIN_ITEM);
        ore("foodPickledCucumber", EDLItems.SLICED_GHERKIN_ITEM);
        ore("processedPickledCucumber", EDLItems.SLICED_GHERKIN_ITEM);
        ore("foodPickledBeetroot", EDLItems.PICKLED_BEET_ITEM);
        ore("foodPickledCarrot", EDLItems.PICKLED_CARROT_ITEM);
        ore("foodPickledEgg", EDLItems.PICKLED_EGG_ITEM);
        ore("foodPickledFish", EDLItems.PICKLED_FISH_ITEM);
        ore("foodPickledMeat", EDLItems.PICKLED_FISH_ITEM);
        ore("foodPickledMeat", EDLItems.PICKLED_SAUSAGE_ITEM);
        ore("foodPickledSausage", EDLItems.PICKLED_SAUSAGE_ITEM);
        ore("foodSausagePickled", EDLItems.PICKLED_SAUSAGE_ITEM);
        ore("foodPickledGinger", EDLItems.PICKLED_GINGER);
        ore("foodPickledOnion", EDLItems.PICKLED_ONION_ITEM);
        ore("foodPickledRind", EDLItems.PICKLED_RIND_ITEM);
        ore("foodPreservedLemon", EDLItems.PRESERVED_LEMON_ITEM);
        ore("pickleJuice", EDLItems.PICKLE_JUICE);
        ore("foodPickleJuice", EDLItems.PICKLE_JUICE);
        registerDynamicJamOres();
        ore("fishRaw", new ItemStack(Items.FISH, 1, 0));
        ore("fishRaw", new ItemStack(Items.FISH, 1, 1));
        ore("foodFishRaw", new ItemStack(Items.FISH, 1, 0));
        ore("foodFishRaw", new ItemStack(Items.FISH, 1, 1));
        ore("rawFish", new ItemStack(Items.FISH, 1, 0));
        ore("rawFish", new ItemStack(Items.FISH, 1, 1));
        ore("fishSauce", EDLItems.FISH_SAUCE);
        ore("foodFishSauce", EDLItems.FISH_SAUCE);
        ore("cropCactus", EDLItems.CACTUS);
        ore("foodCactus", EDLItems.CACTUS);
        ore("foodCactusCooked", EDLItems.COOKED_CACTUS);
        ore("fruitDried", EDLItems.DRIED_FRUIT);
        ore("foodFruitDried", EDLItems.DRIED_FRUIT);
        ore("fishFlakes", EDLItems.FISH_FLAKES);
        ore("ricePuffed", EDLItems.CRISP_RICE);
        ore("foodRicePuffed", EDLItems.CRISP_RICE);
        ore("soybeansSoaked", EDLItems.SOAKED_SOYBEANS);
        ore("foodSoybeansSoaked", EDLItems.SOAKED_SOYBEANS);
        ore("soybeansCooked", EDLItems.COOKED_SOYBEANS);
        ore("foodSoybeansCooked", EDLItems.COOKED_SOYBEANS);
        ore("soybeansMashed", EDLItems.MASHED_SOYBEANS);
        ore("foodSoybeansMashed", EDLItems.MASHED_SOYBEANS);
        ore("soyMilk", EDLItems.SOY_MILK);
        ore("soyMilks", EDLItems.SOY_MILK);
        ore("foodSoyMilk", EDLItems.SOY_MILK);
        ore("foodNatto", EDLItems.NATTO);
        ore("misoPaste", EDLItems.MISO_PASTE);
        ore("foodMisoPaste", EDLItems.MISO_PASTE);
        ore("soySauce", EDLItems.SOY_SAUCE);
        ore("foodSoySauce", EDLItems.SOY_SAUCE);
        ore("condiment", EDLItems.SOY_SAUCE);
        ore("condimentSoySauce", EDLItems.SOY_SAUCE);
        ore("hotSauce", EDLItems.HOT_SAUCE);
        ore("foodHotSauce", EDLItems.HOT_SAUCE);
        ore("foodCornCooked", EDLItems.COOKED_CORN);
        ore("processedGinger", EDLItems.GINGER);
        ore("processedGinger", EDLItems.SLICED_GINGER);
        ore("processedGinger", EDLItems.GRATED_GINGER);
        ore("teaIngredient", EDLItems.MINT);
        ore("teaIngredient", EDLItems.GINGER);
        ore("teaIngredient", EDLItems.SLICED_GINGER);
        ore("teaIngredient", EDLItems.GRATED_GINGER);
        ore("teaIngredient", EDLItems.CORN_SILK);
        ore("teaIngredient", EDLItems.MALLOW_ROOT);
        ore("foodPastaCooked", EDLItems.COOKED_PASTA);
        optionalOre("dough", "farmersdelight:wheat_dough");
        optionalOre("foodDough", "farmersdelight:wheat_dough");
        optionalOre("pastaRaw", "farmersdelight:raw_pasta");
        optionalOre("foodPastaRaw", "farmersdelight:raw_pasta");
        ore("pastaMacaroni", EDLItems.MACARONI);
        ore("foodPastaMacaroni", EDLItems.MACARONI);
        ore("pastaPenne", EDLItems.PENNE);
        ore("foodPastaPenne", EDLItems.PENNE);
        ore("pasta", EDLItems.COOKED_PASTA);
        ore("foodPasta", EDLItems.COOKED_PASTA);
        ore("foodAppleSauce", EDLItems.APPLE_SAUCE);
        ore("foodCongee", EDLItems.CONGEE);
        ore("eggBoiled", EDLItems.BOILED_EGG);
        ore("foodEggBoiled", EDLItems.BOILED_EGG);
        ore("egg", new ItemStack(Items.EGG));
        optionalOre("foodEggCooked", "farmersdelight:fried_egg");
        ore("eggMix", EDLItems.EGG_MIX);
        ore("foodEggScrambled", EDLItems.SCRAMBLED_EGGS);
        ore("omeletteMix", EDLItems.OMELETTE_MIX);
        ore("foodOmelette", EDLItems.OMELETTE);
        ore("butter", EDLItems.BUTTER);
        ore("foodButter", EDLItems.BUTTER);
        ore("vinegar", EDLItems.VINEGAR);
        ore("foodVinegar", EDLItems.VINEGAR);
        ore("condiment", EDLItems.VINEGAR);
        ore("salt", EDLItems.SALT);
        ore("dustSalt", EDLItems.SALT);
        ore("blockSalt", EDLBlocks.SALT_BLOCK);
        ore("yeast", EDLItems.YEAST);
        ore("foodYeast", EDLItems.YEAST);
        ore("yeastSpread", EDLItems.YEAST_SPREAD);
        ore("foodYeastSpread", EDLItems.YEAST_SPREAD);
        ore("cheese", EDLItems.CHEESE);
        ore("foodCheese", EDLItems.CHEESE);
        ore("gravy", EDLItems.GRAVY);
        ore("foodGravy", EDLItems.GRAVY);
        ore("breading", EDLItems.BREADING_MISANPLAS);
        ore("foodBreading", EDLItems.BREADING_MISANPLAS);
        ore("processedGarlic", EDLItems.GARLIC_CLOVE);
        ore("processedGarlic", EDLItems.GRATED_GARLIC);
        ore("processedChili", EDLItems.CHILI);
        ore("processedChili", EDLItems.SLICED_CHILI);
        ore("foodGarlicRoasted", EDLItems.ROASTED_GARLIC);
        ore("broth", itemStack("farmersdelight:bone_broth"));
        ore("stock", itemStack("farmersdelight:bone_broth"));
        ore("sauerkraut", EDLItems.SAUERKRAUT);
        ore("foodSauerkraut", EDLItems.SAUERKRAUT);
        ore("misoSoupIngredient", EDLItems.SHREDDED_CABBAGE);
        ore("misoSoupIngredient", EDLItems.GRATED_CARROT);
        ore("misoSoupIngredient", EDLItems.GRATED_POTATO);
        ore("misoSoupIngredient", EDLItems.SLICED_ONION);
        optionalOre("misoSoupIngredient", "farmersdelight:onion");
        ore("misoSoupIngredient", new ItemStack(Blocks.BROWN_MUSHROOM));
        ore("misoSoupIngredient", new ItemStack(Blocks.RED_MUSHROOM));
        ore("foodMilk", new ItemStack(Items.MILK_BUCKET));
        optionalOre("foodMilk", "farmersdelight:milk_bottle");
        optionalOre("foodBaconRaw", "farmersdelight:bacon");
        ore("eggBoiled", EDLItems.BOILED_EGG);
        ore("foodPickledVegetable", EDLItems.GHERKIN_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_BEET_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_CARROT_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_GINGER);
        ore("foodPickledVegetable", EDLItems.PICKLED_ONION_ITEM);
        ore("foodPickledVegetable", EDLItems.PICKLED_RIND_ITEM);
        ore("foodPickledVegetable", EDLItems.PRESERVED_LEMON_ITEM);
        ore("foodBeefScrapsRaw", EDLItems.BEEF_SCRAPS);
        optionalOre("foodBeefScrapsRaw", "butchercraft:beef_scraps");
        ore("foodBeefCubedRaw", EDLItems.CUBED_BEEF);
        ore("foodChickenBreastRaw", EDLItems.CHICKEN_BREAST);
        ore("foodChickenThighRaw", EDLItems.CHICKEN_THIGH);
        ore("foodChickenRaw", EDLItems.CHICKEN_BREAST);
        ore("foodChickenRaw", EDLItems.CHICKEN_THIGH);
        ore("foodPotatoChips", EDLItems.POTATO_CHIPS);
        ore("foodFrenchFries", EDLItems.FRENCH_FRIES);
        ore("foodButteredToast", EDLItems.BUTTERED_TOAST);
        ore("foodGarlicBread", EDLItems.GARLIC_BREAD);
        ore("foodCheesyGarlicBread", EDLItems.CHEESY_GARLIC_BREAD);
        ore("foodFishCakes", EDLItems.FISH_CAKES);
        ore("foodFriedMushrooms", EDLItems.FRIED_MUSHROOMS);
        ore("foodEggBasket", EDLItems.EGG_IN_THE_BASKET);
        ore("foodFrenchToast", EDLItems.FRENCH_TOAST);
        ore("foodCheeseSandwich", EDLItems.CHEESE_SANDWICH);
        ore("foodGrilledCheese", EDLItems.GRILLED_CHEESE);
        ore("foodButteredPasta", EDLItems.BUTTERED_PASTA);
        ore("foodPotatoSalad", EDLItems.POTATO_SALAD);
        ore("foodCreamCorn", EDLItems.CREAM_CORN);
        ore("foodCornFritters", EDLItems.CORN_FRITTERS);
        ore("foodStewedApples", EDLItems.STEWED_APPLES);
        ore("foodAppleFritters", EDLItems.APPLE_FRITTERS);
        ore("foodAglioEOlio", EDLItems.AGLIO_E_OLIO);
        ore("foodPenneAllArrabbiata", EDLItems.PENNE_ALL_ARRABBIATA);
        ore("foodJalapenoPopper", EDLItems.JALAPENO_POPPER);
        ore("foodChiliCheeseCornbreadMuffin", EDLItems.CHILI_CHEESE_CORNBREAD_MUFFIN);
        ore("foodPotatoSoup", EDLItems.POTATO_SOUP);
        ore("foodTomatoSoup", EDLItems.TOMATO_SOUP);
        ore("foodCarrotSoup", EDLItems.CARROT_SOUP);
        ore("foodCactusSoup", EDLItems.CACTUS_SOUP);
        ore("foodLugaw", EDLItems.LUGAW);
        ore("foodCornChowder", EDLItems.CORN_CHOWDER);
        ore("foodPamonha", EDLItems.PAMONHA);
        ore("cookieDoughSugar", EDLItems.SUGAR_COOKIE_DOUGH);
        ore("foodCookieDoughSugar", EDLItems.SUGAR_COOKIE_DOUGH);
        ore("cookieDough", EDLItems.SUGAR_COOKIE_DOUGH);
        ore("foodCookieDough", EDLItems.SUGAR_COOKIE_DOUGH);
        ore("cookieSugar", EDLItems.SUGAR_COOKIE);
        ore("foodCookie", EDLItems.SUGAR_COOKIE);
        ore("foodCookieSugar", EDLItems.SUGAR_COOKIE);
        ore("foodCookieRawSugarAlex", EDLItems.RAW_SUGAR_COOKIE_ALEX);
        ore("foodCookieRawSugarCreeper", EDLItems.RAW_SUGAR_COOKIE_CREEPER);
        ore("foodCookieRawSugarPickaxe", EDLItems.RAW_SUGAR_COOKIE_PICKAXE);
        ore("foodCookieRawSugarSteve", EDLItems.RAW_SUGAR_COOKIE_STEVE);
        ore("foodCookieRawSugarSword", EDLItems.RAW_SUGAR_COOKIE_SWORD);
        ore("foodCookieRawSugarVillager", EDLItems.RAW_SUGAR_COOKIE_VILLAGER);
        ore("foodCookieRawSugarDiamond", EDLItems.RAW_SUGAR_COOKIE_DIAMOND);
        ore("foodCookieRawSugarEmerald", EDLItems.RAW_SUGAR_COOKIE_EMERALD);
        ore("foodCookieSugarAlex", EDLItems.SUGAR_COOKIE_ALEX);
        ore("foodCookieSugarCreeper", EDLItems.SUGAR_COOKIE_CREEPER);
        ore("foodCookieSugarPickaxe", EDLItems.SUGAR_COOKIE_PICKAXE);
        ore("foodCookieSugarSteve", EDLItems.SUGAR_COOKIE_STEVE);
        ore("foodCookieSugarSword", EDLItems.SUGAR_COOKIE_SWORD);
        ore("foodCookieSugarVillager", EDLItems.SUGAR_COOKIE_VILLAGER);
        ore("foodCookieSugarDiamond", EDLItems.SUGAR_COOKIE_DIAMOND);
        ore("foodCookieSugarEmerald", EDLItems.SUGAR_COOKIE_EMERALD);
        ore("grahamCracker", EDLItems.GRAHAM_CRACKER);
        ore("foodGrahamCracker", EDLItems.GRAHAM_CRACKER);
        ore("foodPanforteSlice", EDLItems.PANFORTE_SLICE);
        ore("marshmallow", EDLItems.MARSHMALLOW);
        ore("foodMarshmallow", EDLItems.MARSHMALLOW);
        ore("smore", EDLItems.SMORE);
        ore("foodSmore", EDLItems.SMORE);
        ore("trailMix", EDLItems.TRAIL_MIX);
        ore("foodTrailMix", EDLItems.TRAIL_MIX);
        ore("nougat", EDLItems.NOUGAT);
        ore("foodNougat", EDLItems.NOUGAT);
        ore("peanutBrittle", EDLItems.PEANUT_BRITTLE);
        ore("foodPeanutBrittle", EDLItems.PEANUT_BRITTLE);
        ore("butterscotch", EDLItems.BUTTERSCOTCH);
        ore("foodButterscotch", EDLItems.BUTTERSCOTCH);
        ore("caramelSauce", EDLItems.CARAMEL_SAUCE);
        ore("foodCaramelSauce", EDLItems.CARAMEL_SAUCE);
        ore("bbqSauce", EDLItems.BBQ_SAUCE);
        ore("foodBbqSauce", EDLItems.BBQ_SAUCE);
        ore("condiment", EDLItems.BBQ_SAUCE);
        ore("ketchup", EDLItems.KETCHUP);
        ore("foodKetchup", EDLItems.KETCHUP);
        ore("condiment", EDLItems.KETCHUP);
        ore("mayo", EDLItems.MAYO);
        ore("foodMayo", EDLItems.MAYO);
        ore("condiment", EDLItems.MAYO);
        ore("aioli", EDLItems.AIOLI);
        ore("foodAioli", EDLItems.AIOLI);
        ore("condiment", EDLItems.AIOLI);
        ore("whippedCream", EDLItems.WHIPPED_CREAM);
        ore("foodWhippedCream", EDLItems.WHIPPED_CREAM);
        ore("foodCustardChocolate", EDLItems.CHOCOLATE_CUSTARD);
        ore("foodCustardPumpkin", EDLItems.PUMPKIN_CUSTARD);
        ore("foodCustardHoney", EDLItems.HONEY_CUSTARD);
        ore("foodCustardSweetBerry", EDLItems.SWEET_BERRY_CUSTARD);
        ore("foodCustardApple", EDLItems.APPLE_CUSTARD);
        ore("foodCustardNutButter", EDLItems.NUT_BUTTER_CUSTARD);
        ore("foodCustard", EDLItems.CHOCOLATE_CUSTARD);
        ore("foodCustard", EDLItems.PUMPKIN_CUSTARD);
        ore("foodCustard", EDLItems.HONEY_CUSTARD);
        ore("foodCustard", EDLItems.SWEET_BERRY_CUSTARD);
        ore("foodCustard", EDLItems.APPLE_CUSTARD);
        ore("foodCustard", EDLItems.NUT_BUTTER_CUSTARD);
        ore("foodPopsicleApple", EDLItems.APPLE_POPSICLE);
        ore("foodPopsicleGlowBerry", EDLItems.GLOW_BERRY_POPSICLE);
        ore("foodPopsicleSweetBerry", EDLItems.SWEET_BERRY_POPSICLE);
        ore("foodPopsicleFudge", EDLItems.FUDGE_POPSICLE);
        ore("foodPopsicleHoney", EDLItems.HONEY_POPSICLE);
        ore("foodPopsicleCaramel", EDLItems.CARAMEL_POPSICLE);
        ore("foodPopsicleCinnamon", EDLItems.CINNAMON_POPSICLE);
        ore("foodPopsicle", EDLItems.APPLE_POPSICLE);
        ore("foodPopsicle", EDLItems.GLOW_BERRY_POPSICLE);
        ore("foodPopsicle", EDLItems.SWEET_BERRY_POPSICLE);
        ore("foodPopsicle", EDLItems.FUDGE_POPSICLE);
        ore("foodPopsicle", EDLItems.HONEY_POPSICLE);
        ore("foodPopsicle", EDLItems.CARAMEL_POPSICLE);
        ore("foodPopsicle", EDLItems.CINNAMON_POPSICLE);
        ore("seaweedPaste", EDLItems.SEAWEED_PASTE);
        ore("agarSheets", EDLItems.AGAR_SHEETS);
        ore("gelatin", EDLItems.AGAR_AGAR);
        ore("agarAgar", EDLItems.AGAR_AGAR);
        ore("foodAgarAgar", EDLItems.AGAR_AGAR);
        ore("foodSeaweedCrisps", EDLItems.SEAWEED_CRISPS);
        ore("foodSeaweedSalad", EDLItems.SEAWEED_SALAD);
        ore("furikake", EDLItems.FURIKAKE);
        ore("foodFurikakeRice", EDLItems.FURIKAKE_RICE);
        ore("riceballFilling", EDLItems.EGG_SALAD);
        ore("riceballFilling", EDLItems.SCRAMBLED_EGGS);
        ore("riceballFilling", EDLItems.FISH_SALAD);
        ore("riceballFilling", EDLItems.BOILED_EGG);
        ore("riceballFilling", EDLItems.PICKLED_GINGER);
        ore("riceballFilling", EDLItems.CHEESE);
        ore("foodRiceball", EDLItems.RICEBALL);
        ore("foodRiceballFilled", EDLItems.RICEBALL_FILLED);
        ore("foodEdamame", EDLItems.EDAMAME);
        ore("foodHoneyChiliChicken", EDLItems.HONEY_CHILI_CHICKEN);
        ore("foodKimchiFriedRice", EDLItems.KIMCHI_FRIED_RICE);
        ore("foodKongjang", EDLItems.KONGJANG);
        ore("foodKiwiburger", EDLItems.KIWIBURGER);
        ore("foodSalamiMix", EDLItems.SALAMI_MIX);
        ore("foodNaemMoo", EDLItems.NAEM_MOO);
        ore("foodCheeseburgerPickle", EDLItems.CHEESEBURGER_PICKLE);
        ore("foodHotWings", EDLItems.HOT_WINGS);
        ore("kimchi", EDLItems.KIMCHI);
        ore("foodKimchi", EDLItems.KIMCHI);
        ore("foodChickenWingCooked", EDLItems.COOKED_CHICKEN_WING);
        ore("foodChickenCubedRaw", EDLItems.CUBED_CHICKEN);
        ore("foodCubedChickenRaw", EDLItems.CUBED_CHICKEN);
        ore("cubedChickenRaw", EDLItems.CUBED_CHICKEN);
        ore("foodBeefGroundRaw", EDLItems.GROUND_BEEF);
        ore("foodPorkGroundRaw", EDLItems.GROUND_PORK);
        ore("curryPowder", EDLItems.CURRY_POWDER);
        ore("foodRicePudding", EDLItems.RICE_PUDDING);
        ore("foodMuffinGinger", EDLItems.MUFFIN_GINGER);
        ore("foodMuffinCinnamon", EDLItems.MUFFIN_CINNAMON);
        ore("foodMuffinSweetBerry", EDLItems.MUFFIN_SWEET_BERRY);
        ore("foodMuffinApple", EDLItems.MUFFIN_APPLE);
        ore("foodFruitBread", EDLItems.FRUIT_BREAD);
        ore("foodBeetMintSalad", EDLItems.BEET_MINT_SALAD);
        ore("gummies", EDLItems.GUMMIES);
        ore("foodGummies", EDLItems.GUMMIES);
        ore("foodFudge", EDLItems.FUDGE);
        ore("alfredoSauce", EDLItems.ALFREDO_SAUCE);
        ore("foodAlfredoSauce", EDLItems.ALFREDO_SAUCE);
        ore("foodPastaAlfredo", EDLItems.PASTA_ALFREDO);
        ore("foodChickenAlfredo", EDLItems.CHICKEN_ALFREDO);
        ore("foodMushroomRisotto", EDLItems.MUSHROOM_RISOTTO);
        ore("foodStuffedCactus", EDLItems.STUFFED_CACTUS);
        ore("foodEggnog", EDLItems.EGGNOG);
        ore("foodGingerBeer", EDLItems.GINGER_BEER);
        ore("foodHorchata", EDLItems.HORCHATA);
        ore("foodXocolatl", EDLItems.XOCOLATL);
        optionalOre("cropKelp", "oceanicexpanse:kelp");
        optionalOre("foodKelp", "oceanicexpanse:kelp");
        optionalOre("foodKelpDried", "oceanicexpanse:dried_kelp");
        ore("fishCooked", new ItemStack(Items.COOKED_FISH, 1, 0));
        ore("fishCooked", new ItemStack(Items.COOKED_FISH, 1, 1));
        ore("foodFishCooked", new ItemStack(Items.COOKED_FISH, 1, 0));
        ore("foodFishCooked", new ItemStack(Items.COOKED_FISH, 1, 1));
        ore("foodChickenRaw", new ItemStack(Items.CHICKEN));
        ore("foodChickenCooked", new ItemStack(Items.COOKED_CHICKEN));
        optionalOre("foodChickenRaw", "farmersdelight:chicken_cuts");
        optionalOre("foodChickenCooked", "farmersdelight:cooked_chicken_cuts");
        optionalOre("foodBaconCooked", "farmersdelight:cooked_bacon");
        optionalOre("foodBacon", "farmersdelight:bacon");
        optionalOre("foodEggCooked", "farmersdelight:fried_egg");
        ore("foodEggCooked", EDLItems.BOILED_EGG);
        optionalOre("foodHamCooked", "farmersdelight:ham");
        optionalOre("foodHamCooked", "farmersdelight:smoked_ham");
        ore("foodHamCooked", new ItemStack(Items.COOKED_PORKCHOP));
        registerButcheryOres();
        registerSourceRecipeAggregateOres();
        ore("caramelCandy", EDLItems.CARAMEL_CANDY);
        ore("foodCaramelCandy", EDLItems.CARAMEL_CANDY);
        ore("foodCaramelApple", EDLItems.CARAMEL_APPLE);
        ore("foodCaramelGoldenApple", EDLItems.CARAMEL_GOLDEN_APPLE);
        ore("foodCaramelPopcorn", EDLItems.CARAMEL_POPCORN);
        ore("foodCaramelCustard", EDLItems.CARAMEL_CUSTARD);
        ore("frosting", EDLItems.FROSTING_BLACK);
        ore("frostingBlack", EDLItems.FROSTING_BLACK);
        ore("frosting", EDLItems.FROSTING_BLUE);
        ore("frostingBlue", EDLItems.FROSTING_BLUE);
        ore("frosting", EDLItems.FROSTING_BROWN);
        ore("frostingBrown", EDLItems.FROSTING_BROWN);
        ore("frosting", EDLItems.FROSTING_CYAN);
        ore("frostingCyan", EDLItems.FROSTING_CYAN);
        ore("frosting", EDLItems.FROSTING_GRAY);
        ore("frostingGray", EDLItems.FROSTING_GRAY);
        ore("frosting", EDLItems.FROSTING_GREEN);
        ore("frostingGreen", EDLItems.FROSTING_GREEN);
        ore("frosting", EDLItems.FROSTING_LIGHT_BLUE);
        ore("frostingLightBlue", EDLItems.FROSTING_LIGHT_BLUE);
        ore("frosting", EDLItems.FROSTING_LIGHT_GRAY);
        ore("frostingLightGray", EDLItems.FROSTING_LIGHT_GRAY);
        ore("frosting", EDLItems.FROSTING_LIME);
        ore("frostingLime", EDLItems.FROSTING_LIME);
        ore("frosting", EDLItems.FROSTING_MAGENTA);
        ore("frostingMagenta", EDLItems.FROSTING_MAGENTA);
        ore("frosting", EDLItems.FROSTING_ORANGE);
        ore("frostingOrange", EDLItems.FROSTING_ORANGE);
        ore("frosting", EDLItems.FROSTING_PINK);
        ore("frostingPink", EDLItems.FROSTING_PINK);
        ore("frosting", EDLItems.FROSTING_PURPLE);
        ore("frostingPurple", EDLItems.FROSTING_PURPLE);
        ore("frosting", EDLItems.FROSTING_RED);
        ore("frostingRed", EDLItems.FROSTING_RED);
        ore("frosting", EDLItems.FROSTING_WHITE);
        ore("frostingWhite", EDLItems.FROSTING_WHITE);
        ore("frosting", EDLItems.FROSTING_YELLOW);
        ore("frostingYellow", EDLItems.FROSTING_YELLOW);
        ore("cracker", EDLItems.CRACKERS);
        ore("foodCracker", EDLItems.CRACKERS);
        ore("foodCarrotGlazed", EDLItems.GLAZED_CARROT);
        ore("foodCactusEggs", EDLItems.CACTUS_EGGS);
        ore("cookieDoughChocolate", EDLItems.CHOCOLATE_COOKIE_DOUGH);
        ore("foodCookieDoughChocolate", EDLItems.CHOCOLATE_COOKIE_DOUGH);
        ore("cookieDough", EDLItems.CHOCOLATE_COOKIE_DOUGH);
        ore("foodCookieDough", EDLItems.CHOCOLATE_COOKIE_DOUGH);
        ore("cookieChocolate", EDLItems.CHOCOLATE_COOKIE);
        ore("foodCookieChocolate", EDLItems.CHOCOLATE_COOKIE);
        ore("foodCookie", EDLItems.CHOCOLATE_COOKIE);
        ore("cookieDoughNutButter", EDLItems.NUT_BUTTER_COOKIE_DOUGH);
        ore("foodCookieDoughNutButter", EDLItems.NUT_BUTTER_COOKIE_DOUGH);
        ore("cookieDough", EDLItems.NUT_BUTTER_COOKIE_DOUGH);
        ore("foodCookieDough", EDLItems.NUT_BUTTER_COOKIE_DOUGH);
        ore("cookieNutButter", EDLItems.NUT_BUTTER_COOKIE);
        ore("foodCookieNutButter", EDLItems.NUT_BUTTER_COOKIE);
        ore("foodCookie", EDLItems.NUT_BUTTER_COOKIE);
        oreCookieDough("Apple", EDLItems.APPLE_COOKIE_DOUGH);
        oreCookie("Apple", EDLItems.APPLE_COOKIE);
        oreCookieDough("ChocolateChip", EDLItems.CHOCOLATE_CHIP_COOKIE_DOUGH);
        oreCookieDough("Gingerbread", EDLItems.GINGERBREAD_COOKIE_DOUGH);
        oreCookie("Gingerbread", EDLItems.GINGERBREAD_COOKIE);
        oreCookieDough("GlowBerry", EDLItems.GLOW_BERRY_COOKIE_DOUGH);
        oreCookie("GlowBerry", EDLItems.GLOW_BERRY_COOKIE);
        oreCookieDough("Honey", EDLItems.HONEY_COOKIE_DOUGH);
        oreCookieDough("Pumpkin", EDLItems.PUMPKIN_COOKIE_DOUGH);
        oreCookie("Pumpkin", EDLItems.PUMPKIN_COOKIE);
        oreCookieDough("SweetBerry", EDLItems.SWEET_BERRY_COOKIE_DOUGH);
        ore("foodCookieRawGingerbreadAlex", EDLItems.RAW_GINGERBREAD_ALEX);
        ore("foodCookieRawGingerbreadCreeper", EDLItems.RAW_GINGERBREAD_CREEPER);
        ore("foodCookieRawGingerbreadPickaxe", EDLItems.RAW_GINGERBREAD_PICKAXE);
        ore("foodCookieRawGingerbreadSteve", EDLItems.RAW_GINGERBREAD_STEVE);
        ore("foodCookieRawGingerbreadSword", EDLItems.RAW_GINGERBREAD_SWORD);
        ore("foodCookieRawGingerbreadVillager", EDLItems.RAW_GINGERBREAD_VILLAGER);
        ore("foodCookieRawGingerbreadDiamond", EDLItems.RAW_GINGERBREAD_DIAMOND);
        ore("foodCookieRawGingerbreadEmerald", EDLItems.RAW_GINGERBREAD_EMERALD);
        ore("foodCookieGingerbreadAlex", EDLItems.GINGERBREAD_ALEX);
        ore("foodCookieGingerbreadCreeper", EDLItems.GINGERBREAD_CREEPER);
        ore("foodCookieGingerbreadPickaxe", EDLItems.GINGERBREAD_PICKAXE);
        ore("foodCookieGingerbreadSteve", EDLItems.GINGERBREAD_STEVE);
        ore("foodCookieGingerbreadSword", EDLItems.GINGERBREAD_SWORD);
        ore("foodCookieGingerbreadVillager", EDLItems.GINGERBREAD_VILLAGER);
        ore("foodCookieGingerbreadDiamond", EDLItems.GINGERBREAD_DIAMOND);
        ore("foodCookieGingerbreadEmerald", EDLItems.GINGERBREAD_EMERALD);
        iceCream("Plain", EDLItems.ICE_CREAM);
        iceCream("Chocolate", EDLItems.CHOCOLATE_ICE_CREAM);
        iceCream("GlowBerry", EDLItems.GLOW_BERRY_ICE_CREAM);
        iceCream("SweetBerry", EDLItems.SWEET_BERRY_ICE_CREAM);
        iceCream("Pumpkin", EDLItems.PUMPKIN_ICE_CREAM);
        iceCream("Honey", EDLItems.HONEY_ICE_CREAM);
        iceCream("Apple", EDLItems.APPLE_ICE_CREAM);
        iceCream("CookieDough", EDLItems.COOKIE_DOUGH_ICE_CREAM);
        iceCream("MintChip", EDLItems.MINT_CHIP_ICE_CREAM);
        iceCream("NutButter", EDLItems.NUT_BUTTER_ICE_CREAM);
        milkshake("Plain", EDLItems.MILKSHAKE);
        milkshake("Chocolate", EDLItems.CHOCOLATE_MILKSHAKE);
        milkshake("GlowBerry", EDLItems.GLOW_BERRY_MILKSHAKE);
        milkshake("SweetBerry", EDLItems.SWEET_BERRY_MILKSHAKE);
        milkshake("Pumpkin", EDLItems.PUMPKIN_MILKSHAKE);
        milkshake("Honey", EDLItems.HONEY_MILKSHAKE);
        milkshake("Apple", EDLItems.APPLE_MILKSHAKE);
        milkshake("CookieDough", EDLItems.COOKIE_DOUGH_MILKSHAKE);
        milkshake("MintChip", EDLItems.MINT_CHIP_MILKSHAKE);
        milkshake("NutButter", EDLItems.NUT_BUTTER_MILKSHAKE);
        ore("foodCrispRiceTreat", EDLItems.CRISP_RICE_TREAT);
        ore("chocolateBar", EDLItems.DARK_CHOCOLATE_BAR);
        ore("chocolateBarDark", EDLItems.DARK_CHOCOLATE_BAR);
        ore("foodChocolateBarDark", EDLItems.DARK_CHOCOLATE_BAR);
        ore("chocolateBar", EDLItems.MILK_CHOCOLATE_BAR);
        ore("chocolateBarMilk", EDLItems.MILK_CHOCOLATE_BAR);
        ore("foodChocolateBarMilk", EDLItems.MILK_CHOCOLATE_BAR);
        ore("chocolateBar", EDLItems.WHITE_CHOCOLATE_BAR);
        ore("chocolateBarWhite", EDLItems.WHITE_CHOCOLATE_BAR);
        ore("foodChocolateBarWhite", EDLItems.WHITE_CHOCOLATE_BAR);
        ore("chocolateBar", EDLItems.BLOOD_CHOCOLATE_BAR);
        ore("chocolateBarBlood", EDLItems.BLOOD_CHOCOLATE_BAR);
        ore("foodChocolateBarBlood", EDLItems.BLOOD_CHOCOLATE_BAR);
        ore("chocolateChips", EDLItems.DARK_CHOCOLATE_CHIPS);
        ore("chocolateChipsDark", EDLItems.DARK_CHOCOLATE_CHIPS);
        ore("foodChocolateChipsDark", EDLItems.DARK_CHOCOLATE_CHIPS);
        ore("chocolateChips", EDLItems.MILK_CHOCOLATE_CHIPS);
        ore("chocolateChipsMilk", EDLItems.MILK_CHOCOLATE_CHIPS);
        ore("foodChocolateChipsMilk", EDLItems.MILK_CHOCOLATE_CHIPS);
        ore("chocolateChips", EDLItems.WHITE_CHOCOLATE_CHIPS);
        ore("chocolateChipsWhite", EDLItems.WHITE_CHOCOLATE_CHIPS);
        ore("foodChocolateChipsWhite", EDLItems.WHITE_CHOCOLATE_CHIPS);
        ore("chocolateChips", EDLItems.BLOOD_CHOCOLATE_CHIPS);
        ore("chocolateChipsBlood", EDLItems.BLOOD_CHOCOLATE_CHIPS);
        ore("foodChocolateChipsBlood", EDLItems.BLOOD_CHOCOLATE_CHIPS);
        ore("chocolateSyrup", EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE);
        ore("chocolateSyrup", EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE);
        ore("chocolateSyrup", EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE);
        ore("chocolateSyrup", EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE);
        ore("liquidChocolate", EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE);
        ore("liquidChocolate", EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE);
        ore("liquidChocolate", EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE);
        ore("liquidChocolate", EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE);
        ore("foodChocolateSyrup", EDLItems.DARK_CHOCOLATE_SYRUP_BOTTLE);
        ore("foodChocolateSyrup", EDLItems.MILK_CHOCOLATE_SYRUP_BOTTLE);
        ore("foodChocolateSyrup", EDLItems.WHITE_CHOCOLATE_SYRUP_BOTTLE);
        ore("foodChocolateSyrup", EDLItems.BLOOD_CHOCOLATE_SYRUP_BOTTLE);
        ore("blood", EDLItems.BLOOD);
        ore("fluidBlood", EDLItems.BLOOD);
        ore("bottleBlood", EDLItems.BLOOD);
        ore("chocolateTruffle", EDLItems.DARK_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffleDark", EDLItems.DARK_CHOCOLATE_TRUFFLE);
        ore("foodChocolateTruffleDark", EDLItems.DARK_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffle", EDLItems.MILK_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffleMilk", EDLItems.MILK_CHOCOLATE_TRUFFLE);
        ore("foodChocolateTruffleMilk", EDLItems.MILK_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffle", EDLItems.WHITE_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffleWhite", EDLItems.WHITE_CHOCOLATE_TRUFFLE);
        ore("foodChocolateTruffleWhite", EDLItems.WHITE_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffle", EDLItems.BLOOD_CHOCOLATE_TRUFFLE);
        ore("chocolateTruffleBlood", EDLItems.BLOOD_CHOCOLATE_TRUFFLE);
        ore("foodChocolateTruffleBlood", EDLItems.BLOOD_CHOCOLATE_TRUFFLE);
        ore("foodChocolateDippedAppleSliceDark", EDLItems.DARK_CHOCOLATE_DIPPED_APPLE_SLICE);
        ore("foodChocolateDippedAppleSliceMilk", EDLItems.MILK_CHOCOLATE_DIPPED_APPLE_SLICE);
        ore("foodChocolateDippedAppleSliceWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_APPLE_SLICE);
        ore("foodChocolateDippedAppleSliceBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_APPLE_SLICE);
        ore("foodChocolateDippedMarshmallowDark", EDLItems.DARK_CHOCOLATE_DIPPED_MARSHMALLOW);
        ore("foodChocolateDippedMarshmallowMilk", EDLItems.MILK_CHOCOLATE_DIPPED_MARSHMALLOW);
        ore("foodChocolateDippedMarshmallowWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_MARSHMALLOW);
        ore("foodChocolateDippedMarshmallowBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_MARSHMALLOW);
        ore("foodChocolateDippedGrahamCrackerDark", EDLItems.DARK_CHOCOLATE_DIPPED_GRAHAM_CRACKER);
        ore("foodChocolateDippedGrahamCrackerMilk", EDLItems.MILK_CHOCOLATE_DIPPED_GRAHAM_CRACKER);
        ore("foodChocolateDippedGrahamCrackerWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_GRAHAM_CRACKER);
        ore("foodChocolateDippedGrahamCrackerBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_GRAHAM_CRACKER);
        ore("foodChocolateDippedCoffeeBeanDark", EDLItems.DARK_CHOCOLATE_DIPPED_COFFEE_BEAN);
        ore("foodChocolateDippedCoffeeBeanMilk", EDLItems.MILK_CHOCOLATE_DIPPED_COFFEE_BEAN);
        ore("foodChocolateDippedCoffeeBeanWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_COFFEE_BEAN);
        ore("foodChocolateDippedCoffeeBeanBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_COFFEE_BEAN);
        ore("foodChocolateDippedSweetBerryDark", EDLItems.DARK_CHOCOLATE_DIPPED_SWEET_BERRY);
        ore("foodChocolateDippedSweetBerryMilk", EDLItems.MILK_CHOCOLATE_DIPPED_SWEET_BERRY);
        ore("foodChocolateDippedSweetBerryWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_SWEET_BERRY);
        ore("foodChocolateDippedSweetBerryBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_SWEET_BERRY);
        ore("foodChocolateDippedGlowBerryDark", EDLItems.DARK_CHOCOLATE_DIPPED_GLOW_BERRY);
        ore("foodChocolateDippedGlowBerryMilk", EDLItems.MILK_CHOCOLATE_DIPPED_GLOW_BERRY);
        ore("foodChocolateDippedGlowBerryWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_GLOW_BERRY);
        ore("foodChocolateDippedGlowBerryBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_GLOW_BERRY);
        ore("foodChocolateDippedBaconDark", EDLItems.DARK_CHOCOLATE_DIPPED_BACON);
        ore("foodChocolateDippedBaconMilk", EDLItems.MILK_CHOCOLATE_DIPPED_BACON);
        ore("foodChocolateDippedBaconWhite", EDLItems.WHITE_CHOCOLATE_DIPPED_BACON);
        ore("foodChocolateDippedBaconBlood", EDLItems.BLOOD_CHOCOLATE_DIPPED_BACON);
        ore("chocolateBarFilled", EDLItems.DARK_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilledDark", EDLItems.DARK_CHOCOLATE_FILLED_BAR);
        ore("foodChocolateBarFilledDark", EDLItems.DARK_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilled", EDLItems.MILK_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilledMilk", EDLItems.MILK_CHOCOLATE_FILLED_BAR);
        ore("foodChocolateBarFilledMilk", EDLItems.MILK_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilled", EDLItems.WHITE_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilledWhite", EDLItems.WHITE_CHOCOLATE_FILLED_BAR);
        ore("foodChocolateBarFilledWhite", EDLItems.WHITE_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilled", EDLItems.BLOOD_CHOCOLATE_FILLED_BAR);
        ore("chocolateBarFilledBlood", EDLItems.BLOOD_CHOCOLATE_FILLED_BAR);
        ore("foodChocolateBarFilledBlood", EDLItems.BLOOD_CHOCOLATE_FILLED_BAR);
        registerChocolateBarFillingOres();
        ore("foodCoffeeJelly", EDLItems.COFFEE_JELLY);
        jelly(EDLItems.JELLY_WHITE);
        jelly(EDLItems.JELLY_ORANGE);
        jelly(EDLItems.JELLY_MAGENTA);
        jelly(EDLItems.JELLY_LIGHT_BLUE);
        jelly(EDLItems.JELLY_YELLOW);
        jelly(EDLItems.JELLY_LIME);
        jelly(EDLItems.JELLY_PINK);
        jelly(EDLItems.JELLY_GREY);
        jelly(EDLItems.JELLY_LIGHT_GREY);
        jelly(EDLItems.JELLY_CYAN);
        jelly(EDLItems.JELLY_PURPLE);
        jelly(EDLItems.JELLY_BLUE);
        jelly(EDLItems.JELLY_BROWN);
        jelly(EDLItems.JELLY_GREEN);
        jelly(EDLItems.JELLY_RED);
        jelly(EDLItems.JELLY_BLACK);
        ore("foodCandyBarSalad", EDLItems.CANDY_BAR_SALAD);
        ore("foodAffogato", EDLItems.AFFOGATO);
        ore("eggYolk", EDLItems.EGG_YOLK);
        ore("foodEggYolk", EDLItems.EGG_YOLK);
        ore("eggWhite", EDLItems.EGG_WHITE);
        ore("foodEggWhite", EDLItems.EGG_WHITE);
        ore("eggOrYolk", new ItemStack(Items.EGG));
        ore("eggOrYolk", EDLItems.EGG_YOLK);
        ore("stiffPeaks", EDLItems.STIFF_PEAKS);
        ore("foodStiffPeaks", EDLItems.STIFF_PEAKS);
        ore("meringue", EDLItems.MERINGUE);
        ore("foodMeringue", EDLItems.MERINGUE);
        ore("lemonJuice", EDLItems.LEMON_JUICE);
        ore("limeJuice", EDLItems.LIME_JUICE);
        ore("orangeJuice", EDLItems.ORANGE_JUICE);
        ore("grapefruitJuice", EDLItems.GRAPEFRUIT_JUICE);
        ore("foodLemonCurd", EDLItems.LEMON_CURD);
        ore("foodDalgonaCoffee", EDLItems.DALGONA_COFFEE);
        ore("foodGrapefruitSorbet", EDLItems.GRAPEFRUIT_SORBET);
        ore("foodChocolateOrange", EDLItems.CHOCOLATE_ORANGE);
        ore("foodChocolateMousse", EDLItems.CHOCOLATE_MOUSSE);
        ore("foodLemonDelicious", EDLItems.LEMON_DELICIOUS);
        ore("foodLimeSouffle", EDLItems.LIME_SOUFFLE);
        ore("foodCheeseSouffle", EDLItems.CHEESE_SOUFFLE);
        ore("foodOrangeChicken", EDLItems.ORANGE_CHICKEN);
        ore("foodMelonRindStirfry", EDLItems.MELON_RIND_STIRFRY);
        ore("foodLemonPosset", EDLItems.LEMON_POSSET);
        ore("foodMelonLimeGlazedChicken", EDLItems.MELON_LIME_GLAZED_CHICKEN);
        ore("foodCheesecakeSlice", EDLItems.CHEESECAKE_SLICE);
        ore("foodCheesecakeChocolateSlice", EDLItems.CHOCOLATE_CHEESECAKE_SLICE);
        ore("foodCheesecakeHoneySlice", EDLItems.HONEY_CHEESECAKE_SLICE);
        ore("foodCheesecakeGlowBerrySlice", EDLItems.GLOW_BERRY_CHEESECAKE_SLICE);
        ore("foodCheesecakePumpkinSlice", EDLItems.PUMPKIN_CHEESECAKE_SLICE);
        ore("foodCheesecakeAppleSlice", EDLItems.APPLE_CHEESECAKE_SLICE);
        ore("foodPieSweetBerrySlice", EDLItems.SWEET_BERRY_PIE_SLICE);
        ore("foodPieGlowBerrySlice", EDLItems.GLOW_BERRY_PIE_SLICE);
        ore("foodPieKeyLimeSlice", EDLItems.KEY_LIME_PIE_SLICE);
        ore("foodPieLemonMeringueSlice", EDLItems.LEMON_MERINGUE_PIE_SLICE);
        ore("foodPieSlice", EDLItems.CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.CHOCOLATE_CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.HONEY_CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.GLOW_BERRY_CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.PUMPKIN_CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.APPLE_CHEESECAKE_SLICE);
        ore("foodPieSlice", EDLItems.SWEET_BERRY_PIE_SLICE);
        ore("foodPieSlice", EDLItems.GLOW_BERRY_PIE_SLICE);
        ore("foodPieSlice", EDLItems.KEY_LIME_PIE_SLICE);
        ore("foodPieSlice", EDLItems.LEMON_MERINGUE_PIE_SLICE);
        ore("foodCakeSlice", EDLItems.COFFEE_CAKE_SLICE);
        ore("foodCakeSlice", EDLItems.CHOCOLATE_CAKE_SLICE);
        ore("foodCakeSlice", EDLItems.LEMON_CUCUMBER_CAKE_SLICE);
        ore("foodCakeSlice", EDLItems.MELON_LAYER_CAKE_SLICE);
        ore("foodCakeSlice", EDLItems.KYIV_CAKE_SLICE);
        ore("foodCakeSlice", EDLItems.PAVLOVA_SLICE);
        ore("foodCakeSlice", EDLItems.MARSHMALLOW_SLICE);
        ore("jerky", EDLItems.JERKY);
        ore("foodJerky", EDLItems.JERKY);
        ore("foodFriedChicken", EDLItems.FRIED_CHICKEN);
        ore("foodChickenParm", EDLItems.CHICKEN_PARM);
        ore("chocolateTruffle", EDLItems.PEANUT_BUTTER_CUP);
        ore("foodChocolateTruffle", EDLItems.PEANUT_BUTTER_CUP);
        ore("foodPeanutButterCup", EDLItems.PEANUT_BUTTER_CUP);
        ore("chocolateTruffle", EDLItems.MALLOW_CUP);
        ore("foodChocolateTruffle", EDLItems.MALLOW_CUP);
        ore("foodMallowCup", EDLItems.MALLOW_CUP);
        ore("toffee", EDLItems.TOFFEE);
        ore("foodToffee", EDLItems.TOFFEE);
        ore("foodPeppermintBark", EDLItems.PEPPERMINT_BARK);
        ore("foodRockyRoad", EDLItems.ROCKY_ROAD);
        ore("foodEtonMess", EDLItems.ETON_MESS);
        ore("foodJaffaCake", EDLItems.JAFFA_CAKE);
        ore("foodPeanutButterJelly", EDLItems.PEANUT_BUTTER_JELLY);
        ore("foodBadFood", EDLItems.BAD_FOOD);
        ore("foodChickenFriedSteak", EDLItems.CHICKEN_FRIED_STEAK);
        ore("foodLavaCake", EDLItems.LAVA_CAKE);
        ore("foodDirtCake", EDLItems.DIRT_CAKE);
        ore("edlSweetener", new ItemStack(Items.SUGAR));
        ore("sweetener", new ItemStack(Items.SUGAR));
        ore("vinegarFermentable", new ItemStack(Items.SUGAR));
        ore("vinegarFermentable", new ItemStack(Items.REEDS));
        ore("vinegarFermentable", new ItemStack(Items.WHEAT));
        optionalOre("vinegarFermentable", "farmersdelight:rice");
        for (ItemStack fruit : OreDictionary.getOres("processedFruit", false)) {
            ore("vinegarFermentable", fruit);
        }
        optionalOre("edlSweetener", "futuremc:honey_bottle");
        optionalOre("sweetener", "futuremc:honey_bottle");
        optionalOre("edlSweetener", "brewinandchewinlegacy:adulterated_honey");
        optionalOre("sweetener", "brewinandchewinlegacy:adulterated_honey");
        optionalOre("honeyBottle", "futuremc:honey_bottle");
        optionalOre("foodHoneyBottle", "futuremc:honey_bottle");
        optionalOre("honeyBottle", "brewinandchewinlegacy:adulterated_honey");
        optionalOre("foodHoneyBottle", "brewinandchewinlegacy:adulterated_honey");
        registerGenericDyeOres();
        registerCandyBowlValidOres();
        candy("Black", EDLItems.CANDY_BLACK);
        candy("Blue", EDLItems.CANDY_BLUE);
        candy("Brown", EDLItems.CANDY_BROWN);
        candy("Cyan", EDLItems.CANDY_CYAN);
        candy("Gray", EDLItems.CANDY_GRAY);
        candy("Green", EDLItems.CANDY_GREEN);
        candy("LightBlue", EDLItems.CANDY_LIGHT_BLUE);
        candy("LightGray", EDLItems.CANDY_LIGHT_GRAY);
        candy("Lime", EDLItems.CANDY_LIME);
        candy("Magenta", EDLItems.CANDY_MAGENTA);
        candy("Orange", EDLItems.CANDY_ORANGE);
        candy("Pink", EDLItems.CANDY_PINK);
        candy("Purple", EDLItems.CANDY_PURPLE);
        candy("Red", EDLItems.CANDY_RED);
        candy("White", EDLItems.CANDY_WHITE);
        candy("Yellow", EDLItems.CANDY_YELLOW);
        candy("MintBlue", EDLItems.MINT_CANDY_BLUE);
        candy("MintGreen", EDLItems.MINT_CANDY_GREEN);
        candy("MintRed", EDLItems.MINT_CANDY_RED);
        candy("CaneBlue", EDLItems.CANDY_CANE_BLUE);
        candy("CaneGreen", EDLItems.CANDY_CANE_GREEN);
        candy("CaneRed", EDLItems.CANDY_CANE_RED);
        candy("Apple", EDLItems.CANDY_APPLE);
        candy("GoldenApple", EDLItems.CANDY_GOLDEN_APPLE);
        candy("Ginger", EDLItems.CANDIED_GINGER);
        candy("CitrusZest", EDLItems.CANDIED_CITRUS_ZEST);
        ore("listAllveggie", EDLItems.CHILI);
        ore("listAllveggie", EDLItems.GARLIC);
        ore("listAllveggie", EDLItems.GINGER);
        ore("listAllveggie", EDLItems.GRATED_POTATO);
        ore("listAllveggie", EDLItems.SLICED_POTATO);
        ore("listAllveggie", EDLItems.POTATO_STICKS);
        ore("listAllveggie", EDLItems.GRATED_CARROT);
        ore("listAllveggie", EDLItems.CUCUMBER);
        ore("listAllveggie", EDLItems.SLICED_CUCUMBER_ITEM);
        spoon(EDLItems.WOODEN_SPOON);
        spoon(EDLItems.STONE_SPOON);
        spoon(EDLItems.IRON_SPOON);
        spoon(EDLItems.GOLD_SPOON);
        spoon(EDLItems.DIAMOND_SPOON);
        spoon(EDLItems.NETHERITE_SPOON);
        ore("toolGrater", EDLItems.GRATER);
        ore("toolWhisk", EDLItems.WHISK);
        ore("whisk", EDLItems.WHISK);
        registerKnifeItemOres();
        ore("toolPestle", EDLItems.PESTLE_STONE);
        ore("pestle", EDLItems.PESTLE_STONE);
        offsetSpatula(EDLItems.OFFSET_SPATULA_WOOD);
        offsetSpatula(EDLItems.OFFSET_SPATULA_IRON);
        offsetSpatula(EDLItems.OFFSET_SPATULA_GOLD);
        offsetSpatula(EDLItems.OFFSET_SPATULA_DIAMOND);
        offsetSpatula(EDLItems.OFFSET_SPATULA_NETHERITE);
        ore("toolPestle", EDLItems.PESTLE_GRANITE);
        ore("pestle", EDLItems.PESTLE_GRANITE);
        ore("toolPestle", EDLItems.PESTLE_DIORITE);
        ore("pestle", EDLItems.PESTLE_DIORITE);
        ore("toolPestle", EDLItems.PESTLE_ANDESITE);
        ore("pestle", EDLItems.PESTLE_ANDESITE);
        ore("toolPestle", EDLItems.PESTLE_ENDSTONE);
        ore("pestle", EDLItems.PESTLE_ENDSTONE);
        ore("toolPestle", EDLItems.PESTLE_DEEPSLATE);
        ore("pestle", EDLItems.PESTLE_DEEPSLATE);
        ore("toolPestle", EDLItems.PESTLE_BLACKSTONE);
        ore("pestle", EDLItems.PESTLE_BLACKSTONE);
        ore("toolPestle", EDLItems.PESTLE_BASALT);
        ore("pestle", EDLItems.PESTLE_BASALT);
        ore("toolPestle", EDLItems.PESTLE_AMETHYST);
        ore("pestle", EDLItems.PESTLE_AMETHYST);
        ore("toolPestle", EDLItems.PESTLE_GILDED_BLACKSTONE);
        ore("pestle", EDLItems.PESTLE_GILDED_BLACKSTONE);
        registerCandleOres();
        registerFarmersDelightCabinetOres();
        ore("keg", EDLBlocks.KEG_BLOCK);
        ore("blockKeg", EDLBlocks.KEG_BLOCK);
        ore("foodBreadcrumbs", EDLBlocks.BREADCRUMBS);
        ore("breadCrumbs", EDLBlocks.BREADCRUMBS);
        ore("breadCrumb", EDLBlocks.BREADCRUMBS);
        ore("breadcrumb", EDLBlocks.BREADCRUMBS);
        ore("blockBreadcrumbs", EDLBlocks.BREADCRUMB_SACK);
        ore("driedCornHusk", EDLItems.DRIED_CORN_HUSK);
        ore("blockDriedCornHusk", EDLBlocks.DRIED_CORN_HUSK_BUNDLE);
        ore("blockSugar", EDLBlocks.SUGAR_SACK);
        ore("blockEgg", EDLBlocks.EGG_CRATE);
        ore("blockMushroomBrown", EDLBlocks.BROWN_MUSHROOM_CRATE);
        ore("blockMushroomRed", EDLBlocks.RED_MUSHROOM_CRATE);
        ore("blockApple", EDLBlocks.APPLE_CRATE);
        ore("blockAppleGolden", EDLBlocks.GOLDEN_APPLE_CRATE);
        ore("blockCarrotGolden", EDLBlocks.GOLDEN_CARROT_CRATE);
        ore("cropChiliPowder", EDLItems.CHILI_POWDER);
        ore("dustChili", EDLItems.CHILI_POWDER);
        ore("foodFlour", EDLItems.FLOUR);
        ore("flour", EDLItems.FLOUR);
        ore("cornMeal", EDLItems.CORN_MEAL);
        ore("foodCornmeal", EDLItems.CORN_MEAL);
        ore("cornmeal", EDLItems.CORN_MEAL);
        ore("dustMallowRoot", EDLItems.MALLOW_POWDER);
        ore("cookingOil", EDLItems.COOKING_OIL);
        ore("foodCookingOil", EDLItems.COOKING_OIL);
        ore("nutButter", EDLItems.PEANUT_BUTTER_BOTTLE);
        ore("foodNutButter", EDLItems.PEANUT_BUTTER_BOTTLE);
        ore("nutButter", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("foodNutButter", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("hazelnutSpread", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("foodHazelnutSpread", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("chocolateNutButterSpread", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("foodChocolateNutButterSpread", EDLItems.HAZELNUT_SPREAD_BOTTLE);
        ore("marshmallowFluff", EDLItems.MARSHMALLOW_FLUFF_BOTTLE);
        ore("foodMarshmallowFluff", EDLItems.MARSHMALLOW_FLUFF_BOTTLE);
        ore("marshmallow", EDLItems.MARSHMALLOW);
        ore("foodMarshmallow", EDLItems.MARSHMALLOW);
        ore("nutRoasted", EDLItems.ROASTED_PEANUTS);
        ore("nutRoasted", EDLItems.ROASTED_HAZELNUTS);
        ore("peanutRoasted", EDLItems.ROASTED_PEANUTS);
        ore("nutHazelnutRoasted", EDLItems.ROASTED_HAZELNUTS);
        ore("cropCoffeeCherries", EDLItems.COFFEE_CHERRIES);
        ore("coffeeCherries", EDLItems.COFFEE_CHERRIES);
        ore("greenCoffee", EDLItems.GREEN_COFFEE);
        ore("coffeeGreen", EDLItems.GREEN_COFFEE);
        ore("coffeeBeans", EDLItems.COFFEE_BEANS);
        ore("beanCoffee", EDLItems.COFFEE_BEANS);
        ore("coffeeBeansRoasted", EDLItems.COFFEE_BEANS);
        ore("groundCoffee", EDLItems.GROUND_COFFEE);
        ore("coffeeGround", EDLItems.GROUND_COFFEE);
        ore("coffee", EDLItems.COFFEE);
        ore("foodCoffee", EDLItems.COFFEE);
        ore("beanCocoaRoasted", EDLItems.ROASTED_COCOA_BEANS);
        ore("foodCocoaRoasted", EDLItems.ROASTED_COCOA_BEANS);
        ore("cocoaSolids", EDLItems.COCOA_SOLIDS);
        ore("cocoaPowder", EDLItems.COCOA_POWDER);
        ore("cocoaButter", EDLItems.COCOA_BUTTER_BOTTLE);
        ore("foodCocoaButter", EDLItems.COCOA_BUTTER_BOTTLE);
        ore("blockCocoaBeans", EDLBlocks.COCOA_BEANS_SACK);
        ore("blockFlour", EDLBlocks.FLOUR_SACK);
        ore("blockCornMeal", EDLBlocks.CORNMEAL_SACK);
        ore("blockChiliPowder", EDLBlocks.CHILI_POWDER_SACK);
        ore("blockMallowPowder", EDLBlocks.MALLOW_POWDER_SACK);
        ore("blockRoastedCocoaBeans", EDLBlocks.ROASTED_COCOA_BEANS_SACK);
        ore("blockCocoaSolids", EDLBlocks.COCOA_SOLIDS_SACK);
        ore("blockCocoaPowder", EDLBlocks.COCOA_POWDER_SACK);
        ore("blockHangingChili", EDLBlocks.HANGING_CHILI);
        ore("blockHangingDriedChili", EDLBlocks.HANGING_DRIED_CHILI);
        ore("blockHangingCorn", EDLBlocks.HANGING_CORN);
        ore("blockHangingGarlic", EDLBlocks.HANGING_GARLIC);
        ore("blockHangingMint", EDLBlocks.HANGING_MINT);
        ore("blockHangingOnions", EDLBlocks.HANGING_ONIONS);
        ore("salamiMix", EDLItems.SALAMI_MIX);
        ore("foodSalamiMix", EDLItems.SALAMI_MIX);
        ore("salami", EDLItems.SALAMI_ITEM);
        ore("foodSalami", EDLItems.SALAMI_ITEM);
        ore("blockCucumber", EDLBlocks.CUCUMBER_CRATE);
        ore("blockSoybean", EDLBlocks.SOYBEAN_SACK);
        ore("blockLemon", EDLBlocks.LEMON_CRATE);
        ore("blockLime", EDLBlocks.LIME_CRATE);
        ore("blockOrange", EDLBlocks.ORANGE_CRATE);
        ore("blockGrapefruit", EDLBlocks.GRAPEFRUIT_CRATE);
        ore("blockHazelnutInShell", EDLBlocks.HAZELNUT_IN_SHELL_SACK);
        ore("blockHazelnut", EDLBlocks.HAZELNUT_SACK);
        ore("blockRoastedHazelnut", EDLBlocks.ROASTED_HAZELNUT_SACK);
        ore("blockCandyCaneBlue", EDLBlocks.CANDY_CANE_BLUE_BLOCK);
        ore("blockCandyCaneGreen", EDLBlocks.CANDY_CANE_GREEN_BLOCK);
        ore("blockCandyCaneRed", EDLBlocks.CANDY_CANE_RED_BLOCK);
        ore("blockChocolateFondueDark", EDLBlocks.DARK_CHOCOLATE_FONDUE_BLOCK);
        ore("blockChocolateFondueMilk", EDLBlocks.MILK_CHOCOLATE_FONDUE_BLOCK);
        ore("blockChocolateFondueWhite", EDLBlocks.WHITE_CHOCOLATE_FONDUE_BLOCK);
        registerAestheticOres();
    }

    private static void registerAestheticOres() {
        String[] colors = {
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
            "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
        };
        for (int i = 0; i < colors.length; i++) {
            String color = toOreSuffix(colors[i]);
            ore("blockWallpaper", EDLBlocks.WALLPAPER_BLOCKS.get(i));
            ore("blockWallpaper" + color, EDLBlocks.WALLPAPER_BLOCKS.get(i));
            ore("blockGingham", EDLBlocks.GINGHAM_BLOCKS.get(i));
            ore("blockGingham" + color, EDLBlocks.GINGHAM_BLOCKS.get(i));
            ore("carpetGingham", EDLBlocks.GINGHAM_CARPET_BLOCKS.get(i));
            ore("carpetGingham" + color, EDLBlocks.GINGHAM_CARPET_BLOCKS.get(i));
        }
    }

    private static String toOreSuffix(String id) {
        StringBuilder builder = new StringBuilder();
        boolean upper = true;
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (c == '_') {
                upper = true;
                continue;
            }
            builder.append(upper ? Character.toUpperCase(c) : c);
            upper = false;
        }
        return builder.toString();
    }

    private static void registerButcheryOres() {
        butcheryOre("Beef", "Scraps", true, EDLItems.BEEF_SCRAPS);
        butcheryOre("Beef", "Scraps", false, EDLItems.COOKED_BEEF_SCRAPS);
        butcheryOre("Beef", "Ground", true, EDLItems.GROUND_BEEF);
        butcheryOre("Beef", "Ground", false, EDLItems.COOKED_GROUND_BEEF);
        butcheryOre("Beef", "Cubed", true, EDLItems.CUBED_BEEF);
        butcheryOre("Beef", "Cubed", false, EDLItems.COOKED_CUBED_BEEF);
        butcheryOre("Beef", "Ribs", true, EDLItems.BEEF_RIBS);
        butcheryOre("Beef", "Ribs", false, EDLItems.COOKED_BEEF_RIBS);
        butcheryOre("Beef", "Roast", true, EDLItems.BEEF_ROAST);
        butcheryOre("Beef", "Roast", false, EDLItems.COOKED_BEEF_ROAST);
        butcheryOre("Beef", "StewMeat", true, EDLItems.BEEF_STEWMEAT);
        butcheryOre("Beef", "StewMeat", false, EDLItems.COOKED_BEEF_STEWMEAT);
        butcheryOre("Beef", "Oxtail", true, EDLItems.OXTAIL);
        butcheryOre("Beef", "Oxtail", false, EDLItems.COOKED_OXTAIL);
        butcheryOre("Beef", "Tongue", true, EDLItems.TONGUE);
        butcheryOre("Beef", "Tongue", false, EDLItems.COOKED_TONGUE);

        butcheryOre("Pork", "Scraps", true, EDLItems.PORK_SCRAPS);
        butcheryOre("Pork", "Scraps", false, EDLItems.COOKED_PORK_SCRAPS);
        butcheryOre("Pork", "Ground", true, EDLItems.GROUND_PORK);
        butcheryOre("Pork", "Ground", false, EDLItems.COOKED_GROUND_PORK);
        butcheryOre("Pork", "Cubed", true, EDLItems.CUBED_PORK);
        butcheryOre("Pork", "Cubed", false, EDLItems.COOKED_CUBED_PORK);
        butcheryOre("Pork", "Ribs", true, EDLItems.PORK_RIBS);
        butcheryOre("Pork", "Ribs", false, EDLItems.COOKED_PORK_RIBS);
        butcheryOre("Pork", "Roast", true, EDLItems.PORK_ROAST);
        butcheryOre("Pork", "Roast", false, EDLItems.COOKED_PORK_ROAST);
        butcheryOre("Pork", "StewMeat", true, EDLItems.PORK_STEWMEAT);
        butcheryOre("Pork", "StewMeat", false, EDLItems.COOKED_PORK_STEWMEAT);

        butcheryOre("Mutton", "Scraps", true, EDLItems.LAMB_SCRAPS);
        butcheryOre("Mutton", "Scraps", false, EDLItems.COOKED_LAMB_SCRAPS);
        butcheryOre("Mutton", "Ground", true, EDLItems.GROUND_LAMB);
        butcheryOre("Mutton", "Ground", false, EDLItems.COOKED_GROUND_LAMB);
        butcheryOre("Mutton", "Cubed", true, EDLItems.CUBED_LAMB);
        butcheryOre("Mutton", "Cubed", false, EDLItems.COOKED_CUBED_LAMB);
        butcheryOre("Mutton", "Ribs", true, EDLItems.LAMB_RIBS);
        butcheryOre("Mutton", "Ribs", false, EDLItems.COOKED_LAMB_RIBS);
        butcheryOre("Mutton", "Roast", true, EDLItems.LAMB_ROAST);
        butcheryOre("Mutton", "Roast", false, EDLItems.COOKED_LAMB_ROAST);
        butcheryOre("Mutton", "StewMeat", true, EDLItems.LAMB_STEWMEAT);
        butcheryOre("Mutton", "StewMeat", false, EDLItems.COOKED_LAMB_STEWMEAT);

        butcheryOre("Chicken", "Breast", true, EDLItems.CHICKEN_BREAST);
        butcheryOre("Chicken", "Breast", false, EDLItems.COOKED_CHICKEN_BREAST);
        butcheryOre("Chicken", "Cubed", true, EDLItems.CUBED_CHICKEN);
        butcheryOre("Chicken", "Cubed", false, EDLItems.COOKED_CUBED_CHICKEN);
        butcheryOre("Chicken", "Ground", true, EDLItems.GROUND_CHICKEN);
        butcheryOre("Chicken", "Ground", false, EDLItems.COOKED_GROUND_CHICKEN);
        butcheryOre("Chicken", "Leg", true, EDLItems.CHICKEN_LEG);
        butcheryOre("Chicken", "Leg", false, EDLItems.COOKED_CHICKEN_LEG);
        butcheryOre("Chicken", "Scraps", true, EDLItems.CHICKEN_SCRAPS);
        butcheryOre("Chicken", "Scraps", false, EDLItems.COOKED_CHICKEN_SCRAPS);
        butcheryOre("Chicken", "Thigh", true, EDLItems.CHICKEN_THIGH);
        butcheryOre("Chicken", "Thigh", false, EDLItems.COOKED_CHICKEN_THIGH);
        butcheryOre("Chicken", "Wing", true, EDLItems.CHICKEN_WING);
        butcheryOre("Chicken", "Wing", false, EDLItems.COOKED_CHICKEN_WING);
        butcheryOre("Chicken", "StewMeat", true, EDLItems.CHICKEN_STEWMEAT);
        butcheryOre("Chicken", "StewMeat", false, EDLItems.COOKED_CHICKEN_STEWMEAT);

        butcheryOre("Goat", "Chop", true, EDLItems.GOAT_CHOP);
        butcheryOre("Goat", "Chop", false, EDLItems.COOKED_GOAT_CHOP);
        butcheryOre("Goat", "Ribs", true, EDLItems.GOAT_RIBS);
        butcheryOre("Goat", "Ribs", false, EDLItems.COOKED_GOAT_RIBS);
        butcheryOre("Goat", "Roast", true, EDLItems.GOAT_ROAST);
        butcheryOre("Goat", "Roast", false, EDLItems.COOKED_GOAT_ROAST);
        butcheryOre("Goat", "Scraps", true, EDLItems.GOAT_SCRAPS);
        butcheryOre("Goat", "Scraps", false, EDLItems.COOKED_GOAT_SCRAPS);
        butcheryOre("Goat", "StewMeat", true, EDLItems.GOAT_STEWMEAT);
        butcheryOre("Goat", "StewMeat", false, EDLItems.COOKED_GOAT_STEWMEAT);
        butcheryOre("Goat", "Ground", true, EDLItems.GROUND_GOAT);
        butcheryOre("Goat", "Ground", false, EDLItems.COOKED_GROUND_GOAT);
        butcheryOre("Goat", "Cubed", true, EDLItems.CUBED_GOAT);
        butcheryOre("Goat", "Cubed", false, EDLItems.COOKED_CUBED_GOAT);
        registerGoatFallbackOres();

        butcheryOre("Rabbit", "Saddle", true, EDLItems.RABBIT_SADDLE);
        butcheryOre("Rabbit", "Saddle", false, EDLItems.COOKED_RABBIT_SADDLE);
        butcheryOre("Rabbit", "Thigh", true, EDLItems.RABBIT_THIGH);
        butcheryOre("Rabbit", "Thigh", false, EDLItems.COOKED_RABBIT_THIGH);
        butcheryOre("Rabbit", "Leg", true, EDLItems.RABBIT_LEG);
        butcheryOre("Rabbit", "Leg", false, EDLItems.COOKED_RABBIT_LEG);
        butcheryOre("Rabbit", "Scraps", true, EDLItems.RABBIT_SCRAPS);
        butcheryOre("Rabbit", "Scraps", false, EDLItems.COOKED_RABBIT_SCRAPS);
        butcheryOre("Rabbit", "StewMeat", true, EDLItems.RABBIT_STEWMEAT);
        butcheryOre("Rabbit", "StewMeat", false, EDLItems.COOKED_RABBIT_STEWMEAT);
        butcheryOre("Rabbit", "Ground", true, EDLItems.GROUND_RABBIT);
        butcheryOre("Rabbit", "Ground", false, EDLItems.COOKED_GROUND_RABBIT);
        butcheryOre("Rabbit", "Cubed", true, EDLItems.CUBED_RABBIT);
        butcheryOre("Rabbit", "Cubed", false, EDLItems.COOKED_CUBED_RABBIT);

        offalOre("Brain", true, EDLItems.BRAIN);
        offalOre("Brain", false, EDLItems.COOKED_BRAIN);
        offalOre("Heart", true, EDLItems.HEART);
        offalOre("Heart", false, EDLItems.COOKED_HEART);
        ore("brain", EDLItems.BRAIN);
        ore("heart", EDLItems.HEART);
        offalOre("Kidney", true, EDLItems.KIDNEY);
        offalOre("Kidney", false, EDLItems.COOKED_KIDNEY);
        offalOre("Liver", true, EDLItems.LIVER);
        offalOre("Liver", false, EDLItems.COOKED_LIVER);
        offalOre("Lung", true, EDLItems.LUNG);
        offalOre("Lung", false, EDLItems.COOKED_LUNG);
        offalOre("Stomach", true, EDLItems.STOMACH);
        offalOre("Stomach", false, EDLItems.COOKED_STOMACH);
        offalOre("Tripe", true, EDLItems.TRIPE);
        offalOre("Tripe", false, EDLItems.COOKED_TRIPE);
        offalOre("Eyeball", true, EDLItems.EYEBALL);
        offalOre("Eyeball", false, EDLItems.COOKED_EYEBALL);

        ore("foodSausageRaw", EDLItems.SAUSAGE);
        ore("foodSausageCooked", EDLItems.COOKED_SAUSAGE);
        ore("fat", EDLItems.FAT);
        ore("foodFat", EDLItems.FAT);
        ore("gelatin", EDLItems.GELATIN);
        ore("foodGelatin", EDLItems.GELATIN);
    }

    private static void registerSourceRecipeAggregateOres() {
        ore("groundBeefRaw", EDLItems.GROUND_BEEF);
        ore("foodGroundBeefRaw", EDLItems.GROUND_BEEF);
        ore("groundMeatRaw", EDLItems.GROUND_BEEF);
        ore("groundMeatRaw", EDLItems.GROUND_PORK);
        ore("groundMeatRaw", EDLItems.GROUND_LAMB);
        ore("groundMeatRaw", EDLItems.GROUND_CHICKEN);
        ore("groundMeatRaw", EDLItems.GROUND_GOAT);
        ore("groundMeatRaw", EDLItems.GROUND_RABBIT);
        ore("groundBeefCooked", EDLItems.COOKED_GROUND_BEEF);
        ore("foodGroundBeefCooked", EDLItems.COOKED_GROUND_BEEF);

        ore("meat", new ItemStack(Items.BEEF));
        ore("meat", new ItemStack(Items.PORKCHOP));
        ore("meat", new ItemStack(Items.CHICKEN));
        ore("meat", new ItemStack(Items.MUTTON));
        ore("meat", new ItemStack(Items.RABBIT));
        ore("meat", new ItemStack(Items.COOKED_BEEF));
        ore("meat", new ItemStack(Items.COOKED_PORKCHOP));
        ore("meat", new ItemStack(Items.COOKED_CHICKEN));
        ore("meat", new ItemStack(Items.COOKED_MUTTON));
        ore("meat", new ItemStack(Items.COOKED_RABBIT));
        ore("meat", EDLItems.BEEF_SCRAPS);
        ore("meat", EDLItems.PORK_SCRAPS);
        ore("meat", EDLItems.LAMB_SCRAPS);
        ore("meat", EDLItems.CHICKEN_SCRAPS);
        ore("meat", EDLItems.GOAT_SCRAPS);
        ore("meat", EDLItems.RABBIT_SCRAPS);
        ore("foodMeatRaw", new ItemStack(Items.BEEF));
        ore("foodMeatRaw", new ItemStack(Items.PORKCHOP));
        ore("foodMeatRaw", new ItemStack(Items.CHICKEN));
        ore("foodMeatRaw", new ItemStack(Items.MUTTON));
        ore("foodMeatRaw", new ItemStack(Items.RABBIT));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_BEEF));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_PORKCHOP));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_CHICKEN));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_FISH));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_FISH, 1, 1));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_MUTTON));
        ore("foodMeatCooked", new ItemStack(Items.COOKED_RABBIT));
        ore("foodBeefCooked", new ItemStack(Items.COOKED_BEEF));
        ore("foodPorkCooked", new ItemStack(Items.COOKED_PORKCHOP));
        ore("foodChickenCooked", new ItemStack(Items.COOKED_CHICKEN));
        ore("foodMuttonCooked", new ItemStack(Items.COOKED_MUTTON));
        ore("foodRabbitCooked", new ItemStack(Items.COOKED_RABBIT));

        ore("roastRaw", EDLItems.BEEF_ROAST);
        ore("roastRaw", EDLItems.PORK_ROAST);
        ore("roastRaw", EDLItems.LAMB_ROAST);
        ore("roastRaw", EDLItems.GOAT_ROAST);
        ore("beefRoast", EDLItems.BEEF_ROAST);
        ore("foodPorkRoastRaw", EDLItems.PORK_ROAST);
        ore("ribsRaw", EDLItems.BEEF_RIBS);
        ore("ribsRaw", EDLItems.PORK_RIBS);
        ore("ribsRaw", EDLItems.LAMB_RIBS);
        ore("ribsRaw", EDLItems.GOAT_RIBS);
        ore("muttonRibs", EDLItems.LAMB_RIBS);

        ore("offal", EDLItems.BRAIN);
        ore("offal", EDLItems.HEART);
        ore("offal", EDLItems.KIDNEY);
        ore("offal", EDLItems.LIVER);
        ore("offal", EDLItems.LUNG);
        ore("offal", EDLItems.STOMACH);
        ore("offal", EDLItems.TRIPE);
        ore("offal", EDLItems.EYEBALL);
        ore("lung", EDLItems.LUNG);
        ore("stomach", EDLItems.STOMACH);
        ore("liver", EDLItems.LIVER);
        ore("scrapMeat", EDLItems.BEEF_SCRAPS);
        ore("scrapMeat", EDLItems.PORK_SCRAPS);
        ore("scrapMeat", EDLItems.LAMB_SCRAPS);
        ore("scrapMeat", EDLItems.CHICKEN_SCRAPS);
        ore("scrapMeat", EDLItems.GOAT_SCRAPS);
        ore("scrapMeat", EDLItems.RABBIT_SCRAPS);
        ore("foodPorkScrapsCooked", EDLItems.COOKED_PORK_SCRAPS);

        ore("seed", new ItemStack(Items.WHEAT_SEEDS));
        ore("seed", new ItemStack(Items.PUMPKIN_SEEDS));
        ore("seed", new ItemStack(Items.MELON_SEEDS));
        ore("starch", new ItemStack(Items.POTATO));
        ore("starch", EDLItems.GRATED_POTATO);
        ore("starch", EDLItems.COOKED_PASTA);
        ore("starch", EDLItems.MACARONI);
        ore("soup", EDLItems.POTATO_SOUP);
        ore("soup", EDLItems.TOMATO_SOUP);
        ore("soup", EDLItems.CARROT_SOUP);
        ore("soup", EDLItems.CACTUS_SOUP);
        ore("soup", EDLItems.FISH_SOUP);
        ore("vegetable", new ItemStack(Items.CARROT));
        ore("vegetable", new ItemStack(Items.POTATO));
        ore("vegetable", EDLItems.SLICED_ONION);
        ore("vegetable", EDLItems.SLICED_TOMATO);
        ore("vegetable", EDLItems.SLICED_CUCUMBER_ITEM);
        optionalOre("vegetable", "farmersdelight:onion");
        optionalOre("vegetable", "farmersdelight:tomato");
    }

    private static void butcheryOre(String meat, String cut, boolean raw, EDLItems.ItemDefinition definition) {
        String state = raw ? "Raw" : "Cooked";
        ore("foodMeat" + state, definition);
        ore("food" + meat + state, definition);
        ore("food" + cut + meat + state, definition);
        ore("food" + meat + cut + state, definition);
        if ("Mutton".equals(meat)) {
            ore("foodLamb" + state, definition);
            ore("food" + cut + "Lamb" + state, definition);
            ore("foodLamb" + cut + state, definition);
        }
        if (raw) {
            ore("rawMeat", definition);
        } else {
            ore("cookedMeat", definition);
        }
    }

    private static void registerGoatFallbackOres() {
        if (EDLItems.hasGoatEntityProvider()) {
            return;
        }

        goatFallbackOre("Chop", true, EDLItems.LAMB_ROAST);
        goatFallbackOre("Chop", false, EDLItems.COOKED_LAMB_ROAST);
        goatFallbackOre("Ribs", true, EDLItems.LAMB_RIBS);
        goatFallbackOre("Ribs", false, EDLItems.COOKED_LAMB_RIBS);
        goatFallbackOre("Roast", true, EDLItems.LAMB_ROAST);
        goatFallbackOre("Roast", false, EDLItems.COOKED_LAMB_ROAST);
        goatFallbackOre("Scraps", true, EDLItems.LAMB_SCRAPS);
        goatFallbackOre("Scraps", false, EDLItems.COOKED_LAMB_SCRAPS);
        goatFallbackOre("StewMeat", true, EDLItems.LAMB_STEWMEAT);
        goatFallbackOre("StewMeat", false, EDLItems.COOKED_LAMB_STEWMEAT);
        goatFallbackOre("Ground", true, EDLItems.GROUND_LAMB);
        goatFallbackOre("Ground", false, EDLItems.COOKED_GROUND_LAMB);
        goatFallbackOre("Cubed", true, EDLItems.CUBED_LAMB);
        goatFallbackOre("Cubed", false, EDLItems.COOKED_CUBED_LAMB);
    }

    private static void goatFallbackOre(String cut, boolean raw, EDLItems.ItemDefinition replacement) {
        String state = raw ? "Raw" : "Cooked";
        ore("foodGoat" + state, replacement);
        ore("foodChevon" + state, replacement);
        ore("food" + cut + "Goat" + state, replacement);
        ore("foodGoat" + cut + state, replacement);
        ore("food" + cut + "Chevon" + state, replacement);
        ore("foodChevon" + cut + state, replacement);
    }

    private static void offalOre(String organ, boolean raw, EDLItems.ItemDefinition definition) {
        String state = raw ? "Raw" : "Cooked";
        ore("foodOffal" + state, definition);
        ore("food" + organ + state, definition);
        ore("foodOffal" + organ + state, definition);
        if (raw) {
            ore("rawMeat", definition);
        } else {
            ore("cookedMeat", definition);
        }
    }

    private static void ore(String name, EDLItems.ItemDefinition definition) {
        if (!definition.isRegistered()) {
            return;
        }

        ItemStack stack = definition.stack(1);
        if (!stack.isEmpty()) {
            OreDictionary.registerOre(name, stack);
        }
    }

    private static void ore(String name, EDLBlocks.BlockDefinition definition) {
        if (!definition.isRegistered() || definition.getItemBlock() == null) {
            return;
        }

        ItemStack stack = definition.stack(1);
        if (!stack.isEmpty()) {
            OreDictionary.registerOre(name, stack);
        }
    }

    private static void ore(String name, ItemStack stack) {
        if (!stack.isEmpty()) {
            OreDictionary.registerOre(name, stack);
        }
    }

    private static void optionalOre(String name, String itemId) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        if (item != null && item != Items.AIR) {
            ore(name, new ItemStack(item));
        }
    }

    private static void registerKnifeItemOres() {
        for (Item item : ForgeRegistries.ITEMS.getValuesCollection()) {
            if (item instanceof IKnifeItem) {
                ItemStack stack = new ItemStack(item);
                if (!isOreRegistered("toolKnife", stack)) {
                    ore("toolKnife", stack);
                }
            }
        }
    }

    private static boolean isOreRegistered(String name, ItemStack stack) {
        for (ItemStack existing : OreDictionary.getOres(name, false)) {
            if (OreDictionary.itemMatches(existing, stack, false)) {
                return true;
            }
        }
        return false;
    }

    private static ItemStack itemStack(String itemId) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId));
        return item == null || item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
    }

    private static void registerCandleOres() {
        int before = OreDictionary.getOres("blockCandle", false).size();
        optionalOre("blockCandle", "deeperdepths:candle");
        optionalOre("blockCandle", "deeperdepths:white_candle");
        optionalOre("blockCandle", "deeperdepths:orange_candle");
        optionalOre("blockCandle", "deeperdepths:magenta_candle");
        optionalOre("blockCandle", "deeperdepths:light_blue_candle");
        optionalOre("blockCandle", "deeperdepths:yellow_candle");
        optionalOre("blockCandle", "deeperdepths:lime_candle");
        optionalOre("blockCandle", "deeperdepths:pink_candle");
        optionalOre("blockCandle", "deeperdepths:gray_candle");
        optionalOre("blockCandle", "deeperdepths:light_gray_candle");
        optionalOre("blockCandle", "deeperdepths:cyan_candle");
        optionalOre("blockCandle", "deeperdepths:purple_candle");
        optionalOre("blockCandle", "deeperdepths:blue_candle");
        optionalOre("blockCandle", "deeperdepths:brown_candle");
        optionalOre("blockCandle", "deeperdepths:green_candle");
        optionalOre("blockCandle", "deeperdepths:red_candle");
        optionalOre("blockCandle", "deeperdepths:black_candle");
        if (OreDictionary.getOres("blockCandle", false).size() == before) {
            ore("blockCandle", new ItemStack(net.minecraft.init.Blocks.TORCH));
        }
    }

    private static void registerFarmersDelightCabinetOres() {
        optionalOre("farmersDelightCabinet", "farmersdelight:oak_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:spruce_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:birch_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:jungle_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:acacia_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:dark_oak_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:bamboo_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:cherry_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:mangrove_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:crimson_cabinet");
        optionalOre("farmersDelightCabinet", "farmersdelight:warped_cabinet");
        ore("farmersDelightCabinet", EDLBlocks.CRIMSON_CABINET);
        ore("farmersDelightCabinet", EDLBlocks.WARPED_CABINET);
        ore("farmersDelightCabinet", EDLBlocks.CHERRY_CABINET);
    }

    private static void registerGenericDyeOres() {
        for (int meta = 0; meta < 16; meta++) {
            ore("dye", new ItemStack(Items.DYE, 1, meta));
        }
    }

    private static void spoon(EDLItems.ItemDefinition definition) {
        ore("toolSpoon", definition);
        ore("spoon", definition);
    }

    private static void offsetSpatula(EDLItems.ItemDefinition definition) {
        ore("toolSpatula", definition);
        ore("spatula", definition);
        ore("toolOffsetSpatula", definition);
        ore("offsetSpatula", definition);
    }

    private static void candy(String suffix, EDLItems.ItemDefinition definition) {
        ore("candy" + suffix, definition);
        ore("foodCandy" + suffix, definition);
        ore("candy", definition);
        ore("foodCandy", definition);
    }

    private static void candyBowlValid(EDLItems.ItemDefinition definition) {
        ore("candyBowlValid", definition);
    }

    private static void registerCandyBowlValidOres() {
        candyBowlValid(EDLItems.CANDY_BLACK);
        candyBowlValid(EDLItems.CANDY_BLUE);
        candyBowlValid(EDLItems.CANDY_BROWN);
        candyBowlValid(EDLItems.CANDY_CYAN);
        candyBowlValid(EDLItems.CANDY_GREEN);
        candyBowlValid(EDLItems.CANDY_GRAY);
        candyBowlValid(EDLItems.CANDY_LIGHT_BLUE);
        candyBowlValid(EDLItems.CANDY_LIGHT_GRAY);
        candyBowlValid(EDLItems.CANDY_LIME);
        candyBowlValid(EDLItems.CANDY_MAGENTA);
        candyBowlValid(EDLItems.CANDY_ORANGE);
        candyBowlValid(EDLItems.CANDY_PINK);
        candyBowlValid(EDLItems.CANDY_PURPLE);
        candyBowlValid(EDLItems.CANDY_RED);
        candyBowlValid(EDLItems.CANDY_WHITE);
        candyBowlValid(EDLItems.CANDY_YELLOW);
        candyBowlValid(EDLItems.CARAMEL_CANDY);
        candyBowlValid(EDLItems.MINT_CANDY_BLUE);
        candyBowlValid(EDLItems.MINT_CANDY_GREEN);
        candyBowlValid(EDLItems.MINT_CANDY_RED);
        candyBowlValid(EDLItems.CANDY_CANE_BLUE);
        candyBowlValid(EDLItems.CANDY_CANE_RED);
        candyBowlValid(EDLItems.CANDY_CANE_GREEN);
        candyBowlValid(EDLItems.CANDIED_GINGER);
        candyBowlValid(EDLItems.CANDIED_CITRUS_ZEST);
    }

    private static void jelly(EDLItems.ItemDefinition definition) {
        ore("jelly", definition);
        ore("foodJelly", definition);
    }

    private static void registerDynamicJamOres() {
        if (!EDLItems.DYNAMIC_JAM.isRegistered()) {
            return;
        }
        for (ItemDynamicJam.Flavor flavor : ItemDynamicJam.Flavor.values()) {
            ItemStack stack = ItemDynamicJam.stack(flavor, 1);
            ore("jam", stack);
            ore("foodJam", stack);
            ore("jelly", stack);
            ore("foodJelly", stack);
            String suffix = toOreSuffix(flavor.getId());
            ore("jam" + suffix, stack);
            ore("foodJam" + suffix, stack);
        }
        ore("jamOrange", ItemDynamicJam.stack(ItemDynamicJam.Flavor.ORANGE, 1));
        ore("foodJamOrange", ItemDynamicJam.stack(ItemDynamicJam.Flavor.ORANGE, 1));
    }

    private static void registerChocolateBarFillingOres() {
        ore("chocolateBarFilling", EDLItems.ROASTED_PEANUTS);
        ore("chocolateBarFilling", EDLItems.ROASTED_HAZELNUTS);
        ore("chocolateBarFilling", EDLItems.DRIED_FRUIT);
        ore("chocolateBarFilling", EDLItems.CRISP_RICE);
        ore("chocolateBarFilling", EDLItems.CORN_FLAKES);
        ore("chocolateBarFilling", EDLItems.CHILI_POWDER);
        ore("chocolateBarFilling", EDLItems.TOFFEE);
        ore("chocolateBarFilling", EDLItems.GUMMIES);
        ore("chocolateBarFilling", EDLItems.PEANUT_BRITTLE);
        ore("chocolateBarFilling", EDLItems.TRAIL_MIX);
        ore("chocolateBarFilling", EDLItems.MINT);
        ore("chocolateBarFilling", EDLItems.CANDY_BLACK);
        ore("chocolateBarFilling", EDLItems.CANDY_BLUE);
        ore("chocolateBarFilling", EDLItems.CANDY_BROWN);
        ore("chocolateBarFilling", EDLItems.CANDY_CYAN);
        ore("chocolateBarFilling", EDLItems.CANDY_GRAY);
        ore("chocolateBarFilling", EDLItems.CANDY_GREEN);
        ore("chocolateBarFilling", EDLItems.CANDY_LIGHT_BLUE);
        ore("chocolateBarFilling", EDLItems.CANDY_LIGHT_GRAY);
        ore("chocolateBarFilling", EDLItems.CANDY_LIME);
        ore("chocolateBarFilling", EDLItems.CANDY_MAGENTA);
        ore("chocolateBarFilling", EDLItems.CANDY_ORANGE);
        ore("chocolateBarFilling", EDLItems.CANDY_PINK);
        ore("chocolateBarFilling", EDLItems.CANDY_PURPLE);
        ore("chocolateBarFilling", EDLItems.CANDY_RED);
        ore("chocolateBarFilling", EDLItems.CANDY_WHITE);
        ore("chocolateBarFilling", EDLItems.CANDY_YELLOW);
        ore("chocolateBarFilling", EDLItems.DARK_CHOCOLATE_CHIPS);
        ore("chocolateBarFilling", EDLItems.MILK_CHOCOLATE_CHIPS);
        ore("chocolateBarFilling", EDLItems.WHITE_CHOCOLATE_CHIPS);
        ore("chocolateBarFilling", EDLItems.SLICED_APPLE);
        ore("chocolateBarFilling", EDLItems.SLICED_LEMON);
        ore("chocolateBarFilling", EDLItems.SLICED_LIME);
        ore("chocolateBarFilling", EDLItems.SLICED_ORANGE);
        ore("chocolateBarFilling", EDLItems.SLICED_GRAPEFRUIT);
        ore("chocolateBarFilling", EDLItems.MELON_CHUNKS);
        ore("chocolateBarFilling", EDLItems.DRIED_CHILI);
    }

    private static void oreCookieDough(String suffix, EDLItems.ItemDefinition definition) {
        ore("cookieDough" + suffix, definition);
        ore("foodCookieDough" + suffix, definition);
        ore("cookieDough", definition);
        ore("foodCookieDough", definition);
    }

    private static void oreCookie(String suffix, EDLItems.ItemDefinition definition) {
        ore("cookie" + suffix, definition);
        ore("foodCookie" + suffix, definition);
        ore("foodCookie", definition);
    }

    private static void iceCream(String suffix, EDLItems.ItemDefinition definition) {
        ore("iceCream" + suffix, definition);
        ore("foodIceCream" + suffix, definition);
        ore("iceCream", definition);
        ore("foodIceCream", definition);
    }

    private static void milkshake(String suffix, EDLItems.ItemDefinition definition) {
        ore("milkshake" + suffix, definition);
        ore("foodMilkshake" + suffix, definition);
        ore("milkshake", definition);
        ore("foodMilkshake", definition);
    }
}
