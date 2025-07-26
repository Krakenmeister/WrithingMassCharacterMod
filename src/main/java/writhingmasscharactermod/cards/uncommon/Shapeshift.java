package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.actions.MutateAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Shapeshift extends WrithingCard {
    public static final String ID = makeID("Shapeshift");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 3;

    private static final int MUTATE_NUMBER = 2;
    private static final int UPG_MUTATE_NUMBER = 0;

    public Shapeshift() {
        super(ID, info, true);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setCustomVar("shapeshiftmutate", MUTATE_NUMBER, UPG_MUTATE_NUMBER);

        setInnate(true);
        setExhaust(true);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new AddRealTemporaryHPAction(source, source, magicNumber));
        if (owner instanceof AbstractPlayer) {
            addToBot(new MutateAction(customVar("shapeshiftmutate"), true));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }
}
