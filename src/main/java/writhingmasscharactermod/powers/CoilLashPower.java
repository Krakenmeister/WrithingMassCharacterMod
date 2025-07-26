package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.util.FormChangePower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class CoilLashPower extends BasePower implements FormChangePower {
    public static final String POWER_ID = makeID("CoilLash");
    private static final boolean TURN_BASED = false;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    public CoilLashPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onFormChange(AbstractForm form) {
        flash();
        addToBot(new MakeTempCardInHandAction(new Whip(), amount));
    }
}
