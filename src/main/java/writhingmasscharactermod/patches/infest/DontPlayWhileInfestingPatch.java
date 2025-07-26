package writhingmasscharactermod.patches.infest;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import writhingmasscharactermod.util.WrithingCard;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="playCard"
)
public class DontPlayWhileInfestingPatch {
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static SpireReturn<Void> Insert(AbstractPlayer __instance) {
        if (__instance.hoveredCard instanceof WrithingCard) {
            if (((WrithingCard) __instance.hoveredCard).isInfesting) {
                ((WrithingCard) __instance.hoveredCard).isInfesting = false;
                __instance.hoveredCard = null;
                __instance.isDraggingCard = false;
                return SpireReturn.Return();
            }
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "queueContains");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
