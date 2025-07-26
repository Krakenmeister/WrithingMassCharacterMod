package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.CocoonPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Cocoon extends WrithingCard {
    public static final String ID = makeID("Cocoon");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Cocoon() {
        this(true);
    }

    public Cocoon(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setInert(true);

        setCostUpgrade(0);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(source, source, new CocoonPower(source)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
