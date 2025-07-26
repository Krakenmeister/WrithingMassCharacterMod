package writhingmasscharactermod.cards.uncommon;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class TenTickles extends BaseCard {
    public static final String ID = makeID("TenTickles");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public TenTickles() {
        super(ID, info);

        setCostUpgrade(0);

        cardsToPreview = new Whip();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int whipsToAdd = BaseMod.MAX_HAND_SIZE - p.hand.group.size() + 1;
        addToBot(new MakeTempCardInHandAction(new Whip(), whipsToAdd));
    }
}
