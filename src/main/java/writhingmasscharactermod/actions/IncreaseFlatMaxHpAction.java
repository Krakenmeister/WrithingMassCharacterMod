package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class IncreaseFlatMaxHpAction extends AbstractGameAction {
    private final boolean showEffect;
    private final int amount;

    public IncreaseFlatMaxHpAction(AbstractCreature creature, int amount, boolean showEffect) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.showEffect = showEffect;
        this.amount = amount;
        this.target = creature;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.increaseMaxHp(amount, this.showEffect);
        }

        this.tickDuration();
    }
}
