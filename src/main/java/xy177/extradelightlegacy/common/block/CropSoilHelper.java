package xy177.extradelightlegacy.common.block;

import com.wdcftgg.farmersdelightlegacy.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;

public final class CropSoilHelper {
    private CropSoilHelper() {
    }

    public static boolean isKnownCropSoil(IBlockState soil) {
        Block block = soil.getBlock();
        return block == Blocks.FARMLAND || block == ModBlocks.RICH_SOIL_FARMLAND;
    }

    public static boolean isGroundPlantSoil(IBlockState soil) {
        Block block = soil.getBlock();
        return block == Blocks.GRASS
            || block == Blocks.DIRT
            || block == Blocks.FARMLAND
            || block == ModBlocks.RICH_SOIL
            || block == ModBlocks.RICH_SOIL_FARMLAND;
    }

    public static boolean canSustainCrop(IBlockState soil, IBlockAccess world, BlockPos soilPos, IPlantable plant) {
        Block block = soil.getBlock();
        return isKnownCropSoil(soil)
            || block.canSustainPlant(soil, world, soilPos, EnumFacing.UP, plant);
    }
}
