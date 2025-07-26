package writhingmasscharactermod.cards.rare;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.ClimaxAction;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;

public class Climax extends WrithingCard {
    public static final String ID = makeID("Climax");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 1;
    private static final int MAGIC_NUMBER = 1;

    private static final int MULTIPLIER = 3;
    private static final int UPG_MULTIPLIER = 1;

    public Climax() {
        this(true, true);
    }

    public Climax(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);
        setInert(true);

        if (previewCards) {
            cardsToPreview = new Climax(!isBenign, false);
        }

        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER);
        setCustomVar("climaxmultiplier", MULTIPLIER, UPG_MULTIPLIER);

        setExhaust(true);
    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        DamageInfo attackDamage = new DamageInfo(m, damage, damageTypeForTurn);
//        addToBot(new ClimaxAction(p, m, attackDamage, customVar("climaxmultiplier"), freeToPlayOnce, energyOnUse));
//    }

    @Override
    public String updateCardText(boolean isBenign) {
        if (isBenign) {
            return cardStrings.DESCRIPTION;
        } else {
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }
    }

    @Override
    public void setBenign(boolean isBenign) {
        super.setBenign(isBenign);

        if (isBenign) {
            this.target = CardTarget.ENEMY;
        } else {
            this.target = CardTarget.SELF;
        }
    }

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        DamageInfo attackDamage = new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL);
        addToBot(new ClimaxAction(source, target, attackDamage, customVar("climaxmultiplier"), freeToPlayOnce, energyOnUse));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        DamageInfo attackDamage = new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL);
        addToBot(new ClimaxAction(source, source, attackDamage, customVar("climaxmultiplier"), freeToPlayOnce, energyOnUse));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (!isBenign) {
            calculateCardDamage(owner);
        } else {
            if (owner instanceof AbstractPlayer) {
                calculateCardDamage((AbstractCreature) m);
            } else {
                calculateCardDamage(AbstractDungeon.player);
            }
        }
    }
}
