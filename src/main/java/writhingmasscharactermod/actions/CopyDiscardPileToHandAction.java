package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CopyDiscardPileToHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final AbstractPlayer player;

    public CopyDiscardPileToHandAction() {
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
    }

    public void update() {
        if (duration == startDuration) {
            if (!player.discardPile.isEmpty()) {
                if (player.discardPile.size() == 1) {
                    AbstractCard cardToCopy = player.discardPile.group.get(0);

                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardToCopy));

                    isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(player.discardPile, 1, TEXT[0], false);

                    tickDuration();
                }
            } else {
                isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard cardToCopy = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardToCopy));

                for(AbstractCard c : player.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            tickDuration();
            if (isDone) {
                for(AbstractCard c : player.hand.group) {
                    c.applyPowers();
                }
            }

        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("CopyDiscardToHandAction").TEXT;
    }
}
