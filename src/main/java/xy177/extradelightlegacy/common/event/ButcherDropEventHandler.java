package xy177.extradelightlegacy.common.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.common.registry.EDLEnchantments;
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.ArrayList;
import java.util.List;

public final class ButcherDropEventHandler {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack weapon = player.getHeldItemMainhand();
        int butcherLevel = EDLEnchantments.getButcherLevel(weapon);
        if (butcherLevel <= 0 || !EDLEnchantments.isKnife(weapon)) {
            return;
        }

        EntityLivingBase entity = event.getEntityLiving();
        List<Drop> drops = getDrops(entity);
        if (drops.isEmpty()) {
            return;
        }

        int looting = Math.max(0, event.getLootingLevel());
        boolean burning = entity.isBurning();
        for (Drop drop : drops) {
            ItemStack stack = drop.roll(entity, butcherLevel, looting, burning);
            if (!stack.isEmpty()) {
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5D, entity.posZ, stack));
            }
        }
    }

    private static List<Drop> getDrops(EntityLivingBase entity) {
        List<Drop> drops = new ArrayList<>();
        if (entity instanceof EntityCow) {
            addCowDrops(drops);
        } else if (entity instanceof EntityPig) {
            addPigDrops(drops);
        } else if (entity instanceof EntitySheep) {
            addSheepDrops(drops);
        } else if (entity instanceof EntityChicken) {
            addChickenDrops(drops);
        } else if (entity instanceof EntityRabbit) {
            addRabbitDrops(drops);
        } else if (isGoatLike(entity)) {
            addGoatDrops(drops);
        }
        return drops;
    }

    private static boolean isGoatLike(EntityLivingBase entity) {
        net.minecraft.util.ResourceLocation id = EntityList.getKey(entity);
        return EDLItems.isGoatEntityId(id);
    }

    private static void addCowDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.BEEF_SCRAPS, EDLItems.COOKED_BEEF_SCRAPS, 1, 3));
        drops.add(Drop.secondary(EDLItems.CUBED_BEEF, EDLItems.COOKED_CUBED_BEEF));
        drops.add(Drop.secondary(EDLItems.GROUND_BEEF, EDLItems.COOKED_GROUND_BEEF));
        drops.add(Drop.secondary(EDLItems.BEEF_RIBS, EDLItems.COOKED_BEEF_RIBS));
        drops.add(Drop.rare(EDLItems.BEEF_ROAST, EDLItems.COOKED_BEEF_ROAST));
        drops.add(Drop.rare(EDLItems.BEEF_STEWMEAT, EDLItems.COOKED_BEEF_STEWMEAT));
        drops.add(Drop.rare(EDLItems.OXTAIL, EDLItems.COOKED_OXTAIL));
        drops.add(Drop.rare(EDLItems.TONGUE, EDLItems.COOKED_TONGUE));
        addLargeOffalDrops(drops);
        drops.add(Drop.material(EDLItems.BLOOD, 0.55F));
        drops.add(Drop.material(EDLItems.FAT, 0.45F));
        drops.add(Drop.material(EDLItems.GELATIN, 0.18F));
    }

    private static void addPigDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.PORK_SCRAPS, EDLItems.COOKED_PORK_SCRAPS, 1, 3));
        drops.add(Drop.secondary(EDLItems.CUBED_PORK, EDLItems.COOKED_CUBED_PORK));
        drops.add(Drop.secondary(EDLItems.GROUND_PORK, EDLItems.COOKED_GROUND_PORK));
        drops.add(Drop.secondary(EDLItems.PORK_RIBS, EDLItems.COOKED_PORK_RIBS));
        drops.add(Drop.rare(EDLItems.PORK_ROAST, EDLItems.COOKED_PORK_ROAST));
        drops.add(Drop.rare(EDLItems.PORK_STEWMEAT, EDLItems.COOKED_PORK_STEWMEAT));
        addLargeOffalDrops(drops);
        drops.add(Drop.material(EDLItems.BLOOD, 0.55F));
        drops.add(Drop.material(EDLItems.FAT, 0.7F));
        drops.add(Drop.material(EDLItems.GELATIN, 0.12F));
    }

    private static void addSheepDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.LAMB_SCRAPS, EDLItems.COOKED_LAMB_SCRAPS, 1, 2));
        drops.add(Drop.secondary(EDLItems.CUBED_LAMB, EDLItems.COOKED_CUBED_LAMB));
        drops.add(Drop.secondary(EDLItems.GROUND_LAMB, EDLItems.COOKED_GROUND_LAMB));
        drops.add(Drop.secondary(EDLItems.LAMB_RIBS, EDLItems.COOKED_LAMB_RIBS));
        drops.add(Drop.rare(EDLItems.LAMB_ROAST, EDLItems.COOKED_LAMB_ROAST));
        drops.add(Drop.rare(EDLItems.LAMB_STEWMEAT, EDLItems.COOKED_LAMB_STEWMEAT));
        addLargeOffalDrops(drops);
        drops.add(Drop.material(EDLItems.BLOOD, 0.45F));
        drops.add(Drop.material(EDLItems.FAT, 0.35F));
        drops.add(Drop.material(EDLItems.GELATIN, 0.1F));
    }

    private static void addChickenDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.CHICKEN_SCRAPS, EDLItems.COOKED_CHICKEN_SCRAPS, 1, 2));
        drops.add(Drop.secondary(EDLItems.CHICKEN_BREAST, EDLItems.COOKED_CHICKEN_BREAST));
        drops.add(Drop.secondary(EDLItems.CHICKEN_LEG, EDLItems.COOKED_CHICKEN_LEG));
        drops.add(Drop.secondary(EDLItems.CHICKEN_THIGH, EDLItems.COOKED_CHICKEN_THIGH));
        drops.add(Drop.secondary(EDLItems.CHICKEN_WING, EDLItems.COOKED_CHICKEN_WING));
        drops.add(Drop.rare(EDLItems.CUBED_CHICKEN, EDLItems.COOKED_CUBED_CHICKEN));
        drops.add(Drop.rare(EDLItems.GROUND_CHICKEN, EDLItems.COOKED_GROUND_CHICKEN));
        drops.add(Drop.rare(EDLItems.CHICKEN_STEWMEAT, EDLItems.COOKED_CHICKEN_STEWMEAT));
        drops.add(Drop.material(EDLItems.BLOOD, 0.25F));
        drops.add(Drop.material(EDLItems.GELATIN, 0.08F));
    }

    private static void addRabbitDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.RABBIT_SCRAPS, EDLItems.COOKED_RABBIT_SCRAPS, 0, 1));
        drops.add(Drop.secondary(EDLItems.RABBIT_SADDLE, EDLItems.COOKED_RABBIT_SADDLE));
        drops.add(Drop.secondary(EDLItems.RABBIT_THIGH, EDLItems.COOKED_RABBIT_THIGH));
        drops.add(Drop.secondary(EDLItems.RABBIT_LEG, EDLItems.COOKED_RABBIT_LEG));
        drops.add(Drop.rare(EDLItems.RABBIT_STEWMEAT, EDLItems.COOKED_RABBIT_STEWMEAT));
        drops.add(Drop.rare(EDLItems.GROUND_RABBIT, EDLItems.COOKED_GROUND_RABBIT));
        drops.add(Drop.rare(EDLItems.CUBED_RABBIT, EDLItems.COOKED_CUBED_RABBIT));
        drops.add(Drop.material(EDLItems.BLOOD, 0.18F));
    }

    private static void addGoatDrops(List<Drop> drops) {
        drops.add(Drop.main(EDLItems.GOAT_SCRAPS, EDLItems.COOKED_GOAT_SCRAPS, 1, 2));
        drops.add(Drop.secondary(EDLItems.GOAT_CHOP, EDLItems.COOKED_GOAT_CHOP));
        drops.add(Drop.secondary(EDLItems.GOAT_RIBS, EDLItems.COOKED_GOAT_RIBS));
        drops.add(Drop.rare(EDLItems.GOAT_ROAST, EDLItems.COOKED_GOAT_ROAST));
        drops.add(Drop.rare(EDLItems.GOAT_STEWMEAT, EDLItems.COOKED_GOAT_STEWMEAT));
        drops.add(Drop.rare(EDLItems.GROUND_GOAT, EDLItems.COOKED_GROUND_GOAT));
        drops.add(Drop.rare(EDLItems.CUBED_GOAT, EDLItems.COOKED_CUBED_GOAT));
        addLargeOffalDrops(drops);
        drops.add(Drop.material(EDLItems.BLOOD, 0.45F));
        drops.add(Drop.material(EDLItems.FAT, 0.35F));
        drops.add(Drop.material(EDLItems.GELATIN, 0.1F));
    }

    private static void addLargeOffalDrops(List<Drop> drops) {
        drops.add(Drop.rare(EDLItems.BRAIN, EDLItems.COOKED_BRAIN));
        drops.add(Drop.rare(EDLItems.HEART, EDLItems.COOKED_HEART));
        drops.add(Drop.rare(EDLItems.KIDNEY, EDLItems.COOKED_KIDNEY));
        drops.add(Drop.rare(EDLItems.LIVER, EDLItems.COOKED_LIVER));
        drops.add(Drop.rare(EDLItems.LUNG, EDLItems.COOKED_LUNG));
        drops.add(Drop.rare(EDLItems.STOMACH, EDLItems.COOKED_STOMACH));
        drops.add(Drop.rare(EDLItems.TRIPE, EDLItems.COOKED_TRIPE));
        drops.add(Drop.rare(EDLItems.EYEBALL, EDLItems.COOKED_EYEBALL));
    }

    private static final class Drop {
        private final EDLItems.ItemDefinition raw;
        private final EDLItems.ItemDefinition cooked;
        private final int min;
        private final int max;
        private final float chance;

        private Drop(EDLItems.ItemDefinition raw, EDLItems.ItemDefinition cooked, int min, int max, float chance) {
            this.raw = raw;
            this.cooked = cooked;
            this.min = min;
            this.max = max;
            this.chance = chance;
        }

        private static Drop main(EDLItems.ItemDefinition raw, EDLItems.ItemDefinition cooked, int min, int max) {
            return new Drop(raw, cooked, min, max, 1.0F);
        }

        private static Drop secondary(EDLItems.ItemDefinition raw, EDLItems.ItemDefinition cooked) {
            return new Drop(raw, cooked, 1, 1, 0.45F);
        }

        private static Drop rare(EDLItems.ItemDefinition raw, EDLItems.ItemDefinition cooked) {
            return new Drop(raw, cooked, 1, 1, 0.22F);
        }

        private static Drop material(EDLItems.ItemDefinition item, float chance) {
            return new Drop(item, null, 1, 1, chance);
        }

        private ItemStack roll(EntityLivingBase entity, int butcherLevel, int looting, boolean burning) {
            EDLItems.ItemDefinition definition = burning && cooked != null ? cooked : raw;
            if (definition == null || !definition.isRegistered()) {
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
            return count <= 0 ? ItemStack.EMPTY : definition.stack(count);
        }
    }
}
