package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.MutateAction;
import writhingmasscharactermod.actions.NaughtyAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.FunishmentPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Funishment extends WrithingCard {
    public static final String ID = makeID("Funishment");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 5;
    private static final int UPG_MAGIC_NUMBER = 3 ;

    public Funishment() {
        this(true, true);
    }

    public Funishment(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Funishment(!isBenign, false);
        }

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void setBenign(boolean isBenign) {
        super.setBenign(isBenign);

        if (isBenign) {
            this.target = CardTarget.SELF;
        } else {
            this.target = CardTarget.ENEMY;
        }
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
        if (owner instanceof AbstractPlayer) {
            addToBot(new ApplyPowerAction(source, source, new FunishmentPower(source, source, magicNumber)));
        } else if (owner instanceof AbstractMonster) {
            owner.addPower(new FunishmentPower(source, source, magicNumber));
        }
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        if (owner instanceof AbstractPlayer) {
            addToBot(new ApplyPowerAction(target, source, new FunishmentPower(target, source, magicNumber)));
        } else if (owner instanceof AbstractMonster) {
            owner.addPower(new FunishmentPower(target, source, magicNumber));
        }
    }
}
