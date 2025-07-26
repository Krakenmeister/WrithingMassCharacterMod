package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.CannibalismPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Cannibalism extends WrithingCard {
    public static final String ID = makeID("Cannibalism");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Cannibalism() {
        this(true);
    }

    public Cannibalism(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setInert(true);

        setInnate(false, true);
        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        String description = "";
        if (upgraded) {
            description += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        description += cardStrings.DESCRIPTION;
        return description;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(source, source, new CannibalismPower(source)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }
}
