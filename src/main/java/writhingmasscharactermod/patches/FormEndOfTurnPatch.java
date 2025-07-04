package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import writhingmasscharactermod.cards.common.ReactiveStrike;

import java.util.ArrayList;

@SpirePatch2(
        clz= GameActionManager.class,
        method="callEndOfTurnActions"
)
public class FormEndOfTurnPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void EndOfTurnLogic(GameActionManager __instance)
    {
        FormFieldPatch.form.get(AbstractDungeon.player).onEndOfTurn();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher methodMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "onEndOfTurn");

            return LineFinder.findInOrder(ctMethodToPatch, methodMatcher);
        }
    }
}
