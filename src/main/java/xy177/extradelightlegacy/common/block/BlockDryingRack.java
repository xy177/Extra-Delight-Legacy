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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityDryingRack;

public class BlockDryingRack extends Block implements ITileEntityProvider {
    public BlockDryingRack() {
        super(Material.WOOD);
        setHardness(2.5F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        setLightOpacity(0);
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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDryingRack();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityDryingRack)) {
            return false;
        }

        TileEntityDryingRack dryingRack = (TileEntityDryingRack) tile;
        if (world.isRemote) {
            return true;
        }

        boolean handled;
        if (player.isSneaking()) {
            handled = dryingRack.extractOne(player);
        } else {
            ItemStack held = player.getHeldItem(hand);
            handled = dryingRack.insertHeldItem(player, held);
        }

        return handled;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityDryingRack) {
            TileEntityDryingRack dryingRack = (TileEntityDryingRack) tile;
            for (ItemStack stack : dryingRack.getItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
            dryingRack.clear();
        }
        super.breakBlock(world, pos, state);
    }
}
