package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import xy177.extradelightlegacy.common.block.CropSoilHelper;

import java.util.function.Supplier;

public class ItemPlantableBlock extends Item {
    public enum SoilMode {
        CROP,
        GROUND
    }

    private final Supplier<Block> blockSupplier;
    private final SoilMode soilMode;

    public ItemPlantableBlock(Supplier<Block> blockSupplier, SoilMode soilMode) {
        this.blockSupplier = blockSupplier;
        this.soilMode = soilMode;
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

        if (canPlant(soil, world, pos, block) && world.isAirBlock(plantPos)) {
            world.setBlockState(plantPos, block.getDefaultState());
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    private boolean canPlant(IBlockState soil, World world, BlockPos soilPos, Block block) {
        if (soilMode == SoilMode.GROUND) {
            return CropSoilHelper.isGroundPlantSoil(soil);
        }

        return block instanceof IPlantable
            && CropSoilHelper.canSustainCrop(soil, world, soilPos, (IPlantable) block);
    }
}
