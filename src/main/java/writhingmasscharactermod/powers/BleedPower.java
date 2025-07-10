package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnLoseMonsterHpPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class BleedPower extends BasePower implements OnLoseMonsterHpPower {
    public static final String POWER_ID = makeID("Bleed");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public BleedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        if (this.amount >= 9999) {
            this.amount = 9999;
        }
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        System.out.println("RECEIVING DAMAGE: " + damage);
        if ((type == DamageInfo.DamageType.HP_LOSS && damage > 0F) || damage > owner.currentBlock) {
            damage += amount;
        }

        return damage;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        return super.onLoseHp(damageAmount) + amount;
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public int onLoseMonsterHp(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL) {
            return damageAmount + amount;
        }
        return damageAmount;
    }
}
