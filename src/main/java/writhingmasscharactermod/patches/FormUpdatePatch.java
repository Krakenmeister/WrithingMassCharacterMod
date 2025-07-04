package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import writhingmasscharactermod.cards.common.ReactiveStrike;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractPlayer.class,
        method="combatUpdate"
)
public class FormUpdatePatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void UpdateForm(AbstractPlayer __instance)
    {
        FormFieldPatch.form.get(__instance).update();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher methodMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "update");

            return LineFinder.findInOrder(ctMethodToPatch, methodMatcher);
        }
    }
}
