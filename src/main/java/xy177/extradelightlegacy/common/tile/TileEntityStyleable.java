package xy177.extradelightlegacy.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStyleable extends TileEntity implements IStyleableTile {
    private int styleCount = 1;
    private int style;

    public TileEntityStyleable() {
    }

    public TileEntityStyleable(int styleCount) {
        this.styleCount = Math.max(1, styleCount);
    }

    public void setStyleCount(int styleCount) {
        this.styleCount = Math.max(1, styleCount);
        if (style >= this.styleCount) {
            style = 0;
        }
    }

    @Override
    public int getStyleCount() {
        return styleCount;
    }

    @Override
    public int getStyle() {
        return style;
    }

    @Override
    public void setStyle(int style) {
        this.style = Math.max(0, Math.min(styleCount - 1, style));
        markUpdated();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Style", style);
        compound.setInteger("StyleCount", styleCount);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        styleCount = Math.max(1, compound.getInteger("StyleCount"));
        style = Math.max(0, Math.min(styleCount - 1, compound.getInteger("Style")));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    protected void markUpdated() {
        markDirty();
        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }
}
