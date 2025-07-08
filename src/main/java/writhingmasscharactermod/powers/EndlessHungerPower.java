package writhingmasscharactermod.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import writhingmasscharactermod.util.OnEnemyDeathPower;

import static writhingmasscharactermod.WrithingMassCharacterMod.makeID;

public class EndlessHungerPower extends BasePower implements OnEnemyDeathPower {
    public static final String POWER_ID = makeID("EndlessHunger");
    private static final AbstractPower.PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURN_BASED = false;

    public EndlessHungerPower(AbstractCreature owner, int maxHpLoss, int maxHpGain) {
        super(POWER_ID, TYPE, TURN_BASED, owner, maxHpGain);

        this.amount2 = maxHpLoss;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.player.decreaseMaxHealth(amount2);
    }

    @Override
    public void onEnemyDeath(AbstractMonster m) {
        AbstractDungeon.player.increaseMaxHp(amount, true);
    }
}