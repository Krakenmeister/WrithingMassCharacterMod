package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.LashPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.WrithingCard;


public class Whip extends WrithingCard {
    public static final String ID = makeID("Whip");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 2;

    public Whip() {
        this(true, true);
    }

    public Whip(boolean isBenign, boolean previewCards) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Whip(!isBenign, calculateWhipDamage());
        }

        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    public Whip(boolean isBenign, boolean previewCards, boolean shouldExhaust) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        if (previewCards) {
            cardsToPreview = new Whip(!isBenign, calculateWhipDamage(), shouldExhaust);
        }

        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        if (shouldExhaust) {
            setExhaust(true);
            this.rawDescription = updateCardText(isBenign);
            this.initializeDescription();
        }
    }

    public Whip(boolean isBenign, int baseDamage) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        setDamage(baseDamage);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    public Whip(boolean isBenign, int baseDamage, boolean shouldExhaust) {
        super(ID, info, isBenign);

        setBenign(isBenign);
        setMutable(true);

        setDamage(baseDamage);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        if (shouldExhaust) {
            setExhaust(true);
            this.rawDescription = updateCardText(isBenign);
            this.initializeDescription();
        }
    }

    public void makeExhaust() {
        setExhaust(true);
        cardsToPreview = new Whip(!isBenign, calculateWhipDamage(), true);
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

        if (exhaust) {
            description += cardStrings.EXTENDED_DESCRIPTION[1];
        }

        return description;
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
        addToBot(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LashPower(AbstractDungeon.player, magicNumber)));
    }

    @Override
    public void malignantUse(AbstractCreature source, AbstractCreature target) {
        addToBot(new DamageAction(source, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LashPower(AbstractDungeon.player, magicNumber)));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = calculateWhipDamage();

        if (cardsToPreview != null) {
            cardsToPreview.applyPowers();
        }

        super.applyPowers();
    }

    private int calculateWhipDamage() {
        if (AbstractDungeon.player == null) {
            return DAMAGE;
        }

        if (AbstractDungeon.player.hasPower(LashPower.POWER_ID)) {
            return DAMAGE + AbstractDungeon.player.getPower(LashPower.POWER_ID).amount;
        }

        return DAMAGE;
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        this.baseDamage = calculateWhipDamage();

        if (cardsToPreview != null) {
            cardsToPreview.baseDamage = calculateWhipDamage();
        }

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
