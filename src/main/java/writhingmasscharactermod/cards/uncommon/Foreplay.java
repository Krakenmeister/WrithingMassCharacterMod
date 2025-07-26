package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.ForeplayAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Foreplay extends WrithingCard {
    public static final String ID = makeID("Foreplay");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int FOREPLAY_MULTIPLIER = 2;

    public Foreplay() {
        this(true, true);
    }

    public Foreplay(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);
        setInert(true);

        if (previewCards) {
            cardsToPreview = new Foreplay(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("foreplaymultiplier", FOREPLAY_MULTIPLIER);
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
        addToBot(new ForeplayAction((AbstractPlayer) source, magicNumber, customVar("foreplaymultiplier"), freeToPlayOnce, energyOnUse));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ForeplayAction((AbstractPlayer) source, magicNumber, -1 * customVar("foreplaymultiplier"), freeToPlayOnce, energyOnUse));
    }
}
