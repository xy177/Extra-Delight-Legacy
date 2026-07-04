package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockTooltip extends ItemBlock {
    private final String tooltipKey;
    private final TextFormatting color;

    public ItemBlockTooltip(Block block, String tooltipKey, TextFormatting color) {
        super(block);
        this.tooltipKey = tooltipKey;
        this.color = color;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(color + I18n.translateToLocal(tooltipKey));
    }
}
