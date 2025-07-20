package writhingmasscharactermod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;
import writhingmasscharactermod.util.WrithingCard;

@SpirePatch2(
        clz= AbstractCard.class,
        method="renderTitle"
)
public class RedTitlePatch2 {
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static SpireReturn<Void> Insert(AbstractCard __instance, SpriteBatch sb) {
        if (__instance instanceof WrithingCard) {
            Color titleColor = Color.WHITE.cpy();
            if (!((WrithingCard)__instance).isBenign) {
                titleColor.set(Settings.RED_TEXT_COLOR.cpy());
            }
            FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, __instance.name, __instance.current_x, __instance.current_y, 0.0F, 175.0F * __instance.drawScale * Settings.scale, __instance.angle, false, titleColor);
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText");
            int[] allMatches = LineFinder.findAllInOrder(ctMethodToPatch, matcher);
            return new int[] {allMatches[3]}; // 4th call to renderRotatedText
        }
    }
}
