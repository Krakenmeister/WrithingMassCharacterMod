package writhingmasscharactermod.forms;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class HighForm extends AbstractForm {
    public static final String FORM_ID = makeID("High");
    private static final StanceStrings formStrings = CardCrawlGame.languagePack.getStanceString(FORM_ID);
    private static final String NAME = formStrings.NAME;
    private static final String[] DESCRIPTIONS = formStrings.DESCRIPTION;

    public HighForm() {
        this.ID = FORM_ID;
        this.name = NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 3));
    }
}
