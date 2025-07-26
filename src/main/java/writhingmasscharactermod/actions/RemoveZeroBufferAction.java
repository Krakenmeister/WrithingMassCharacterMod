package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.BufferPower;

public class RemoveZeroBufferAction extends AbstractGameAction {
    public RemoveZeroBufferAction(AbstractCreature owner) {
        this.target = owner;
    }

    public void update() {
        if (target.hasPower(BufferPower.POWER_ID)) {
            if (target.getPower(BufferPower.POWER_ID).amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(target, target, BufferPower.POWER_ID));
            }
        }

        isDone = true;
    }
}
