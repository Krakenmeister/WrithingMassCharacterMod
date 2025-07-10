package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class JokesOnYouPower extends BasePower {
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
    public int onLoseHp(int damageAmount) {
        System.out.println("got here");
        System.out.println(damageAmount);

        if (damageAmount > 0) {
            flash();
            addToBot(new HealAction(owner, owner, amount));
        }

        return super.onLoseHp(damageAmount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        System.out.println("what");
        System.out.println(damageAmount);
        return super.onAttacked(info, damageAmount);
    }
}
