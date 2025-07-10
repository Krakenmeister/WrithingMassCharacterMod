package writhingmasscharactermod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IncreasePlayerMaxHpAction extends AbstractGameAction {
    private final boolean showEffect;
    private final int amount;

    public IncreasePlayerMaxHpAction(AbstractPlayer p, int amount, boolean showEffect) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.showEffect = showEffect;
        this.amount = amount;
        this.target = p;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.increaseMaxHp(amount, this.showEffect);
        }

        this.tickDuration();
    }
}
