package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.AddRealTemporaryHPAction;
import writhingmasscharactermod.actions.OsmosisAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Osmosis extends WrithingCard {
    public static final String ID = makeID("Osmosis");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 3;

    public Osmosis() {
        this(true);
    }

    public Osmosis(boolean isBenign) {
        super(ID, info, isBenign);

        setBenign(isBenign);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (upgraded) {
            return cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
        }

        return cardStrings.DESCRIPTION;
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        if (magicNumber > 0) {
            addToBot(new GainBlockAction(source, magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new OsmosisAction(source));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        benignUse(source, target);
    }

}