package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import writhingmasscharactermod.patches.realtemphp.RealTempHPField;
import writhingmasscharactermod.util.OnHealCard;


public class AddRealTemporaryHPAction extends AbstractGameAction {
    public AddRealTemporaryHPAction(AbstractCreature target, AbstractCreature source, int amount) {
        int healAmount = amount;
        if (target.isPlayer) {
            for(AbstractCard card : ((AbstractPlayer)target).hand.group) {
                if (card instanceof OnHealCard) {
                    healAmount = ((OnHealCard)card).onHeal(healAmount);
                }
            }
        }
        this.setValues(target, source, healAmount);
        this.actionType = ActionType.HEAL;
    }

    public void update() {
        if (this.duration == 0.5F) {
            RealTempHPField.realTempHp.set(this.target, (Integer)RealTempHPField.realTempHp.get(this.target) + this.amount);
            if (this.amount > 0) {
                AbstractDungeon.effectsQueue.add(new HealEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY, this.amount));
                this.target.healthBarUpdatedEvent();
            }
        }

        this.tickDuration();
    }
}

