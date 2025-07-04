package writhingmasscharactermod.powers;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class ToxicRecoilPower extends BasePower {
    public static final String POWER_ID = makeID("ToxicRecoil");
    private static final boolean TURN_BASED = false;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    public ToxicRecoilPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            addToBot(new ApplyPowerAction(info.owner, owner, new PoisonPower(info.owner, owner, amount), amount, AbstractGameAction.AttackEffect.POISON));
        }

        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}

