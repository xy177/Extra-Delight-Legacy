package xy177.extradelightlegacy.client.render;

import xy177.extradelightlegacy.common.tile.TileEntityCandyBowl;

public class TileEntityCandyBowlRenderer extends TileEntityDisplayBowlRenderer<TileEntityCandyBowl> {
    private static final double[][] OFFSETS = {
        {-0.12D, 0.05D, -0.03D, 0.0D, 25.0D},
        {0.12D, 0.05D, -0.03D, 90.0D, 25.0D},
        {0.12D, 0.05D, 0.15D, 180.0D, 25.0D},
        {-0.12D, 0.05D, 0.15D, 270.0D, 25.0D},
        {-0.06D, 0.17D, -0.12D, 45.0D, 35.0D},
        {0.12D, 0.17D, 0.0D, 135.0D, 35.0D},
        {0.06D, 0.17D, 0.12D, 215.0D, 35.0D},
        {-0.12D, 0.17D, 0.0D, 305.0D, 35.0D},
        {-0.12D, 0.32D, -0.24D, 0.0D, 90.0D}
    };

    public TileEntityCandyBowlRenderer() {
        super(OFFSETS, 1.2F, true);
    }
}
