package xy177.extradelightlegacy.common.util;

import com.wdcftgg.farmersdelightlegacy.common.util.HeatSourceHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class EDLHeatSources {
    private EDLHeatSources() {
    }

    public static boolean isCookwareHeated(World world, BlockPos cookwarePos) {
        if (world == null || cookwarePos == null) {
            return false;
        }

        try {
            return HeatSourceHelper.isCookwareHeated(world, cookwarePos);
        } catch (Throwable ignored) {
            BlockPos below = cookwarePos.down();
            return world.getBlockState(below).getBlock() == Blocks.FIRE
                || world.getBlockState(below).getBlock() == Blocks.LAVA
                || world.getBlockState(below).getBlock() == Blocks.FLOWING_LAVA
                || world.getBlockState(below).getBlock() == Blocks.MAGMA
                || world.getBlockState(below).getBlock() == Blocks.LIT_FURNACE;
        }
    }
}
