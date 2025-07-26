package writhingmasscharactermod.cards.basic;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Defend extends WrithingCard {
    public static final String ID = makeID("Defend");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public Defend() {
        this(true, true);
    }

    public Defend(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Defend(!isBenign, false);
        }

        setBlock(BLOCK, UPG_BLOCK);

        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new GainBlockAction(source, source, block));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new LoseBlockAction(source, source, block));
    }
}
