package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class CannibalismPower extends BasePower {
    public static final String POWER_ID = makeID("Cannibalism");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public CannibalismPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        this.amount = -1;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
