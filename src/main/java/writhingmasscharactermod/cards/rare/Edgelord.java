package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import writhingmasscharactermod.actions.IncreaseFlatMaxHpAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.LowForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.FormChangeTrigger;
import writhingmasscharactermod.util.WrithingCard;

public class Edgelord extends WrithingCard implements FormChangeTrigger {
    public static final String ID = makeID("Edgelord");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Edgelord() {
        this(true, true);
    }

    public Edgelord(boolean isBenign, boolean previewCards) {
        super(ID, info, true);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Edgelord(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setExhaust(true);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth < 1 / 3)) {
            addToBot(new IncreaseFlatMaxHpAction(source, magicNumber, true));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth < 1 / 3)) {
            addToBot(new InstantKillAction(source));
        }
    }

    @Override
    public void triggerExhaustedCardsOnFormChange(AbstractForm form) {
        triggerOnGlowCheck();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth < 1 / 3)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}
