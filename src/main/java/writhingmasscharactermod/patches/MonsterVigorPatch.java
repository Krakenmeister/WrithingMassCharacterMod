package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.CtBehavior;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class MonsterVigorPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"m"}
    )
    public static void Insert(GameActionManager __instance, AbstractMonster m) {
        for (AbstractPower power : m.powers) {
            if (power instanceof VigorPower) {
                power.flash();
                __instance.addToBottom(new RemoveSpecificPowerAction(power.owner, power.owner, "Vigor"));
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "applyTurnPowers");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
            for (int i = 0; i < lines.length; i++) {
                lines[i]++;
            }
            return lines;
        }
    }
}
