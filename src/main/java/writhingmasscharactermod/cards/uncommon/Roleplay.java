package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import writhingmasscharactermod.actions.RoleplayAction;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class Roleplay extends BaseCard {
    public static final String ID = makeID("Roleplay");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            -1
    );

    private static final int DAMAGE = 1;
    private static final int MAGIC_NUMBER = 1;

    private static final int MULTIPLIER = 3;
    private static final int UPG_MULTIPLIER = 1;

    public Roleplay() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER);
        setCustomVar("roleplaymultiplier", MULTIPLIER, UPG_MULTIPLIER);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo selfDamage = new DamageInfo(p, magicNumber, damageTypeForTurn);
        DamageInfo attackDamage = new DamageInfo(m, damage, damageTypeForTurn);
        addToBot(new RoleplayAction(p, m, selfDamage, attackDamage, customVar("roleplaymultiplier"), freeToPlayOnce, energyOnUse));
    }

    /*@Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = GeneralUtils.applyDamageCalculations(AbstractDungeon.player, DamageInfo.DamageType.NORMAL, baseMagicNumber);
        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        magicNumber = GeneralUtils.applyDamageCalculations(AbstractDungeon.player, DamageInfo.DamageType.NORMAL, baseMagicNumber);
        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }*/
}
