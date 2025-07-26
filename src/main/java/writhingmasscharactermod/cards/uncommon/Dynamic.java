package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.MutualismPower;
import writhingmasscharactermod.powers.SoulBondPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;


public class Dynamic extends WrithingCard {
    public static final String ID = makeID("Dynamic");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Dynamic() {
        this(true, true);
    }

    public Dynamic(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setInert(true);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Dynamic(!isBenign, false);
        }

        setExhaust(true, false);

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        super.upgrade();

        this.rawDescription = updateCardText(isBenign);
        this.initializeDescription();
    }

    @Override
    public String updateCardText(boolean isBenign) {
        String description = "";
        if (isBenign) {
            description += cardStrings.DESCRIPTION;
        } else {
            description += cardStrings.EXTENDED_DESCRIPTION[0];
        }

        if (!upgraded) {
            description += cardStrings.EXTENDED_DESCRIPTION[1];
        }

        return description;
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, source, new SoulBondPower(target, 1)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, source, new MutualismPower(target, 1)));
    }
}
