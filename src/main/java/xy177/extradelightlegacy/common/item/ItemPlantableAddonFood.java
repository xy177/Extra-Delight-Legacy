package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import xy177.extradelightlegacy.common.block.CropSoilHelper;

import java.util.function.Supplier;

public class ItemPlantableAddonFood extends AddonFoodItem {
    private final Supplier<Block> cropSupplier;

    public ItemPlantableAddonFood(int healAmount, float saturation, Supplier<Block> cropSupplier) {
        super(healAmount, saturation, false);
        this.cropSupplier = cropSupplier;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        Block crop = cropSupplier.get();
        BlockPos plantPos = pos.up();
        IBlockState soil = world.getBlockState(pos);

        if (facing != EnumFacing.UP || crop == null || !player.canPlayerEdit(plantPos, facing, stack)) {
            return EnumActionResult.PASS;
        }

        if (crop instanceof IPlantable
            && CropSoilHelper.canSustainCrop(soil, world, pos, (IPlantable) crop)
            && world.isAirBlock(plantPos)) {
            world.setBlockState(plantPos, crop.getDefaultState());
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }
}
