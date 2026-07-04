package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.BlockRecipeFeast;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFeastBlock extends ItemBlock {
    public ItemFeastBlock(Block block) {
        super(block);
        addPropertyOverride(new ResourceLocation(ExtraDelightLegacy.MODID, "servings"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return BlockRecipeFeast.getServings(stack);
            }
        });
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.BLUE + I18n.translateToLocal(ExtraDelightLegacy.MODID + ".tooltip.feast"));
    }
}
