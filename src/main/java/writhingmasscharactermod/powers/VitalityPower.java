package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class VitalityPower extends BasePower {
    public static final String POWER_ID = makeID("Vitality");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public VitalityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = true;
    }

    @Override
    public int onHeal(int healAmount) {
        healAmount = healAmount + amount;

        return super.onHeal(healAmount);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if (amount > 0) {
            type = PowerType.BUFF;
        } else if (amount < 0) {
            type = PowerType.DEBUFF;
        }
    }
}
