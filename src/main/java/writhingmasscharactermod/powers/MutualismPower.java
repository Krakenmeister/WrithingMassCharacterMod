package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class MutualismPower extends BasePower {
    public static final String POWER_ID = makeID("Mutualism");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public MutualismPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        if (this.amount >= 9999) {
            this.amount = 9999;
        }
    }

    public void updateDescription() {
        if (amount <= 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public int onHeal(int healAmount) {
        for (int i = 0; i < amount; i++) {
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));
        }

        return super.onHeal(healAmount);
    }
}
