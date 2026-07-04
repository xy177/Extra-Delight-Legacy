package xy177.extradelightlegacy.common.event;

import com.wdcftgg.farmersdelightlegacy.common.advancement.SimplePlayerTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import xy177.extradelightlegacy.ExtraDelightLegacy;

public final class EDLAdvancements {
    public static final SimplePlayerTrigger BURN_MINT = register("burn_mint");
    public static final SimplePlayerTrigger GRIND = register("grind");
    public static final SimplePlayerTrigger MIXING_BOWL = register("mixingbowl");

    private EDLAdvancements() {
    }

    public static void init() {
    }

    public static void trigger(SimplePlayerTrigger trigger, net.minecraft.entity.player.EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            trigger.trigger((EntityPlayerMP) player);
        }
    }

    private static SimplePlayerTrigger register(String path) {
        return CriteriaTriggers.register(new SimplePlayerTrigger(new ResourceLocation(ExtraDelightLegacy.MODID, path)));
    }
}
