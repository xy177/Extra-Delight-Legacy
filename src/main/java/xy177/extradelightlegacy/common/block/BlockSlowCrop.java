package xy177.extradelightlegacy.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockSlowCrop extends BlockSimpleCrop {
    public BlockSlowCrop(Supplier<Item> seedSupplier, Supplier<Item> cropSupplier, int maxAge) {
        super(seedSupplier, cropSupplier, maxAge);
    }

    @Override
    public void updateTick(World world, net.minecraft.util.math.BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextInt(3) != 0) {
            super.updateTick(world, pos, state, rand);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(World world) {
        return super.getBonemealAgeIncrease(world) / 3;
    }
}
