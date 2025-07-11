package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class Kiss extends BaseCard {
    public static final String ID = makeID("Kiss");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;

    private static final int KISS_GAIN = 1;

    public Kiss() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("kissgain", KISS_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, customVar("kissgain"))));
    }
}
