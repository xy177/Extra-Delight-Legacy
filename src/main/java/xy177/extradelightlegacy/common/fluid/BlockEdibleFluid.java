package xy177.extradelightlegacy.common.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import xy177.extradelightlegacy.common.registry.EDLFluids;

public class BlockEdibleFluid extends BlockFluidClassic {
    public BlockEdibleFluid(Fluid fluid) {
        super(fluid, Material.WATER);
        setQuantaPerBlock(8);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (held.isEmpty() || held.getItem() != Items.BUCKET || state.getValue(BlockFluidBase.LEVEL) != 0) {
            return false;
        }

        EDLFluids.FluidDefinition definition = EDLFluids.find(getFluid());
        if (definition == null || definition.getBucket() == null) {
            return false;
        }

        if (!world.isRemote) {
            world.setBlockToAir(pos);
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
                ItemStack filled = definition.bucketStack();
                if (!player.inventory.addItemStackToInventory(filled)) {
                    player.dropItem(filled, false);
                }
            }
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }
}
