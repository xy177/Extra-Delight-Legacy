package xy177.extradelightlegacy.common.crafting;

import com.wdcftgg.farmersdelightlegacy.api.recipe.knife.HuntingDropOutput;
import com.wdcftgg.farmersdelightlegacy.api.recipe.knife.HuntingDropRecipeApi;
import com.wdcftgg.farmersdelightlegacy.common.recipe.HuntingDropRecipeManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLEnchantments;
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ButcherDropRegistry {
    private static final ManagedRecipeRegistry<ButcherDrop> DROPS = new ManagedRecipeRegistry<>();
    private static final List<String> JEI_RECIPE_IDS = new ArrayList<>();

    private ButcherDropRegistry() {
    }

    public static void registerDefaults() {
        DROPS.clear();
        registerCowDrops();
        registerPigDrops();
        registerSheepDrops();
        registerChickenDrops();
        registerRabbitDrops();
        registerGoatDrops();
    }

    public static boolean register(String id, ResourceLocation entityId, ItemStack raw, ItemStack cooked,
                                   int min, int max, float chance) {
        if (entityId == null) {
            return false;
        }
        return register(id, entityId.toString(), entityId,
            entity -> entityId.equals(EntityList.getKey(entity)), raw, cooked, min, max, chance);
    }

    public static boolean remove(String id) {
        return DROPS.remove(id);
    }

    public static int removeByEntity(ResourceLocation entityId) {
        return entityId == null ? 0 : DROPS.removeIf(drop -> entityId.equals(drop.getEntityId()));
    }

    public static int removeByOutput(ItemStack output) {
        return DROPS.removeIf(drop -> RecipeManagerUtil.stackMatches(output, drop.getRaw())
            || RecipeManagerUtil.stackMatches(output, drop.getCooked()));
    }

    public static int removeAll() {
        return DROPS.removeAll();
    }

    public static List<ButcherDrop> getDrops(EntityLivingBase entity) {
        if (entity == null) {
            return Collections.emptyList();
        }
        List<ButcherDrop> matches = new ArrayList<>();
        for (ButcherDrop drop : DROPS.getRecipes()) {
            if (drop.matches(entity)) {
                matches.add(drop);
            }
        }
        return matches;
    }

    public static List<ButcherDrop> getDrops() {
        return DROPS.getRecipes();
    }

    public static void captureBaseline() {
        DROPS.captureBaseline();
    }

    public static void restoreBaseline() {
        DROPS.restoreBaseline();
    }

    public static void syncHuntingJeiRecipes() {
        for (String id : JEI_RECIPE_IDS) {
            HuntingDropRecipeApi.unregisterRecipe(id);
        }
        JEI_RECIPE_IDS.clear();
        if (EDLEnchantments.BUTCHER == null) {
            return;
        }

        Map<String, List<ButcherDrop>> grouped = new LinkedHashMap<>();
        for (ButcherDrop drop : DROPS.getRecipes()) {
            grouped.computeIfAbsent(drop.getTargetKey(), ignored -> new ArrayList<>()).add(drop);
        }
        for (Map.Entry<String, List<ButcherDrop>> group : grouped.entrySet()) {
            List<ButcherDrop> drops = group.getValue();
            if (drops.isEmpty()) {
                continue;
            }
            List<HuntingDropOutput> outputs = new ArrayList<>();
            for (ButcherDrop drop : drops) {
                outputs.add(HuntingDropOutput.of(drop.getRaw(), drop.getChance(), 0.04F));
            }
            ButcherDrop first = drops.get(0);
            String recipeId = ExtraDelightLegacy.MODID + ":butcher/"
                + group.getKey().replace(':', '/').replaceAll("[^a-z0-9_./-]", "_");
            if (HuntingDropRecipeApi.registerRecipeAdvanceJei(
                recipeId,
                first.getMatcher(),
                outputs,
                false,
                first.getEntityId(),
                null,
                Collections.singletonList("extradelightlegacy.jei.butcher_drop")
            )) {
                JEI_RECIPE_IDS.add(recipeId);
            }
        }
    }

    private static boolean register(String id, String targetKey, ResourceLocation entityId,
                                    HuntingDropRecipeManager.HuntingTargetMatcher matcher,
                                    ItemStack raw, ItemStack cooked, int min, int max, float chance) {
        if (matcher == null || entityId == null || raw == null || raw.isEmpty() || min < 0 || max < min
            || chance < 0.0F || chance > 1.0F) {
            return false;
        }
        String normalized = RecipeManagerUtil.normalizeId(id);
        return normalized != null && DROPS.register(normalized,
            new ButcherDrop(normalized, targetKey, entityId, matcher, raw, cooked, min, max, chance));
    }

    private static void registerCowDrops() {
        Target target = new Target("minecraft:cow", new ResourceLocation("minecraft", "cow"), entity -> entity instanceof EntityCow);
        registerMain(target, "beef_scraps", EDLItems.BEEF_SCRAPS, EDLItems.COOKED_BEEF_SCRAPS, 1, 3);
        registerSecondary(target, "cubed_beef", EDLItems.CUBED_BEEF, EDLItems.COOKED_CUBED_BEEF);
        registerSecondary(target, "ground_beef", EDLItems.GROUND_BEEF, EDLItems.COOKED_GROUND_BEEF);
        registerSecondary(target, "beef_ribs", EDLItems.BEEF_RIBS, EDLItems.COOKED_BEEF_RIBS);
        registerRare(target, "beef_roast", EDLItems.BEEF_ROAST, EDLItems.COOKED_BEEF_ROAST);
        registerRare(target, "beef_stewmeat", EDLItems.BEEF_STEWMEAT, EDLItems.COOKED_BEEF_STEWMEAT);
        registerRare(target, "oxtail", EDLItems.OXTAIL, EDLItems.COOKED_OXTAIL);
        registerRare(target, "tongue", EDLItems.TONGUE, EDLItems.COOKED_TONGUE);
        registerLargeOffal(target);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.55F);
        registerMaterial(target, "fat", EDLItems.FAT, 0.45F);
        registerMaterial(target, "gelatin", EDLItems.GELATIN, 0.18F);
    }

    private static void registerPigDrops() {
        Target target = new Target("minecraft:pig", new ResourceLocation("minecraft", "pig"), entity -> entity instanceof EntityPig);
        registerMain(target, "pork_scraps", EDLItems.PORK_SCRAPS, EDLItems.COOKED_PORK_SCRAPS, 1, 3);
        registerSecondary(target, "cubed_pork", EDLItems.CUBED_PORK, EDLItems.COOKED_CUBED_PORK);
        registerSecondary(target, "ground_pork", EDLItems.GROUND_PORK, EDLItems.COOKED_GROUND_PORK);
        registerSecondary(target, "pork_ribs", EDLItems.PORK_RIBS, EDLItems.COOKED_PORK_RIBS);
        registerRare(target, "pork_roast", EDLItems.PORK_ROAST, EDLItems.COOKED_PORK_ROAST);
        registerRare(target, "pork_stewmeat", EDLItems.PORK_STEWMEAT, EDLItems.COOKED_PORK_STEWMEAT);
        registerLargeOffal(target);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.55F);
        registerMaterial(target, "fat", EDLItems.FAT, 0.7F);
        registerMaterial(target, "gelatin", EDLItems.GELATIN, 0.12F);
    }

    private static void registerSheepDrops() {
        Target target = new Target("minecraft:sheep", new ResourceLocation("minecraft", "sheep"), entity -> entity instanceof EntitySheep);
        registerMain(target, "lamb_scraps", EDLItems.LAMB_SCRAPS, EDLItems.COOKED_LAMB_SCRAPS, 1, 2);
        registerSecondary(target, "cubed_lamb", EDLItems.CUBED_LAMB, EDLItems.COOKED_CUBED_LAMB);
        registerSecondary(target, "ground_lamb", EDLItems.GROUND_LAMB, EDLItems.COOKED_GROUND_LAMB);
        registerSecondary(target, "lamb_ribs", EDLItems.LAMB_RIBS, EDLItems.COOKED_LAMB_RIBS);
        registerRare(target, "lamb_roast", EDLItems.LAMB_ROAST, EDLItems.COOKED_LAMB_ROAST);
        registerRare(target, "lamb_stewmeat", EDLItems.LAMB_STEWMEAT, EDLItems.COOKED_LAMB_STEWMEAT);
        registerLargeOffal(target);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.45F);
        registerMaterial(target, "fat", EDLItems.FAT, 0.3F);
        registerMaterial(target, "gelatin", EDLItems.GELATIN, 0.1F);
    }

    private static void registerChickenDrops() {
        Target target = new Target("minecraft:chicken", new ResourceLocation("minecraft", "chicken"), entity -> entity instanceof EntityChicken);
        registerMain(target, "chicken_scraps", EDLItems.CHICKEN_SCRAPS, EDLItems.COOKED_CHICKEN_SCRAPS, 1, 2);
        registerSecondary(target, "chicken_breast", EDLItems.CHICKEN_BREAST, EDLItems.COOKED_CHICKEN_BREAST);
        registerSecondary(target, "chicken_leg", EDLItems.CHICKEN_LEG, EDLItems.COOKED_CHICKEN_LEG);
        registerSecondary(target, "chicken_thigh", EDLItems.CHICKEN_THIGH, EDLItems.COOKED_CHICKEN_THIGH);
        registerSecondary(target, "chicken_wing", EDLItems.CHICKEN_WING, EDLItems.COOKED_CHICKEN_WING);
        registerRare(target, "cubed_chicken", EDLItems.CUBED_CHICKEN, EDLItems.COOKED_CUBED_CHICKEN);
        registerRare(target, "ground_chicken", EDLItems.GROUND_CHICKEN, EDLItems.COOKED_GROUND_CHICKEN);
        registerRare(target, "chicken_stewmeat", EDLItems.CHICKEN_STEWMEAT, EDLItems.COOKED_CHICKEN_STEWMEAT);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.25F);
        registerMaterial(target, "gelatin", EDLItems.GELATIN, 0.08F);
    }

    private static void registerRabbitDrops() {
        Target target = new Target("minecraft:rabbit", new ResourceLocation("minecraft", "rabbit"), entity -> entity instanceof EntityRabbit);
        registerMain(target, "rabbit_scraps", EDLItems.RABBIT_SCRAPS, EDLItems.COOKED_RABBIT_SCRAPS, 0, 1);
        registerSecondary(target, "rabbit_saddle", EDLItems.RABBIT_SADDLE, EDLItems.COOKED_RABBIT_SADDLE);
        registerSecondary(target, "rabbit_thigh", EDLItems.RABBIT_THIGH, EDLItems.COOKED_RABBIT_THIGH);
        registerSecondary(target, "rabbit_leg", EDLItems.RABBIT_LEG, EDLItems.COOKED_RABBIT_LEG);
        registerRare(target, "rabbit_stewmeat", EDLItems.RABBIT_STEWMEAT, EDLItems.COOKED_RABBIT_STEWMEAT);
        registerRare(target, "ground_rabbit", EDLItems.GROUND_RABBIT, EDLItems.COOKED_GROUND_RABBIT);
        registerRare(target, "cubed_rabbit", EDLItems.CUBED_RABBIT, EDLItems.COOKED_CUBED_RABBIT);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.18F);
    }

    private static void registerGoatDrops() {
        ResourceLocation entityId = EDLItems.firstGoatEntityId();
        if (entityId == null) {
            return;
        }
        Target target = new Target("goat", entityId, entity -> EDLItems.isGoatEntityId(EntityList.getKey(entity)));
        registerMain(target, "goat_scraps", EDLItems.GOAT_SCRAPS, EDLItems.COOKED_GOAT_SCRAPS, 1, 2);
        registerSecondary(target, "goat_chop", EDLItems.GOAT_CHOP, EDLItems.COOKED_GOAT_CHOP);
        registerSecondary(target, "goat_ribs", EDLItems.GOAT_RIBS, EDLItems.COOKED_GOAT_RIBS);
        registerRare(target, "goat_roast", EDLItems.GOAT_ROAST, EDLItems.COOKED_GOAT_ROAST);
        registerRare(target, "goat_stewmeat", EDLItems.GOAT_STEWMEAT, EDLItems.COOKED_GOAT_STEWMEAT);
        registerRare(target, "ground_goat", EDLItems.GROUND_GOAT, EDLItems.COOKED_GROUND_GOAT);
        registerRare(target, "cubed_goat", EDLItems.CUBED_GOAT, EDLItems.COOKED_CUBED_GOAT);
        registerLargeOffal(target);
        registerMaterial(target, "blood", EDLItems.BLOOD, 0.45F);
        registerMaterial(target, "fat", EDLItems.FAT, 0.35F);
        registerMaterial(target, "gelatin", EDLItems.GELATIN, 0.1F);
    }

    private static void registerLargeOffal(Target target) {
        registerRare(target, "brain", EDLItems.BRAIN, EDLItems.COOKED_BRAIN);
        registerRare(target, "heart", EDLItems.HEART, EDLItems.COOKED_HEART);
        registerRare(target, "kidney", EDLItems.KIDNEY, EDLItems.COOKED_KIDNEY);
        registerRare(target, "liver", EDLItems.LIVER, EDLItems.COOKED_LIVER);
        registerRare(target, "lung", EDLItems.LUNG, EDLItems.COOKED_LUNG);
        registerRare(target, "stomach", EDLItems.STOMACH, EDLItems.COOKED_STOMACH);
        registerRare(target, "tripe", EDLItems.TRIPE, EDLItems.COOKED_TRIPE);
        registerRare(target, "eyeball", EDLItems.EYEBALL, EDLItems.COOKED_EYEBALL);
    }

    private static void registerMain(Target target, String name, EDLItems.ItemDefinition raw,
                                     EDLItems.ItemDefinition cooked, int min, int max) {
        registerDefault(target, name, raw, cooked, min, max, 1.0F);
    }

    private static void registerSecondary(Target target, String name, EDLItems.ItemDefinition raw,
                                          EDLItems.ItemDefinition cooked) {
        registerDefault(target, name, raw, cooked, 1, 1, 0.45F);
    }

    private static void registerRare(Target target, String name, EDLItems.ItemDefinition raw,
                                     EDLItems.ItemDefinition cooked) {
        registerDefault(target, name, raw, cooked, 1, 1, 0.22F);
    }

    private static void registerMaterial(Target target, String name, EDLItems.ItemDefinition item, float chance) {
        registerDefault(target, name, item, null, 1, 1, chance);
    }

    private static void registerDefault(Target target, String name, EDLItems.ItemDefinition raw,
                                        EDLItems.ItemDefinition cooked, int min, int max, float chance) {
        ItemStack rawStack = raw == null ? ItemStack.EMPTY : raw.stack(1);
        ItemStack cookedStack = cooked == null ? ItemStack.EMPTY : cooked.stack(1);
        register(ExtraDelightLegacy.MODID + ":butcher/" + target.key.replace(':', '/') + "/" + name,
            target.key, target.entityId, target.matcher, rawStack, cookedStack, min, max, chance);
    }

    private static final class Target {
        private final String key;
        private final ResourceLocation entityId;
        private final HuntingDropRecipeManager.HuntingTargetMatcher matcher;

        private Target(String key, ResourceLocation entityId, HuntingDropRecipeManager.HuntingTargetMatcher matcher) {
            this.key = key;
            this.entityId = entityId;
            this.matcher = matcher;
        }
    }

    public static final class ButcherDrop {
        private final String id;
        private final String targetKey;
        private final ResourceLocation entityId;
        private final HuntingDropRecipeManager.HuntingTargetMatcher matcher;
        private final ItemStack raw;
        private final ItemStack cooked;
        private final int min;
        private final int max;
        private final float chance;

        private ButcherDrop(String id, String targetKey, ResourceLocation entityId,
                            HuntingDropRecipeManager.HuntingTargetMatcher matcher,
                            ItemStack raw, ItemStack cooked, int min, int max, float chance) {
            this.id = id;
            this.targetKey = targetKey;
            this.entityId = entityId;
            this.matcher = matcher;
            this.raw = raw.copy();
            this.cooked = cooked == null ? ItemStack.EMPTY : cooked.copy();
            this.raw.setCount(1);
            if (!this.cooked.isEmpty()) {
                this.cooked.setCount(1);
            }
            this.min = min;
            this.max = max;
            this.chance = chance;
        }

        public String getId() {
            return id;
        }

        public String getTargetKey() {
            return targetKey;
        }

        public ResourceLocation getEntityId() {
            return entityId;
        }

        public HuntingDropRecipeManager.HuntingTargetMatcher getMatcher() {
            return matcher;
        }

        public ItemStack getRaw() {
            return raw.copy();
        }

        public ItemStack getCooked() {
            return cooked.copy();
        }

        public float getChance() {
            return chance;
        }

        public boolean matches(EntityLivingBase entity) {
            return matcher.matches(entity);
        }

        public ItemStack roll(EntityLivingBase entity, int butcherLevel, int looting, boolean burning) {
            ItemStack result = burning && !cooked.isEmpty() ? cooked : raw;
            if (result.isEmpty()) {
                return ItemStack.EMPTY;
            }
            float adjustedChance = Math.min(1.0F, chance + (butcherLevel - 1) * 0.12F + looting * 0.04F);
            if (entity.getRNG().nextFloat() > adjustedChance) {
                return ItemStack.EMPTY;
            }
            int count = min + (max > min ? entity.getRNG().nextInt(max - min + 1) : 0);
            if (looting > 0 && entity.getRNG().nextFloat() < 0.55F) {
                count += entity.getRNG().nextInt(looting + 1);
            }
            if (butcherLevel > 1 && entity.getRNG().nextFloat() < 0.45F) {
                count += entity.getRNG().nextInt(butcherLevel);
            }
            if (count <= 0) {
                return ItemStack.EMPTY;
            }
            ItemStack stack = result.copy();
            stack.setCount(count);
            return stack;
        }
    }
}
