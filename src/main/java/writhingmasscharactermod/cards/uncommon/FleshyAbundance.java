package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.FleshyAbundanceAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class FleshyAbundance extends WrithingCard {
    public static final String ID = makeID("FleshyAbundance");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC_NUMBER = 6;
    private static final int UPG_MAGIC_NUMBER = 3;

    public FleshyAbundance() {
        this(true, true);
    }

    public FleshyAbundance(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);
        setInert(true);

        if (previewCards) {
            cardsToPreview = new FleshyAbundance(!isBenign, false);
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
        addToBot(new FleshyAbundanceAction((AbstractPlayer) source, magicNumber, freeToPlayOnce, energyOnUse));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new FleshyAbundanceAction((AbstractPlayer) source, -1 * magicNumber, freeToPlayOnce, energyOnUse));
    }
}