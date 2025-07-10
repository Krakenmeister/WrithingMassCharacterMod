package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.patches.realtemphp.RealTempHPField;
import writhingmasscharactermod.util.CardStats;

public class Mitosis extends BaseCard {
    public static final String ID = makeID("Mitosis");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Mitosis() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentHealth = p.currentHealth;
        int currentTempHealth = RealTempHPField.realTempHp.get(p);
        if (upgraded) {
            addToBot(new AddRealTemporaryHPAction(p, p, currentTempHealth));
        }
        addToBot(new HealAction(p, p, currentHealth));
    }
}
