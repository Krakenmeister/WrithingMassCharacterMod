package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class DecreaseMaxHpAction extends AbstractGameAction {
    private final int amount;

    public DecreaseMaxHpAction(AbstractCreature creature, int amount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.amount = amount;
        this.target = creature;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.decreaseMaxHealth(amount);
        }

        this.tickDuration();
    }
}
