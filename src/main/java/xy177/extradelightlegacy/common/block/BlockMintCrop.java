package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xy177.extradelightlegacy.common.config.EDLConfig;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMintCrop extends BlockBush {
    private static final AxisAlignedBB MINT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    private final Supplier<Item> dropSupplier;

    public BlockMintCrop(Supplier<Item> dropSupplier) {
        this.dropSupplier = dropSupplier;
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
        disableStats();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return MINT_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return CropSoilHelper.isGroundPlantSoil(state);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return world.getLight(pos) >= 8 && CropSoilHelper.isGroundPlantSoil(world.getBlockState(pos.down()));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item drop = dropSupplier.get();
        return drop == null ? Item.getItemFromBlock(this) : drop;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (worldIn.isRemote || !EDLConfig.shouldMintSpread() || isColdBiome(worldIn, pos)) {
            return;
        }

        int spreadRate = EDLConfig.getMintSpreadRate();
        if (spreadRate <= 0 || rand.nextInt(spreadRate) != 0 || !worldIn.isAreaLoaded(pos, 1)) {
            return;
        }

        if (hasReachedLocalSpreadCap(worldIn, pos)) {
            return;
        }

        BlockPos target = findValidSpot(worldIn, pos, state, rand);
        if (!target.equals(pos)) {
            worldIn.setBlockState(target, state, 2);
        }
    }

    private BlockPos findValidSpot(World world, BlockPos origin, IBlockState state, Random rand) {
        EnumFacing[] directions = EnumFacing.HORIZONTALS.clone();
        for (int i = directions.length - 1; i > 0; i--) {
            int swapIndex = rand.nextInt(i + 1);
            EnumFacing temp = directions[i];
            directions[i] = directions[swapIndex];
            directions[swapIndex] = temp;
        }

        int attempts = directions.length;
        for (int i = 0; i < attempts; i++) {
            BlockPos target = origin.offset(directions[i]);

            if (!target.equals(origin)
                && world.isAirBlock(target)
                && canBlockStay(world, target, state)
                && canSpreadTo(world, origin, target)) {
                return target;
            }
        }
        return origin;
    }

    private boolean hasReachedLocalSpreadCap(World world, BlockPos origin) {
        int radius = 5;
        if (!world.isAreaLoaded(origin, radius)) {
            return true;
        }

        int maxNearby = EDLConfig.getMintSpreadMaxNearby();
        int count = 0;
        for (BlockPos check : BlockPos.getAllInBoxMutable(origin.add(-radius, -1, -radius), origin.add(radius, 1, radius))) {
            if (world.getBlockState(check).getBlock() == this && ++count >= maxNearby) {
                return true;
            }
        }
        return false;
    }

    private boolean canSpreadTo(World world, BlockPos origin, BlockPos target) {
        if (hasReachedChunkSpreadCap(world, origin)) {
            return false;
        }

        int originChunkX = origin.getX() >> 4;
        int originChunkZ = origin.getZ() >> 4;
        if ((target.getX() >> 4) == originChunkX && (target.getZ() >> 4) == originChunkZ) {
            return true;
        }

        return canCrossChunkBorder(world, origin);
    }

    private boolean hasReachedChunkSpreadCap(World world, BlockPos origin) {
        int maxPerChunk = EDLConfig.getMintSpreadMaxPerChunk();
        int chunkMinX = (origin.getX() >> 4) << 4;
        int chunkMinZ = (origin.getZ() >> 4) << 4;
        int count = 0;
        for (int x = chunkMinX; x < chunkMinX + 16; x++) {
            for (int z = chunkMinZ; z < chunkMinZ + 16; z++) {
                for (int y = 0; y < world.getHeight(); y++) {
                    BlockPos check = new BlockPos(x, y, z);
                    if (world.getBlockState(check).getBlock() == this && ++count >= maxPerChunk) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canCrossChunkBorder(World world, BlockPos origin) {
        boolean westBorder = (origin.getX() & 15) == 0;
        boolean eastBorder = (origin.getX() & 15) == 15;
        boolean northBorder = (origin.getZ() & 15) == 0;
        boolean southBorder = (origin.getZ() & 15) == 15;
        if (!westBorder && !eastBorder && !northBorder && !southBorder) {
            return false;
        }

        return isBlockedOrBorderDirection(world, origin, EnumFacing.WEST, westBorder)
            && isBlockedOrBorderDirection(world, origin, EnumFacing.EAST, eastBorder)
            && isBlockedOrBorderDirection(world, origin, EnumFacing.NORTH, northBorder)
            && isBlockedOrBorderDirection(world, origin, EnumFacing.SOUTH, southBorder);
    }

    private boolean isBlockedOrBorderDirection(World world, BlockPos origin, EnumFacing facing, boolean isBorderDirection) {
        if (isBorderDirection) {
            return true;
        }

        BlockPos neighbor = origin.offset(facing);
        return !world.isAirBlock(neighbor) && world.getBlockState(neighbor).getBlock() != this;
    }

    private boolean isColdBiome(World world, BlockPos pos) {
        return world.getBiome(pos).getTemperature(pos) <= 0.15F;
    }
}
