package writhingmasscharactermod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import writhingmasscharactermod.cards.BaseCard;
import writhingmasscharactermod.character.WrithingMassCharacter;
import writhingmasscharactermod.powers.SoulBondPower;
import writhingmasscharactermod.util.CardStats;
import writhingmasscharactermod.util.GeneralUtils;

public class MassDestruction extends BaseCard {
    public static final String ID = makeID("MassDestruction");
    private static final CardStats info = new CardStats(
            WrithingMassCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            3
    );

    public MassDestruction() {
        super(ID, info);

        try {
            setDamage(AbstractDungeon.player.currentHealth);
        } catch (Exception e) {
            setDamage(WrithingMassCharacter.MAX_HP);
        }

        setEthereal(true);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }

        addToBot(new WaitAction(0.8F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        try {
            setDamage(AbstractDungeon.player.currentHealth);
        } catch (Exception e) {
            setDamage(WrithingMassCharacter.MAX_HP);
        }

        super.calculateCardDamage(mo);
    }

    public void externalSetDamage(int damage) {
        setDamage(damage);
    }
}
