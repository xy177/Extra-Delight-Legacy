package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import com.wdcftgg.farmersdelightlegacy.common.item.ItemFoodTooltip.FoodEffectEntry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.block.CropSoilHelper;

import java.util.List;
import java.util.function.Supplier;

public class ItemGroundPlantableFood extends AddonFoodItem {
    private final Supplier<Block> blockSupplier;

    public ItemGroundPlantableFood(int healAmount, float saturation, Supplier<Block> blockSupplier) {
        super(healAmount, saturation, false);
        this.blockSupplier = blockSupplier;
    }

    public ItemGroundPlantableFood(int healAmount, float saturation, Supplier<Block> blockSupplier, List<FoodEffectEntry> effects) {
        super(healAmount, saturation, false, effects);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        Block block = blockSupplier.get();
        BlockPos plantPos = pos.up();
        IBlockState soil = world.getBlockState(pos);
        if (facing != EnumFacing.UP || block == null || !player.canPlayerEdit(plantPos, facing, stack)) {
            return EnumActionResult.PASS;
        }

        if (CropSoilHelper.isGroundPlantSoil(soil) && world.isAirBlock(plantPos)) {
            world.setBlockState(plantPos, block.getDefaultState());
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
