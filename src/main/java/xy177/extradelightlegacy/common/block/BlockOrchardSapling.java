package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockOrchardSapling extends BlockBush implements IGrowable {
    private final Supplier<Block> leavesSupplier;
    private final Supplier<Block> logSupplier;
    private final int baseHeight;

    public BlockOrchardSapling(Supplier<Block> leavesSupplier, Supplier<Block> logSupplier, int baseHeight) {
        this.leavesSupplier = leavesSupplier;
        this.logSupplier = logSupplier;
        this.baseHeight = baseHeight;
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        if (!world.isRemote && world.getLightFromNeighbors(pos.up()) >= 9 && random.nextInt(7) == 0) {
            grow(world, pos, random);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState soil = world.getBlockState(pos.down());
        return CropSoilHelper.isGroundPlantSoil(soil);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state) {
        return random.nextFloat() < 0.45F;
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, IBlockState state) {
        grow(world, pos, random);
    }

    private void grow(World world, BlockPos pos, Random random) {
        Block leaves = leavesSupplier.get();
        Block log = logSupplier.get();
        int trunkHeight = baseHeight + random.nextInt(3);
        if (leaves == null || log == null || !canGenerate(world, pos, trunkHeight)) {
            return;
        }

        IBlockState logState = log.getDefaultState();
        for (int y = 0; y < trunkHeight; y++) {
            world.setBlockState(pos.up(y), logState, 2);
        }

        BlockPos foliageCenter = pos.up(trunkHeight);
        placeFruitLeafLayer(world, foliageCenter, -3, 2, leaves, random, true);
        placeFruitLeafLayer(world, foliageCenter, -2, 3, leaves, random, false);
        placeFruitLeafLayer(world, foliageCenter, -1, 3, leaves, random, false);
        placeFruitLeafLayer(world, foliageCenter, 0, 2, leaves, random, true);
    }

    private boolean canGenerate(World world, BlockPos pos, int trunkHeight) {
        for (int y = 1; y < trunkHeight; y++) {
            if (!isReplaceable(world, pos.up(y))) {
                return false;
            }
        }

        BlockPos foliageCenter = pos.up(trunkHeight);
        return canPlaceLeafLayer(world, foliageCenter, -3, 2)
            && canPlaceLeafLayer(world, foliageCenter, -2, 3)
            && canPlaceLeafLayer(world, foliageCenter, -1, 3)
            && canPlaceLeafLayer(world, foliageCenter, 0, 2);
    }

    private boolean canPlaceLeafLayer(World world, BlockPos center, int localY, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (!shouldSkipLeafPosition(dx, localY, dz, radius, null)
                    && !isReplaceable(world, center.add(dx, localY, dz))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isReplaceable(World world, BlockPos pos) {
        return world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos);
    }

    private void placeFruitLeafLayer(World world, BlockPos center, int localY, int radius, Block leaves, Random random, boolean fruitLayer) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (!shouldSkipLeafPosition(dx, localY, dz, radius, random)) {
                    boolean fruitCapable = fruitLayer || isOuterFruitLeaf(dx, dz, radius);
                    placeLeaves(world, center.add(dx, localY, dz), getLeafState(leaves, random, fruitCapable));
                }
            }
        }
    }

    private IBlockState getLeafState(Block leaves, Random random, boolean fruitCapable) {
        if (leaves instanceof BlockOrchardLeaves) {
            return ((BlockOrchardLeaves) leaves).getGeneratedState(random, fruitCapable);
        }
        return leaves.getDefaultState();
    }

    private boolean isOuterFruitLeaf(int dx, int dz, int radius) {
        return Math.abs(dx) >= radius - 1 || Math.abs(dz) >= radius - 1;
    }

    private boolean shouldSkipLeafPosition(int dx, int localY, int dz, int radius, Random random) {
        if (radius <= 0) {
            return false;
        }

        int ax = Math.abs(dx);
        int az = Math.abs(dz);
        if (ax == radius && az == radius) {
            return true;
        }

        boolean outerCorner = ax + az > radius + 1;
        return outerCorner && (random == null || random.nextBoolean());
    }

    private void placeLeaves(World world, BlockPos pos, IBlockState state) {
        if (world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
            world.setBlockState(pos, state, 2);
        }
    }
}
