package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCornBottom extends BlockSimpleCrop {
    private final Supplier<Block> topSupplier;

    public BlockCornBottom(Supplier<Item> seedSupplier, Supplier<Block> topSupplier) {
        super(seedSupplier, seedSupplier, 3);
        this.topSupplier = topSupplier;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return !isMaxAge(state) || canGrowTop(world, pos);
    }

    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
        if (!isMaxAge(state)) {
            int nextAge = Math.min(getAge(state) + 1, getMaxAge());
            world.setBlockState(pos, withAge(nextAge), 2);
            if (nextAge >= getMaxAge()) {
                ensureTopBlock(world, pos);
            }
            return;
        }

        growTop(world, pos);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        grow(world, pos, state);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        IBlockState current = world.getBlockState(pos);
        if (current.getBlock() == this && isMaxAge(current)) {
            ensureTopBlock(world, pos);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        Block top = topSupplier.get();
        BlockPos abovePos = pos.up();
        if (top != null && world.getBlockState(abovePos).getBlock() == top) {
            world.destroyBlock(abovePos, true);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!hasTopBlock(world, pos)) {
            return false;
        }

        return handleBottomRightClick(world, pos, state, player, hand);
    }

    public boolean handleBottomRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
        if (world.isRemote) {
            return true;
        }

        ItemStack stack = player.getHeldItem(hand);
        if (isBoneMeal(stack) && ItemDye.applyBonemeal(stack, world, pos, player, hand)) {
            world.playEvent(2005, pos, 0);
        }

        // Mature corn yield belongs to the top half. Swallow bottom-half right-clicks so
        // generic right-click harvest handlers do not treat the mature bottom as a crop.
        return true;
    }

    public boolean hasTopBlock(World world, BlockPos pos) {
        Block top = topSupplier.get();
        return top != null && world.getBlockState(pos.up()).getBlock() == top;
    }

    private boolean canGrowTop(World world, BlockPos pos) {
        Block top = topSupplier.get();
        if (top == null) {
            return false;
        }

        IBlockState above = world.getBlockState(pos.up());
        return world.isAirBlock(pos.up())
            || above.getBlock() instanceof BlockCornTop && !((BlockCornTop) above.getBlock()).isMaxAge(above);
    }

    private void growTop(World world, BlockPos pos) {
        Block top = topSupplier.get();
        if (top == null) {
            return;
        }

        BlockPos abovePos = pos.up();
        IBlockState above = world.getBlockState(abovePos);
        if (above.getBlock() instanceof BlockCornTop) {
            ((BlockCornTop) above.getBlock()).grow(world, abovePos, above);
        } else if (world.isAirBlock(abovePos)) {
            world.setBlockState(abovePos, top.getDefaultState(), 2);
        }
    }

    private void ensureTopBlock(World world, BlockPos pos) {
        Block top = topSupplier.get();
        BlockPos abovePos = pos.up();
        if (top != null && world.isAirBlock(abovePos)) {
            world.setBlockState(abovePos, top.getDefaultState(), 2);
        }
    }

    private static boolean isBoneMeal(ItemStack stack) {
        return !stack.isEmpty()
            && stack.getItem() == Items.DYE
            && stack.getMetadata() == EnumDyeColor.WHITE.getDyeDamage();
    }
}
