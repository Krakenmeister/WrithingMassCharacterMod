package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import writhingmasscharactermod.powers.LoseBufferPower;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.WrithingCard;

public class NaughtyAction extends AbstractGameAction {
    private boolean didAction;

    public NaughtyAction(AbstractCreature target, int amount) {
        this.actionType = ActionType.WAIT;
        this.duration = 0.5F;
        this.didAction = false;
        this.target = target;
        this.amount = amount;
    }

    public void update() {
        if (!didAction) {
            int numMalignantCards = 0;
            if (target instanceof AbstractPlayer) {
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card instanceof WrithingCard) {
                        if (((WrithingCard) card).isMutable && !((WrithingCard) card).isBenign) {
                            numMalignantCards++;
                        }
                    }
                }
            } else if (target instanceof AbstractMonster) {
                numMalignantCards = InfestUtils.GetNumMalignant((AbstractMonster) target);
            }

            //addToBot(new ApplyPowerAction(target, target, new BufferPower(target, numMalignantCards)));
            //addToBot(new ApplyPowerAction(target, target, new LoseBufferPower(target, numMalignantCards)));
            for (int i = 0; i < numMalignantCards; i++) {
                addToBot(new HealAction(target, target, amount));
            }
            didAction = true;
        }

        tickDuration();
    }
}
