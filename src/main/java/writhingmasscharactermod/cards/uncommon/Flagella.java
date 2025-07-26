package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.InfestCardAction;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Flagella extends WrithingCard {
    public static final String ID = makeID("Flagella");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Flagella() {
        this(true);
    }

    public Flagella(boolean isBenign) {
        super(ID, info, true);

        setBenign(isBenign);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        cardsToPreview = new Whip(false, false, true);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new InfestCardAction((AbstractMonster) target, new Whip(false, true, true)));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
