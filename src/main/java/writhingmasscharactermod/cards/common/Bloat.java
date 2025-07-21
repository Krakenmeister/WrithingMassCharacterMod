package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.FormChangeTrigger;
import writhingmasscharactermod.util.WrithingCard;

public class Bloat extends WrithingCard implements FormChangeTrigger {
    public static final String ID = makeID("Bloat");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public Bloat() {
        this(true, true);
    }

    public Bloat(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Bloat(!isBenign, false);
        }

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    protected String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new GainBlockAction(source, source, block));
        if (FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID)) {
            addToBot(new GainBlockAction(source, source, block));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new LoseBlockAction(source, source, block));
        if (FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID)) {
            addToBot(new LoseBlockAction(source, source, block));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void triggerExhaustedCardsOnFormChange(AbstractForm form) {
        triggerOnGlowCheck();
    }
}
