package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.GeneralUtils;

public class Bloom extends BaseCard {
    public static final String ID = makeID("Bloom");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC_NUMBER = 6;
    private static final int UPG_MAGIC_NUMBER = 4;

    private static final int REGEN = 4;
    private static final int UPG_REGEN = 1;

    public Bloom() {
        super(ID, info);

        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        setCustomVar("bloomregen", REGEN, UPG_REGEN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, customVar("bloomregen"))));
    }

    @Override
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
    }
}
