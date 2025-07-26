package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.cards.common.Whip;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class DanseMacabreMalignantPower extends BasePower {
    public static final String POWER_ID = makeID("DanseMacabreMalignant");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DanseMacabreMalignantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        addToBot(new MakeTempCardInHandAction(new Whip(false, true, true), amount));
    }
}
