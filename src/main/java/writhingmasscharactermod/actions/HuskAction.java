package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import writhingmasscharactermod.powers.BleedPower;

public class HuskAction extends AbstractGameAction {
    private final AbstractCreature target;

    public HuskAction(AbstractCreature target) {
        this.actionType = ActionType.WAIT;
        this.target = target;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST && target.hasPower(BleedPower.POWER_ID)) {
            int bleedAmount = target.getPower(BleedPower.POWER_ID).amount;
            this.addToTop(new ApplyPowerAction(target, target, new BleedPower(target, bleedAmount), bleedAmount));
        }

        this.tickDuration();
    }
}
