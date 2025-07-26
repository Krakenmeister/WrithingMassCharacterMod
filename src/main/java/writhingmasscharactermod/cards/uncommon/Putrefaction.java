package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Putrefaction extends WrithingCard {
    public static final String ID = makeID("Putrefaction");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 4;

    public Putrefaction() {
        this(true, true);
    }

    public Putrefaction(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Putrefaction(!isBenign, false);
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
        int targetPoison = 0;
        if (target.hasPower(PoisonPower.POWER_ID)) {
            targetPoison = target.getPower(PoisonPower.POWER_ID).amount;
        }

        addToBot(new AddRealTemporaryHPAction(source, source, targetPoison + magicNumber));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        int targetPoison = 0;
        if (target.hasPower(PoisonPower.POWER_ID)) {
            targetPoison = target.getPower(PoisonPower.POWER_ID).amount;
        }

        addToBot(new ApplyPowerAction(source, source, new PoisonPower(source, source, targetPoison + magicNumber)));
    }
}
