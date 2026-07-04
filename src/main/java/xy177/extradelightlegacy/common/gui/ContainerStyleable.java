package xy177.extradelightlegacy.common.gui;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import xy177.extradelightlegacy.common.block.IStyleableBlock;

public class ContainerStyleable extends Container {
    private final World world;
    private final BlockPos pos;

    public ContainerStyleable(InventoryPlayer playerInventory, World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public IBlockState getCurrentState() {
        return world.getBlockState(pos);
    }

    public IBlockState getActualState() {
        IBlockState state = getCurrentState();
        return state.getActualState((IBlockAccess) world, pos);
    }

    public IStyleableBlock getStyleable() {
        Block block = getCurrentState().getBlock();
        return block instanceof IStyleableBlock ? (IStyleableBlock) block : null;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getStyleCount() {
        IStyleableBlock styleable = getStyleable();
        return styleable == null ? 0 : styleable.getStyleCount(world, pos, getCurrentState());
    }

    public int getCurrentStyle() {
        IStyleableBlock styleable = getStyleable();
        return styleable == null ? 0 : styleable.getCurrentStyle(world, pos, getCurrentState());
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int id) {
        IBlockState state = getCurrentState();
        if (!(state.getBlock() instanceof IStyleableBlock)) {
            return false;
        }

        IStyleableBlock styleable = (IStyleableBlock) state.getBlock();
        if (id == 0) {
            styleable.setNextStyle(world, pos, state);
        } else if (id == 1) {
            styleable.setPrevStyle(world, pos, state);
        } else {
            int style = id - 2;
            if (style < 0 || style >= styleable.getStyleCount(world, pos, state)) {
                return false;
            }
            styleable.setStyle(world, pos, state, style);
        }
        return true;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return getStyleable() != null
            && playerIn.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
}
