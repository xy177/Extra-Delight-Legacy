package xy177.extradelightlegacy.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.block.BlockPickleJar;

import javax.annotation.Nullable;

public class ItemPickleJarBlock extends ItemBlock {
    public ItemPickleJarBlock(Block block) {
        super(block);
        addPropertyOverride(new ResourceLocation(ExtraDelightLegacy.MODID, "servings"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return BlockPickleJar.getServings(stack);
            }
        });
    }
}
