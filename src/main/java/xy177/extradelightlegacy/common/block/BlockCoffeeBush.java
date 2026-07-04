package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCoffeeBush extends BlockBush implements IGrowable {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
    private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(3.0D / 16.0D, 0.0D, 3.0D / 16.0D, 13.0D / 16.0D, 8.0D / 16.0D, 13.0D / 16.0D);
    private static final AxisAlignedBB MID_AABB = new AxisAlignedBB(1.0D / 16.0D, 0.0D, 1.0D / 16.0D, 15.0D / 16.0D, 1.0D, 15.0D / 16.0D);
    private final Supplier<Item> fruitSupplier;

    public BlockCoffeeBush(Supplier<Item> fruitSupplier) {
        this.fruitSupplier = fruitSupplier;
        setDefaultState(blockState.getBaseState().withProperty(AGE, 0));
        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
        disableStats();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int age = state.getValue(AGE);
        if (age == 0) {
            return SAPLING_AABB;
        }
        return age < 3 ? MID_AABB : FULL_BLOCK_AABB;
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
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        int age = state.getValue(AGE);
        if (!worldIn.isRemote && age < 3 && worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(5) == 0) {
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        int age = state.getValue(AGE);
        ItemStack held = playerIn.getHeldItem(hand);
        if (age < 3 && !held.isEmpty() && held.getItem() == Items.DYE && held.getMetadata() == 15) {
            return false;
        }
        if (age < 3) {
            return false;
        }

        Item fruit = fruitSupplier.get();
        if (fruit == null) {
            return false;
        }

        int count = 2 + worldIn.rand.nextInt(2);
        spawnAsEntity(worldIn, pos, new ItemStack(fruit, count));
        worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_STEP, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
        worldIn.setBlockState(pos, state.withProperty(AGE, 1), 3);
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item fruit = fruitSupplier.get();
        return fruit == null ? Item.getItemFromBlock(this) : fruit;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int age = state.getValue(AGE);
        if (age >= 3) {
            return 2 + random.nextInt(2) + Math.max(0, fortune);
        }
        return 1;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        Item fruit = fruitSupplier.get();
        return fruit == null ? ItemStack.EMPTY : new ItemStack(fruit);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        int age = Math.min(3, state.getValue(AGE) + 1);
        worldIn.setBlockState(pos, state.withProperty(AGE, age), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(AGE, Math.max(0, Math.min(3, meta)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }
}
