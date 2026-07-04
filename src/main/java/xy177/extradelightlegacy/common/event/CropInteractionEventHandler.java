package xy177.extradelightlegacy.common.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.BlockCornBottom;

@Mod.EventBusSubscriber(modid = ExtraDelightLegacy.MODID)
public final class CropInteractionEventHandler {
    private CropInteractionEventHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);

        if (!(state.getBlock() instanceof BlockCornBottom)) {
            return;
        }

        BlockCornBottom corn = (BlockCornBottom) state.getBlock();
        if (!corn.hasTopBlock(world, pos)) {
            return;
        }

        if (!world.isRemote) {
            corn.handleBottomRightClick(world, pos, state, event.getEntityPlayer(), event.getHand());
        }

        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
    }
}
