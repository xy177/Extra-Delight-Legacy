package xy177.extradelightlegacy.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import xy177.extradelightlegacy.client.gui.GuiChiller;
import xy177.extradelightlegacy.client.gui.GuiDisplayCabinet;
import xy177.extradelightlegacy.client.gui.GuiDoughShaping;
import xy177.extradelightlegacy.client.gui.GuiMeltingPot;
import xy177.extradelightlegacy.client.gui.GuiMixingBowl;
import xy177.extradelightlegacy.client.gui.GuiOven;
import xy177.extradelightlegacy.client.gui.GuiPicnicBasket;
import xy177.extradelightlegacy.client.gui.GuiStyleable;
import xy177.extradelightlegacy.client.gui.GuiVat;
import xy177.extradelightlegacy.common.block.IStyleableBlock;
import xy177.extradelightlegacy.common.registry.EDLBlocks;

public class EDLGuiHandler implements IGuiHandler {
    public static final int DOUGH_SHAPING = 1;
    public static final int MIXING_BOWL = 2;
    public static final int CHILLER = 3;
    public static final int MELTING_POT = 4;
    public static final int OVEN = 5;
    public static final int VAT = 6;
    public static final int PICNIC_BASKET = 7;
    public static final int COUNTER_CABINET = 8;
    public static final int SINK_CABINET = 9;
    public static final int STYLEABLE = 10;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == DOUGH_SHAPING && EDLBlocks.DOUGH_SHAPING.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.DOUGH_SHAPING.getBlock()) {
            return new ContainerDoughShaping(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == MIXING_BOWL && EDLBlocks.MIXING_BOWL.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.MIXING_BOWL.getBlock()) {
            return new ContainerMixingBowl(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == CHILLER && EDLBlocks.CHILLER.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.CHILLER.getBlock()) {
            return new ContainerChiller(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == MELTING_POT && EDLBlocks.MELTING_POT.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.MELTING_POT.getBlock()) {
            return new ContainerMeltingPot(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == OVEN && EDLBlocks.OVEN.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.OVEN.getBlock()) {
            return new ContainerOven(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == VAT && EDLBlocks.VAT.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.VAT.getBlock()) {
            return new ContainerVat(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == PICNIC_BASKET) {
            return new ContainerPicnicBasket(player.inventory, world, new BlockPos(x, y, z));
        } else if (id == COUNTER_CABINET) {
            return new ContainerDisplayCabinet(player.inventory, world, new BlockPos(x, y, z), 28, 27, false);
        } else if (id == SINK_CABINET) {
            return new ContainerDisplayCabinet(player.inventory, world, new BlockPos(x, y, z), 19, 18, true);
        } else if (id == STYLEABLE
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof IStyleableBlock) {
            return new ContainerStyleable(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == DOUGH_SHAPING && EDLBlocks.DOUGH_SHAPING.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.DOUGH_SHAPING.getBlock()) {
            return new GuiDoughShaping(new ContainerDoughShaping(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == MIXING_BOWL && EDLBlocks.MIXING_BOWL.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.MIXING_BOWL.getBlock()) {
            return new GuiMixingBowl(new ContainerMixingBowl(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == CHILLER && EDLBlocks.CHILLER.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.CHILLER.getBlock()) {
            return new GuiChiller(new ContainerChiller(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == MELTING_POT && EDLBlocks.MELTING_POT.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.MELTING_POT.getBlock()) {
            return new GuiMeltingPot(new ContainerMeltingPot(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == OVEN && EDLBlocks.OVEN.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.OVEN.getBlock()) {
            return new GuiOven(new ContainerOven(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == VAT && EDLBlocks.VAT.getBlock() != null
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() == EDLBlocks.VAT.getBlock()) {
            return new GuiVat(new ContainerVat(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == PICNIC_BASKET) {
            return new GuiPicnicBasket(new ContainerPicnicBasket(player.inventory, world, new BlockPos(x, y, z)));
        } else if (id == COUNTER_CABINET) {
            return new GuiDisplayCabinet(
                new ContainerDisplayCabinet(player.inventory, world, new BlockPos(x, y, z), 28, 27, false),
                "screen.extradelightlegacy.counter_cabinet.name",
                false
            );
        } else if (id == SINK_CABINET) {
            return new GuiDisplayCabinet(
                new ContainerDisplayCabinet(player.inventory, world, new BlockPos(x, y, z), 19, 18, true),
                "screen.extradelightlegacy.sink.name",
                true
            );
        } else if (id == STYLEABLE
            && world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof IStyleableBlock) {
            return new GuiStyleable(new ContainerStyleable(player.inventory, world, new BlockPos(x, y, z)));
        }
        return null;
    }
}
