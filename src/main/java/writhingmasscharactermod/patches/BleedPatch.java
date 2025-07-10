package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import writhingmasscharactermod.patches.realtemphp.RealTempHPPlayerDamage;
import writhingmasscharactermod.util.OnLoseMonsterHpPower;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class BleedPatch {
    @SpireInsertPatch(
            locator = BeforeUseStaggerIfBlockLocator.class,
            localvars = {"damageAmount"}
    )
    public static void Insert(AbstractMonster __instance, DamageInfo info, @ByRef int[] damageAmount) {
        for(AbstractPower power : __instance.powers) {
            if (power instanceof OnLoseMonsterHpPower) {
                damageAmount[0] = ((OnLoseMonsterHpPower)power).onLoseMonsterHp(info, damageAmount[0]);
            }
        }
    }

    public static class BeforeUseStaggerIfBlockLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractMonster.class,
                    "useStaggerAnimation"
            );

            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);

            if (lines.length == 0) {
                throw new RuntimeException("Could not find useStaggerAnimation() call");
            }

            return new int[] { lines[0] - 1 };
        }
    }
}



