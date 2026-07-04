package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.item.ItemOffsetSpatula;
import xy177.extradelightlegacy.common.tile.TileEntityDisplayBowl;

public abstract class BlockDisplayBowl extends Block implements ITileEntityProvider, IStyleableBlock {
    public static final PropertyInteger STYLE = PropertyInteger.create("style", 0, 49);
    private final AxisAlignedBB box;

    protected BlockDisplayBowl(AxisAlignedBB box) {
        super(Material.WOOD);
        this.box = box;
        setHardness(0.6F);
        setResistance(1.0F);
        setSoundType(SoundType.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(STYLE, 0));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityDisplayBowl)) {
            return false;
        }

        TileEntityDisplayBowl bowl = (TileEntityDisplayBowl) tile;
        ItemStack held = player.getHeldItem(hand);
        if (isStyleableBowl() && !held.isEmpty() && held.getItem() instanceof ItemOffsetSpatula) {
            if (!world.isRemote) {
                if (player.isSneaking()) {
                    player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.STYLEABLE, world, pos.getX(), pos.getY(), pos.getZ());
                } else {
                    setNextStyle(world, pos, state);
                    if (!player.capabilities.isCreativeMode && held.isItemStackDamageable()) {
                        held.damageItem(1, player);
                    }
                    world.playSound(null, pos, net.minecraft.init.SoundEvents.BLOCK_SLIME_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return true;
        }

        if (isValidItem(held)) {
            if (!world.isRemote && bowl.addItem(held)) {
                if (!player.capabilities.isCreativeMode) {
                    held.shrink(1);
                }
                playClick(world, pos, 1.2F);
            }
            return true;
        }

        if (held.isEmpty()) {
            if (!world.isRemote) {
                ItemStack extracted = bowl.removeLastItem();
                if (!extracted.isEmpty() && !player.inventory.addItemStackToInventory(extracted)) {
                    spawnAsEntity(world, pos, extracted);
                }
                if (!extracted.isEmpty()) {
                    playClick(world, pos, 0.8F);
                }
            }
            return true;
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityDisplayBowl) {
            for (ItemStack stack : ((TileEntityDisplayBowl) tile).copyItems()) {
                if (!stack.isEmpty()) {
                    spawnAsEntity(world, pos, stack);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (isStyleableBowl()) {
            StyleableBlockHelper.addDropWithTileStyle(drops, this, world, pos);
        } else {
            drops.add(new ItemStack(this));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return box;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(STYLE, isStyleableBowl() ? StyleableBlockHelper.getTileStyle(worldIn, pos, 0) : 0);
    }

    @Override
    public int getStyleCount() {
        return isStyleableBowl() ? 50 : 1;
    }

    @Override
    public int getCurrentStyle(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    public IBlockState withStyle(IBlockState state, int style) {
        return state.withProperty(STYLE, Math.max(0, Math.min(49, style)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STYLE);
    }

    protected boolean hasOrePrefix(ItemStack stack, String... prefixes) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int oreId : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(oreId);
            for (String prefix : prefixes) {
                if (name.equals(prefix) || name.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean hasExactOre(ItemStack stack, String ore) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int oreId : OreDictionary.getOreIDs(stack)) {
            if (ore.equals(OreDictionary.getOreName(oreId))) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean isValidItem(ItemStack stack);

    protected boolean isStyleableBowl() {
        return false;
    }

    private void playClick(World world, BlockPos pos, float pitch) {
        SoundEvent sound = net.minecraft.init.SoundEvents.ENTITY_ITEM_PICKUP;
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.45F, pitch);
    }
}
