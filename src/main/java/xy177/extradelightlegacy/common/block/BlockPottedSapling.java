package xy177.extradelightlegacy.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BlockPottedSapling extends BlockBush {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
    private final Supplier<Item> saplingSupplier;

    public BlockPottedSapling(Supplier<Item> saplingSupplier) {
        this.saplingSupplier = saplingSupplier;
        setHardness(0.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.FLOWER_POT;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(Blocks.FLOWER_POT));
        Item sapling = saplingSupplier.get();
        if (sapling != null) {
            drops.add(new ItemStack(sapling));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            getDropsForPickup(playerIn, worldIn, pos, state);
            worldIn.setBlockToAir(pos);
        }
        return true;
    }

    private void getDropsForPickup(EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        NonNullList<ItemStack> drops = NonNullList.create();
        getDrops(drops, world, pos, state, 0);
        for (ItemStack drop : drops) {
            if (!player.inventory.addItemStackToInventory(drop)) {
                player.dropItem(drop, false);
            }
        }
    }
}
