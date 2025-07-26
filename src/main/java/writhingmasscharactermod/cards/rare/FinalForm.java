package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import writhingmasscharactermod.actions.SetHealthAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class FinalForm extends WrithingCard {
    public static final String ID = makeID("FinalForm");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 8;
    private static final int UPG_MAGIC_NUMBER = 3;

    public FinalForm() {
        this(true, true);
    }

    public FinalForm(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new FinalForm(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
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
        addToBot(new SetHealthAction(source, 1));
        addToBot(new ApplyPowerAction(source, source, new StrengthPower(source, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new HealAction(source, source, 999));
        addToBot(new ApplyPowerAction(source, source, new StrengthPower(source, -1 * magicNumber)));
    }
}
