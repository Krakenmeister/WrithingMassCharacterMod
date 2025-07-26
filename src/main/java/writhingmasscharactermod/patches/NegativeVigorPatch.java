package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.CtBehavior;

@SpirePatch2(
        clz = VigorPower.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCreature.class, int.class }
)
public class NegativeVigorPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(VigorPower __instance) {
        __instance.canGoNegative = true;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctConstructor) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(VigorPower.class, "updateDescription");
            return LineFinder.findInOrder(ctConstructor, matcher);
        }
    }
}
