package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class ItemJarBlock extends ItemBlock {
    public ItemJarBlock(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Fluid", 10)) {
            return;
        }
        FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Fluid"));
        if (fluid == null || fluid.amount <= 0 || fluid.getFluid() == null) {
            return;
        }
        tooltip.add(TextFormatting.WHITE + I18n.translateToLocalFormatted(
            "extradelightlegacy.jar.contains",
            fluid.amount,
            fluid.getLocalizedName()
        ));
    }
}
