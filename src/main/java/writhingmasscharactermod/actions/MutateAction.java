package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import writhingmasscharactermod.util.WrithingCard;

import java.util.ArrayList;
import java.util.List;

public class MutateAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private AbstractPlayer p;
    private List<AbstractCard> immutableCards = new ArrayList<>();

    public MutateAction(int amount) {
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for(AbstractCard c : p.hand.group) {
                if (!(c instanceof WrithingCard)) {
                    immutableCards.add(c);
                } else {
                    if (!((WrithingCard)c).isMutable) {
                        immutableCards.add(c);
                    }
                }
            }

            if (immutableCards.size() == p.hand.group.size()) {
                isDone = true;
                return;
            }

            if (p.hand.group.size() - immutableCards.size() <= amount) {
                for (AbstractCard c : p.hand.group) {
                    if (c instanceof WrithingCard) {
                        if (((WrithingCard)c).isMutable) {
                            ((WrithingCard)c).toggleBenign();
                            ((WrithingCard)c).superFlash();
                            ((WrithingCard)c).applyPowers();
                        }
                    }
                }
                isDone = true;
                return;
            }

            p.hand.group.removeAll(immutableCards);
            if (p.hand.group.size() > amount) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false);
                this.tickDuration();
                return;
            } else {
                for (AbstractCard c : p.hand.group) {
                    if (c instanceof WrithingCard) {
                        if (((WrithingCard)c).isMutable) {
                            ((WrithingCard)c).toggleBenign();
                            ((WrithingCard)c).superFlash();
                            ((WrithingCard)c).applyPowers();
                        }
                    }
                }
                isDone = true;
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                ((WrithingCard)c).toggleBenign();
                ((WrithingCard)c).superFlash();
                ((WrithingCard)c).applyPowers();
                p.hand.addToTop(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {
        for(AbstractCard c : immutableCards) {
            p.hand.addToTop(c);
        }

        p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("MutateAction");
        TEXT = uiStrings.TEXT;
    }
}
