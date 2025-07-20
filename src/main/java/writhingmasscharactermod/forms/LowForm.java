package writhingmasscharactermod.forms;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class LowForm extends AbstractForm {
    public static final String FORM_ID = makeID("Low");
    private static final StanceStrings formStrings = CardCrawlGame.languagePack.getStanceString(FORM_ID);
    private static final String NAME = formStrings.NAME;
    private static final String[] DESCRIPTIONS = formStrings.DESCRIPTION;

    public LowForm() {
        this.ID = FORM_ID;
        this.name = NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
    }

    @Override
    public void onEnterForm() {
        super.onEnterForm();

        AbstractDungeon.player.tint.changeColor(Color.MAGENTA);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.MAGENTA, true));
    }

    @Override
    public void onExitForm() {
        super.onExitForm();

        AbstractDungeon.player.tint.changeColor(Color.WHITE);
    }
}
