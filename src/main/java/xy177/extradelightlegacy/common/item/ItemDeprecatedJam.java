package xy177.extradelightlegacy.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDeprecatedJam extends ItemContainerFood {
    private final ItemDynamicJam.Flavor targetFlavor;

    public ItemDeprecatedJam(ItemDynamicJam.Flavor targetFlavor) {
        super(1, 0.1F, net.minecraft.init.Items.GLASS_BOTTLE);
        this.targetFlavor = targetFlavor;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, ItemDynamicJam.stack(targetFlavor, held.getCount()));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return ItemDynamicJam.stack(targetFlavor, Math.max(1, stack.getCount())).getDisplayName();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.RED + "已废弃，右键可转换为动态果酱。");
    }
}
