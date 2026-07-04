package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.IStyleableBlock;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;

import java.util.List;

public class ItemOffsetSpatula extends ItemDamageableTool {
    public ItemOffsetSpatula(int durability) {
        super(durability);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (!(block instanceof IStyleableBlock)) {
            return EnumActionResult.PASS;
        }

        if (!world.isRemote) {
            IStyleableBlock styleable = (IStyleableBlock) block;
            if (player.isSneaking()) {
                player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.STYLEABLE, world, pos.getX(), pos.getY(), pos.getZ());
            } else {
                styleable.setNextStyle(world, pos, state);
                ItemStack tool = player.getHeldItem(hand);
                if (!player.capabilities.isCreativeMode && tool.isItemStackDamageable()) {
                    tool.damageItem(1, player);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_SLIME_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                world.spawnParticle(
                    EnumParticleTypes.BLOCK_CRACK,
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D,
                    0.0D,
                    0.0D,
                    0.0D,
                    Block.getStateId(net.minecraft.init.Blocks.SNOW.getDefaultState())
                );
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.AQUA + I18n.translateToLocal("extradelightlegacy.tooltip.spatula"));
    }
}
