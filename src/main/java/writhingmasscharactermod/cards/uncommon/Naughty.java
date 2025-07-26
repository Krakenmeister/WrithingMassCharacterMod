package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.MutateAction;
import writhingmasscharactermod.actions.NaughtyAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Naughty extends WrithingCard {
    public static final String ID = makeID("Naughty");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int NAUGHTY_MUTATE = 3;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 2;

    public Naughty() {
        this(true);
    }

    public Naughty(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);

        setCustomVar("naughtymutate", NAUGHTY_MUTATE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        if (owner instanceof AbstractPlayer) {
            addToBot(new MutateAction(customVar("naughtymutate"), true));
        }
        addToBot(new NaughtyAction(source, magicNumber));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
