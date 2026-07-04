package xy177.extradelightlegacy.common.block;

import com.wdcftgg.farmersdelightlegacy.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
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
import xy177.extradelightlegacy.common.registry.EDLItems;

import java.util.Random;

public class BlockCinnamonLog extends BlockFruitLog {
    private static final int REGEN_SEARCH_RANGE = 3;
    private final boolean stripped;

    public BlockCinnamonLog(boolean stripped) {
        this.stripped = stripped;
        setTickRandomly(stripped);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (stripped || EDLBlocks.STRIPPED_CINNAMON_LOG.getBlock() == null || EDLItems.CINNAMON_BARK.getItem() == null) {
            return false;
        }

        ItemStack held = player.getHeldItem(hand);
        if (!isAxe(held)) {
            return false;
        }

        if (!world.isRemote) {
            IBlockState strippedState = EDLBlocks.STRIPPED_CINNAMON_LOG.getBlock()
                .getDefaultState()
                .withProperty(LOG_AXIS, state.getValue(LOG_AXIS));
            world.setBlockState(pos, strippedState, 3);
            Block.spawnAsEntity(world, pos, EDLItems.CINNAMON_BARK.stack(1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, held)));
            playStripSound(world, pos);
            if (!player.capabilities.isCreativeMode) {
                held.damageItem(1, player);
            }
        }
        return true;
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!stripped || EDLBlocks.CINNAMON_LOG.getBlock() == null || !canRegrowFrom(world, pos)) {
            return;
        }

        world.setBlockState(pos, EDLBlocks.CINNAMON_LOG.getBlock()
            .getDefaultState()
            .withProperty(LOG_AXIS, state.getValue(LOG_AXIS)), 3);
    }

    private static boolean isAxe(ItemStack stack) {
        return !stack.isEmpty()
            && (stack.getItem() instanceof ItemAxe || stack.getItem().getToolClasses(stack).contains("axe"));
    }

    private static boolean canRegrowFrom(World world, BlockPos pos) {
        BlockPos top = edgeOfStrippedRun(world, pos, EnumFacing.UP);
        BlockPos bottom = edgeOfStrippedRun(world, pos, EnumFacing.DOWN);
        Block lower = world.getBlockState(bottom.down()).getBlock();
        if (isFertileSupport(lower)) {
            return true;
        }

        Block upper = world.getBlockState(top.up()).getBlock();
        return isUpperSupport(upper) && isLowerSupport(lower);
    }

    private static BlockPos edgeOfStrippedRun(World world, BlockPos origin, EnumFacing direction) {
        Block stripped = EDLBlocks.STRIPPED_CINNAMON_LOG.getBlock();
        BlockPos cursor = origin;
        for (int i = 0; i < REGEN_SEARCH_RANGE; i++) {
            BlockPos next = cursor.offset(direction);
            if (world.getBlockState(next).getBlock() != stripped) {
                break;
            }
            cursor = next;
        }
        return cursor;
    }

    private static boolean isLowerSupport(Block block) {
        return block == Blocks.DIRT
            || block == Blocks.GRASS
            || block == EDLBlocks.CINNAMON_LOG.getBlock();
    }

    private static boolean isUpperSupport(Block block) {
        return block == EDLBlocks.CINNAMON_LOG.getBlock()
            || block == EDLBlocks.CINNAMON_LEAVES.getBlock();
    }

    private static boolean isFertileSupport(Block block) {
        return block == ModBlocks.RICH_SOIL
            || block == ModBlocks.RICH_SOIL_FARMLAND
            || block == ModBlocks.ORGANIC_COMPOST;
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
