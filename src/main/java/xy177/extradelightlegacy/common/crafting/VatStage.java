package xy177.extradelightlegacy.common.crafting;

public final class VatStage {
    private final MixingBowlIngredient ingredient;
    private final boolean requiresLid;
    private final int time;

    public VatStage(MixingBowlIngredient ingredient, boolean requiresLid, int time) {
        this.ingredient = ingredient;
        this.requiresLid = requiresLid;
        this.time = time;
    }

    public MixingBowlIngredient getIngredient() {
        return ingredient;
    }

    public boolean requiresLid() {
        return requiresLid;
    }

    public int getTime() {
        return time;
    }

    public boolean hasIngredient() {
        return ingredient != null;
    }
}
