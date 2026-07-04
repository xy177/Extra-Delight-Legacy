package xy177.extradelightlegacy.common.item;

import com.wdcftgg.farmersdelightlegacy.api.food.AddonFoodItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class ItemFrosting extends AddonFoodItem {
    private static final String[] COLORS = {
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
        "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };

    private final String color;

    public ItemFrosting(String color, int healAmount, float saturation, net.minecraft.util.ResourceLocation effect,
                        int duration, int amplifier, float chance) {
        super(healAmount, saturation, false, effect, duration, amplifier, chance);
        this.color = color;
        setAlwaysEdible();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block source = EDLBlocks.GINGERBREAD_COOKIE_BLOCK.getBlock();
        Block target = getTargetBlock();
        if (source == null || target == null || world.getBlockState(pos).getBlock() != source) {
            return EnumActionResult.PASS;
        }

        if (!world.isRemote) {
            world.setBlockState(pos, target.getDefaultState(), 3);
            if (!player.capabilities.isCreativeMode) {
                ItemStack held = player.getHeldItem(hand);
                held.shrink(1);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return EnumActionResult.SUCCESS;
    }

    private Block getTargetBlock() {
        int index = colorIndex(color);
        if (index < 0 || index >= EDLBlocks.FROSTED_GINGERBREAD_BLOCKS.size()) {
            return null;
        }
        return EDLBlocks.FROSTED_GINGERBREAD_BLOCKS.get(index).getBlock();
    }

    private static int colorIndex(String color) {
        for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i].equals(color)) {
                return i;
            }
        }
        return -1;
    }
}
