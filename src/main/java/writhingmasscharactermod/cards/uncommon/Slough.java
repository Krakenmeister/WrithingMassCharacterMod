package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.RealMalleablePower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Slough extends WrithingCard {
    public static final String ID = makeID("Slough");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Slough() {
        this(true);
    }

    public Slough(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setInert(true);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(source, source, new RealMalleablePower(source, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
