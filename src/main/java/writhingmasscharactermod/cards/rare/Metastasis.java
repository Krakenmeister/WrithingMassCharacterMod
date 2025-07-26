package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Metastasis extends WrithingCard {
    public static final String ID = makeID("Metastasis");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Metastasis() {
        this(true, true);
    }

    public Metastasis(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Metastasis(!isBenign, false);
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
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DuplicationPower(AbstractDungeon.player, magicNumber), magicNumber));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ExhaustAction(magicNumber, false, false, false));
    }
}
