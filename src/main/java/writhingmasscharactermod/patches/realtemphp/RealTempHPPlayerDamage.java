package writhingmasscharactermod.patches.realtemphp;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class RealTempHPPlayerDamage {
    static boolean hadRealTempHp;

    @SpireInsertPatch(
            locator = SecondCurrentHealthAccessLocator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        hadRealTempHp = false;
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

                if (__instance instanceof AbstractPlayer) {
                    for(AbstractRelic relic : ((AbstractPlayer)__instance).relics) {
                        if (relic instanceof OnLoseTempHpRelic) {
                            damageAmount[0] = ((OnLoseTempHpRelic)relic).onLoseTempHp(info, damageAmount[0]);
                        }
                    }
                }

                hadRealTempHp = true;

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

    @SpireInsertPatch(
            locator = StrikeEffectLocator.class
    )
    public static SpireReturn<Void> Insert(AbstractCreature __instance, DamageInfo info) {
        return hadRealTempHp ? SpireReturn.Return((Void) null) : SpireReturn.Continue();
    }

    public static class SecondCurrentHealthAccessLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "currentHealth");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);

            if (matches.length < 2) {
                throw new RuntimeException("Less than 2 accesses to currentHealth found!");
            }

            return new int[] { matches[1] };
        }
    }

    private static class StrikeEffectLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(StrikeEffect.class);
            int[] all = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            return new int[]{all[all.length - 1]};
        }
    }
}