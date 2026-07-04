package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.tile.TileEntityChocolateBox;

public class BlockChocolateBox extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool OPEN = PropertyBool.create("open");
    private static final AxisAlignedBB BOX_EAST_WEST = new AxisAlignedBB(4.0D / 16.0D, 0.0D, 2.0D / 16.0D, 12.0D / 16.0D, 3.0D / 16.0D, 14.0D / 16.0D);
    private static final AxisAlignedBB BOX_NORTH_SOUTH = new AxisAlignedBB(2.0D / 16.0D, 0.0D, 4.0D / 16.0D, 14.0D / 16.0D, 3.0D / 16.0D, 12.0D / 16.0D);
    private static final String[] VALID_ORES = {"foodCandy", "chocolateBar", "chocolateBarFilled", "chocolateTruffle"};

    public BlockChocolateBox() {
        super(Material.CAKE);
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.CLOTH);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(OPEN, false));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChocolateBox();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                IBlockState updated = state.withProperty(OPEN, !state.getValue(OPEN));
                world.setBlockState(pos, updated, 3);
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.5F, updated.getValue(OPEN) ? 1.2F : 0.8F);
            }
            return true;
        }

        if (!state.getValue(OPEN)) {
            return false;
        }

        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityChocolateBox)) {
            return false;
        }

        TileEntityChocolateBox box = (TileEntityChocolateBox) tile;
        ItemStack held = player.getHeldItem(hand);
        if (isValidChocolateBoxItem(held)) {
            if (!world.isRemote && box.addItem(held)) {
                if (!player.capabilities.isCreativeMode) {
                    held.shrink(1);
                }
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.45F, 1.4F);
            }
            return true;
        }

        if (!world.isRemote) {
            ItemStack extracted = box.removeLastItem();
            if (!extracted.isEmpty() && !player.inventory.addItemStackToInventory(extracted)) {
                spawnAsEntity(world, pos, extracted);
            }
        }
        return true;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal).withProperty(OPEN, false);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (stack.isEmpty() || !stack.hasTagCompound()) {
            return;
        }
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null || !tag.hasKey("BlockEntityTag", 10)) {
            return;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityChocolateBox) {
            NBTTagCompound blockEntityTag = tag.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey(TileEntityChocolateBox.TAG_INVENTORY, 10)) {
                ((TileEntityChocolateBox) tile).readInventoryFromNBT(blockEntityTag.getCompoundTag(TileEntityChocolateBox.TAG_INVENTORY));
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        return facing == EnumFacing.EAST || facing == EnumFacing.WEST ? BOX_EAST_WEST : BOX_NORTH_SOUTH;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack drop = new ItemStack(this);
        TileEntity tile = world == null ? null : world.getTileEntity(pos);
        if (tile instanceof TileEntityChocolateBox && ((TileEntityChocolateBox) tile).getFilledSlots() > 0) {
            NBTTagCompound stackTag = new NBTTagCompound();
            NBTTagCompound blockEntityTag = new NBTTagCompound();
            blockEntityTag.setTag(TileEntityChocolateBox.TAG_INVENTORY, ((TileEntityChocolateBox) tile).writeInventoryToNBT());
            stackTag.setTag("BlockEntityTag", blockEntityTag);
            drop.setTagCompound(stackTag);
        }
        drops.add(drop);
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
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(OPEN)) {
            meta |= 4;
        }
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
            .withProperty(OPEN, (meta & 4) != 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, OPEN);
    }

    public static boolean isValidChocolateBoxItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int oreId : OreDictionary.getOreIDs(stack)) {
            String oreName = OreDictionary.getOreName(oreId);
            for (String validOre : VALID_ORES) {
                if (validOre.equals(oreName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
