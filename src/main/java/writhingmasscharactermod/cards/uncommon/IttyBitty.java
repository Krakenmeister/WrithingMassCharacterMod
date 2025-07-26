package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.patches.realtemphp.RealTempHPField;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.OnHealthChangedCard;
import writhingmasscharactermod.util.WrithingCard;

public class IttyBitty extends WrithingCard implements OnHealthChangedCard {
    public static final String ID = makeID("IttyBitty");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 15;
    private static final int UPG_MAGIC_NUMBER = 5;

    public IttyBitty() {
        super(ID, info, true);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        this.OnHealthChanged();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new AddRealTemporaryHPAction(source, source, magicNumber));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }

    @Override
    public void OnHealthChanged() {
        if (owner == null) {
            return;
        }

        int newCost = (owner.currentHealth + RealTempHPField.realTempHp.get(owner)) / 10;

        int tmpCost = this.cost;
        int diff = this.cost - this.costForTurn;
        tmpCost = newCost;
        if (tmpCost < 0) {
            tmpCost = 0;
        }

        if (tmpCost != this.cost) {
            this.isCostModified = true;
            this.cost = tmpCost;
            this.costForTurn = this.cost - diff;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
        }
    }
}
