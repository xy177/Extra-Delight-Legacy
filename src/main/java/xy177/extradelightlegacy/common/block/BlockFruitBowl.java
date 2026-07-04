package xy177.extradelightlegacy.common.block;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityFruitBowl;

public class BlockFruitBowl extends BlockDisplayBowl {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(2.0D / 16.0D, 0.0D, 2.0D / 16.0D, 14.0D / 16.0D, 5.0D / 16.0D, 14.0D / 16.0D);

    public BlockFruitBowl() {
        super(BOX);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFruitBowl();
    }

    @Override
    protected boolean isValidItem(ItemStack stack) {
        return hasOrePrefix(stack, "foodFruit", "fruitApple", "fruitLemon", "fruitLime", "fruitOrange", "fruitGrapefruit")
            || hasExactOre(stack, "fruit")
            || hasExactOre(stack, "cropApple")
            || hasExactOre(stack, "cropLemon")
            || hasExactOre(stack, "cropLime")
            || hasExactOre(stack, "cropOrange")
            || hasExactOre(stack, "cropGrapefruit");
    }

    @Override
    protected boolean isStyleableBowl() {
        return true;
    }
}
