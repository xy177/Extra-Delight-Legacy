package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityBubblePot;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class BlockBubblePot extends Block implements ITileEntityProvider {
    public static final PropertyBool READY = PropertyBool.create("ready");
    private static final AxisAlignedBB POT_BOX = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

    private final Supplier<ItemStack> outputSupplier;
    private final Supplier<ItemStack[]> remainderSupplier;
    private final int speed;

    public BlockBubblePot(Supplier<ItemStack> outputSupplier, int speed) {
        this(outputSupplier, speed, () -> new ItemStack[0]);
    }

    public BlockBubblePot(Supplier<ItemStack> outputSupplier, int speed, Supplier<ItemStack[]> remainderSupplier) {
        super(Material.ROCK);
        this.outputSupplier = outputSupplier;
        this.remainderSupplier = remainderSupplier;
        this.speed = Math.max(1, speed);
        setHardness(0.5F);
        setResistance(1.0F);
        setSoundType(SoundType.STONE);
        setLightOpacity(0);
        setTickRandomly(true);
        setDefaultState(blockState.getBaseState().withProperty(READY, Boolean.FALSE));
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBubblePot();
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (!state.getValue(READY) && random.nextInt(speed) == 0) {
            world.setBlockState(pos, state.withProperty(READY, Boolean.TRUE), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!state.getValue(READY)) {
            return false;
        }

        ItemStack held = player.getHeldItem(hand);
        if (held.isEmpty() || held.getItem() != Items.GLASS_BOTTLE) {
            return false;
        }

        if (!world.isRemote) {
            ItemStack output = outputSupplier.get();
            if (!output.isEmpty()) {
                held.shrink(1);
                if (!player.inventory.addItemStackToInventory(output.copy())) {
                    player.dropItem(output.copy(), false);
                }
                for (ItemStack remainder : remainderSupplier.get()) {
                    if (!remainder.isEmpty()) {
                        spawnAsEntity(world, pos, remainder.copy());
                    }
                }
                world.setBlockToAir(pos);
            }
        }
        return true;
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (!state.getValue(READY)) {
            return;
        }

        double x = pos.getX() + 0.25D + random.nextDouble() * 0.5D;
        double y = pos.getY() + 0.4D;
        double z = pos.getZ() + 0.25D + random.nextDouble() * 0.5D;
        world.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, x, y, z, 0.0D, 0.02D, 0.0D);
        if (random.nextInt(6) == 0) {
            world.playSound(x, y, z, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.AMBIENT, 0.08F, 1.4F, false);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return POT_BOX;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return POT_BOX;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(READY, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(READY) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, READY);
    }
}
