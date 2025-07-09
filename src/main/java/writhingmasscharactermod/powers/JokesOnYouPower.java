package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnAnyHealthLossPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class JokesOnYouPower extends BasePower implements OnAnyHealthLossPower {
    public static final String POWER_ID = makeID("JokesOnYou");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public JokesOnYouPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        if (this.amount >= 9999) {
            this.amount = 9999;
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new RemoveSpecificPowerAction(owner, owner, JokesOnYouPower.POWER_ID));
    }

    @Override
    public void onAnyHealthLoss(DamageInfo info, int damageAmount, boolean wasTempHpLost) {
        System.out.println(damageAmount);
        System.out.println(wasTempHpLost);
        if (damageAmount > 0 || wasTempHpLost) {
            flash();
            addToBot(new HealAction(owner, owner, amount));
        }
    }
}
