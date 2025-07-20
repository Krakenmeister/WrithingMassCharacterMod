package writhingmasscharactermod.patches.infest;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import writhingmasscharactermod.actions.InfestedCardUseAction;
import writhingmasscharactermod.util.WrithingCard;

import java.util.List;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class PlayInfestedCardsPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"m"}
    )
    public static void Insert(GameActionManager __instance, AbstractMonster m) {
        List<WrithingCard> infestedCards = InfestCardFieldPatch.infestedCards.get(m);
        for (WrithingCard infestedCard : infestedCards) {
            AbstractDungeon.actionManager.addToBottom(new InfestedCardUseAction(m, infestedCard));
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(AbstractMonster.class, "takeTurn");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
            for (int i = 0; i < lines.length; i++) {
                lines[i]++;
            }
            return lines;
        }
    }
}
