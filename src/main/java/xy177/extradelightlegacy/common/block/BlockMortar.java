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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import xy177.extradelightlegacy.ExtraDelightLegacy;
import xy177.extradelightlegacy.common.gui.EDLGuiHandler;
import xy177.extradelightlegacy.common.registry.EDLItems;
import xy177.extradelightlegacy.common.tile.TileEntityMortar;

public class BlockMortar extends Block implements ITileEntityProvider, IStyleableBlock {
    public static final PropertyInteger STYLE = PropertyInteger.create("style", 0, 15);
    private static final AxisAlignedBB SHAPE = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.375D, 0.75D);

    public BlockMortar() {
        super(Material.ROCK);
        setHardness(0.5F);
        setResistance(2.0F);
        setSoundType(SoundType.STONE);
        setLightOpacity(0);
        setDefaultState(blockState.getBaseState().withProperty(STYLE, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SHAPE;
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMortar();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (!held.isEmpty() && held.getItem() instanceof xy177.extradelightlegacy.common.item.ItemOffsetSpatula) {
            if (!world.isRemote) {
                if (player.isSneaking()) {
                    player.openGui(ExtraDelightLegacy.instance, EDLGuiHandler.STYLEABLE, world, pos.getX(), pos.getY(), pos.getZ());
                } else {
                    setNextStyle(world, pos, state);
                    if (!player.capabilities.isCreativeMode && held.isItemStackDamageable()) {
                        held.damageItem(1, player);
                    }
                    world.playSound(null, pos, net.minecraft.init.SoundEvents.BLOCK_SLIME_PLACE, net.minecraft.util.SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return true;
        }

        TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityMortar)) {
            return false;
        }

        TileEntityMortar mortar = (TileEntityMortar) tile;
        if (world.isRemote) {
            return true;
        }

        if (player.isSneaking()) {
            return mortar.extractItem(player);
        }

        if (mortar.handleFluidContainer(player, held)) {
            return true;
        }

        if (isPestle(held) && mortar.grind(player)) {
            return true;
        }

        return mortar.insertHeldItem(player, held);
    }

    @Override
    public int getStyleCount() {
        return 16;
    }

    @Override
    public int getCurrentStyle(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    public IBlockState withStyle(IBlockState state, int style) {
        return state.withProperty(STYLE, Math.max(0, Math.min(15, style)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STYLE, meta & 15);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STYLE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STYLE);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityMortar) {
            TileEntityMortar mortar = (TileEntityMortar) tile;
            ItemStack stack = mortar.getInsertedItem();
            if (!stack.isEmpty()) {
                spawnAsEntity(world, pos, stack);
            }
            mortar.clear();
        }
        super.breakBlock(world, pos, state);
    }

    private static boolean isPestle(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (EDLItems.PESTLE_STONE.isRegistered() && stack.getItem() == EDLItems.PESTLE_STONE.getItem()) {
            return true;
        }
        int toolId = OreDictionary.getOreID("toolPestle");
        int pestleId = OreDictionary.getOreID("pestle");
        for (int id : OreDictionary.getOreIDs(stack)) {
            if (id == toolId || id == pestleId) {
                return true;
            }
        }
        return false;
    }
}
