package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import writhingmasscharactermod.cards.common.Bloat;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.HighForm;
import writhingmasscharactermod.forms.LowForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.FormChangeTrigger;
import writhingmasscharactermod.util.WrithingCard;

public class RoundTwo extends WrithingCard implements FormChangeTrigger {
    public static final String ID = makeID("RoundTwo");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int ROUND_TWO_ADDITIONAL = 9;
    private static final int UPG_ROUND_TWO_ADDITIONAL = 14;

    public RoundTwo() {
        this(true, true);
    }

    public RoundTwo(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new RoundTwo(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("roundtwoadditional", ROUND_TWO_ADDITIONAL, UPG_ROUND_TWO_ADDITIONAL);
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
        addToBot(new HealAction(source, source, magicNumber));
        if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth < 1 / 3)) {
            addToBot(new HealAction(source, source, customVar("roundtwoadditional")));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new LoseHPAction(source, source, magicNumber));
        if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth > 2 / 3)) {
            addToBot(new LoseHPAction(source, source, customVar("roundtwoadditional")));
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isBenign) {
            if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth < 1 / 3)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            if ((FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(HighForm.FORM_ID) && owner instanceof AbstractPlayer) || (owner.currentHealth / owner.maxHealth > 2 / 3)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }

    }

    @Override
    public void triggerExhaustedCardsOnFormChange(AbstractForm form) {
        triggerOnGlowCheck();
    }
}
