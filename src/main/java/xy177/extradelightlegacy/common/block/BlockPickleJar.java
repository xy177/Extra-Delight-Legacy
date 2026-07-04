package xy177.extradelightlegacy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.common.tile.TileEntityPickleJarDisplay;
import xy177.extradelightlegacy.common.registry.EDLItems;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.tile.TileEntityFeastServings;

import java.util.function.Supplier;

public class BlockPickleJar extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger SERVINGS = PropertyInteger.create("servings", 0, 4);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.75D, 0.75D);
    private final Supplier<Item> servingSupplier;

    public BlockPickleJar(Supplier<Item> servingSupplier) {
        super(Material.GLASS);
        this.servingSupplier = servingSupplier;
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.GLASS);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, 4));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFeastServings(4);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        int currentServings = getServings(world, pos, 4);
        if (currentServings <= 0) {
            if (!world.isRemote) {
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                world.destroyBlock(pos, true);
            }
            return true;
        }
        if (BlockPickleJarDisplay.isPickleJarItem(held)) {
            if (!world.isRemote) {
                convertToDisplay(world, pos, state, currentServings, held, player);
            }
            return true;
        }
        if (!isSpoon(held)) {
            return false;
        }
        Item item = servingSupplier.get();
        if (item == null) {
            return false;
        }
        if (!world.isRemote) {
            ItemStack serving = new ItemStack(item);
            if (!player.inventory.addItemStackToInventory(serving)) {
                player.dropItem(serving, false);
            }
            if (!player.capabilities.isCreativeMode) {
                if (held.isItemStackDamageable()) {
                    held.damageItem(1, player);
                } else {
                    held.shrink(1);
                }
            }
            setServings(world, pos, currentServings - 1);
            world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    private static boolean isSpoon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if ("toolSpoon".equals(name) || "spoon".equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer,
                                            EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal).withProperty(SERVINGS, 4);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        setServings(worldIn, pos, stack.hasTagCompound() ? stack.getTagCompound().getInteger("Servings") : 4);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return canStay(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canStay(worldIn, pos)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
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
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(SERVINGS, 4);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SERVINGS, getServings(worldIn, pos, 4));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SERVINGS);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int servings = getServings(world, pos, 4);
        if (servings <= 0) {
            ItemStack juice = EDLItems.PICKLE_JUICE.stack(1);
            if (!juice.isEmpty()) {
                drops.add(juice);
            }
            return;
        }

        ItemStack stack = new ItemStack(this);
        if (servings != 4) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("Servings", servings);
            stack.setTagCompound(tag);
        }
        drops.add(stack);
    }

    public static boolean tryTakeServingFromJarStack(World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                                     ItemStack held, ItemStack jarStack) {
        if (!isSpoon(held) || !(jarStack.getItem() instanceof ItemBlock)) {
            return false;
        }
        Block block = ((ItemBlock) jarStack.getItem()).getBlock();
        if (!(block instanceof BlockPickleJar)) {
            return false;
        }
        int servings = getServings(jarStack);
        if (servings <= 0) {
            return false;
        }
        Item resultItem = ((BlockPickleJar) block).servingSupplier.get();
        if (resultItem == null) {
            return false;
        }
        if (!world.isRemote) {
            ItemStack result = new ItemStack(resultItem);
            if (!player.inventory.addItemStackToInventory(result)) {
                player.dropItem(result, false);
            }
            if (!player.capabilities.isCreativeMode) {
                if (held.isItemStackDamageable()) {
                    held.damageItem(1, player);
                } else {
                    held.shrink(1);
                }
            }
            setServings(jarStack, servings - 1);
            world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    public static ItemStack getDropForJarStack(ItemStack jarStack) {
        if (getServings(jarStack) <= 0) {
            return EDLItems.PICKLE_JUICE.stack(1);
        }
        return jarStack.copy();
    }

    private void convertToDisplay(World world, BlockPos pos, IBlockState state, int currentServings,
                                  ItemStack held, EntityPlayer player) {
        Block displayBlock = BlockPickleJarDisplay.getDisplayBlock();
        if (!(displayBlock instanceof BlockPickleJarDisplay)) {
            return;
        }
        ItemStack first = new ItemStack(this);
        if (currentServings != 4) {
            setServings(first, currentServings);
        }
        ItemStack second = held.copy();
        second.setCount(1);

        EnumFacing facing = state.getValue(FACING);
        world.setBlockState(pos, displayBlock.getDefaultState().withProperty(BlockPickleJarDisplay.FACING, facing), 3);
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPickleJarDisplay) {
            TileEntityPickleJarDisplay display = (TileEntityPickleJarDisplay) tile;
            display.addJar(first);
            display.addJar(second);
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.8F, 1.0F);
        } else {
            world.setBlockState(pos, state, 3);
            setServings(world, pos, currentServings);
        }
    }

    public static int getServings(ItemStack stack) {
        if (!stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("Servings", 3)) {
            return Math.max(0, Math.min(4, stack.getTagCompound().getInteger("Servings")));
        }
        return 4;
    }

    private static void setServings(ItemStack stack, int servings) {
        int clamped = Math.max(0, Math.min(4, servings));
        NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        tag.setInteger("Servings", clamped);
        stack.setTagCompound(tag);
    }

    private static int getServings(IBlockAccess world, BlockPos pos, int fallback) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            return Math.max(0, Math.min(4, ((TileEntityFeastServings) tile).getServings()));
        }
        return fallback;
    }

    private static void setServings(World world, BlockPos pos, int servings) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            ((TileEntityFeastServings) tile).setServings(Math.max(0, Math.min(4, servings)));
        }
    }

    public static boolean canJarStay(World world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getBlockState(below).isSideSolid(world, below, EnumFacing.UP);
    }

    private static boolean canStay(World world, BlockPos pos) {
        return canJarStay(world, pos);
    }
}
