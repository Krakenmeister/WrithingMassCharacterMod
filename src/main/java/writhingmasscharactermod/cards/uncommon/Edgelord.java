package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.IncreasePlayerMaxHpAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.forms.LowForm;
import writhingmasscharactermod.patches.FormFieldPatch;
import writhingmasscharactermod.util.CardStats;

public class Edgelord extends BaseCard {
    public static final String ID = makeID("Edgelord");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 2;

    public Edgelord() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (FormFieldPatch.form.get(p).ID.equals(LowForm.FORM_ID)) {
            addToBot(new IncreasePlayerMaxHpAction(p, magicNumber, true));
        }
    }
}
