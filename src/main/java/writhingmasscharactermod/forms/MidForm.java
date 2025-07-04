package writhingmasscharactermod.forms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import writhingmasscharactermod.cards.effects.MidFormEffect;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class MidForm extends AbstractForm {
    public static final String FORM_ID = makeID("Mid");
    private static final StanceStrings formStrings = CardCrawlGame.languagePack.getStanceString(FORM_ID);
    private static final String NAME = formStrings.NAME;
    private static final String[] DESCRIPTIONS = formStrings.DESCRIPTION;

    public MidForm() {
        this.ID = FORM_ID;
        this.name = NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onEnterForm() {
        super.onEnterForm();

        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GREEN, true));
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    public void updateAnimation() {
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new MidFormEffect());
        }

    }
}
