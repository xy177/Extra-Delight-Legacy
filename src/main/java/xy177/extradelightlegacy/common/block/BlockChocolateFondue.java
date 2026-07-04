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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.common.registry.EDLItems;
import xy177.extradelightlegacy.common.tile.TileEntityFeastServings;

import java.util.function.Supplier;

public class BlockChocolateFondue extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger SERVINGS = PropertyInteger.create("servings", 0, 8);
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.625D, 0.875D);

    private final Supplier<Item> appleResult;
    private final Supplier<Item> marshmallowResult;
    private final Supplier<Item> grahamResult;
    private final Supplier<Item> sweetBerryResult;
    private final Supplier<Item> glowBerryResult;
    private final Supplier<Item> baconResult;

    public BlockChocolateFondue(Supplier<Item> appleResult, Supplier<Item> marshmallowResult, Supplier<Item> grahamResult,
                                Supplier<Item> sweetBerryResult, Supplier<Item> glowBerryResult, Supplier<Item> baconResult) {
        super(Material.IRON);
        this.appleResult = appleResult;
        this.marshmallowResult = marshmallowResult;
        this.grahamResult = grahamResult;
        this.sweetBerryResult = sweetBerryResult;
        this.glowBerryResult = glowBerryResult;
        this.baconResult = baconResult;
        setHardness(1.0F);
        setResistance(4.0F);
        setSoundType(SoundType.METAL);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SERVINGS, 8));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFeastServings(8);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        int servings = getServings(world, pos, 8);
        if (servings <= 0) {
            if (!world.isRemote) {
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
                world.destroyBlock(pos, true);
            }
            return true;
        }

        if (held.isEmpty()) {
            return false;
        }

        ItemStack result = getDipResult(held);
        if (result.isEmpty()) {
            return false;
        }

        if (!world.isRemote) {
            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
            }
            if (!player.inventory.addItemStackToInventory(result)) {
                player.dropItem(result, false);
            }
            setServings(world, pos, servings - 1);
        }
        return true;
    }

    private ItemStack getDipResult(ItemStack input) {
        if (matches(input, EDLItems.SLICED_APPLE.getItem())) {
            return stack(appleResult);
        }
        if (matches(input, EDLItems.MARSHMALLOW.getItem())) {
            return stack(marshmallowResult);
        }
        if (matches(input, EDLItems.GRAHAM_CRACKER.getItem())) {
            return stack(grahamResult);
        }
        if (matchesOre(input, "cropBerrySweet")) {
            return stack(sweetBerryResult);
        }
        if (matchesOre(input, "cropBerryGlow")) {
            return stack(glowBerryResult);
        }
        if (matchesOre(input, "foodBaconCooked")) {
            return stack(baconResult);
        }
        return ItemStack.EMPTY;
    }

    private static boolean matches(ItemStack stack, Item item) {
        return item != null && stack.getItem() == item;
    }

    private static boolean matchesOre(ItemStack stack, String oreName) {
        if (stack.isEmpty()) {
            return false;
        }
        int oreId = OreDictionary.getOreID(oreName);
        for (int stackOreId : OreDictionary.getOreIDs(stack)) {
            if (stackOreId == oreId) {
                return true;
            }
        }
        return false;
    }

    private static ItemStack stack(Supplier<Item> supplier) {
        Item item = supplier.get();
        return item == null ? ItemStack.EMPTY : new ItemStack(item);
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
        return getDefaultState().withProperty(FACING, horizontal).withProperty(SERVINGS, 8);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        setServings(worldIn, pos, stack.hasTagCompound() ? stack.getTagCompound().getInteger("Servings") : 8);
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
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(SERVINGS, 8);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(SERVINGS, getServings(worldIn, pos, 8));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SERVINGS);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack stack = new ItemStack(this);
        int servings = getServings(world, pos, 8);
        if (servings != 8) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("Servings", servings);
            stack.setTagCompound(tag);
        }
        drops.add(stack);
    }

    private static int getServings(IBlockAccess world, BlockPos pos, int fallback) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            return Math.max(0, Math.min(8, ((TileEntityFeastServings) tile).getServings()));
        }
        return fallback;
    }

    private static void setServings(World world, BlockPos pos, int servings) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityFeastServings) {
            ((TileEntityFeastServings) tile).setServings(Math.max(0, Math.min(8, servings)));
        }
    }

    private static boolean canStay(World world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getBlockState(below).isSideSolid(world, below, EnumFacing.UP);
    }
}
