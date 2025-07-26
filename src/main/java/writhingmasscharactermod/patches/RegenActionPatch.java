package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import javassist.CtBehavior;

@SpirePatch2(
        clz = RegenAction.class,
        method = "update"
)
public class RegenActionPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(RegenAction __instance) {
        if (__instance.target.hasPower(RegenPower.POWER_ID) && !__instance.target.isPlayer) {
            AbstractPower p = __instance.target.getPower(RegenPower.POWER_ID);
            --p.amount;
            if (p.amount == 0) {
                __instance.target.powers.remove(p);
            } else {
                p.updateDescription();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // Match the call to getPower("Regeneration")
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "isPlayer");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
