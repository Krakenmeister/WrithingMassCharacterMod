package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.IncreaseFlatMaxHpAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.AbstractForm;
import writhingmasscharactermod.forms.LowForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.FormChangeTrigger;

public class Edgelord extends BaseCard implements FormChangeTrigger {
    public static final String ID = makeID("Edgelord");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Edgelord() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (FormFieldPatch.form.get(p).ID.equals(LowForm.FORM_ID)) {
            addToBot(new IncreaseFlatMaxHpAction(p, magicNumber, true));
        }
    }

    @Override
    public void triggerExhaustedCardsOnFormChange(AbstractForm form) {
        triggerOnGlowCheck();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (FormFieldPatch.form.get(AbstractDungeon.player).ID.equals(LowForm.FORM_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
}
