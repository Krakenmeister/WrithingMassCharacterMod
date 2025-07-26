package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.CoilLashPower;
import writhingmasscharactermod.util.CardStats;

public class CoilLash extends BaseCard {
    public static final String ID = makeID("CoilLash");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;

    public CoilLash() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        cardsToPreview = new Whip();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CoilLashPower(p, magicNumber)));
    }
}
