package writhingmasscharactermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ClimaxAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractCreature source;
    private AbstractCreature target;
    private DamageInfo selfDamage;
    private DamageInfo attackDamage;
    private int energyOnUse = -1;

    public ClimaxAction(AbstractCreature source, AbstractCreature target, DamageInfo selfDamage, DamageInfo attackDamage, int amount, boolean freeToPlayOnce, int energyOnUse) {
        this.source = source;
        this.target = target;
        this.selfDamage = selfDamage;
        this.attackDamage = attackDamage;

        this.amount = amount;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            effect += 2;
            AbstractDungeon.player.getRelic("Chemical X").flash();
        }

        if (effect > 0) {
            for(int i = 0; i < amount * effect; ++i) {
                addToTop(new DamageAction(target, attackDamage, true));
            }
            /*for(int i = 0; i < amount * effect; ++i) {
                addToTop(new DamageAction(source, selfDamage, true));
            }*/

            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}