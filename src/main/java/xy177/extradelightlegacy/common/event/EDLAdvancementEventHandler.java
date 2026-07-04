package xy177.extradelightlegacy.common.event;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.common.block.BlockMintCrop;

public class EDLAdvancementEventHandler {
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isRemote) {
            return;
        }

        ItemStack held = event.getItemStack();
        if (!held.isEmpty()
            && held.getItem() == Items.FLINT_AND_STEEL
            && event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockMintCrop) {
            EDLAdvancements.trigger(EDLAdvancements.BURN_MINT, event.getEntityPlayer());
        }
    }
}
