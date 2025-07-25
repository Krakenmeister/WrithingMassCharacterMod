package writhingmasscharactermod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.OnUseInfestedCardPower;
import writhingmasscharactermod.util.WrithingCard;

public class InfestedCardUseAction extends AbstractGameAction {
    private WrithingCard card;
    private boolean triggered = false;
    private static final float ACTION_DURATION = Settings.ACTION_DUR_LONG;
    private boolean sendToDiscard;

    public InfestedCardUseAction(AbstractMonster m, WrithingCard c) {
        this.source = m;
        this.target = AbstractDungeon.player;
        this.card = c;
        this.duration = ACTION_DURATION;
        this.actionType = ActionType.WAIT;
        this.sendToDiscard = false;
    }

    public InfestedCardUseAction(AbstractMonster m, WrithingCard c, boolean sendToDiscard) {
        this.source = m;
        this.target = AbstractDungeon.player;
        this.card = c;
        this.duration = ACTION_DURATION;
        this.actionType = ActionType.WAIT;
        this.sendToDiscard = sendToDiscard;
    }

    public void update() {
        if (!triggered) {
            triggered = true;

            card.writhingUse(source, target);

            if (card.exhaust) {
                InfestUtils.ExhaustInfestedCard((AbstractMonster) source, card, sendToDiscard);
            }

            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof OnUseInfestedCardPower) {
                    ((OnUseInfestedCardPower) power).OnUseInfestedCard(card);
                }
            }

            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!monster.isDeadOrEscaped()) {
                    for (AbstractPower power : monster.powers) {
                        if (power instanceof OnUseInfestedCardPower) {
                            ((OnUseInfestedCardPower) power).OnUseInfestedCard(card);
                        }
                    }
                }
            }

            float centerX = Settings.WIDTH / 2.0F;
            float centerY = Settings.HEIGHT / 2.0F;

            float verticalRange = 150.0F * Settings.scale;
            float horizontalRange = 400.0F * Settings.scale;

            float offsetX = AbstractDungeon.cardRandomRng.random(-horizontalRange, horizontalRange);
            float offsetY = AbstractDungeon.cardRandomRng.random(-verticalRange, verticalRange);

            AbstractCard copy = card.makeStatEquivalentCopy();
            copy.calculateCardDamage(null);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(copy, centerX + offsetX, centerY + offsetY));

            if (sendToDiscard && !card.exhaust) {
                card.setOwner(AbstractDungeon.player);
                AbstractDungeon.player.discardPile.addToTop(card);
                AbstractDungeon.player.discardPile.refreshHandLayout();
            }
        }

        tickDuration();
    }
}
