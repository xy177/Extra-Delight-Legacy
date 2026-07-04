package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockSimpleCrop extends BlockCrops {
    private final Supplier<Item> seedSupplier;
    private final Supplier<Item> cropSupplier;
    private final int maxAge;

    public BlockSimpleCrop(Supplier<Item> seedSupplier, Supplier<Item> cropSupplier) {
        this(seedSupplier, cropSupplier, 7);
    }

    public BlockSimpleCrop(Supplier<Item> seedSupplier, Supplier<Item> cropSupplier, int maxAge) {
        this.seedSupplier = seedSupplier;
        this.cropSupplier = cropSupplier;
        this.maxAge = maxAge;
        setSoundType(SoundType.PLANT);
        disableStats();
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return CropSoilHelper.isKnownCropSoil(state);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState soil = world.getBlockState(pos.down());
        return (world.getLight(pos) >= 8 || world.canSeeSky(pos))
            && CropSoilHelper.canSustainCrop(soil, world, pos.down(), this);
    }

    @Override
    protected Item getSeed() {
        Item seed = seedSupplier.get();
        return seed == null ? Item.getItemFromBlock(this) : seed;
    }

    @Override
    protected Item getCrop() {
        Item crop = cropSupplier.get();
        return crop == null ? getSeed() : crop;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return isMaxAge(state) ? getCrop() : getSeed();
    }
}
