package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.util.InfestUtils;
import writhingmasscharactermod.util.WrithingCard;

public class InfestAnyMonsterAction extends AbstractGameAction {
    private enum Phase { SELECT_CARD, SELECT_MONSTER, DONE }

    private Phase phase = Phase.SELECT_CARD;
    private AbstractCard selectedCard = null;
    private boolean selectionOpened = false;

    public InfestAnyMonsterAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        switch (phase) {
            case SELECT_CARD:
                if (!selectionOpened) {
                    AbstractDungeon.handCardSelectScreen.open("Choose a card to infest", 1, false, false);
                    selectionOpened = true;
                    return;
                }

                if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                    selectedCard = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    AbstractDungeon.handCardSelectScreen.selectedCards.clear();

                    selectedCard.target = AbstractCard.CardTarget.ENEMY;
                    AbstractDungeon.player.cardInUse = selectedCard;
                    AbstractDungeon.player.isDraggingCard = true;
                    AbstractDungeon.player.hoveredCard = selectedCard;
                    selectedCard.calculateCardDamage(null);

                    ((WrithingCard) selectedCard).isInfesting = true;

                    phase = Phase.SELECT_MONSTER;
                }
                break;

            case SELECT_MONSTER:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDeadOrEscaped() && m.hb.hovered && InputHelper.justClickedLeft) {
                        InputHelper.justClickedLeft = false;

                        if (selectedCard instanceof WrithingCard) {
                            InfestUtils.InfestMonster(m, (WrithingCard) selectedCard);
                        }

                        AbstractDungeon.player.cardInUse = null;
                        AbstractDungeon.player.isDraggingCard = false;

                        phase = Phase.DONE;
                        break;
                    }
                }

                break;

            case DONE:
                this.isDone = true;
                break;
        }
    }
}
