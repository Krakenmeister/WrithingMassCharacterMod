package writhingmasscharactermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnLoseMonsterHpPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class ProboscisPower extends BasePower implements OnLoseMonsterHpPower {
    private final AbstractCreature benefactor;

    public static final String POWER_ID = makeID("Proboscis");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public ProboscisPower(AbstractCreature owner, AbstractCreature benefactor, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        this.benefactor = benefactor;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int onLoseMonsterHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToTop(new HealAction(benefactor, benefactor, amount));
        }
        return damageAmount;
    }

    /*public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            addToTop(new HealAction(benefactor, benefactor, amount));
        }

        return damageAmount;
    }*/
}
