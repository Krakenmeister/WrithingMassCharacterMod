package writhingmasscharactermod.patches.realtemphp;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.tempHp.PlayerDamage;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import javassist.CtBehavior;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class RealTempHPMonsterDamage {
    @SpireInsertPatch(
            locator = ThirdCurrentHealthAccessLocator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        if (damageAmount[0] > 0) {
            for(AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                if (mod.ignoresTempHP(__instance)) {
                    return;
                }
            }

            int temporaryHealth = (Integer) RealTempHPField.realTempHp.get(__instance);
            if (temporaryHealth > 0) {
                for(AbstractPower power : __instance.powers) {
                    if (power instanceof OnLoseTempHpPower) {
                        damageAmount[0] = ((OnLoseTempHpPower)power).onLoseTempHp(info, damageAmount[0]);
                    }
                }

                for(int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(__instance.hb.cX, __instance.hb.cY));
                }

                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
                if (temporaryHealth >= damageAmount[0]) {
                    temporaryHealth -= damageAmount[0];
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, damageAmount[0]));
                    damageAmount[0] = 0;
                } else {
                    damageAmount[0] -= temporaryHealth;
                    AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(__instance, __instance.hb.cX, __instance.hb.cY, temporaryHealth));
                    temporaryHealth = 0;
                }

                RealTempHPField.realTempHp.set(__instance, temporaryHealth);
            }

        }
    }

    public static class ThirdCurrentHealthAccessLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);

            if (matches.length < 3) {
                throw new RuntimeException("Less than 3 accesses to currentHealth found!");
            }

            return new int[] { matches[2] };
        }
    }
}