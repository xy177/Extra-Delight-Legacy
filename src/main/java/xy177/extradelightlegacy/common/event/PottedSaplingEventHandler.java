package xy177.extradelightlegacy.common.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

@Mod.EventBusSubscriber(modid = ExtraDelightLegacy.MODID)
public final class PottedSaplingEventHandler {
    private PottedSaplingEventHandler() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() != Blocks.FLOWER_POT) {
            return;
        }

        ItemStack held = event.getEntityPlayer().getHeldItem(event.getHand());
        if (held.isEmpty()) {
            return;
        }

        Block potted = getPottedBlock(held.getItem());
        if (potted == null) {
            return;
        }

        if (!world.isRemote) {
            world.setBlockState(pos, potted.getDefaultState(), 3);
            if (!event.getEntityPlayer().capabilities.isCreativeMode) {
                held.shrink(1);
            }
        }

        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
    }

    private static Block getPottedBlock(Item item) {
        if (item == EDLBlocks.APPLE_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_APPLE_SAPLING.getBlock();
        }
        if (item == EDLBlocks.CINNAMON_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_CINNAMON_SAPLING.getBlock();
        }
        if (item == EDLBlocks.HAZELNUT_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_HAZELNUT_SAPLING.getBlock();
        }
        if (item == EDLBlocks.LEMON_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_LEMON_SAPLING.getBlock();
        }
        if (item == EDLBlocks.LIME_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_LIME_SAPLING.getBlock();
        }
        if (item == EDLBlocks.ORANGE_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_ORANGE_SAPLING.getBlock();
        }
        if (item == EDLBlocks.GRAPEFRUIT_SAPLING.getItemBlock()) {
            return EDLBlocks.POTTED_GRAPEFRUIT_SAPLING.getBlock();
        }
        return null;
    }
}
