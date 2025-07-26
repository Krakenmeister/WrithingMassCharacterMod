package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.WrithingCard;

import java.util.ArrayList;
import java.util.List;

public class InfestAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private AbstractPlayer p;
    private AbstractMonster m;
    private List<AbstractCard> inertCards = new ArrayList<>();

    public InfestAction(AbstractMonster monster, int amount) {
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        m = monster;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for(AbstractCard c : p.hand.group) {
                if (!(c instanceof WrithingCard)) {
                    inertCards.add(c);
                } else {
                    if (((WrithingCard)c).isInert) {
                        inertCards.add(c);
                    }
                }
            }

            if (inertCards.size() == p.hand.group.size()) {
                isDone = true;
                return;
            }

            if (p.hand.group.size() - inertCards.size() <= amount) {
                amount = p.hand.group.size() - inertCards.size();
            }

            p.hand.group.removeAll(inertCards);
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], Math.min(p.hand.group.size(), amount), false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                InfestUtils.InfestMonster(m, (WrithingCard) c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for(AbstractCard c : inertCards) {
            p.hand.addToTop(c);
        }

        p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("InfestAction");
        TEXT = uiStrings.TEXT;
    }
}
