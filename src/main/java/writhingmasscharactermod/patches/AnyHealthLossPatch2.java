package writhingmasscharactermod.patches;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;
import writhingmasscharactermod.util.OnAnyHealthLossPower;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class AnyHealthLossPatch2 {
    @SpireInsertPatch(
            locator = BeforeDecrementBlockLocator.class
    )
    public static void Insert(AbstractPlayer __instance, DamageInfo info) {
        TempHPTracker.tempHpBefore = TempHPField.tempHp.get(__instance);
    }

    public static class BeforeDecrementBlockLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // Match the call to AbstractCreature.decrementBlock(DamageInfo)
            Matcher matcher = new Matcher.MethodCallMatcher(
                    AbstractPlayer.class,
                    "decrementBlock"
            );

            // Find the line where this method is called
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
