package writhingmasscharactermod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import writhingmasscharactermod.util.WrithingCard;

public class InfestedCardUseAction extends AbstractGameAction {
    private WrithingCard card;
    private boolean triggered = false;

    public InfestedCardUseAction(AbstractMonster m, WrithingCard c) {
        this.source = m;
        this.target = AbstractDungeon.player;
        this.card = c;
        this.duration = Settings.ACTION_DUR_LONG;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (!triggered) {
            triggered = true;

            card.flash(Color.YELLOW);
            card.shouldFlash = true;

            card.writhingUse(source, target);

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
        }

        tickDuration();
    }
}
