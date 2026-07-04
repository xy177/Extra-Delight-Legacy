package xy177.extradelightlegacy.client.render;

import xy177.extradelightlegacy.common.tile.TileEntityFruitBowl;

public class TileEntityFruitBowlRenderer extends TileEntityDisplayBowlRenderer<TileEntityFruitBowl> {
    private static final double[][] OFFSETS = {
        {0.0D, 0.25D, 0.23D, 0.0D, 45.0D},
        {0.0D, 0.25D, 0.23D, 90.0D, 45.0D},
        {0.0D, 0.25D, 0.23D, 180.0D, 45.0D},
        {0.0D, 0.25D, 0.23D, 270.0D, 45.0D},
        {0.0D, 0.25D, 0.15D, 45.0D, 45.0D},
        {0.0D, 0.25D, 0.15D, 135.0D, 45.0D},
        {0.0D, 0.25D, 0.15D, 225.0D, 45.0D},
        {0.0D, 0.25D, 0.15D, 315.0D, 45.0D},
        {0.0D, 0.25D, -0.05D, 0.0D, 90.0D}
    };

    public TileEntityFruitBowlRenderer() {
        super(OFFSETS, 0.75F);
    }
}
