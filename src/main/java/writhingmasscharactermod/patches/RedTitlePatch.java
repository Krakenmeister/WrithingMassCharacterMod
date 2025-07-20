package writhingmasscharactermod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CtBehavior;
import writhingmasscharactermod.util.WrithingCard;

@SpirePatch2(
        clz= AbstractCard.class,
        method="renderTitle"
)
public class RedTitlePatch {
    @SpireInsertPatch(
            locator=Locator.class,
            localvars = {"color"}
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb, Color color) {
        if (__instance instanceof WrithingCard) {
            if (!((WrithingCard)__instance).isBenign) {
                color.set(Settings.RED_TEXT_COLOR.cpy());
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(Color.class, "a");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
