package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;
import writhingmasscharactermod.cards.common.Whip;
import writhingmasscharactermod.util.WrithingCard;

@SpirePatch2(
        clz = AbstractCard.class,
        method = "makeStatEquivalentCopy"
)
public class StatEquivalentCopyPatch {
    @SpireInsertPatch(locator = Locator.class, localvars = {"card"})
    public static void Insert(AbstractCard __instance, AbstractCard card) {
        if (card instanceof WrithingCard && __instance instanceof WrithingCard) {
            WrithingCard copy = (WrithingCard) card;
            WrithingCard original = (WrithingCard) __instance;

            copy.setOwner(original.owner);
            copy.setBenign(original.isBenign);
            copy.isMutable = original.isMutable;
            copy.exhaust = original.exhaust;
            if (copy instanceof Whip && original.exhaust) {
                ((Whip) copy).makeExhaust();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // Match access to 'freeToPlayOnce', the last field before return
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
