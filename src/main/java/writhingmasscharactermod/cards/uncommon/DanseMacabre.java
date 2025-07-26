package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.DanseMacabreMalignantPower;
import writhingmasscharactermod.powers.DanseMacabrePower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class DanseMacabre extends WrithingCard {
    public static final String ID = makeID("DanseMacabre");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 1;

    public DanseMacabre() {
        this(true);
    }

    public DanseMacabre(boolean isBenign) {
        super(ID, info, isBenign);

        upgradePreviews = false;

        setBenign(isBenign);
        setInert(true);
        setMutable(true);

        cardsToPreview = new Whip(isBenign, false, true);

        setInnate(false, true);
        setMagic(MAGIC_NUMBER);
        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        String description = "";
        if (upgraded) {
            description += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        if (isBenign) {
            description += cardStrings.DESCRIPTION;
        } else {
            description += cardStrings.EXTENDED_DESCRIPTION[1];
        }
        return description;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(source, source, new DanseMacabrePower(source, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(source, source, new DanseMacabreMalignantPower(source, magicNumber)));
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public void setBenign(boolean isBenign) {
        this.isBenign = isBenign;
        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
        this.initializeTitle();

        if (cardsToPreview != null) {
            if (cardsToPreview instanceof WrithingCard) {
                ((WrithingCard)cardsToPreview).setBenign(isBenign);
            }
        }
    }
}
