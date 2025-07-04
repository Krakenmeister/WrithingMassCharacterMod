package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class ResiliencePower extends BasePower {
    public static final String POWER_ID = makeID("Resilience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ResiliencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        if (this.amount >= 9999) {
            this.amount = 9999;
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
