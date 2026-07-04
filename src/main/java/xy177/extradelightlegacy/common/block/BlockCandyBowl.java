package xy177.extradelightlegacy.common.block;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityCandyBowl;

public class BlockCandyBowl extends BlockDisplayBowl {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(4.0D / 16.0D, 0.0D, 4.0D / 16.0D, 12.0D / 16.0D, 5.0D / 16.0D, 12.0D / 16.0D);

    public BlockCandyBowl() {
        super(BOX);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCandyBowl();
    }

    @Override
    protected boolean isValidItem(ItemStack stack) {
        return hasExactOre(stack, "candyBowlValid");
    }
}
