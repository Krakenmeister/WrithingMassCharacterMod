package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.BleedPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;


public class Spank extends WrithingCard {
    public static final String ID = makeID("Spank");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Spank() {
        this(true, true);
    }

    public Spank(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Spank(!isBenign, false);
        }

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
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
    public void setBenign(boolean isBenign) {
        super.setBenign(isBenign);

        if (isBenign) {
            this.target = CardTarget.ENEMY;
        } else {
            this.target = CardTarget.SELF_AND_ENEMY;
        }
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

    @Override
    public void benignUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(target, source, new BleedPower(target, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new DamageAction(source, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(target, source, new BleedPower(target, magicNumber)));
    }
}
