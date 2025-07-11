package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.RunicTrianglePower;
import writhingmasscharactermod.util.CardStats;

public class RunicTriangle extends BaseCard {
    public static final String ID = makeID("RunicTriangle");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC_NUMBER = 1;

    public RunicTriangle() {
        super(ID, info);

        setMagic(MAGIC_NUMBER);

        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RunicTrianglePower(p, magicNumber)));
    }
}
