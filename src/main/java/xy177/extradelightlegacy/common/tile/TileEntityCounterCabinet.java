package xy177.extradelightlegacy.common.tile;

public class TileEntityCounterCabinet extends TileEntityDisplayCabinet {
    public static final int SLOT_COUNT = 28;
    public static final int DISPLAY_SLOT = 27;

    public TileEntityCounterCabinet() {
        super(SLOT_COUNT, DISPLAY_SLOT, "screen.extradelightlegacy.counter_cabinet.name");
    }
}
