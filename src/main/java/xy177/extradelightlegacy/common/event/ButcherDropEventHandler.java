package xy177.extradelightlegacy.common.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.common.crafting.ButcherDropRegistry;
import xy177.extradelightlegacy.common.registry.EDLEnchantments;

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
        List<ButcherDropRegistry.ButcherDrop> drops = ButcherDropRegistry.getDrops(entity);
        if (drops.isEmpty()) {
            return;
        }

        int looting = Math.max(0, event.getLootingLevel());
        boolean burning = entity.isBurning();
        for (ButcherDropRegistry.ButcherDrop drop : drops) {
            ItemStack stack = drop.roll(entity, butcherLevel, looting, burning);
            if (!stack.isEmpty()) {
                event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY + 0.5D, entity.posZ, stack));
            }
        }
    }
}
