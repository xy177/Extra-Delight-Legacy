package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityJar;

public class BlockJar extends Block implements ITileEntityProvider {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(5.0D / 16.0D, 0.0D, 5.0D / 16.0D, 11.0D / 16.0D, 9.0D / 16.0D, 11.0D / 16.0D);

    public BlockJar() {
        super(Material.GLASS);
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.GLASS);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityJar();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityJar)) {
            return false;
        }
        ItemStack held = player.getHeldItem(hand);
        if (held.isEmpty()) {
            return false;
        }
        if (!world.isRemote) {
            return ((TileEntityJar) tile).handleFluidContainer(player, held);
        }
        return ((TileEntityJar) tile).canHandleFluidContainer(held);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                                net.minecraft.entity.EntityLivingBase placer, ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityJar) {
            ((TileEntityJar) tile).readFluidFromStack(stack);
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack stack = new ItemStack(this);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityJar) {
            ((TileEntityJar) tile).writeFluidToStack(stack);
        }
        drops.add(stack);
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
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
