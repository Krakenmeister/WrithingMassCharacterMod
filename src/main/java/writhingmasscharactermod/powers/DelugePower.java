package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.MidForm;
import writhingmasscharactermod.util.FormChangePower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class DelugePower extends BasePower implements FormChangePower {
    public static final String POWER_ID = makeID("Deluge");
    private static final boolean TURN_BASED = false;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    public DelugePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onFormChange(AbstractForm form) {
        if (form.ID.equals(MidForm.FORM_ID)) {
            addToBot(new DrawCardAction(amount));
        }
    }
}
