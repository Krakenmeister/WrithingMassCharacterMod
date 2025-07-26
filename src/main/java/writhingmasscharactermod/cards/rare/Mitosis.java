package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.patches.realtemphp.RealTempHPField;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Mitosis extends WrithingCard {
    public static final String ID = makeID("Mitosis");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Mitosis() {
        this(true);
    }

    public Mitosis(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);

        setCostUpgrade(1);

        setExhaust(true);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        int currentHealth = source.currentHealth;
        int currentTempHealth = RealTempHPField.realTempHp.get(source);

        addToBot(new AddRealTemporaryHPAction(source, source, currentTempHealth));
        addToBot(new HealAction(source, source, currentHealth));
//        int currentBlock = p.currentBlock;
//        if (upgraded) {
//            addToBot(new GainBlockAction(p, p, currentBlock));
//        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}
