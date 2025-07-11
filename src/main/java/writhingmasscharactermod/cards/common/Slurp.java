package writhingmasscharactermod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.util.CardStats;

public class Slurp extends BaseCard {
    public static final String ID = makeID("Slurp");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF_AND_ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 1;

    public Slurp() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
    }
}
