package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import javassist.CtBehavior;
import writhingmasscharactermod.cards.common.ReactiveStrike;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractCard.class,
        method="applyPowers"
)
public class DamageInfoFormPatch5 {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp", "i"}
    )
    public static void MultiDamageApplyForm(AbstractCard __instance, float[] tmp, int i)
    {
        tmp[i] = FormFieldPatch.form.get(AbstractDungeon.player).atDamageGive(tmp[i], __instance.damageTypeForTurn, __instance);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethod) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractStance.class, "atDamageGive");

            int[] hits = LineFinder.findAllInOrder(ctMethod, matcher);

            if (hits.length < 2) {
                throw new IllegalStateException("Expected at least two calls to AbstractStance.atDamageGive, found " + hits.length);
            }

            return new int[] { hits[1] };
        }
    }
}
