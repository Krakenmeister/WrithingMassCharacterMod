package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;
import writhingmasscharactermod.util.OnBlockCard;

@SpirePatch2(
        clz= GainBlockAction.class,
        method="update"
)
public class SuperDexterityPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(GainBlockAction __instance) {
        if (__instance.target.isPlayer) {
            for(AbstractCard card : ((AbstractPlayer)__instance.target).hand.group) {
                if (card instanceof OnBlockCard) {
                    __instance.amount = ((OnBlockCard)card).onBlock(__instance.amount);
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "addBlock");

            int[] lines = LineFinder.findInOrder(ctBehavior, matcher);

            if (lines.length == 0) {
                throw new RuntimeException("Could not find call to addBlock(...)");
            }

            return new int[]{ lines[0] };
        }
    }
}
