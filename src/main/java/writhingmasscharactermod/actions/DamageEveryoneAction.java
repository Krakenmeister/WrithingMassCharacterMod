package writhingmasscharactermod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DamageEveryoneAction extends AbstractGameAction {
    private int[] damage;
    private int baseDamage;
    private boolean firstFrame = true;
    private boolean useBaseDamage;

    public DamageEveryoneAction(AbstractCreature source, int[] damageMatrix, DamageInfo.DamageType type, AttackEffect effect) {
        this.source = source;
        this.damage = damageMatrix;
        this.damageType = type;
        this.attackEffect = effect;
        this.useBaseDamage = false;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public DamageEveryoneAction(AbstractCreature source, int baseDamage, DamageInfo.DamageType type, AttackEffect effect) {
        this(source, null, type, effect);
        this.baseDamage = baseDamage;
        this.useBaseDamage = true;
    }

    @Override
    public void update() {
        if (firstFrame) {
            if (useBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage, true);
            }

            boolean playedSfx = false;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    if (!playedSfx) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, attackEffect));
                        playedSfx = true;
                    } else {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, attackEffect, true));
                    }
                }
            }

            // Optional: add a flash effect for the player too
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, attackEffect, true));

            firstFrame = false;
        }

        tickDuration();
        if (isDone) {
            // Notify player powers
            if (source instanceof AbstractPlayer) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    p.onDamageAllEnemies(this.damage);
                }
            }

            // Damage all monsters
            int i = 0;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    applyAttackTint(m, attackEffect);
                    m.damage(new DamageInfo(source, damage[i], damageType));
                }
                i++;
            }

            // Damage the player
            if (!AbstractDungeon.player.isDeadOrEscaped()) {
                applyAttackTint(AbstractDungeon.player, attackEffect);
                AbstractDungeon.player.damage(new DamageInfo(source, damage[i], damageType));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(0.1f));
            }
        }
    }

    private void applyAttackTint(AbstractCreature target, AttackEffect effect) {
        if (effect == AttackEffect.POISON) {
            target.tint.color.set(Color.CHARTREUSE);
        } else if (effect == AttackEffect.FIRE) {
            target.tint.color.set(Color.RED);
        }
        target.tint.changeColor(Color.WHITE.cpy());
    }
}
