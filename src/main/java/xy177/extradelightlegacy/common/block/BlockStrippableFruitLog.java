package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class BlockStrippableFruitLog extends BlockFruitLog {
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (EDLBlocks.STRIPPED_FRUIT_LOG.getBlock() == null) {
            return false;
        }

        ItemStack held = player.getHeldItem(hand);
        if (!isAxe(held)) {
            return false;
        }

        if (!world.isRemote) {
            IBlockState strippedState = EDLBlocks.STRIPPED_FRUIT_LOG.getBlock()
                .getDefaultState()
                .withProperty(LOG_AXIS, state.getValue(LOG_AXIS));
            world.setBlockState(pos, strippedState, 3);
            playStripSound(world, pos);
            if (!player.capabilities.isCreativeMode) {
                held.damageItem(1, player);
            }
        }
        return true;
    }

    private static boolean isAxe(ItemStack stack) {
        return !stack.isEmpty()
            && (stack.getItem() instanceof ItemAxe || stack.getItem().getToolClasses(stack).contains("axe"));
    }

    private static void playStripSound(World world, BlockPos pos) {
        SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation("futuremc", "item.axe.strip"));
        if (sound == null) {
            sound = SoundEvent.REGISTRY.getObject(new ResourceLocation("futuremc", "axe_strip"));
        }
        if (sound == null) {
            sound = SoundEvents.BLOCK_WOOD_HIT;
        }
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
