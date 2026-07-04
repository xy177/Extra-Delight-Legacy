package xy177.extradelightlegacy.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntitySpiceRackDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityWallDisplay;
import xy177.extradelightlegacy.common.tile.TileEntityWreathDisplay;

public class BlockDisplayWallDecor extends BlockWallDecor implements ITileEntityProvider {
    private final DisplayKind displayKind;

    public BlockDisplayWallDecor(Material material, SoundType soundType, AxisAlignedBB northBox,
                                 AxisAlignedBB southBox, AxisAlignedBB eastBox, AxisAlignedBB westBox,
                                 boolean collidable, DisplayKind displayKind) {
        super(material, soundType, northBox, southBox, eastBox, westBox, collidable);
        this.displayKind = displayKind;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return displayKind == DisplayKind.SPICE_RACK
            ? new TileEntitySpiceRackDisplay()
            : new TileEntityWreathDisplay();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityWallDisplay)) {
            return false;
        }

        TileEntityWallDisplay display = (TileEntityWallDisplay) tile;
        ItemStack held = player.getHeldItem(hand);
        if (!held.isEmpty()) {
            if (!world.isRemote && display.addItem(held)) {
                if (!player.capabilities.isCreativeMode) {
                    held.shrink(1);
                }
                world.playSound(null, pos, net.minecraft.init.SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.45F, 1.2F);
            }
            return true;
        }

        if (!world.isRemote) {
            ItemStack extracted = display.removeLastItem();
            if (!extracted.isEmpty()) {
                if (!player.inventory.addItemStackToInventory(extracted)) {
                    spawnAsEntity(world, pos, extracted);
                }
                world.playSound(null, pos, net.minecraft.init.SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.45F, 0.8F);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityWallDisplay) {
            for (ItemStack stack : ((TileEntityWallDisplay) tile).copyItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(this));
    }

    public enum DisplayKind {
        SPICE_RACK,
        WREATH
    }
}
