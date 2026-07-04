package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockOrchardLeaves extends Block {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
    public static final PropertyBool STERILE = PropertyBool.create("sterile");
    private static final int MAX_AGE = 3;

    private final Supplier<Item> fruitSupplier;
    private final Supplier<Item> saplingSupplier;
    private final Supplier<Block> petalLitterSupplier;
    private final boolean fruiting;

    public BlockOrchardLeaves(Supplier<Item> fruitSupplier, Supplier<Item> saplingSupplier) {
        this(fruitSupplier, saplingSupplier, null, true);
    }

    public BlockOrchardLeaves(Supplier<Item> fruitSupplier, Supplier<Item> saplingSupplier, boolean fruiting) {
        this(fruitSupplier, saplingSupplier, null, fruiting);
    }

    public BlockOrchardLeaves(Supplier<Item> fruitSupplier, Supplier<Item> saplingSupplier,
                              Supplier<Block> petalLitterSupplier, boolean fruiting) {
        super(Material.LEAVES);
        this.fruitSupplier = fruitSupplier;
        this.saplingSupplier = saplingSupplier;
        this.petalLitterSupplier = petalLitterSupplier;
        this.fruiting = fruiting;
        setHardness(0.2F);
        setLightOpacity(1);
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
        setDefaultState(blockState.getBaseState().withProperty(AGE, 0).withProperty(STERILE, true));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, net.minecraft.util.EnumFacing side) {
        return true;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, STERILE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(AGE, meta & 3)
            .withProperty(STERILE, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE) | (state.getValue(STERILE) ? 4 : 0);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(AGE, 0).withProperty(STERILE, true);
    }

    public IBlockState getGeneratedState(Random random, boolean fruitCapable) {
        return getDefaultState()
            .withProperty(AGE, 0)
            .withProperty(STERILE, !fruiting || !fruitCapable || random.nextBoolean());
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (worldIn.isRemote) {
            return;
        }

        if (!hasSupportingLog(worldIn, pos)) {
            if (rand.nextInt(8) == 0) {
                dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return;
        }

        if (fruiting && !state.getValue(STERILE) && state.getValue(AGE) < MAX_AGE
            && worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(5) == 0) {
            if (state.getValue(AGE) == 1) {
                tryPlacePetalLitter(worldIn, pos);
            }
            worldIn.setBlockState(pos, state.withProperty(AGE, state.getValue(AGE) + 1), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(AGE) < MAX_AGE) {
            return false;
        }

        if (!fruiting) {
            return false;
        }

        if (!worldIn.isRemote) {
            Item fruit = fruitSupplier.get();
            if (fruit != null) {
                ItemStack stack = new ItemStack(fruit);
                if (!playerIn.inventory.addItemStackToInventory(stack)) {
                    playerIn.dropItem(stack, false);
                }
            }
            worldIn.setBlockState(pos, state.withProperty(AGE, 0), 2);
        }
        return true;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random random = world instanceof World ? ((World) world).rand : new Random();
        Item fruit = fruitSupplier.get();
        Item sapling = saplingSupplier.get();

        if (fruiting && fruit != null && state.getValue(AGE) >= MAX_AGE) {
            drops.add(new ItemStack(fruit));
        }

        int saplingChance = Math.max(1, 18 - fortune * 2);
        if (sapling != null && random.nextInt(saplingChance) == 0) {
            drops.add(new ItemStack(sapling));
        }
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    private boolean hasSupportingLog(World world, BlockPos origin) {
        int radius = 6;
        if (!world.isAreaLoaded(origin, radius)) {
            return true;
        }

        for (BlockPos check : BlockPos.getAllInBoxMutable(origin.add(-radius, -radius, -radius), origin.add(radius, radius, radius))) {
            int distance = Math.abs(check.getX() - origin.getX())
                + Math.abs(check.getY() - origin.getY())
                + Math.abs(check.getZ() - origin.getZ());
            if (distance <= radius && world.getBlockState(check).getBlock() instanceof BlockLog) {
                return true;
            }
        }
        return false;
    }

    private void tryPlacePetalLitter(World world, BlockPos pos) {
        if (petalLitterSupplier == null) {
            return;
        }
        Block litter = petalLitterSupplier.get();
        if (litter == null) {
            return;
        }
        BlockPos target = searchBelow(world, pos, 5);
        if (target != null && world.isAirBlock(target) && litter.canPlaceBlockAt(world, target)) {
            world.setBlockState(target, litter.getDefaultState(), 2);
        }
    }

    private BlockPos searchBelow(World world, BlockPos pos, int limit) {
        for (int i = 2; i < limit + 2; i++) {
            BlockPos support = pos.down(i);
            IBlockState supportState = world.getBlockState(support);
            if (supportState.isSideSolid(world, support, EnumFacing.UP) && world.isAirBlock(support.up())) {
                return support.up();
            }
        }
        return null;
    }
}
