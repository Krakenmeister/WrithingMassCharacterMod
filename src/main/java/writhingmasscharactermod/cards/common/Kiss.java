package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Kiss extends WrithingCard {
    public static final String ID = makeID("Kiss");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int KISS_GAIN = 1;

    public Kiss() {
        this(true, true);
    }

    public Kiss(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Kiss(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("kissgain", KISS_GAIN);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (isBenign) {
            if (!upgraded) {
                return cardStrings.DESCRIPTION;
            } else {
                return cardStrings.UPGRADE_DESCRIPTION;
            }
        } else {
            if (!upgraded) {
                return cardStrings.EXTENDED_DESCRIPTION[0];
            } else {
                return cardStrings.EXTENDED_DESCRIPTION[1];
            }
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        if (owner instanceof AbstractPlayer) {
            addToBot(new GainEnergyAction(magicNumber));
        }
        addToBot(new ApplyPowerAction(target, source, new StrengthPower(target, customVar("kissgain"))));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        if (owner instanceof AbstractPlayer) {
            addToBot(new LoseEnergyAction(magicNumber));
        }
        addToBot(new ApplyPowerAction(target, source, new StrengthPower(target, customVar("kissgain"))));
    }
}
