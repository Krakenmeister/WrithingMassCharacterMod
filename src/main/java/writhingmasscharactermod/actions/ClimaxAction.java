package writhingmasscharactermod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ClimaxAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractCreature source;
    private AbstractCreature target;
    private DamageInfo attackDamage;
    private int energyOnUse = -1;
    private float timer;
    private int actionCounter;

    public ClimaxAction(AbstractCreature source, AbstractCreature target, DamageInfo attackDamage, int amount, boolean freeToPlayOnce, int energyOnUse) {
        this.source = source;
        this.target = target;
        this.attackDamage = attackDamage;

        this.amount = amount;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;

        this.timer = 0.1F;
        this.actionCounter = 0;
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

        if (actionCounter < amount * effect) {
            if (timer > 0F) {
                timer -= Gdx.graphics.getDeltaTime();
            } else {
                timer = 0.1F;
                if (this.target.currentHealth > 0) {
                    this.target.damage(attackDamage);
                }
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                    this.isDone = true;
                }
                actionCounter++;
            }
        } else {
            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
            this.isDone = true;
        }


//        if (effect > 0) {
//            for (int i = 0; i < amount * effect; ++i) {
//                if (timer > 0F) {
//                    timer -= Gdx.graphics.getDeltaTime();
//                } else {
//                    timer = 0.1F;
//                    if (this.target.currentHealth > 0) {
//                        this.target.damage(attackDamage);
//                    }
//                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
//                        AbstractDungeon.actionManager.clearPostCombatActions();
//                    }
//                }
//
//            }
//
////            for(int i = 0; i < amount * effect; ++i) {
////                addToTop(new DamageAction(target, attackDamage, true));
////            }
//
//            if (!this.freeToPlayOnce) {
//                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
//            }
//        }
//
//        this.isDone = true;
    }
}