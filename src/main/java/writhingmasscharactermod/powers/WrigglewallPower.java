package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class WrigglewallPower extends BasePower{
    public static final String POWER_ID = makeID("Wrigglewall");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WrigglewallPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        if (owner.hasPower(RealMalleablePower.POWER_ID)) {
            ((RealMalleablePower)owner.getPower(RealMalleablePower.POWER_ID)).updateDescription(true);
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
