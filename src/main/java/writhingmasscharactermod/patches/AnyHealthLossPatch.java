package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import writhingmasscharactermod.util.OnAnyHealthLossPower;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class AnyHealthLossPatch {
    @SpireInsertPatch(
            locator = BeforeRelicOnAttackedLocator.class,
            localvars = {"damageAmount"}
    )
    public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
        int tempHpBefore = TempHPTracker.tempHpBefore;
        int tempHpAfter = TempHPField.tempHp.get(__instance);

        for (AbstractPower power : __instance.powers) {
            if (power instanceof OnAnyHealthLossPower) {
                ((OnAnyHealthLossPower) power).onAnyHealthLoss(info, damageAmount[0], tempHpAfter < tempHpBefore);
            }
        }
    }

    public static class BeforeRelicOnAttackedLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractRelic.class,
                    "onAttackedToChangeDamage"
            );

            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
            if (lines.length == 0) {
                throw new RuntimeException("Could not find onAttackedToChangeDamage!");
            }

            // Insert just before the for loop
            return new int[] { lines[0] - 1 };
        }
    }
}
