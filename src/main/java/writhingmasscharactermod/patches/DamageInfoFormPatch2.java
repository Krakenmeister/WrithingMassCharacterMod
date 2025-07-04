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
        clz= DamageInfo.class,
        method="applyPowers"
)
public class DamageInfoFormPatch2 {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void Insert(@ByRef float[] tmp, DamageInfo __instance)
    {
        //tmp = AbstractDungeon.player.stance.atDamageGive(tmp, this.type);
        tmp[0] = FormFieldPatch.form.get(AbstractDungeon.player).atDamageGive(tmp[0], __instance.type);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher methodMatcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");

            return LineFinder.findInOrder(ctMethodToPatch, methodMatcher);
        }
    }
}
