package xy177.extradelightlegacy.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCornCobPipe extends Item {
    public ItemCornCobPipe() {
        setMaxStackSize(1);
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, net.minecraft.util.math.BlockPos pos,
                                           net.minecraft.util.EnumFacing side, float hitX, float hitY, float hitZ,
                                           EnumHand hand) {
        return EnumActionResult.PASS;
    }

    @Override
    public net.minecraft.util.ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);
        ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (!helmet.isEmpty()) {
            return new net.minecraft.util.ActionResult<>(EnumActionResult.PASS, held);
        }

        player.setItemStackToSlot(EntityEquipmentSlot.HEAD, held.copy());
        held.setCount(0);
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            player.getSoundCategory(), 1.0F, 1.0F);
        return new net.minecraft.util.ActionResult<>(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote || itemSlot != 3 || !(entity instanceof EntityPlayer) || world.rand.nextInt(5) != 0) {
            return;
        }

        double yaw = Math.toRadians(entity.rotationYaw + 30.0F);
        double x = entity.posX - Math.sin(yaw) * 0.5D;
        double z = entity.posZ + Math.cos(yaw) * 0.5D;
        world.spawnParticle(net.minecraft.util.EnumParticleTypes.SPELL, x, entity.posY + 1.7D, z, 0.0D, 0.0D, 0.0D);
    }
}
