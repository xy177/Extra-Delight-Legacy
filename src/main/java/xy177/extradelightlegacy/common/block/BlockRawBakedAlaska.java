package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockRawBakedAlaska extends Block {
    public static final PropertyBool ON_FIRE = PropertyBool.create("on_fire");
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

    private final Supplier<Block> bakedBlock;

    public BlockRawBakedAlaska(Supplier<Block> bakedBlock) {
        super(Material.CLOTH);
        this.bakedBlock = bakedBlock;
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.CLOTH);
        setTickRandomly(true);
        setDefaultState(blockState.getBaseState().withProperty(ON_FIRE, Boolean.FALSE));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (state.getValue(ON_FIRE) || held.getItem() != Items.FLINT_AND_STEEL) {
            return false;
        }

        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                held.damageItem(1, player);
            }
            world.setBlockState(pos, getDefaultState().withProperty(ON_FIRE, Boolean.TRUE), 2);
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
        }
        return true;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote && state.getValue(ON_FIRE)) {
            Block target = bakedBlock.get();
            if (target != null) {
                world.setBlockState(pos, target.getDefaultState(), 3);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
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
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ON_FIRE) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ON_FIRE, meta != 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ON_FIRE);
    }
}
