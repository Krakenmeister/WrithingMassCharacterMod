package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.PowerBottomAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class PowerBottom extends WrithingCard {
    private static final CardStrings cardStrings;
    public static final String ID = makeID("PowerBottom");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 2;

    public PowerBottom() {
        this(true, true);
    }

    public PowerBottom(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new PowerBottom(!isBenign, false);
        }

        baseDamage = 0;
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        baseDamage = AbstractDungeon.player.discardPile.size() * 2;
        calculateCardDamage(target);

        if (owner instanceof AbstractPlayer) {
            addToBot(new ScryAction(magicNumber));
        }

        addToBot(new PowerBottomAction(source, target, this));

        initializeDescription();
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        baseDamage = AbstractDungeon.player.discardPile.size() * 2;
        calculateCardDamage(target);

        if (owner instanceof AbstractPlayer) {
            addToBot(new DiscardAction(source, source, magicNumber, false));
        }

        addToBot(new PowerBottomAction(source, target, this));

        initializeDescription();
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
    public void calculateCardDamage(AbstractMonster m) {
        if (owner instanceof AbstractPlayer) {
            calculateCardDamage((AbstractCreature) m);
        } else {
            calculateCardDamage(AbstractDungeon.player);
        }
    }

    public void calculateCardDamage(AbstractCreature target) {
        baseDamage = AbstractDungeon.player.discardPile.size() * 2;

        super.calculateCardDamage(target);

        rawDescription = updateCardText(isBenign) + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.discardPile.size() * 2;

        super.applyPowers();

        rawDescription = updateCardText(isBenign) + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new PowerBottom();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
