package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ItemShuckableOutput extends Item {
    private final Supplier<ItemStack[]> outputSupplier;

    public ItemShuckableOutput(Supplier<ItemStack[]> outputSupplier) {
        this.outputSupplier = outputSupplier;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
            }

            for (ItemStack output : outputSupplier.get()) {
                give(player, output);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, held);
    }

    private static void give(EntityPlayer player, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        if (!player.addItemStackToInventory(stack)) {
            player.dropItem(stack, false);
        }
    }
}
