package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import writhingmasscharactermod.actions.SetHealthAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class FinalForm extends BaseCard {
    public static final String ID = makeID("FinalForm");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int INTANGIBLE = 1;

    public FinalForm() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("finalformintangible", INTANGIBLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SetHealthAction(p, 1));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, customVar("finalformintangible")), customVar("finalformintangible")));
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, magicNumber), magicNumber));
    }
}
