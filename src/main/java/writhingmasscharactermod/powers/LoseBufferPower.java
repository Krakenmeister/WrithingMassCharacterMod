package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import writhingmasscharactermod.actions.RemoveZeroBufferAction;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class LoseBufferPower extends BasePower {
    public static final String POWER_ID = makeID("LoseBuffer");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public LoseBufferPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new BufferPower(this.owner, -1 * this.amount), -1 * this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, LoseBufferPower.POWER_ID));
        addToBot(new RemoveZeroBufferAction(this.owner));
    }
}
