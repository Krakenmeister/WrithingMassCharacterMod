package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class AntibodiesPower extends BasePower {
    public static final String POWER_ID = makeID("Antibodies");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public AntibodiesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        addToBot(new ApplyPowerAction(owner, owner, new BufferPower(owner, amount)));
        addToBot(new ApplyPowerAction(owner, owner, new LoseBufferPower(owner, amount)));
    }
}
