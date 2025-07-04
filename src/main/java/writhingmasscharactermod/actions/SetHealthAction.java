package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SetHealthAction extends AbstractGameAction {
    private int health;

    public SetHealthAction(AbstractCreature target, int health) {
        this.source = null;
        this.target = target;
        this.health = health;
    }

    public void update() {
        this.target.currentHealth = health;
        this.target.healthBarUpdatedEvent();
        //this.target.damage(new DamageInfo((AbstractCreature)null, 0, DamageInfo.DamageType.HP_LOSS));
        this.isDone = true;
    }
}