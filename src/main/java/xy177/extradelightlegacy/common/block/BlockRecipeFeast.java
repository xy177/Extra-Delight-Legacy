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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.crafting.FeastServingRegistry;
import xy177.extradelightlegacy.common.tile.TileEntityFeastServings;

public class BlockRecipeFeast extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger SERVINGS = PropertyInteger.create("servings", 0, 7);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.625D, 0.9375D);

    private final boolean hasLeftovers;
    private final int maxServings;

    public BlockRecipeFeast(boolean hasLeftovers) {
        this(hasLeftovers, 4);
    }

    public BlockRecipeFeast(boolean hasLeftovers, int maxServings) {
        super(Material.CLOTH);
        this.hasLeftovers = hasLeftovers;
        this.maxServings = Math.max(1, Math.min(7, maxServings));
        setHardness(0.8F);
        setResistance(1.0F);
        setSoundType(SoundType.CLOTH);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, this.maxServings));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFeastServings(maxServings);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        int servings = getServings(world, pos, maxServings);
        if (servings <= 0) {
            if (!world.isRemote) {
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                ItemStack leftover = FeastServingRegistry.findLeftover(this);
                if (!leftover.isEmpty()) {
                    spawnAsEntity(world, pos, leftover);
                }
                world.setBlockToAir(pos);
            }
            return true;
        }

        FeastServingRegistry.FeastServingRecipe recipe = FeastServingRegistry.findRecipe(this, held);
        ItemStack result = recipe == null ? ItemStack.EMPTY : recipe.getResult();
        if (result.isEmpty()) {
            if (!world.isRemote) {
                ItemStack required = FeastServingRegistry.findRequiredContainer(this);
                if (required.isEmpty()) {
                    player.sendStatusMessage(new TextComponentTranslation(
                        ExtraDelightLegacy.MODID + ".block.recipefeast.use_container"
                    ), true);
                } else {
                    player.sendStatusMessage(new TextComponentTranslation(
                        ExtraDelightLegacy.MODID + ".block.recipefeast.use_container.specific",
                        required.getDisplayName()
                    ), true);
                }
            }
            return true;
        }

        if (!world.isRemote) {
            if (recipe.consumesContainer() && !player.capabilities.isCreativeMode) {
                if (held.isItemStackDamageable()) {
                    held.damageItem(1, player);
                } else {
                    held.shrink(1);
                }
            }
            if (!player.inventory.addItemStackToInventory(result)) {
                player.dropItem(result, false);
            }
            int newServings = servings - 1;
            setServings(world, pos, newServings);
            if (newServings <= 0 && !hasLeftovers) {
                world.setBlockToAir(pos);
            }
            world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing horizontal = placer == null ? EnumFacing.NORTH : placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, horizontal).withProperty(SERVINGS, maxServings);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        setServings(worldIn, pos, getServings(stack));
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
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(SERVINGS, maxServings);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SERVINGS, getServings(worldIn, pos, maxServings));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SERVINGS);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int servings = getServings(world, pos, maxServings);
        if (servings <= 0) {
            ItemStack leftover = FeastServingRegistry.findLeftover(this);
            if (!leftover.isEmpty()) {
                drops.add(leftover);
            }
            return;
        }
        ItemStack stack = new ItemStack(this);
        if (servings != maxServings) {
            setServings(stack, servings);
        }
        drops.add(stack);
    }

    public static int getServings(ItemStack stack) {
        int fallback = 4;
        if (!stack.isEmpty() && stack.getItem() instanceof net.minecraft.item.ItemBlock) {
            Block block = ((net.minecraft.item.ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockRecipeFeast) {
                fallback = ((BlockRecipeFeast) block).maxServings;
            }
        }
        return getServings(stack, fallback);
    }

    private static int getServings(ItemStack stack, int fallback) {
        if (!stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("Servings", 3)) {
            return Math.max(0, Math.min(7, stack.getTagCompound().getInteger("Servings")));
        }
        return fallback;
    }

    private static void setServings(ItemStack stack, int servings) {
        NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        tag.setInteger("Servings", Math.max(0, Math.min(7, servings)));
        stack.setTagCompound(tag);
    }

    private static int getServings(IBlockAccess world, BlockPos pos, int fallback) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            return Math.max(0, Math.min(7, ((TileEntityFeastServings) tile).getServings()));
        }
        return fallback;
    }

    private static void setServings(World world, BlockPos pos, int servings) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            ((TileEntityFeastServings) tile).setServings(Math.max(0, Math.min(7, servings)));
        }
    }

    private static boolean canStay(World world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getBlockState(below).isSideSolid(world, below, EnumFacing.UP);
    }
}
