package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.InfestAnyMonsterAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.OnInfestedCard;
import writhingmasscharactermod.util.WrithingCard;

public class Proliferate extends WrithingCard implements OnInfestedCard {
    public static final String ID = makeID("Proliferate");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -2
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Proliferate() {
        super(ID, info, true);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {

    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (isInfesting) return true;

        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void OnInfested() {
        addToBot(new InfestAnyMonsterAction());
    }
}
