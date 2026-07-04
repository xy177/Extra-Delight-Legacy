package xy177.extradelightlegacy.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipe;
import xy177.extradelightlegacy.common.crafting.BottleFluidRecipeManager;
import xy177.extradelightlegacy.common.crafting.ChillerRecipe;
import xy177.extradelightlegacy.common.crafting.ChillerRecipeManager;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipe;
import xy177.extradelightlegacy.common.crafting.DoughShapingRecipeManager;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipe;
import xy177.extradelightlegacy.common.crafting.DryingRackRecipeManager;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipe;
import xy177.extradelightlegacy.common.crafting.EvaporatorRecipeManager;
import xy177.extradelightlegacy.common.crafting.JuicerRecipe;
import xy177.extradelightlegacy.common.crafting.JuicerRecipeManager;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipe;
import xy177.extradelightlegacy.common.crafting.MeltingPotRecipeManager;
import xy177.extradelightlegacy.common.crafting.MortarRecipe;
import xy177.extradelightlegacy.common.crafting.MortarRecipeManager;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipe;
import xy177.extradelightlegacy.common.crafting.MixingBowlRecipeManager;
import xy177.extradelightlegacy.common.crafting.OvenRecipe;
import xy177.extradelightlegacy.common.crafting.OvenRecipeManager;
import xy177.extradelightlegacy.common.crafting.VatRecipe;
import xy177.extradelightlegacy.common.crafting.VatRecipeManager;
import xy177.extradelightlegacy.common.registry.EDLBlocks;
import xy177.extradelightlegacy.common.registry.EDLItems;
import xy177.extradelightlegacy.common.registry.EDLRecipes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JEIPlugin
public class ExtraDelightLegacyJeiPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
            new DryingRackRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new MortarRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new DoughShapingRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new BottleFluidRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new MixingBowlRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new ChillerRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new MeltingPotRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new OvenRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new JuicerRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new VatRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
            new EvaporatorRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void register(IModRegistry registry) {
        EDLRecipes.init();
        registry.addRecipes(createDryingRecipes(), EDLJeiRecipeTypes.DRYING);
        registry.addRecipes(createMortarRecipes(), EDLJeiRecipeTypes.MORTAR);
        registry.addRecipes(createDoughShapingRecipes(), EDLJeiRecipeTypes.DOUGH_SHAPING);
        registry.addRecipes(createBottleFluidRecipes(), EDLJeiRecipeTypes.BOTTLE_FLUID);
        registry.addRecipes(createMixingBowlRecipes(), EDLJeiRecipeTypes.MIXING_BOWL);
        registry.addRecipes(createChillerRecipes(), EDLJeiRecipeTypes.CHILLER);
        registry.addRecipes(createMeltingPotRecipes(), EDLJeiRecipeTypes.MELTING_POT);
        registry.addRecipes(createOvenRecipes(), EDLJeiRecipeTypes.OVEN);
        registry.addRecipes(createJuicerRecipes(), EDLJeiRecipeTypes.JUICER);
        registry.addRecipes(createVatRecipes(), EDLJeiRecipeTypes.VAT);
        registry.addRecipes(createEvaporatorRecipes(), EDLJeiRecipeTypes.EVAPORATOR);
        if (EDLBlocks.DRYING_RACK.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.DRYING_RACK.getItemBlock()), EDLJeiRecipeTypes.DRYING);
        }
        if (EDLBlocks.MORTAR_STONE.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.MORTAR_STONE.getItemBlock()), EDLJeiRecipeTypes.MORTAR);
        }
        if (EDLItems.PESTLE_STONE.isRegistered()) {
            registry.addRecipeCatalyst(EDLItems.PESTLE_STONE.stack(1), EDLJeiRecipeTypes.MORTAR);
        }
        if (EDLBlocks.DOUGH_SHAPING.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.DOUGH_SHAPING.getItemBlock()), EDLJeiRecipeTypes.DOUGH_SHAPING);
        }
        if (EDLBlocks.MIXING_BOWL.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.MIXING_BOWL.getItemBlock()), EDLJeiRecipeTypes.MIXING_BOWL);
        }
        if (EDLBlocks.CHILLER.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.CHILLER.getItemBlock()), EDLJeiRecipeTypes.CHILLER);
        }
        if (EDLBlocks.MELTING_POT.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.MELTING_POT.getItemBlock()), EDLJeiRecipeTypes.MELTING_POT);
        }
        if (EDLBlocks.OVEN.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.OVEN.getItemBlock()), EDLJeiRecipeTypes.OVEN);
        }
        if (EDLBlocks.JUICER.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.JUICER.getItemBlock()), EDLJeiRecipeTypes.JUICER);
        }
        if (EDLBlocks.VAT.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.VAT.getItemBlock()), EDLJeiRecipeTypes.VAT);
        }
        if (EDLBlocks.VAT_LID.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.VAT_LID.getItemBlock()), EDLJeiRecipeTypes.VAT);
        }
        if (EDLBlocks.EVAPORATOR.getItemBlock() != null) {
            registry.addRecipeCatalyst(new ItemStack(EDLBlocks.EVAPORATOR.getItemBlock()), EDLJeiRecipeTypes.EVAPORATOR);
        }
        if (EDLItems.WOODEN_SPOON.isRegistered()) {
            registry.addRecipeCatalyst(EDLItems.WOODEN_SPOON.stack(1), EDLJeiRecipeTypes.MIXING_BOWL);
        }
        if (EDLItems.WHISK.isRegistered()) {
            registry.addRecipeCatalyst(EDLItems.WHISK.stack(1), EDLJeiRecipeTypes.MIXING_BOWL);
        }
        registry.addRecipeCatalyst(new ItemStack(Items.GLASS_BOTTLE), EDLJeiRecipeTypes.BOTTLE_FLUID);
        for (BottleFluidRecipe recipe : BottleFluidRecipeManager.getRecipes()) {
            registry.addRecipeCatalyst(recipe.getBottle(), EDLJeiRecipeTypes.BOTTLE_FLUID);
            registry.addRecipeCatalyst(recipe.getBucket(), EDLJeiRecipeTypes.BOTTLE_FLUID);
        }
        hideSourceHiddenItems(registry);
    }

    private static void hideSourceHiddenItems(IModRegistry registry) {
        if (EDLItems.EASTER_EGG.isRegistered()) {
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(EDLItems.EASTER_EGG.stack(1));
        }
        if (EDLItems.JAM.isRegistered()) {
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(EDLItems.JAM.stack(1));
        }
        if (EDLItems.GLOW_BERRY_JAM.isRegistered()) {
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(EDLItems.GLOW_BERRY_JAM.stack(1));
        }
        if (EDLItems.GOLDEN_APPLE_JAM.isRegistered()) {
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(EDLItems.GOLDEN_APPLE_JAM.stack(1));
        }
        if (EDLItems.MINT_JELLY.isRegistered()) {
            registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(EDLItems.MINT_JELLY.stack(1));
        }
    }

    private static List<DryingRackJeiRecipe> createDryingRecipes() {
        List<DryingRackJeiRecipe> recipes = new ArrayList<>();
        for (DryingRackRecipe recipe : DryingRackRecipeManager.getRecipes()) {
            recipes.add(new DryingRackJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<MortarJeiRecipe> createMortarRecipes() {
        List<MortarJeiRecipe> recipes = new ArrayList<>();
        for (MortarRecipe recipe : MortarRecipeManager.getRecipes()) {
            recipes.add(new MortarJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<DoughShapingJeiRecipe> createDoughShapingRecipes() {
        List<DoughShapingJeiRecipe> recipes = new ArrayList<>();
        for (DoughShapingRecipe recipe : DoughShapingRecipeManager.getRecipes()) {
            recipes.add(new DoughShapingJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<BottleFluidJeiRecipe> createBottleFluidRecipes() {
        List<BottleFluidJeiRecipe> recipes = new ArrayList<>();
        for (BottleFluidRecipe recipe : BottleFluidRecipeManager.getRecipes()) {
            recipes.add(new BottleFluidJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<MixingBowlJeiRecipe> createMixingBowlRecipes() {
        List<MixingBowlJeiRecipe> recipes = new ArrayList<>();
        for (MixingBowlRecipe recipe : MixingBowlRecipeManager.getRecipes()) {
            recipes.add(new MixingBowlJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<ChillerJeiRecipe> createChillerRecipes() {
        List<ChillerJeiRecipe> recipes = new ArrayList<>();
        for (ChillerRecipe recipe : ChillerRecipeManager.getRecipes()) {
            recipes.add(new ChillerJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<MeltingPotJeiRecipe> createMeltingPotRecipes() {
        List<MeltingPotJeiRecipe> recipes = new ArrayList<>();
        for (MeltingPotRecipe recipe : MeltingPotRecipeManager.getRecipes()) {
            recipes.add(new MeltingPotJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<OvenJeiRecipe> createOvenRecipes() {
        List<OvenJeiRecipe> recipes = new ArrayList<>();
        Map<String, List<OvenRecipe>> condensed = new LinkedHashMap<>();
        for (OvenRecipe recipe : OvenRecipeManager.getRecipes()) {
            String group = getBulkOvenGroup(recipe);
            if (group == null) {
                recipes.add(new OvenJeiRecipe(recipe));
            } else {
                if (!condensed.containsKey(group)) {
                    condensed.put(group, new ArrayList<OvenRecipe>());
                }
                condensed.get(group).add(recipe);
            }
        }
        for (List<OvenRecipe> group : condensed.values()) {
            recipes.add(new OvenJeiRecipe(group));
        }
        return recipes;
    }

    private static String getBulkOvenGroup(OvenRecipe recipe) {
        String name = recipe.getName();
        int split = name.lastIndexOf('_');
        if (split <= 0 || split >= name.length() - 1) {
            return null;
        }

        int count;
        try {
            count = Integer.parseInt(name.substring(split + 1));
        } catch (NumberFormatException ignored) {
            return null;
        }
        if (count < 1 || count > 9 || recipe.getIngredients().isEmpty() || recipe.shouldConsumeContainer()) {
            return null;
        }

        return name.substring(0, split)
            + "|container=" + stackKey(recipe.getContainer())
            + "|time=" + recipe.getCookingTime();
    }

    private static String stackKey(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem().getRegistryName() == null) {
            return "empty";
        }
        return stack.getItem().getRegistryName().toString() + ":" + stack.getMetadata();
    }

    private static List<JuicerJeiRecipe> createJuicerRecipes() {
        List<JuicerJeiRecipe> recipes = new ArrayList<>();
        for (JuicerRecipe recipe : JuicerRecipeManager.getRecipes()) {
            recipes.add(new JuicerJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<VatJeiRecipe> createVatRecipes() {
        List<VatJeiRecipe> recipes = new ArrayList<>();
        for (VatRecipe recipe : VatRecipeManager.getRecipes()) {
            recipes.add(new VatJeiRecipe(recipe));
        }
        return recipes;
    }

    private static List<EvaporatorJeiRecipe> createEvaporatorRecipes() {
        List<EvaporatorJeiRecipe> recipes = new ArrayList<>();
        for (EvaporatorRecipe recipe : EvaporatorRecipeManager.getRecipes()) {
            recipes.add(new EvaporatorJeiRecipe(recipe));
        }
        return recipes;
    }
}
