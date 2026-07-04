package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BlockCornTop extends BlockSimpleCrop {
    private final Supplier<Block> bottomSupplier;

    public BlockCornTop(Supplier<Item> seedSupplier, Supplier<Item> cropSupplier, Supplier<Block> bottomSupplier) {
        super(seedSupplier, cropSupplier, 3);
        this.bottomSupplier = bottomSupplier;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == bottomSupplier.get();
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState below = world.getBlockState(pos.down());
        return (world.getLight(pos) >= 8 || world.canSeeSky(pos))
            && below.getBlock() == bottomSupplier.get();
    }
}
